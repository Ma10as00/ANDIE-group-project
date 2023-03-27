package cosc202.andie;
import java.awt.image.*;

public class BrightnessFilter implements ImageOperation{
    public int scale;
    public Brightness(int scale) {
        this.scale = scale;
        }
        
        public BufferedImage apply (BufferedImage previousImage){
            try{
                float brightness = (scale > 0 ? 1.0f + (scale/10f) : 1 -Math.abs(scale/10f));
        RescaleOp rescale = new RescaleOp(brightness, 0, null);
        rescale.filter(previousImage, previousImage);
        }
        catch(Exception e){
            System.out.println("Get good sonny boy");
            }
            return previousImage;
        }
}