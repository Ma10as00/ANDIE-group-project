package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.HeadlessException;

 /**
 * <p>
 * Actions provided by the Edit menu.
 * </p>
 * 
 * <p>
 * The Edit menu is very common across a wide range of applications.
 * There are a lot of operations that a user might expect to see here.
 * In the sample code there are Undo and Redo actions, but more may need to be added.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class EditActions {
    
    /** A list of actions for the Edit menu. */
    protected ArrayList<Action> actions;
    /** 
     * The main GUI frame. Only here so that we can pack the 
     * frame when we undo or redo operations to an image.
     */
    private JFrame frame;

    /**
     * <p>
     * Create a set of Edit menu actions.
     * </p>
     * 
     * * @param frame the main GUI frame from which we will apply FileActions.
     */
    public EditActions(JFrame frame) {
        actions = new ArrayList<Action>();
        this.frame = frame;
        actions.add(new UndoAction(LanguageActions.getLocaleString("undo"), null, LanguageActions.getLocaleString("undoDes"), Integer.valueOf(KeyEvent.VK_Z)));
        actions.add(new RedoAction(LanguageActions.getLocaleString("redo"), null, LanguageActions.getLocaleString("redoDes"), Integer.valueOf(KeyEvent.VK_Y)));
    }

    /**
     * <p>
     * Create a menu contianing the list of Edit actions.
     * </p>
     * 
     * @return The edit menu UI element.
     */
    public JMenu createMenu() {
        JMenu editMenu = new JMenu(LanguageActions.getLocaleString("edit"));

        for (Action action: actions) {
            editMenu.add(new JMenuItem(action));
        }

        return editMenu;
    }

    /**
     * <p>
     * Action to undo an {@link ImageOperation}.
     * </p>
     * 
     * @see EditableImage#undo()
     */
    public class UndoAction extends ImageAction {

        /**
         * <p>
         * Create a new undo action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        UndoAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the undo action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the UndoAction is triggered.
         * It undoes the most recently applied operation.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            try {
                if (target.getImage().hasImage() == false) {
                    // There is not an image undo, so display error message.
                    JOptionPane.showMessageDialog(null, LanguageActions.getLocaleString("noImageToUndo"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                }
                else if (target.getImage().hasOps() == false) {
                    // There are no image operations to undo, so display error message.
                    JOptionPane.showMessageDialog(null, LanguageActions.getLocaleString("noUndo"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                }
                else {
                    // There is an image open, and operations to undo, carry on.
                    // Note, we are also checking if the undone operation was a resize
                    // to decide whether the frame should be packed.
                    int resize = target.getImage().undo();
                    target.repaint();
                    target.getParent().revalidate();
                    if (resize == 1) {
                        // The undone operation was a resize.
                        // Reset the zoom of the image.
                        target.setZoom(100);
                        // Pack the main GUI frame to the size of the image.
                        frame.pack();
                        // Make main GUI frame centered on screen.
                        frame.setLocationRelativeTo(null);
                    }
                }
            } catch (HeadlessException ex) {
                // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                // Won't happen for our users, so just exit.
                System.exit(1);
            }
        }
    }

     /**
     * <p>
     * Action to redo an {@link ImageOperation}.
     * </p>
     * 
     * @see EditableImage#redo()
     */   
    public class RedoAction extends ImageAction {

        /**
         * <p>
         * Create a new redo action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        RedoAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }
        
        /**
         * <p>
         * Callback for when the redo action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the RedoAction is triggered.
         * It redoes the most recently undone operation.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            try {
                if (target.getImage().hasImage() == false) {
                    // There is not an image open to undo, so display error message.
                    JOptionPane.showMessageDialog(null, LanguageActions.getLocaleString("noImageToRedo"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                }
                else if (target.getImage().hasRedoOps() == false) {
                    // There are no image operations to undo, so display error message.
                    JOptionPane.showMessageDialog(null, LanguageActions.getLocaleString("noRedo"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                }
                else {
                    // There is an image open, and operations to redo, carry on.
                    // Note, we are also checking if the redone operation was a resize
                    // to decide whether the frame should be packed.
                    int resize = target.getImage().redo();
                    target.repaint();
                    target.getParent().revalidate();
                    if (resize == 1) {
                        // The redone operation was a resize.
                        // Reset the zoom of the image.
                        target.setZoom(100);
                        // Pack the main GUI frame to the size of the image.
                        frame.pack();
                        // Make main GUI frame centered on screen.
                        frame.setLocationRelativeTo(null);
                    }
                }
            } catch (HeadlessException ex) {
                // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                // Won't happen for our users, so just exit.
                System.exit(1);
            }
        }
    }
}
