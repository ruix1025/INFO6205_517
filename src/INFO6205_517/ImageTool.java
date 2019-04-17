package INFO6205_517;

import java.awt.Color;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class ImageTool extends Component {

	
	public ImageTool(BufferedImage image) {
		try {
		    // get the BufferedImage, using the ImageIO class
//		    File pic = new File("test3.jpg");
//		    BufferedImage image = ImageIO.read(pic);
		    marchThroughImage(image);
		    } catch (IOException e) {
		      System.err.println(e.getMessage());
		    }
	}
	 
	 public void produceImage(BufferedImage image, boolean[] nums) throws IOException {
			int rgb = 0;
			int w = image.getWidth();
			int h = image.getHeight();
			for(int i = 0; i < h; i++) {
				for(int j = 0; j < w; j++) {
					int cur = i * w + j;
					// 0 means white
					if(nums[cur] == false) {
						Color col = new Color(255, 255, 255); 
						rgb = col.getRGB();
					}
					
					// more than 0 means black
					else {
						Color col = new Color(0, 0, 0); 
						rgb = col.getRGB();
					}
					
					image.setRGB(j, i, rgb);
				}
			}
			// Output new graph
			Iterator<ImageWriter> it = ImageIO.getImageWritersByFormatName("jpg");
		    ImageWriter writer = it.next();
		    ImageOutputStream ios = ImageIO.createImageOutputStream(new File("testtest.jpg"));
		    writer.setOutput(ios);
		    writer.write(image);
		    image.flush();
		    ios.flush();
		} 
	 
	
	public static boolean[][] marchThroughImage(BufferedImage image) throws IOException {
		int w = image.getWidth();
		int h = image.getHeight();
		boolean[][] result = new boolean[w][h];
		int rgb = 0;

		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				int pixel = image.getRGB(j, i);
				boolean whiteOrBlack = true;
				int red = (pixel & 0xff0000) >> 16;
			
				// White's RGB = (255, 255, 255)
				if (red > 100) {   
					whiteOrBlack = false;
					Color col = new Color(255, 255, 255); 
					rgb = col.getRGB();
				}
				
				// Black's RGB = (0, 0, 0)
				else {  
					whiteOrBlack = true;
					Color col = new Color(0, 0, 0); 
					rgb = col.getRGB();
				}
				
				image.setRGB(j, i, rgb);
				
				// black is true and white is false
				result[i][j] = whiteOrBlack;

			
			}
		}
		
		// Output new graph
		Iterator<ImageWriter> it = ImageIO.getImageWritersByFormatName("jpg");
	    ImageWriter writer = it.next();
	    ImageOutputStream ios = ImageIO.createImageOutputStream(new File("new1.jpg"));
	    writer.setOutput(ios);
	    writer.write(image);
	    image.flush();
	    ios.flush();

		return result;
	}

	
	
	public ImageTool() {
		try {
		    // get the BufferedImage, using the ImageIO class
		    File pic = new File("test3.jpg");
		    BufferedImage image = ImageIO.read(pic);
		    marchThroughImage(image);
		    } catch (IOException e) {
		      System.err.println(e.getMessage());
		    }
	}

}