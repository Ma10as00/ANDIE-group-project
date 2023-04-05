package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.nio.file.*;

/**
 * <p>
 * Actions provided by the File menu.
 * </p>
 * 
 * <p>
 * The File menu is very common across applications,
 * and there are several items that the user will expect to find here.
 * Opening and saving files is an obvious one, but also exiting the program.
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
public class FileActions {

    /** A list of actions for the File menu. */
    protected ArrayList<Action> actions;
    /**
     * The main GUI frame. Only here so that we can pack the
     * frame when we open a new image.
     */
    private JFrame frame;

    /**
     * <p>
     * Create a set of File menu actions.
     * </p>
     */
    public FileActions(JFrame frame) {
        actions = new ArrayList<Action>();
        this.frame = frame;
        actions.add(new FileOpenAction(LanguageActions.getLocaleString("open"), null,
                LanguageActions.getLocaleString("openDes"), Integer.valueOf(KeyEvent.VK_O)));
        actions.add(new FileSaveAction(LanguageActions.getLocaleString("save"), null,
                LanguageActions.getLocaleString("saveDes"), Integer.valueOf(KeyEvent.VK_S)));
        actions.add(new FileSaveAsAction(LanguageActions.getLocaleString("saveAs"), null,
                LanguageActions.getLocaleString("saveAsDes"), Integer.valueOf(KeyEvent.VK_A)));
        actions.add(new FileExportAction(LanguageActions.getLocaleString("export"), null,
                LanguageActions.getLocaleString("exportDes"), Integer.valueOf(KeyEvent.VK_E)));
        actions.add(new FileExitAction(LanguageActions.getLocaleString("exit"), null,
                LanguageActions.getLocaleString("exitDes"), Integer.valueOf(0)));
    }

    /**
     * <p>
     * Create a menu contianing the list of File actions.
     * </p>
     * 
     * @return The File menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(LanguageActions.getLocaleString("file"));

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Method to check whether a given file name is a valid PNG file name. That is,
     * if it ends in .png, only contains a single '.', and has characters before
     * '.png'.
     * This is used by {@link FileOpenAction}, {@link FileSaveAsAction},
     * {@link FileExportAction}.
     * </p>
     * 
     * @param imageFilename The image file name to check if valid.
     * @return true if {@link imageFileName} is a valid PNG file name, false
     *         otherwise.
     */
    private boolean isValidPNGName(String imageFilename) {
        boolean isPNGFile = true;
        // Set this up so that I can extract just the file name
        // as mac and windows have different ways of naming the canonical path.
        Path dummyPath = Paths.get(imageFilename);
        String justFilename = dummyPath.getFileName().toString();

        if (justFilename.contains(".") == false) {
            // The image file name has no '.', cannot be a valid image file name.
            isPNGFile = false;
        } else {
            String extension = justFilename.substring(justFilename.lastIndexOf(".")).toLowerCase();
            if (!extension.equals(".png")) {
                // The image file name extension is not valid as it doesn't end in .png.
                isPNGFile = false;
            }
            if (justFilename.lastIndexOf(".") != justFilename.indexOf(".")) {
                // There are is more than one '.' in file name, so the image file name is
                // nvalid.
                isPNGFile = false;
            }
            if (justFilename.equals(".png")) {
                // The name of the image is null, i.e. the file name is ".png". This is not
                // valid.
                isPNGFile = false;
            }
        }

        return isPNGFile;
    }

    /**
     * <p>
     * Method to check whether a given file name is already an image file name.
     * This is used by {@link FileSaveAsAction} and {@link FileExportAction}
     * to check if there is a possibility of writting over another file in the same
     * directory.
     * </p>
     * 
     * @param imageFilename The image file name to check if valid.
     * @return true if {@link imageFileName} is a valid PNG file name, false
     *         otherwise.
     */
    private boolean isExistingFilename(String imageFilename) {
        try {
            // This just attempts to read in an image from a file.
            File imageFile = new File(imageFilename);
            BufferedImage dummyImage = ImageIO.read(imageFile);
        } catch (Exception e) {
            // This will happen if the file doesn't already exist, return false.
            return false;
        }
        return true;
    }

    /**
     * <p>
     * Action to open an image from file.
     * </p>
     * 
     * @see EditableImage#open(String)
     */
    public class FileOpenAction extends ImageAction {

        /**
         * <p>
         * Create a new file-open action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileOpenAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-open action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileOpenAction is triggered.
         * It prompts the user to select a file and opens it as an image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage()) {
                // There is an image open, warn user that any unsaved changes will be deleted.
                try {
                    int option = JOptionPane.showConfirmDialog(null,
                            "If you open another image without saving or exporting this image, any changes will be lost.",
                            "Warning", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
                        // User cancelled or closed box, don't open an image.
                        return;
                    }
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse.
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            }

            // User either had no image open, or had an image open but decided to still open
            // another one.
            // So, we attempt to open an image file.
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    // First, check that the file trying to be opened is a png image
                    if (isValidPNGName(imageFilepath) == false) {
                        // The image file name is not valid. Show error message and do not open.
                        JOptionPane.showMessageDialog(null, "You have not selected a PNG image file.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // Open the image file and any associated image operations file.
                    target.getImage().open(imageFilepath);
                } catch (HeadlessException eh) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse.
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                } catch (Exception ex) {
                    // There would have been an error in getting canonical pathname.
                    // Just let the user know. Probably won't happen.
                    try {
                        JOptionPane.showMessageDialog(null, "Sorry, there has been an error in opening the file.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (HeadlessException eh) {
                        // Headless exception, thrown when the code is dependent on a keyboard or mouse.
                        // Won't happen for our users, so just exit.
                        System.exit(1);
                    }
                }
            }

            target.repaint();
            target.getParent().revalidate();
            // Pack the main GUI frame to the size of the newly opened image.
            frame.pack();
            // Make main GUI frame centered on screen
            frame.setLocationRelativeTo(null);
        }

    }

    /**
     * <p>
     * Action to save an image to its current file location.
     * </p>
     * 
     * @see EditableImage#save()
     */
    public class FileSaveAction extends ImageAction {

        /**
         * <p>
         * Create a new file-save action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileSaveAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-save action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileSaveAction is triggered.
         * It saves the image to its original filepath.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                JOptionPane.showMessageDialog(null, "There is no image open to save.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                // There is an image open, carry on.
                target.getImage().save();
            }
        }

    }

    /**
     * <p>
     * Action to save an image to a new file location.
     * </p>
     * 
     * @see EditableImage#saveAs(String)
     */
    public class FileSaveAsAction extends ImageAction {

        /**
         * <p>
         * Create a new file-save-as action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileSaveAsAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-save-as action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileSaveAsAction is triggered.
         * It prompts the user to select a file and saves the image to it.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message, and do not save as.
                JOptionPane.showMessageDialog(null, "There is no image open to save as.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            // There is an image open, carry on.
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(target);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    // Check that the image file name is valid.
                    if (isValidPNGName(imageFilepath) == false) {
                        // The image file name is not valid. Show error message and do not save as.
                        JOptionPane.showMessageDialog(null,
                                "You have not entered a valid PNG image file name.\n(The name must end with .png, cannot contain any other '.', and must contain characters preceeding '.png')",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Check that the image file name does not describe an image that already
                    // exists.
                    if (isExistingFilename(imageFilepath)) {
                        // The image file name already describes another file name.
                        // Ask user if they want to override or cancel.
                        try {
                            int option = JOptionPane.showConfirmDialog(null,
                                    "Another file already exists with the same name in this directory.\nClick OK to replace it, or cancel.",
                                    "Warning", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                            if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
                                // User cancelled or closed the pop up, don't export.
                                return;
                            }
                        } catch (HeadlessException ex) {
                            // Headless exception, thrown when the code is dependent on a keyboard or mouse.
                            // Won't happen for our users, so just exit.
                            System.exit(1);
                        }
                    }

                    target.getImage().saveAs(imageFilepath);
                } catch (HeadlessException eh) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse.
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                } catch (Exception ex) {
                    // There would have been an error in getting canonical pathname.
                    // Just let the user know. Probably won't happen.
                    try {
                        JOptionPane.showMessageDialog(null, "Sorry, there has been an error in saving the file as.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (HeadlessException eh) {
                        // Headless exception, thrown when the code is dependent on a keyboard or mouse.
                        // Won't happen for our users, so just exit.
                        System.exit(1);
                    }
                }
            }
        }

    }

    /**
     * <p>
     * Action to quit the ANDIE application.
     * </p>
     */
    public class FileExitAction extends ImageAction {

        /**
         * <p>
         * Create a new file-exit action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileExitAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            // Note, this now extends ImageAction instead of AbstractAction so it can
            // access the target ImagePanel
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-exit action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileExitAction is triggered.
         * It quits the program.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (target.getImage().hasImage()) {
                // There is an image open, warn user that any unsaved changes will be deleted.
                try {
                    int option = JOptionPane.showConfirmDialog(null,
                            "If you exit without saving or exporting, any changes will be lost.", "Warning",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (option == JOptionPane.CANCEL_OPTION) {
                        // User cancelled, don't exit.
                        return;
                    }
                    if (option == JOptionPane.OK_OPTION) {
                        // User clicked ok, exit.
                        System.exit(0);
                    }
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse.
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            } else {
                // If there is no image open, exit.
                System.exit(0);
            }
        }

    }

    public class FileExportAction extends ImageAction {

        /**
         * <p>
         * Create a new file-export action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileExportAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            // Check if there is an image to export
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                JOptionPane.showMessageDialog(null, "There is no image open to export.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // There is an image open, carry on.
            // Allow user to name image file and select location to export to.
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    // Get file path as inputted by user.
                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    // Check that the image file name is valid.
                    if (isValidPNGName(imageFilepath) == false) {
                        // The image file name is not valid. Show error message and do not export.
                        JOptionPane.showMessageDialog(null,
                                "You have not entered a valid PNG image file name.\n(The name must end with .png, cannot contain any other '.', and must contain characters preceeding '.png')",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Check that the image file name does not describe an image that already
                    // exists.
                    if (isExistingFilename(imageFilepath)) {
                        // The image file name already describes another file name.
                        // Ask user if they want to override or cancel.
                        try {
                            int option = JOptionPane.showConfirmDialog(null,
                                    "Another file already exists with the same name in this directory.\nClick OK to replace it, or cancel.",
                                    "Warning", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                            if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
                                // User cancelled or closed the pop up, don't export.
                                return;
                            }
                        } catch (HeadlessException ex) {
                            // Headless exception, thrown when the code is dependent on a keyboard or mouse.
                            // Won't happen for our users, so just exit.
                            System.exit(1);
                        }
                    }

                    // Export the image.
                    target.getImage().export(imageFilepath);
                } catch (HeadlessException eh) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse.
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                } catch (Exception ex) {
                    // There would have been an error in getting canonical pathname.
                    // Just let the user know. Probably won't happen.
                    try {
                        JOptionPane.showMessageDialog(null, "Sorry, there has been an error in exporting the file.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (HeadlessException eh) {
                        // Headless exception, thrown when the code is dependent on a keyboard or mouse.
                        // Won't happen for our users, so just exit.
                        System.exit(1);
                    }
                }
            }
        }
    }
}
