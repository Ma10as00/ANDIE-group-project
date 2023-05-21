package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Rectangle;

public class DrawRec implements ImageOperation, java.io.Serializable {

    private Rectangle r;
    private Color col;
    private boolean fill;

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
            BasicStroke stroke = new BasicStroke((float) DrawActions.userWidth);
            g2d.setStroke(stroke);
            g2d.draw(r);
        }
        g2d.dispose();
        return input;
    }

}
