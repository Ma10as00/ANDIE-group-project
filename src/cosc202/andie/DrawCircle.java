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
 * This class draws a circle (or possibly an ellipse) on the image. 
 * It may either draw a filled circle or draw an outlined circle.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Katie Wink (Modified by Stella Srzich)
 */
public class DrawCircle implements ImageOperation, Serializable {

    /** The colour of this circle. */
    private Color col;

    /** The x coordinate of the upper left corner of the bounds of this circle. */
    private int x;

    /** The y coordinate of the upper left corner of the bounds of this circle. */
    private int y;

    /** The width of the bounds of this circle. */
    private int width;

    /** The height of the bounds of this circle. */
    private int height;

    /** Whether or not this circled is filled. */
    private boolean fill;

    /** If this circle is filled, this is the stroke width of the outlined circle. */
    private int strokeWidth;

    /**
     * <p>
     * Construct a draw circle.
     * </p>
     * 
     * <p>
     * This will draw a filled or outlined circle (or possibly an ellipse) on 
     * the image this {@link ImageOperation} is applied to. This may be in any
     * position on the image and may have any chosen colour, or width (if it is outlined).
     * </p>
     * 
     * @param scale The scale of the {@link ImagePanel} the {@link EditableImage} is in.
     * @param x The x coordinate of the upper left corner of the bounds of the circle.
     * @param y The y coordinate of the upper left corner of the bounds of the circle.
     * @param height The height of the bounds of the circle.
     * @param width The width of the bounds of the circle.
     * @param fill True to draw a filled circle, false to draw an outlined circle.
     */
    public DrawCircle(double scale, int x, int y, int height, int width, boolean fill) {
        // Scaling the center coordinates, width, and height based on the provided scale value.
        // The x, y, width, and height values of the circle are divided by the scale to obtain scaled values.
        // Setting the fill status based on the provided boolean value.
        this.x = (int) ((double) x / scale);
        this.y = (int) ((double) y / scale);
        this.height = (int) ((double) height / scale);
        this.width = (int) ((double) width / scale);
        this.fill = fill;

        // The stroke width is calculated by dividing DrawActions.userWidth by the scale.
        // Creating a BasicStroke object for the stroke of the circle.
        this.strokeWidth = (int) (DrawActions.userWidth / scale);
        // Creating a Color object based on the RGB values obtained from DrawActions.userColour.
        col = new Color(DrawActions.userColour.getRed(), DrawActions.userColour.getGreen(),
                DrawActions.userColour.getBlue());
    }

    /**
     * <p>
     * Apply a draw circle on an image.
     * </p>
     * 
     * <p>
     * This will draw a filled or outlined circle (or possibly an ellipse) on 
     * the image this {@link ImageOperation} is applied to. This will be in a given 
     * position with a given width and height, and with a chosen colour and stroke width.
     * </p>
     * 
     * @param input The image to apply the draw circle to.
     * @return The resulting image with a circle drawn on it.
     */
    @Override
    public BufferedImage apply(BufferedImage input) {
        // Creating a Graphics2D object based on the input BufferedImage.
        Graphics2D g = input.createGraphics();

        // If the circle is not supposed to be filled:
        if (!fill) {
            // Set the color on the Graphics2D object.
            // Fill the circle with the specified color.
            g.setColor(col);
            g.fillOval(x, y, width, height);
        }
        // If the circle is supposed to be filled:
        if (fill) {
            // Set the color on the Graphics2D object.
            // Draw the outlined circle with the specified color.
            g.setColor(this.col);
            BasicStroke stroke = new BasicStroke(strokeWidth);
            g.setStroke(stroke);
            g.drawOval(x, y, width, height);
        }
        // Releasing system resources associated with the Graphics2D object.
        g.dispose();
        // Returning the modified BufferedImage.
        return input;
    }

}
