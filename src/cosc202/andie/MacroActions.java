package cosc202.andie;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Class keeping track of all the {@link ImageAction}s concerning macros, and providing a drop-down menu to the GUI.
 * <p>
 * Actions involve letting the user record the operations they apply to their image, and save them in a {@link Macro} for reuse.
 * 
 * 
 * @author Mathias Øgaard 
 */
public class MacroActions{

    /**
     * A list of actions for the Macro menu.
     */
    public ArrayList<Action> actions;    
    
    /** 
    * The main GUI frame. Only here so that we can pack the 
    * frame when we rotate an image.
    */
   private JFrame frame;

    /**
     * <p>
     * Constructs the list of macro actions.
     * </p>
     */
    public MacroActions(JFrame frame){
        actions = new ArrayList<Action>();
        this.frame = frame;
        actions.add(new StartRecordingAction(LanguageActions.getLocaleString("initrecord"), null, LanguageActions.getLocaleString("initrecorddesc"), Integer.valueOf(KeyEvent.VK_8)));
        actions.add(new StopRecordingAction(LanguageActions.getLocaleString("endrecord"), null, LanguageActions.getLocaleString("endrecorddesc"), Integer.valueOf(KeyEvent.VK_9)));
        actions.add(new ApplyMacroAction(LanguageActions.getLocaleString("applymacro"), null, LanguageActions.getLocaleString("applymacrodesc"), Integer.valueOf(KeyEvent.VK_L)));
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

        for (Action action: actions) {
            menu.add(new JMenuItem(action));
        }

        return menu;
    }
    

    /**
     * Action for starting a recording of {@link ImageOperation}s.
     * <p>
     * The recording should take note of all operations that the user applies to the image, and put them into an ordered {@link java.awt.List}.
     * <p>
     * When performed, this should add an {@link OperationRecorder} to the target {@link ImagePanel}, and show the user that a recording is initiated.
     * 
     * @author Mathias Øgaard
     */
    public class StartRecordingAction extends ImageAction{

        /**
         * Constructs a {@link StartRecordingAction}.
         */
        public StartRecordingAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_8, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
            
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (target.ongoingRecording){
                JOptionPane.showMessageDialog(null, LanguageActions.getLocaleString("recorderror"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
                return;
            }
            IOperationRecorder rec = new OperationRecorder();
            target.getImage().addPropertyChangeListener("ops",rec);
            target.ongoingRecording = true;
            // TODO Add some graphics to show the user that recording has started.
        }
        
    }
    
    /**
     * Action for finnishing a recording of {@link ImageOperation}s.
     * <p>
     * If there is an active {@link OperationRecorder} on the target {@link ImagePanel}, this action should detach it from the panel, and use its recorded information to build a {@link Macro}.
     * <p>
     * When performed, this should also remove any graphics in the GUI that indicates an ongoing recording.
     * <p>
     * The user should also be given the opportunity to save the macro as a file.
     * 
     * @author Mathias Øgaard
     */
    public class StopRecordingAction extends ImageAction{

        /**
         * Constructs a {@link StopRecordingAction}.
         */
        protected StopRecordingAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_9, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            EditableImage targetImage = target.getImage();

            // Retrieve the recording from the ImagePanel -------------------------
            if(!targetImage.hasListeners("ops")){
                JOptionPane.showMessageDialog(null, LanguageActions.getLocaleString("stoprecorderror"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
            }
            
            PropertyChangeListener[] plcs = targetImage.getPropertyChangeListeners("ops");

            if (plcs.length > 1){
                throw new UnsupportedOperationException("Couldn't finnish recording because there are more than one PropertyChangeListeners.");
            }
            if (!(plcs[0] instanceof IOperationRecorder)){
                throw new ClassCastException("Unknown PropertyChangeListener. Was not instance of IOperationRecorder.");
            }

            IOperationRecorder rec = (IOperationRecorder) plcs[0];
            //---------------------------------------------------------------------

            //If no operations were recorded, ask user if they really want to stop the recording
            if(rec.getOps().size() < 1){
                int choice = JOptionPane.showOptionDialog(null,LanguageActions.getLocaleString("norecord"), LanguageActions.getLocaleString("error"), 
                                                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (choice == JOptionPane.NO_OPTION)
                    return;
            }
            //----------------------------------------------------------------------


            // Build Macro based on the recording ----------------------------------
            IMacro m = new Macro();
            for(int i=0; i<rec.getOps().size(); i++){
                ImageOperation currentOp = rec.getOps().get(i);
                m.addOp(currentOp);
            }
            //----------------------------------------------------------------------
            

            // Close recording -----------------------------------------------------
            targetImage.removePropertyChangeListener("ops",rec);
            target.ongoingRecording = false;
            // TODO Remove any graphics indicating an ongoing recording
            //----------------------------------------------------------------------


            // Give the user an option to save the macro ---------------------------
            try {
                int saveOrNot = JOptionPane.showOptionDialog(null, LanguageActions.getLocaleString("wantsave"), LanguageActions.getLocaleString("save"), 
                                                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (saveOrNot == JOptionPane.YES_OPTION)
                    saveMacro(m);
            } catch (HeadlessException he) {
                System.exit(1);
            } 
            //-----------------------------------------------------------------------
        }

        /**
         * Method to give the user an option to save a set of operations as a macro.
         */
        private void saveMacro(IMacro m) {
            String filename = JOptionPane.showInputDialog(null, LanguageActions.getLocaleString("macrosaveas"),LanguageActions.getLocaleString("savemacro"), JOptionPane.QUESTION_MESSAGE);
            try {
                FileOutputStream fileOut = new FileOutputStream(filename + ".ops");
                ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
                objOut.writeObject(m);
                objOut.close();
                fileOut.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, LanguageActions.getLocaleString("savemacroerror"), LanguageActions.getLocaleString("error"), JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Action for loading a {@link IMacro} from a file, and apply it to the target image.
     * <p>
     * If there is an active {@link IOperationRecorder}, all the operations from this {@link IMacro} should be added to the recording in the correct order.
     * <p>
     * @author Mathias Øgaard
     */
    public class ApplyMacroAction extends ImageAction{

        protected ApplyMacroAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_L, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(target);

            if(result == JFileChooser.APPROVE_OPTION){
                File macroFile = fileChooser.getSelectedFile();
                try {
                    FileInputStream fileIn = new FileInputStream(macroFile);
                    ObjectInputStream objIn = new ObjectInputStream(fileIn);

                    Object obj = objIn.readObject(); 
                    if(obj instanceof IMacro){
                        IMacro macro = (IMacro) obj;
                        target.getImage().apply(macro);

                        target.repaint();
                        target.getParent().revalidate();
                        // Reset the zoom of the image.
                        target.setZoom(100);
                        // Pack the main GUI frame to the size of the image.
                        frame.pack();
                        // Make main GUI frame centered on screen.
                        frame.setLocationRelativeTo(null);

                    }else{
                        JOptionPane.showMessageDialog(null, LanguageActions.getLocaleString("filenomacro"), LanguageActions.getLocaleString("invalidfile"), JOptionPane.ERROR_MESSAGE);
                    }

                    objIn.close();
                    fileIn.close();
                } catch (Exception ex) {
                    System.exit(1);
                }
            }
        }
    }
}
