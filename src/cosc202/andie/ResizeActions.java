package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

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
     * <p>
     * Create a set of Resize menu actions.
     * </p>
     */
    public ResizeActions() {
        actions = new ArrayList<Action>();
        actions.add(new ImageResize50Action("Resize 50%", null, "Resize image to 50% its original size", null));
        actions.add(new ImageResize150Action("Resize 150%", null, "Resize image to 150% its original size", null));
        actions.add(new ImageResizeNAction("Custom Resize", null, "Use slider to resize image", null));
    }

    /**
     * <p>
     * Create a menu contianing the list of Resize actions.
     * </p>
     * 
     * @return The Resize menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("Resize");

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
                JOptionPane.showMessageDialog(null, "There is no image to resize by 50%.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                // There is an image open, carry on.
                target.getImage().apply(new ImageResize50());
                target.repaint();
                target.getParent().revalidate();
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
                JOptionPane.showMessageDialog(null, "There is no image to resize by 150%.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                // There is an image open, carry on.
                target.getImage().apply(new ImageResize150());
                target.repaint();
                target.getParent().revalidate();
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
                JOptionPane.showMessageDialog(null, "There is no image to custom resize.", "Error", JOptionPane.ERROR_MESSAGE);
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

                // Ask user for resizePercent value with slider.
                int option = JOptionPane.showOptionDialog(null, jslider, "Resize Percent",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                }
                if (option == JOptionPane.OK_OPTION) {
                    resizePercent = jslider.getValue();
                }

                // Apply the image resize with n given from user.
                target.getImage().apply(new ImageResizeN(resizePercent));
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }
}
