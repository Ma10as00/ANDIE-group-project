package tests.test;

import org.junit.jupiter.api.Test;
import java.awt.image.*;
import cosc202.andie.*;
import javax.imageio.*;
import java.net.*;

/**
 * <p>
 * Tests to test that the colour actions in
 * {@link ColourActions} behave as we would expect.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Stella Srzich
 */
public class ColourTest {

    /**
     * <p>
     * This is a method to check if two buffered images are equal. That is, if they
     * have the
     * same width and height, and they have the same ARGB values for all pixels.
     * </p>
     * 
     * @param image1 The BufferedImage we would like to compare another
     *               BufferedImage to.
     * @param image2 The other BufferedImage we would like to compare image 1 with.
     * @return true if the images are 'equal', false otherwise.
     */
    private static boolean bufferedImagesEqual(BufferedImage image1, BufferedImage image2) {
        if (image1 == null && image2 == null) {
            return true;
        }
        int h1 = image1.getHeight();
        int h2 = image2.getHeight();
        int w1 = image1.getWidth();
        int w2 = image2.getWidth();
        // Check their widths and heights are equal.
        if (h1 == h2 && w1 == w2) {
            for (int x = 0; x < w1; x++) {
                for (int y = 0; y < h1; y++) {
                    if (image1.getRGB(x, y) != image2.getRGB(x, y)) {
                        // A pixel is not the same.
                        return false;
                    }
                }
            }
        } else {
            // Different heights or widths.
            return false;
        }
        // If we get here, they must be 'equal' buffered images.
        return true;
    }

    /**
     * <p>
     * Test to make sure that when an image is converted to grey by
     * {@link ConvertToGrey}, it is correctly converted to grey.
     * This is done on an actual test image.
     * </p>
     */
    @Test
    public void ConvertToGreyTest() {
        // First, we try to read in the test image.
        BufferedImage original = null;
        try {
            URL path = ColourTest.class.getResource("test.png");
            original = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }
        // Now, we read in the image we are comparing to.
        BufferedImage expected = null;
        try {
            URL path = ColourTest.class.getResource("test_grey.png");
            expected = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }

        // Now, we try to apply the action.
        ConvertToGrey grey = new ConvertToGrey();
        BufferedImage actual = null;
        if (original != null) {
            actual = grey.apply(original);
        }
        // Check that the two images are 'equal'.
        assert (bufferedImagesEqual(expected, actual));
    }

    /**
     * <p>
     * Test to make sure that when an image has its brightness changed by
     * {@link BrightnessFilter}, it is correctly altered.
     * This is done on an actual test image.
     * </p>
     */
    @Test
    public void BrightnessTest() {
        // First, we try to read in the test image.
        BufferedImage original = null;
        try {
            URL path = ColourTest.class.getResource("test.png");
            original = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }
        // Now, we read in the image we are comparing to.
        BufferedImage expected = null;
        try {
            URL path = ColourTest.class.getResource("test_bright.png");
            expected = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }

        // Now, we try to apply the filter.
        BrightnessFilter bright = new BrightnessFilter(50);
        BufferedImage actual = null;
        if (original != null) {
            actual = bright.apply(original);
        }
        // Check that the two images are 'equal'.
        assert (bufferedImagesEqual(expected, actual));
    }

    /**
     * <p>
     * Test to make sure that when an image has its contrast changed by
     * {@link ContrastFilter}, it is correctly altered.
     * This is done on an actual test image.
     * </p>
     */
    @Test
    public void ContrastTest() {
        // First, we try to read in the test image.
        BufferedImage original = null;
        try {
            URL path = ColourTest.class.getResource("test.png");
            original = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }
        // Now, we read in the image we are comparing to.
        BufferedImage expected = null;
        try {
            URL path = ColourTest.class.getResource("test_contrast.png");
            expected = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }

        // Now, we try to apply the filter.
        ContrastFilter contrast = new ContrastFilter(50);
        BufferedImage actual = null;
        if (original != null) {
            actual = contrast.apply(original);
        }
        // Check that the two images are 'equal'.
        assert (bufferedImagesEqual(expected, actual));
    }

}
