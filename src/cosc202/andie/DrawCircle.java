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

    DrawCircle(Point start, Point end) {
        this.start = start;
        this.end = end;
        col = DrawActions.userColour;
    }

    private RenderingHints getHints() {
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return hints;
    }

    @Override
    public BufferedImage apply(BufferedImage input) {
        Graphics2D g = input.createGraphics();
        g.setColor(col);
        g.fillOval(Math.min(ImagePanel.enterX,
                ImagePanel.enterY),
                Math.min(
                        ImagePanel.enterY,
                        ImagePanel.exitY),
                Math.abs((ImagePanel.exitX - 20) - (ImagePanel.enterX - 20)),
                Math.abs((ImagePanel.exitY - 20) - (ImagePanel.enterY - 20)));

        g.dispose();
        return input;
    }

}
