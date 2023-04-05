package test.cosc202.andie;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;

import cosc202.andie.MedianFilter;

public class MedianFilterTest {

    // accessor should return same value as mutator
    @RepeatedTest(10)
    @Test
    public void testMutator() {
        int testFigure = 1;
        MedianFilter test_mf = new MedianFilter(testFigure);
        assertEquals(testFigure, test_mf.getRadius());
    }

    // default constructor should generate same kernel as radius is 1
    @Test
    public void testDefault() {
        MedianFilter test_mf1 = new MedianFilter();
        MedianFilter test_mf2 = new MedianFilter(1);
        assertEquals(test_mf1.getRadius(), test_mf2.getRadius());
    }
}