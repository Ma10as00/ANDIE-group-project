package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a sobel horizontal filter.
 * </p>
 * 
 * <p>
 * A sobel horizontal filter detects the horizontal edges of a given image with a kernel.
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
public class SobelHorizontalFilter implements ImageOperation, java.io.Serializable {

    /**
     * This boolean gives us the option to apply the sobel horizontal filter after a gaussian blur
     * filter with radius 1 is applied. This works better for natural images where there is a lot of
     * noise potentially obstructing the actual edges. If it is true, the Gaussian blur filter with radiu 1 is applied
     * before we apply the sobel filter.
     */
    private boolean removeNoise;

    /**
     * <p>
     * Construct a sobel horizontal filter.
     * </p>
     * 
     * <p>
     * This filter converts an image to grey scale and detects the horizontal edges.
     * removeNoise gives us the option to apply the sobel horizontal filter after a gaussian blur
     * filter with radius 1 is applied. This works better for natural images where there is a lot of
     * noise potentially obstructing the actual edges. If it is true, the gaussian blur filter with radius 1 is applied
     * before we apply the sobel filter.
     * </p>
     * @see GaussianBlurFilter
     * @param removeNoise True to apply a light Gaussian blur filter before the sobel filter, false otherwise.
     */
    SobelHorizontalFilter(boolean removeNoise) {
        this.removeNoise = removeNoise;
    }

    /**
     * <p>
     * Construct a sobel horizontal filter.
     * </p>
     * 
     * <p>
     * By default, removeNoise is true. That is, a light Gaussian blur filter
     * is applied to the image before its horizontal edges are detected.
     * </p>
     * @see GaussianBlurFilter
     * @see SobelHorizontalFilter(boolean removeNoise)
     */
    SobelHorizontalFilter() {
        this(true);
    }

    /**
     * <p>
     * Apply a sobel horizontal filter to an image.
     * </p>
     * 
     * <p>
     * As with many filters, the sobel horizontal filter is implemented via convolution.
     * First, the image is converted to grey scale. Then, if removeNoise is true, a light
     * Gaussian blur filter is applied. Lastly, the horizontal edges are detected via convolution.
     * </p>
     * 
     * @param input The image to apply the sobel horizontal filter to.
     * @return The resulting (horizontal edge detected) image.
     */
    public BufferedImage apply(BufferedImage input) {
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

        // If we want to remove the noise, apply a gaussian blur filter of radius 1.
        if (removeNoise) {
            GaussianBlurFilter blur =  new GaussianBlurFilter();
            edgesPlusInput = blur.apply(edgesPlusInput);
        }

        // Apply the edge detection filter to the new buffered image that has extended edges.
        // Note, I am not using ConvolveOp as it makes the negative values get lost.
        int width = edgesPlusInput.getWidth();
        int height = edgesPlusInput.getHeight();
        // We use an array of int to store the pixel values for now so that the negative ones don't get lost.
        int[][] pixels = new int[width][height];
        // We apply the kernel manually.
        // This part also converts the image to grey.
        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {
                int val00 = greyscale(edgesPlusInput.getRGB(x - 1, y - 1));
                int val01 = greyscale(edgesPlusInput.getRGB(x - 1, y));
                int val02 = greyscale(edgesPlusInput.getRGB(x - 1, y + 1));

                int val10 = greyscale(edgesPlusInput.getRGB(x, y - 1));
                int val11 = greyscale(edgesPlusInput.getRGB(x, y));
                int val12 = greyscale(edgesPlusInput.getRGB(x, y + 1));

                int val20 = greyscale(edgesPlusInput.getRGB(x + 1, y - 1));
                int val21 = greyscale(edgesPlusInput.getRGB(x + 1, y));
                int val22 = greyscale(edgesPlusInput.getRGB(x + 1, y + 1));
                // Manually do the kernel convolution.
                int val = (int)( (((-1 * val00) + (0 * val01) + (1 * val02)) 
                            + ((-2 * val10) + (0 * val11) + (2 * val12))
                            + ((-1 * val20) + (0 * val21) + (1 * val22))));
                // Save this value.
                pixels[x][y] = (int) Math.round((float)val/2.0f);
            }
        }
        // Now, we 'squish' all values to be between 0 and 255 by scaling and adding
        // to deal with negative values. Note, this part makes the image mostly grey rather than mostly black.
        BufferedImage uncroppedOutput = offset(pixels);

        // Crop the uncropped output.
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < input.getWidth(); x++) {
            for (int y = 0; y < input.getHeight(); y++) {
                output.setRGB(x, y, uncroppedOutput.getRGB(x + radius, y + radius));
            }
        }
        // Finally, make the edges white and the background black by
        // taking the absolute difference between a pixel channel value and 127.
        //output = absolute(output);
        // Return the output.
        return output;
    }

    /**
     * This support method is used to convert an individual int RGB pixel value
     * to grey scale. This follows the convention of weighting green higher and blue lower
     * as to match how humans perceive brightness.
     * @param rgb The int RGB value of a pixel in a BufferedImage.
     * @return rgb converted to an int RGB value of a pixel in grey scale.
     */
    private static int greyscale(int rgb) {
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = (rgb) & 0xff;
        int grey = (int) Math.round(0.3*r + 0.6*g + 0.1*b);
        //int grey = (int) Math.round((r + g + b)/3);
        return grey;
    }

    /**
     * This support method is used in the final stages of the filter to offset, and rescale, the pixel values
     * to deal with negative results. It takes all pixel values, calculates the range the pixel values
     * are in, and shifts them, potentially squishing or squeezing the range with a scale factor, into
     * the range 0 to 255. Note, this always makes it fully opaque in alpha.
     * @param input The image to be offset to the range 0 to 255. 
     * @return The offset image.
     */
    private static BufferedImage offset(int[][] input) {
        int width = input.length;
        int height = input[0].length;
        // Used to store the min and max pixel values.
        int min = 0;
        int max = 1;
        // Create a new BufferedImage to store the offet image. 
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        // Loop through each pixel in the image to find the minimum and maximum value.
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int val = input[x][y];
                // Update max and min.
                if (val < min) {
                    min = val;
                }  
                if (val > max) {
                    max = val;
                }            
            }
        }
        // Now, loop through the pixel values to offset and rescale them.
        int offset = -min;
        double scale = 255d / ((double) max - min);
        // Finally, rescale pixel values.
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int val = input[x][y];
                // Get the ARGB channels.
                int newVal = (int)((val + offset) * scale);
                // Put the offset pixel value in output. Note, we keep fully opacity.
                int pixel = 0xff000000 | (newVal << 16) | (newVal << 8) | newVal;
                output.setRGB(x, y, pixel);
            }
        }
        return output;
    }

    /**
     * This support method is used in the final stages of the filter after the offset and rescaling
     * to be between 0 and 255. This method then takes those values, and gets their absolute
     * difference from 127, and then rescales everything so that it is still between 0 and 255. 
     * Note, this does not touch the alpha channel. And, this is the stage that means an edge from
     * dark to light is preceived as the same as an edge from light to dark.
     * @param input The image to be made absolute.
     * @return The absolute image, i.e. white edges and black background.
     */
    private static BufferedImage absolute(BufferedImage input) {
        int width = input.getWidth();
        int height = input.getHeight();
        // Create output image.
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        // Now, loop through the pixel values to offset and rescale them.
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int val = input.getRGB(x, y);
                // Get the RGB channels.
                int r = (val >> 16) & 0xff;
                int g = (val >> 8) & 0xff;
                int b = val & 0xff;
                // Get absolute difference from 127 and scale back to between 0 and 255.
                int newR = (int)(Math.abs(r - 127) * (255d/127d));
                int newG = (int)(Math.abs(g - 127) * (255d/127d));
                int newB = (int)(Math.abs(b - 127) * (255d/127d));
                // Put the offset pixel value in output. Note, we keep fully opacity.
                int pixel = 0xff000000 | (newR << 16) | (newG << 8) | newB;
                output.setRGB(x, y, pixel);
            }
        }
        return output;
    }

}


