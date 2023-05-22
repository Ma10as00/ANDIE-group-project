package tests.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.beans.PropertyChangeListener;
import java.util.Stack;

import org.junit.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import cosc202.andie.*;

/**
 * Class for testing basic functionality of the {@link OperationRecorder} class.
 * @author Mathias Øgaard
 */
public class OperationRecorderTest {

    /** Robot acting as the user of the Andie-program */
    private Robot user;

    /**
     * Initiates the GUI of Andie, along with a robot user to interact with Andie.
     */
    private void startAndie(){
        //Constructing robot user to interact with Andie:
        try {
            user = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        //Initiating GUI of Andie, with a starting image:
        try {
            Andie.createAndShowGUI();
            ImageAction.setTarget(Andie.imagePanel);
            setImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests that the {@link cosc202.andie.MacroActions.StartRecordingAction} actually adds a {@link java.beans.PropertyChangeListener} to the image,
     * and that the field ongoingRecording is set to true.
     */
    @Test
    public void StartRecordingTest(){
        startAndie();

        //Should be no ongoing recordings yet:
        assertFalse(Andie.imagePanel.ongoingRecording);
        assertFalse(Andie.imagePanel.getImage().hasListeners("ops"));

        //User initiates a StartRecordingAction:
        startRecording();

        //Now, there should be a recording ongoing:
        System.out.println(Andie.imagePanel.ongoingRecording);
        assertTrue(Andie.imagePanel.ongoingRecording);
        assertTrue(Andie.imagePanel.getImage().hasListeners("ops"));
    }

    /**
     * Tests that the {@link cosc202.andie.OperationRecorder} actually records it when an operation is applied to the image.
     */
    @Test
    public void OperationIsRecordedTest(){
        startAndie();
        startRecording();

        //Retrieving the recorder from Andie:
        PropertyChangeListener[] pcls = Andie.imagePanel.getImage().getPropertyChangeListeners("ops");
        OperationRecorder rec = (OperationRecorder) pcls[0];
        //The recorder should have 0 recorded operations:
        assertEquals(0, rec.getOps().size()); 

        //User makes image greyscale:
        greyScaleFilter();

        //The recorder should now have recorded an instance of ConvertToGrey:
        assertEquals(1, rec.getOps().size());
        String recordedOp = rec.getOps().get(0).getClass().getSimpleName();
        assertEquals("ConvertToGrey", recordedOp);
    }

    /**
     * Passing an image of a red square to {@link Andie#imagePanel}.
     * <p>
     * This image is only for the purpose of testing how the program responds when a user applies {@link ImageOperation}s to it.
     */
    private void setImage() {
        //Constructing BufferedImage:
        int width = 100;
        int height = 100;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = image.getGraphics();
        //Coloring the image red, just to give it some content:
        g.setColor(Color.RED);
        g.fillRect(0, 0, width, height);
        g.dispose();

        //Constructing EditableImage:
        EditableImage edim = new EditableImage(
            image, 
            image,
            new Stack<ImageOperation>(), 
            new Stack<ImageOperation>(),
            new Stack<ImageOperation>(), 
            "",
            ".ops", 
            Andie.frame
        );
            
        //Passing constructed image to Andie's ImagePanel:
        Andie.imagePanel.setImage(edim);
    }

    /**
     * Mimicing a user initiating the "Start recording" action in the GUI.
     */
    private void startRecording() {
        //User presses Ctrl+8, the command for "Start recording"
        user.keyPress(KeyEvent.VK_CONTROL);
        user.delay(100);
        user.keyPress(KeyEvent.VK_8);
        user.delay(100);
        user.keyRelease(KeyEvent.VK_CONTROL);
        user.delay(100);
        user.keyRelease(KeyEvent.VK_8);
        user.delay(100);
    }

    /**
     * Mimicing a user initiating the "Greyscale" action in the GUI.
     */
    private void greyScaleFilter() {
        //User presses Ctrl+G, the command for "Greyscale"
        user.keyPress(KeyEvent.VK_CONTROL);
        user.delay(100);
        user.keyPress(KeyEvent.VK_G);
        user.delay(100);
        user.keyRelease(KeyEvent.VK_CONTROL);
        user.delay(100);
        user.keyRelease(KeyEvent.VK_G);
        user.delay(100);
    }
}
