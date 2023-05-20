package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.Rectangle;

public class DrawRec implements ImageOperation, java.io.Serializable  {

    private Rectangle r;
    private Color col;

    DrawRec(Rectangle rect, Color userColor) {
        r = rect;
        col = userColor;
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
        Graphics2D g2d = (Graphics2D) input.getGraphics();
        g2d.setColor(col);
        g2d.fill(r);
        g2d.dispose();
        return input;
    }

}
