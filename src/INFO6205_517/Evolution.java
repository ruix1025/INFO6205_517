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
	
private static void draw(BufferedImage image) throws IOException {
		int count = 0;
		int times = 5; // the number of graphs you want to generate in the process
		boolean[][] origin = ImageTool.readImage(image);
		int h = image.getHeight();
		int w = image.getWidth();
		List<List<boolean[]>> lastInds = new ArrayList<List <boolean[]>>();
		
		while(count <= times) {
			List<List<BI>> best_result = new ArrayList<List<BI>>();
			if(count==0) {
				for(int i=0; i< h; i++) {
//					GenerationTool gt = new GenerationTool();
					
					GenerationTool_X gt = new GenerationTool_X();  // If you want to try directed mutation, please make this line useful
					 
					int curLimit = gt.getLimit();
					gt.setLimit(curLimit/ times);
					List<BI> tmp = gt.run(origin[i]);
					List<boolean[]> last = gt.getCurInds();
					lastInds.add(last);
					best_result.add(tmp);
				}
			}
			if(count != 0) {
				for(int i=0; i< h; i++) {
					GenerationTool_X gt = new GenerationTool_X();
					int curLimit = gt.getLimit();
					gt.setLimit(curLimit/ times);
					List<BI> tmp = gt.run(origin[i], lastInds.get(i));
					List<boolean[]> last = gt.getCurInds();
					lastInds.set(i, last);
					best_result.add(tmp);
				}
			}
			boolean[] fixed = new boolean[best_result.size() * w];
			for(int i = 0; i < h; i ++) {
				for(int j = 0; j < w; j ++) {
					fixed[i * w + j] = best_result.get(i).get(best_result.get(i).size() - 1).getIndv()[j];
				}
				int num  = best_result.get(i).size() - 1;
			}
			ImageTool it = new ImageTool(image);
			it.produceImage(image, fixed, "Test" + String.valueOf(count));
			System.out.println("Print the " + count + " graph");
			count++;
		}	
	}
	


	public static void main(String[] args) throws Exception {
		File pic = new File("original.jpg");
	    BufferedImage image = ImageIO.read(pic);
	    draw(image);
	}
}
