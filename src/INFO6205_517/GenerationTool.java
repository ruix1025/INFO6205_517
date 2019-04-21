package INFO6205_517;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class GenerationTool {
	
     
    private int width; //image width    
    boolean[] originalImage; // color information of each row of original/target image
    private int gene_len; // length of gene
    private int chrom_len; 
    private int population; // the number of individual in each generation
    private double p_Cross; // the probability of random crossover appearing
    private double p_Mutation; // the probability of random mutation appearing
    private int gen_limit; // the limitation of the number of generation
    private List<boolean[]> inds = new ArrayList<boolean[]>(population); // individuals in one generation
    private List<BI> bestInds = new ArrayList<BI>(gen_limit); // the set of individuals who take the highest fitness value in their own generation
   
    
    // Constructor
    public GenerationTool() {
    	gene_len = 1; // Every pixel point in one row is a gene
    	p_Cross = 0.05; 
    	p_Mutation = 0.000002;
    	gen_limit = 1000;
    }
    
       
	/**
	 * Method to implement the Genetic Algorithm
	 * @param  originalRow one row of the original/target picture
	 * @return bestInds the set of individuals who take the highest fitness value in their own generation
	 */
    public List<BI> run(boolean[] originalRow) throws IOException {	
    	// Initialization
    	initialValues(originalRow); 
    	initPopulation();
        Random rand = new Random(System.currentTimeMillis());
        // Evolution
        while (gen_limit-- > 0) {
            Collections.shuffle(inds);
            for (int i = 0; i < population - 1; i += 5) {
            	// Crossover
                if (rand.nextDouble() < p_Cross) crossover(inds.get(i), inds.get(i + 1));
                // Mutation
                if (rand.nextDouble() < p_Mutation) mutation(inds.get(i));
            }
            // Natural Selection
            if (selection() == 1) break;
        }
        return bestInds;
    }
    
    
    public List<BI> run(boolean[] originalRow, List<boolean[]> lastInds) throws IOException {
    	initialValues(originalRow);
    	inds = lastInds;
        Random rand = new Random(System.currentTimeMillis());
        while (gen_limit-- > 0) {
            Collections.shuffle(inds);
            for (int i = 0; i < population - 1; i += 5) {
            	// Crossover
                if (rand.nextDouble() < p_Cross) crossover(inds.get(i), inds.get(i + 1));
                // Mutation
                if (rand.nextDouble() < p_Mutation) mutation(inds.get(i));
            }
            // Natural Selection
            if (selection() == 1) break;
        }
        return bestInds;
    }
    
	/**
	 * Method to set values based on target graph
	 * @param originalLine the color info of each line of original/target graph 
	 */
    private void initialValues(boolean[] originalLine) throws IOException {
    	originalImage = originalLine;
    	width = originalImage.length;
    	chrom_len = width;
    	population = width * originalImage.length; // the number of individuals in one generation equal the number of color points of graph
    }
    
    
    
	/**
	 * Method to randomly initialize individuals in the first generation 
	 */
    private void initPopulation() {
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < population; i++) {
            int len = gene_len * chrom_len; // the number of color points in one row
            boolean[] ind = new boolean[len];
            for (int j = 0; j <len; j++)
            	ind[j] = r.nextBoolean();
            inds.add(ind);
        }
    }
    
    
    
	/**
	 * Method to make different individuals implement random crossover / exchange some genes
	 * @param ind1 the first individual 
	 * @param nums the second individual
	 * @return slice
	 */
    public int crossover(boolean[] ind1, boolean[] ind2) {
        Random r = new Random(System.currentTimeMillis());
        int length = ind1.length;
        int slice = 0;
        do {
            slice = r.nextInt(length);
        } while (slice == 0);
        if (slice < length / 2) {
            for (int i = 0; i < slice; i++) {
                boolean tmp = ind1[i];
                ind1[i] = ind2[i];
                ind2[i] = tmp;
            }
        } else {
            for (int i = slice; i < length; i++) {
                boolean tmp = ind1[i];
                ind1[i] = ind2[i];
                ind2[i] = tmp;
            }
        }
        return slice;
    }
    
    
    
	/**
	 * Method to make individual randomly choose its gene to implement mutation
	 * @param ind individual we chose
	 * @return i the index of gene who was mutated
	 */
    public int mutation(boolean[] ind) {
        int length = ind.length;
        Random r = new Random(System.currentTimeMillis());
        int i = r.nextInt(length);
        ind[i] ^= true;
        return i;
    }
    
    
    
	/**
	 * Method to calculate the fitness value of each individual
	 * @param ind individual/ array of one row of current generated graph 
	 * @param originalImage array of the same row in original image. 
	 * @param chrom_len length of chrom/ the length of width of graph
	 * @return the fitness value calculated based on the fitness function
	 */
    public double getFitness(boolean[] ind, boolean[] originalImage, int chrom_len) {
    	double fitness = 0;
    	for(int i = 0; i < chrom_len; i++) {
    		if(originalImage[i] == ind[i])
    			fitness++;
    	}
    	return fitness/chrom_len;
    }
    

    
	/**
	 * Method to select the best individual(with the highest fitness value) from one generation
	 * Then use Roulette Whell Selection to select better individuals and put them into next generation and create corresponding BI instance
	 * Save them into the Best individuals array
	 */
    public double selection() throws IOException {
    	// array to save individuals selected by using Roulette Whell method in the current generation
        boolean[][] next_generation = new boolean[population][]; 
        int length = gene_len * chrom_len;
        for (int i = 0; i < population; i++) next_generation[i] = new boolean[length];
        double[] cumulation = new double[population];
        int best_index = 0;
        double max_fitness = getFitness(inds.get(best_index), originalImage, chrom_len);
        
        // Roulette Whell Selection
        cumulation[0] = max_fitness;
        for (int i = 1; i < population; i++) {
            double fit = getFitness(inds.get(i),originalImage,chrom_len);
            cumulation[i] = cumulation[i - 1] + fit;
            if (fit > max_fitness) {
                best_index = i;
                max_fitness = fit;
            }
        }
        for (int i = 0; i < population; i++)
        	cumulation[i] = cumulation[i]/ cumulation[population - 1];
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < population; i++) {
              next_generation[i] = inds.get(BinarySearch(cumulation, rand.nextDouble() * cumulation[population - 1]));
        }
        // Create BI instance 
        BI bi = new BI(max_fitness, inds.get(best_index));
        bestInds.add(bi);
        // Put them into next generation
        for (int i = 0; i < population; i++) inds.set(i, next_generation[i]);
        System.out.println("Current Max Fitness: "+ max_fitness);
        return max_fitness;
    }
 
    
    
    public int BinarySearch(double[] arr, double target) {
        if (target < 0 || target == 0 || target > arr[arr.length - 1])
            return -1;
        int min = 0;
        int max = arr.length - 1;
        int medium = min;
        do {
            if (medium == (min + max) / 2) break;
            medium = (min + max) / 2;
            if (arr[medium] < target) min = medium;
            else if (arr[medium] > target) max = medium;
            else return medium;
        } while (min < max);
        return max;
    }
    	
    // Get and Set methods
    public int getLimit() {
    	return this.gen_limit;
    }
    
    public void setLimit(int limit) {
    	this.gen_limit = limit;
    }
    
    public List<boolean[]> getCurInds(){
    	return this.inds;
    }
    	
}
