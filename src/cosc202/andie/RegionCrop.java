package cosc202.andie;
import java.awt.Graphics;
import java.awt.image.*;

/**
 * <p>
 * ImageOperation to crop an area given a selected region.
 * </p>
 * 
 * <p>
 * The crop feature will take the area that has been selected by the user and then crop out all pixels that have not been selected.
 * It does this by getting a subImage of the rectangle that was selected and then setting this subImage as the new BufferedImage.
 * It takes parameters of the top left point and the bottom right point (Point being the x value and y value).
 * </p>
 * 
 * @author Katie Wink
 */
public class RegionCrop implements ImageOperation {
    int lX, lY, rX, rY;
    int width, height;  

    /**
     * Stores the x and y values and then uses them to calculate the width anbd heigh of the new subImage
     * 
     * @param lX Top left x value
     * @param lY Top left y value
     * @param rX Bottom right x value
     * @param rY Bottom right y value
     */
    RegionCrop(int lX, int lY, int rX, int rY){
        this.lX = lX; 
        this.lY = lY;
        this.rX = rX;
        this.rY = rY;
        width = rX - lX; 
        height = rY - lY; 
    }


    /**
     * <p>
     * Apply a region crop to an image.
     * </p>

     * @param previousImage The image to crop.
     * @return The resulting (cropped) image.
     */
    public BufferedImage apply(BufferedImage previousImage) {
        BufferedImage img = previousImage.getSubimage(lX, lY, width, height); //fill in the corners of the desired crop location here
        BufferedImage copyOfImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = copyOfImage.createGraphics();
        g.drawImage(img, 0, 0, null);
        return copyOfImage; 
    } 
}
