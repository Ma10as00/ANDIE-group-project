package cosc202.andie.macros;

import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.*;

import cosc202.andie.*;

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
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Constructs the list of macro actions.
     * </p>
     */
    public MacroActions(){
        actions = new ArrayList<Action>();
        // TODO Add language support
        actions.add(new InitMacroAction("Initiate recording", null, "Records all operations applied to the image after this button is pushed.", Integer.valueOf(KeyEvent.VK_8)));
        actions.add(new FinnishMacroAction("End recording", null, "Ends an ongoing recording, and gives you the option to save the recorded operations as a macro.", Integer.valueOf(KeyEvent.VK_9)));
        actions.add(new LoadNUseMacroAction("Apply macro", null, "Lets you load a saved macro and apply it to the image.", Integer.valueOf(KeyEvent.VK_L)));
    }

    /**
     * <p>
     * Create a menu containing the list of Macro actions.
     * </p>
     * 
     * @return The macro menu UI element.
     */
    public JMenu createMenu() {
        // TODO Add mult. language support
        JMenu menu = new JMenu("Macro");

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
    public class InitMacroAction extends ImageAction{

        private OperationRecorder rec;

        /**
         * Constructs an {@link InitMacroAction}.
         */
        public InitMacroAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
            rec = new OperationRecorder(target);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (target.getPropertyChangeListeners().length > 0){
                throw new UnsupportedOperationException("A recording is already initiated.");
            }
            target.addPropertyChangeListener("image",rec);
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
    public class FinnishMacroAction extends ImageAction{

        /**
         * Constructs a {@link FinnishMacroAction}.
         */
        protected FinnishMacroAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            // Retrieve the recording from the ImagePanel
            PropertyChangeListener[] plcs = target.getPropertyChangeListeners();
            if (plcs.length > 1){
                throw new UnsupportedOperationException("Couldn't finnish recording because there are more than one PropertyChangeListeners.");
            }
            if (plcs.length < 1){
                throw new UnsupportedOperationException("Couldn't find any recordings to finnish. Panel had no PropertyChangeListeners.");
            }
            if (!(plcs[0] instanceof IOperationRecorder)){ // TODO This should never happen, but currently it's always happening.
                throw new ClassCastException("Unknown PropertyChangeListener. Was not instance of IOperationRecorder.");
            }
            IOperationRecorder rec = (IOperationRecorder) plcs[0];

            // Build Macro based on the recording
            Macro m = new Macro();
            for(int i=0; i<rec.size(); i++){
                m.addOp(rec.get(i));
            }
            
            // Close recording
            target.removePropertyChangeListener("image", rec);
            // TODO Remove any graphics indicating an ongoing recording

            // Give the user an option to save the macro
            saveMacro();
        }

        /**
         * Method to give the user an option to save a set of operations as a macro.
         */
        private void saveMacro() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'saveMacro'");
        }

    }

    /**
     * Action for loading a {@link Macro} from a file, and apply it to the target image.
     * <p>
     * If there is an active {@link OperationRecorder}, all the operations from this {@link Macro} should be added to the recording in the correct order.
     * <p>
     * @author Mathias Øgaard
     */
    public class LoadNUseMacroAction extends ImageAction{

        protected LoadNUseMacroAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
        }

    }
}
