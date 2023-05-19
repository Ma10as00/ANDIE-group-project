package cosc202.andie;

import java.awt.image.*;
import java.awt.*;

/**
 * <p>
 * ImageOperation to apply an emboss filter (there are many types).
 * </p>
 * 
 * <p>
 * An emboss filter makes the image look like it is 'poking' out or in, in a certain direction.
 * The outputted image will typically be grey where there are no edges, and it will look like sections of the image
 * are poking out or in, defined by 'coloured shadows'. There are 8 different directions as can be selected
 * as specified in the constructor. Note, this implementation gives us the option to apply the filter after 
 * a light Gaussian blur filter (of radius 1), which typically embosses a noisy natrual image better.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Stella Srzich (Modified from Steven Mills)
 */
public class EmbossFilter implements ImageOperation, java.io.Serializable {

    /**
     * This boolean gives us the option to apply the emboss filter after a gaussian blur
     * filter with radius 1 is applied. This works better for natural images where there is a lot of
     * noise potentially obstructing the actual edges. If it is true, the Gaussian blur filter with radius 1 is applied
     * before we apply the sobel filter.
     */
    private boolean removeNoise;

    /**
     * The emboss type determines the kernel applied to the image and thus the type of emboss
     * the image has applied to it. It will be one of the eight emboss kernels, 
     * as decided by which option is chosen in the constructor for embossType.
     */
    private int[] kernel;

    /** 
     * This static, final int is used to decide the kernel that is used in an emboss filter.
     * This corrseponds to the first emboss kernel.
     */
    public static final int EMBOSS_1 = 1;

    /** 
     * This static, final int is used to decide the kernel that is used in an emboss filter.
     * This corrseponds to the second emboss kernel.
     */
    public static final int EMBOSS_2 = 2;

    /** 
     * This static, final int is used to decide the kernel that is used in an emboss filter.
     * This corrseponds to the third emboss kernel.
     */
    public static final int EMBOSS_3 = 3;

    /** 
     * This static, final int is used to decide the kernel that is used in an emboss filter.
     * This corrseponds to the fourth emboss kernel.
     */
    public static final int EMBOSS_4 = 4;

    /** 
     * This static, final int is used to decide the kernel that is used in an emboss filter.
     * This corrseponds to the fifth emboss kernel.
     */
    public static final int EMBOSS_5 = 5;

    /** 
     * This static, final int is used to decide the kernel that is used in an emboss filter.
     * This corrseponds to the sixth emboss kernel.
     */
    public static final int EMBOSS_6 = 6;

    /** 
     * This static, final int is used to decide the kernel that is used in an emboss filter.
     * This corrseponds to the seventh emboss kernel.
     */
    public static final int EMBOSS_7 = 7;

    /** 
     * This static, final int is used to decide the kernel that is used in an emboss filter.
     * This corrseponds to the eighth emboss kernel.
     */
    public static final int EMBOSS_8 = 8;

    /**
     * <p>
     * Construct an emboss filter.
     * </p>
     * 
     * <p>
     * An emboss filter makes the image look like it is 'poking' out or in, in a certain direction.
     * The outputted image will typically be grey where there are no edges, and it will look like sections of the image
     * are poking out or in, defined by 'coloured shadows'. There are 8 different directions as can be selected
     * as can be chosen with the embossType. removeNoise gives us the option to apply the sobel horizontal filter after a gaussian blur
     * filter with radius 1 is applied. This works better for natural images where there is a lot of
     * noise potentially obstructing the actual edges. If it is true, the gaussian blur filter with radius 1 is applied
     * before we apply the emboss filter.
     * </p>
     * @see GaussianBlurFilter
     * @param removeNoise True to apply a light Gaussian blur filter before the sobel filter, false otherwise.
     * @param embossType Determines the direction of the emboss. The options are ints 1 to 8 (inclusive). See the EMBOSS_N public static ints of this class.
     */
    public EmbossFilter(boolean removeNoise, int embossType) {
        this.removeNoise = removeNoise;
        if (embossType == EMBOSS_1) {
            this.kernel = new int[] {0, 0, 0, 1, 0, -1, 0, 0, 0};
        }
        else if (embossType == EMBOSS_2) {
            this.kernel = new int[] {1, 0, 0, 0, 0, 0, 0, 0, -1};
        }
        else if (embossType == EMBOSS_3) {
            this.kernel = new int[] {0, 1, 0, 0, 0, 0, 0, -1, 0};
        }
        else if (embossType == EMBOSS_4) {
            this.kernel = new int[] {0, 0, 1, 0, 0, 0, -1, 0, 0};
        }
        else if (embossType == EMBOSS_5) {
            this.kernel = new int[] {0, 0, 0, -1, 0, 1, 0, 0, 0};
        }
        else if (embossType == EMBOSS_6) {
            this.kernel = new int[] {-1, 0, 0, 0, 0, 0, 0, 0, 1};
        }
        else if (embossType == EMBOSS_7) {
            this.kernel = new int[] {0, -1, 0, 0, 0, 0, 0, 1, 0};
        }
        else { // EMBOSS_8
            this.kernel = new int[] {0, 0, -1, 0, 0, 0, 1, 0, 0};
        }
    }

    /**
     * <p>
     * Construct a default emboss filter.
     * </p>
     * 
     * <p>
     * By default, a light Gaussian blur filter is applied to the image before it is embossed. 
     * And, by default, this will be with the eighth option for direction as implemented by a kernel. 
     * </p>
     * @see GaussianBlurFilter
     */
    public EmbossFilter() {
        this(true, 1);
    }

    /**
     * <p>
     * Apply an emboss filter to an image.
     * </p>
     * 
     * <p>
     * An emboss filter makes the image look like it is 'poking' out or in, in a certain direction.
     * The outputted image will typically be grey where there are no edges, and it will look like sections of the image
     * are poking out or in, defined by 'coloured shadows'. There are 8 different directions as can be selected
     * as can be chosen with the embossType. removeNoise gives us the option to apply the sobel horizontal filter after a gaussian blur
     * filter with radius 1 is applied. This works better for natural images where there is a lot of
     * noise potentially obstructing the actual edges. If it is true, the gaussian blur filter with radius 1 is applied
     * before we apply the emboss filter.
     * </p>
     * 
     * @param input The image to apply the sobel horizontal filter to.
     * @return The resulting (horizontal edge detected) image.
     */
    public BufferedImage apply(BufferedImage input) {
        // Create a new image with the same values as in the original image, but with 
        // the edge pixel values copied to new edge pixel values (the image is bigger by the radius of the kernel)
        // on each side and the top and bottom.
        int radius = 1;
        BufferedImage edgesPlusInput = new BufferedImage(input.getWidth() + 2*radius, input.getHeight() + 2*radius, BufferedImage.TYPE_INT_ARGB);
        // Fill the pixel values of this new buffered image.
        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                // Copy over pixel values from the original image to pixels to the right and lower by 'radius' amount.
                edgesPlusInput.setRGB(x + radius, y + radius, input.getRGB(x, y));
                // If we are at an edge, then we copy that value to the values above/below/right/left.
                if (y == 0) { // We are at the top of the image.
                    for (int i = 0; i < radius; i++) {
                        edgesPlusInput.setRGB(x + radius, i, input.getRGB(x, y));
                    }
                }
                else if (y == input.getHeight() - 1) { // We are at the bottom of the image.
                    for (int i = 0; i < radius; i++) {
                        edgesPlusInput.setRGB(x + radius, i + input.getHeight() + radius, input.getRGB(x, y));
                    }
                }
                if (x == 0) { // We are at the left of the image.
                    for (int i = 0; i < radius; i++) {
                        edgesPlusInput.setRGB(i, y + radius, input.getRGB(x, y));
                    }
                }
                else if (x == input.getWidth() - 1) { // We are at the right of the image.
                    for (int i = 0; i < radius; i++) {
                        edgesPlusInput.setRGB(i + input.getWidth() + radius, y + radius, input.getRGB(x, y));
                    }
                }
                // Dealing with corners.
                if (x == 0 && y == 0) { // Top left corner.
                    for (int a = 0; a < radius; a++) {
                        for (int b = 0; b < radius; b++) {
                            edgesPlusInput.setRGB(a, b, input.getRGB(x, y));
                        }
                    }
                }
                else if (x == 0 && y == input.getHeight() - 1) { // Bottom left corner.
                    for (int a = 0; a < radius; a++) {
                        for (int b = 0; b < radius; b++) {
                            edgesPlusInput.setRGB(a, b + input.getHeight() + radius, input.getRGB(x, y));
                        }
                    }
                }
                else if (x == input.getWidth() - 1 && y == 0) { // Top right corner.
                    for (int a = 0; a < radius; a++) {
                        for (int b = 0; b < radius; b++) {
                            edgesPlusInput.setRGB(a + input.getWidth() + radius, b, input.getRGB(x, y));
                        }
                    }
                }
                else if (x == input.getWidth() - 1 && y == input.getHeight() - 1) { // Bottom right corner.
                    for (int a = 0; a < radius; a++) {
                        for (int b = 0; b < radius; b++) {
                            edgesPlusInput.setRGB(a + input.getWidth() + radius, b + input.getHeight() + radius, input.getRGB(x, y));
                        }
                    }
                }
            }
        }

        // If we want to remove the noise, apply a gaussian blur filter of radius 1.
        if (removeNoise) {
            GaussianBlurFilter blur =  new GaussianBlurFilter();
            edgesPlusInput = blur.apply(edgesPlusInput);
        }

        // Apply the emboss filter to the new buffered image that has extended edges.
        // Note, I am not using ConvolveOp as it makes the negative values get lost.
        int width = edgesPlusInput.getWidth();
        int height = edgesPlusInput.getHeight();
        // Create a new buffered image to store the outputted values, but still not cropped.
        BufferedImage uncroppedOutput = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        // We apply the kernel manually. This part also deals with negative values.
        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {
                // Get the pixel int RGB values.
                int val00 = edgesPlusInput.getRGB(x - 1, y - 1);
                int val01 = edgesPlusInput.getRGB(x - 1, y);
                int val02 = edgesPlusInput.getRGB(x - 1, y + 1);

                int val10 = edgesPlusInput.getRGB(x, y - 1);
                int val11 = edgesPlusInput.getRGB(x, y);
                int val12 = edgesPlusInput.getRGB(x, y + 1);

                int val20 = edgesPlusInput.getRGB(x + 1, y - 1);
                int val21 = edgesPlusInput.getRGB(x + 1, y);
                int val22 = edgesPlusInput.getRGB(x + 1, y + 1);
                // Get the values for each colour channel for each pixel.
                int r00 = (val00 >> 16) & 0xff;
                int g00 = (val00 >> 8) & 0xff;
                int b00 = val00 & 0xff;
                int r01 = (val01 >> 16) & 0xff;
                int g01 = (val01 >> 8) & 0xff;
                int b01 = val01 & 0xff;
                int r02 = (val02 >> 16) & 0xff;
                int g02 = (val02 >> 8) & 0xff;
                int b02 = val02 & 0xff;

                int r10 = (val10 >> 16) & 0xff;
                int g10 = (val10 >> 8) & 0xff;
                int b10 = val10 & 0xff;
                int r11 = (val11 >> 16) & 0xff;
                int g11 = (val11 >> 8) & 0xff;
                int b11 = val11 & 0xff;
                int r12 = (val12 >> 16) & 0xff;
                int g12 = (val12 >> 8) & 0xff;
                int b12 = val12 & 0xff;

                int r20 = (val20 >> 16) & 0xff;
                int g20 = (val20 >> 8) & 0xff;
                int b20 = val20 & 0xff;
                int r21 = (val21 >> 16) & 0xff;
                int g21 = (val21 >> 8) & 0xff;
                int b21 = val21 & 0xff;
                int r22 = (val22 >> 16) & 0xff;
                int g22 = (val22 >> 8) & 0xff;
                int b22 = val22 & 0xff;
                // Make arrays holding the channel values.
                int[] arrR = {r00, r01, r02, r10, r11, r12, r20, r21, r22};
                int[] arrG = {g00, g01, g02, g10, g11, g12, g20, g21, g22};
                int[] arrB = {b00, b01, b02, b10, b11, b12, b20, b21, b22};

                // Manually do the kernel convolution for each channel.
                int valR = this.dotProduct(arrR);
                int valG = this.dotProduct(arrG);
                int valB = this.dotProduct(arrB);

                // Deal with negative results, shifting them to 127.
                int newR = Math.min(Math.max(valR + 127, 0), 255);
                int newG = Math.min(Math.max(valG + 127, 0), 255);
                int newB = Math.min(Math.max(valB + 127, 0), 255);
    
                // Set the new pixel color values.
                Color newColor = new Color(newR, newG, newB);
                uncroppedOutput.setRGB(x, y, newColor.getRGB());
            }
        }

        // Crop the uncropped output.
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < input.getWidth(); x++) {
            for (int y = 0; y < input.getHeight(); y++) {
                output.setRGB(x, y, uncroppedOutput.getRGB(x + radius, y + radius));
            }
        }

        // Return the output.
        return output;
    }

    /**
     * This support method calculates the euclidean dot product of two vectors. In particular, 
     * this is between a set of values for a given 3x3 grid of the images in a given channel
     * (i.e. R, G or B) and the kernel for this EmbossFilter. This is used to apply the kernel
     * to each channel for a given pixel in the image. Note, we are expecting a int[] channelValues
     * of length 9.
     * @param channelValues The int values for a RGB channel of a 3x3 grid in an image.
     * @return The result of applying a dot product of channelValues with this EditableImage's kernel.
     */
    private int dotProduct(int[] channelValues) {
        int dot = 0;
        for (int i = 0; i < channelValues.length; i++) {
            dot += channelValues[i]*kernel[i];
        }
        return dot;
    }

}


