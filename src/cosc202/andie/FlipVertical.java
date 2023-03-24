package cosc202.andie;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.Serializable;


public class FlipVertical implements ImageOperation, Serializable{

    @Override
    public BufferedImage apply(BufferedImage input) {

        int width = input.getWidth();
        int height = input.getHeight();

        BufferedImage flipped = new BufferedImage(width, height, input.getType());

        Graphics2D g = flipped.createGraphics();    // The "canvas" the new image is drawn on
        AffineTransform at = g.getTransform();

        // Flipping canvas around y-axis:
        at.scale(1, -1);
        
        // Moving the canvas back to it's original coordinates:
        at.translate(0, -height);  

        g.setTransform(at); // Executes the transformation
        g.drawImage(input,0,0,null); // Draws the image on the transformed canvas.
        g.dispose();
        
        return flipped;
    }
}