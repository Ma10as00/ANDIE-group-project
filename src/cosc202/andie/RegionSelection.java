package cosc202.andie;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.image.*;




public class RegionSelection implements ImageOperation {
    int lX, lY, rX, rY;
    int width, height;  

    RegionSelection(int lX, int lY, int rX, int rY){
        this.lX = lX; 
        this.lY = lY;
        this.rX = rX;
        this.rY = rY;
        width = rX - lX; 
        height = rY - lY; 
    }



    public BufferedImage apply(BufferedImage previousImage) {
        /* MouseHandler.mousePressed(e);
        MouseHandler.mouseReleased(e);  */
        Graphics2D g2d = previousImage.createGraphics();
        AlphaComposite dark = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);    // 50% opacity
        g2d.setComposite(dark);
        g2d.fillRect(lX, lY, width, height);
        g2d.dispose();
        return previousImage; 
    } 
}
