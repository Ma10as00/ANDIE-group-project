package test.cosc202.andie;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cosc202.andie.GaussianBlurFilter;

class GaussianBlurFilterTest {

    int valTest = 0;

    GaussianBlurFilter blurTest;

    @BeforeEach
    public void setup() {
        blurTest = new GaussianBlurFilter(valTest);

    }

    @Test
    public void GaussianBlurFilter() {
        blurTest = new GaussianBlurFilterTest(valTest);
        assertEquals(0, blurTest.calculateGaussian());
    }

}