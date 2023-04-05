package test.cosc202.andie;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import cosc202.andie.ResizeActions;

public class ResizeActions {
    int testFigure = 10;

    ResizeActions test_resize;

    @BeforeEach
    void setup() {
        test_resize = new ResizeActions();
        test_resize.resizePercent(testFigure);
    }

    @RepeatedTest(10)
    @Test
    void test_getSize() {
        assertEquals(testFigure, test_resize.getScale());
    }
}