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
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.swing.ImageIcon;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import java.awt.image.Raster;
import java.awt.Point;

public class PixelGrabberTest {
	public static void main(String[] args) throws Exception {
		// compute the starting time
		long startTime = System.nanoTime();
		long endTime;
		
		// get the image
		File imgFile = new File("circles_1080p_fullred.jpg");
		
		try {
			BufferedImage img = ImageIO.read(imgFile);
			
			// set up the pixel array
			byte[] subpixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
			
			if (img.getAlphaRaster() == null) { // image has no alpha channel
				for (
					int subpixel = 0, row = 0, col = 0;
					subpixel < subpixels.length;
					subpixel += 3 // since each pixel has 3 subpixels (RGB)
				) {
					byte redSubpixel = subpixels[subpixel + 2];
					byte blueSubpixel = subpixels[subpixel];
					byte greenSubpixel = subpixels[subpixel + 1];
					
					if ((redSubpixel & 0xFF) > 0x00) {
						subpixels[subpixel] = (byte)0xff; // full blue
						subpixels[subpixel + 1] = (byte)0x00; // remove green
						subpixels[subpixel + 2] = (byte)0x00; // remove red
					}

					if (
						((redSubpixel & 0xFF) == 0xFF) &&
						((greenSubpixel & 0xFF) == 0x00) &&
						((redSubpixel & 0xFF) == 0x00)
					) {
						subpixels[subpixel] = (byte)0xff; // full blue
						subpixels[subpixel + 1] = (byte)0x00; // remove green
						subpixels[subpixel + 2] = (byte)0x00; // remove red
					}
				}

				// create a new blank canvas for the result
				BufferedImage resultImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
				
				// fill in the 
				resultImg.setData(
					Raster.createRaster(
						resultImg.getSampleModel(),
						new DataBufferByte(subpixels, subpixels.length),
						new Point()
					)
				);
				ImageIO.write(resultImg, "png", new File("output.png"));
			} else {
				throw new Exception("Unexpected transparency.");
			} 
			
			endTime = System.nanoTime();
			System.out.println("Duration: " + (int)((endTime - startTime)/1000000) + " ms");
		} catch (IOException ex) {
			System.err.println("Bad I/O: " + ex);
		} catch (Exception ex) {
			throw ex;
		}
	}
}