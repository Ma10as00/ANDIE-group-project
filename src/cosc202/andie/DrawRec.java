package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.Point;
import java.awt.Rectangle;

public class DrawRec implements ImageOperation {

    private Rectangle r;
    private Color col;
    private int width;
    private boolean fill;

    DrawRec(Rectangle r, Color c, int width, boolean fill) {
        this.r = r;
        this.col = c;
        this.width = width;
        this.fill = fill;
    }

    // RenderingHints use a collections of keys and associate values to allow the
    // method to provide input to drawline
    private RenderingHints getHints() {
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return hints;
    }

    @Override
    public BufferedImage apply(BufferedImage input) {
        Graphics2D g = input.createGraphics();
        g.setStroke(new BasicStroke(width));
        g.setColor(col);
        if (fill) {
            g.fillRect(r.x, r.y, r.width, r.height);
        } else {
            g.drawRect(r.x, r.y, r.width, r.height);
        }
        //g.dispose();
        return input;
    }
}
