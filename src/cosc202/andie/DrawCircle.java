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
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * 
 * <p>
 * This class draws a circle on the image.
 * It will either fill draw a circle or draw an outline drpending on the boolean
 * "fill"
 * 
 * </p>
 * 
 * @author Katie Wink
 */

// The class represents a circle-drawing operation on an image.
// It implements the ImageOperation interface to define the apply() method.
// The class is also Serializable, indicating that its instances can be serialized.
public class DrawCircle implements ImageOperation, Serializable {

    // Private instance variables for storing the color, center coordinates, width, height, fill status, and stroke.
    private Color col;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean fill;
    private BasicStroke stroke;

    // Constructor for the DrawCircle class.
    // It takes a scale value, center coordinates, width, height, and a boolean value indicating whether the circle should be filled or not.
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
        this.stroke = new BasicStroke((int) (DrawActions.userWidth / scale));
        // Creating a Color object based on the RGB values obtained from DrawActions.userColour.
        col = new Color(DrawActions.userColour.getRed(), DrawActions.userColour.getGreen(),
                DrawActions.userColour.getBlue());
    }

    @Override
    // Implementation of the apply() method from the ImageOperation interface.
    // It takes a BufferedImage as input and returns the modified image.
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
            // Fill the circle with the specified color.
            g.setColor(this.col);
            g.setStroke(stroke);
            g.drawOval(x, y, width, height);
        }
        // Releasing system resources associated with the Graphics2D object.
        g.dispose();
        // Returning the modified BufferedImage.
        return input;
    }

}
