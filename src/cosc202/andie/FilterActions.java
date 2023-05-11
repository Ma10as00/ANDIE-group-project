package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.HeadlessException;
import java.awt.Toolkit;

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
        actions.add(new SharpenFilterAction(LanguageActions.getLocaleString("sharpen"), null, LanguageActions.getLocaleString("sharpenDes"), Integer.valueOf(KeyEvent.VK_P)));
        actions.add(new MeanFilterAction(LanguageActions.getLocaleString("meanFilter"), null, LanguageActions.getLocaleString("meanFilterDes"), Integer.valueOf(KeyEvent.VK_M)));
        actions.add(new MedianFilterAction(LanguageActions.getLocaleString("median"), null, LanguageActions.getLocaleString("medianDes"), Integer.valueOf(KeyEvent.VK_D)));
        actions.add(new GaussianBlurFilterAction(LanguageActions.getLocaleString("gaussian"), null, LanguageActions.getLocaleString("gaussianDes"), Integer.valueOf(KeyEvent.VK_N)));
        actions.add(new SobelHorizontalFilterAction(LanguageActions.getLocaleString("sobelHorizontal"), null, LanguageActions.getLocaleString("sobelHorizontalDes"), null));
        actions.add(new SobelVerticalFilterAction(LanguageActions.getLocaleString("sobelVertical"), null, LanguageActions.getLocaleString("sobelVerticalDes"), null));
        actions.add(new SobelOrientationFilterAction(LanguageActions.getLocaleString("sobelOrientation"), null, LanguageActions.getLocaleString("sobelOrientationlDes"), null));
        actions.add(new EmbossFilterAction(LanguageActions.getLocaleString("emboss"), null, LanguageActions.getLocaleString("embossDes"), null));
    }

    /**
     * <p>
     * Create a menu contianing the list of Filter actions.
     * </p>
     * 
     * @return The filter menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(LanguageActions.getLocaleString("filter"));

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
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_M, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        }

        /**
         * <p>
         * Callback for when the mean filter action is triggered.
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
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                try {
                    JOptionPane.showMessageDialog(null, LanguageActions.getLocaleString("meanErr"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            }
            else {
                // There is an image open, carry on.
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
                        if (jslider.getValue() == 0) { // No change to apply.
                            return;
                        }
                        target.getImage().apply(new MeanFilter(jslider.getValue()));
                        target.repaint();
                        target.getParent().revalidate();
                    }
                });

                // Ask user for radius value with slider.
                try {
                    int option = JOptionPane.showOptionDialog(null, jslider, LanguageActions.getLocaleString("meanSlid"),
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (option == JOptionPane.CANCEL_OPTION) {
                        // Set the image in target back to the actual image and repaint.
                        target.setImage(actualImage);
                        target.repaint();
                        target.getParent().revalidate();
                        return;
                    }
                    if (option == JOptionPane.CLOSED_OPTION) {
                        // Set the image in target back to the actual image and repaint.
                        target.setImage(actualImage);
                        target.repaint();
                        target.getParent().revalidate();
                        return;
                    }
                    if (option == JOptionPane.OK_OPTION) {
                        // Set the image in the target back to the actual image.
                        target.setImage(actualImage);
                        radius = jslider.getValue();
                    }
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
                if (radius == 0) { // No filter to apply.
                    return;
                }
                // Create and apply the filter.
                target.getImage().apply(new MeanFilter(radius));
                target.repaint();
                target.getParent().revalidate();
            }
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
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_P, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        }

        /**
         * <p>
         * Callback for when the sharpen filter action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the SharpenFilterAction is triggered.
         * It prompts the user for an amount to sharpen by, then applies the {@link SharpenFilter}
         * with that specified amount.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                try {
                    JOptionPane.showMessageDialog(null, LanguageActions.getLocaleString("sharpenErr"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            }
            else {
                // There is an image open, carry on.
                // Determine the amount - ask the user.
                int amount = 0;

                // Set up slider for user to enter amount.
                JSlider jslider = new JSlider();
                jslider.setValue(0);
                jslider.setMaximum(5);
                jslider.setMinimum(0);
                jslider.setMajorTickSpacing(1);
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
                        if (jslider.getValue() == 0) { // No change to apply.
                            return;
                        }
                        target.getImage().apply(new SharpenFilter(jslider.getValue()));
                        target.repaint();
                        target.getParent().revalidate();
                    }
                });

                // Ask user for radius value with slider.
                try {
                    int option = JOptionPane.showOptionDialog(null, jslider, LanguageActions.getLocaleString("sharpenSlid"),
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (option == JOptionPane.CANCEL_OPTION) {
                        // Set the image in target back to the actual image and repaint.
                        target.setImage(actualImage);
                        target.repaint();
                        target.getParent().revalidate();
                        return;
                    }
                    if (option == JOptionPane.CLOSED_OPTION) {
                        // Set the image in target back to the actual image and repaint.
                        target.setImage(actualImage);
                        target.repaint();
                        target.getParent().revalidate();
                        return;
                    }
                    if (option == JOptionPane.OK_OPTION) {
                        // Set the image in the target back to the actual image.
                        target.setImage(actualImage);
                        amount = jslider.getValue();
                    }
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
                if (amount == 0) { // No filter to apply.
                    return;
                }
                // Create and apply the filter.
                target.getImage().apply(new SharpenFilter(amount));
                target.repaint();
                target.getParent().revalidate();
            }
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
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        }

        /**
         * <p>
         * Callback for when the Gaussian blur filter action is triggered.
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
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                try { 
                    JOptionPane.showMessageDialog(null, LanguageActions.getLocaleString("gaussianErr"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            }
            else {
                // There is an image open, carry on.
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
                        if (jslider.getValue() == 0) { // No change to apply.
                            return;
                        }
                        target.getImage().apply(new GaussianBlurFilter(jslider.getValue()));
                        target.repaint();
                        target.getParent().revalidate();
                    }
                });

                // Ask user for radius value with slider.
                try {
                    int option = JOptionPane.showOptionDialog(null, jslider, LanguageActions.getLocaleString("gaussianSlid"),
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (option == JOptionPane.CANCEL_OPTION) {
                        // Set the image in target back to the actual image and repaint.
                        target.setImage(actualImage);
                        target.repaint();
                        target.getParent().revalidate();
                        return;
                    }
                    if (option == JOptionPane.CLOSED_OPTION) {
                        // Set the image in target back to the actual image and repaint.
                        target.setImage(actualImage);
                        target.repaint();
                        target.getParent().revalidate();
                        return;
                    }
                    if (option == JOptionPane.OK_OPTION) {
                        // Set the image in the target back to the actual image.
                        target.setImage(actualImage);
                        radius = jslider.getValue();
                    }
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
                if (radius == 0) { // No filter to apply.
                    return;
                }
                // Create and apply the filter.
                target.getImage().apply(new GaussianBlurFilter(radius));
                target.repaint();
                target.getParent().revalidate();
            }
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
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        }

        /**
         * <p>
         * Callback for when the median filter action is triggered.
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
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                try{
                    JOptionPane.showMessageDialog(null, LanguageActions.getLocaleString("medianErr"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            }
            else {
                // There is an image open, carry on.
                // Determine the radius - ask the user.
                int radius = 0;

                // Set up slider for user to enter radius.
                JSlider jslider = new JSlider();
                jslider.setValue(0);
                jslider.setMaximum(4);
                jslider.setMinimum(0);
                jslider.setMajorTickSpacing(1);
                jslider.setPaintLabels(true);
                jslider.setPaintTicks(true);

                // Copy this here so that we still have reference to the actual EditableImage.
                EditableImage actualImage = target.getImage();

                // This part updates how the image looks when the slider is moved.
                jslider.addChangeListener(new ChangeListener() {
                    // Used to keep track of the last value of the slider
                    private int lastValue = 0;
                    public void stateChanged(ChangeEvent ce) {
                        // Create a deep copy of the editable image (so that we don't change the actual editable image)
                        EditableImage copyImage = actualImage.deepCopyEditableImage();
                        // Set the target to have this new copy of the actual image.
                        target.setImage(copyImage);
                        // Apply the median filter to the new copy of the actual image.
                        if (jslider.getValue() == 0) { // No change to apply.
                            return;
                        }
                        // We only update the image if the difference between the last slider value and
                        // new slider value is greater than or equal to 1. Otherwise it is too laggy.
                        if (Math.abs(lastValue - jslider.getValue()) >= 1) {
                            target.getImage().apply(new MedianFilter(jslider.getValue()));
                            target.repaint();
                            target.getParent().revalidate();
                            // Update the last value.
                            lastValue = jslider.getValue();
                        }
                    }
                });

                // Ask user for radius value with slider.
                try {
                    int option = JOptionPane.showOptionDialog(null, jslider, LanguageActions.getLocaleString("medianRad"),
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (option == JOptionPane.CANCEL_OPTION) {
                        // Set the image in target back to the actual image and repaint.
                        target.setImage(actualImage);
                        target.repaint();
                        target.getParent().revalidate();
                        return;
                    }
                    if (option == JOptionPane.CLOSED_OPTION) {
                        // Set the image in target back to the actual image and repaint.
                        target.setImage(actualImage);
                        target.repaint();
                        target.getParent().revalidate();
                        return;
                    }
                    if (option == JOptionPane.OK_OPTION) {
                        // Set the image in the target back to the actual image.
                        target.setImage(actualImage);
                        radius = jslider.getValue();
                    }
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
                if (radius == 0) { // No filter to apply.
                    return;
                }
                // Create and apply the filter.
                target.getImage().apply(new MedianFilter(radius));
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }

    /**
     * <p>
     * Action to detect horizontal edges a sobel horizontal filter.
     * </p>
     * 
     * @see SobelHorizontalFilter
     */
    public class SobelHorizontalFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new sobel horizontal filter action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        SobelHorizontalFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_LEFT_PARENTHESIS, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        }

        /**
         * <p>
         * Callback for when the sobel horizontal filter action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the SobelHorizontalFilterAction is triggered.
         * It asks the user whether they want to get rid of noise, then applys the approprate {@link SobelHorizontalFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                try { 
                    JOptionPane.showMessageDialog(null, LanguageActions.getLocaleString("sobelErr"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            }
            else {
                // There is an image open, carry on.
                // Determine if the user wants to remove noise - ask the user.
                boolean removeNoise = false;
                // Ask user if they want to remove noise.
                try {
                    int option = JOptionPane.showOptionDialog(null, LanguageActions.getLocaleString("sobelQuestion"), LanguageActions.getLocaleString("sobelHorizontalTitle"),
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (option == JOptionPane.YES_OPTION) {
                        // The user wants to remove noise.
                        removeNoise = true;
                    }
                    if (option == JOptionPane.CLOSED_OPTION) {
                        // Do nothing, the user has cancelled the window.
                        return;
                    }
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
                // Create and apply the filter.
                target.getImage().apply(new SobelHorizontalFilter(removeNoise));
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }
    
    /**
     * <p>
     * Action to detect vertical edges with a sobel vertical filter.
     * </p>
     * 
     * @see SobelVerticalFilter
     */
    public class SobelVerticalFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new sobel vertical filter action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        SobelVerticalFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT_PARENTHESIS, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        }

        /**
         * <p>
         * Callback for when the sobel vertical filter action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the SobelVerticalFilterAction is triggered.
         * It asks the user whether they want to get rid of noise, then applys the approprate {@link SobelVerticalFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                try { 
                    JOptionPane.showMessageDialog(null, LanguageActions.getLocaleString("sobelErr"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            }
            else {
                // There is an image open, carry on.
                // Determine if the user wants to remove noise - ask the user.
                boolean removeNoise = false;
                // Ask user if they want to remove noise.
                try {
                    int option = JOptionPane.showOptionDialog(null, LanguageActions.getLocaleString("sobelQuestion"), LanguageActions.getLocaleString("sobelVericalTitle"),
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (option == JOptionPane.YES_OPTION) {
                        // The user wants to remove noise.
                        removeNoise = true;
                    }
                    if (option == JOptionPane.CLOSED_OPTION) {
                        // Do nothing, the user has cancelled the window.
                        return;
                    }
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
                // Create and apply the filter.
                target.getImage().apply(new SobelVerticalFilter(removeNoise));
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }

    /**
     * <p>
     * Action to detect edges their orientation with a sobel orientation filter.
     * </p>
     * 
     * @see SobelOrientationFilter
     */
    public class SobelOrientationFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new sobel orientation filter action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        SobelOrientationFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_SEMICOLON, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        }

        /**
         * <p>
         * Callback for when the sobel orientation filter action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the SobelOrientationlFilterAction is triggered.
         * It asks the user whether they want to get rid of noise, then applys the approprate {@link SobelOrientationFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                try { 
                    JOptionPane.showMessageDialog(null, LanguageActions.getLocaleString("sobelErr"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            }
            else {
                // There is an image open, carry on.
                // Determine if the user wants to remove noise - ask the user.
                boolean removeNoise = false;
                // Ask user if they want to remove noise.
                try {
                    int option = JOptionPane.showOptionDialog(null, LanguageActions.getLocaleString("sobelQuestion"), LanguageActions.getLocaleString("sobelOrientationTitle"),
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (option == JOptionPane.YES_OPTION) {
                        // The user wants to remove noise.
                        removeNoise = true;
                    }
                    if (option == JOptionPane.CLOSED_OPTION) {
                        // Do nothing, the user has cancelled the window.
                        return;
                    }
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
                // Create and apply the filter.
                target.getImage().apply(new SobelOrientationFilter(removeNoise));
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }

    /**
     * <p>
     * Action to emboss an image with an emboss filter.
     * </p>
     * 
     * @see EmbossFilter
     */
    public class EmbossFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new emboss filter action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        EmbossFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_COMMA, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        }

        /**
         * <p>
         * Callback for when the emboss filter action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the EmbossFilterAction is triggered.
         * It asks the user whether they want to get rid of noise, then applys the approprate {@link EmbossFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                try {
                    JOptionPane.showMessageDialog(null, LanguageActions.getLocaleString("embossErr"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            }
            else {
                // There is an image open, carry on.
                // Determine the embossType - ask the user.
                int embossType = 1;

                // Set up slider for user to enter amount.
                JSlider jslider = new JSlider();
                jslider.setValue(1);
                jslider.setMaximum(8);
                jslider.setMinimum(1);
                jslider.setMajorTickSpacing(1);
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
                        // Apply the emboss to the new copy of the actual image.
                        if (jslider.getValue() == 0) { // No change to apply.
                            return;
                        }
                        target.getImage().apply(new EmbossFilter(true, (int)jslider.getValue()));
                        target.repaint();
                        target.getParent().revalidate();
                    }
                });
                // Ask user for emboss type with a slider.
                try {
                    int option = JOptionPane.showOptionDialog(null, jslider, LanguageActions.getLocaleString("embossSlid"),
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (option == JOptionPane.CANCEL_OPTION) {
                        // Set the image in target back to the actual image and repaint.
                        target.setImage(actualImage);
                        target.repaint();
                        target.getParent().revalidate();
                        return;
                    }
                    if (option == JOptionPane.CLOSED_OPTION) {
                        // Set the image in target back to the actual image and repaint.
                        target.setImage(actualImage);
                        target.repaint();
                        target.getParent().revalidate();
                        return;
                    }
                    if (option == JOptionPane.OK_OPTION) {
                        // Set the image in the target back to the actual image.
                        target.setImage(actualImage);
                        embossType = (int)jslider.getValue();
                    }
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
                // Create and apply the filter. This will automatically get rid of noise.
                target.getImage().apply(new EmbossFilter(true, (int)jslider.getValue()));
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }

}
