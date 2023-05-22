package cosc202.andie;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;

public class DrawCircle implements ImageOperation, Serializable {

    private Color col;
    int x;
    int y;
    int width;
    int height;
    boolean fill;
    BasicStroke stoke = new BasicStroke(width);

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
