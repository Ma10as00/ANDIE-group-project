package tests.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import cosc202.andie.BrightnessFilter;
import cosc202.andie.ConvertToGrey;
import cosc202.andie.ImageOperation;
import cosc202.andie.ImageResize150;
import cosc202.andie.Macro;

public class MacroTest {

    private Macro m;
    
    private ImageOperation resize = new ImageResize150();
    private ImageOperation bright = new BrightnessFilter(2);
    private ImageOperation grey = new ConvertToGrey();
    private String newLine = System.lineSeparator();

    private void setup(){
        m = new Macro();
        m.clear();
        m.addOp(resize);
        m.addOp(bright);
        m.addOp(grey);
    }
    
    @Test
    public void ConstructAndChangeMacroTest(){
        m = new Macro();
        assertEquals(m, new ArrayList<ImageOperation>());
        m.addOp(resize);
        m.addOp(bright);
        m.addOp(grey);
        assertEquals(resize, m.get(0));
        m.removeOp(resize);
        assertEquals(bright, m.get(0));
    }

    @Test
    public void MacroToStringTest(){
        setup();
        String resizeName = resize.getClass().getName();
        String brightName = bright.getClass().getName();
        String greyName = grey.getClass().getName();
        assertEquals(("Macro containing: " + newLine + 
                        resizeName + newLine + 
                        brightName + newLine + 
                        greyName), 
                        m.toString());
        m.clear();
        assertEquals("Macro containing: No operations", m.toString());
    }
}
