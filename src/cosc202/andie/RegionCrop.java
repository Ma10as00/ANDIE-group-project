package cosc202.andie;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.*;

/**
 * <p>
 * ImageOperation to crop an area given a selected region.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * <p>
 * The crop feature will take the area that has been selected by the user and
 * then crop out all pixels that have not been selected.
 * It does this by getting a subImage of the rectangle that was selected and
 * then setting this subImage as the new BufferedImage.
 * It takes parameters of the top left point and the bottom right point (Point
 * being the x value and y value).
 * </p>
 * 
 * @author Katie Wink
 */
public class RegionCrop implements ImageOperation, java.io.Serializable {

    /*
     * This stores the x and y coordinates of the upper-left corner of the region
     * selected,
     * as well as the width and height of that region.
     */
    private Rectangle r;

    /**
     * This is stores the scale of the EditableImage at the time when the crop was
     * applied.
     * This means we can correctly crop the image how the user would expect.
     */
    private double scale;

    /**
     * <p>
     * Construct a new RegionCrop.
     * </p>
     * 
     * <p>
     * RegionCrop crops an image to the region that has been selected as a
     * rectangle.
     * </p>
     * 
     * @param rect The {@link Rectangle} of the region selected that we would like
     *             the image to be cropped to.
     */
    public RegionCrop(double scale, Rectangle rect) {
        this.scale = scale;
        r = rect;
    }

    /**
     * <p>
     * Apply a region crop to an image.
     * </p>
     * 
     * @param previousImage The image to crop.
     * @return The resulting (cropped) image.
     */
    public BufferedImage apply(BufferedImage previousImage) {
        BufferedImage img = previousImage.getSubimage((int) (r.x / scale), (int) (r.y / scale), (int) (r.width / scale),
                (int) (r.height / scale));
        // fill in the corners of the desired crop location here
        BufferedImage copyOfImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = copyOfImage.createGraphics();
        g.drawImage(img, 0, 0, null);
        return copyOfImage;
    }
}
