package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;

public class DrawCircle implements ImageOperation, java.io.Serializable {

    private Color col;
    int x;
    int y;
    int width;
    int height;

    DrawCircle(int x, int y, int height, int width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        col = DrawActions.userColour;
    }

    @Override
    public BufferedImage apply(BufferedImage input) {
        Graphics2D g = input.createGraphics();
        g.setColor(col);
        g.fillOval(x, y, width, height);
        g.dispose();
        return input;
    }

}
