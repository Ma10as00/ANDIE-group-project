package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.util.prefs.*;

/**
 * <p>
 * Actions provided by the View menu.
 * </p>
 * 
 * <p>
 * The View menu contains actions that affect how the image is displayed in the application.
 * These actions do not affect the contents of the image itself, just the way it is displayed.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills (Modified by Stella Srzich)
 * @version 1.0
 */
public class ViewActions {
    
    /** A list of actions for the View menu. */
    protected ArrayList<Action> actions;

    /** An instance of ZoomInAction to be used in renderToolbar. */
    protected ZoomInAction zoomInAction;

    /** An instance of ZoomOutAction to be used in renderToolbar. */
    protected ZoomOutAction zoomOutAction;

    /** An instance of ZoomFullAction to be used in renderToolbar. */
    protected ZoomFullAction zoomFullAction;

    /**
     * <p>
     * Create a set of View menu actions.
     * </p>
     */
    public ViewActions() {
        actions = new ArrayList<Action>();
        this.zoomInAction = new ZoomInAction(LanguageActions.getLocaleString("zoomIn"), null, LanguageActions.getLocaleString("zoomInDes"), Integer.valueOf(KeyEvent.VK_EQUALS));
        actions.add(this.zoomInAction);
        this.zoomOutAction = new ZoomOutAction(LanguageActions.getLocaleString("zoomOut"), null, LanguageActions.getLocaleString("zoomOutDes"), Integer.valueOf(KeyEvent.VK_MINUS));
        actions.add(this.zoomOutAction);
        this.zoomFullAction = new ZoomFullAction(LanguageActions.getLocaleString("zoomFull"), null, LanguageActions.getLocaleString("zoomFullDes"), Integer.valueOf(KeyEvent.VK_F));
        actions.add(this.zoomFullAction);
        actions.add(new ZoomChangeAction(LanguageActions.getLocaleString("customZoom"), null, LanguageActions.getLocaleString("customZoomDes"), Integer.valueOf(KeyEvent.VK_T)));
        actions.add(new DarkMode(LanguageActions.getLocaleString("darkMode"), null,
                LanguageActions.getLocaleString("darkmodedesc"), Integer.valueOf(KeyEvent.VK_M)));
    }

    /**
     * <p>
     * Create a menu containing the list of View actions.
     * </p>
     * 
     * @return The view menu UI element.
     */
    public JMenu createMenu() {
        JMenu viewMenu = new JMenu(LanguageActions.getLocaleString("view"));

        for (Action action: actions) {
            JMenuItem item = new JMenuItem(action);
            item.setBorderPainted(false);
            viewMenu.add(item);
        }

        return viewMenu;
    }

    /**
     * <p>
     * Accessor method to return ZoomInAction as a single action.
     * </p>
     * 
     * @return an instance of ZoomInAction.
     */
    public ZoomInAction getZoomInAction() {
        return this.zoomInAction;
    }

    /**
     * <p>
     * Action to zoom in on an image.
     * </p>
     * 
     * <p>
     * Note that this action only affects the way the image is displayed, not its actual contents.
     * </p>
     */
    public class ZoomInAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-in action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ZoomInAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        }

        /**
         * <p>
         * Callback for when the zoom-in action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ZoomInAction is triggered.
         * It increases the zoom level by 10%, to a maximum of 200%.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                try {
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("noZoomIn"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            }
            else {
                // There is an image open, carry on.
                target.setZoom(target.getZoom()+10);
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }

    /**
     * <p>
     * Accessor method to return ZoomOutAction as a single action.
     * </p>
     * 
     * @return an instance of ZoomOutAction.
     */
    public ZoomOutAction getZoomOutAction() {
        return this.zoomOutAction;
    }

    /**
     * <p>
     * Action to zoom out of an image.
     * </p>
     * 
     * <p>
     * Note that this action only affects the way the image is displayed, not its actual contents.
     * </p>
     */
    public class ZoomOutAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-out action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ZoomOutAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        }

        /**
         * <p>
         * Callback for when the zoom-iout action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ZoomOutAction is triggered.
         * It decreases the zoom level by 10%, to a minimum of 50%.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                try {
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("noZoomOut"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            }
            else {
                // There is an image open, carry on.
                target.setZoom(target.getZoom()-10);
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }

    /**
     * <p>
     * Action to change the zoom of an image with a slider.
     * </p>
     * 
     * <p>
     * Note that this action only affects the way the image is displayed, not its actual contents.
     * </p>
     */
    public class ZoomChangeAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-change action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ZoomChangeAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_T, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        }

        /**
         * <p>
         * Callback for when the zoom-change action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ZoomChangeAction is triggered.
         * It changes the zoom of the image by the user input.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                try {
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("noCustomZoom"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            }
            else {
                // There is an image open, carry on.
                // Need to keep track of the original zoom as the slider changes its value.
                double zoom = target.getZoom();
                final int oldZoom = (int) zoom;
                // Determine the zoom change - ask the user.

                // Set up slider for user to the zoom change.
                JSlider jslider = new JSlider();
                jslider.setMaximum(200);
                jslider.setMinimum(50);
                jslider.setMajorTickSpacing(50);
                jslider.setValue(oldZoom);
                jslider.setPaintLabels(true);
                jslider.setPaintTicks(true);

                // Copy this here so that we still have reference to the actual EditableImage.
                EditableImage actualImage = target.getImage();

                // This part updates how the image looks when the slider is moved.
                jslider.addChangeListener(new ChangeListener() {
                    public void stateChanged(ChangeEvent ce) {
                        // Create a deep copy of the editable image (so that we don't change the actual editable image)
                        EditableImage copyImage = actualImage.deepCopyEditableImage();
                        // Set the target to have this new copy of the actual image.
                        target.setImage(copyImage);
                        // Apply the brightness change to the new copy of the actual image.
                        if (jslider.getValue() == oldZoom) { // No change to apply.
                            return;
                        }
                        target.setZoom(jslider.getValue());
                        target.repaint();
                        target.getParent().revalidate();
                    }
                });

                // Ask user for zoom change value with slider.
                try {
                    int option = JOptionPane.showOptionDialog(Andie.frame, jslider, LanguageActions.getLocaleString("zoomChange"),
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
                        // Set the image in target back to the actual image and repaint.
                        target.setImage(actualImage);
                        // Reset the zoom value.
                        target.setZoom(zoom);
                        target.repaint();
                        target.getParent().revalidate();
                        return;
                    }
                    if (option == JOptionPane.OK_OPTION) {
                        // Set the image in the target back to the actual image.
                        target.setImage(actualImage);
                    }
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }

                // Apply changed zoom.
                target.setZoom(jslider.getValue());
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }

    /**
     * <p>
     * Accessor method to return ZoomFullAction as a single action.
     * </p>
     * 
     * @return an instance of ZoomFullAction.
     */
    public ZoomFullAction getZoomFullAction() {
        return this.zoomFullAction;
    }

    /**
     * <p>
     * Action to reset the zoom level to actual size.
     * </p>
     * 
     * <p>
     * Note that this action only affects the way the image is displayed, not its actual contents.
     * </p>
     */
    public class ZoomFullAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-full action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ZoomFullAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        }

        /**
         * <p>
         * Callback for when the zoom-full action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ZoomFullAction is triggered.
         * It resets the Zoom level to 100%.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                try {
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("noZoomFull"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            }
            else {
                // There is an image open, carry on.
                target.setZoom(100);
                // Note, the line below was changed from target.revalidate();, as it didin't
                // return the zoom to initial zoom level after some zoom changes
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }

    /**
     * <p>
     * Action to change view to dark mode (or back to light mode).
     * </p>
     * 
     * <p>
     * This allows the user to toggle between dark and light mode in ANDIE.
     * </p>
     * 
     */
    public class DarkMode extends ImageAction {

        /**
         * <p>
         * Create a new DarkMode.
         * </p>
         * 
         * <p>
         * This allows the user to toggle between dark and light mode in ANDIE.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        DarkMode(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            this.putValue(Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_M, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        }

        /**
         * <p>
         * Callback for when the dark mode action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the darkMode is triggered.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            Preferences prefs = Preferences.userNodeForPackage(Andie.class);
            prefs.remove("mode");
            if (Andie.darkMode) {
                prefs.put("mode", "light");
                Andie.darkMode = false;
            } else {
                prefs.put("mode", "dark");
                Andie.darkMode = true;
            }
            // This actually updates the mode.
            Andie.updateDarkMode();
        }
    }
}
