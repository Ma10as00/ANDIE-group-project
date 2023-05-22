package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.HeadlessException;
import java.awt.Toolkit;

/**
 * <p>
 * Actions provided by the Edit menu.
 * </p>
 * 
 * <p>
 * The Edit menu is very common across a wide range of applications.
 * There are a lot of operations that a user might expect to see here.
 * In the sample code there are Undo and Redo actions, but more may need to be
 * added.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Steven Mills (Modified by Stella Srzich)
 * @version 1.0
 */
public class EditActions {

    /** A list of actions for the Edit menu. */
    protected ArrayList<Action> actions;

    /** An instance of UndoAction to be used in renderToolbar. */
    protected UndoAction undoAction;

    /** An instance of RedoAction to be used in renderToolbar. */
    protected RedoAction redoAction;

    /**
     * <p>
     * Create a set of Edit menu actions.
     * </p>
     * 
     */
    public EditActions() {
        actions = new ArrayList<Action>();
        this.undoAction = new UndoAction(LanguageActions.getLocaleString("undo"), null,
                LanguageActions.getLocaleString("undoDes"), Integer.valueOf(KeyEvent.VK_Z));
        this.redoAction = new RedoAction(LanguageActions.getLocaleString("redo"), null,
                LanguageActions.getLocaleString("redoDes"), Integer.valueOf(KeyEvent.VK_Y));
        actions.add(this.undoAction);
        actions.add(this.redoAction);
        actions.add(new UndoAllAction(LanguageActions.getLocaleString("undoAll"), null,
                LanguageActions.getLocaleString("undoAllDes"), Integer.valueOf(KeyEvent.VK_A)));
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

        for (Action action : actions) {
            JMenuItem item = new JMenuItem(action);
            item.setBorderPainted(false);
            editMenu.add(item);
        }

        return editMenu;
    }

    /**
     * <p>
     * Accessor method to return UndoAction as a single action.
     * </p>
     * 
     * @return an instance of UndoAction.
     */
    public UndoAction getUndoAction() {
        return this.undoAction;
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
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        UndoAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            this.putValue(Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
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
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("noImageToUndo"),
                            LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } else if (target.getImage().hasOps() == false) {
                    // There are no image operations to undo, so display error message.
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("noUndo"),
                            LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } else if (target.ongoingRecording) {
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("recordmacronoundo"),
                            LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } else {
                    // There is an image open, and operations to undo, carry on.
                    // Note, we are also checking if any of the undone operations was a resize or
                    // rotation
                    // to decide whether the frame should be packed.
                    target.getImage().undo();
                    target.repaint();
                    target.getParent().revalidate();
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
     * Action to undo all {@link ImageOperation}s.
     * </p>
     * 
     * @see EditableImage#undoAll()
     */
    public class UndoAllAction extends ImageAction {

        /**
         * <p>
         * Create a new undo all action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        UndoAllAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            this.putValue(Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        }

        /**
         * <p>
         * Callback for when the undo all action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the UndoAllAction is triggered.
         * It undoes all currenlty applied operations on an image. It also warns
         * the user in case they clicked it by mistake.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            try {
                if (target.getImage().hasImage() == false) {
                    // There is not an image undo, so display error message.
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("noImageToUndoAll"),
                            LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } else if (target.getImage().hasOps() == false) {
                    // There are no image operations to undo, so display error message.
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("noUndoAll"),
                            LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } else if (target.ongoingRecording) {
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("recordmacronoundo"),
                            LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } else {
                    // There is an image open, and operations to undo, carry on.
                    // Check that the user is sure they want to undo all operations.
                    int option = JOptionPane.showConfirmDialog(Andie.frame,
                            LanguageActions.getLocaleString("warningUndoAll"),
                            LanguageActions.getLocaleString("warning"), JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.WARNING_MESSAGE);
                    if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
                        // User cancelled or closed box, don't undo all.
                        return;
                    }
                    // There is an image open, and operations to undo, and the user has confirmed,
                    // so carry on.
                    // Note, we are also checking if the undone operation was a resize or rotation
                    // to decide whether the frame should be packed.
                    target.getImage().undoAll();
                    target.repaint();
                    target.getParent().revalidate();
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
     * Accessor method to return RedoAction as a single action.
     * </p>
     * 
     * @return an instance of RedoAction.
     */
    public RedoAction getRedoAction() {
        return this.redoAction;
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
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        RedoAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            this.putValue(Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
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
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("noImageToRedo"),
                            LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } else if (target.getImage().hasRedoOps() == false) {
                    // There are no image operations to undo, so display error message.
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("noRedo"),
                            LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } else if (target.ongoingRecording) {
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("recordmacroredo"),
                            LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } else {
                    // There is an image open, and operations to redo, carry on.
                    // Note, we are also checking if the redone operation was a resize or rotation
                    // to decide whether the frame should be packed.
                    target.getImage().redo();
                    target.repaint();
                    target.getParent().revalidate();
                }
            } catch (HeadlessException ex) {
                // Headless exception, thrown when the code is dependent on a keyboard or mouse.
                // Won't happen for our users, so just exit.
                System.exit(1);
            }
        }
    }

}
