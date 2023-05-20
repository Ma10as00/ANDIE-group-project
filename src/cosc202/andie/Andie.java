package cosc202.andie;

import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import java.util.prefs.Preferences;

import javax.swing.*;
import javax.imageio.*;

/**
 * <p>
 * Main class for A Non-Destructive Image Editor (ANDIE).
 * </p>
 * 
 * <p>
 * This class is the entry point for the program.
 * It creates a Graphical User Interface (GUI) that provides access to various
 * image editing and processing operations.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class Andie {

    /** An ImagePanel to disply the image currenlty being edited. */
    public static ImagePanel imagePanel;

    /** The main JPanel in which the ImagePanel sits. */
    public static JPanel outerPanel;

    /** A JFrame of the main GUI frame. */
    public static JFrame frame;

    /** A boolean to represent whether or not we are in dark mode. */
    public static boolean darkMode;

    /** The colour used as the background in dark mode and light mode. */
    private static Color grey = new Color(30, 30, 30);

    /** The second colour used as the background in dark mode. */
    private static Color lightGrey = new Color(45, 45, 45);

    /** The forth colour used as the background in dark mode. */
    private static Color darkGrey = new Color(20, 20, 20);

    /** The fifth colour used as the background in dark mode. */
    private static Color lightWhite = new Color(225, 225, 225);

    /** The sixth colour used as the background in light mode. */
    private static Color darkerWhite = new Color(220, 220, 220);

    /** The seventh colour used as the background in light mode. */
    private static Color darkWhite = new Color(240, 240, 240);

    /**
     * <p>
     * Launches the main GUI for the ANDIE program.
     * </p>
     * 
     * <p>
     * This method sets up an interface consisting of an active image (an
     * {@code ImagePanel})
     * and calls upon renderMenu() to create a JMenuBar.
     * 
     * @throws Exception if something goes wrong.
     */
    private static void createAndShowGUI() throws Exception {
        // Sets the starting language to NZ English.
        Preferences prefs = Preferences.userNodeForPackage(Andie.class);
        String lang = prefs.get("language", "en");
        String country = prefs.get("country", "NZ");
        String languageCode = lang + "_" + country;

        // If language code is en sets default to English.
        if (languageCode.equals("en_NZ")) {
            Locale.setDefault(new Locale("en", "NZ"));
        }
        // If language code is mi set default language to Maori.
        else if (languageCode.equals("mi_NZ")) {
            Locale.setDefault(new Locale("mi", "NZ"));
        }
        // If language code is no sets default to Norwegian.
        else if (languageCode.equals("no_NO")) {
            Locale.setDefault(new Locale("no", "NO"));
        }
        // If language code is es sets default to Spanish.
        else if (languageCode.equals("sp_ES")) {
            Locale.setDefault(new Locale("sp", "ES"));
        }
        // If language code is de sets default to German
        else if (languageCode.equals("de_DE")) {
            Locale.setDefault(new Locale("de", "DE"));
        }

        // Set up the main GUI frame.
        frame = new JFrame("ANDIE");
        Image image = ImageIO.read(Andie.class.getClassLoader().getResource("icon.png"));
        frame.setIconImage(image);
        // Changed default close operation to DO_NOTHING_ON_CLOSE
        // so that a WindowListener can handle the operation.
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frameClosing();
            }
        });

        // Note, I deleted the ImagePanel declaration here so that there
        // is a static data feild for the ImagePanel instead. This means
        // the windowClosing method can access the ImagePanel as well.

        // The main content area is an ImagePanel.
        imagePanel = new ImagePanel(frame);
        ImageAction.setTarget(imagePanel);

        // Create another panel to hold the image panel.
        // Note, the imagePanel is always centered in the outerPanel, which has a scroll pane.
        outerPanel = new JPanel();
        outerPanel.setLayout(new GridBagLayout());
        outerPanel.add(imagePanel, new GridBagConstraints());
        JScrollPane scrollPane = new JScrollPane(outerPanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            // TO DO Auto-generated catch block
            e.printStackTrace();
        }
        // Calls renderMenu method to render the menu in the selected language.
        renderMenu();

        // Calls renderToolbar method to render the toolbar.
        renderToolbar();

        // Update the mode.
        if (Andie.darkMode) {
            UIManager.put("MenuItem.background", lightGrey);
            UIManager.put("MenuItem.opaque", true);

            // Set the background and foreground colors for the frame
            frame.setBackground(darkGrey);
            frame.setForeground(darkGrey);

            // Set the background and foreground colors for the outer panel
            outerPanel.setBackground(darkGrey);
            outerPanel.setForeground(darkGrey);

            // Set the background and foreground colors for the image panel
            imagePanel.setBackground(darkGrey);
            imagePanel.setForeground(darkGrey);
        }
        else {
            UIManager.put("MenuItem.background", Color.white);
            UIManager.put("MenuItem.opaque", true);

            // Set the background and foreground colors for the frame
            frame.setBackground(darkerWhite);
            frame.setForeground(darkerWhite);

            // Set the background and foreground colors for the outer panel
            outerPanel.setBackground(darkerWhite);
            outerPanel.setForeground(darkerWhite);

            // Set the background and foreground colors for the image panel
            imagePanel.setBackground(darkerWhite);
            imagePanel.setForeground(darkerWhite);
        }

        frame.pack();
        // Make window centered on screen.
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * <p>
     * Constructs the JMenuBar separately so the method can be recalled when the
     * language is changed.
     * </p>
     * 
     * <p>
     * This method sets up various menus which can be used to trigger operations to
     * load, save,
     * edit, etc.
     * These operations are implemented {@link ImageOperation}s and triggerd via
     * {@code ImageAction}s grouped by their general purpose into menus.
     * </p>
     * 
     * @see ImagePanel
     * @see ImageAction
     * @see ImageOperation
     * @see FileActions
     * @see EditActions
     * @see ViewActions
     * @see FilterActions
     * @see ColourActions
     * @see MacroActions
     * @see OrientationActions
     * @see ResizeActions
     * @see LanguageActions
     * @see DrawActions
     * 
     */
    public static void renderMenu() {
        // Add in menus for various types of action the user may perform.
        JMenuBar menuBar = new JMenuBar();
        setMenuBackground(menuBar);

        // Add the left-aligned menus.

        // File menus are pretty standard, so things that usually go in File menus go
        // here. We pass a frame so that when we open an image, the frame is packed to
        // the new image size.
        FileActions fileActions = new FileActions(frame);
        menuBar.add(fileActions.createMenu());

        // Ability to change the language from a set of included language bundles.
        LanguageActions languageActions = new LanguageActions();
        menuBar.add(languageActions.createMenu());

        // View actions control how the image is displayed, its zoom, but do not alter
        // its actual content
        ViewActions viewActions = new ViewActions();
        menuBar.add(viewActions.createMenu());

        // Likewise Edit menus are very common, so should be clear what might go here.
        EditActions editActions = new EditActions();
        menuBar.add(editActions.createMenu());

        // Add the right aligned menus.
        menuBar.add(Box.createHorizontalGlue());

        // Orientation actions change the orientation of the image, altering its
        // content. 
        OrientationActions orientationActions = new OrientationActions();
        menuBar.add(orientationActions.createMenu());

        // Resize actions change the size of the image, altering its content.
        ResizeActions resizeActions = new ResizeActions();
        menuBar.add(resizeActions.createMenu());

        // Actions that affect the representation of colour in the image.
        ColourActions colourActions = new ColourActions();
        menuBar.add(colourActions.createMenu());

        // Filter actions apply a per-pixel operation to the image, generally based on a local window.
        FilterActions filterActions = new FilterActions();
        menuBar.add(filterActions.createMenu());

        // Draw actions to edit the image with crop or draw a line, rectangle or cirlce.
        DrawActions drawAction = new DrawActions();
        menuBar.add(drawAction.createMenu());

        // Macro actions can record what operations are applied to the image, and put
        // them together into macros.
        MacroActions macroActions = new MacroActions();
        menuBar.add(macroActions.createMenu());

        // Change the colour depending on the mode.
        if (Andie.darkMode) {
            menuBar.setBackground(lightGrey);
            menuBar.setForeground(lightWhite);
        }
        else {
            menuBar.setBackground(Color.white);
            menuBar.setForeground(grey);
        }
        menuBar.setOpaque(true);
        frame.setJMenuBar(menuBar);

        // Update the title of the main GUI.
        if (imagePanel.getImage().hasImage()) {
            imagePanel.getImage().updateFrameTitle();
        }
    }

    private static void setMenuBackground(JMenuBar menuBar) {
        if (Andie.darkMode) {
            UIManager.put("Menu.background", lightGrey);
            UIManager.put("Menu.foreground", lightWhite);
            UIManager.put("MenuItem.background", lightGrey);
            UIManager.put("MenuItem.foreground", lightWhite);
            UIManager.put("MenuItem.opaque", true);
        } else {
            UIManager.put("Menu.background", Color.white);
            UIManager.put("Menu.foreground", grey);
            UIManager.put("MenuItem.background", Color.white);
            UIManager.put("MenuItem.foreground", grey);
            UIManager.put("MenuItem.opaque", true);
        }
    }

    /**
     * <p>
     * Constructs the JToolBar separately so the individual JButton actions can be
     * applied at once.
     * </p>
     * 
     * <p>
     * This method adds a JToolBar and JButtons using various Actions from Andie.
     * </p>
     * 
     * @see ImageAction
     * @see FileActions
     * @see EditActions
     * @see ViewActions
     * 
     */
    public static void renderToolbar() {
        JToolBar toolbar = new JToolBar();
        toolbar.setOrientation(SwingConstants.VERTICAL);
        toolbar.setFloatable(false);
        frame.add(toolbar,BorderLayout.WEST);
        JButton button = null;
        if (Andie.darkMode) {
            toolbar.setBackground(grey);
            toolbar.setForeground(lightWhite);
        } else {
            toolbar.setBackground(darkWhite);
            toolbar.setForeground(grey);
        }

        // Adds the save button to the toolbar.
        FileActions fileActions = new FileActions(frame);
        button = createButton(fileActions.getFileSaveAction(), "saveImageIcon.png");
        toolbar.add(button);

        // Adds a separator to the toolbar.
        toolbar.addSeparator();

        // Adds the undo button to the toolbar.
        EditActions editActions = new EditActions();
        button = createButton(editActions.getUndoAction(), "");
        toolbar.add(button);

        // Adds the redo button to the toolbar.
        button = createButton(editActions.getRedoAction(), "");
        toolbar.add(button);

        // Adds a separator to the toolbar.
        toolbar.addSeparator();

        // Adds the Zoom In button to the toolbar.
        ViewActions viewActions = new ViewActions();
        button = createButton(viewActions.getZoomInAction(), "");

        toolbar.add(button);

        // Adds the Zoom out button to the toolbar.
        button = createButton(viewActions.getZoomOutAction(), "");
        toolbar.add(button);

        // Adds the Zoom Full button to the toolbar.
        button = createButton(viewActions.getZoomFullAction(), "");
        toolbar.add(button);

        // Adds a separator to the toolbar.
        toolbar.addSeparator();

        // Adds the rotate left button to the toolbar.
        OrientationActions orientationActions = new OrientationActions();
        button = createButton(orientationActions.getRotateLeftAction(), "");
        toolbar.add(button);

        // Adds the rotate right button to the toolbar.
        button = createButton(orientationActions.getRotateRightAction(), "");
        toolbar.add(button);

        // Adds a separator to the toolbar.
        toolbar.addSeparator();

        // Adds the crop button to the toolbar.
        DrawActions drawActions = new DrawActions();
        button = createButton(drawActions.getCropAction(), "");
        toolbar.add(button);

        // Adds select tool to the toolbar.
        button = createButton(drawActions.getSelectAction(), "");
        toolbar.add(button);

        // Adds pick colour to the toolbar.
        button = createButton(drawActions.getPickColourAction(), "");
        toolbar.add(button);
    }

    private static JButton createButton(Action action, String imagePath){
        JButton button = new JButton(action);
        if (Andie.darkMode) {
            button.setForeground(grey);
            button.setBackground(lightWhite);
        } else {
            button.setForeground(grey);
            button.setBackground(darkWhite);
        }
        try {
        if (button.getIcon() == null) {
            Image buttonImage = ImageIO.read(Andie.class.getClassLoader().getResource(imagePath));
            buttonImage = buttonImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);

            button.setIcon(new ImageIcon(buttonImage));
        }
        } catch(Exception fileNotFoundException) {

        }

        return button;
    }

    /**
     * <p>
     * Handles a user closing the main GUI frame.
     * </p>
     * 
     * <p>
     * If the user trys to exit the main GUI frame with an image open, a warning
     * dialogue box
     * will warn them that any unsaved changes will be lost.
     * </p>
     */
    private static void frameClosing() {
        // Check if there is an image open.
        if (imagePanel.getImage().hasImage()) {
            if (!imagePanel.getImage().opsSaved()) {
                // There is an image open and it has unsaved operations, warn user that any unsaved changes will be deleted.
                try {
                    int option = JOptionPane.showConfirmDialog(null, LanguageActions.getLocaleString("errorExit"),
                            LanguageActions.getLocaleString("warning"), JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.WARNING_MESSAGE);
                    if (option == JOptionPane.OK_OPTION) {
                        // User clicked ok, exit.
                        System.exit(0);
                    }
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse,
                    // as with confrim dialog.
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            }
            else {
                // If the image is saved, exit.
                System.exit(0);
            }
        } else {
            // There is no image open, exit.
            System.exit(0);
        }
    }

    public static void updateDarkMode() {
        if (darkMode) {
            UIManager.put("MenuItem.background", lightGrey);
            UIManager.put("MenuItem.opaque", true);

            // Set the background and foreground colors for the frame
            frame.setBackground(darkGrey);
            frame.setForeground(darkGrey);

            // Set the background and foreground colors for the outer panel
            outerPanel.setBackground(darkGrey);
            outerPanel.setForeground(darkGrey);

            // Set the background and foreground colors for the image panel
            imagePanel.setBackground(darkGrey);
            imagePanel.setForeground(darkGrey);

            // Set the background and foreground colors for the menu bar
            JMenuBar menuBar = frame.getJMenuBar();
            menuBar.setBackground(lightGrey);
            menuBar.setForeground(lightWhite);

            // Set the background and foreground colors for the tool bar
            JToolBar toolbar = (JToolBar) frame.getContentPane().getComponent(1);
            toolbar.setBackground(grey);
            toolbar.setForeground(lightWhite);

            // Set the background and foreground colors for individual buttons in the
            // toolbar
            Component[] components = toolbar.getComponents();
            for (Component component : components) {
                if (component instanceof JButton) {
                    JButton button = (JButton) component;
                    button.setBackground(grey);
                    button.setForeground(lightWhite);
                }
            }
        }
        if (!darkMode) {
            UIManager.put("MenuItem.background", Color.white);
            UIManager.put("MenuItem.opaque", true);

            // Set the background and foreground colors for the frame
            frame.setBackground(darkerWhite);
            frame.setForeground(darkerWhite);

            // Set the background and foreground colors for the outer panel
            outerPanel.setBackground(darkerWhite);
            outerPanel.setForeground(darkerWhite);

            // Set the background and foreground colors for the image panel
            imagePanel.setBackground(darkerWhite);
            imagePanel.setForeground(darkerWhite);

            // Set the background and foreground colors for the menu bar
            JMenuBar menuBar = frame.getJMenuBar();
            menuBar.setBackground(Color.white);
            menuBar.setForeground(grey);

            // Set the background and foreground colors for the tool bar
            JToolBar toolbar = (JToolBar) frame.getContentPane().getComponent(1);
            toolbar.setBackground(darkWhite);
            toolbar.setForeground(grey);

            // Set the background and foreground colors for individual buttons in the
            // toolbar
            Component[] components = toolbar.getComponents();
            for (Component component : components) {
                if (component instanceof JButton) {
                    JButton button = (JButton) component;
                    button.setBackground(darkWhite);
                    button.setForeground(grey);
                }
            }
        }
        // Repaint the frame and image panel to reflect the changes
        renderMenu();
        renderToolbar();
        frame.repaint();
        imagePanel.repaint();
    }

    /**
     * <p>
     * Main entry point to the ANDIE program.
     * </p>
     * 
     * <p>
     * Creates and launches the main GUI in a separate thread.
     * As a result, this is essentially a wrapper around {@code createAndShowGUI()}.
     * </p>
     * 
     * @param args Command line arguments, not currently used
     * @throws Exception If something goes awry
     * @see #createAndShowGUI()
     */
    public static void main(String[] args) throws Exception {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.exit(1);
                }
            }
        });
    }

}
