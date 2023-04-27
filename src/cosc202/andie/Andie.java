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
    private static ImagePanel imagePanel;
    /** A JFrame of the main GUI frame. */
    private static JFrame frame;

    

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

        //Sets the starting language to NZ English
        Preferences prefs = Preferences.userNodeForPackage(Andie.class);
        String lang = prefs.get("language", "en");
        String country = prefs.get("country", "NZ");
        String languageCode = lang + "_" + country;

        // If language code is en sets default to English.
        if(languageCode.equals("en_NZ")){
            // Set Default Locale to English
            Locale.setDefault(new Locale("en", "NZ"));
        }
        // If language code is mi set default language to Maori.
        else if(languageCode.equals("mi_NZ")){
            Locale.setDefault(new Locale("mi", "NZ"));
        }
        // If language code is no sets default to Norwegian.
        else if(languageCode.equals("no_NO")){
            Locale.setDefault(new Locale("no", "NO"));
        }
        // If language code is es sets default to Spanish.
        else if(languageCode.equals("sp_ES")){
            Locale.setDefault(new Locale("sp", "ES"));
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
        imagePanel = new ImagePanel();
        ImageAction.setTarget(imagePanel);
        JScrollPane scrollPane = new JScrollPane(imagePanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Calls renderMenu method to render the menu in the selected language.
        renderMenu();
        
        frame.pack();
        // Make window centered on screen.
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
    }

    /**
     * <p>
     * Constructs the JMenuBar separately so the method can be recalled when the language is changed.
     * </p>
     * 
     * <p>
     * This method sets up various menus which can be used to trigger operations to load, save,
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
     * @see OrientationActions
     * @see ResizeActions 
     * @see LanguageActions 
     * 
     */
    public static void renderMenu() {
        // Add in menus for various types of action the user may perform.
        JMenuBar menuBar = new JMenuBar();

        // File menus are pretty standard, so things that usually go in File menus go
        // here. We pass a frame so that when we open an image, the frame is packed to the new image size.
        FileActions fileActions = new FileActions(frame);
        menuBar.add(fileActions.createMenu());

        // Likewise Edit menus are very common, so should be clear what might go here.
        // We pass a frame so that when we undo or redo operations on an image, possiby changing its size the frame is packed to the new image size.
        EditActions editActions = new EditActions(frame);
        menuBar.add(editActions.createMenu());

        // View actions control how the image is displayed, its zoom, but do not alter its actual content
        ViewActions viewActions = new ViewActions();
        menuBar.add(viewActions.createMenu());

        // Orientation actions change the orientation of the image, altering its content.
        // We pass a frame so that when we rotate an image, possiby changing its size the frame is packed to the new image size.
        OrientationActions orientationActions = new OrientationActions(frame);
        menuBar.add(orientationActions.createMenu());

        // Resize actions change the size of the image, altering its content.
        // We pass a frame so that when we resize an image, the frame is packed to the new image size.
        ResizeActions resizeActions = new ResizeActions(frame);
        menuBar.add(resizeActions.createMenu());

        // Filters apply a per-pixel operation to the image, generally based on a local window.
        FilterActions filterActions = new FilterActions();
        menuBar.add(filterActions.createMenu());

        // Actions that affect the representation of colour in the image.
        ColourActions colourActions = new ColourActions();
        menuBar.add(colourActions.createMenu());

        // Ability to change the language from a set of included language bundles.
        LanguageActions languageActions = new LanguageActions();
        menuBar.add(languageActions.createMenu());

        SelectionActions selectionActions = new SelectionActions();
        menuBar.add(selectionActions.createMenu()); 

        frame.setJMenuBar(menuBar);
        frame.pack();
    }

    /**
     * <p>
     * Handles a user closing the main GUI frame.
     * </p>
     * 
     * <p>
     * If the user trys to exit the main GUI frame with an image open, a warning dialogue box
     * will warn them that any unsaved changes will be lost. 
     * </p>
     */
    private static void frameClosing() {
        // Check if there is an image open.
        if (imagePanel.getImage().hasImage()) {
            // There is an image open, warn user that any unsaved changes will be deleted.
            try {
                int option = JOptionPane.showConfirmDialog(null, "If you exit without saving or exporting this image, any changes will be lost.", "Warning", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                if (option == JOptionPane.OK_OPTION) {
                    // User clicked ok, exit.
                    System.exit(0);
                }
            }
            catch (HeadlessException ex) {
                // Headless exception, thrown when the code is dependent on a keyboard or mouse, as with confrim dialog.
                // Won't happen for our users, so just exit.
                System.exit(1);
            }
        }
        else {
            // There is no image open, exit.
            System.exit(0);
        }
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
