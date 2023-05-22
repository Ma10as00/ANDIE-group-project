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
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
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
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean fill;
    private BasicStroke stroke;

    public DrawCircle(double scale, int x, int y, int height, int width, boolean fill) {
        this.x = (int) ((double) x / scale);
        this.y = (int) ((double) y / scale);
        this.height = (int) ((double) height / scale);
        this.width = (int) ((double) width / scale);
        this.fill = fill;
        this.stroke = new BasicStroke((int) (DrawActions.userWidth / scale));
        col = new Color(DrawActions.userColour.getRed(), DrawActions.userColour.getGreen(),
                DrawActions.userColour.getBlue());
    }

    @Override
    public BufferedImage apply(BufferedImage input) {
        Graphics2D g = input.createGraphics();
        if (!fill) {
            g.setColor(col);
            g.fillOval(x, y, width, height);
        }
        if (fill) {
            g.setColor(this.col);
            g.setStroke(stroke);
            g.drawOval(x, y, width, height);
        }
        g.dispose();
        return input;
    }

}
