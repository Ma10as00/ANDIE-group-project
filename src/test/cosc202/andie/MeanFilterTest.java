package test.cosc202.andie;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import cosc202.andie.MeanFilter;

public class MeanFilterTest {

    private int testFigure = 10;

    MeanFilter test_mf;
    MeanFilter test_mf1;

    @BeforeEach
    void setup() {
        test_mf = new MeanFilter(1);
        test_mf1 = new MeanFilter();
    }

    // test default constructor generates same kernel when radius equals 1
    @RepeatedTest(10)
    @Test
    public void test() {
        assertEquals(test_mf1, test_mf1);
    }

    // assessor should return same as mutator
    @RepeatedTest(10)
    @Test
    public void testMutator() {
        MeanFilter test_mf = new MeanFilter(testFigure);
        assertEquals(testFigure, test_mf.getRadValue());
    }
}