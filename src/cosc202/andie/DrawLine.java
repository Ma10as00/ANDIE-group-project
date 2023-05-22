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
    int enterX;
    int enterY;
    int exitX;
    int exitY;
    BasicStroke stroke = new BasicStroke(DrawActions.userWidth);

    DrawLine(int enterX, int enterY, int exitX, int exitY) {
        this.enterX = enterX;
        this.enterY = enterY;
        this.exitX = exitX;
        this.exitY = exitY;
        col = DrawActions.userColour;
    }

    @Override
    public BufferedImage apply(BufferedImage input) {
        Graphics2D g = (Graphics2D) input.getGraphics();
        g.setStroke(stroke);
        g.setColor(col);
        g.drawLine(enterX, enterY, exitX, exitY);
        g.dispose();
        return input;
    }
}
