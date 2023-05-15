package cosc202.andie;

import java.util.*;
import javax.swing.*;

public class SelectionActions {
    protected ArrayList<Action> actions;
    public int startX, startY, endX, endY;

    /**
     * <p>
     * Create a set of Selection menu actions.
     * </p>
     */
    public SelectionActions() {
        actions = new ArrayList<Action>();

    }

    /**
     * <p>
     * Create a menu contianing the list of Selection actions.
     * </p>
     * 
     * @return The colour menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(LanguageActions.getLocaleString("selection"));

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

}
