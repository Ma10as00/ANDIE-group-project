package tests.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Stack;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import cosc202.andie.*;

/**
 * Class for testing basic functionality of the {@link OperationRecorder} class.
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Mathias Øgaard
 */
public class OperationRecorderTest {

    private EditableImage image;

    /**
     * Sets up an {@link ImagePanel} with an {@link EditableImage} to {@link Andie}.
     */
    private void setup(){
        Andie.imagePanel = new ImagePanel(Andie.frame);
        ImageAction.setTarget(Andie.imagePanel);
        image = setImage();
        
    }

    /**
     * Tests that an {@link OperationRecorder} can be successfully added as a {@link java.beans.PropertyChangeListener} to the image.
     */
    @Test
    public void AddRecorderTest(){
        setup();

        //Should be no ongoing recordings yet:
        assertFalse(image.hasListeners("ops"));

        // User initiates a StartRecordingAction:
        startRecording();

        //Now, there should be a recording ongoing:
        assertTrue(image.hasListeners("ops"));
    }

    /**
     * Tests that the {@link cosc202.andie.OperationRecorder} actually records it
     * when an operation is applied to the image.
     * 
     * @throws Exception if startAndie() failed in the current environment.
     */
    @Test
    public void OperationIsRecordedTest(){
        setup();
        OperationRecorder rec = startRecording();

        //The recorder should have 0 recorded operations:
        assertEquals(0, rec.getOps().size()); 

        //User makes image greyscale:
        image.apply(new ConvertToGrey());

        // The recorder should now have recorded an instance of ConvertToGrey:
        assertEquals(1, rec.getOps().size());
        String recordedOp = rec.getOps().get(0).getClass().getSimpleName();
        assertEquals("ConvertToGrey", recordedOp);
    }

    /** Tests that a recorder can be detatched from an image without loosing its recorded operations. */
    @Test
    public void RemoveRecorderTest(){
        setup();
        OperationRecorder rec = startRecording();

        //Image should have a Recorder listening:
        assertTrue(image.hasListeners("ops"));

        //Applying some operations to the image:
        image.apply(new ConvertToGrey());
        image.apply(new FlipVertical());

        //Recorder should have recorded 2 operations:
        assertEquals(2, rec.getOps().size());

        //Detaching recorder from image
        image.removePropertyChangeListener("ops", rec); 

        //Image should not have any listeners anymore:
        assertFalse(image.hasListeners("ops"));

        //Recorder should still hold 2 operations after being detached from image
        assertEquals(2, rec.getOps().size());
    }

    /**
     * Passing an image of a red square to {@link Andie#imagePanel}.
     * <p>
     * This image is only for the purpose of testing how the program (particularly the recorders) responds when a user applies {@link ImageOperation}s to it.
     * @return the image that was passed to {@link Andie#imagePanel}
     */
    private EditableImage setImage() {
        //Constructing BufferedImage:
        int width = 100;
        int height = 100;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = image.getGraphics();
        // Coloring the image red, just to give it some content:
        g.setColor(Color.RED);
        g.fillRect(0, 0, width, height);
        g.dispose();

        // Constructing EditableImage:
        EditableImage edim = new EditableImage(
                image,
                image,
                new Stack<ImageOperation>(),
                new Stack<ImageOperation>(),
                new Stack<ImageOperation>(),
                "",
                ".ops",
                Andie.frame);

        // Passing constructed image to Andie's ImagePanel:
        Andie.imagePanel.setImage(edim);
        return edim;
    }

    /**
     * Adding a new {@link OperationRecorder} to the image in {@link Andie#imagePanel}.
     * @return the recorder that was added
     */
    private OperationRecorder startRecording() {
        OperationRecorder rec = new OperationRecorder();
        image.addPropertyChangeListener("ops", rec);
        return rec;
    }
}
