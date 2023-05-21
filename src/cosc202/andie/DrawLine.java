package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
<<<<<<< HEAD
import java.awt.RenderingHints;
import java.awt.Point;
import java.awt.LinearGradientPaint;
=======
>>>>>>> 73e0992777e09832da46f0910a136126c8e96aa0

/**
 * An image operation that draws pixels to the screen in an array with a
 * selected
 */
public class DrawLine implements ImageOperation, java.io.Serializable {

    private Color col;
    int enterX;
    int enterY;
    int exitX;
    int exitY;

<<<<<<< HEAD
    DrawLine(int enterX, int enterY, int exitX, int exitY) {
        this.enterX = enterX;
        this.enterY = enterY;
        this.exitX = exitX;
        this.enterY = enterY;
        col = DrawActions.userColour;
=======
    /**
     * Image operation that draws a line over the image
     * 
     * 
     */
    DrawLine() {
>>>>>>> 73e0992777e09832da46f0910a136126c8e96aa0

    }

    @Override
    public BufferedImage apply(BufferedImage input) {
<<<<<<< HEAD
        Graphics2D g = (Graphics2D) input.getGraphics();
        g.setColor(col);
        g.drawLine(enterX, enterY, exitX, enterY);
=======

        // Return the image
>>>>>>> 73e0992777e09832da46f0910a136126c8e96aa0
        return input;
    }

}
