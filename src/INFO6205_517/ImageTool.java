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

	// Constructor
	public ImageTool(BufferedImage image) {
		try {
		    // get the BufferedImage, using the ImageIO class
		    readImage(image);
		    } catch (IOException e) {
		      System.err.println(e.getMessage());
		    }
	}
	
	
	/**
	 * Method to produce the image based on the input boolean array 
	 * @param image the original/target graph 
	 * @param nums the fixed boolean array which is produced in the process of Natural Selection
	 * @param name the name of the output graph file
	 */
	 public void produceImage(BufferedImage image, boolean[] nums, String name) throws IOException {
			int rgb = 0; // the info of color
			int w = image.getWidth(); // the width of image
			int h = image.getHeight(); // the height of image
			for(int i = 0; i < h; i++) {
				for(int j = 0; j < w; j++) {
					int cur = i * w + j;
					if(nums[cur] == false) { // false means white
						Color col = new Color(255, 255, 255); 
						rgb = col.getRGB();
					}
					else {                  // true means black
						Color col = new Color(0, 0, 0); 
						rgb = col.getRGB();
					}
					image.setRGB(j, i, rgb);
				}
			}
			
			// Output new graph
			Iterator<ImageWriter> it = ImageIO.getImageWritersByFormatName("jpg");
		    ImageWriter writer = it.next();
		    ImageOutputStream ios = ImageIO.createImageOutputStream(new File(name + ".jpg"));
		    writer.setOutput(ios);
		    writer.write(image);
		    image.flush();
		    ios.flush();
		} 
	 
	 
	/**
	 * Method to read the original image and use array to store the color/RGB information of this picture
	 * @param image the original/target graph 
	 * @return colorInfo the array store the whole color information about the target/original pic
	 */
	public static boolean[][] readImage(BufferedImage image) throws IOException {
		
		int w = image.getWidth(); // the width of image
		int h = image.getHeight(); // the height of image
		boolean[][] colorInfo = new boolean[w][h]; // the array to store color information
		int rgb = 0; // the info of color
		
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				int pixel = image.getRGB(j, i); // get the color information from the image
				boolean isBlack = true;         // If white, false; If black, true
				int red = (pixel & 0xff0000) >> 16; // the degree of red can change the color to white or black 
				if (red > 100) {                // White's RGB = (255, 255, 255)
					isBlack = false;
					Color col = new Color(255, 255, 255); 
					rgb = col.getRGB();
				}
				else {                         // Black's RGB = (0, 0, 0)
					isBlack = true;
					Color col = new Color(0, 0, 0); 
					rgb = col.getRGB();
				}
				image.setRGB(j, i, rgb);       // set the new color information from the image
				colorInfo[i][j] = isBlack;	
			}
		}
		return colorInfo;
	}

}