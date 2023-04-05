package test.cosc202.andie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import cosc202.andie.ContrastFilter;

class ContrastFilterTest {
    @RepeatedTest(10)
    @Test
    public void bfTest() {
        int test = 1;
        ContrastFilter bfTest = new ContrastFilter(test);
        assertEquals(test, bfTest.getConValue());
    }
}