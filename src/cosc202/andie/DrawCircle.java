package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;

public class DrawCircle implements ImageOperation, java.io.Serializable {

    private Color col;

    DrawCircle() {
        col = DrawActions.userColour;
    }

    @Override
    public BufferedImage apply(BufferedImage input) {
        Graphics2D g = input.createGraphics();
        g.setColor(col);
        g.fillOval(Math.min(ImagePanel.enterX, ImagePanel.enterY),
                Math.min(ImagePanel.enterY, ImagePanel.exitY),
                Math.abs((ImagePanel.exitX - 20) - (ImagePanel.enterX - 20)),
                Math.abs((ImagePanel.exitY - 20) - (ImagePanel.enterY - 20)));

        g.dispose();
        return input;
    }

}
