package cosc202.andie;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Rectangle;

public class DrawRec implements ImageOperation, Serializable {

    private Rectangle r;
    private Color col;
    private boolean fill;
    BasicStroke stroke = new BasicStroke(DrawActions.userWidth);

    DrawRec(Rectangle rect, Color userColor, Boolean fill) {
        r = rect;
        col = userColor;
        this.fill = fill;
    }

    @Override
    public BufferedImage apply(BufferedImage input) {
        Graphics2D g2d = (Graphics2D) input.getGraphics();
        if (!fill) {
            g2d.setColor(col);
            g2d.fill(r);
        }

        if (fill) {
            g2d.setColor(DrawActions.userColour);
            g2d.setStroke(stroke);
            g2d.draw(r);
        }
        g2d.dispose();
        return input;
    }

}
