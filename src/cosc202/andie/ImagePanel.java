package cosc202.andie;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

/**
 * <p>
 * UI display element for {@link EditableImage}s.
 * </p>
 * 
 * <p>
 * This class extends {@link JPanel} to allow for rendering of an image, as well as zooming
 * in and out. 
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class ImagePanel extends JPanel {
    
    /**
     * The image to display in the ImagePanel.
     */
    private EditableImage image;
    MouseHandler mHandler = new MouseHandler();
    public static int enterX, enterY, exitX, exitY, width, height, clickX, clickY; 
    public static Rectangle rect; 
    public boolean ongoingRecording = false;

    /**
     * <p>
     * The zoom-level of the current view.
     * A scale of 1.0 represents actual size; 0.5 is zoomed out to half size; 1.5 is zoomed in to one-and-a-half size; and so forth.
     * </p>
     * 
     * <p>
     * Note that the scale is internally represented as a multiplier, but externally as a percentage.
     * </p>
     */
    private double scale;

    /**
     * <p>
     * Create a new ImagePanel.
     * </p>
     * 
     * <p>
     * Newly created ImagePanels have a default zoom level of 100%
     * </p>
     */
    public ImagePanel() {
        image = new EditableImage();
        scale = 1.0;
        this.addMouseListener(mHandler);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e){
                enterX = e.getX();
                enterY = e.getY(); 
            }
        });

        addMouseMotionListener(new MouseAdapter() {      
            public void mouseDragged(MouseEvent e) {
                exitX = e.getX();
                exitY = e.getY();
                rect = new Rectangle(Math.min(enterX, exitX), Math.min(enterY, exitY), Math.abs(exitX - enterX), Math.abs(exitY - enterY));
                repaint(); 
            }
        });

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                clickX = e.getX();
                clickY = e.getY();
                if(image.hasImage() && clickX != 0){
                    rect = null; 
                    repaint();
                }
            }
            });
 
        }

    /**
     * <p>
     * Get the currently displayed image.
     * </p>
     *
     * @return the image currently displayed.
     */
    public EditableImage getImage() {
        return image;
    }

    /**
     * <p>
     * Set the image to a new {@link EditableImage}.
     * </p>
     */
    public void setImage(EditableImage image) {
        this.image = image;
    }

    /**
     * <p>
     * Get the current zoom level as a percentage.
     * </p>
     * 
     * <p>
     * The percentage zoom is used for the external interface, where 100% is the original size, 50% is half-size, etc. 
     * </p>
     * @return The current zoom level as a percentage.
     */
    public double getZoom() {
        return 100*scale;
    }

    /**
     * <p>
     * Set the current zoom level as a percentage.
     * </p>
     * 
     * <p>
     * The percentage zoom is used for the external interface, where 100% is the original size, 50% is half-size, etc. 
     * The zoom level is restricted to the range [50, 200].
     * </p>
     * @param zoomPercent The new zoom level as a percentage.
     */
    public void setZoom(double zoomPercent) {
        if (zoomPercent < 50) {
            zoomPercent = 50;
        }
        if (zoomPercent > 200) {
            zoomPercent = 200;
        }
        scale = zoomPercent / 100;
    }


    /**
     * <p>
     * Gets the preferred size of this component for UI layout.
     * </p>
     * 
     * <p>
     * The preferred size is the size of the image (scaled by zoom level), or a default size if no image is present.
     * </p>
     * 
     * @return The preferred size of this component.
     */
    @Override
    public Dimension getPreferredSize() {
        if (image.hasImage()) {
            return new Dimension((int) Math.round(image.getCurrentImage().getWidth()*scale), 
                                 (int) Math.round(image.getCurrentImage().getHeight()*scale));
        } else {
            return new Dimension(450, 450);
        }
    }

    static boolean findPoint(int x1, int y1, int x2,int y2, int x, int y){
        if (x > x1 && x < x2 &&y > y1 && y < y2)return true;

        return false;
    }

    /**
     * <p>
     * (Re)draw the component in the GUI.
     * </p>
     * 
     * @param g The Graphics component to draw the image on.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image.hasImage()) {
            Graphics2D g2  = (Graphics2D) g.create();
            g2.scale(scale, scale);
            g2.drawImage(image.getCurrentImage(), null, 0, 0);
            g2.dispose();
        }
        Graphics2D g2d = (Graphics2D) g; 
            if(rect!= null && image.hasImage()){
                Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,0, new float[]{9}, 0);
                g2d.setStroke(dashed);
                g2d.setColor(Color.black);
                g2d.draw(rect);
                
                }
            }
            
   

    public class MouseHandler implements MouseListener, MouseMotionListener{
        public static int enterX, enterY, exitX, exitY, width, height, clickX, clickY; 
        public Rectangle rect; 

        @Override
        public void mouseClicked(MouseEvent e) {
            clickX = e.getX();
            clickY = e.getY();
        }
        @Override
        public void mouseEntered(MouseEvent e) {

        }
        @Override
        public void mouseReleased(MouseEvent e) {
            exitX = e.getX();
            exitY = e.getY(); 

        }
        @Override
        public void mouseExited(MouseEvent e) {

        }
        @Override
        public void mousePressed(MouseEvent e) {
            enterX = e.getX();
            enterY = e.getY();

        }
        
        public static int getExitX(){
            return exitX; 
        }
        public static int getExitY(){
            return exitY; 
        }
        public static int getEnterX(){
            return enterX; 
        }
        public static int getEnterY(){
            return enterY; 
        }
        public static int getClickX(){
            return clickX; 
        }
        public static int getClickY(){
            return clickY; 
        }
        @Override
        public void mouseDragged(MouseEvent e) {

        }
        @Override
        public void mouseMoved(MouseEvent e) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'mouseMoved'");
        }
        
     }

    }

