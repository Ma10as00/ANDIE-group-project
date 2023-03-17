package cosc202.andie;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Rotate90Right implements ImageOperation, Serializable {

    @Override
    public BufferedImage apply(BufferedImage input) {

        //When rotating 90 degrees, the width and height of the image swap places
        int width = input.getHeight();
        int height = input.getWidth();

        BufferedImage rotated = new BufferedImage(width, height, input.getType());

        Graphics2D g = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate(width, 0);
        at.quadrantRotate(1);
        g.setTransform(at);
        g.drawImage(input,0,0,null);
        g.dispose();

        return rotated;
    }
    
}
