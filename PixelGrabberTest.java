import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.awt.Toolkit;
import java.util.HashSet;
import javax.imageio.ImageIO;
import java.awt.image.DataBufferByte;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PixelGrabberTest {
	public static void main(String[] args) {
		// compute the starting time
		final long startTime = System.nanoTime();
		final long endTime;
		
		// get the image
		final File imgFile = new File("circles_2560-1600.png");
		
		try {
			final BufferedImage img = ImageIO.read(imgFile);
			
			// set up the pixel array and set
			final byte[] subpixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
			final int[] pixels = new int[img.getWidth() * img.getHeight()];
			final HashSet<Integer> pixelSet = new HashSet<>();
			
			if (img.getAlphaRaster() == null) { // image has no alpha channel
				for (
					int subpixel = 0, row = 0, col = 0;
					subpixel < subpixels.length;
					subpixel += 3 // since each pixel has 3 subpixels (RGB)
				) {
					pixels[subpixel / 3] = -16777216 // 100% alpha (no transparency)
							+ ((int) subpixels[subpixel] & 0xff) // blue
							+ (((int) subpixels[subpixel + 1] & 0xff) << 8) // green
							+ (((int) subpixels[subpixel + 2] & 0xff) << 16); // red
				}
			} else {
				throw new Exception("Unexpected transparency.");
			} 
			
			endTime = System.nanoTime();
			System.out.println("Duration: " + (int)((endTime - startTime)/1000000) + " ms");
		} catch (IOException ex) {
			System.err.println("Bad I/O: " + ex);
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}
}