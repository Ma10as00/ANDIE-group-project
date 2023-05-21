package tests.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import cosc202.andie.BrightnessFilter;
import cosc202.andie.ConvertToGrey;
import cosc202.andie.ImageOperation;
import cosc202.andie.ImageResize150;
import cosc202.andie.Macro;

/**
 * Class for testing basic functionality of the {@link Macro} class.
 * @author Mathias Øgaard
 */
public class MacroTest {

    private Macro m;
    
    private ImageOperation resize = new ImageResize150();
    private ImageOperation bright = new BrightnessFilter(2);
    private ImageOperation grey = new ConvertToGrey();
    private String newLine = System.lineSeparator();

    /**
     * Constructs a {@link Macro} with some {@link ImageOperation}s added, ready for testing.
     */
    private void setup(){
        m = new Macro();
        m.clear();
        m.addOp(resize);
        m.addOp(bright);
        m.addOp(grey);
    }
    
    /**
     * Tests that {@link Macro}s are constructed as they should, and that {@link ImageOperation}s are added in the right order.
     */
    @Test
    public void ConstructAndChangeMacroTest(){
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
     * "Macro containing: 
     * <p>
     * operation 1
     * <p>
     * .
     * <p>
     * .
     * <p>
     * operation n"
     */
    @Test
    public void MacroToStringTest(){
        setup();
        String resizeName = resize.getClass().getSimpleName();
        String brightName = bright.getClass().getSimpleName();
        String greyName = grey.getClass().getSimpleName();
        assertEquals(("Macro containing: " + newLine + 
                        resizeName + newLine + 
                        brightName + newLine + 
                        greyName), 
                        m.toString());
        m.clear();
        assertEquals("Macro containing: No operations", m.toString());
    }
}
