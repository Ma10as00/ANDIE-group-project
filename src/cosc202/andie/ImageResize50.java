package cosc202.andie;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * <p>
 * Image operation to resize the image to 50% of it's size
 * </p>
 * 
 * 
 */
public class ImageResize50 implements ImageOperation, Serializable{

    /**
    * <p>
    * Image operation to resize the image
    * </p>
    * 
    * @param input a BufferedImage object to apply the image resize to
    * @return resizedImage the image having been reduced to 50% of it's size
    */
    @Override
    public BufferedImage apply(BufferedImage input) {

        int newWidth = (input.getWidth()/2);
        int newHeight = (input.getHeight()/2);

        Image resize = input.getScaledInstance(newWidth, newHeight, Image.SCALE_AREA_AVERAGING);

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        resizedImage.getGraphics().drawImage(resize,0,0,null);
        
        return resizedImage;
    }
}