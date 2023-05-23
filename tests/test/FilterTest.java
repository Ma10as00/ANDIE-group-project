package tests.test;

import org.junit.jupiter.api.Test;
import java.awt.image.*;
import cosc202.andie.*;
import javax.imageio.*;
import java.net.*;

/**
 * <p>
 * Tests to test that the filter actions in
 * {@link FilterActions} behave as we would expect.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Stella Srzich
 */
public class FilterTest {

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
     * Test to make sure that when an image is sharpened by
     * {@link SharpenFilter}, it is correctly sharpened.
     * This is done on an actual test image.
     * </p>
     */
    @Test
    public void SharpenTest() {
        // First, we try to read in the test image.
        BufferedImage original = null;
        try {
            URL path = FilterTest.class.getResource("test.png");
            original = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }
        // Now, we read in the image we are comparing to.
        BufferedImage expected = null;
        try {
            URL path = FilterTest.class.getResource("test_sharpen.png");
            expected = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }

        // Now, we try to apply the action.
        SharpenFilter sharp = new SharpenFilter(1);
        BufferedImage actual = null;
        if (original != null) {
            actual = sharp.apply(original);
        }
        // Check that the two images are 'equal'.
        assert (bufferedImagesEqual(expected, actual));
    }

    /**
     * <p>
     * Test to make sure that when an image is blurred by
     * {@link MeanFilter}, it is correctly blurred.
     * This is done on an actual test image.
     * </p>
     */
    @Test
    public void MeanTest() {
        // First, we try to read in the test image.
        BufferedImage original = null;
        try {
            URL path = FilterTest.class.getResource("test.png");
            original = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }
        // Now, we read in the image we are comparing to.
        BufferedImage expected = null;
        try {
            URL path = FilterTest.class.getResource("test_mean.png");
            expected = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }

        // Now, we try to apply the filter.
        MeanFilter blur = new MeanFilter(1);
        BufferedImage actual = null;
        if (original != null) {
            actual = blur.apply(original);
        }
        // Check that the two images are 'equal'.
        assert (bufferedImagesEqual(expected, actual));
    }

    /**
     * <p>
     * Test to make sure that when an image is blurred by
     * {@link MedianFilter}, it is correctly blurred.
     * This is done on an actual test image.
     * </p>
     */
    @Test
    public void MedianTest() {
        // First, we try to read in the test image.
        BufferedImage original = null;
        try {
            URL path = FilterTest.class.getResource("test.png");
            original = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }
        // Now, we read in the image we are comparing to.
        BufferedImage expected = null;
        try {
            URL path = FilterTest.class.getResource("test_median.png");
            expected = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }

        // Now, we try to apply the filter.
        MedianFilter blur = new MedianFilter(1);
        BufferedImage actual = null;
        if (original != null) {
            actual = blur.apply(original);
        }
        // Check that the two images are 'equal'.
        assert (bufferedImagesEqual(expected, actual));
    }

    /**
     * <p>
     * Test to make sure that when an image is blurred by
     * {@link GaussianBlur}, it is correctly blurred.
     * This is done on an actual test image.
     * </p>
     */
    @Test
    public void GaussianBlurTest() {
        // First, we try to read in the test image.
        BufferedImage original = null;
        try {
            URL path = FilterTest.class.getResource("test.png");
            original = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }
        // Now, we read in the image we are comparing to.
        BufferedImage expected = null;
        try {
            URL path = FilterTest.class.getResource("test_gauss.png");
            expected = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }

        // Now, we try to apply the filter.
        GaussianBlurFilter gauss = new GaussianBlurFilter(5);
        BufferedImage actual = null;
        if (original != null) {
            actual = gauss.apply(original);
        }
        // Check that the two images are 'equal'.
        assert (bufferedImagesEqual(expected, actual));
    }

    /**
     * <p>
     * Test to make sure that when an image is filtered by
     * {@link SobelHorizontalFilter}, it is correctly filtered.
     * This is done on an actual test image.
     * </p>
     */
    @Test
    public void SobelHorizontalTest() {
        // First, we try to read in the test image.
        BufferedImage original = null;
        try {
            URL path = FilterTest.class.getResource("test.png");
            original = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }
        // Now, we read in the image we are comparing to.
        BufferedImage expected = null;
        try {
            URL path = FilterTest.class.getResource("test_sobelhor.png");
            expected = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }

        // Now, we try to apply the filter.
        SobelHorizontalFilter sobel = new SobelHorizontalFilter(false);
        BufferedImage actual = null;
        if (original != null) {
            actual = sobel.apply(original);
        }
        // Check that the two images are 'equal'.
        assert (bufferedImagesEqual(expected, actual));
    }

    /**
     * <p>
     * Test to make sure that when an image is filtered by
     * {@link SobelVerticalFilter}, it is correctly filtered.
     * This is done on an actual test image.
     * </p>
     */
    @Test
    public void SobelVerticalTest() {
        // First, we try to read in the test image.
        BufferedImage original = null;
        try {
            URL path = FilterTest.class.getResource("test.png");
            original = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }
        // Now, we read in the image we are comparing to.
        BufferedImage expected = null;
        try {
            URL path = FilterTest.class.getResource("test_sobelver.png");
            expected = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }

        // Now, we try to apply the filter.
        SobelVerticalFilter sobel = new SobelVerticalFilter(false);
        BufferedImage actual = null;
        if (original != null) {
            actual = sobel.apply(original);
        }
        // Check that the two images are 'equal'.
        assert (bufferedImagesEqual(expected, actual));
    }

    /**
     * <p>
     * Test to make sure that when an image is filtered by
     * {@link SobelOrientationFilter}, with hue = false, it is correctly filtered.
     * This is done on an actual test image.
     * </p>
     */
    @Test
    public void SobelFullTest() {
        // First, we try to read in the test image.
        BufferedImage original = null;
        try {
            URL path = FilterTest.class.getResource("test.png");
            original = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }
        // Now, we read in the image we are comparing to.
        BufferedImage expected = null;
        try {
            URL path = FilterTest.class.getResource("test_sobelfull.png");
            expected = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }

        // Now, we try to apply the filter.
        SobelOrientationFilter sobel = new SobelOrientationFilter(false, false);
        BufferedImage actual = null;
        if (original != null) {
            actual = sobel.apply(original);
        }
        // Check that the two images are 'equal'.
        assert (bufferedImagesEqual(expected, actual));
    }

    /**
     * <p>
     * Test to make sure that when an image is filtered by
     * {@link SobelOrientationFilter}, with hue = true, it is correctly filtered.
     * This is done on an actual test image.
     * </p>
     */
    @Test
    public void SobelOrientationTest() {
        // First, we try to read in the test image.
        BufferedImage original = null;
        try {
            URL path = FilterTest.class.getResource("test.png");
            original = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }
        // Now, we read in the image we are comparing to.
        BufferedImage expected = null;
        try {
            URL path = FilterTest.class.getResource("test_sobelorien.png");
            expected = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }

        // Now, we try to apply the filter.
        SobelOrientationFilter sobel = new SobelOrientationFilter(false, true);
        BufferedImage actual = null;
        if (original != null) {
            actual = sobel.apply(original);
        }
        // Check that the two images are 'equal'.
        assert (bufferedImagesEqual(expected, actual));
    }

    /**
     * <p>
     * Test to make sure that when an image is filtered by
     * {@link EmbossFilter}, with embossType = 1, it is correctly filtered.
     * This is done on an actual test image.
     * </p>
     */
    @Test
    public void Emboss1Test() {
        // First, we try to read in the test image.
        BufferedImage original = null;
        try {
            URL path = FilterTest.class.getResource("test.png");
            original = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }
        // Now, we read in the image we are comparing to.
        BufferedImage expected = null;
        try {
            URL path = FilterTest.class.getResource("test_emboss1.png");
            expected = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }

        // Now, we try to apply the filter.
        EmbossFilter emboss = new EmbossFilter(false, 1);
        BufferedImage actual = null;
        if (original != null) {
            actual = emboss.apply(original);
        }
        // Check that the two images are 'equal'.
        assert (bufferedImagesEqual(expected, actual));
    }

    /**
     * <p>
     * Test to make sure that when an image is filtered by
     * {@link EmbossFilter}, with embossType = 2, it is correctly filtered.
     * This is done on an actual test image.
     * </p>
     */
    @Test
    public void Emboss2Test() {
        // First, we try to read in the test image.
        BufferedImage original = null;
        try {
            URL path = FilterTest.class.getResource("test.png");
            original = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }
        // Now, we read in the image we are comparing to.
        BufferedImage expected = null;
        try {
            URL path = FilterTest.class.getResource("test_emboss2.png");
            expected = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }

        // Now, we try to apply the filter.
        EmbossFilter emboss = new EmbossFilter(false, 2);
        BufferedImage actual = null;
        if (original != null) {
            actual = emboss.apply(original);
        }
        // Check that the two images are 'equal'.
        assert (bufferedImagesEqual(expected, actual));
    }

    /**
     * <p>
     * Test to make sure that when an image is filtered by
     * {@link EmbossFilter}, with embossType = 3, it is correctly filtered.
     * This is done on an actual test image.
     * </p>
     */
    @Test
    public void Emboss3Test() {
        // First, we try to read in the test image.
        BufferedImage original = null;
        try {
            URL path = FilterTest.class.getResource("test.png");
            original = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }
        // Now, we read in the image we are comparing to.
        BufferedImage expected = null;
        try {
            URL path = FilterTest.class.getResource("test_emboss3.png");
            expected = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }

        // Now, we try to apply the filter.
        EmbossFilter emboss = new EmbossFilter(false, 3);
        BufferedImage actual = null;
        if (original != null) {
            actual = emboss.apply(original);
        }
        // Check that the two images are 'equal'.
        assert (bufferedImagesEqual(expected, actual));
    }

    /**
     * <p>
     * Test to make sure that when an image is filtered by
     * {@link EmbossFilter}, with embossType = 4, it is correctly filtered.
     * This is done on an actual test image.
     * </p>
     */
    @Test
    public void Emboss4Test() {
        // First, we try to read in the test image.
        BufferedImage original = null;
        try {
            URL path = FilterTest.class.getResource("test.png");
            original = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }
        // Now, we read in the image we are comparing to.
        BufferedImage expected = null;
        try {
            URL path = FilterTest.class.getResource("test_emboss4.png");
            expected = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }

        // Now, we try to apply the filter.
        EmbossFilter emboss = new EmbossFilter(false, 4);
        BufferedImage actual = null;
        if (original != null) {
            actual = emboss.apply(original);
        }
        // Check that the two images are 'equal'.
        assert (bufferedImagesEqual(expected, actual));
    }

    /**
     * <p>
     * Test to make sure that when an image is filtered by
     * {@link EmbossFilter}, with embossType = 5, it is correctly filtered.
     * This is done on an actual test image.
     * </p>
     */
    @Test
    public void Emboss5Test() {
        // First, we try to read in the test image.
        BufferedImage original = null;
        try {
            URL path = FilterTest.class.getResource("test.png");
            original = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }
        // Now, we read in the image we are comparing to.
        BufferedImage expected = null;
        try {
            URL path = FilterTest.class.getResource("test_emboss5.png");
            expected = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }

        // Now, we try to apply the filter.
        EmbossFilter emboss = new EmbossFilter(false, 5);
        BufferedImage actual = null;
        if (original != null) {
            actual = emboss.apply(original);
        }
        // Check that the two images are 'equal'.
        assert (bufferedImagesEqual(expected, actual));
    }

    /**
     * <p>
     * Test to make sure that when an image is filtered by
     * {@link EmbossFilter}, with embossType = 6, it is correctly filtered.
     * This is done on an actual test image.
     * </p>
     */
    @Test
    public void Emboss6Test() {
        // First, we try to read in the test image.
        BufferedImage original = null;
        try {
            URL path = FilterTest.class.getResource("test.png");
            original = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }
        // Now, we read in the image we are comparing to.
        BufferedImage expected = null;
        try {
            URL path = FilterTest.class.getResource("test_emboss6.png");
            expected = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }

        // Now, we try to apply the filter.
        EmbossFilter emboss = new EmbossFilter(false, 6);
        BufferedImage actual = null;
        if (original != null) {
            actual = emboss.apply(original);
        }
        // Check that the two images are 'equal'.
        assert (bufferedImagesEqual(expected, actual));
    }

    /**
     * <p>
     * Test to make sure that when an image is filtered by
     * {@link EmbossFilter}, with embossType = 7, it is correctly filtered.
     * This is done on an actual test image.
     * </p>
     */
    @Test
    public void Emboss7Test() {
        // First, we try to read in the test image.
        BufferedImage original = null;
        try {
            URL path = FilterTest.class.getResource("test.png");
            original = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }
        // Now, we read in the image we are comparing to.
        BufferedImage expected = null;
        try {
            URL path = FilterTest.class.getResource("test_emboss7.png");
            expected = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }

        // Now, we try to apply the filter.
        EmbossFilter emboss = new EmbossFilter(false, 7);
        BufferedImage actual = null;
        if (original != null) {
            actual = emboss.apply(original);
        }
        // Check that the two images are 'equal'.
        assert (bufferedImagesEqual(expected, actual));
    }

    /**
     * <p>
     * Test to make sure that when an image is filtered by
     * {@link EmbossFilter}, with embossType = 8, it is correctly filtered.
     * This is done on an actual test image.
     * </p>
     */
    @Test
    public void Emboss8Test() {
        // First, we try to read in the test image.
        BufferedImage original = null;
        try {
            URL path = FilterTest.class.getResource("test.png");
            original = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }
        // Now, we read in the image we are comparing to.
        BufferedImage expected = null;
        try {
            URL path = FilterTest.class.getResource("test_emboss8.png");
            expected = ImageIO.read(path);
        } catch (Exception e) {
            // This will happen for various reasons. But, will not happen by the way it is
            // set up.
        }

        // Now, we try to apply the filter.
        EmbossFilter emboss = new EmbossFilter(false, 8);
        BufferedImage actual = null;
        if (original != null) {
            actual = emboss.apply(original);
        }
        // Check that the two images are 'equal'.
        assert (bufferedImagesEqual(expected, actual));
    }

}
