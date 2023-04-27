package cosc202.andie.macros;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;

import cosc202.andie.ImageAction;

public class MacroActions{
    
    public class InitMacroAction extends ImageAction{

        private OperationRecorder rec;

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
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
        }
        
    }
    
    public class FinnishMacroAction extends ImageAction{

        protected FinnishMacroAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            PropertyChangeListener[] plcs = target.getPropertyChangeListeners();
            if (plcs.length > 1)
                throw new UnsupportedOperationException("Couldn't finnish recording because there are more than one PropertyChangeListeners.");
            PropertyChangeListener plc = plcs[0];
            if (!(plc instanceof IOperationRecorder))
                throw new ClassCastException("Unknown PropertyChangeListener. Was not instance of IOperationRecorder.");
            IOperationRecorder rec = (IOperationRecorder) plc;
            //
            Macro m = new Macro();
            for(int i=0; i<rec.size(); i++){
                m.addOp(rec.get(i));
            }
            saveMacro();
            target.removePropertyChangeListener("image", rec);
        }

        private void saveMacro() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'saveMacro'");
        }

    }
}
