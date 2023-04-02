package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class OrientationActions {

    /**
     * A list of actions for the Orientation menu.
     */
    protected ArrayList<Action> actions;

    /**
     * Constructs the list of orientation actions.
     */
    public OrientationActions(){
        actions = new ArrayList<Action>();
        actions.add(new RotateRightAction(LanguageActions.getLocaleString("rotateRight"), null, LanguageActions.getLocaleString("rotateRightDes"), Integer.valueOf(KeyEvent.VK_3)));
        actions.add(new RotateLeftAction(LanguageActions.getLocaleString("rotateLeft"), null, LanguageActions.getLocaleString("rotateLeftDes"), Integer.valueOf(KeyEvent.VK_4)));
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
            menu.add(new JMenuItem(action));
        }

        return menu;
    }


    /**
     * Action to rotate image 90 degrees to the right.
     */
    public class RotateRightAction extends ImageAction{

        RotateRightAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name,icon,desc,mnemonic);
        }      

        @Override
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                JOptionPane.showMessageDialog(null, "There is no image to rotate right.", "Error", JOptionPane.ERROR_MESSAGE);
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
     * Action to rotate image 90 degrees to the right.
     */
    public class RotateLeftAction extends ImageAction{

        RotateLeftAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name,icon,desc,mnemonic);
        }      

        @Override
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                JOptionPane.showMessageDialog(null, "There is no image rotate left.", "Error", JOptionPane.ERROR_MESSAGE);
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
     * Action to rotate image 90 degrees to the right.
     */
    public class Rotate180Action extends ImageAction{

        Rotate180Action(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name,icon,desc,mnemonic);
        }      

        @Override
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                JOptionPane.showMessageDialog(null, "There is no image to rotate by 180 degrees", "Error", JOptionPane.ERROR_MESSAGE);
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
     * Action to flip the image vertically.
     */
    public class FlipVertAction extends ImageAction{

        FlipVertAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name,icon,desc,mnemonic);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                JOptionPane.showMessageDialog(null, "There is no image to flip vertically.", "Error", JOptionPane.ERROR_MESSAGE);
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
     * Action to flip the image vertically.
     */
    public class FlipHorAction extends ImageAction{

        FlipHorAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name,icon,desc,mnemonic);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                JOptionPane.showMessageDialog(null, "There is no image to flip horizontally.", "Error", JOptionPane.ERROR_MESSAGE);
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
