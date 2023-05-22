package cosc202.andie;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
<<<<<<< HEAD
import java.awt.RenderingHints;
import java.awt.Point;
import java.awt.LinearGradientPaint;

public class DrawLine implements ImageOperation, java.io.Serializable {

    private Color col;
    int enterX;
    int enterY;
    int exitX;
    int exitY;

    DrawLine(int enterX, int enterY, int exitX, int exitY) {
        this.enterX = enterX;
        this.enterY = enterY;
        this.exitX = exitX;
        this.enterY = enterY;
        col = DrawActions.userColour;
=======

/**
 * An image operation that draws pixels to the screen in an array with a
 * selected
 */
public class DrawLine implements ImageOperation, Serializable {

    private Color col;
    private int enterX;
    private int enterY;
    private int exitX;
    private int exitY;
    private BasicStroke stroke;
>>>>>>> 32732ca4a46dfa37e819e2b0400c184a8684f7d3

    public DrawLine(double scale, int enterX, int enterY, int exitX, int exitY) {
        this.enterX = (int)((double)enterX/scale);
        this.enterY = (int)((double)enterY/scale);
        this.exitX = (int)((double)exitX/scale);
        this.exitY = (int)((double)exitY/scale);
        this.stroke = new BasicStroke((int)(DrawActions.userWidth/scale));
        col = new Color(DrawActions.userColour.getRed(), DrawActions.userColour.getGreen(), DrawActions.userColour.getBlue());
    }

    @Override
    public BufferedImage apply(BufferedImage input) {
<<<<<<< HEAD
        Graphics2D g = (Graphics2D) input.getGraphics();
        g.setColor(col);
        g.drawLine(enterX, enterY, exitX, enterY);
=======
        Graphics2D g = input.createGraphics();
        g.setStroke(stroke);
        g.setColor(col);
        g.drawLine(enterX, enterY, exitX, exitY);
        g.dispose();
>>>>>>> 32732ca4a46dfa37e819e2b0400c184a8684f7d3
        return input;
    }
}
