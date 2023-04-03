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
     * <p>
     * Construct a sharpen filter.
     * </p>
     */
    SharpenFilter() {

    }

    /**
     * <p>
     * Apply a sharpen filter to an image.
     * </p>
     * 
     * <p>
     * As with many filters, the sharpen filter is implemented via convolution.
     * This is generic as of now (there is no variable radius).
     * </p>
     * 
     * @param input The image to apply the sharpen filter to.
     * @return The resulting (sharpened) image.
     */
    public BufferedImage apply(BufferedImage input) {
        // The values for the sharpening kernel as a 9-element array.
        float [] array = { 0 , -1/2.0f, 0 ,
                        -1/2.0f, 3.0f, -1/2.0f,
                        0   , -1/2.0f,   0   };

        Kernel kernel = new Kernel(3, 3, array);
        ConvolveOp convOp = new ConvolveOp(kernel);
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        convOp.filter(input, output);

        return output;
    }


}

