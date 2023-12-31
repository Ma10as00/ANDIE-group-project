package cosc202.andie;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * <p>
 * Image operation to resize the image to 50% of its size.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 */
public class ImageResize50 implements ImageOperation, Serializable {

    /**
     * <p>
     * Apply an image resize 50 operation to resize the image by 50%.
     * </p>
     * 
     * @param input a BufferedImage object to apply the image resize to.
     * @return resizedImage the image having been reduced to 50% of it's size.
     */
    @Override
    public BufferedImage apply(BufferedImage input) {

        int newWidth = (input.getWidth() / 2);
        int newHeight = (input.getHeight() / 2);

        Image resize = input.getScaledInstance(newWidth, newHeight, Image.SCALE_AREA_AVERAGING);

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        resizedImage.getGraphics().drawImage(resize, 0, 0, null);

        return resizedImage;
    }
}