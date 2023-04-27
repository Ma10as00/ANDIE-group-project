package cosc202.andie;

import java.awt.image.*;
import java.util.*;

/**
 * <p>
 * ImageOperation to apply a median (blur) filter.
 * </p>
 * 
 * <p>
 * A median filter blurs an image by replacing each pixel with the median of the
 * pixels in a surrounding neighbourhood. This is much more "blocky" than the 
 * mean or Gaussian blur filters.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Stella Srzich (Modified from Steven Mills)
 * @version 1.0
 */
public class MedianFilter implements ImageOperation, java.io.Serializable {
    
    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a 5x5 filter, and so forth.
     */
    private int radius;

    /**
     * <p>
     * Construct a median filter with the given size.
     * </p>
     * 
     * <p>
     * The size of the filter is the 'radius' of the local neighboorhood used.
     * A size of 1 is a 3x3 filter, 2 is 5x5, and so on.
     * Larger filters give a stronger blurring effect.
     * </p>
     * 
     * @param radius The radius of the newly constructed MedianFilter
     */
    MedianFilter(int radius) {
        this.radius = radius;    
    }

    /**
     * <p>
     * Construct a median filter with the default size.
     * </p
     * >
     * <p>
     * By default, a median filter has radius 1.
     * </p>
     * 
     * @see MedianFilter(int)
     */
    MedianFilter() {
        this(1);
    }

    /**
     * <p>
     * Apply a median filter to an image.
     * </p>
     * 
     * <p>
     * The median filter is implemented by storing the alpha, red, green, and blue
     * channel values from the local neighbourhood of a pixel in an array 
     * and then finding the median values, and assigning that argb value
     * to the pixel in the output image. The size of the local neighbourhood is 
     * specified by the {@link radius}. Larger radii lead to stronger blurring.
     * Note, this implementation does not change the value of pixels at the edge of
     * an image. That is, argb values at position (x, y) with x less than radius + 1, 
     * x >= image width - radius, y less than radius + 1, or y >= image width - radius
     * will just be copied to the output image.
     * </p>
     * 
     * @param input The image to apply the median filter to.
     * @return The resulting (blurred) image.
     */
    public BufferedImage apply(BufferedImage input) {
        // Make a new BufferedImage to write the median pixels.
        // Note, this implementation currently ignores the edges of the image.
        // So, I have made a deep copy of the input image to later be written over.
        ColorModel cm = input.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = input.copyData(null);
        BufferedImage output =  new BufferedImage(cm, raster, isAlphaPremultiplied, null);

        // Loop through each pixel and get the median values for 
        // red, green, blue and alpha channels, then set each corresponding
        // pixel in our output BufferedImage to the given value.
        for (int y = radius; y < input.getHeight() - radius; ++y) {
            for (int x = radius; x < input.getWidth() - radius; ++x) {

                // Collect TYPE_INT_ARGB values for
                // all pixels in the neighbourhood.
                int[] argb = input.getRGB(x - radius, y - radius, 2*radius+1, 2*radius+1, null, 0, 2*radius+1);
                int[] a = new int[argb.length];
                int[] r = new int[argb.length];
                int[] g = new int[argb.length];
                int[] b = new int[argb.length];

                // Split up these values into their channels
                for (int i = 0; i < argb.length; i++) {
                    a[i] = (argb[i] & 0xFF000000) >> 24;
                    r[i] = (argb[i] & 0x00FF0000) >> 16;
                    g[i] = (argb[i] & 0x0000FF00) >> 8;
                    b[i] = (argb[i] & 0x000000FF);
                }

                // Find the median value of each argb channel
                Arrays.sort(a);
                Arrays.sort(r);
                Arrays.sort(g);
                Arrays.sort(b);

                int aVal = a[a.length/2];
                int rVal = r[r.length/2];
                int gVal = g[g.length/2];
                int bVal = b[b.length/2];

                int val = (aVal << 24) | (rVal << 16) | (gVal << 8) | bVal;

                // Finally, set the argb value in to the given pixel
                // in the output image
                output.setRGB(x, y, val);
            }
        }
        
        return output;
    }


}

