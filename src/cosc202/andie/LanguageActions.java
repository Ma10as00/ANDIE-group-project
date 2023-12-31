package cosc202.andie;

import java.util.*;
import java.util.prefs.Preferences;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Language.
 * </p>
 * 
 * <p>
 * The Language menu contains actions that can change the langauge used in
 * ANDIE. This
 * includes English, Māori, Spanish and Norwegian.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Michael Campbell
 */
public class LanguageActions {

    /**
     * A list of actions for the Language menu.
     */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Returns a term from the ResourceBundle with a given key.
     * </p>
     * 
     * @param key a string representing the term to be pulled from the bundle.
     * @return a given string from the bundle related to key.
     */
    public static String getLocaleString(String key) {
        ResourceBundle bundle = ResourceBundle.getBundle("cosc202.andie.MessageBundle");

        return bundle.getString(key);
    }

    /**
     * <p>
     * Construct a LanguageActions.
     * </p>
     */
    public LanguageActions() {
        actions = new ArrayList<Action>();
        actions.add(new EnglishAction("English", null, "Changes the language to English", null));
        actions.add(new MaoriAction("Māori", null, "Ka huri te reo ki te Maori", null));
        actions.add(new NorwegianAction("Norsk", null, "Endrer språket til norsk", null));
        actions.add(new GermanAction("Deutsch", null, "Ändert die Sprache auf Deutsch", null));
        actions.add(new SpanishAction("Español", null, "cambia el idioma a español", null));
    }

    /**
     * <p>
     * Create a menu contianing the list of Language actions.
     * </p>
     * 
     * @return The Language menu UI element.
     */
    public JMenu createMenu() {
        JMenu languageMenu = new JMenu(getLocaleString("language"));

        for (Action action : actions) {
            JMenuItem item = new JMenuItem(action);
            item.setBorderPainted(false);
            languageMenu.add(item);
        }

        return languageMenu;
    }

    /**
     * <p>
     * Action to change the language to English.
     * </p>
     * 
     */
    public class EnglishAction extends ImageAction {

        /**
         * <p>
         * Create a new English action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        EnglishAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the EnglishAction is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the EnglishAction is triggered.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            Language("english");

            // Calls renderMenu() to repaint the menu in English.
            Andie.renderMenu();

        }

    }

    /**
     * <p>
     * Action to change the language to Māori.
     * </p>
     * 
     */
    public class MaoriAction extends ImageAction {

        /**
         * <p>
         * Create a new Maori action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        MaoriAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the MaoriAction is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MaoriAction is triggered.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            Language("maori");

            // Calls renderMenu() to repaint the menu in Maori.
            Andie.renderMenu();

        }

    }

    /**
     * <p>
     * Action to change the language to Norwegian.
     * </p>
     * 
     */
    public class NorwegianAction extends ImageAction {

        /**
         * <p>
         * Create a new Norwegian action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        NorwegianAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the NorwegianAction is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the NorwegianAction is triggered.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            Language("norwegian");

            // Calls renderMenu() to repaint the menu in Norwegian.
            Andie.renderMenu();

        }

    }

    /**
     * <p>
     * Action to change the language to Spanish.
     * </p>
     * 
     * 
     */
    public class SpanishAction extends ImageAction {

        /**
         * <p>
         * Create a new Spanish action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SpanishAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the SpanishAction is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the SpanishAction is triggered.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            Language("spanish");

            // Calls renderMenu() to repaint the menu in Spanish.
            Andie.renderMenu();

        }

    }

    /**
     * <p>
     * Action to change the language to German.
     * </p>
     * 
     */
    public class GermanAction extends ImageAction {

        /**
         * <p>
         * Create a new German action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        GermanAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the GermanAction is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the GermanAction is triggered.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            Language("german");

            // Calls renderMenu() to repaint the menu in German.
            Andie.renderMenu();

        }

    }

    /**
     * <p>
     * A method to change the language to that provided in param lang.
     * </p>
     * 
     * @param lang a string representing each of the supported language bundles.
     */
    public void Language(String lang) {

        Preferences prefs = Preferences.userNodeForPackage(Andie.class);
        prefs.remove("language");
        prefs.remove("country");

        switch (lang.toLowerCase()) {
            case "english":
                prefs.put("language", "en");
                prefs.put("country", "NZ");
                Locale.setDefault(new Locale(prefs.get("language", "en"), prefs.get("country", "NZ")));
                break;
            case "maori":
                prefs.put("language", "mi");
                prefs.put("country", "NZ");
                Locale.setDefault(new Locale(prefs.get("language", "mi"), prefs.get("country", "NZ")));
                break;
            case "norwegian":
                prefs.put("language", "no");
                prefs.put("country", "NO");
                Locale.setDefault(new Locale(prefs.get("language", "no"), prefs.get("country", "NO")));
                break;
            case "spanish":
                prefs.put("language", "sp");
                prefs.put("country", "ES");
                Locale.setDefault(new Locale(prefs.get("language", "sp"), prefs.get("country", "ES")));
                break;
            case "german":
                prefs.put("language", "de");
                prefs.put("country", "DE");
                Locale.setDefault(new Locale(prefs.get("language", "de"), prefs.get("country", "DE")));
                break;
        }
    }
}
