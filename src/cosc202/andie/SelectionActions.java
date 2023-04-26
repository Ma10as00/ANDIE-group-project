package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

import cosc202.andie.ImagePanel.MouseHandler;

import java.awt.HeadlessException;

public class SelectionActions {
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Selection menu actions.
     * </p>
     */
    public SelectionActions() {
        actions = new ArrayList<Action>();
        actions.add(new RegionSelectionAction(LanguageActions.getLocaleString("regionselection"), null, LanguageActions.getLocaleString("selectionDesc"), Integer.valueOf(KeyEvent.VK_J)));
        actions.add(new RegionCropAction(LanguageActions.getLocaleString("regioncrop"), null, LanguageActions.getLocaleString("cropdesc"), Integer.valueOf(KeyEvent.VK_I)));
        
    }

    /**
     * <p>
     * Create a menu contianing the list of Selection actions.
     * </p>
     * 
     * @return The colour menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(LanguageActions.getLocaleString("Selection"));

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }
    /**
     * <p>
     * Action to select region
     * </p>
     * 
     * @see RegionSelection
     */
    public class RegionSelectionAction extends ImageAction{

        /**
         * <p>
         * Create a new select region action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        RegionSelectionAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            target.getImage().apply(new RegionSelection(MouseHandler.getEnterX(), MouseHandler.getEnterY(), MouseHandler.getExitX(), MouseHandler.getExitY()));
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * <p>
     * Action to select region
     * </p>
     * 
     * @see RegionCrop
     */
    public class RegionCropAction extends ImageAction{

        /**
         * <p>
         * Create a new select region action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        RegionCropAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }


        @Override
        public void actionPerformed(ActionEvent e) {
                target.getImage().apply(new RegionCrop(MouseHandler.getEnterX(), MouseHandler.getEnterY(), MouseHandler.getExitX(), MouseHandler.getExitY()));
                target.repaint();
                target.getParent().revalidate();
        }
    }
}
