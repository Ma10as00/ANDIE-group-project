package cosc202.andie;

import java.util.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.*;

public class DrawActions extends JFrame {

    /** Creates a new array list for draw rectangle actions */
    protected ArrayList<Action> actionsSubRect;
    /** Creates a new array list for draw circle actions */
    protected ArrayList<Action> actionsSubCirc;
    /**  */
    public static Color userColour = Color.white;
    public static int userWidth = 5;

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

        // Creates an instance of select and region crop to be used in the toolbar (and
        // this menu).
        this.selectAction = new SelectAction(LanguageActions.getLocaleString("selectTool"), null,
                LanguageActions.getLocaleString("returnToSelect"), null);
        actions.add(this.selectAction);

        this.cropAction = new RegionCropAction(LanguageActions.getLocaleString("crop"), null,
                LanguageActions.getLocaleString("regionCropDesc"), Integer.valueOf(KeyEvent.VK_X));
        actions.add(this.cropAction);

        // Create an instance of PickColourAction and SelectWidthAction to be used in
        // the toolbar and add to
        // the list of actions (and this menu).
        this.pickColourAction = new PickColourAction(LanguageActions.getLocaleString("pickCol"), null,
                LanguageActions.getLocaleString("pickColDesc"), Integer.valueOf(0));
        actions.add(this.pickColourAction);
        actions.add(new SelectWidth(LanguageActions.getLocaleString("width"), null,
                LanguageActions.getLocaleString("pickAWidth"), Integer.valueOf(KeyEvent.VK_P)));

        // Add the draw line/circle/rectangle actions to the list of sub actions (and
        // this menu).
        actions.add(new DrawLineAction(LanguageActions.getLocaleString("drawLine"), null,
                LanguageActions.getLocaleString("drawLineDesc"), Integer.valueOf(0)));

        actionsSubRect = new ArrayList<Action>();
        actionsSubRect.add(new DrawRecAction(LanguageActions.getLocaleString("drawRec"), null,
                LanguageActions.getLocaleString("drawRecDesc"), Integer.valueOf(0)));
        actionsSubRect.add(new DrawRecOutlineAction(LanguageActions.getLocaleString("drawRecOutline"), null,
                LanguageActions.getLocaleString("drawRecOutlineDesc"), Integer.valueOf(0)));

        actionsSubCirc = new ArrayList<Action>();
        actionsSubCirc.add(new DrawCircleAction(LanguageActions.getLocaleString("drawCircle"), null,
                LanguageActions.getLocaleString("drawCircleDesc"), Integer.valueOf(0)));
        actionsSubCirc.add(new DrawCircOutlineAction(LanguageActions.getLocaleString("drawCircOutline"), null,
                LanguageActions.getLocaleString("drawCircOutlineDesc"), Integer.valueOf(0)));
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
        JMenu subMenuCirc = new JMenu(LanguageActions.getLocaleString("circle"));
        subMenuCirc.setBorderPainted(false);
        JMenu subMenuRect = new JMenu(LanguageActions.getLocaleString("rectangle"));
        subMenuRect.setBorderPainted(false);

        for (Action action : actions) {
            JMenuItem item = new JMenuItem(action);
            item.setBorderPainted(false);
            fileMenu.add(item);
        }
        for (Action action : actionsSubRect) {
            JMenuItem item = new JMenuItem(action);
            item.setBorderPainted(false);
            subMenuRect.add(item);
        }
        for (Action action : actionsSubCirc) {
            JMenuItem item = new JMenuItem(action);
            item.setBorderPainted(false);
            subMenuCirc.add(item);
        }

        fileMenu.add(subMenuRect);
        fileMenu.add(subMenuCirc);
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

    /**
     * <p>
     * Action method that sets the "tool" to selection tool so that you can select a
     * region on the image panel
     * Drawing done in ImagePanel
     * </p>
     * 
     */
    public class SelectAction extends ImageAction {

        protected SelectAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the select region is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the SelectAction is triggered.
         * It sets the "tool" state to 0 which allows a region to be selected in
         * imagePanel
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                try {
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("selectErr"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            }
            else {
                // There is an image open, carry on.
                target.setTool(0);
            }
        }

    }

    /**
     * <p>
     * Action method that prompts user to pick a custom width
     * Uses JSlider to choose and save user choice
     * </p>
     * 
     */
    public class SelectWidth extends ImageAction {

        /**
         * <p>
         * Create a new select width action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SelectWidth(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the select width action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the SelectWidth is triggered.
         * It opens a JSlider that prompts the user to select a width of the
         * line/outline drawn
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            JSlider jslider = new JSlider();
            jslider.setValue(userWidth);
            jslider.setMaximum(50);
            jslider.setMinimum(5);
            jslider.setMajorTickSpacing(5);
            jslider.setPaintLabels(true);
            jslider.setPaintTicks(true);

            try {
                int option = JOptionPane.showOptionDialog(Andie.frame, jslider,
                        LanguageActions.getLocaleString("widthSlider"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
                    return;
                }
                if (option == JOptionPane.OK_OPTION) {
                    userWidth = jslider.getValue();
                }
                if (userWidth == 5) {
                    return;
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

        /**
         * <p>
         * Callback for when the draw rectangle action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the DrawRecAction is triggered.
         * It sets the "tool" state to 1 which allows a rectangle to be drawn in
         * ImagePanel
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                try {
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("rectFillErr"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            }
            else {
                try {
                    target.deselect();
                    target.setTool(1);
                    target.repaint();
                } catch (Exception ert) {
                }
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

        /**
         * <p>
         * Create a new select colour action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        PickColourAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the pick colour action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the PickColourAction is triggered.
         * It opens a JColorChooser that allows the user to pick and confirm their
         * selection
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                try {
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("colourErr"),
                            LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse.
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            } else {
                // There is an image open, carry on.
                // Determine the colour the user wants to pick.
                // Remember the original colour.
                Color originalColour = new Color(userColour.getRed(), userColour.getGreen(), userColour.getBlue());

                // Set up buttons for the user to pick a new colour.
                JButton colourButton = new JButton(LanguageActions.getLocaleString("changeCol"));

                // Set up the panel that will change colours.
                JPanel colourPanel = new JPanel(new GridLayout(0, 1));
                colourPanel.setBackground(userColour);

                // Add these to the outer panel.
                JPanel outerPanel = new JPanel(new BorderLayout());
                outerPanel.add(colourPanel, BorderLayout.CENTER);
                outerPanel.add(colourButton, BorderLayout.SOUTH);

                // Make the colout button allow the user to pick a colour.
                colourButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        Color colour = JColorChooser.showDialog(Andie.frame,
                                LanguageActions.getLocaleString("pickCol"), userColour);
                        if (colour != null) {
                            userColour = colour;
                        }
                        colourPanel.setBackground(userColour);
                    }
                });

                // Ask user for the colour with an option dialogue.
                try {
                    int option = JOptionPane.showOptionDialog(Andie.frame, outerPanel,
                            LanguageActions.getLocaleString("drawCol"),
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
                    if (option == JOptionPane.CLOSED_OPTION || option == JOptionPane.CANCEL_OPTION) {
                        // Set the colour back to the original colour.
                        userColour = originalColour;
                        return;
                    }
                    if (option == JOptionPane.OK_OPTION) {
                        // Do nothing, the colour is already set.
                        return;
                    }
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse.
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            }
        }
    }

    /**
     * <p>
     * Action to draw circle.
     * </p>
     * 
     * @see DrawCircle
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

        /**
         * <p>
         * Callback for when the draw circle action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the DrawCircleAction is triggered.
         * It sets the "tool" state to 2 which allows a circle to be drawn in ImagePanel
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                try {
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("circleFillErr"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            }
            else {
                // There is an image open, carry on.
                try {
                    target.deselect();
                    target.setTool(2);
                    target.repaint();
                } catch (Exception ert) {
                }
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

        /**
         * <p>
         * Callback for when the draw line action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the DrawLineAction is triggered.
         * It sets the "tool" state to 3 which allows a line to be drawn in ImagePanel
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                try {
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("lineErr"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            }
            else {
                // There is an image open, carry on.
                try {
                    target.deselect();
                    target.setTool(3);
                    target.repaint();
                } catch (Exception ert) {
                }
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
                return;
            }
            // Check if there is a selected region.
            if (ImagePanel.rect == null) {
                // Trying to crop when there is no region selected. Give the user an error.
                JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("cropErrorNoSelectedRegion"),
                        LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                return;
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

    /**
     * <p>
     * Action to draw a rectangle outline.
     * </p>
     * 
     * @see DrawRec
     */
    public class DrawRecOutlineAction extends ImageAction {

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
        DrawRecOutlineAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the draw Rectangle outline action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the DrawRecOutlineAction is triggered.
         * It sets the "tool" state to 4 which allows an outline of a rectangle to be
         * drawn in ImagePanel
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                try {
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("rectOutErr"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            }
            else {
                // There is an image open, carry on.
                try {
                    target.deselect();
                    target.setTool(4);
                    target.repaint();
                } catch (Exception ert) {
                }
            }
        }

    }

    /**
     * <p>
     * Action to draw a outline of a circle.
     * </p>
     * 
     * @see DrawCircle
     */
    public class DrawCircOutlineAction extends ImageAction {

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
        DrawCircOutlineAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the draw circle outline action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the DrawLineAction is triggered.
         * It sets the "tool" state to 5 which allows the outline of a circle to be
         * drawn in ImagePanel
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                try {
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("circleOutErr"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            }
            else {
                // There is an image open, carry on.
                try {
                    target.deselect();
                    target.setTool(5);
                    target.repaint();
                } catch (Exception ert) {
                }
            }
        }

    }

}
