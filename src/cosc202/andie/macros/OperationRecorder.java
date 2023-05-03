package cosc202.andie.macros;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

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
    private ImageOperation last;
    private ImagePanel panel;

    public OperationRecorder(ImagePanel target){
        recordedOps = new ArrayList<>();
        panel = target;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(opsWasChanged())
            recordedOps.add(getLastOp());
            last = getLastOp();    
    }

    /**
     * @return The last {@link ImageOperation} in {@link EditableImage#ops}
     */
    private ImageOperation getLastOp() {
        return panel.getImage().getLastOp();
    }

    /**
     * Checks if the {@link EditableImage}'s {@code ops} actually was changed. 
     * <p>
     * Note that this will missguide the recorder if the user does an {@link UndoAction}. So the program should not allow the user to do so.
     * 
     * @return {@code false} if the last recorded {@link ImageOperation} is still the last operation that was applied on the {@link EditableImage}. {@code true} othwerwise.
     */
    private boolean opsWasChanged() {
        return !(last == getLastOp());
    }

    @Override
    public List<ImageOperation> getOps() {
        return recordedOps;
    }
}
