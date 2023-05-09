package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.HeadlessException;

/**
 * <p>
 * Actions provided by the Reszie menu.
 * </p>
 * 
 * <p>
 * The Resize menu contains actions that resize the image by changing the number of pixels in
 * the image and their value. This includes a resize by 50% , a resize by 150%, and a resize by n%,
 * as specified by the user.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills (Modified by Michael Campbell and Stella Srzich)
 * @version 1.0
 */
public class ResizeActions {
    
    /** A list of actions for the Resize menu. */
    protected ArrayList<Action> actions;
    /** 
     * The main GUI frame. Only here so that we can pack the 
     * frame when we resize an image.
     */
    private JFrame frame;

    /**
     * <p>
     * Create a set of Resize menu actions.
     * </p>
     * 
     * @param frame the main GUI frame from which we will apply ResizeActions.
     */
    public ResizeActions(JFrame frame) {
        actions = new ArrayList<Action>();
        this.frame = frame;
        actions.add(new ImageResize50Action(LanguageActions.getLocaleString("resize50"), null, LanguageActions.getLocaleString("resize50Des"), null));
        actions.add(new ImageResize150Action(LanguageActions.getLocaleString("resize150"), null, LanguageActions.getLocaleString("resize150Des"), null));
        actions.add(new ImageResizeNAction(LanguageActions.getLocaleString("customResize"), null, LanguageActions.getLocaleString("customResizeDes"), null));
    }

    /**
     * <p>
     * Create a menu contianing the list of Resize actions.
     * </p>
     * 
     * @return The Resize menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(LanguageActions.getLocaleString("resize"));

        for(Action action: actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Action to resize an image by 50%.
     * </p>
     * 
     * @see ImageResize50
     */
    public class ImageResize50Action extends ImageAction{
        
        /**
         * <p>
         * Create a new image resize 50 action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ImageResize50Action(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name,icon,desc,mnemonic);
        }

        /**
         * <p>
         * Callback for when the image resize 50 action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ImageResize50Action is triggered.
         * It applys an {@link ImageResize50}.
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
                    JOptionPane.showMessageDialog(null, LanguageActions.getLocaleString("resize50Err"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            }
            else {
                // There is an image open, carry on.
                target.getImage().apply(new ImageResize50());
                target.repaint();
                target.getParent().revalidate();
                // Reset the zoom of the image.
                target.setZoom(100);
                // Pack the main GUI frame to the size of the image.
                frame.pack();
                // Make main GUI frame centered on screen.
                frame.setLocationRelativeTo(null);
            }
        }
    }

    /**
     * <p>
     * Action to resize an image by 150%.
     * </p>
     * 
     * @see ImageResize150
     */
    public class ImageResize150Action extends ImageAction{

        /**
         * <p>
         * Create a new image resize 150 action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ImageResize150Action(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name,icon,desc,mnemonic);
        }

        /**
         * <p>
         * Callback for when the image resize 150 action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ImageResize150Action is triggered.
         * It applys an {@link ImageResize150}.
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
                    JOptionPane.showMessageDialog(null, LanguageActions.getLocaleString("resize150Err"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            }
            else {
                // There is an image open, carry on.
                target.getImage().apply(new ImageResize150());
                target.repaint();
                target.getParent().revalidate();
                // Reset the zoom of the image.
                target.setZoom(100);
                // Pack the main GUI frame to the size of the image.
                frame.pack();
                // Make main GUI frame centered on screen.
                frame.setLocationRelativeTo(null);
            }
        }
    }

    /**
     * <p>
     * Action to resize an image by n%, given by user input.
     * </p>
     * 
     * @see ImageResizeN
     */
    public class ImageResizeNAction extends ImageAction{

        /**
         * <p>
         * Create a new image resize n action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ImageResizeNAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name,icon,desc,mnemonic);
        }

        /**
         * <p>
         * Callback for when the image resize n action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ImageResizeNAction is triggered.
         * It applys an {@link ImageResizeN}.
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
                    JOptionPane.showMessageDialog(null, LanguageActions.getLocaleString("resizeErr"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            }
            else {
                // There is an image open, carry on.
                // Determine the resize percent - ask the user.
                int resizePercent = 100;

                // Set up slider for user to enter resizePercent.
                JSlider jslider = new JSlider();
                jslider.setValue(100);
                jslider.setMaximum(200);
                jslider.setMinimum(50);
                jslider.setMajorTickSpacing(25);
                jslider.setPaintLabels(true);
                jslider.setPaintTicks(true);

                // Copy this here so that we still have reference to the actual EditableImage.
                EditableImage actualImage = target.getImage();
                // Need to keep track of the original zoom as the slider changes its value.
                double zoom = target.getZoom();

                // This part updates how the image looks when the slider is moved.
                jslider.addChangeListener(new ChangeListener() {
                    public void stateChanged(ChangeEvent ce) {
                        // Create a deep copy of the editable image (so that we don't change the actual editable image)
                        EditableImage copyImage = actualImage.deepCopyEditableImage();
                        // Set the target to have this new copy of the actual image.
                        target.setImage(copyImage);
                        // Apply the brightness change to the new copy of the actual image.
                        if (jslider.getValue() == 0) { // No change to apply.
                            return;
                        }
                        // Apply the image resize with n given from user.
                        target.getImage().apply(new ImageResizeN(jslider.getValue()));
                        target.repaint();
                        target.getParent().revalidate();
                        // Reset the zoom of the image.
                        target.setZoom(100);
                        // Pack the main GUI frame to the size of the image.
                        frame.pack();
                        // Make main GUI frame centered on screen.
                        frame.setLocationRelativeTo(null);
                    }
                });

                // Ask user for resizePercent value with slider.
                try {
                    int option = JOptionPane.showOptionDialog(null, jslider, LanguageActions.getLocaleString("resizeSlid"),
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
                        // Set the image in target back to the actual image and repaint.
                        target.setImage(actualImage);
                        target.repaint();
                        target.getParent().revalidate();
                        // Reset the zoom of the image.
                        target.setZoom(100);
                        // Pack the main GUI frame to the size of the image.
                        frame.pack();
                        // Make main GUI frame centered on screen.
                        frame.setLocationRelativeTo(null);
                        // Reset the zoom value.
                        target.setZoom(zoom);
                        target.repaint();
                        target.getParent().revalidate();
                        return;
                    }
                    if (option == JOptionPane.OK_OPTION) {
                        // Set the image in the target back to the actual image.
                        target.setImage(actualImage);
                        resizePercent = jslider.getValue();
                    }
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
                if (resizePercent == 100) { // No resize to apply.
                    return;
                }
                // Apply the image resize with n given from user.
                target.getImage().apply(new ImageResizeN(resizePercent));
                target.repaint();
                target.getParent().revalidate();
                // Reset the zoom of the image.
                target.setZoom(100);
                // Pack the main GUI frame to the size of the image.
                frame.pack();
                // Make main GUI frame centered on screen.
                frame.setLocationRelativeTo(null);
            }
        }
    }
}
