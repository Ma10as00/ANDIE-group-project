package cosc202.andie;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;

/** 
* <p>
* <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
* 4.0</a>
* </p>
* /

/**
 * An image operation that draws pixels to the screen in an array with a
 * selected
 */

// The class represents a line-drawing operation on an image.
// It implements the ImageOperation interface to define the apply() method.
// The class is also Serializable, indicating that its instances can be serialized.
public class DrawLine implements ImageOperation, Serializable {

    // Private instance variables for storing the color, starting and ending coordinates of the line, and stroke.
    private Color col;
    private int enterX;
    private int enterY;
    private int exitX;
    private int exitY;
    private int strokeWidth;

    // Constructor for the DrawLine class.
    // It takes a scale value and starting/ending coordinates of the line as parameters.
    public DrawLine(double scale, int enterX, int enterY, int exitX, int exitY) {

        // Scaling the coordinates based on the provided scale value.
        // The starting and ending coordinates are divided by the scale to obtain scaled values.
        // Creating a BasicStroke object for the stroke of the line.
        this.enterX = (int)((double)enterX/scale);
        this.enterY = (int)((double)enterY/scale);
        this.exitX = (int)((double)exitX/scale);
        this.exitY = (int)((double)exitY/scale);
        
        // The stroke width is calculated by dividing DrawActions.userWidth by the scale.
        this.strokeWidth = (int)(DrawActions.userWidth/scale);
        // Creating a Color object based on the RGB values obtained from DrawActions.userColour.
        col = new Color(DrawActions.userColour.getRed(), DrawActions.userColour.getGreen(), DrawActions.userColour.getBlue());
    }
    // Implementation of the apply() method from the ImageOperation interface.
    // It takes a BufferedImage as input and returns the modified image.
    @Override
    public BufferedImage apply(BufferedImage input) {
        // Creating a Graphics2D object based on the input BufferedImage.
        Graphics2D g = input.createGraphics();
        BasicStroke stroke = new BasicStroke(strokeWidth);
        g.setStroke(stroke);
        g.setColor(col);
        g.drawLine(enterX, enterY, exitX, exitY);
        // Drawing a line on the image using the drawLine() method of the Graphics2D object.
        // The line starts at (enterX, enterY) and ends at (exitX, exitY).
        g.dispose();
        // Releasing system resources associated with the Graphics2D object.
        return input;
        // Returning the modified BufferedImage.
    }
}
