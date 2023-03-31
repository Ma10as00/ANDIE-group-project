package cosc202.andie;

import java.awt.*;
import java.awt.event.*;
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

    /** An ImagePanel. */
    private static ImagePanel imagePanel;

    /**
     * <p>
     * Launches the main GUI for the ANDIE program.
     * </p>
     * 
     * <p>
     * This method sets up an interface consisting of an active image (an
     * {@code ImagePanel})
     * and various menus which can be used to trigger operations to load, save,
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
     * 
     * @throws Exception if something goes wrong.
     */
    private static void createAndShowGUI() throws Exception {
        // Set up the main GUI frame
        JFrame frame = new JFrame("ANDIE");

        Image image = ImageIO.read(Andie.class.getClassLoader().getResource("icon.png"));
        frame.setIconImage(image);
        // Changed default close operation to DO_NOTHING_ON_CLOSE
        // so that a WindowListener can handle the operation 
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frameClosing();
            }
        });

        
        // Note, I deleted the ImagePanel instatiation here so that there 
        // is a static data feild for the ImagePanel instead. This means
        // the user windowClosing method can access the ImagePanel as well.

        // The main content area is an ImagePanel.
        imagePanel = new ImagePanel();
        ImageAction.setTarget(imagePanel);
        JScrollPane scrollPane = new JScrollPane(imagePanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Add in menus for various types of action the user may perform.
        JMenuBar menuBar = new JMenuBar();

        // File menus are pretty standard, so things that usually go in File menus go
        // here. We pass a frame so that when we open an image, the frame is packed to the new image size.
        FileActions fileActions = new FileActions(frame);
        menuBar.add(fileActions.createMenu());

        // Likewise Edit menus are very common, so should be clear what might go here.
        EditActions editActions = new EditActions();
        menuBar.add(editActions.createMenu());

        // View actions control how the image is displayed, its zoom, but do not alter its actual content
        ViewActions viewActions = new ViewActions();
        menuBar.add(viewActions.createMenu());

        // Orientation actions change the orientation of the image, altering its content.
        OrientationActions orientationActions = new OrientationActions();
        menuBar.add(orientationActions.createMenu());

        // Resize actions change the size of the image, altering its content.
        ResizeActions resizeActions = new ResizeActions();
        menuBar.add(resizeActions.createMenu());

        // Filters apply a per-pixel operation to the image, generally based on a local window.
        FilterActions filterActions = new FilterActions();
        menuBar.add(filterActions.createMenu());

        // Actions that affect the representation of colour in the image.
        ColourActions colourActions = new ColourActions();
        menuBar.add(colourActions.createMenu());

        frame.setJMenuBar(menuBar);
        frame.pack();
        // Make window centered on screen.
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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
                // Headless exception, thrown when the code is dependent on a keyboard or mouse. 
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
