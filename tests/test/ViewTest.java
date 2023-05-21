package tests.test;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

import cosc202.andie.*;
import javax.swing.*;

/**
 * <p>
 * Tests to test that the view actions in 
 * {@link ViewActions} behave as we would expect.
 * </p>
 * 
 * @author Stella Srzich
 */
public class ViewTest {
    
    /**
     * <p>
     * Test to make sure that when an image is zoomed in by
     * {@link ZoomInAction}, it's view decreases by 10%.
     * But, its actual dimensions don't.
     * <\p>
     */
    @Test
    public void ZoomInTest() {
        ImagePanel imagePanel = new ImagePanel();
        double zoomBefore = imagePanel.getZoom();
        // Change the zoom as done in ZoomInAction.
        imagePanel.setZoom(imagePanel.getZoom()+10);
        double zoomAfter = imagePanel.getZoom();
        assertEquals(zoomBefore + 10d, zoomAfter, 0.1d);
    }

    /**
     * <p>
     * Test to make sure that when an image is zoomed in by
     * {@link ZoomInAction}, it's view increases by 10%.
     * But, its actual dimensions don't.
     * </p>
     */
    @Test
    public void ZoomOutTest() {
        ImagePanel imagePanel = new ImagePanel();
        double zoomBefore = imagePanel.getZoom();
        // Change the zoom as done in ZoomOutAction.
        imagePanel.setZoom(imagePanel.getZoom()-10);
        double zoomAfter = imagePanel.getZoom();
        assertEquals(zoomBefore - 10d, zoomAfter, 0.1d);
    }

    /**
     * <p>
     * Test to make sure that when an image is resized by
     * {@link ZoomChangeAction}, it's dimensions decrease by n%.
     * This is done for values of n.
     * </p>
     */
    @Test
    public void ZoomChangeTest() {
        // Repeat test for different zoom changes.
        for (double change = -100; change < 200; change+=10) {
            ImagePanel imagePanel = new ImagePanel();
            double zoomBefore = imagePanel.getZoom();
            // Change the zoom as done in ZoomInAction.
            imagePanel.setZoom(imagePanel.getZoom()+change);
            double zoomAfter = imagePanel.getZoom();
            double expected = zoomBefore + change;
            if (expected > 200) {
                expected = 200;
            }
            else if (expected < 50) {
                expected = 50;
            }
            assertEquals(expected, zoomAfter, 0.1d);
        }
    }
}

