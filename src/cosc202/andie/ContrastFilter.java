package cosc202.andie;

import java.awt.image.*;
import javax.swing.*;
import java.awt.HeadlessException;

/**
 * <p>
 * ImagineOperation to apply a contrast filter.
 * </p>
 * 
 * <p>
 * ContrastFilter that implements the ImageOperation interface,
 * The class has a constructor that takes an integer parameter scale,
 * which is used to determine the contrast of the image.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 *
 */
public class ContrastFilter implements ImageOperation, java.io.Serializable {

    /** The size of the scale which is what is used to determine the contrast. */
    int value;

    /**
     * <p>
     * Construct the contrast filter with the given scale.
     * </p>
     * 
     * @param value the value that is used to detetmind the scale of contrast.
     * 
     */
    public ContrastFilter(int value) {
        this.value = value;
    }

    /**
     * <p>
     * Apply a contrast filter to an image.
     * </p>
     * 
     * <p>
     * The ContrastFilter class is used to apply a contrast filter to a
     * BufferedImage. The amount of contrast applied is determined by the 
     * value field, which is set by the constructor. The apply method applies the contrast 
     * filter to the input image and returns the filtered image.
     * </p>
     * 
     * @param previousImage the input image to filter
     * @return a new image with the contrast filter applied
     * 
     */
    @Override
    public BufferedImage apply(BufferedImage previousImage) {
        try {
            float contrast = 1.0f + ((float) value) / 100.0f;
            RescaleOp rescale = new RescaleOp(contrast, (-12.75f * contrast), null);
            rescale.filter(previousImage, previousImage);
        } catch (IllegalArgumentException e) {
            // This will not happen by the way we have set it up.
            // But, occurs if there is an issue with the scaling factors or colour model.
            // Tell the user and do nothing.
            try {
                JOptionPane.showMessageDialog(null, "Sorry, there has been an error in changing the contrast.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (HeadlessException eh) {
                // Headless exception, thrown when the code is dependent on a keyboard or mouse.

                // Won't happen for our users, so just exit.
                System.exit(1);
            }
        }
        return previousImage;
    }

}
