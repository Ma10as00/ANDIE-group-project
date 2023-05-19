package cosc202.andie;

import java.util.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Rectangle;
import javax.swing.*;

public class DrawActions extends JFrame {
    protected ArrayList<Action> actions;
    public int startX, startY, endX, endY;
    public Rectangle rectangle;
    int chooseOption;
    JPanel panel;
    Color bgColor = Color.WHITE;
    public static Color userColour;
    public static boolean drawMode;

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
        actions.add(new DrawLineAction(LanguageActions.getLocaleString("drawLine"),
                null,
                LanguageActions.getLocaleString("drawLineDesc"), Integer.valueOf(0)));
        actions.add(new pickColour(LanguageActions.getLocaleString("pickCol"), null,
                LanguageActions.getLocaleString("pickColDesc"), Integer.valueOf(0)));

        actions.add(new select(LanguageActions.getLocaleString("selectTool"), null,
                LanguageActions.getLocaleString("returnToSelect"), null));

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

    public class select extends ImageAction {

        protected select(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            target.setTool(0);
        }

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
            try {
                target.deselect();
                target.setTool(1);
                target.repaint();
            } catch (Exception ert) {
            }
        }

    }

    public class pickColour extends ImageAction {

        pickColour(String name, ImageIcon icon, String desc, Integer mnemonic) {
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
                    Color color = JColorChooser.showDialog(DrawActions.this,
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
            setSize(300, 200);
            setLocationRelativeTo(null);
            setVisible(true);
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
            try {
                target.deselect();
                target.setTool(3);
                target.repaint();
            } catch (Exception ert) {
            }
        }
    }
}
