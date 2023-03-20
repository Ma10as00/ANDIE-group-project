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
 * @author Steven Mills
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
     * @see MeanFilter(int)
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

        Kernel kernel = new Kernel(2*radius+1, 2*radius+1, array);
        ConvolveOp convOp = new ConvolveOp(kernel);
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        convOp.filter(input, output);

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
        int x = (index % side) - 1;
        int y = 1 - (index / side);

        // Calulate G(x, y, simga).
        float value = 1.0f/(2 * (float)Math.PI * sigma * sigma);
        double pow = Math.exp((double)((float)(-1 * (x * x + y * y))) / (2 * sigma * sigma));
        value = value * (float)pow;

        return value;
    }


}

