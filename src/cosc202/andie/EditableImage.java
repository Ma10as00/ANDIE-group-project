package cosc202.andie;

import java.util.*;
import java.io.*;
import java.awt.image.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.awt.HeadlessException;
import javax.imageio.*;
import javax.swing.*;
import java.nio.file.*;

/**
 * <p>
 * An image with a set of operations applied to it.
 * </p>
 * 
 * <p>
 * The EditableImage represents an image with a series of operations applied to it.
 * It is fairly core to the ANDIE program, being the central data structure.
 * The operations are applied to a copy of the original image so that they can be undone.
 * THis is what is meant by "A Non-Destructive Image Editor" - you can always undo back to the original image.
 * </p>
 * 
 * <p>
 * Internally the EditableImage has two {@link BufferedImage}s - the original image 
 * and the result of applying the current set of operations to it. 
 * The operations themselves are stored on a {@link Stack}, with a second {@link Stack} 
 * being used to allow undone operations to be redone.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills (Modified by Stella Srzich, Katie Wink and Mathias Øgaard)
 * @version 1.0
 */
public class EditableImage {

    /** The original image. This should never be altered by ANDIE. */
    private BufferedImage original;
    /** The current image, the result of applying {@link ops} to {@link original}. */
    private BufferedImage current;
    /** The sequence of operations currently applied to the image. */
    private Stack<ImageOperation> ops;
    /** A memory of 'undone' operations to support 'redo'. */
    private Stack<ImageOperation> redoOps;
    /** A memory of the original operations that were in the saved .ops file.
     * This is used to determine if the image has been saved or not. */
    private Stack<ImageOperation> savedOps;
    /** The file where the original image is stored. */
    private String imageFilename;
    /** The file where the operation sequence is stored. */
    private String opsFilename;
    /** Support for recording {@link ImageOperation}s that is applied to this image. */
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    /** The main GUI frame. Only here so that we can update the title of the frame. */
    private JFrame frame;
    /** Keeps track of if this is just a dummy image used to preview actions */
    private boolean dummy;

    /**
     * <p>
     * Create a new EditableImage.
     * </p>
     * 
     * <p>
     * A new EditableImage has no image (it is a null reference), and an empty stack of operations.
     * </p>
     * @param frame The main frame of the GUI.
     */
    public EditableImage(JFrame frame) {
        original = null;
        current = null;
        ops = new Stack<ImageOperation>();
        redoOps = new Stack<ImageOperation>();
        savedOps = new Stack<ImageOperation>();
        imageFilename = null;
        opsFilename = null;
        this.frame = frame;
        dummy = false;
    }

    /**
     * <p>
     * Create a new EditableImage.
     * </p>
     * 
     * <p>
     * This constructor is only used within EditableImage (and in {@link tests.test.OperationRecorderTest}). It constructs a new EditableImage with the specified
     * parameters. This is used in {@link deepCopyEditableImage} to create a deep copy of this EditableImage. This should
     * not be used to create actual {@link EditableImage}s for {@link ImagePanel}s.
     * </p>
     * 
     * @param original The original image. This should never be altered by ANDIE.
     * @param current The current image, the result of applying {@link ops} to {@link original}.
     * @param ops The sequence of operations currently applied to the image.
     * @param redoOps A memory of 'undone' operations to support 'redo'.
     * @param savedOps The sequence of operations currently saved in the '.ops' file associated with this image image.
     * @param imageFilename The file where the original image is stored.
     * @param opsFilename The file where the operation sequence is stored.
     * @param frame The main frame of the GUI.
     */
    public EditableImage(BufferedImage original, BufferedImage current, Stack<ImageOperation> ops, Stack<ImageOperation> redoOps, Stack<ImageOperation> savedOps, String imageFilename, String opsFilename, JFrame frame) {
        this.original = original;
        this.current = current;
        this.ops = ops;
        this.redoOps = redoOps;
        this.savedOps = savedOps;
        this.imageFilename = imageFilename;
        this.opsFilename = opsFilename;
        this.frame = frame;
        dummy = true;
    }

    /**
     * <p>
     * Check if there is an image loaded.
     * </p>
     * 
     * @return True if there is an image, false otherwise.
     */
    public boolean hasImage() {
        return current != null;
    }

    /**
     * <p>
     * Check if there are operations in {@link ops}.
     * </p>
     * 
     * @return True if there is are operations, false otherwise.
     */
    public boolean hasOps() {
        return !ops.empty();
    }

    /**
     * <p>
     * Check if there are operations to redo in {@link redoOps}.
     * </p>
     * 
     * @return True if there is are operations, false otherwise.
     */
    public boolean hasRedoOps() {
        return !redoOps.empty();
    }

    /**
     * <p>
     * Make a 'deep' copy of a BufferedImage. 
     * </p>
     * 
     * <p>
     * Object instances in Java are accessed via references, which means that assignment does
     * not copy an object, it merely makes another reference to the original.
     * In order to make an independent copy, the {@code clone()} method is generally used.
     * {@link BufferedImage} does not implement {@link Cloneable} interface, and so the 
     * {@code clone()} method is not accessible.
     * </p>
     * 
     * <p>
     * This method makes a cloned copy of a BufferedImage.
     * This requires knoweldge of some details about the internals of the BufferedImage,
     * but essentially comes down to making a new BufferedImage made up of copies of
     * the internal parts of the input.
     * </p>
     * 
     * <p>
     * This code is taken from StackOverflow:
     * <a href="https://stackoverflow.com/a/3514297">https://stackoverflow.com/a/3514297</a>
     * in response to 
     * <a href="https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage">https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage</a>.
     * Code by Klark used under the CC BY-SA 2.5 license.
     * </p>
     * 
     * <p>
     * This method (only) is released under <a href="https://creativecommons.org/licenses/by-sa/2.5/">CC BY-SA 2.5</a>
     * </p>
     * 
     * @param bi The BufferedImage to copy.
     * @return A deep copy of the input.
     */
    private static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
    
    /**
     * <p>
     * Open an image from a file.
     * </p>
     * 
     * <p>
     * Opens an image from the specified file.
     * Also tries to open a set of operations from the file with <code>.ops</code> added.
     * So if you open <code>some/path/to/image.png</code>, this method will also try to
     * read the operations from <code>some/path/to/image.png.ops</code>.
     * </p>
     * 
     * @param filePath The file to open the image from.
     */
    @SuppressWarnings("unchecked")
    public void open(String filePath) {
        try {
            this.imageFilename = filePath;
            this.opsFilename = this.imageFilename + ".ops";
            File imageFile = new File(imageFilename);
            original = ImageIO.read(imageFile);
            current = deepCopy(original);
            // This clears the image operations, possibly from the prior open image.
            ops.clear();
            redoOps.clear();
            savedOps.clear();
        }
        catch (Exception e){
            // This will happen for various reasons. But, will not happen by the way it is set up.
            // So, just exit.
            System.exit(1);
        }
        // This part tries to also read the operations file
        // associated with the image that has been opened.
        // If it doesn't exist yet, no file is read.
        try {
            FileInputStream fileIn = new FileInputStream(this.opsFilename);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);

            // Silence the Java compiler warning about type casting.
            // Understanding the cause of the warning is way beyond
            // the scope of COSC202, but if you're interested, it has
            // to do with "type erasure" in Java: the compiler cannot
            // produce code that fails at this point in all cases in
            // which there is actually a type mismatch for one of the
            // elements within the Stack, i.e., a non-ImageOperation.
            Stack<ImageOperation> opsFromFile = (Stack<ImageOperation>) objIn.readObject();
            ops = opsFromFile;
            // Note, I have added this here so we can tell if the current edited image is saved.
            // i.e. if the currentOps matches the savedOps.
            savedOps = (Stack<ImageOperation>)ops.clone();
            objIn.close();
            fileIn.close();
        } catch (Exception ex) {
            // Could be no file or something else. Carry on for now.
        }
        this.refresh();
        // Make the image file name appear in the header of the main GUI.
        this.updateFrameTitle();
    }

    /**
     * <p>
     * Save an image to file.
     * </p>
     * 
     * <p>
     * Saves an image to the file it was opened from, or the most recent file saved as.
     * Also saves a set of operations from the file with <code>.ops</code> added.
     * So if you save to <code>some/path/to/image.png</code>, this method will also save
     * the current operations to <code>some/path/to/image.png.ops</code>.
     * </p>
     * 
     */
    @SuppressWarnings("unchecked")
    public void save() {
        if (this.opsFilename == null) {
            this.opsFilename = this.imageFilename + ".ops";
        }
        try {
            // Write image file based on file extension.
            String extension = imageFilename.substring(1+imageFilename.lastIndexOf(".")).toLowerCase();
            ImageIO.write(original, extension, new File(imageFilename));
            // Write operations file.
            FileOutputStream fileOut = new FileOutputStream(this.opsFilename);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
            objOut.writeObject(this.ops);
            objOut.close();
            fileOut.close();
            // Update the saved ops.
            savedOps = (Stack<ImageOperation>)ops.clone();
            // Make the image file name appear in the header of the main GUI.
            this.updateFrameTitle();
        }
        catch (Exception e) {
            // Something has gone wrong in saving the file. Tell the user and do nothing.
            JOptionPane.showMessageDialog(null, "Sorry, there has been an error in saving the file. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * <p>
     * Save an image to a speficied file.
     * </p>
     * 
     * <p>
     * Saves an image to the file provided as a parameter.
     * Also saves a set of operations from the file with <code>.ops</code> added.
     * So if you save to <code>some/path/to/image.png</code>, this method will also save
     * the current operations to <code>some/path/to/image.png.ops</code>.
     * </p>
     * 
     * @param imageFilename The file location to save the image to.
     */
    public void saveAs(String imageFilename) {
        this.imageFilename = imageFilename;
        this.opsFilename = imageFilename + ".ops";
        save();
    }

    /**
     * <p>
     * Exports image with operations to new file.
     * </p>
     * 
     * <p>
     * Exports an image to a file default extension is type .png 
     * Allows user to enter new name for the file and sets type as .png for default
     * </p>
     * 
     * @param imageFilename the new file name that image will get exported to.
     */
    public void export(String imageFilename) {
        // Deleted the code line below so that once you export an image, you are still working with the original image
        // with the original image opertaions file. This felt more natural.
        // this.imageFilename = imageFilename; //sets file name based on export method in FileActions
        try {
            // Find extension of the file. In all cases this will be png.
            String extension = imageFilename.substring(1+imageFilename.lastIndexOf(".")).toLowerCase();
            // Writes image to file using ImageIO.
            ImageIO.write(current, extension, new File(imageFilename));  
        } catch (Exception e) {
            // This will not happen by the way we have set it up.
            // But, occurs if the file name is null, or if there is an error in writting to the file.
            // So, just in case, let the user know there was an issue and do nothing.
            try {
                JOptionPane.showMessageDialog(null, "Sorry, there has been an error in exporting the file.", "Error", JOptionPane.ERROR_MESSAGE);
            }   
            catch (HeadlessException eh) {
                // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                // Won't happen for our users, so just exit.
                System.exit(1);
            }
        }
    }

    /**
     * <p>
     * Apply an {@link ImageOperation} to this image. This also updates the header of the main
     * GUI frame to refelect whether the current operations applied match those saved in an
     * associated .ops file.
     * </p>
     * 
     * @param op The operation to apply.
     */
    public void apply(ImageOperation op) {
        current = op.apply(current);
        @SuppressWarnings("unchecked")
        Stack<ImageOperation> oldOps = (Stack<ImageOperation>) ops.clone();
        ops.add(op);
        // For PropertyChangeListeners:
        propertyChangeSupport.firePropertyChange("ops", oldOps, ops);
        // Make the image file name appear in the header of the main GUI (particularily, if it is saved).
        this.updateFrameTitle();
    }

    /** 
     * <p>
     * Method to add a specific Listener to this {@link EditableImage}.
     * </p>
     * 
     * @param listener The listener to add to this image.
     * @param propertyName The property to which the listener is added, should be "ops" for our purpose.
     * @see PropertyChangeSupport#addPropertyChangeListener(String, PropertyChangeListener) 
     * @author Mathias Øgaard
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener){
        propertyChangeSupport.addPropertyChangeListener(propertyName,listener);
    }

    /** 
     * <p>
     * Method to remove a specific Listener from this {@link EditableImage}.
     * </p>
     * 
     * @param listener The listener to remove from this image
     * @param propertyName The property from which the listener is removed, should be "ops" for our purpose
     * @see PropertyChangeSupport#removePropertyChangeListener(String, PropertyChangeListener)  
     * @author Mathias Øgaard
     */
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener){
        propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
    }

    /** 
     * <p>
     * Method to get this {@link EditableImage}'s' currently Listeners.
     * </p>
     * 
     * @param propertyName The property to retrieve listeners from, should be "ops" for our purpose.
     * @return An array of all listeners for the given property.
     * @see PropertyChangeSupport#getPropertyChangeListeners(String)  
     * @author Mathias Øgaard
     */
    public PropertyChangeListener[] getPropertyChangeListeners(String propertyName){
        return propertyChangeSupport.getPropertyChangeListeners(propertyName);
    }

    /** 
     * <p>
     * Method to check whether this {@link EditableImage} currently has Listeners.
     * </p>
     * 
     * @param propertyName The property to search for listeners on, should be "ops" for our purpose.
     * @return True if there are one or more listeners for the given property.
     * @see PropertyChangeSupport#hasListeners(String)  
     * @author Mathias Øgaard
     */
    public boolean hasListeners(String propertyName){
        return propertyChangeSupport.hasListeners(propertyName);
    }

    /**
     * <p>
     * Undo the last {@link ImageOperation} applied to the image.
     * Also tells you if the undone image operation was a resize or rotation by returing 1, 
     * or that it wasn't by returning 0.
     * </p>
     * 
     * <p>
     * Note, this functionality is not really used in ANDIE anymore as the design choice
     * was made to not repack the frame after image operations occur that change the
     * dimensions of an image. However, it has been left as it still may be useful 
     * for future purposes.
     * </p>
     * 
     * @return 1 if the undone operation was a resize or rotation, 0 otherwise.
     */
    public int undo() {
        // int to tell us if the redone operation was a resize.
        int resizeOrRotate = 0;
        ImageOperation un = ops.pop();
        if (un instanceof ImageResize50 || un instanceof ImageResize150 || un instanceof ImageResizeN || un instanceof RotateRight || un instanceof RotateLeft || un instanceof IMacro || un instanceof RegionCrop) {
            resizeOrRotate = 1;
        }
        redoOps.push(un); 
        refresh();
        return resizeOrRotate;
    }

    /**
     * <p>
     * Undo all {@link ImageOperation}s currenlty applied to the image.
     * Also tells you if the undone image operation was a resize or rotation by returing 1, 
     * or that it wasn't by returning 0.
     * </p>
     *
     * <p>
     * Note, this functionality is not really used in ANDIE anymore as the design choice
     * was made to not repack the frame after image operations occur that change the
     * dimensions of an image. However, it has been left as it still may be useful 
     * for future purposes.
     * </p>
     * 
     * @return 1 if any of the undone operations was a resize or rotation, 0 otherwise.
     */
    public int undoAll() {
        // int to tell us if the redone operation was a resize.
        int resizeOrRotate = 0;
        while(this.hasOps()) { // Keep undoing until all operations are gone
            int r = this.undo(); // Undoes the operation
            if (r == 1) { // If there is a single resize or rotation that was undone, we keep track of it
                resizeOrRotate = 1;
            }
        }
        refresh(); 
        return resizeOrRotate;
    }

    /**
     * <p>
     * Reapply the most recently {@link undo}ne {@link ImageOperation} to the image.
     * Also tells you if the redone image operation was a resize or rotation by returing 1, 
     * or that it wasn't by returning 0.
     * </p>
     * 
     * <p>
     * Note, this functionality is not really used in ANDIE anymore as the design choice
     * was made to not repack the frame after image operations occur that change the
     * dimensions of an image. However, it has been left as it still may be useful 
     * for future purposes.
     * </p>
     * 
     * @return 1 if the redone operation was a resize or rotation, 0 otherwise.
     */
    public int redo()  {
        // int to tell us if the redone operation was a resize.
        int resizeOrRotate = 0;
        ImageOperation re = redoOps.pop();
        // If the image operation was a resize operation, return 1.
        if (re instanceof ImageResize50 || re instanceof ImageResize150 || re instanceof ImageResizeN || re instanceof RotateRight || re instanceof RotateLeft || re instanceof IMacro || re instanceof RegionCrop) {
            resizeOrRotate = 1;
        }
        apply(re);
        refresh();
        return resizeOrRotate;
    }

    /**
     * <p>
     * Method to help an {@code IOperationRecorder} to see the last 
     * operation that was applied to the image.
     * </p>
     * 
     * @return The last {@link ImageOperation} that was applied
     * @author Mathias Øgaard
     */
    public ImageOperation getLastOp(){
        return ops.peek();
    }

    /**
     * <p>
     * Method to get the stack of operations currently stored in {@link ops}.
     * </p>
     * 
     * @return The stack of operations for this EditableImage.
     */
    public Stack<ImageOperation> getOps(){
        return ops;
    }

    /**
     * <p>
     * Get the current image after the current operations have been applied.
     * </p>
     * 
     * @return The result of applying all of the current operations to the {@link original} image.
     */
    public BufferedImage getCurrentImage() {
        return current;
    }

    /**
     * <p>
     * Reapply the current list of operations to the original.
     * </p>
     * 
     * <p>
     * While the latest version of the image is stored in {@link current}, this
     * method makes a fresh copy of the original and applies the operations to it in sequence.
     * This is useful when undoing changes to the image, or in any other case where {@link current}
     * cannot be easily incrementally updated. 
     * </p>
     * 
     */
    private void refresh()  {
        current = deepCopy(original);
        for (ImageOperation op: ops) {
            current = op.apply(current);
        }
        // Update the title of the main frame of the GUI.
        updateFrameTitle();
    }

    /**
     * <p>
     * Method to create and return a reference to a deep copy of this {@link EditableImage}.
     * </p>
     * 
     * <p>
     * Note, this essentially creates a new EditableImage instance with
     * the behaviour as this EditableImage. That is, the new EditableImage 
     * will have data feilds that are different to the data feilds of
     * this EditableImage, i.e. different references. But, the values within
     * the objects of the data feilds will be the same. 
     * </p>
     * 
     * <p>
     * This is only really to be used for the purpose of previewing {@link ImageOperation}s.
     * </p>
     * 
     * @return a reference to a deep copy of this {@link EditableImage}.
     */
    @SuppressWarnings("unchecked")
    public EditableImage deepCopyEditableImage()  {
        // Create deep copies of all data feilds.
        BufferedImage origin = deepCopy(original);
        BufferedImage curr = deepCopy(current);
        Stack<ImageOperation> o = (Stack<ImageOperation>)ops.clone();
        Stack<ImageOperation> r = (Stack<ImageOperation>)redoOps.clone();
        Stack<ImageOperation> s = (Stack<ImageOperation>)savedOps.clone();
        String ifn = imageFilename; // Strings are immutable, do don't need to clone
        String ofn = opsFilename;
        // Construct a new editable image.
        EditableImage copy = new EditableImage(origin, curr, o, r, s, ifn, ofn, frame);
        // Return new editable image.
        return copy;
    }

    /**
     * <p>
     * Method to determine if the operations currently applied to this EditableImage
     * match the saved operations in the associated .ops file.
     * </p>
     * 
     * <p>
     * This works by checking if the savedOps match exactly (in order and instance) the 
     * operations currently applied to this editable image.
     * </p>
     * 
     * @return True if the operations saved in the associated .ops file matches the operations currently
     * applied to this image, false otherwise.
     */
    public boolean opsSaved() {
        // Create lists to iterate over.
        ArrayList<ImageOperation> opsList = new ArrayList<ImageOperation>(ops);
        ArrayList<ImageOperation> savedOpsList = new ArrayList<ImageOperation>(savedOps);
        // Check they are both the same size. If not, they will not be the same.
        if (opsList.size() != savedOpsList.size()) {
            return false;
        }
        // Now, iterate over list to make sure they are the same.
        for (int i = 0; i < opsList.size(); i++) {
            if (opsList.get(i) != savedOpsList.get(i)) {
                return false;
            }
        }
        // If we are here, all image operations are the same ref. and are in the same order.
        // So, return true.
        return true;
    }

    /**
     * <p>
     * This method is used to update the title of the main GUI frame to contain the 
     * name of the image file open, and whether or not it is saved. This will look like
     * "ANDIE (imagefilename.png) - Saved" or "ANDIE (imagefilename.png) - Unsaved".
     * </p>
     * 
     * <p>
     * Note, this only applies to images that are not used to preview the results 
     * of {@link ImageAction}s. That is, only to the actual image opened (not 
     * deep copies of {@link EditableImage}s).
     * </p>
     * 
     */
    public void updateFrameTitle() {
        if (!dummy) {
            Path imagePath = Paths.get(imageFilename);
            String justFilename = imagePath.getFileName().toString();
            if (this.opsSaved()) {
                // The image operations applied match those saved.
                frame.setTitle("ANDIE (" + justFilename + ") - " + LanguageActions.getLocaleString("saved"));
            }
            else {
                // The image operations applied do not match those saved.
                frame.setTitle("ANDIE (" + justFilename + ") - " + LanguageActions.getLocaleString("unsaved"));
            }
        }
    }
}
