package cosc202.andie;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * <p>
 * Implementation of an IMacro, keeping the "child operations" in an
 * {@link ArrayList}.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * <p>
 * This class also provides a toString() method, printing out all operations
 * that are contained in the Macro.
 * </p>
 * 
 * @author Mathias Øgaard
 */
public class Macro extends ArrayList<ImageOperation> implements IMacro {

    @Override
    public BufferedImage apply(BufferedImage input) {
        // Make copy of input
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), input.getType());
        Graphics g = output.getGraphics();
        g.drawImage(input, 0, 0, null);
        g.dispose();

        // Apply the operations in the right order
        for (int i = 0; i < size(); i++) {
            ImageOperation op = get(i);
            output = op.apply(output);
        }
        return output;
    }

    @Override
    public String toString() {
        String str = LanguageActions.getLocaleString("macroContains") + " ";
        if (size() < 1) {
            str += LanguageActions.getLocaleString("macroNoOps");
        } else {
            for (int i = 0; i < size(); i++) {
                str += System.lineSeparator();
                String op = LanguageActions.getLocaleString(get(i).getClass().getSimpleName());
                str += " - " + op;
            }
        }
        return str;
<<<<<<< HEAD
    }

=======
    } 
    
>>>>>>> e3361f5641565fd5ce6bf783b800f3e9b1603cc3
}
