package cosc202.andie.macros;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import cosc202.andie.ImageOperation;

/**
 * Implementation of an IMacro, keeping the "child operations" in an {@link ArrayList}.
 * <p>
 * This class also provides a toString() method, printing out all operations that are contained in the Macro.
 * 
 * @author Mathias Øgaard
 */
public class Macro extends ArrayList<ImageOperation> implements IMacro {

    @Override
    public BufferedImage apply(BufferedImage input) {
        BufferedImage output = input;
        for (int i=0; i<size(); i++){   //Apply the operations in the right order
            ImageOperation op = get(i);
            output = op.apply(output);
        }
        return output;
    }

    @Override
    public String toString() {
        String str = "Macro containing: ";
        if(size()<1)
            str += "No operations";
        else{
            for(int i=0; i<size(); i++){
                str += System.lineSeparator();
                String op = get(i).getClass().getName();
                str += op;
            }
        }
        return str;
    }
    
}