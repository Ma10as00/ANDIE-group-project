package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.HeadlessException;
import java.awt.Toolkit;

/**
 * <p>
 * Actions provided by the Orientation menu.
 * </p>
 * 
 * <p>
 * The Orientation menu contains actions that affect the orentation of the image.
 * That is, whether it is flipped or rotated. 
 * </p>
 * 
 */
public class OrientationActions {

    /**
     * A list of actions for the Orientation menu.
     */
    protected ArrayList<Action> actions;

    /** An instance of RotateRightAction to be used in renderToolbar. */
    protected RotateRightAction rotateRightAction;

    /** An instance of RotateLeftAction to be used in renderToolbar. */
    protected RotateLeftAction rotateLeftAction;

    /**
     * <p>
     * Constructs the list of orientation actions.
     * </p>
     * 
     */
    public OrientationActions(){
        actions = new ArrayList<Action>();
        this.rotateRightAction = new RotateRightAction(LanguageActions.getLocaleString("rotateRight"), null, LanguageActions.getLocaleString("rotateRightDes"), Integer.valueOf(KeyEvent.VK_3));
        actions.add(this.rotateRightAction);
        this.rotateLeftAction = new RotateLeftAction(LanguageActions.getLocaleString("rotateLeft"), null, LanguageActions.getLocaleString("rotateLeftDes"), Integer.valueOf(KeyEvent.VK_4));
        actions.add(this.rotateLeftAction);
        actions.add(new Rotate180Action(LanguageActions.getLocaleString("rotate180"), null, LanguageActions.getLocaleString("rotate180Des"), Integer.valueOf(KeyEvent.VK_5)));
        actions.add(new FlipVertAction(LanguageActions.getLocaleString("flipVertically"), null, LanguageActions.getLocaleString("flipVerticallyDes"), Integer.valueOf(KeyEvent.VK_6)));
        actions.add(new FlipHorAction(LanguageActions.getLocaleString("flipHorizontally"), null, LanguageActions.getLocaleString("flipHorizontallyDes"), Integer.valueOf(KeyEvent.VK_7)));
    }

    /**
     * <p>
     * Create a menu containing the list of Orientation actions.
     * </p>
     * 
     * @return The orientation menu UI element.
     */
    public JMenu createMenu() {
        JMenu menu = new JMenu(LanguageActions.getLocaleString("orientation"));

        for (Action action: actions) {
            JMenuItem item = new JMenuItem(action);
            item.setBorderPainted(false);
            menu.add(item);
        }

        return menu;
    }

    /**
     * <p>
     * Accessor method to return RotateRightAction as a single action.
     * </p>
     * 
     * @return an instance of RotateRightAction.
     */
    public RotateRightAction getRotateRightAction() {
        return this.rotateRightAction;
    }

    /**
     * <p>
     * Action to rotate image 90 degrees to the right.
     * </p>
     * 
     * @see RotateRight
     */
    public class RotateRightAction extends ImageAction{
        /**
         * <p>
         * Create a new RotateRightAction action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        RotateRightAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name,icon,desc,mnemonic);
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_3, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        }      

        /**
         * <p>
         * Callback for when the RotateRight action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the RotateRight is triggered.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                try {
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("rotateRightErr"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            }
            else {
                // There is an image open, carry on.
                target.getImage().apply(new RotateRight());
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }

    /**
     * <p>
     * Accessor method to return RotateLeftAction as a single action.
     * </p>
     * 
     * @return an instance of RotateLeftAction.
     */
    public RotateLeftAction getRotateLeftAction() {
        return this.rotateLeftAction;
    }

    /**
     * <p>
     * Action to rotate image 90 degrees to the right.
     * </p>
     * 
     * @see RotateLeft
     */
    public class RotateLeftAction extends ImageAction{

        /**
         * <p>
         * Create a new RotateLeftAction action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        RotateLeftAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name,icon,desc,mnemonic);
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_4, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        }    

        /**
         * <p>
         * Callback for when the rotateLeft action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the RotateLeftAction is triggered.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                try {
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("rotateLeftErr"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            }
            else {
                // There is an image open, carry on.
                target.getImage().apply(new RotateLeft());
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }

    /**
     * <p>
     * Action to rotate image 90 degrees to the right.
     * </p>
     * 
     * @see Rotate180
     */
    public class Rotate180Action extends ImageAction{

        /**
         * <p>
         * Create a new Rotate180Action action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        Rotate180Action(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name,icon,desc,mnemonic);
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_5, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        } 

        /**
         * <p>
         * Callback for when the rotate180 action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the Rotate180 is triggered.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                try {
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("rotate180Err"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            }
            else {
                // There is an image open, carry on.
                target.getImage().apply(new Rotate180());
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }

    /**
     * <p>
     * Action to flip the image vertically.
     * </p>
     * 
     * @see FlipVertical
     */
    public class FlipVertAction extends ImageAction{

        /**
         * <p>
         * Create a new FlipVertAction action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FlipVertAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name,icon,desc,mnemonic);
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_6, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        }
        /**
         * <p>
         * Callback for when the FlipVertical action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FlipVertical is triggered.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                try {
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("flipVerErr"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            }
            else {
                // There is an image open, carry on.
                target.getImage().apply(new FlipVertical());
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }

    /**
     * <p>
     * Action to flip the image horizontally.
     * </p>
     * 
     * @see FlipHorizontal
     */
    public class FlipHorAction extends ImageAction{

        /**
         * <p>
         * Create a new FlipHorizontalAction action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FlipHorAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name,icon,desc,mnemonic);
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_7, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        }

        /**
         * <p>
         * Callback for when the FlipHorizontal action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FlipHorizontal is triggered.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                try {
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("flipHorErr"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            }
            else {
                // There is an image open, carry on.
                target.getImage().apply(new FlipHorizontal());
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }
}
