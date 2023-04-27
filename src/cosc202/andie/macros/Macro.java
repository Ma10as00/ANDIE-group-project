package cosc202.andie.macros;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import cosc202.andie.ImageOperation;

/**
 * Implementation of an IMacro, keeping the "child operations" in an {@link ArrayList}.
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
    
}
