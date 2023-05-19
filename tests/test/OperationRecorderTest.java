package tests.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.swing.Action;

import org.junit.Test;

import cosc202.andie.EditableImage;
import cosc202.andie.ImagePanel;
import cosc202.andie.MacroActions;

public class OperationRecorderTest {

    private ImagePanel panel;
    private MacroActions macroActions;
    
    private void setup(){
        panel = new ImagePanel();
        macroActions = new MacroActions(null);
        EditableImage image = new EditableImage();
        image.open("pictures/ANDIE_GUI.png");
        panel.setImage(image);
    }

    @Test
    public void StartRecordingTest(){
        setup();
        Action start = macroActions.actions.get(0);
        assertFalse(panel.ongoingRecording);
        assertFalse(panel.getImage().hasListeners("ops"));
        start.actionPerformed(null);
        assertTrue(panel.ongoingRecording);
        assertTrue(panel.getImage().hasListeners("ops"));
    }

    @Test
    public void OperationIsRecordedTest(){
        setup();
        Action start = macroActions.actions.get(0);
        start.actionPerformed(null);
        //TODO Unfinished test
    }
    
}
