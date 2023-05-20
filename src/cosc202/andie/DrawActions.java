package cosc202.andie;

import java.awt.image.BufferedImage;

import java.util.*;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.*;

import cosc202.andie.ImagePanel.MouseHandler;

import javax.imageio.*;
import java.io.*;
import java.nio.file.*;

public class DrawActions {
    protected ArrayList<Action> actions;
    public int startX, startY, endX, endY;
    public Rectangle rect;

    /**
     * <p>
     * Create a set of Drawing menu actions.
     * </p>
     */
    public DrawActions() {
        actions = new ArrayList<Action>();
        actions.add(new DrawCircleAction(LanguageActions.getLocaleString("drawCircle"), null,
                LanguageActions.getLocaleString("drawCircleDesc"), Integer.valueOf(0)));
        actions.add(new DrawRecAction(LanguageActions.getLocaleString("drawRec"), null,
                LanguageActions.getLocaleString("drawRecDesc"), Integer.valueOf(0)));
        actions.add(new DrawLineAction(LanguageActions.getLocaleString("drawLine"), null,
                LanguageActions.getLocaleString("drawLineDesc"), Integer.valueOf(0)));

    }

    /**
     * <p>
     * Create a menu contianing the list of Selection actions.
     * </p>
     * 
     * @return The colour menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(LanguageActions.getLocaleString("drawing"));

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
     * @see DrawRec
     */
    public class DrawRecAction extends ImageAction {

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
        DrawRecAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            try{

            JPanel choosePanel = new JPanel(new GridLayout(0, 1));
            JCheckBox colourBox = new JCheckBox("Colour");
            choosePanel.add(colourBox);

            int chooseOption = JOptionPane.showOptionDialog(null, choosePanel, "Colour
            Rectangle?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.CANCEL_OPTION, null,
            null, chooseOption);
            if(chooseOption == JOptionPane.CANCEL_OPTION){
            return;
            }else if(chooseOption == JOptionPane.OK_CANCEL_OPTION){
            target.getImage().undo();
            //Andie.RegionSelection = false;

            //if(colourBox.isSelected()) target.getImage().apply(new
            DrawRec(Andie.mouseSec, ColourActions.activeColour , chooseOption, enabled));

            target.repaint();
            target.getParent().revalidate();
            }
            }
            }
    }
}

/**
 * <p>
 * Action to draw circle
 * </p>
 * 
 * @see DrawRec
 */
public class DrawCircleAction extends ImageAction {

    /**
     * <p>
     * Create a new draw circle action.
     * </p>
     * 
     * @param name     The name of the action (ignored if null).
     * @param icon     An icon to use to represent the action (ignored if null).
     * @param desc     A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    DrawCircleAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
        super(name, icon, desc, mnemonic);
    }

    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}

/**
 * <p>
 * Action to draw line
 * </p>
 * 
 * @see DrawLine
 */
public class DrawLineAction extends ImageAction {

    /**
     * <p>
     * Create a new draw line action.
     * </p>
     * 
     * @param name     The name of the action (ignored if null).
     * @param icon     An icon to use to represent the action (ignored if null).
     * @param desc     A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    DrawLineAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
        super(name, icon, desc, mnemonic);
    }

    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}}
