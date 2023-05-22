package cosc202.andie;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Rectangle;

/**
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 */
// The class represents a rectangle-drawing operation on an image.
// It implements the ImageOperation interface to define the apply() method.
// The class is also Serializable, indicating that its instances can be serialized.
public class DrawRec implements ImageOperation, Serializable {

    // Private instance variables for storing the rectangle, color, fill status, and stroke.
    private Rectangle r;
    private Color col;
    private boolean fill;
    private int strokeWidth;

    // Constructor for the DrawRec class.
    // It takes a scale value, a rectangle object, and a boolean value indicating whether the rectangle should be filled or not.
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
    // Implementation of the apply() method from the ImageOperation interface.
    // It takes a BufferedImage as input and returns the modified image.
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
