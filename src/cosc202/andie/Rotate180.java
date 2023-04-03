package cosc202.andie;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * ImageOperation that rotates an image 180 degrees.
 * <p>
 * In other words, applying this operation to an image will return the same image upside-down.
 * 
 * @author Mathias Øgaard
 */
public class Rotate180 implements ImageOperation, Serializable{

    @Override
    public BufferedImage apply(BufferedImage input) {
        //When rotating 180 degrees, the width and height of the image stays the same
        int width = input.getWidth();
        int height = input.getHeight();

        BufferedImage rotated = new BufferedImage(width, height, input.getType());

        Graphics2D g = rotated.createGraphics();        // The "canvas" the new image is drawn on
        AffineTransform at = g.getTransform();

        // Move canvas down and to the right, so the top left corner is rightly placed:
        at.translate(width, height);        

        // Rotate canvas, so all corners of image is rightly placed:        
        at.quadrantRotate(2);
        
        g.setTransform(at); // Executes the transformation
        g.drawImage(input,0,0,null); // Draws the image on the transformed canvas.
        g.dispose();

        return rotated;
    }
    
}
