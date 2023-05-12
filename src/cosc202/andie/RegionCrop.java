package cosc202.andie;
import java.awt.Graphics;
import java.awt.image.*;


public class RegionCrop implements ImageOperation {
    int lX, lY, rX, rY;
    int width, height;  

    RegionCrop(int lX, int lY, int rX, int rY){
        this.lX = lX; 
        this.lY = lY;
        this.rX = rX;
        this.rY = rY;
        width = rX - lX; 
        height = rY - lY; 
    }



    public BufferedImage apply(BufferedImage previousImage) {
        BufferedImage img = previousImage.getSubimage(lX, lY, width, height); //fill in the corners of the desired crop location here
        BufferedImage copyOfImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = copyOfImage.createGraphics();
        g.drawImage(img, 0, 0, null);
        return copyOfImage; 
    } 
}
