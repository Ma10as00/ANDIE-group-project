package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

import cosc202.andie.ImagePanel.MouseHandler;

import java.awt.HeadlessException;
import java.awt.*;

public class SelectionActions {
    protected ArrayList<Action> actions;
    public int startX, startY, endX, endY; 
    public Rectangle rect; 

    /**
     * <p>
     * Create a set of Selection menu actions.
     * </p>
     */
    public SelectionActions() {
        actions = new ArrayList<Action>();
        actions.add(new RegionSelectionAction(LanguageActions.getLocaleString("regionSelection"), null, LanguageActions.getLocaleString("regionSelectionDesc"), Integer.valueOf(KeyEvent.VK_J)));
        actions.add(new RegionCropAction(LanguageActions.getLocaleString("regionCrop"), null, LanguageActions.getLocaleString("regionCropDesc"), Integer.valueOf(KeyEvent.VK_I)));
        
    }

    /**
     * <p>
     * Create a menu contianing the list of Selection actions.
     * </p>
     * 
     * @return The colour menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(LanguageActions.getLocaleString("selection"));

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
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g; 
            if(rect!= null){
                Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,0, new float[]{9}, 0);
                g2d.setStroke(dashed);
                g2d.setColor(Color.black);
                g2d.draw(rect);
      
            }
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            while(rect == null){
                addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e){
                        startX = e.getX(); 
                        startY = e.getY(); 
                    }
                });
                addMouseListener(new MouseAdapter() {
                    public void mouseReleased(MouseEvent e){
                        endX = e.getX();
                        endY = e.getY(); 
                        rect = new Rectangle(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));

                    }
                });
                
            }
            
                //JOptionPane.showMessageDialog(null, "Crop Image"); 
                target.getImage().apply(new RegionCrop(startX, startY, endX, endY));
                target.repaint();
                target.getParent().revalidate();
        }


        private void addMouseListener(MouseAdapter mouseAdapter) {
        }


    }
   

}
