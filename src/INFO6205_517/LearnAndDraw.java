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

	/*public static void main(String[] args) throws IOException {
		String IMAGENAME = "panda2.png";   
		BufferedImage image = ImageIO.read(new LearnAndDraw().getClass().getResource(IMAGENAME));
		boolean[][] originalImage = ImageTool.marchThroughImage(image);
		int HEIGHT = image.getHeight();
		List<List<BI>> best_result = new ArrayList<List<BI>>();
		
		for(int i=0; i< HEIGHT; i++) {
			GenerationTool2 gt = new GenerationTool2();
			List<BI> tmp = gt.run(i, originalImage[i]);
			best_result.add(tmp);
			System.out.println(tmp.size());
		}
		
		
		//GenerationTool gt = new GenerationTool();
		//gt.run();
		//System.out.println(gt.run());
	}*/
	
	public static void main(String[] args) throws Exception {
		File pic = new File("test3.jpg");
	    BufferedImage image = ImageIO.read(pic);
		boolean[][] originalImage = ImageTool.marchThroughImage(image);
		int HEIGHT = image.getHeight();
		int WIDTH = image.getWidth();
		List<List<BI>> best_result = new ArrayList<List<BI>>();
		
		ExecutorService pool = Executors.newFixedThreadPool(7);
		for(int i=0; i< HEIGHT; i++) {
			//System.out.println("================");
//			GenerationTool2 gt = new GenerationTool2(i, originalImage[i]);
//			List<BI> tmp = gt.run();
//			best_result.add(tmp);
			
			//GenerationTool2 gt3 = new GenerationTool2();
			//List<BI> tmp = gt3.run(originalImage[i]);
			//best_result.add(tmp);
			//System.out.println(tmp.size());
			boolean[] originalImageLine = originalImage[i];
			Callable<List<BI>> run = new Callable<List<BI>>(){
				@Override
				public List<BI> call() throws IOException, InterruptedException{
					GenerationTool2 gt3 = new GenerationTool2();
					List<BI> tmp = gt3.run(originalImageLine);
					Thread.sleep(1000);
					return tmp;
				}
			};
			pool.submit(run); 	
			best_result.add(run.call());
		}
		pool.shutdown();
		
		boolean[] fixed = new boolean[best_result.size() * WIDTH];
		for(int i = 0; i < HEIGHT; i ++) {
			for(int j = 0; j < WIDTH; j ++) {
				fixed[i * WIDTH + j] = best_result.get(i).get(best_result.get(i).size() - 1).getIndv()[j];
//				int no = i * WIDTH + j;
//				System.out.println("The" + no + " time : " + fixed[i * WIDTH + j]);
//				System.out.println(best_result.get(i).size() - 1);
//				System.out.println("AA");
			}
		}
		
		ImageTool it = new ImageTool(image);
		it.produceImage(image, fixed);
	}
	

}
