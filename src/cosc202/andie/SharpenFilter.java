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
    SharpenFilter(int amount) {
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
    SharpenFilter() {
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
        ConvolveOp convOp = new ConvolveOp(kernel);
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        convOp.filter(input, output);

        return output;
    }
}

