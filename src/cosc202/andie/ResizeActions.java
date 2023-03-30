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
 * the image and their value. 
 * This includes a resize 50% and resize 150%.
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
    
    /** A list of actions for the Filter menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Resize menu actions.
     * </p>
     */
    public ResizeActions() {
        actions = new ArrayList<Action>();
        actions.add(new ImageResize50Action("Resize 50%", null, "Resize image to 50% it's original size", null));
        actions.add(new ImageResize150Action("Resize 150%", null, "Resize image to 150% it's original size", null));
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
     * Action to resize the image to 50%
     */
    public class ImageResize50Action extends ImageAction{

        ImageResize50Action(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name,icon,desc,mnemonic);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            target.getImage().apply(new ImageResize50());
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * Action to resize the image to 150%
     */
    public class ImageResize150Action extends ImageAction{

        ImageResize150Action(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name,icon,desc,mnemonic);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            target.getImage().apply(new ImageResize150());
            target.repaint();
            target.getParent().revalidate();
        }
    }
}
