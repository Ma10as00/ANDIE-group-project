package cosc202.andie;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * <p>
 * Image operation to resize the image to n% of its size.
 * </p>
 * 
 * <p>
 * An image resize by n% resizes the image, changing the dimensions to n% of its
 * original size. This also changes the values of the pixels, possibly losing
 * information.
 * </p>
 *
 * @author Stella Srzich (Modified from Michael Campbell)
 */
public class ImageResizeN implements ImageOperation, Serializable {

    /**
     * The percentage or resize to apply. A resize percent of 100% is no change,
     * 50% is smaller, 150% is bigger and so on. This is in the range [50, 200].
     */
    private int resizePercent;

    /**
     * <p>
     * Construct a ImageResizeN with a given resizePercent.
     * </p>
     * 
<<<<<<< HEAD
     * </p>
     * Note, resizePercent is restricted to the closed interval [50, 200]. If
     * anything outside
=======
     * <p>
     * Note, resizePercent is restricted to the closed interval [50, 200]. If anything outside
>>>>>>> 54f0e85ccd2cba52b667ea225bf35f8256410ee8
     * of this range is passed, 50% or 200% will be used as resizePercent.
     * </p>
     * 
     * @param resizePercent the n% we would like to resize the image by.
     */
<<<<<<< HEAD
    ImageResizeN(int resizePercent) {
        this.resizePercent = resizePercent;
=======
    public ImageResizeN(int resizePercent) {
        this.resizePercent = resizePercent;    
>>>>>>> 54f0e85ccd2cba52b667ea225bf35f8256410ee8
    }

    /**
     * <p>
     * Construct a ImageResizeN with a given resizePercent.
     * </p>
     * 
     * <p>
     * By default, a ImageResizeN has a resziePercent of 100 (no change to image).
     * </p>
     * 
     * @see {@link ImageResizeN(int resizePercent)}
     */
    ImageResizeN() {
        this(100);
    }

    /**
     * <p>
     * Apply an image resize n to an image.
     * </p>
     * 
     * <p>
     * An image resize by n% resizes the image, changing the dimensions to n% of its
     * original size. This also changes the values of the pixels, possibly losing
     * information.
     * </p>
     * 
     * @param input a BufferedImage object to apply the image resize n to.
     * @return the input image modified to n% of its original size.
     */
    @Override
    public BufferedImage apply(BufferedImage input) {
        // Make sure resizePercent is in range [50, 200].
        if (resizePercent < 50) {
            resizePercent = 50;
        }
        if (resizePercent > 200) {
            resizePercent = 200;
        }

        // Just so that resizedImage is in scope of return. And, if resizePercent == 100
        // nothing will happen.
        BufferedImage resizedImage = input;

        if (resizePercent > 100) {
            double scale = 100.0 / (resizePercent - 100.0);

            int newWidth = input.getWidth() + ((int) (((double) input.getWidth()) / scale));
            int newHeight = input.getHeight() + ((int) (((double) input.getHeight()) / scale));

            Image resize = input.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

            resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
            resizedImage.getGraphics().drawImage(resize, 0, 0, null);
        }
        if (resizePercent < 100) {
            double scale = 100.0 / resizePercent;

            int newWidth = ((int) (((double) input.getWidth()) / scale));
            int newHeight = ((int) (((double) input.getHeight()) / scale));

            Image resize = input.getScaledInstance(newWidth, newHeight, Image.SCALE_AREA_AVERAGING);

            resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
            resizedImage.getGraphics().drawImage(resize, 0, 0, null);
        }

        return resizedImage;
    }

}
