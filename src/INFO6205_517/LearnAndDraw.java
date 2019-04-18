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

import INFO6205_517.GenerationTool2.BI;

public class LearnAndDraw {
	
	public static void main(String[] args) throws Exception {
		File pic = new File("test3.jpg");
	    BufferedImage image = ImageIO.read(pic);
		boolean[][] originalImage = ImageTool.marchThroughImage(image);
		int HEIGHT = image.getHeight();
		int WIDTH = image.getWidth();
		List<List<BI>> best_result = new ArrayList<List<BI>>();
		
		//ExecutorService pool = Executors.newFixedThreadPool(7);
		for(int i=0; i< HEIGHT; i++) {
			//System.out.println("================");
//			GenerationTool2 gt = new GenerationTool2(i, originalImage[i]);
//			List<BI> tmp = gt.run();
//			best_result.add(tmp);
			GenerationTool2 gt3 = new GenerationTool2();
			List<BI> tmp = gt3.run(originalImage[i]);
			best_result.add(tmp);
			//System.out.println(tmp.size());
			/*boolean[] originalImageLine_1 = originalImage[i];
			InvolveLine il_1 = new InvolveLine();
			il_1.setLine(originalImageLine_1);
			pool.submit(il_1); 	
			boolean[] originalImageLine_2 = originalImage[i+1];
			InvolveLine il_2 = new InvolveLine();
			il_2.setLine(originalImageLine_2);
			pool.submit(il_2); 	
			boolean[] originalImageLine_3 = originalImage[i+2];
			InvolveLine il_3 = new InvolveLine();
			il_3.setLine(originalImageLine_3);
			pool.submit(il_3); 	
			boolean[] originalImageLine_4 = originalImage[i+3];
			InvolveLine il_4 = new InvolveLine();
			il_4.setLine(originalImageLine_4);
			pool.submit(il_4);
			
			int threadCount = ((ThreadPoolExecutor)pool).getActiveCount();
			System.out.println("Running thread: " + threadCount);
			
			best_result.add(il_1.call());
			best_result.add(il_2.call());
			//best_result.add(il_3.call());
			//best_result.add(il_4.call());*/
		}
		//pool.shutdown();
		
		boolean[] fixed = new boolean[best_result.size() * WIDTH];
		for(int i = 0; i < HEIGHT; i ++) {
			for(int j = 0; j < WIDTH; j ++) {
				fixed[i * WIDTH + j] = best_result.get(i).get(best_result.get(i).size() - 1).getIndv()[j];
			}
		}
		
		ImageTool it = new ImageTool(image);
		it.produceImage(image, fixed);
	}
	

}
