package cosc202.andie;

import java.io.Serializable;
import java.util.*;

/**
 * <p>
 * This interface describes a macro - a list of image operations combined into
 * one.
 * </p>
 * 
 * <p>
 * Hence, a macro is both an {@code ImageOperation} in itself, as well as being
 * an ordered list of "smaller" operations.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Mathias Øgaard
 */
public interface IMacro extends List<ImageOperation>, ImageOperation, Serializable {

    /**
     * <p>
     * Adds an operation to the macro.
     * </p>
     * 
     * <p>
     * The operation will be added to the end of the list of operations, not the
     * start.
     * </p>
     * 
     * @param op - The operation to add
     * @return {@code true} (as specified by {@link Collection#add})
     * @throws NullPointerException if {@code op} is null and this list does not
     *                              permit null elements
     */
    public default boolean addOp(ImageOperation op) {
        return add(op);
    }

    /**
     * <p>
     * Removes an operation from the macro, as described in
     * {@link List#remove(Object)}.
     * </p>
     * 
     * @param op - ImageOperation to remove
     * @return {@code true} if {@code op} was successfully identified and removed
     */
    public default boolean removeOp(ImageOperation op) {
        return remove(op);
    }

}
