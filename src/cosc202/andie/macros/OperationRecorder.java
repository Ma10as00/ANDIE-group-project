package cosc202.andie.macros;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.JOptionPane;

import cosc202.andie.*;
import cosc202.andie.EditActions.UndoAction;

/**
 * Implementation of {@link IOperationRecorder}, storing the operations in an ArrayList.
 * <p>
 * The propertyChange() method should be triggered every time the {@link EditableImage} is changed (In particular, every time its {@code ops} are extended).
 * <p>
 * The {@link ImageOperation} that was applied is then retrieved from the image, and added to the list of recorded operations.
 * <p>
 * NB: Note that the recorder will not be able to handle {@link UndoAction}s, because this changes {@link EditableImage#ops} without applying any new {@link ImageOperation}s.
 * 
 * @author Mathias Øgaard
 */
public class OperationRecorder implements IOperationRecorder{

    private ArrayList<ImageOperation> recordedOps;

    public OperationRecorder(){
        recordedOps = new ArrayList<>();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object newVal = evt.getNewValue();
        Object oldVal = evt.getOldValue();

        try {
            @SuppressWarnings("unchecked")
            Stack<ImageOperation> oldOps = (Stack<ImageOperation>) oldVal; // Is catched if cast fails
            @SuppressWarnings("unchecked") 
            Stack<ImageOperation> newOps = (Stack<ImageOperation>) newVal; // Is catched if cast fails
            if(!newOps.equals(oldOps)){ //Note that this will missguide the recorder if the user does an UndoAction. So the program should not allow the user to do so.
                recordedOps.add(newOps.peek());
            }
        } catch (ClassCastException e) {
            JOptionPane.showMessageDialog(null, "Failed to record because the changed values wasn't instances of Stack<ImageOperation>", LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public List<ImageOperation> getOps() {
        return recordedOps;
    }
}
