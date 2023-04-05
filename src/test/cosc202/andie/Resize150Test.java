package test.cosc202.andie;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import java.awt.image.*;

import cosc202.andie.ImageResize150;

public class Resize150Test {
    @RepeatedTest(10)
    @Test
    public void Resize150Test() {
        // Ceate test image.
        BufferedImage testImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        int widthBefore = testImage.getWidth();
        int heightBefore = testImage.getHeight();

        // Resize test image by 150%.
        ImageResize150 resize = new ImageResize150();
        BufferedImage resizedImage = resize.apply(testImage);
        int widthAfter = resizedImage.getWidth();
        int heightAfter = resizedImage.getHeight();

        assert ((double) widthAfter == 1.5 * (double) widthBefore);
        assert ((double) heightAfter == 1.5 * (double) heightBefore);

    }
}
