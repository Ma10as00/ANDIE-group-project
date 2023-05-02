package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.Point;

/**
 * An image operation that draws pixels to the screen in an array with a
 * selected
 */
public class DrawLine implements ImageOperation {

    private Point[] positions = null;
    private Color col;
    private int width;

    /**
     * Image operation that draws a line over the image
     * 
     * @param l     the array of Points we want to draw lines between
     * @param col   the color we want the line to be
     * @param width the brush size
     */
    DrawLine(Point[] l, Color col, int width) {
        positions = l;
        this.col = col;
        this.width = width;
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
        g.setRenderingHints(getHints());
        g.setStroke(new BasicStroke(width));
        g.setColor(col);
        // will not paint if no colour is selected
        try {
            // System.out.println("Doing this");
            for (int i = 1; i < positions.length; i++) {
                // get the Point
                Point current = positions[i];
                Point last = positions[i - 1];
                if (positions.length > 1) {
                    try {
                        // sets colour
                        g.drawLine(current.x, current.y, last.x, last.y);
                    } catch (Exception e) {
                        // Stops from painting past the screen
                    }
                } else {

                }

            }
        } catch (Exception e) {

        }
        // Return the image
        return input;
    }

}
