package cosc202.andie;

import java.awt.image.*;
import javax.swing.*;
import java.awt.HeadlessException;

/**
 * <p>
 * ImagineOperation to apply a brightness filter.
 * </p>
 * 
 * <p>
 * BrightnessFilter that implements the ImageOperation interface,
 * The class has a constructor that takes an integer parameter scale,
 * which is used to determine the brightness of the image.
 * </p>
 * 
 * <p>
 * 
 *
 * 
 */

public class BrightnessFilter implements ImageOperation, java.io.Serializable  {
    // The size of the scale which is what is used to determine the brightness
    public int scale;

    // Construct the brightness filter with the given scale
    public BrightnessFilter(int scale) {
        this.scale = scale;
    }

    /**
     * <p>
     * Apply a brightness filter to an image,
     * </p>
     * 
     * <p>
     * The apply method takes a BufferedImage object as input,
     * and applies a brightness filter to it using the RescaleOp class.
     * The RescaleOp class is used to adjust the brightness and contrast of an
     * image.
     * The filter method of RescaleOp takes two arguments: the source image and the
     * destination image.
     * In this case, the source and destination images are the same,
     * since the method is modifying the input image directly.
     *
     * The brightness value used in the RescaleOp constructor is determined by the
     * scale parameter passed to the constructor.
     * If scale is positive, the brightness is increased by scale/10f,
     * and if scale is negative, the brightness is decreased by scale/10f.
     * If scale is 0, the brightness is unchanged.
     * 
     * 
     * </p>
     * 
     * @param previousImage the input image to filter
     * @return a new image with the brightness filter applied
     */
    public BufferedImage apply(BufferedImage previousImage) {
        try {
            float brightness = (scale > 0 ? 1.0f + (scale / 10f) : 1 - Math.abs(scale / 10f));
            RescaleOp rescale = new RescaleOp(brightness, 0, null);
            rescale.filter(previousImage, previousImage);
        } catch (IllegalArgumentException e) {
            // This will not happen by the way we have set it up.
            // But, occurs if there is an issue with the scaling factors or colour model.
            // Tell the user and do nothing.
            try {
                JOptionPane.showMessageDialog(null, "Sorry, there has been an error in changing the brightness.", "Error", JOptionPane.ERROR_MESSAGE);
            }   
            catch (HeadlessException eh) {
                // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                // Won't happen for our users, so just exit.
                System.exit(1);
            }
        }
        return previousImage;
    }
}