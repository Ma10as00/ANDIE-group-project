package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a sharpen filter.
 * </p>
 * 
 * <p>
 * A sharpen filter sharpens an image by replacing each pixel by the average of the
 * pixels in a surrounding neighbourhood, and can be implemented by a convoloution.
 * This specific sharpen filter can be done to carying degrees, by applying
 * multiple generic sharpen filters on top of each other.
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
public class SharpenFilter implements ImageOperation, java.io.Serializable {

    /**
     * The amount we want to sharpen by. An amount of 1 just applies a generic sharpen filter.
     * An amount greater than 1 results in a sharper image. Note, this does not work by 
     * increasing the radius of the kernel, it works by changing the values in a 3x3 kernel, 
     * scaling the edge detector contribution.
     */
    private int amount;

    /**
     * <p>
     * Construct a sharpen filter with the given amount.
     * </p>
     * 
     * <p>
     * The amount of the sharpen filter is the strength of
     * the sharpen filter applied to the image. That is, 
     * an amount of 1 applies a generic sharpen filter, and a higher amount
     * applies a stronger sharpen filter which results in a 'sharper' image.
     * </p>
     * 
     * @param amount The amount of the newly constructed SharpenFilter
     */
    public SharpenFilter(int amount) {
        this.amount = amount;
    }

    /**
     * <p>
     * Construct a generic sharpen filter.
     * </p>
     * 
     * <p>
     * By default, a generic sharpen filter has an amount of 1. 
     * That is, a generic sharpen filter is applied.
     * </p>
     * 
     * @see SharpenFilter(int)
     */
    public SharpenFilter() {
        this(1);
    }

    /**
     * <p>
     * Apply a sharpen filter to an image.
     * </p>
     * 
     * <p>
     * As with many filters, the sharpen filter is implemented via convolution.
     * With an amount of 1, the generic sharpen filter is applied. With 
     * a higher amount, a stronger sharpen filter is applied. Note, this does
     * not work by increasing the radius of the kernel. Rather, it changes the values
     * of the numbers in the 3x3 kernel.
     * </p>
     * 
     * @param input The image to apply the sharpen filter to.
     * @return The resulting (sharpened) image, sharpened to the specified amount.
     */
    public BufferedImage apply(BufferedImage input) {
        // The values for the sharpening kernel as a 9-element array with the specified amount.
        // Note, this is using [0 0 0; 0 1 0; 0 0 0] + [0 -1 0; -1 4 -1; 0 -1 0]*amount/2.
        float factor = ((float)amount)/2.0f;
        float [] array = { 0 , -1*factor, 0 ,
                        -1*factor, 1 + 4*factor, -1*factor,
                        0   , -1*factor,   0   };
        
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
}

