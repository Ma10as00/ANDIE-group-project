package cosc202.andie;

import java.io.Serializable;
import java.util.*;

/**
 * This interface describes a macro - a list of image operations combined into one.
 * <p>
 * Hence, a macro is both an {@code ImageOperation} in itself, as well as being an ordered list of "smaller" operations.
 * 
 * @author Mathias Øgaard
 */
public interface IMacro extends List<ImageOperation>, ImageOperation, Serializable{

    /**
     * Adds an operation to the macro. 
     * <p>
     * The operation will be added to the end of the list of operations, not the start.
     * @param op - The operation to add
     * @return {@code true} (as specified by {@link Collection#add})
     * @throws NullPointerException if {@code op} is null and this
     *         list does not permit null elements
     */
    public default boolean addOp(ImageOperation op){
        return add(op);
    }

    /**
     * Removes an operation from the macro, as described in {@link List#remove(Object)}.
     * @param op - ImageOperation to remove
     * @return {@code true} if {@code op} was successfully identified and removed
     */
    public default boolean removeOp(ImageOperation op){
        return remove(op);
    }
    
}
