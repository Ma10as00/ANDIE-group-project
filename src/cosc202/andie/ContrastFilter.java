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
 * 
 *
 * 
 */
public class ContrastFilter implements ImageOperation, java.io.Serializable {
    // The size of the scale which is what is used to determine the contrast
    int value;

    // Construct the contrast filter with the given scale
    public ContrastFilter(int value) {
        this.value = value;
    }

    /**
     * <p>
     * Apply a contrast filter to an image,
     * </p>
     * 
     * The apply method takes in a value field that compute a contrast value.
     * The contrast value is then used to create a RescaleOp object.
     * The RescaleOp object is then used to filter the previousImage using the
     * filter method.
     * The filter method modifies the previousImage in place by applying the
     * contrast filter to it. Finally,
     * the apply method returns the previousImage.
     * 
     * 
     * The ContrastFilter class is used to apply a contrast filter to a
     * BufferedImage.
     * The amount of contrast applied is determined by the value field, which is set
     * by the constructor.
     * The apply method applies the contrast filter to the input image and returns
     * the filtered image.
     * 
     * @param previousImage the input image to filter
     * @return a new image with the contrast filter applied
     */
    @Override
    public BufferedImage apply(BufferedImage previousImage) {
        try {
            float contrast = 1.0f + value / 10f;
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

    /**
     * 
     * @return value
     * @desc test
     */
    public int getConValue() {
        return value;
    }
}