package cosc202.andie;

import java.awt.image.*;

public class ContrastFilter implements ImageOperation {
    int value;

    public ContrastFilter(int value) {
        this.value = value;
    }

    @Override
    public BufferedImage apply(BufferedImage previousImage) {
        float contrast;
        try {

            contrast = 1.0f + value / 10f;
            System.out.print(contrast);
            RescaleOp rescale = new RescaleOp(contrast, (-12.75f * contrast), null);
            rescale.filter(previousImage, previousImage);

        } catch (NullPointerException e) {
            System.out.println("Need an imagine sonny boy");
        }
        return previousImage;
    }
}