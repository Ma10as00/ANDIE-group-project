package cosc202.andie;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;

/**
 * An image operation that draws pixels to the screen in an array with a
 * selected
 */
public class DrawLine implements ImageOperation, Serializable {

    private Color col;
    private int enterX;
    private int enterY;
    private int exitX;
    private int exitY;
    private BasicStroke stroke = new BasicStroke(DrawActions.userWidth);

    DrawLine(int enterX, int enterY, int exitX, int exitY) {
        this.enterX = enterX;
        this.enterY = enterY;
        this.exitX = exitX;
        this.exitY = exitY;
        col = new Color(DrawActions.userColour.getRed(), DrawActions.userColour.getGreen(), DrawActions.userColour.getBlue());
    }

    @Override
    public BufferedImage apply(BufferedImage input) {
        Graphics2D g = input.createGraphics();
        g.setStroke(stroke);
        g.setColor(col);
        g.drawLine(enterX, enterY, exitX, exitY);
        g.dispose();
        return input;
    }
}
