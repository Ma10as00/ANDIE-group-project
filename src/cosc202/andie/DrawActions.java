package cosc202.andie;

import java.util.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Rectangle;
import javax.swing.*;
import java.awt.image.RasterFormatException;
import java.awt.Toolkit;
import java.awt.event.*;

public class DrawActions extends JFrame {
    protected ArrayList<Action> actionsSub;
    public int startX, startY, endX, endY;
    public Rectangle rectangle;
    int chooseOption;
    JPanel panel;
    Color bgColor = Color.WHITE;
    public static Color userColour;
    public static boolean drawMode;

    /** 
     * A list of actions for the Tool (Draw) menu. 
     */
    protected ArrayList<Action> actions;

    /** 
     * An instance of RegionCropAction to be used in renderToolbar. 
     */
    protected RegionCropAction cropAction;

    /** 
     * An instance of PickColourAction to be used in renderToolbar. 
     */
    protected PickColourAction pickColourAction;

    /** 
     * An instance of SelectAction to be used in renderToolbar. 
     */
    protected SelectAction selectAction;

    /**
     * <p>
     * Create a set of Drawing menu actions.
     * </p>
     */
    public DrawActions() {
        actions = new ArrayList<Action>();

        // Creates an instance of select and pickColour to be used in the toolbar (not in this menu).
        this.pickColourAction = new PickColourAction(LanguageActions.getLocaleString("pickCol"), null,
                LanguageActions.getLocaleString("pickColDesc"), Integer.valueOf(0));

        this.selectAction = new SelectAction(LanguageActions.getLocaleString("selectTool"), null,
                LanguageActions.getLocaleString("returnToSelect"), null);

        // Create an instance of RegionCropAction to be used in the toolbar and add to the list of actions (for this menu).
        this.cropAction = new RegionCropAction(LanguageActions.getLocaleString("crop"), null,
                LanguageActions.getLocaleString("regionCropDesc"), Integer.valueOf(KeyEvent.VK_X));
        actions.add(this.cropAction);

        // Add the draw line/circle/rectangle actions to the list of sub actions (for this menu).
        actionsSub = new ArrayList<Action>();
        actionsSub.add(new DrawCircleAction(LanguageActions.getLocaleString("drawCircle"), null,
                LanguageActions.getLocaleString("drawCircleDesc"), Integer.valueOf(0)));
        actionsSub.add(new DrawRecAction(LanguageActions.getLocaleString("drawRec"), null,
                LanguageActions.getLocaleString("drawRecDesc"), Integer.valueOf(0)));
        actionsSub.add(new DrawLineAction(LanguageActions.getLocaleString("drawLine"),
                null,
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
        JMenu fileMenu = new JMenu(LanguageActions.getLocaleString("tools"));
        JMenu subMenu = new JMenu(LanguageActions.getLocaleString("drawing"));

        for (Action action : actions) {
            JMenuItem item = new JMenuItem(action);
            item.setBorderPainted(false);
            fileMenu.add(item);
        }
        for (Action action : actionsSub) {
            JMenuItem item = new JMenuItem(action);
            item.setBorderPainted(false);
            subMenu.add(item);
        }

        fileMenu.add(subMenu);
        return fileMenu;
    }

    /**
     * <p>
     * Accessor method to return SelectAction as a single action.
     * </p>
     * 
     * @return an instance of SelectAction.
     */
    public SelectAction getSelectAction() {
        return this.selectAction;
    }

    public class SelectAction extends ImageAction {

        protected SelectAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            target.setTool(0);
        }

    }

    /**
     * <p>
     * Action to draw a rectangle.
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
            try {
                target.deselect();
                target.setTool(1);
                target.repaint();
            } catch (Exception ert) {
            }
        }

    }

    /**
     * <p>
     * Accessor method to return PickColourAction as a single action.
     * </p>
     * 
     * @return an instance of PickColourAction.
     */
    public PickColourAction getPickColourAction() {
        return this.pickColourAction;
    }

    public class PickColourAction extends ImageAction {

        PickColourAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            panel = new JPanel(new BorderLayout());
            JPanel subPanel = new JPanel();

            JButton btnColor = new JButton("Change Color");
            JButton confirmButton = new JButton("Confirm");
            subPanel.add(btnColor);
            subPanel.add(confirmButton);
            panel.add(subPanel, BorderLayout.SOUTH);
            btnColor.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    Color color = JColorChooser.showDialog(Andie.frame,
                            "Choose a color", bgColor);
                    if (color != null) {
                        bgColor = color;
                    }
                    panel.setBackground(bgColor);
                }
            });

            confirmButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent awt) {
                    userColour = bgColor;
                    panel.setVisible(false);
                    JComponent comp = (JComponent) awt.getSource();
                    Window win = SwingUtilities.getWindowAncestor(comp);
                    win.dispose();
                }
            });

            setContentPane(panel);
            userColour = bgColor;
            setTitle("Colour Chooser");
            setSize(200, 150);
            setLocationRelativeTo(Andie.frame);
            setVisible(true);
        }
    }

    /**
     * <p>
     * Action to draw circle.
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
            try {
                target.deselect();
                target.setTool(2);
                target.repaint();
            } catch (Exception ert) {
            }
        }
    }

    /**
     * <p>
     * Action to draw line.
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
            try {
                target.deselect();
                target.setTool(3);
                target.repaint();
            } catch (Exception ert) {
            }
        }
    }

    /**
     * <p>
     * Accessor method to return CropAction as a single action.
     * </p>
     * 
     * @return an instance of CropAction.
     */
    public RegionCropAction getCropAction() {
        return this.cropAction;
    }

    /**
     * <p>
     * Action to crop a selected region.
     * </p>
     * 
     * @see RegionCrop
     */
    public class RegionCropAction extends ImageAction {

        /**
         * <p>
         * Create a new crop action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        RegionCropAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            this.putValue(Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_X, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        }

        /**
         * <p>
         * Callback for when the crop action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the RegionCrop is triggered.
         * It will crop the selected region and then reset the selected region.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if (target.getImage().hasImage() == false) {
                // There is not an image crop, so display error message.
                JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("cropError"),
                        LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
            }
            // Check if there is a selected region.
            if (ImagePanel.rect == null) {
                // Trying to crop when there is no region selected. Give the user an error.
                JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("cropErrorNoSelectedRegion"),
                        LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
            }
            // There is an image open, and a selected region, so we try to crop it.
            target.getImage().apply(new RegionCrop(ImagePanel.rect));
            ImagePanel.rect = null;
            target.repaint();
            target.getParent().revalidate();
            target.repaint();
            ImagePanel.enterX = 0;
            ImagePanel.enterY = 0;
            ImagePanel.exitX = 0;
            ImagePanel.exitY = 0;                
            target.getParent().revalidate();
        }
    }

}
