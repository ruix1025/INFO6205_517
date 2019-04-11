import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageTools extends Component {

	/*
	 * public static void main(String[] foo) { new ImageChange2Boolean(); }
	 */
	
	public boolean[] marchThroughImage(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();
		boolean[] result = new boolean[w * h];

		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				int pixel = image.getRGB(j, i);
				boolean whiteOrBlack = true;
				int red = (pixel & 0xff0000) >> 16;
				if (red > 100)
					whiteOrBlack = false;
				// black is true and white is false
				result[i * w + j] = whiteOrBlack;
			}
		}

		return result;
	}

}
