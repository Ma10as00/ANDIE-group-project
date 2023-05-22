package cosc202.andie;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.util.ArrayList;
import java.nio.file.*;

import javax.swing.*;

/**
 * <p>
 * Class keeping track of all the {@link ImageAction}s concerning macros, and
 * providing a drop-down menu to the GUI.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * <p>
 * Actions involve letting the user record the operations they apply to their
 * image, and save them in a {@link Macro} for reuse.
 * </p>
 * 
 * @author Mathias Øgaard (Modified by Stella Srzich)
 */
public class MacroActions {

    /**
     * A list of actions for the Macro menu.
     */
    public ArrayList<Action> actions;

    /**
     * <p>
     * Constructs the list of macro actions.
     * </p>
     */
    public MacroActions() {
        actions = new ArrayList<Action>();
        actions.add(new StartRecordingAction(LanguageActions.getLocaleString("initrecord"), null,
                LanguageActions.getLocaleString("initrecorddesc"), Integer.valueOf(KeyEvent.VK_8)));
        actions.add(new StopRecordingAction(LanguageActions.getLocaleString("endrecord"), null,
                LanguageActions.getLocaleString("endrecorddesc"), Integer.valueOf(KeyEvent.VK_9)));
        actions.add(new ApplyMacroAction(LanguageActions.getLocaleString("applymacro"), null,
                LanguageActions.getLocaleString("applymacrodesc"), Integer.valueOf(KeyEvent.VK_L)));
    }

    /**
     * <p>
     * Create a menu containing the list of Macro actions.
     * </p>
     * 
     * @return The macro menu UI element.
     */
    public JMenu createMenu() {
        JMenu menu = new JMenu(LanguageActions.getLocaleString("macro"));

        for (Action action : actions) {
            JMenuItem item = new JMenuItem(action);
            item.setBorderPainted(false);
            menu.add(item);
        }

        return menu;
    }

    /**
     * <p>
     * Method to check whether a given file name is a valid ops.png file name. That
     * is,
     * if it ends in .ops, only contains a single '.', and has characters before
     * '.ops'.
     * This is used by {@link ApplyMacroAction} and {@link StopRecordingAction}.
     * </p>
     * 
     * @param opsFilename The ops file name to check if valid.
     * @return true if {@link opsFileName} is a valid Ops file name, false
     *         otherwise.
     */
    private boolean isValidOpsName(String opsFilename) {
        boolean isOpsFile = true;
        // Set this up so that I can extract just the file name
        // as mac and windows have different ways of naming the canonical path.
        String justFilename = "";
        try {
            Path dummyPath = Paths.get(opsFilename);
            justFilename = dummyPath.getFileName().toString();
        } catch (InvalidPathException e) {
            // Occurs in imageFilename cannot be converted to a path. This won't happen,
            // but just incase return false, as it will not be a valid PNG name
            return false;
        }

        if (justFilename.contains(".") == false) {
            // The image file name has no '.', cannot be a valid image file name.
            isOpsFile = false;
        } else {
            String extension = justFilename.substring(justFilename.lastIndexOf(".")).toLowerCase();
            if (!extension.equals(".ops")) {
                // The image file name extension is not valid as it doesn't end in .ops.
                isOpsFile = false;
            }
            if (justFilename.lastIndexOf(".") != justFilename.indexOf(".")) {
                // There are is more than one '.' in file name, so the image file name is
                // nvalid.
                isOpsFile = false;
            }
            if (justFilename.equals(".ops")) {
                // The name of the image is null, i.e. the file name is ".ops". This is not
                // valid.
                isOpsFile = false;
            }
        }
        return isOpsFile;
    }

    /**
     * <p>
     * Method to check whether a given file name is already an ops file name.
     * This is used by {@link StopRecordingAction}.
     * to check if there is a possibility of writting over another file in the same
     * directory.
     * </p>
     * 
     * @param opsFilename The image file name to check if valid.
     * @return true if {@link opsFileName} is a valid Ops file name, false
     *         otherwise.
     */
    private boolean isExistingFilename(String opsFilename) {
        try {
            // This just attempts to read in an ops file.
            File opsFilepath = new File(opsFilename);
            FileInputStream fileIn = new FileInputStream(opsFilepath);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);
            Object obj = objIn.readObject();
            objIn.close();
            fileIn.close();
            if (obj instanceof IMacro) {
                // This will happen if the file exists, return true.
                return true;
            }
        } catch (Exception e) {
            // This will happen if the file doesn't already exist, return false.
            return false;
        }
        // This will happen if the file doesn't already exist, return false.
        return false;
    }

    /**
     * <p>
     * Action for starting a recording of {@link ImageOperation}s.
     * </p>
     * 
     * <p>
     * The recording should take note of all operations that the user applies to the
     * image, and put them into an ordered {@link java.awt.List}.
     * When performed, this should add an {@link OperationRecorder} to the target
     * {@link ImagePanel}, and show the user that a recording is initiated.
     * </p>
     * 
     * @author Mathias Øgaard
     */
    public class StartRecordingAction extends ImageAction {

        /**
         * Constructing instance of {@link StartRecordingAction}, as defined in
         * {@link ImageAction#ImageAction(String, ImageIcon, String, Integer)}.
         * 
         * @param name
         * @param icon
         * @param desc
         * @param mnemonic
         */
        public StartRecordingAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            this.putValue(Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_8, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Check if there is an image open.
            if (!target.getImage().hasImage()) {
                // There is not an image open, don't allow the user to initiate a recording.
                try {
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("macroImageErr"),
                            LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse.
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
            }
            // Check if a macro recording is ongoing.
            if (target.ongoingRecording) {
                JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("recorderror"),
                        LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                return;
            }
            IOperationRecorder rec = new OperationRecorder();
            target.getImage().addPropertyChangeListener("ops", rec);
            target.ongoingRecording = true;

            // TO DO Add some graphics to show the user that recording has started.
        }

    }

    /**
     * <p>
     * Action for finnishing a recording of {@link ImageOperation}s.
     * </p>
     * 
     * <p>
     * If there is an active {@link OperationRecorder} on the target
     * {@link ImagePanel}, this action should detach it from the panel, and use its
     * recorded information to build a {@link Macro}.
     * </p>
     * 
     * <p>
     * When performed, this should also remove any graphics in the GUI that
     * indicates an ongoing recording.
     * The user should also be given the opportunity to save the macro as a file.
     * </p>
     * 
     * @author Mathias Øgaard
     */
    public class StopRecordingAction extends ImageAction {

        /**
         * Constructing instance of {@link StopRecordingAction}, as defined in
         * {@link ImageAction#ImageAction(String, ImageIcon, String, Integer)}.
         * 
         * @param name
         * @param icon
         * @param desc
         * @param mnemonic
         */
        protected StopRecordingAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            this.putValue(Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_9, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            EditableImage targetImage = target.getImage();

            // Retrieve the recording from the ImagePanel.
            if (!targetImage.hasListeners("ops")) {
                JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("stoprecorderror"),
                        LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
            }
            PropertyChangeListener[] plcs = targetImage.getPropertyChangeListeners("ops");
            if (plcs.length > 1) {
                throw new UnsupportedOperationException(
                        "Couldn't finnish recording because there are more than one PropertyChangeListeners.");
            }
            if (!(plcs[0] instanceof IOperationRecorder)) {
                throw new ClassCastException("Unknown PropertyChangeListener. Was not instance of IOperationRecorder.");
            }
            IOperationRecorder rec = (IOperationRecorder) plcs[0];

            // If no operations were recorded, ask user if they really want to stop the
            // recording.
            if (rec.getOps().size() < 1) {
                int choice = JOptionPane.showOptionDialog(Andie.frame, LanguageActions.getLocaleString("norecord"),
                        LanguageActions.getLocaleString("error"),
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (choice == JOptionPane.NO_OPTION)
                    return;
            }

            // Build Macro based on the recording.
            IMacro m = new Macro();
            for (int i = 0; i < rec.getOps().size(); i++) {
                ImageOperation currentOp = rec.getOps().get(i);
                m.addOp(currentOp);
            }

            // Close recording.
            targetImage.removePropertyChangeListener("ops", rec);
            target.ongoingRecording = false;

            // TO DO Remove any graphics indicating an ongoing recording

            // Give the user an option to save the macro.
            try {
                int saveOrNot = JOptionPane.showOptionDialog(null,
                        LanguageActions.getLocaleString("wantsave") + System.lineSeparator() + m,
                        LanguageActions.getLocaleString("save"),
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (saveOrNot == JOptionPane.YES_OPTION) {
                    // We loop until the file is successfully saved or the user actively cancelled,
                    // just in case they entered the wrong file name but don't want to lose the
                    // macro.
                    boolean done = false;
                    while (!done) {
                        done = saveMacro(m);
                    }
                }
            } catch (HeadlessException he) {
                System.exit(1);
            }
        }

        /**
         * Method to give the user an option to save a set of operations as a macro.
         * This will return true
         * if the user has sucessfully saved the macro or has decided to cancel saving
         * the macro. It will return false
         * if the user has not saved the macro for any other reason. For example, if
         * they accidentally used an invalid
         * file name.
         * 
         * @param m The IMacro being saved.
         * @return True if the user successfully saved the file or decided to cancel
         *         saving the file. False otherwise.
         */
        private boolean saveMacro(IMacro m) {
            // Ask the user if they want to save the macros. Did not do this here as always
            // will want to.
            // String filename = JOptionPane.showInputDialog(null,
            // LanguageActions.getLocaleString("macrosaveas"),LanguageActions.getLocaleString("savemacro"),
            // JOptionPane.QUESTION_MESSAGE);
            // Check if there is an image open.
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message, and do not save.
                try {
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("errorNoImageAs"),
                            LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse.
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
                return true;
            }
            // There is an image open, carry on.
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(target);
            // This is used to keep track of if the user wants to override a file.
            boolean override = true;
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String opsFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    // Check that the ops file name is valid.
                    if (isValidOpsName(opsFilepath) == false) {
                        // The image file name is not valid. Show error message and do not save as.
                        JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("opsSyntaxError"),
                                LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                        // We return false as the user probably doesn't want to lose the recorded
                        // macros.
                        return false;
                    }
                    // Check that the ops file name does not describe an image that already exists.
                    else if (isExistingFilename(opsFilepath)) {
                        // The ops file name already describes another file name.
                        // Ask user if they want to override or cancel.
                        int option = JOptionPane.showConfirmDialog(Andie.frame,
                                LanguageActions.getLocaleString("warningAnotherFile"),
                                LanguageActions.getLocaleString("warning"), JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.WARNING_MESSAGE);
                        if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
                            // User cancelled or closed the pop up, allow them to try to save again.
                            return false;
                        }
                    }
                    if (override) {
                        // Save the ops file.
                        FileOutputStream fileOut = new FileOutputStream(opsFilepath);
                        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
                        objOut.writeObject(m);
                        objOut.close();
                        fileOut.close();
                        // At this point, the macro is succsefully saved. Return true.
                        return true;
                    }
                } catch (HeadlessException eh) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse.
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                } catch (Exception ex) {
                    // There would have been an error in getting canonical pathname.
                    // Just let the user know. Probably won't happen.
                    try {
                        JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("savemacroerror"),
                                LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                    } catch (HeadlessException eh) {
                        // Headless exception, thrown when the code is dependent on a keyboard or mouse.
                        // Won't happen for our users, so just exit.
                        System.exit(1);
                    }
                }
            } else {
                // The user closed the window.
                return true;
            }
            // At this point, there was an error. Return false to allow the user to try
            // again.
            return false;
        }
    }

    /**
     * <p>
     * Action for loading a {@link IMacro} from a file, and apply it to the target
     * image.
     * </p>
     * 
     * <p>
     * If there is an active {@link IOperationRecorder}, all the operations from
     * this {@link IMacro} should be added to the recording in the correct order.
     * </p>
     * 
     * @author Mathias Øgaard
     */
    public class ApplyMacroAction extends ImageAction {

        /**
         * Constructing instance of {@link ApplyMacroAction}, as defined in
         * {@link ImageAction#ImageAction(String, ImageIcon, String, Integer)}.
         * 
         * @param name
         * @param icon
         * @param desc
         * @param mnemonic
         */
        protected ApplyMacroAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            this.putValue(Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_L, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // We loop until the file is successfully opened or the user actively cancelled,
            // just in case they clicked the wrong file name but don't want to have to click
            // apply macro again.
            boolean done = false;
            while (!done) {
                done = openMacro();
            }
        }

        /**
         * This is a support method to open an {@link IMacro} from a file.
         */
        private boolean openMacro() {
            // Check if there is an image to export
            if (target.getImage().hasImage() == false) {
                // There is not an image open, so display error message.
                try {
                    JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("errorNoOpen"),
                            LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse.
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                }
                // No image was open, so don't ask them to open another macro.
                return true;
            }

            // User has an image open, so we can apply macros.
            // So, we attempt to open an ops file.
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String opsFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    // First, check that the file trying to be opened is an ops file.
                    if (isValidOpsName(opsFilepath) == false) {
                        // The ops file name is not valid. Show error message and do not open.
                        JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("errorNotOps"),
                                LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                        // The file opened was not a macro file. Allow the user to try again.
                        return false;
                    }
                    File macroFile = fileChooser.getSelectedFile();
                    // Apply the ops file.
                    try {
                        FileInputStream fileIn = new FileInputStream(macroFile);
                        ObjectInputStream objIn = new ObjectInputStream(fileIn);

                        Object obj = objIn.readObject();
                        if (obj instanceof IMacro) {
                            IMacro macro = (IMacro) obj;
                            target.getImage().apply(macro);

                            ImagePanel.rect = null;
                            target.repaint();
                            target.getParent().revalidate();
                        } else {
                            JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("filenomacro"),
                                    LanguageActions.getLocaleString("invalidfile"), JOptionPane.ERROR_MESSAGE);
                        }
                        objIn.close();
                        fileIn.close();
                    } catch (Exception ex) {
                        System.exit(1);
                    }
                } catch (HeadlessException eh) {
                    // Headless exception, thrown when the code is dependent on a keyboard or mouse.
                    // Won't happen for our users, so just exit.
                    System.exit(1);
                } catch (Exception ex) {
                    // There would have been an error in getting canonical pathname.
                    // Just let the user know. Probably won't happen.
                    try {
                        JOptionPane.showMessageDialog(Andie.frame, LanguageActions.getLocaleString("errorOpenFile"),
                                LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                    } catch (HeadlessException eh) {
                        // Headless exception, thrown when the code is dependent on a keyboard or mouse.
                        // Won't happen for our users, so just exit.
                        System.exit(1);
                    }
                }
            }
            // There was either an error opening the .ops file, or the user sucessfully
            // opened an .ops file,
            // or the user clicked cancel. So, don't make the user try again.
            return true;
        }
    }
}
