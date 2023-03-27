package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Colour menu.
 * </p>
 * 
 * <p>
 * The Colour menu contains actions that affect the colour of each pixel directly 
 * without reference to the rest of the image.
 * This includes conversion to greyscale in the sample code, but more operations will need to be added.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
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
        actions.add(new ConvertToGreyAction("Greyscale", null, "Convert to greyscale", Integer.valueOf(KeyEvent.VK_G)));
        actions.add(new BrightnessAction("Brightness", null, "Change Brightness", Integer.valueOf(KeyEvent.VK_B))); 
        actions.add(new ContrastFAction("Contrast", null, "Change Contrast", Integer.valueOf(KeyEvent.VK_C)));
    }

    /**
     * <p>
     * Create a menu contianing the list of Colour actions.
     * </p>
     * 
     * @return The colour menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("Colour");

        for(Action action: actions) {
            fileMenu.add(new JMenuItem(action));
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
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ConvertToGreyAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
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
            target.getImage().apply(new ConvertToGrey());
            target.repaint();
            target.getParent().revalidate();
        }
        
    public class BrightnessAction extends ImageAction{

        BrightnessAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        @Override
            public void actionPerformed(ActionEvent e) {

                JSlider jslider = new JSlider();
                jslider.setValue(0);
                jslider.setMaximum(10);
                jslider.setMinimum(-10);
                jslider.setMajorTickSpacing(2);
                jslider.setPaintLabels(true);
                jslider.setPaintTicks(true);
                
                int select = JOptionPane.showOptionDialog(null, jslider, "Brightness Amount",
                     JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                 if (select == JOptionPane.CANCEL_OPTION){
                        return;
                }
                    target.getImage().apply(new BrightnessFilter(jslider.getValue()));
                    target.repaint();
                    target.getParent().revalidate();
                }
        }
                        
    public class ContrastAction extends ImageAction{
        
            ContrastAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
                super(name, icon, desc, mnemonic);
                             
            }
            
            @Override
            public void actionPerformed(ActionEvent e) {
                JSlider jslider = new JSlider();
                jslider.setValue(0);
                jslider.setMaximum(10);
                jslider.setMinimum(-10);
                jslider.setMajorTickSpacing(2);
                jslider.setPaintLabels(true);
                jslider.setPaintTicks(true);
                
                int select = JOptionPane.showOptionDialog(null, jslider, "Contrast Amount",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (select == JOptionPane.CANCEL_OPTION){
                    return;
                }
                     target.getImage().apply(new ContrastFilter(jslider.getValue()));
                     target.repaint();
                     target.getParent().revalidate();
                }
                     
        }

    }

}
