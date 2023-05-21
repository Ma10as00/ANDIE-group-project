package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;

public class DrawRec implements ImageOperation, java.io.Serializable {

    private Rectangle r;
    private Color col;

    DrawRec(Rectangle rect, Color userColor) {
        r = rect;
        col = userColor;
    }

    @Override
    public BufferedImage apply(BufferedImage input) {
        Graphics2D g2d = (Graphics2D) input.getGraphics();
        g2d.setColor(col);
        g2d.fill(r);
        g2d.dispose();
        return input;
    }

}
