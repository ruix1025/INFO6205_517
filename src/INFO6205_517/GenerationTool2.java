package INFO6205_517;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;


public class GenerationTool2 {
	
    public class BI {
        double fitness;
        boolean[] indv;
 
        public BI(double f, boolean[] ind) {
            fitness = f;
            indv = ind;
        }
 
        public double getFitness() {
            return fitness;
        }
 
        public boolean[] getIndv() {
            return indv;
        }
    }
     
    private int WIDTH; //image width    
    boolean[] originalImage;
    
    private int gene_len; 
    private int chrom_len; 
    private int population; 
    private double cross_ratio;
    private double muta_ratio;
    private int iter_limit; 
    private int black_number;
    private List<boolean[]> individuals = new ArrayList<boolean[]>(population); //Individual is each line of the image
    private List<BI> best_individual = new ArrayList<BI>(iter_limit);
    
    private void initialValues(boolean[] originalLine) throws IOException {
    	originalImage = originalLine;
    	WIDTH = originalImage.length;
    	gene_len = 1;//Every pixel is only black or white
    	chrom_len = WIDTH;
    	population = 250;//The number of individuals in each generation
    	cross_ratio = 0.4;
    	muta_ratio = 0.05;
    	iter_limit = 10;
    }
    
    private void initPopulation() {
        Random r = new Random(System.currentTimeMillis());
        black_number = 0;
        for (int j = 0; j < WIDTH; j++)
        	if(originalImage[j]) black_number++;
        
        for (int i = 0; i < population; i++) {
            int len = gene_len * chrom_len;
            boolean[] ind = new boolean[len];
            /*for (int k = 0; k < len; k++)
            	ind[k] = false;
            for (int j = 0; j < black_number; j++)
                ind[r.nextInt(len)] = true;*/
            for (int j = 0; j <len; j++)
            	ind[j] = r.nextBoolean();
            individuals.add(ind);
        }
        
    }
    
    private void cross(boolean[] arr1, boolean[] arr2) {
        Random r = new Random(System.currentTimeMillis());
        int length = arr1.length;
        //System.out.println(length);
        int slice = 0;
        do {
            slice = r.nextInt(length);
        } while (slice == 0);
        if (slice < length / 2) {
            for (int i = 0; i < slice; i++) {
                boolean tmp = arr1[i];
                arr1[i] = arr2[i];
                arr2[i] = tmp;
            }
        } else {
            for (int i = slice; i < length; i++) {
                boolean tmp = arr1[i];
                arr1[i] = arr2[i];
                arr2[i] = tmp;
            }
        }
    }
    
    private void mutation(boolean[] individual) {
    	if(black_number == 0) return;
        int length = individual.length;
        Random r = new Random(System.currentTimeMillis());
        individual[r.nextInt(length)] ^= false;
        /*boolean whiteOrBlack = false;//suggest the first point is white or black
        int firstPoint = r.nextInt(length);
        if (individual[firstPoint]) 
        	whiteOrBlack = true;
        individual[firstPoint] ^= false;
        int secondPoint = r.nextInt(length);
        while (individual[secondPoint] != whiteOrBlack)
        	secondPoint = r.nextInt(length);
        individual[secondPoint] ^= false;*/
    }
    
    private int findByHalf(double[] arr, double find) {
        if (find < 0 || find == 0 || find > arr[arr.length - 1])
            return -1;
        int min = 0;
        int max = arr.length - 1;
        int medium = min;
        do {
            if (medium == (min + max) / 2)
                break;
            medium = (min + max) / 2;
            if (arr[medium] < find)
                min = medium;
            else if (arr[medium] > find)
                max = medium;
            else
                return medium;
 
        } while (min < max);
        return max;
    }
    
    private double getFitness(boolean[] individual) throws IOException {
    	double fitness = 0;
    	for(int i = 0; i < chrom_len; i++) {
    		if(originalImage[i] == individual[i])
    			fitness++;
    	}
    	return fitness/chrom_len;
    }
    
    public double selection() throws IOException {
        boolean[][] next_generation = new boolean[population][]; 
        int length = gene_len * chrom_len;
        for (int i = 0; i < population; i++)
            next_generation[i] = new boolean[length];
        double[] cumulation = new double[population];
        int best_index = 0;
        double max_fitness = getFitness(individuals.get(best_index));
        System.out.println("Max fitness " + max_fitness);
        cumulation[0] = max_fitness;
        for (int i = 1; i < population; i++) {
            double fit = getFitness(individuals.get(i));
            cumulation[i] = cumulation[i - 1] + fit;
            
            if (fit > max_fitness) {
                best_index = i;
                max_fitness = fit;
            }
        }
        for (int i = 0; i < population; i++)
        	cumulation[i] = cumulation[i]/cumulation[population - 1];
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < population; i++) {
              next_generation[i] = individuals.get(findByHalf(cumulation,
    	                    rand.nextDouble() * cumulation[population - 1]));
        }
       
        BI bi = new BI(max_fitness, individuals.get(best_index));
        // printPath(individuals.get(best_index));
        //System.out.println(max_fitness);
        best_individual.add(bi);
        
        for (int i = 0; i < population; i++)
            individuals.set(i, next_generation[i]);
        return max_fitness;
    }
    
    public List<BI> run(boolean[] originalLine) throws IOException {
    	initialValues(originalLine);
        initPopulation();
        Random rand = new Random(System.currentTimeMillis());
        while (iter_limit-- > 0) {
            
            Collections.shuffle(individuals);
            //System.out.println(individuals);
            for (int i = 0; i < population - 1; i += 2) {
                
                if (rand.nextDouble() < cross_ratio) {
                    cross(individuals.get(i), individuals.get(i + 1));
                }
                
                if (rand.nextDouble() < muta_ratio) {
                    mutation(individuals.get(i));
                }
            }
            
            if (selection() == 1)
                break;
        }
        return best_individual;
    }
    
}
