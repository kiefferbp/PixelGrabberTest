import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.awt.Toolkit;
import java.util.HashSet;

public class PixelGrabberTest {
	public static final int WIDTH = 2560;
	public static final int HEIGHT = 1600;

	public static boolean isGrayScaleImage(PixelGrabber pg) {
		return pg.getPixels() instanceof byte[];
	}
	
	public static void main(String[] args) {
		final long startTime = System.nanoTime();
		final int[] pixels = new int[WIDTH * HEIGHT];
		final Image img = Toolkit.getDefaultToolkit().getImage("circles_2560-1600.png");
		final PixelGrabber pg = new PixelGrabber(img, 0, 0, WIDTH, HEIGHT, pixels, 0, WIDTH);
		final HashSet<Integer> pixelSet = new HashSet<>();
		final long endTime;
		
		try {
			if (!pg.grabPixels()) {
				System.err.println("Pixel grab failed!");
				return;
			}

			for (final int pixel : pixels) {
				pixelSet.add(pixel);
			}
			
			System.out.println(pixelSet);

			endTime = System.nanoTime();
			System.out.println("Duration: " + (int)((endTime - startTime)/1000000) + " ms");
			
		} catch (InterruptedException ex) {
			System.err.println(ex);
		}
	}
}