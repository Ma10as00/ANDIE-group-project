package tests.test;

import org.junit.jupiter.api.Test;
import java.awt.image.*;
import cosc202.andie.*;
import javax.imageio.*;
import java.net.*;

/**
 * <p>
 * Tests to test that the orientation actions in
 * {@link OrientationActions} behave as we would expect.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Stella Srzich
 */
public class OrientationTest {

    /**
     * This is a method to check if two buffered images are equal. That is, if they
     * have the
     * same width and height, and they have the same ARGB values for all pixels.
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
     * Test to make sure that when an image is rotated right by
     * {@link RotateRight}, it is correctly rotated right.
     * This is done on an actual test image.
     * <\p>
     */
    @Test
    public void RotateRightTest() {
        // First, we try to read in the test image.
        BufferedImage original = null;
        try {
            URL path = OrientationTest.class.getResource("test.png");
            original = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }
        // Now, we read in the image we are comparing to.
        BufferedImage expected = null;
        try {
            URL path = OrientationTest.class.getResource("test_right.png");
            expected = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }

        // Now, we try to apply the rotation.
        RotateRight rotate = new RotateRight();
        BufferedImage actual = null;
        if (original != null) {
            actual = rotate.apply(original);
        }
        // Check that the two images are 'equal'.
        assert (bufferedImagesEqual(expected, actual));
    }

    /**
     * <p>
     * Test to make sure that when an image is rotated left by
     * {@link RotateLeft}, it is correctly rotated left.
     * This is done on an actual test image.
     * <\p>
     */
    @Test
    public void RotateLeftTest() {
        // First, we try to read in the test image.
        BufferedImage original = null;
        try {
            URL path = OrientationTest.class.getResource("test.png");
            original = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }
        // Now, we read in the image we are comparing to.
        BufferedImage expected = null;
        try {
            URL path = OrientationTest.class.getResource("test_left.png");
            expected = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }

        // Now, we try to apply the rotation.
        RotateLeft rotate = new RotateLeft();
        BufferedImage actual = null;
        if (original != null) {
            actual = rotate.apply(original);
        }
        // Check that the two images are 'equal'.
        assert (bufferedImagesEqual(expected, actual));
    }

    /**
     * <p>
     * Test to make sure that when an image is rotated 180 degrees by
     * {@link Rotate180}, it is correctly rotated 180 degrees.
     * This is done on an actual test image.
     * <\p>
     */
    @Test
    public void Rotate180Test() {
        // First, we try to read in the test image.
        BufferedImage original = null;
        try {
            URL path = OrientationTest.class.getResource("test.png");
            original = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }
        // Now, we read in the image we are comparing to.
        BufferedImage expected = null;
        try {
            URL path = OrientationTest.class.getResource("test_180.png");
            expected = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }

        // Now, we try to apply the rotation.
        Rotate180 rotate = new Rotate180();
        BufferedImage actual = null;
        if (original != null) {
            actual = rotate.apply(original);
        }
        // Check that the two images are 'equal'.
        assert (bufferedImagesEqual(expected, actual));
    }

    /**
     * <p>
     * Test to make sure that when an image is flipped vertically
     * {@link FlipVertical}, it is correctly flipped vertically
     * This is done on an actual test image.
     * <\p>
     */
    @Test
    public void FlipVerTest() {
        // First, we try to read in the test image.
        BufferedImage original = null;
        try {
            URL path = OrientationTest.class.getResource("test.png");
            original = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }
        // Now, we read in the image we are comparing to.
        BufferedImage expected = null;
        try {
            URL path = OrientationTest.class.getResource("test_vertical.png");
            expected = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }

        // Now, we try to apply the flip.
        FlipVertical flip = new FlipVertical();
        BufferedImage actual = null;
        if (original != null) {
            actual = flip.apply(original);
        }
        // Check that the two images are 'equal'.
        assert (bufferedImagesEqual(expected, actual));
    }

    /**
     * <p>
     * Test to make sure that when an image is flipped horizontally
     * {@link FlipHortizontal}, it is correctly flipped horizontally
     * This is done on an actual test image.
     * <\p>
     */
    @Test
    public void FlipHorTest() {
        // First, we try to read in the test image.
        BufferedImage original = null;
        try {
            URL path = OrientationTest.class.getResource("test.png");
            original = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }
        // Now, we read in the image we are comparing to.
        BufferedImage expected = null;
        try {
            URL path = OrientationTest.class.getResource("test_horizontal.png");
            expected = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }

        // Now, we try to apply the flip.
        FlipHorizontal flip = new FlipHorizontal();
        BufferedImage actual = null;
        if (original != null) {
            actual = flip.apply(original);
        }
        // Check that the two images are 'equal'.
        assert (bufferedImagesEqual(expected, actual));
    }

}
