package cosc202.andie.macros;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

import cosc202.andie.*;

/**
 * Implementation of {@link IOperationRecorder}, storing the operations in an ArrayList.
 * <p>
 * The propertyChange() method should be triggered every time the {@link EditableImage} is changed (In particular, every time its {@code ops} are extended).
 * <p>
 * The {@link ImageOperation} that was applied is then retrieved from the image, and added to the list of recorded operations.
 * 
 * @author Mathias Øgaard
 */
public class OperationRecorder extends ArrayList<ImageOperation> implements IOperationRecorder{

    private EditableImage image;

    public OperationRecorder(ImagePanel target){
        image = target.getImage();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        add(image.getLastOp());
    }

}
