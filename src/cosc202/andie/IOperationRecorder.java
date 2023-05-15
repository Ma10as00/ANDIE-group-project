package cosc202.andie;

import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * This interface describes a class that records what operations are applied to an image, and stores them in an ordered list.
 * <p>
 * The recorder only cares about the operations that changes the image ({@link ImageOperation}), and not what actions the user performs in the GUI ({@link ImageAction}).
 * e.g. if an {@link UndoAction} is made, this will not be detected, because no {@link ImageOperation}s are applied to the image.
 * <p>
 * When the image changes, the recorder should realize this, and add the operation that was done to the list.
 * In particular, the recorder should react every time {@link EditableImage#apply(ImageOperation)} is called. (Or every time the field {@code ops} is changed.)
 * <p>
 * For implementations of this interface to be able to track the ImageOperations, they need to be added in the ImagePanel ({@code target}), with the following line of code:
 * <p>
 * {@code target.addPropertyChangeListener("image", new OperationRecorder(target));}
 * <p>
 * The propertyChange() method will then be triggered every time the image changes.
 * This method should retrieve the last {@link ImageOperation} that was applied to the image, and add it to the list of recorded operations.
 * 
 * @author Mathias Øgaard
 */
public interface IOperationRecorder extends PropertyChangeListener {

    /**
     * @return the list of recorded operations
     */
    public List<ImageOperation> getOps();
}
