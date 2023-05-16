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
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Stella Srzich (Modified from Steven Mills)
 * @version 1.0
 */
public class MedianFilter implements ImageOperation, java.io.Serializable {

    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a
     * 5x5 filter, and so forth.
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
    public MedianFilter(int radius) {
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
    public MedianFilter() {
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
     * Note, this implementation deals with the edges of the image.
     * </p>
     * 
     * @param input The image to apply the median filter to.
     * @return The resulting (blurred) image.
     */
    public BufferedImage apply(BufferedImage input) {
        // Create a new image with the same values as in the original image, but with
        // the edge pixel values copied to new edge pixel values (the image is bigger by
        // the radius of the kernel)
        // on each side and the top and bottom.
        BufferedImage edgesPlusInput = new BufferedImage(input.getWidth() + 2 * radius, input.getHeight() + 2 * radius,
                BufferedImage.TYPE_INT_ARGB);
        // Fill the pixel values of this new buffered image.
        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                // Copy over pixel values from the original image to pixels to the right and
                // lower by 'radius' amount.
                edgesPlusInput.setRGB(x + radius, y + radius, input.getRGB(x, y));
                // If we are at an edge, then we copy that value to the values
                // above/below/right/left.
                if (y == 0) { // We are at the top of the image.
                    for (int i = 0; i < radius; i++) {
                        edgesPlusInput.setRGB(x + radius, i, input.getRGB(x, y));
                    }
                } else if (y == input.getHeight() - 1) { // We are at the bottom of the image.
                    for (int i = 0; i < radius; i++) {
                        edgesPlusInput.setRGB(x + radius, i + input.getHeight() + radius, input.getRGB(x, y));
                    }
                }
                if (x == 0) { // We are at the left of the image.
                    for (int i = 0; i < radius; i++) {
                        edgesPlusInput.setRGB(i, y + radius, input.getRGB(x, y));
                    }
                } else if (x == input.getWidth() - 1) { // We are at the right of the image.
                    for (int i = 0; i < radius; i++) {
                        edgesPlusInput.setRGB(i + input.getWidth() + radius, y + radius, input.getRGB(x, y));
                    }
                }
                // Dealing with corners.
                if (x == 0 && y == 0) { // Top left corner.
                    for (int a = 0; a < radius; a++) {
                        for (int b = 0; b < radius; b++) {
                            edgesPlusInput.setRGB(a, b, input.getRGB(x, y));
                        }
                    }
                } else if (x == 0 && y == input.getHeight() - 1) { // Bottom left corner.
                    for (int a = 0; a < radius; a++) {
                        for (int b = 0; b < radius; b++) {
                            edgesPlusInput.setRGB(a, b + input.getHeight() + radius, input.getRGB(x, y));
                        }
                    }
                } else if (x == input.getWidth() - 1 && y == 0) { // Top right corner.
                    for (int a = 0; a < radius; a++) {
                        for (int b = 0; b < radius; b++) {
                            edgesPlusInput.setRGB(a + input.getWidth() + radius, b, input.getRGB(x, y));
                        }
                    }
                } else if (x == input.getWidth() - 1 && y == input.getHeight() - 1) { // Bottom right corner.
                    for (int a = 0; a < radius; a++) {
                        for (int b = 0; b < radius; b++) {
                            edgesPlusInput.setRGB(a + input.getWidth() + radius, b + input.getHeight() + radius,
                                    input.getRGB(x, y));
                        }
                    }
                }
            }
        }

        // Make this buffered image to apply the filter into. Note, it is still
        // uncropped.
        BufferedImage uncroppedOutput = new BufferedImage(edgesPlusInput.getColorModel(), edgesPlusInput.copyData(null),
                edgesPlusInput.isAlphaPremultiplied(), null);

        // Loop through each pixel and get the median values for
        // red, green, blue and alpha channels, then set each corresponding
        // pixel in our output BufferedImage to the given value.
        for (int y = radius; y < edgesPlusInput.getHeight() - radius; ++y) {
            for (int x = radius; x < edgesPlusInput.getWidth() - radius; ++x) {

                // Collect TYPE_INT_ARGB values for
                // all pixels in the neighbourhood.
                int[] argb = edgesPlusInput.getRGB(x - radius, y - radius, 2 * radius + 1, 2 * radius + 1, null, 0,
                        2 * radius + 1);
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

                int aVal = a[a.length / 2];
                int rVal = r[r.length / 2];
                int gVal = g[g.length / 2];
                int bVal = b[b.length / 2];

                int val = (aVal << 24) | (rVal << 16) | (gVal << 8) | bVal;

                // Finally, set the argb value in to the given pixel
                // in the output image
                uncroppedOutput.setRGB(x, y, val);
            }
        }

        // Crop the uncropped output.
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < input.getWidth(); x++) {
            for (int y = 0; y < input.getHeight(); y++) {
                output.setRGB(x, y, uncroppedOutput.getRGB(x + radius, y + radius));
            }
        }

        return output;
    }
}
