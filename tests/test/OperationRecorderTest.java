package tests.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.beans.PropertyChangeListener;

import javax.swing.*;

import org.junit.Test;

import cosc202.andie.*;

/**
 * Class for testing basic functionality of the {@link OperationRecorder} class.
 * @author Mathias Øgaard
 */
public class OperationRecorderTest {

    /**
     * Initiates the GUI of Andie.
     */
    private void startAndie(){
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

        //User clicks on the button for a StartRecordingAction:
        startRecording();

        //Now, there should be a recording ongoing:
        assertTrue(Andie.imagePanel.ongoingRecording);
        assertTrue(Andie.imagePanel.getImage().hasListeners("ops"));
    }

    /**
     * Opening a picture in the ImagePanel of Andie.
     */
    private void openImage() {
        // Should find a way to use this
        String relativePath = "pictures/ANDIE_GUI.png"; 
        // instead of this:
        String fullPath = "C:/COSC202/andie-return-a-plus/pictures/ANDIE_GUI.png"; 
        
        Andie.imagePanel.getImage().open(fullPath); // This will only work on Mathias' computer.
    }

    /**
     * Mimicing a user clicking on the "Start recording" button in the GUI.
     */
    private void startRecording() {
        JMenuItem startRecordingButton = Andie.frame.getJMenuBar().getMenu(7).getItem(0);
        startRecordingButton.doClick();
    }

    /**
     * Tests that the {@link cosc202.andie.OperationRecorder} actually records it when an operation is applied to the image.
     */
    @Test
    public void OperationIsRecordedTest(){
        System.out.println("Made it to here");
        startAndie();
        openImage(); //TODO This isn't working as it should right now
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
     * Mimicing a user clicking on the "Greyscale" button in the GUI.
     */
    private void greyScaleFilter() {
        JMenuItem greyScaleButton = Andie.frame.getJMenuBar().getMenu(6).getItem(0);
        greyScaleButton.doClick();
    }
    
}
