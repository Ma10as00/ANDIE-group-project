package cosc202.andie;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Rectangle;

/**
 * <p>
 * ImageOperation to draw a rectangle on the image.
 * </p> 
 * 
 * <p>
 * This class draws a rectangle on the image. 
 * It may either draw a filled rectangle or draw an outlined rectangle.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Katie Wink (Modified by Stella Srzich)
 */
public class DrawRec implements ImageOperation, Serializable {

    /** The rectangle bounding this drawn rectangle. */
    private Rectangle r;

    /** The colour of the drawn rectangle. */
    private Color col;

    /** Whether or not this rectangle is filled. */
    private boolean fill;

    /** If this rectangle is not filled, this is the width of the stroke of the outline. */
    private int strokeWidth;

    /**
     * <p>
     * Construct a draw rec.
     * </p>
     * 
     * <p>
     * This will draw a filled or outlined rectangle on 
     * the image this {@link ImageOperation} is applied to. This may be in any
     * position on the image and may have any chosen colour, or width (if it is outlined).
     * </p>
     * 
     * @param scale The scale of the {@link ImagePanel} the {@link EditableImage} is in.
     * @param rect The rectangle bounding this drawn rectangle.
     * @param fill True to draw a filled rectangle, false to draw an outlined rectangle.
     */
    public DrawRec(double scale, Rectangle rect, Boolean fill) {
        // Scaling the rectangle coordinates and dimensions based on the provided scale value.
        // The x, y, width, and height values of the rectangle are divided by the scale to obtain scaled values.
        int x = (int) ((double) rect.x / scale);
        int y = (int) ((double) rect.y / scale);
        int width = (int) ((double) rect.width / scale);
        int height = (int) ((double) rect.height / scale);
        this.strokeWidth = (int) (DrawActions.userWidth / scale);
        // Creating a Rectangle object with the scaled coordinates and dimensions.
        r = new Rectangle(x, y, width, height);
        // Creating a Color object based on the RGB values obtained from DrawActions.userColour.
        col = new Color(DrawActions.userColour.getRed(), DrawActions.userColour.getGreen(),
                DrawActions.userColour.getBlue());
        // Setting the fill status based on the provided boolean value.
        this.fill = fill;
    }


    /**
     * <p>
     * Apply a draw rec on an image.
     * </p>
     * 
     * <p>
     * This will draw a filled or outlined rectangle on 
     * the image this {@link ImageOperation} is applied to. This will be in a given 
     * bounded rectangle on the image, and with a chosen colour and stroke width.
     * </p>
     * 
     * @param input The image to apply the draw circle to.
     * @return The resulting image with a circle drawn on it.
     */
    @Override
    public BufferedImage apply(BufferedImage input) {
        // Creating a Graphics2D object based on the input BufferedImage.
        Graphics2D g2d = (Graphics2D) input.getGraphics();

        // If the rectangle is not supposed to be filled:
        if (!fill) {
            // Set the color on the Graphics2D object.
            g2d.setColor(this.col);
            // Fill the rectangle with the specified color.
            g2d.fill(r);
        }
        // If the rectangle is supposed to be filled:
        if (fill) {
            // Set the color and stroke on the Graphics2D object.
            g2d.setColor(this.col);
            BasicStroke stroke = new BasicStroke(strokeWidth);
            g2d.setStroke(stroke);
            // Draw the outline of the rectangle using the specified color and stroke.
            g2d.draw(r);
        }
        // Releasing system resources associated with the Graphics2D object.
        g2d.dispose();
        // Returning the modified BufferedImage.
        return input;
    }

}
