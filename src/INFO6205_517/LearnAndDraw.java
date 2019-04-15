package INFO6205_517;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import INFO6205_517.GenerationTool2.BI;

public class LearnAndDraw {

	public static void main(String[] args) throws IOException {
		String IMAGENAME = "panda2.png";   
		BufferedImage image = ImageIO.read(new LearnAndDraw().getClass().getResource(IMAGENAME));
		boolean[][] originalImage = ImageTool.marchThroughImage(image);
		int HEIGHT = image.getHeight();
		List<List<BI>> best_result = new ArrayList<List<BI>>();
		
		for(int i=0; i< HEIGHT; i++) {
			GenerationTool2 gt = new GenerationTool2();
			List<BI> tmp = gt.run(i, originalImage[i]);
			best_result.add(tmp);
		}
		
		
		//GenerationTool gt = new GenerationTool();
		//gt.run();
		//System.out.println(gt.run());
	}

}
