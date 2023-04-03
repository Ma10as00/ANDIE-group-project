package cosc202.andie;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * <p>
 * Image operation to resize the image to 150% of it's size
 * </p>
 * 
 * @param input a BufferedImage object to apply the image resize to
 * @return resizedImage the image having been increased to 150% of it's size
 */
public class ImageResize150 implements ImageOperation, Serializable{

    @Override
    public BufferedImage apply(BufferedImage input) {

        int newWidth = input.getWidth() + (input.getWidth()/2);
        int newHeight = input.getHeight() + (input.getHeight()/2);

        Image resize = input.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        resizedImage.getGraphics().drawImage(resize,0,0,null);
        
        return resizedImage;
        
    }
}
