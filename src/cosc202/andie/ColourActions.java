package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.HeadlessException;
import java.awt.Toolkit;

/**
 * <p>
 * Actions provided by the Colour menu.
 * </p>
 * 
 * <p>
 * The Colour menu contains actions that affect the colour of each pixel
 * directly
 * without reference to the rest of the image.
 * This includes conversion to greyscale in the sample code, but more operations
 * will need to be added.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Steven Mills (Modified by James Liu)
 * @version 1.0
 */
public class ColourActions {

    /** A list of actions for the Colour menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Colour menu actions.
     * </p>
     */
    public ColourActions() {
        actions = new ArrayList<Action>();
        actions.add(new ConvertToGreyAction(LanguageActions.getLocaleString("greyscale"), null,
                LanguageActions.getLocaleString("greyscaleDes"), Integer.valueOf(KeyEvent.VK_G)));
        actions.add(new BrightnessAction(LanguageActions.getLocaleString("brightness"), null,
                LanguageActions.getLocaleString("brightnessDes"), Integer.valueOf(KeyEvent.VK_B)));
        actions.add(new ContrastAction(LanguageActions.getLocaleString("contrast"), null,
                LanguageActions.getLocaleString("contrastDes"), Integer.valueOf(KeyEvent.VK_C)));
    }

    /**
     * <p>
     * Create a menu contianing the list of Colour actions.
     * </p>
     * 
     * @return The colour menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(LanguageActions.getLocaleString("colour"));

        for (Action action : actions) {
            JMenuItem item = new JMenuItem(action);
            item.setBorderPainted(false);
            fileMenu.add(item);
        }

        return fileMenu;
    }

    /**
     * <p>
     * Action to convert an image to greyscale.
     * </p>
     * 
     * @see ConvertToGrey
     */
    public class ConvertToGreyAction extends ImageAction {

        /**
         * <p>
         * Create a new convert-to-grey action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ConvertToGreyAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            this.putValue(Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_G, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ConvertToGreyAction is triggered.
         * It changes the image to greyscale.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                try {
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("greyscaleErr"),
                            LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse.
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            } else {
                // There is an image open, carry on.
                target.getImage().apply(new ConvertToGrey());
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }

    /**
     * <p>
     * Action to brighten an image.
     * </p>
     * 
     * @see BrightnessFilter
     */
    public class BrightnessAction extends ImageAction {

        /**
         * <p>
         * Create a new change brightness action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        BrightnessAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            this.putValue(Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_B, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        }

        /**
         * <p>
         * Callback for when the BrightnessAction is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the BrightnessFilter is triggered.
         * It changes the image's brightness depending on user input.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            try {
                if (target.getImage().hasImage() == false) {
                    // There is not an image open, so display error message.
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("brightnessErr"),
                            LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } else {
                    // There is an image open, carry on.
                    JSlider jslider = new JSlider();
                    jslider.setValue(0);
                    jslider.setMaximum(100);
                    jslider.setMinimum(-100);
                    jslider.setMajorTickSpacing(50);
                    jslider.setPaintLabels(true);
                    jslider.setPaintTicks(true);

                    // Copy this here so that we still have reference to the actual EditableImage.
                    EditableImage actualImage = target.getImage();

                    // This part updates how the image looks when the slider is moved.
                    jslider.addChangeListener(new ChangeListener() {
                        public void stateChanged(ChangeEvent ce) {
                            // Create a deep copy of the editable image (so that we don't change the actual
                            // editable image)
                            EditableImage copyImage = actualImage.deepCopyEditableImage();
                            // Set the target to have this new copy of the actual image.
                            target.setImage(copyImage);
                            // Apply the brightness change to the new copy of the actual image.
                            if (jslider.getValue() == 0) { // No change in brightness to apply.
                                return;
                            }
                            target.getImage().apply(new BrightnessFilter(jslider.getValue()));
                            target.repaint();
                            target.getParent().revalidate();
                        }
                    });

                    int select = JOptionPane.showOptionDialog(Andie.frame, jslider,
                            LanguageActions.getLocaleString("brightnessSlid"),
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (select == JOptionPane.CANCEL_OPTION || select == JOptionPane.CLOSED_OPTION) {
                        // Set the image in target back to the actual image and repaint.
                        target.setImage(actualImage);
                        target.repaint();
                        target.getParent().revalidate();
                        return;
                    }
                    if (select == JOptionPane.OK_OPTION) {
                        // Set the image in the target back to the actual image.
                        target.setImage(actualImage);
                        if (jslider.getValue() == 0) { // No change in brightness to apply.
                            return;
                        }
                        target.getImage().apply(new BrightnessFilter(jslider.getValue()));
                        target.repaint();
                        target.getParent().revalidate();
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
     * Action to change the contrast of an image.
     * </p>
     * 
     * @see ContrastFilter
     */
    public class ContrastAction extends ImageAction {

        /**
         * <p>
         * Create a new contrast action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ContrastAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            this.putValue(Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        }

        /**
         * <p>
         * Callback for when the contrast action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ContrastFilter is triggered.
         * It changes the image's contrast depending on user input.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            try {
                if (target.getImage().hasImage() == false) {
                    // There is not an image open, so display error message.
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("contrastErr"),
                            LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } else {
                    // There is an image open, carry on.
                    JSlider jslider = new JSlider();
                    jslider.setValue(0);
                    jslider.setMaximum(100);
                    jslider.setMinimum(-100);
                    jslider.setMajorTickSpacing(50);
                    jslider.setPaintLabels(true);
                    jslider.setPaintTicks(true);

                    // Copy this here so that we still have reference to the actual EditableImage.
                    EditableImage actualImage = target.getImage();

                    // This part updates how the image looks when the slider is moved.
                    jslider.addChangeListener(new ChangeListener() {
                        public void stateChanged(ChangeEvent ce) {
                            // Create a deep copy of the editable image (so that we don't change the actual
                            // editable image)
                            EditableImage copyImage = actualImage.deepCopyEditableImage();
                            // Set the target to have this new copy of the actual image.
                            target.setImage(copyImage);
                            // Apply the brightness change to the new copy of the actual image.
                            if (jslider.getValue() == 0) { // No change in contrast to apply.
                                return;
                            }
                            target.getImage().apply(new ContrastFilter(jslider.getValue()));
                            target.repaint();
                            target.getParent().revalidate();
                        }
                    });

                    int select = JOptionPane.showOptionDialog(Andie.frame, jslider,
                            LanguageActions.getLocaleString("contrastSlid"),
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (select == JOptionPane.CANCEL_OPTION || select == JOptionPane.CLOSED_OPTION) {
                        // Set the image in target back to the actual image and repaint.
                        target.setImage(actualImage);
                        target.repaint();
                        target.getParent().revalidate();
                        return;
                    }
                    if (select == JOptionPane.OK_OPTION) {
                        // Set the image in the target back to the actual image.
                        target.setImage(actualImage);
                        if (jslider.getValue() == 0) { // No change in contrast to apply.
                            return;
                        }
                        target.getImage().apply(new ContrastFilter(jslider.getValue()));
                        target.repaint();
                        target.getParent().revalidate();
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
