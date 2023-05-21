package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
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

    }

    @Override
    public BufferedImage apply(BufferedImage input) {
        Graphics2D g = (Graphics2D) input.getGraphics();
        g.setColor(col);
        g.drawLine(enterX, enterY, exitX, enterY);
        return input;
    }

}
