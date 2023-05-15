package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a sobel horizontal filter.
 * </p>
 * 
 * <p>
 * A sobel orientation filter detects the horizontal and vertical component of edges of a given image with a kernel.
 * The outputted image will be dark (possibly black) where there are no edges, and will be 
 * lighter where edges have been detected. Then, the orientation of the edges decides the hue of the pixel. This
 * hue is decided by the angle of the polar coordinate of the point (magnitiude of horizontal edge, magnitude of vertical edge)
 * in the cartesian plane, where the positive x axis is red, negative x axis is green, positive y axis is yellow and negative
 * y axis is blue. Note, this implementation gives us the option to apply the filter after a light Gaussian blur filter.
 * (of radius 1), which typically detects the edges of a natrual image better.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Stella Srzich (Modified from Steven Mills)
 */
public class SobelOrientationFilter implements ImageOperation, java.io.Serializable {

    /**
     * This boolean gives us the option to apply the sobel orientation filter after a gaussian blur
     * filter with radius 1 is applied. This works better for natural images where there is a lot of
     * noise potentially obstructing the actual edges. If it is true, the Gaussian blur filter with radiu 1 is applied
     * before we apply the sobel filter.
     */
    private boolean removeNoise;

    /**
     * This boolean allows the user to decide if they want to have the edges coloured based on orientation, 
     * or not colour them. If it is true, the edges are coloured. If it is not, the edges aren't coloured -
     * this is equivalent to the standard full Sobel filter.
     */
    private boolean hue;

    /**
     * <p>
     * Construct a sobel orientation filter.
     * </p>
     * 
     * <p>
     * This filter detects the horisontal and vertical edges of an image. The modulus of the 
     * 'strength' of this detection is used to decide the brightness of each pixel. So, where the is a bright pixel,
     * there will be an edge, and where there is a black pixel, there won't be an edge. Then, the hue is determined
     * by the orientation of the edge. removeNoise gives us the option to apply the sobel horizontal filter after a gaussian blur
     * filter with radius 1 is applied. This works better for natural images where there is a lot of
     * noise potentially obstructing the actual edges. If it is true, the gaussian blur filter with radius 1 is applied
     * before we apply the sobel filter.
     * </p>
     * 
     * <p>
     * The user may also decide if they want to have the edges coloured based on orientation, 
     * or not colour them with hue. If it is true, the edges are coloured. If it is not, the edges aren't coloured -
     * this is equivalent to the standard full Sobel filter.
     * </p>
     * @see GaussianBlurFilter
     * @param removeNoise True to apply a light Gaussian blur filter before the sobel filter, false otherwise.
     * @param hue True to colour the edges based on orientation, false otherwise.
     */
    SobelOrientationFilter(boolean removeNoise, boolean hue) {
        this.removeNoise = removeNoise;
        this.hue = hue;
    }

    /**
     * <p>
     * Construct a sobel orientation filter.
     * </p>
     * 
     * <p>
     * By default, removeNoise and hue are true. That is, a light Gaussian blur filter
     * is applied to the image before its edges are detected. And, the edges are coloured based
     * on orientation.
     * </p>
     * @see GaussianBlurFilter
     * @see SobelOrientationFilter(boolean removeNoise, boolean hue)
     */
    SobelOrientationFilter() {
        this(true, true);
    }

    /**
     * <p>
     * Apply a sobel orientation filter to an image.
     * </p>
     * 
     * <p>
     * As with many filters, the sobel orientation filter is implemented via convolution.
     * First, this filter will apply a light Gaussian blur if removeNoise is true. Then, 
     * the horizontal and vertical edges are detected with Sobel kernels. The modulus of the 
     * 'strength' of this detection is used to decide the brightness of each pixel. So, where the is a bright pixel,
     * there will be an edge, and where there is a black pixel, there won't be an edge. Then, the hue is determined
     * by the orientation of the edge.
     * </p>
     * 
     * @param input The image to apply the sobel orientation filter to.
     * @return The resulting (edge orientation detected) image.
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
        int[][] magnitudes = new int[width][height];
        double[][] thetas = new double[width][height];
        // We apply the kernel manually.
        // This part also converts the image to grey scale.
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
                int dx = (int)( (((-1 * val00) + (0 * val01) + (1 * val02)) 
                            + ((-2 * val10) + (0 * val11) + (2 * val12))
                            + ((-1 * val20) + (0 * val21) + (1 * val22))));

                int dy = (int) (((-1 * val00) + (-2 * val01) + (-1 * val02))
                            + ((0 * val10) + (0 * val11) + (0 * val12))
                            + ((1 * val20) + (2 * val21) + (1 * val22)));
                // Scale the values to normalise.
                int dxVal = (int) Math.round((float)dx/2.0f);
                int dyVal = (int) Math.round((float)dy/2.0f);
                // Calculate the magnitude of the edge.
                int mag = (int)Math.sqrt(dxVal * dxVal + dyVal * dyVal);
                // Save this value.
                magnitudes[x][y] = mag;
                // Calculate the angles in radians this would be, i.e. polar coordinate.
                // This can be 0 to 2*pi.
                thetas[x][y] = Math.atan2(dyVal, dxVal) + Math.PI;
            }
        }
        // Now, we 'squish' all values to be between 0 and 255 by scaling and adding
        // to deal with negative values. Note, this part makes the image mostly grey rather than mostly black.
        BufferedImage uncroppedOutput = offset(magnitudes);
        // Now, we assign a hue to each pixel based on its 
        // Crop the uncropped output.
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < input.getWidth(); x++) {
            for (int y = 0; y < input.getHeight(); y++) {
                output.setRGB(x, y, uncroppedOutput.getRGB(x + radius, y + radius));
            }
        }
        // Now, add hue to each pixel based on the orientation of the edge at that pixel.
        // This is decided with the hue option.
        if (hue){
            output = colour(thetas, output);
        }
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
     * This support method is used to decide the hue of each pixel. The brightness of each pixel
     * will have already been determined before this method has been called. Note, the decision has 
     * been made that if theta is from a polar coordinate in the cartesian plane, the positive
     * x axis is red, negative x axis is green, positive y axis is yellow and negative
     * y axis is blue. Alpha channel is not effected at all.
     * @param input The image have hue added. This is assumed to be in grey scale.
     * @oaram theta The 2-d array of theta values for each pixel, which correspond to hue.
     * @return The image now with hue decided by theta at each pixel.
     */
    private static BufferedImage colour(double[][] theta, BufferedImage input) {
        int width = input.getWidth();
        int height = input.getHeight();
        // Create a new BufferedImage to store the offet image. 
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        // Loop through each pixel to assign the right hue with the brightness.
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Get the angle of edge at this pixel. From 0 to 2pi.
                double angle = theta[x][y];
                // Get pixel int value.
                int val = input.getRGB(x, y);
                // Get the RGB channels. Only really care about this for magnitude.
                int r = (val >> 16) & 0xff;
                int g = (val >> 8) & 0xff;
                int b = val & 0xff;
                // Define new values. Will convert to int later. 
                // Due to cos and sin, will always be between 0 and 1.
                double newR = 0;
                double newG = 0;
                double newB = 0;
                // Assign hue values. These will be relatively scaled later.
                if (Math.cos(angle) > 0) {
                    newR = Math.cos(angle); // Red value, green will be 0.
                }
                else if (Math.cos(angle) < 0) {
                    newG = -Math.cos(angle); // Green value, red will be 0.
                }
                if (Math.sin(angle) > 0) {
                    newR += Math.sin(angle); // Yellow (red and green) value, blue will be 0.
                    newG += Math.sin(angle); // Yellow (red and green) value, blue will be 0.
                }
                else if (Math.sin(angle) < 0) {
                    newB = -Math.sin(angle); // Blue value, yellow will be 0.
                }
                // Actually make the int pixel values. Just scaling the values really.
                // Also scaling them to keep in mind human perception of colour
                int pixelR = (int)Math.round(((double)r)*newR);
                int pixelG = (int)Math.round(((double)g)*newG);
                int pixelB = (int)Math.round(((double)b)*newB);
                // Put the coloured pixel values in output. Note, we keep fully opacity.
                int pixel = 0xff000000 | (pixelR << 16) | (pixelG << 8) | pixelB;
                output.setRGB(x, y, pixel);
            }
        }
        return output;
    }

}


