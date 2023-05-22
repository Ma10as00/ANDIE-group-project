package cosc202.andie;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Rectangle;

public class DrawRec implements ImageOperation, Serializable {

    private Rectangle r;
    private Color col;
    private boolean fill;
    private int strokeWidth;

    public DrawRec(double scale, Rectangle rect, Boolean fill) {
        int x = (int)((double)rect.x/scale);
        int y = (int)((double)rect.y/scale);
        int width = (int)((double)rect.width/scale);
        int height = (int)((double)rect.height/scale);
        this.strokeWidth = (int)(DrawActions.userWidth/scale);
        r = new Rectangle(x, y, width, height);
        col = new Color(DrawActions.userColour.getRed(), DrawActions.userColour.getGreen(), DrawActions.userColour.getBlue());
        this.fill = fill;
    }

    @Override
    public BufferedImage apply(BufferedImage input) {
        Graphics2D g2d = (Graphics2D) input.getGraphics();
        if (!fill) {
            g2d.setColor(this.col);
            g2d.fill(r);
        }
        if (fill) {
            g2d.setColor(this.col);
            BasicStroke stroke = new BasicStroke(strokeWidth);
            g2d.setStroke(stroke);
            g2d.draw(r);
        }
        g2d.dispose();
        return input;
    }

}
