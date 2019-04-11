
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * 用遗传算法走迷宫
 *
 * @author Orisun
 *
 */
public class Draw {
 
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
    
    private String IMAGENAME = "panda.jpg";  //Picture name 
    private int WIDTH;      //The width of the picture
    private int HEIGHT;    //The height of the picture
    
    private int gene_len; // 基因长度
    private int chrom_len = WIDTH*HEIGHT; // 染色体长度
    private int population; // 种群大小
    private double cross_ratio;// 交叉率
    private double muta_ratio;// 变异率
    private int iter_limit;// 最多进化的代数
    private List<boolean[]> individuals = new ArrayList<boolean[]>(population); // 存储当代种群的染色体
    private List<BI> best_individual = new ArrayList<BI>(iter_limit);// 存储每一代中最优秀的个体
 
    //Initial the whole program, read picture and initial values
    private void initialValues() throws IOException {
    	BufferedImage image = ImageIO.read(this.getClass().getResource(IMAGENAME));
    	WIDTH = image.getWidth();
    	HEIGHT = image.getHeight();
    	gene_len = 2;
    	population = 20;
    	cross_ratio = 0.83;
    	muta_ratio = 0.002;
    	iter_limit = 300;
    }
    
    // 初始化种群
    public void initPopulation() {
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < population; i++) {
            int len = gene_len * chrom_len;
            boolean[] ind = new boolean[len];
            for (int j = 0; j < len; j++)
                ind[j] = r.nextBoolean();
            individuals.add(ind);
        }
    }
 
    // 交叉
    public void cross(boolean[] arr1, boolean[] arr2) {
        Random r = new Random(System.currentTimeMillis());
        int length = arr1.length;
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
 
    // 变异
    public void mutation(boolean[] individual) {
        int length = individual.length;
        Random r = new Random(System.currentTimeMillis());
        individual[r.nextInt(length)] ^= false;
    }
 
    // 轮盘法选择下一代,并返回当代最高的适应度值
    public double selection() {
        boolean[][] next_generation = new boolean[population][]; // 下一代
        int length = gene_len * chrom_len;
        for (int i = 0; i < population; i++)
            next_generation[i] = new boolean[length];
        double[] cumulation = new double[population];
        int best_index = 0;
        double max_fitness = getFitness(individuals.get(best_index));
        cumulation[0] = max_fitness;
        for (int i = 1; i < population; i++) {
            double fit = getFitness(individuals.get(i));
            cumulation[i] = cumulation[i - 1] + fit;
            // 寻找当代的最优个体
            if (fit > max_fitness) {
                best_index = i;
                max_fitness = fit;
            }
        }
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < population; i++)
            next_generation[i] = individuals.get(findByHalf(cumulation,
                    rand.nextDouble() * cumulation[population - 1]));
        // 把当代的最优个体及其适应度放到best_individual中
        BI bi = new BI(max_fitness, individuals.get(best_index));
        // printPath(individuals.get(best_index));
        //System.out.println(max_fitness);
        best_individual.add(bi);
        // 新一代作为当前代
        for (int i = 0; i < population; i++)
            individuals.set(i, next_generation[i]);
        return max_fitness;
    }
 
    // 折半查找
    public int findByHalf(double[] arr, double find) {
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
 
    // 计算适应度
    public double getFitness(boolean[] individual) {
    	
    }
 
    // 运行遗传算法
    public boolean run() {
        // 初始化种群
        initPopulation();
        Random rand = new Random(System.currentTimeMillis());
        boolean success = false;
        while (iter_limit-- > 0) {
            // 打乱种群的顺序
            Collections.shuffle(individuals);
            for (int i = 0; i < population - 1; i += 2) {
                // 交叉
                if (rand.nextDouble() < cross_ratio) {
                    cross(individuals.get(i), individuals.get(i + 1));
                }
                // 变异
                if (rand.nextDouble() < muta_ratio) {
                    mutation(individuals.get(i));
                }
            }
            // 种群更替
            if (selection() == 1) {
                success = true;
                break;
            }
        }
        return success;
    }
 
//  public static void main(String[] args) {
//      GA ga = new GA(8, 8);
//      if (!ga.run()) {
//          System.out.println("没有找到走出迷宫的路径.");
//      } else {
//          int gen = ga.best_individual.size();
//          boolean[] individual = ga.best_individual.get(gen - 1).indv;
//          System.out.println(ga.getPath(individual));
//      }
//  }
}
