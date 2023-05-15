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
     * <p>
     * The image to display in the ImagePanel.
     * Imports Mouse Handler
     * Storing variables of mouse clicks and drags 
     * </p>
     */
    private EditableImage image;
    /**
     * <p>
     * Imports Mouse Handler
     * </p>
     */
    MouseHandler mHandler = new MouseHandler();

    /**
     * <p>
     * Storing variables of mouse clicks and drags 
     * </p>
     */
    public static int enterX, enterY, exitX, exitY, width, height, clickX, clickY; 
    /**
     * <p>
     * Storing the rectangle the is selected 
     * </p>
     */
    public static Rectangle rect; 
    /**
     * <p>
     * If marcos is recording 
     * </p>
     */
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
    /**
     * <p>
     * Adds Mouse Listener to the image panel
     * </p>
     */
        addMouseListener(new MouseAdapter() {
          /**
           * <p>
           * Finds and stores the first point that the user clicks the mouse.
           * Will not store any value higher than the max width/height of the image so that you cannot select larger than image
           * </p>
           * @param e Mouse event
          */ 
            public void mousePressed(MouseEvent e){
                enterX = e.getX();
                enterY = e.getY();
                if(image.hasImage()){
                    if(enterX > image.getCurrentImage().getWidth()){
                        enterX = image.getCurrentImage().getWidth();
                    }
                    if(enterY > image.getCurrentImage().getHeight()){
                        enterY = image.getCurrentImage().getHeight();
                    }
                }

            }
        });

        /**
         * <p>
         * New Mouse Listener added
         * </p>
         */
        addMouseMotionListener(new MouseAdapter() {   
            /**
             * <p>
             * Stores the point where the user releases their mouse
             * Uses this point and the "enter" points to create a new rectangle with those values 
             * Takes into account drawing rectangle left to right and right to left
             * Calls repaint to draw rectangle as you draw
             * </p>
             * @param e Mouse event
             */   
            public void mouseDragged(MouseEvent e) {
                exitX = e.getX();
                exitY = e.getY();
                if(image.hasImage()){
                    if(exitX > image.getCurrentImage().getWidth()){
                        exitX = image.getCurrentImage().getWidth();
                    }
                    if(exitY > image.getCurrentImage().getHeight()){
                        exitY = image.getCurrentImage().getHeight();
                    }
                }
                rect = new Rectangle(Math.min(enterX, exitX), Math.min(enterY, exitY), Math.abs(exitX - enterX), Math.abs(exitY - enterY));
                repaint(); 
            }
        });

        /**
         * <p>
         * Adds new mouse listener
         * </p>
         */
        addMouseListener(new MouseAdapter() {
            /**
             * <p>
             * Stores the point where the user clicked
             * This is used to remove the selected area
             * Turns the rectangle null- removes drawn shape
             * Sets other point values to 0, so that you cannot crop a shape when there is no region selected
             * </p>
             * @param e Mouse event
             */
            public void mouseClicked(MouseEvent e){
                clickX = e.getX();
                clickY = e.getY();
                if(image.hasImage() && clickX != 0){
                    rect = null; 
                    enterX = 0; 
                    exitX = 0;
                    enterY = 0;
                    exitY = 0; 
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
     * 
     * @param image the editable image
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
        if(image.hasImage()){
            if(rect!= null){
                Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,0, new float[]{9}, 0);
                g2d.setStroke(dashed);
                g2d.setColor(Color.black);
                g2d.draw(rect);
                
                }
            }   
            }
            
   
    /**
     * <p>
     * Class that implements Mouse Listener and Motion Listener
     * Includes override methods that are used in ImagePanel
     * </p>
     */
    public class MouseHandler implements MouseListener, MouseMotionListener{
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }
        @Override
        public void mouseExited(MouseEvent e) {

        }
        @Override
        public void mousePressed(MouseEvent e) {

        }
        @Override
        public void mouseDragged(MouseEvent e) {

        }
        @Override
        public void mouseMoved(MouseEvent e) {

        }
        
     }

    }

