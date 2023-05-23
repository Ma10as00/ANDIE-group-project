package cosc202.andie;

import java.util.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.*;
import javax.swing.*;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.*;

/**
 * <p>
 * Actions provided by the Tools menu.
 * </p>
 * 
 * <p>
 * The tools menu contains actions that allow you to drag your mouse to crop
 * and draw on an image. This includes a region selecion, crop, choosing a colour
 * choosing a width for outlines and lines, and drawing lines and outlined or filled
 * rectangles and ellipses.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Katie Wink (Modified by James Liu and Stella Srzich)
 */
public class DrawActions extends JFrame {

    /** Creates a new array list for draw rectangle actions. */
    protected ArrayList<Action> actionsSubRect;

    /** Creates a new array list for draw circle actions. */
    protected ArrayList<Action> actionsSubCirc;

    /** Stores the colour currently being used to draw. */
    public static Color userColour = Color.white;

    /** Stores the width of outlines and lines currently being used to draw. */
    public static int userWidth = 5;

    /** Stores the BasicStroke for outlines and lines currently being used to draw. */
    public static BasicStroke stroke = new BasicStroke(userWidth);

    /** 
     * Stores whether or not the user is currently drawing or selecting a region. This
     * is used to correctly colour the select region and colour buttons in thhe toolbar. 
     */
    public static boolean drawing = false;

    /**
     * A list of actions for the Tools menu.
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
     * Create a set of Tool menu actions.
     * </p>
     */
    public DrawActions() {
        actions = new ArrayList<Action>();

        // Creates an instance of select and region crop to be used in the toolbar (and
        // this menu).
        this.selectAction = new SelectAction(LanguageActions.getLocaleString("selectTool"), null,
                LanguageActions.getLocaleString("returnToSelect"), Integer.valueOf(KeyEvent.VK_R));
        actions.add(this.selectAction);

        this.cropAction = new RegionCropAction(LanguageActions.getLocaleString("crop"), null,
                LanguageActions.getLocaleString("regionCropDesc"), Integer.valueOf(KeyEvent.VK_X));
        actions.add(this.cropAction);

        // Create an instance of PickColourAction and SelectWidthAction to be used in
        // the toolbar and add to
        // the list of actions (and this menu).
        this.pickColourAction = new PickColourAction(LanguageActions.getLocaleString("pickCol"), null,
                LanguageActions.getLocaleString("pickColDesc"), Integer.valueOf(KeyEvent.VK_H));
        actions.add(this.pickColourAction);
        actions.add(new SelectWidth(LanguageActions.getLocaleString("width"), null,
                LanguageActions.getLocaleString("pickAWidth"), Integer.valueOf(KeyEvent.VK_W)));

        // Add the draw line/circle/rectangle actions to the list of sub actions (and
        // this menu).
        actions.add(new DrawLineAction(LanguageActions.getLocaleString("drawLine"), null,
                LanguageActions.getLocaleString("drawLineDesc"),null));

        // Create a new ArrayList to store Action objects related to drawing rectangles.
        // Add a new DrawRecAction object representing the action to draw a filled rectangle.
                actionsSubRect = new ArrayList<Action>();
        actionsSubRect.add(new DrawRecAction(LanguageActions.getLocaleString("drawRec"), null,
                LanguageActions.getLocaleString("drawRecDesc"), null));
        actionsSubRect.add(new DrawRecOutlineAction(LanguageActions.getLocaleString("drawRecOutline"), null,
                LanguageActions.getLocaleString("drawRecOutlineDesc"), null));

        // Create a new ArrayList to store Action objects related to drawing circles.
        // Add a new DrawCircleAction object representing the action to draw a filled circle.
        actionsSubCirc = new ArrayList<Action>();
        actionsSubCirc.add(new DrawCircleAction(LanguageActions.getLocaleString("drawCircle"), null,
                LanguageActions.getLocaleString("drawCircleDesc"), null));
        actionsSubCirc.add(new DrawCircOutlineAction(LanguageActions.getLocaleString("drawCircOutline"), null,
                LanguageActions.getLocaleString("drawCircOutlineDesc"), null));
    }

    /**
     * <p>
     * Create a menu contianing the list of Tool actions.
     * </p>
     * 
     * @return The Tool menu UI element.
     */
    public JMenu createMenu() {
        // Create a new JMenu object named fileMenu with the display name obtained from the LanguageActions.getLocaleString method.
        JMenu fileMenu = new JMenu(LanguageActions.getLocaleString("tools"));
        // Create a new JMenu object named subMenuCirc with the display name obtained from the LanguageActions.getLocaleString method.
        JMenu subMenuCirc = new JMenu(LanguageActions.getLocaleString("circle"));
        subMenuCirc.setBorderPainted(false);
        // Create a new JMenu object named subMenuRect with the display name obtained from the LanguageActions.getLocaleString method.
        JMenu subMenuRect = new JMenu(LanguageActions.getLocaleString("rectangle"));
        subMenuRect.setBorderPainted(false);

        // Set the border painted property of each JMenuItem to false.
        // Add each JMenuItem to the fileMenu.
        for (Action action : actions) {
            JMenuItem item = new JMenuItem(action);
            item.setBorderPainted(false);
            fileMenu.add(item);
        }
        // Set the border painted property of each JMenuItem to false.
        // Add each JMenuItem to the subMenuRect.
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
        // Set the border painted property of each JMenuItem to false.
        // Add each JMenuItem to the subMenuCirc.
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
     * region on the image panel.
     * </p>
     * 
     */
    public class SelectAction extends ImageAction {

        protected SelectAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
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
                // Update that we are currently selecting a region for toolbar.
                drawing = false;
                // Make this reflected in the toolbar.
                Andie.updateDarkMode();
            }
        }

    }

    /**
     * <p>
     * Action method that prompts user to pick a custom width
     * Uses JSlider to choose and save user choice.
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
            this.putValue(Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_W, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        }

        /**
         * <p>
         * Callback for when the select width action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the SelectWidth is triggered.
         * It opens a JSlider that prompts the user to select a width of the
         * line/outline drawn.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                try {
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("widthErr"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            }
            else {
                // There is an image open, carry on.
                // Determine the width - ask the user.

                // Set up slider for user to pick the width, set to last width.
                JSlider jslider = new JSlider();
                jslider.setValue(userWidth);
                jslider.setMaximum(55);
                jslider.setMinimum(5);
                jslider.setMajorTickSpacing(10);
                jslider.setPaintLabels(true);
                jslider.setPaintTicks(true);
                
                // Create a panel for the width panel.
                JPanel holdWidthPanel = new JPanel(new GridBagLayout());
                holdWidthPanel.setPreferredSize(new Dimension(100, 60));

                // Create a panel to preview the new width.
                JPanel widthPanel = new JPanel(new GridLayout(0, 1));
                widthPanel.setBackground(userColour);
                widthPanel.setPreferredSize(new Dimension(100, userWidth));
                holdWidthPanel.add(widthPanel, new GridBagConstraints());

                // Add these to the outer panel.
                JPanel outerPanel = new JPanel(new BorderLayout());
                outerPanel.add(holdWidthPanel, BorderLayout.CENTER);
                outerPanel.add(jslider, BorderLayout.SOUTH);

                // This part updates how the image looks when the slider is moved.
                jslider.addChangeListener(new ChangeListener() {
                    public void stateChanged(ChangeEvent ce) {
                        // Update the width of the width panel.
                        widthPanel.setPreferredSize(new Dimension(100, jslider.getValue()));
                        widthPanel.repaint();
                        widthPanel.getParent().revalidate();
                        holdWidthPanel.repaint();
                        holdWidthPanel.getParent().revalidate();
                        outerPanel.repaint();
                        outerPanel.getParent().revalidate();
                    }
                });

                // Ask user for width with slider in JOptionPane.
                try {
                    int option = JOptionPane.showOptionDialog(Andie.frame, outerPanel, LanguageActions.getLocaleString("widthSlider"),
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
                        // Do nothing. The original width is still in userWidth.
                        return;
                    }
                    if (option == JOptionPane.OK_OPTION) {
                        // Update userWidth to the new width.
                        userWidth = jslider.getValue();
                        stroke = new BasicStroke(userWidth);
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
         * ImagePanel.
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
                    // Update that we are currently drawing for toolbar.
                    drawing = true;
                    // Make this reflected in the toolbar.
                    Andie.updateDarkMode();
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
            this.putValue(Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_H, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        }

        /**
         * <p>
         * Callback for when the pick colour action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the PickColourAction is triggered.
         * It opens a JColorChooser that allows the user to pick and confirm their
         * selection.
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

                // Create a panel to hold the colour panel.
                JPanel holdColourPanel = new JPanel(new GridBagLayout());
                holdColourPanel.setPreferredSize(new Dimension(100, 60));

                // Set up the panel that will change colours.
                JPanel colourPanel = new JPanel(new GridLayout(0, 1));
                colourPanel.setPreferredSize(new Dimension(100, userWidth));
                colourPanel.setBackground(originalColour);
                holdColourPanel.add(colourPanel, new GridBagConstraints());

                // Add these to the outer panel.
                JPanel outerPanel = new JPanel(new BorderLayout());
                outerPanel.add(holdColourPanel, BorderLayout.CENTER);
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
                        colourPanel.setBackground(new Color(userColour.getRed(), userColour.getGreen(), userColour.getBlue()));
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
         * It sets the "tool" state to 2 which allows a circle to be drawn in ImagePanel.
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
                    // Update that we are currently drawing for toolbar.
                    drawing = true;
                    // Make this reflected in the toolbar.
                    Andie.updateDarkMode();
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
         * It sets the "tool" state to 3 which allows a line to be drawn in ImagePanel.
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
                    // Update that we are currently drawing for toolbar.
                    drawing = true;
                    // Make this reflected in the toolbar.
                    Andie.updateDarkMode();
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
            target.getImage().apply(new RegionCrop(ImagePanel.scale, ImagePanel.rect));
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
         * drawn in ImagePanel.
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
                    // Update that we are currently drawing for toolbar.
                    drawing = true;
                    // Make this reflected in the toolbar.
                    Andie.updateDarkMode();
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
         * drawn in ImagePanel.
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
                    // Update that we are currently drawing for toolbar.
                    drawing = true;
                    // Make this reflected in the toolbar.
                    Andie.updateDarkMode();
                } catch (Exception ert) {
                }
            }
        }
    }
}
