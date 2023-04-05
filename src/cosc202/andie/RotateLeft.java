package cosc202.andie;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * ImageOperation that rotates an image 90 degrees to the left.
 * <p>
 * If this operation is applyed to an image with dimensions {@code (width, height)}, the resulting image will have dimensions {@code (height, width)}.
 * 
 * @author Mathias Øgaard
 */
public class RotateLeft implements ImageOperation, Serializable{

    /**
     * <p>
     * Apply RotateLeft to an image.
     * </p>
     * 
     * <p>
     * Apply RotateLeft to a image- rotates at 90degrees to the left
     * </p>
     * 
     * @param input The image to Rotate to the left.
     * @return The resulting (rotated) image.
     */
    @Override
    public BufferedImage apply(BufferedImage input) {
        //When rotating 90 degrees, the width and height of the image swap places
        int width = input.getHeight();
        int height = input.getWidth();

        BufferedImage rotated = new BufferedImage(width, height, input.getType());

        Graphics2D g = rotated.createGraphics();        // The "canvas" the new image is drawn on
        AffineTransform at = g.getTransform();

        // Move canvas down, so the top left corner is rightly placed:
        at.translate(0, height);        

        // Rotate canvas, so all corners of image is rightly placed:        
        at.quadrantRotate(3);
        
        g.setTransform(at); // Executes the transformation
        g.drawImage(input,0,0,null); // Draws the image on the transformed canvas.
        g.dispose();

        return rotated;
    }
    
}
