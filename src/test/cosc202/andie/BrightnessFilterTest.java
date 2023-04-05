package test.cosc202.andie;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cosc202.andie.BrightnessFilter;

class BrightnessFilterTest {

    int test = 0;

    BrightnessFilter bfTest;

    @BeforeEach
    public void setup() {
        bfTest = new BrightnessFilter(test);
    }

    @Test
    public void BrightnessFilter() {
        bfTest = new BrightnessFilter(test);
        assertEquals(0, bfTest.getScale());
    }
}