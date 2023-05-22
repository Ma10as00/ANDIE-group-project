package cosc202.andie;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;

/**
 * <p>
 * ImageOperation to draw a circle on the image.
 * </p>
 * 
 * <p>
 * This class draws a circle on the image.
 * It will either fill draw a circle or draw an outline drpending on the boolean
 * "fill"
 * 
 * </p>
 * 
 * @author Katie Wink
 */
public class DrawCircle implements ImageOperation, Serializable {

    private Color col;
    int x;
    int y;
    int width;
    int height;
    boolean fill;
    BasicStroke stoke = new BasicStroke(DrawActions.userWidth);

    DrawCircle(int x, int y, int height, int width, boolean fill) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.fill = fill;
        col = DrawActions.userColour;
    }

    @Override
    public BufferedImage apply(BufferedImage input) {
        Graphics2D g = input.createGraphics();
        if (!fill) {
            g.setColor(col);
            g.fillOval(x, y, width, height);
        }

        if (fill) {
            g.setColor(DrawActions.userColour);
            g.setStroke(stoke);
            g.drawOval(x, y, width, height);
        }
        g.dispose();
        return input;
    }

}
