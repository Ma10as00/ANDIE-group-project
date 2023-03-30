package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Filter menu.
 * </p>
 * 
 * <p>
 * The Filter menu contains actions that update each pixel in an image based on
 * some small local neighbourhood. 
 * This includes a mean filter (a simple blur) in the sample code, a sharpen filter, 
 * a Gaussian blur filter (more natural blur), and a median blur (more blocky blur).
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills (Modified by Stella Srzich)
 * @version 1.0
 */
public class FilterActions {
    
    /** A list of actions for the Filter menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Filter menu actions.
     * </p>
     */
    public FilterActions() {
        actions = new ArrayList<Action>();
        actions.add(new MeanFilterAction("Mean Filter", null, "Apply a mean blur filter", Integer.valueOf(KeyEvent.VK_M)));
        actions.add(new SharpenFilterAction("Sharpen Filter", null, "Apply a sharpen filter", Integer.valueOf(KeyEvent.VK_S)));
        actions.add(new GaussianBlurFilterAction("Gaussian Blur Filter", null, "Apply a Gaussian blur filter", Integer.valueOf(KeyEvent.VK_G)));
        actions.add(new MedianFilterAction("Median Filter", null, "Apply a median blur filter", Integer.valueOf(KeyEvent.VK_D)));
    }

    /**
     * <p>
     * Create a menu contianing the list of Filter actions.
     * </p>
     * 
     * @return The filter menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("Filter");

        for(Action action: actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Action to blur an image with a mean filter.
     * </p>
     * 
     * @see MeanFilter
     */
    public class MeanFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new mean filter action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        MeanFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MeanFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized {@link MeanFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the radius - ask the user.
            int radius = 0;

            // Set up slider for user to enter radius.
            JSlider jslider = new JSlider();
            jslider.setValue(0);
            jslider.setMaximum(10);
            jslider.setMinimum(0);
            jslider.setMajorTickSpacing(1);
            jslider.setPaintLabels(true);
            jslider.setPaintTicks(true);

            // Ask user for radius value with slider.
            int option = JOptionPane.showOptionDialog(null, jslider, "Mean Filter Radius",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            }
            if (option == JOptionPane.OK_OPTION) {
                radius = jslider.getValue();
            }

            // Create and apply the filter.
            target.getImage().apply(new MeanFilter(radius));
            target.repaint();
            target.getParent().revalidate();
        }

    }

     /**
     * <p>
     * Action to sharpen an image with a sharpen filter.
     * </p>
     * 
     * @see SharpenFilter
     */
    public class SharpenFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new sharpen-filter action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        SharpenFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the SharpenFilterAction is triggered.
         * It applys the generic {@link SharpenFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Create and apply the filter.
            target.getImage().apply(new SharpenFilter());
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to blur an image with a Gaussian blur filter.
     * </p>
     * 
     * @see GaussianBlurFilter
     */
    public class GaussianBlurFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new Gaussian blur filter action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        GaussianBlurFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the GaussianBlurFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized {@link GaussianBlurFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the radius - ask the user.
            int radius = 0;

            // Set up slider for user to enter radius.
            JSlider jslider = new JSlider();
            jslider.setValue(0);
            jslider.setMaximum(10);
            jslider.setMinimum(0);
            jslider.setMajorTickSpacing(1);
            jslider.setPaintLabels(true);
            jslider.setPaintTicks(true);

            // Ask user for radius value with slider.
            int option = JOptionPane.showOptionDialog(null, jslider, "Gaussian Blur Filter Radius",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            }
            if (option == JOptionPane.OK_OPTION) {
                radius = jslider.getValue();
            }

            // Create and apply the filter.
            target.getImage().apply(new GaussianBlurFilter(radius));
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to blur an image with a median filter.
     * </p>
     * 
     * @see MedianFilter
     */
    public class MedianFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new median filter action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        MedianFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MedianFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized {@link MedianFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the radius - ask the user.
            int radius = 0;

            // Set up slider for user to enter radius.
            JSlider jslider = new JSlider();
            jslider.setValue(0);
            jslider.setMaximum(5);
            jslider.setMinimum(0);
            jslider.setMajorTickSpacing(1);
            jslider.setPaintLabels(true);
            jslider.setPaintTicks(true);

            // Ask user for radius value with slider.
            int option = JOptionPane.showOptionDialog(null, jslider, "Median Filter Radius",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            }
            if (option == JOptionPane.OK_OPTION) {
                radius = jslider.getValue();
            }

            // Create and apply the filter.
            target.getImage().apply(new MedianFilter(radius));
            target.repaint();
            target.getParent().revalidate();
        }

    }
}
