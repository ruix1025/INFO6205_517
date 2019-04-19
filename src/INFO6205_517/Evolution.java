package INFO6205_517;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadPoolExecutor;

import javax.imageio.ImageIO;


public class Evolution {
	
	public static void main(String[] args) throws Exception {
		// Read the original Image and produce the corresponding information of the image
		File pic = new File("original.jpg"); 
	    BufferedImage image = ImageIO.read(pic);
		boolean[][] originalImage = ImageTool.readImage(image); // the color/RGB information of each row and column of the target image
		int h = image.getHeight();                              // the height of image
		int w = image.getWidth();                               // the width of image
		List<List<BI>> best_result = new ArrayList<List<BI>>(); // Container to store all the best fitness rows
		
		// make each row implement GAs
		for(int i=0; i< h; i++) {
			
			/**
			 * Total Random mutation
			 */
			GenerationTool gt = new GenerationTool();
			List<BI> tmp = gt.run(originalImage[i]); // best individuals of each kind of row
			best_result.add(tmp);                   // the size of best_result is equals height of image. the size of tmp equals the number of generation
			
			
			/**
			 * Random mutation with part of Directed mutation (mutation_Xmen)
			 */
//			GenerationTool_X gtX = new GenerationTool_X();
//			List<BI> tmpX = gtX.run(originalImage[i]); // Best individuals of each kind of row
//			best_result.add(tmpX);                     // the size of best_result is equals height of image. the size of tmp equals the number of generation
			
			
		}
		
		// Choose the last BI individual of each row and combine them in one fixed array for generating a integral picture
		boolean[] fixed = new boolean[best_result.size() * w];
		for(int i = 0; i < h; i ++) {
			for(int j = 0; j < w; j ++) {
				fixed[i * w + j] = best_result.get(i).get(best_result.get(i).size() - 1).getIndv()[j];
			}
		}
		
		// Generate the final picture after whole process based on the fixed array which was produced before
		ImageTool it = new ImageTool(image);
		it.produceImage(image, fixed, "final");
	}
}
