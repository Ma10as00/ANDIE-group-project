package tests.test;

import org.junit.jupiter.api.Test;
import java.awt.image.*;
import cosc202.andie.*;

/**
 * <p>
 * Tests to test that the resize actions in
 * {@link ResizeActions} behave as we would expect.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Stella Srzich
 */
public class ResizeTest {

    /**
     * <p>
     * Test to make sure that when an image is resized by
     * {@link ImageResize50}, it's dimensions change by 50%.
     * This is done for various dimensions of an image.
     * </p>
     */
    @Test
    public void Resize50Test() {
        // Repeat test for various dimensions of an image.
        for (int n = 50; n < 1000; n += 50) {
            // Create test image.
            BufferedImage testImage = new BufferedImage(n, n, BufferedImage.TYPE_INT_ARGB);
            int widthBefore = testImage.getWidth();
            int heightBefore = testImage.getHeight();

            // Resize test image by 150%.
            ImageResize50 resize = new ImageResize50();
            BufferedImage resizedImage = resize.apply(testImage);
            int widthAfter = resizedImage.getWidth();
            int heightAfter = resizedImage.getHeight();

            assert (widthAfter == 0.5 * widthBefore);
            assert (heightAfter == 0.5 * heightBefore);
        }
    }

    /**
     * <p>
     * Test to make sure that when an image is resized by
     * {@link ImageResize150}, it's dimensions change by 150%.
     * This is done for various dimensions of an image.
     * </p>
     */
    @Test
    public void Resize150Test() {
        // Repeat test for various dimensions of an image.
        for (int n = 50; n < 1000; n += 50) {
            // Create test image.
            BufferedImage testImage = new BufferedImage(n, n, BufferedImage.TYPE_INT_ARGB);
            int widthBefore = testImage.getWidth();
            int heightBefore = testImage.getHeight();

            // Resize test image by 150%.
            ImageResize150 resize = new ImageResize150();
            BufferedImage resizedImage = resize.apply(testImage);
            int widthAfter = resizedImage.getWidth();
            int heightAfter = resizedImage.getHeight();

            // Assert dimensions are as expected.
            assert (widthAfter == 1.5 * widthBefore);
            assert (heightAfter == 1.5 * heightBefore);
        }
    }

    /**
     * <p>
     * Test to make sure that when an image is resized by
     * {@link ImageResizeN}, it's dimensions decrease by n%.
     * This is done for values of n.
     * </p>
     */
    @Test
    public void ResizeNTest() {
        // Repeat test for various dimensions of an image.
        for (int n = 50; n <= 200; n += 10) {
            // Create test image.
            BufferedImage testImage = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
            int widthBefore = testImage.getWidth();
            int heightBefore = testImage.getHeight();

            // Resize test image by 150%.
            ImageResizeN resize = new ImageResizeN(n);
            BufferedImage resizedImage = resize.apply(testImage);
            int widthAfter = resizedImage.getWidth();
            int heightAfter = resizedImage.getHeight();

            // Assert dimensions are as expected.
            assert (widthAfter == n * widthBefore / 100);
            assert (heightAfter == n * heightBefore / 100);
        }
    }
}
