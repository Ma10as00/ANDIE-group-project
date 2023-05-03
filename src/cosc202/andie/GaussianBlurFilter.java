package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a Gaussian blur (natrual blur) filter.
 * </p>
 * 
 * <p>
 * A Gaussian blur filter blurs an image by applying a convolution where each entry
 * is taken from the 2-dimensional Gaussian function, with x and y (positions) and 
 * sigma (standard deviation) as parameters. Sigma is about one third of the kernel radius.
 * So, bigger values of the radius make sigma bigger, and thus a stronger blur.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @see java.awt.image.ConvolveOp
 * @author Stella Srzich (Modified from Steven Mills)
 * @version 1.0
 */
public class GaussianBlurFilter implements ImageOperation, java.io.Serializable {
    
    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a 5x5 filter, and so forth.
     */
    private int radius;

    /**
     * <p>
     * Construct a Gaussian blur filter with the given size.
     * </p>
     * 
     * <p>
     * The size of the filter is the 'radius' of the convolution kernel used,
     * which also effect sigma (the standard deviation) for G(x, y, sigma).
     * A size of 1 is a 3x3 filter, 2 is 5x5, and so on.
     * Larger filters give a stronger blurring effect.
     * </p>
     * 
     * @param radius The radius of the newly constructed GaussianBlurFilter
     */
    GaussianBlurFilter(int radius) {
        this.radius = radius;    
    }

    /**
     * <p>
     * Construct a Gaussian blur filter with the default size.
     * </p
     * >
     * <p>
     * By default, a Gaussian blur filter has radius 1.
     * </p>
     * 
     * @see GaussianBlurFilter(int)
     */
    GaussianBlurFilter() {
        this(1);
    }

    /**
     * <p>
     * Apply a Gaussian blur filter to an image.
     * </p>
     * 
     * <p>
     * As with many filters, the Gaussian blur filter is implemented via convolution.
     * The size of the convolution kernel is specified by the {@link radius}.  
     * Larger radii lead to stronger blurring, and affects sigma.
     * </p>
     * 
     * @param input The image to apply the Gaussian blur filter to.
     * @return The resulting (blurred) image.
     */
    public BufferedImage apply(BufferedImage input) {
        // Convert the radius to sigma for creating our array for our kernel.
        // Note this is not entirely accurate, but is good enough.
        float sigma = ((float)radius)/3.0f;

        // Create array to store kernel values.
        int size = (2*radius+1) * (2*radius+1);
        float [] array = new float[size];

        // Fill array with corresponding Gaussian values (unnormalized).
        for (int i = 0; i < size; i++) {
            array[i] = calculateGaussian(i, size, sigma);
        }
        
        // Normalise the values in array (make sure they sum to 1).
        float sum = 0;
        for (int i = 0; i < size; i++) {
            sum += array[i];
        }
        for (int i = 0; i < size; i++) {
            array[i] = array[i] / sum;
        }

        // Create the kernel.
        Kernel kernel = new Kernel(2*radius+1, 2*radius+1, array);

        // Create a new image with the same values as in the original image, but with 
        // the edge pixel values copied to new edge pixel values (the image is bigger by the radius of the kernel)
        // on each side and the top and bottom.
        BufferedImage edgesPlusInput = new BufferedImage(input.getWidth() + 2*radius, input.getHeight() + 2*radius, BufferedImage.TYPE_INT_ARGB);
        // Fill the pixel values of this new buffered image.
        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                // Copy over pixel values from the original image to pixels to the right and lower by 'radius' amount.
                edgesPlusInput.setRGB(x + radius, y + radius, input.getRGB(x, y));
                // If we are at an edge, then we copy that value to the values above/below/right/left.
                if (y == 0) { // We are at the top of the image.
                    for (int i = 0; i < radius; i++) {
                        edgesPlusInput.setRGB(x + radius, i, input.getRGB(x, y));
                    }
                }
                else if (y == input.getHeight() - 1) { // We are at the bottom of the image.
                    for (int i = 0; i < radius; i++) {
                        edgesPlusInput.setRGB(x + radius, i + input.getHeight() + radius, input.getRGB(x, y));
                    }
                }
                if (x == 0) { // We are at the left of the image.
                    for (int i = 0; i < radius; i++) {
                        edgesPlusInput.setRGB(i, y + radius, input.getRGB(x, y));
                    }
                }
                else if (x == input.getWidth() - 1) { // We are at the right of the image.
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
                }
                else if (x == 0 && y == input.getHeight() - 1) { // Bottom left corner.
                    for (int a = 0; a < radius; a++) {
                        for (int b = 0; b < radius; b++) {
                            edgesPlusInput.setRGB(a, b + input.getHeight() + radius, input.getRGB(x, y));
                        }
                    }
                }
                else if (x == input.getWidth() - 1 && y == 0) { // Top right corner.
                    for (int a = 0; a < radius; a++) {
                        for (int b = 0; b < radius; b++) {
                            edgesPlusInput.setRGB(a + input.getWidth() + radius, b, input.getRGB(x, y));
                        }
                    }
                }
                else if (x == input.getWidth() - 1 && y == input.getHeight() - 1) { // Bottom right corner.
                    for (int a = 0; a < radius; a++) {
                        for (int b = 0; b < radius; b++) {
                            edgesPlusInput.setRGB(a + input.getWidth() + radius, b + input.getHeight() + radius, input.getRGB(x, y));
                        }
                    }
                }
            }
        }
        // Apply the filter to the new buffered image that has interpolated edges.
        ConvolveOp convOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        BufferedImage uncroppedOutput = new BufferedImage(edgesPlusInput.getColorModel(), edgesPlusInput.copyData(null), edgesPlusInput.isAlphaPremultiplied(), null);
        convOp.filter(edgesPlusInput, uncroppedOutput);

        // Crop the uncropped output.
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < input.getWidth(); x++) {
            for (int y = 0; y < input.getHeight(); y++) {
                output.setRGB(x, y, uncroppedOutput.getRGB(x + radius, y + radius));
            }
        }

        return output;
    }

    /**
     * <p>
     * Calculate a 2-dimensional Gaussian float values for an element
     * in an array of float values for our kernel.
     * </p>
     * 
     * <p>
     * Note, this depends on the length of the array and the index in the array
     * we are calulating the Gaussian for, as this will determine x and y
     * in G(x, y, sigma).
     * </p>
     * 
     * @param index The index of the array we are calculating the Gaussian for.
     * @param size The size of the array.
     * @param sigma The value of sigma (the standard deviation).
     * @return The resulting (blurred)) image.
     */
    private float calculateGaussian(int index, int size, float sigma) {
        // Calculate x and y, the integer coordinates in our kernel.
        // Note, (x, y) = (0, 0) is the center of the kernel.
        // Also note, implicit assumption, which is true when called from 
        // the apply method, that size is a square number.
        int side = (int)Math.sqrt(size); 
        int radius = (side - 1)/2;
        int x = (index % side) - radius;
        int y = radius - (index / side);

        // Calulate G(x, y, sigma).
        float value = 1.0f/(2 * ((float)Math.PI) * sigma * sigma);
        double pow = Math.exp((double)((float)(-1.0f * (x * x + y * y))) / (2.0f * sigma * sigma));
        value = value * ((float)pow);

        return value;
    }


}

