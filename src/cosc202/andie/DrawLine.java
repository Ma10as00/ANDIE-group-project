package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;

/**
 * An image operation that draws pixels to the screen in an array with a
 * selected
 */
public class DrawLine implements ImageOperation, java.io.Serializable {

    private Color col;
    private int width;

    /**
     * Image operation that draws a line over the image
     * 
     * 
     */
    DrawLine() {

    }

    @Override
    public BufferedImage apply(BufferedImage input) {

        // Return the image
        return input;
    }

}
