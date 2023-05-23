package tests.test;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import cosc202.andie.BrightnessFilter;
import cosc202.andie.ConvertToGrey;
import cosc202.andie.ImageOperation;
import cosc202.andie.ImageResize150;
import cosc202.andie.LanguageActions;
import cosc202.andie.Macro;
import java.util.Locale;
import java.util.ArrayList;
import java.util.*;

/**
 * Class for testing basic functionality of the {@link Macro} class.
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Mathias Øgaard
 */
public class MacroTest {

    private Macro m;

    private ImageOperation resize = new ImageResize150();
    private ImageOperation bright = new BrightnessFilter(2);
    private ImageOperation grey = new ConvertToGrey();
    private String newLine = System.lineSeparator();

    /**
     * Constructs a {@link Macro} with some {@link ImageOperation}s added, ready for
     * testing.
     */
    private void setup() {
        m = new Macro();
        m.clear();
        m.addOp(resize);
        m.addOp(bright);
        m.addOp(grey);
    }

    /**
     * Tests that {@link Macro}s are constructed as they should, and that
     * {@link ImageOperation}s are added in the right order.
     */
    @Test
    public void ConstructAndChangeMacroTest() {
        m = new Macro();
        assertEquals(m, new ArrayList<ImageOperation>());
        m.addOp(resize);
        m.addOp(bright);
        m.addOp(grey);
        assertEquals(resize, m.get(0)); // resize was added first, and should be at index 0.
        m.removeOp(resize); // Removing resize from the Macro
        assertEquals(bright, m.get(0)); // bright should now be at index 0, since it was added second.
    }

    /**
     * Tests that {@link Macro}'s toString()-method works as it should.
     * The Macro's String-representation should be like this:
     * <p>
     * "Macro contains:
     * <p>
     * - Operation 1
     * <p>
     * .
     * <p>
     * .
     * <p>
     * - Operation n"
     */
    @Test
    public void MacroToStringTest() {
        setup();
        try {
            Locale.setDefault(new Locale("en", "NZ"));
        String resizeName = LanguageActions.getLocaleString("ImageResize150");
        String brightName = LanguageActions.getLocaleString("BrightnessFilter");
        String greyName = LanguageActions.getLocaleString("ConvertToGrey");
        assertEquals((LanguageActions.getLocaleString("macroContains") + " " + newLine + 
                        " - " + resizeName + newLine + 
                        " - " + brightName + newLine + 
                        " - " + greyName), 
                        m.toString());
        m.clear();
        assertEquals(LanguageActions.getLocaleString("macroContains") + " " + LanguageActions.getLocaleString("macroNoOps"), m.toString());
        } catch (MissingResourceException e) {
        }
    }
}
