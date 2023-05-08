package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a sobel vertical filter.
 * </p>
 * 
 * <p>
 * A sobel vertical filter detects the vertical edges of a given image with a kernel.
 * The outputted image will be dark (possibly black) where there are no edges, and will be 
 * lighter (possibly white) where edges have been detected. This is greyscale. Note, this 
 * implementation gives us the option to apply the filter after a light Gaussian blur filter
 * (of radius 1), which typically detects the edges of a natrual image better.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Stella Srzich (Modified from Steven Mills)
 */
public class SobelVerticalFilter implements ImageOperation, java.io.Serializable {

    /**
     * This boolean gives us the option to apply the sobel vertical filter after a gaussian blur
     * filter with radius 1 is applied. This works better for natural images where there is a lot of
     * noise potentially obstructing the actual edges. If it is true, the Gaussian blur filter with radiu 1 is applied
     * before we apply the sobel filter.
     */
    private boolean removeNoise;

    /**
     * <p>
     * Construct a sobel vertical filter.
     * </p>
     * 
     * <p>
     * This filter converts an image to grey scale and detects the vertical edges.
     * removeNoise gives us the option to apply the sobel vertical filter after a gaussian blur
     * filter with radius 1 is applied. This works better for natural images where there is a lot of
     * noise potentially obstructing the actual edges. If it is true, the gaussian blur filter with radius 1 is applied
     * before we apply the sobel filter.
     * </p>
     * @see GaussianBlurFilter
     * @param removeNoise True to apply a light Gaussian blur filter before the sobel filter, false otherwise.
     */
    SobelVerticalFilter(boolean removeNoise) {
        this.removeNoise = removeNoise;
    }

    /**
     * <p>
     * Construct a sobel vertical filter.
     * </p>
     * 
     * <p>
     * By default, removeNoise is true. That is, a light Gaussian blur filter
     * is applied to the image before its vertical edges are detected.
     * </p>
     * @see GaussianBlurFilter
     * @see SobelVerticalFilter(boolean removeNoise)
     */
    SobelVerticalFilter() {
        this(true);
    }

    /**
     * <p>
     * Apply a sobel vertical filter to an image.
     * </p>
     * 
     * <p>
     * As with many filters, the sobel vertical filter is implemented via convolution.
     * First, the image is converted to grey scale. Then, if removeNoise is true, a light
     * Gaussian blur filter is applied. Lastly, the vertical edges are detected via convolution.
     * </p>
     * 
     * @param input The image to apply the sobel vertical filter to.
     * @return The resulting (vertical edge detected) image.
     */
    public BufferedImage apply(BufferedImage input) {
        // The values for the edge detection kernel as a 9-element array.
        float [] array = {-1.0f/2.0f, -1.0f, -1.0f/2.0f,
                            0,       0,     0,
                          1.0f/2.0f,  1.0f, 1.0f/2.0f};
        
        Kernel kernel = new Kernel(3, 3, array);
        // Create a new image with the same values as in the original image, but with 
        // the edge pixel values copied to new edge pixel values (the image is bigger by the radius of the kernel)
        // on each side and the top and bottom.
        int radius = 1;
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
        // First, convert the image to grey scale.
        ConvertToGrey grey = new ConvertToGrey();
        BufferedImage edgesPlusInputGrey = grey.apply(edgesPlusInput);

        // If we want to remove the noise, apply a gaussian blur filter of radius 1.
        if (removeNoise) {
            GaussianBlurFilter blur =  new GaussianBlurFilter();
            edgesPlusInputGrey = blur.apply(edgesPlusInput);
        }

        // Apply the edge detection filter to the new buffered image that has extended edges.
        ConvolveOp convOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        BufferedImage uncroppedOutput = new BufferedImage(edgesPlusInputGrey.getColorModel(), edgesPlusInputGrey.copyData(null), edgesPlusInputGrey.isAlphaPremultiplied(), null);
        convOp.filter(edgesPlusInputGrey.getRaster(), uncroppedOutput.getRaster());

        // Crop the uncropped output.
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < input.getWidth(); x++) {
            for (int y = 0; y < input.getHeight(); y++) {
                output.setRGB(x, y, uncroppedOutput.getRGB(x + radius, y + radius));
            }
        }

        // The convolution results in the alpha channel being 0 at all pixels as 
        // the alpha channel will usually have been 255 everywhere before. So, 
        // set it back to 255.
        BufferedImage offsetOutput = makeFullAlpha(output);
        
        // Return the output.
        return offsetOutput;
    }

    /**
     * This support method is used in the final stages of the filter to make all
     * alpha channels 255. That is, make the image opaque.
     * @param input The image to be made opaque.
     * @return The opaque image.
     */
    private static BufferedImage makeFullAlpha(BufferedImage input) {
        int width = input.getWidth();
        int height = input.getHeight();
        // Create a new BufferedImage to store the new image.
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        // Loop through each pixel in the image.
        for (int y = 0; y < height; y++) {
           for (int x = 0; x < width; x++) {
                int val = input.getRGB(x, y);
                // Get the ARGB channels.
                int a = (val >> 24) & 0xff;
                int r = (val >> 16) & 0xff;
                int g = (val >> 8) & 0xff;
                int b = val & 0xff;
                // Offset and scale each channel value.
                int fullA = 255;
                // Put the offset pixel value in output.
                int newVal = (fullA << 24) | (r << 16) | (g << 8) | b;
                output.setRGB(x, y, newVal);
           }
        }
        return output;
    }

}



