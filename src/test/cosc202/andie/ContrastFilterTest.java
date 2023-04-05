package test.cosc202.andie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import cosc202.andie.ContrastFilter;

class ContrastFilterTest {
    @RepeatedTest(10)
    @Test
    public void cfTest() {
        int test = 1;
        ContrastFilter cfTest = new ContrastFilter(test);
        assertEquals(test, cfTest.getConValue());
    }
}