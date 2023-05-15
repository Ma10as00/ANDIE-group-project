package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.Point;

public class DrawCircle implements ImageOperation {

    private Point start;
    private Point end;
    private Color col;
    private int width;
    private boolean fill;

    DrawCircle(Point start, Point end, Color c, int width, boolean fill) {
        this.start = start;
        this.end = end;
        this.col = col;
        this.width = width;
        this.fill = fill;
    }

    private RenderingHints getHints() {
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return hints;
    }

    @Override
    public BufferedImage apply(BufferedImage input) {
        Graphics2D g = input.createGraphics();
        g.setRenderingHints(getHints());
        g.setStroke(new BasicStroke(width));
        g.setColor(col);
        if (fill) {
            g.fillOval((int) Math.min(start.x, start.y),
                    (int) Math.min(start.y, end.y),
                    (int) Math.abs((end.x - 20) - (start.x - 20)),
                    (int) Math.abs((end.y - 20) - (start.y - 20)));
        } else {
            g.drawOval((int) Math.min(start.x, end.x),
                    (int) Math.min(start.y, end.y),
                    (int) Math.abs((end.x - 20) - (start.x - 20)),
                    (int) Math.abs((end.y - 20) - (start.y - 20)));
        }
        g.dispose();
        return input;
    }

}
