package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

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
    
    /**
     * A list of actions for the View menu.
     */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of View menu actions.
     * </p>
     */
    public ViewActions() {
        actions = new ArrayList<Action>();
        actions.add(new ZoomInAction(LanguageActions.getLocaleString("zoomIn"), null, LanguageActions.getLocaleString("zoomInDes"), Integer.valueOf(KeyEvent.VK_PLUS)));
        actions.add(new ZoomOutAction(LanguageActions.getLocaleString("zoomOut"), null, LanguageActions.getLocaleString("zoomOutDes"), Integer.valueOf(KeyEvent.VK_MINUS)));
        actions.add(new ZoomFullAction(LanguageActions.getLocaleString("zoomFull"), null, LanguageActions.getLocaleString("zoomFullDes"), Integer.valueOf(KeyEvent.VK_1)));
        actions.add(new ZoomChangeAction(LanguageActions.getLocaleString("customZoom"), null, LanguageActions.getLocaleString("customZoomDes"), Integer.valueOf(KeyEvent.VK_2)));
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
            viewMenu.add(new JMenuItem(action));
        }

        return viewMenu;
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
                JOptionPane.showMessageDialog(null, "There is no image to zoom in on.", "Error", JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(null, "There is no image to zoom out on.", "Error", JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(null, "There is no image to custom zoom on.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                // There is an image open, carry on.
                // Determine the zoom change - ask the user.
                int change = 0;

                // Set up slider for user to the zoom change.
                JSlider jslider = new JSlider();
                jslider.setValue(0);
                jslider.setMaximum(150);
                jslider.setMinimum(-150);
                jslider.setMajorTickSpacing(50);
                jslider.setPaintLabels(true);
                jslider.setPaintTicks(true);

                // Ask user for zoom change value with slider.
                int option = JOptionPane.showOptionDialog(null, jslider, "Zoom Change",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                }
                if (option == JOptionPane.OK_OPTION) {
                    change = jslider.getValue();
                }

                // Apply changed zoom.
                target.setZoom(target.getZoom()+change);
                target.repaint();
                target.getParent().revalidate();
            }
        }

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
                JOptionPane.showMessageDialog(null, "There is no image to zoom full on.", "Error", JOptionPane.ERROR_MESSAGE);
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



}
