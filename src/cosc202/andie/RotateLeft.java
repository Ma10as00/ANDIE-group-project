package cosc202.andie;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * @author Mathias Øgaard
 */
public class RotateLeft implements ImageOperation, Serializable{

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
