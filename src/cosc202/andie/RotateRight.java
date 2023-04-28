package cosc202.andie;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * <p>
 * ImageOperation that rotates an image 90 degrees to the right.
 * </p>
 * 
 * <p>
 * If this operation is applyed to an image with dimensions {@code (width, height)}, the resulting image will have dimensions {@code (height, width)}.
 * </p>
 * 
 * @author Mathias Øgaard
 */
public class RotateRight implements ImageOperation, Serializable {

    /**
     * <p>
     * Apply RotateRight to a image - Rotates the image by 90 degrees to the right.
     * </p>
     * 
     * @param input The image to Rotate to the right.
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

        // Move canvas to the right, so the top left corner is rightly placed:
        at.translate(width, 0);        

        // Rotate canvas, so all corners of image is rightly placed:        
        at.quadrantRotate(1);
        
        g.setTransform(at); // Executes the transformation
        g.drawImage(input,0,0,null); // Draws the image on the transformed canvas.
        g.dispose();

        return rotated;
    }
    
}
