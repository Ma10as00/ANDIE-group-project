package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class OrientationActions {

    /**
     * A list of actions for the Orientation menu.
     */
    protected ArrayList<Action> actions;

    public OrientationActions(){
        actions = new ArrayList<Action>();
        actions.add(new RotateRightAction("Rotate Right", null, "Rotate image by 90 degrees to the right", Integer.valueOf(KeyEvent.VK_2)));
        actions.add(new RotateLeftAction("Rotate Left", null, "Rotate image by 90 degrees to the left", Integer.valueOf(KeyEvent.VK_3)));
        actions.add(new Rotate180Action("Rotate 180 Degrees", null, "Rotate image by 180 degrees", Integer.valueOf(KeyEvent.VK_4)));
        actions.add(new FlipVertAction("Flip Vertically", null, "Flip image along vertical axis", Integer.valueOf(KeyEvent.VK_5)));
        actions.add(new FlipHorAction("Flip Horizontally", null, "Flip image along horizontal axis", Integer.valueOf(KeyEvent.VK_6)));
    }

        /**
     * <p>
     * Create a menu containing the list of Orientation actions.
     * </p>
     * 
     * @return The orientation menu UI element.
     */
    public JMenu createMenu() {
        JMenu menu = new JMenu("Orientation");

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
            target.getImage().apply(new RotateRight());
            target.repaint();
            target.getParent().revalidate();
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
            target.getImage().apply(new RotateLeft());
            target.repaint();
            target.getParent().revalidate();
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
            target.getImage().apply(new Rotate180());
            target.repaint();
            target.getParent().revalidate();
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
            target.getImage().apply(new FlipVertical());
            target.repaint();
            target.getParent().revalidate();
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
            target.getImage().apply(new FlipHorizontal());
            target.repaint();
            target.getParent().revalidate();
        }
    }
    
}
