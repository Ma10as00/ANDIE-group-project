package cosc202.andie;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;

/**
 * <p>
 * ImageOperation to draw a line on the image.
 * </p> 
 * 
 * <p>
 * This class draws a line on the image. This will be in the chosen colour, 
 * with the chosen width, at the chosen position.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Katie Wink (Modified by James Liu and Stella Srzich)
 */
public class DrawLine implements ImageOperation, Serializable {

    /** The colour of this line. */
    private Color col;

    /** The x coordinate of the starting point of this line. */
    private int enterX;

    /** The y coordinate of the starting point of this line. */
    private int enterY;

    /** The x coordinate of the ending point of this line. */
    private int exitX;

    /** The y coordinate of the ending point of this line. */
    private int exitY;

    /** The width of this line. */
    private int strokeWidth;

    /**
     * <p>
     * Construct a draw line.
     * </p>
     * 
     * <p>
     * This will draw a line on the image this {@link ImageOperation} is applied to. 
     * This may be in any position on the image and may have any chosen colour or width.
     * </p>
     * 
     * @param scale The scale of the {@link ImagePanel} the {@link EditableImage} is in.
     * @param enterX The x coordinate of the starting point of this line.
     * @param enterY The y coordinate of the starting point of this line.
     * @param exitX The x coordinate of the ending point of this line.
     * @param exitY The y coordinate of the ending point of this line.
     */
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


     /**
     * <p>
     * Apply a draw line on an image.
     * </p>
     * 
     * <p>
     * This will draw a line on the image this {@link ImageOperation} is applied to. 
     * This may be in any position on the image and may have any chosen colour or width
     * specified in the constructor.
     * </p>
     * 
     * @param input The image to apply the draw line.
     * @return The resulting image with a line drawn on it.
     */
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
        // Returning the modified BufferedImage.
        return input;
    }
    
}
