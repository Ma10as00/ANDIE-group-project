package tests.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.beans.PropertyChangeListener;

import org.junit.Test;

import java.awt.*;
import java.awt.event.KeyEvent;

import cosc202.andie.*;

/**
 * Class for testing basic functionality of the {@link OperationRecorder} class.
 * @author Mathias Øgaard
 */
public class OperationRecorderTest {

    /** Robot acting as the user of the Andie-program */
    private Robot robot;

    /**
     * Initiates the GUI of Andie.
     */
    private void startAndie(){
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        try {
            Andie.createAndShowGUI();
            ImageAction.setTarget(Andie.imagePanel);
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
        openImage();

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
        System.out.println("Made it to here");
        startAndie();
        openImage();
        startRecording();

        //Retrieving the recorder from Andie:
        PropertyChangeListener[] pcls = Andie.imagePanel.getImage().getPropertyChangeListeners("ops");
        OperationRecorder rec = (OperationRecorder) pcls[0];
        //The recorder should have 0 recorded operations:
        assertTrue(rec.getOps().size() == 0); 

        //User makes image greyscale:
        greyScaleFilter();

        //The recorder should now have recorded an instance of ConvertToGrey:
        assertTrue(rec.getOps().size() == 1);
        String recordedOp = rec.getOps().get(0).getClass().getSimpleName();
        assertEquals("ConvertToGrey", recordedOp);
    }

    /**
     * Opening a picture in the ImagePanel of Andie.
     */
    private void openImage() {
        // Find path to the folder of this project, "..../andie-return-a-plus":
        String currentDirectory = System.getProperty("user.dir"); 
        // Add path within folder:
        String relativePath = "pictures/ANDIE_GUI.png"; 
        
        //This should now work on all computers, no matter where they this project stored:
        Andie.imagePanel.getImage().open(currentDirectory + relativePath);
    }

    /**
     * Mimicing a user initiating the "Start recording" action in the GUI.
     */
    private void startRecording() {
        //User presses Ctrl+8, the command for "Start recording"
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.delay(100);
        robot.keyPress(KeyEvent.VK_8);
        robot.delay(100);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.delay(100);
        robot.keyRelease(KeyEvent.VK_8);
        robot.delay(100);
    }

    /**
     * Mimicing a user initiating the "Greyscale" action in the GUI.
     */
    private void greyScaleFilter() {
        //User presses Ctrl+G, the command for "Greyscale"
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.delay(100);
        robot.keyPress(KeyEvent.VK_G);
        robot.delay(100);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.delay(100);
        robot.keyRelease(KeyEvent.VK_G);
        robot.delay(100);
    }
}
