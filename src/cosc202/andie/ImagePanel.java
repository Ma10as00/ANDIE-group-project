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
 * This class extends {@link JPanel} to allow for rendering of an image, as well
 * as zooming
 * in and out.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
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

    /**
     * Imports a Mouse Handler.
     */
    public MouseHandler mHandler = new MouseHandler();

    /**
     * Storing variables of mouse clicks and drags.
     */
    public static int enterX, enterY, exitX, exitY, width, height, clickX, clickY;

    /**
     * Storing the rectangle the is selected.
     */
    public static Rectangle rect;

    /**
     * To keep track of if marcos is recording.
     */
    public boolean ongoingRecording = false;

    /** Sets the tool int which will be used to decide what shape is to be drawn */
    public int tool;

    /** Sets the tool int 0 to selection tool to make coding easier */
    private static int selection = 0;
    /** Sets the tool int 1 to draw rectangle tool to make coding easier */
    private static int drawRect = 1;
    /** Sets the tool int 2 to draw circle tool to make coding easier */
    private static int drawCircle = 2;
    /** Sets the tool int 3 to draw line tool to make coding easier */
    private static int drawLine = 3;
    /** Sets the tool int 4 to draw rectangle outline tool to make coding easier */
    private static int drawRectOutline = 4;
    /** Sets the tool int 5 to draw circle outline tool to make coding easier */
    private static int drawCircOutline = 5;

    /**
     * <p>
     * The zoom-level of the current view.
     * A scale of 1.0 represents actual size; 0.5 is zoomed out to half size; 1.5 is
     * zoomed in to one-and-a-half size; and so forth.
     * </p>
     * 
     * <p>
     * Note that the scale is internally represented as a multiplier, but externally
     * as a percentage.
     * </p>
     */
    public static double scale;

    /**
     * <p>
     * Create a new ImagePanel.
     * </p>
     * 
     * <p>
     * Newly created ImagePanels have a default zoom level of 100%.
     * </p>
     * 
     * @param frame The main frame of the GUI.
     */
    public ImagePanel(JFrame frame) {
        image = new EditableImage(frame);
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
             * Will not store any value higher than the max width/height of the image so
             * that you cannot select larger than image
             * </p>
             * 
             * @param e Mouse event
             */
            public void mousePressed(MouseEvent e) {
                enterX = e.getX();
                enterY = e.getY();
                if (image.hasImage()) {
                    if (enterX > image.getCurrentImage().getWidth()) {
                        enterX = image.getCurrentImage().getWidth();
                    }
                    if (enterY > image.getCurrentImage().getHeight()) {
                        enterY = image.getCurrentImage().getHeight();
                    }
                    if (enterX < 0) {
                        enterX = 0;
                    }
                    if (enterY < 0) {
                        enterY = 0;
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
             * Uses this point and the "enter" points to create a new rectangle with those
             * values
             * Takes into account drawing rectangle left to right and right to left
             * Calls repaint to draw rectangle as you draw
             * </p>
             * 
             * @param e Mouse event
             */
            public void mouseDragged(MouseEvent e) {
                exitX = e.getX();
                exitY = e.getY();
                if (image.hasImage()) {
                    if (exitX > image.getCurrentImage().getWidth()) {
                        exitX = image.getCurrentImage().getWidth();
                    }
                    if (exitY > image.getCurrentImage().getHeight()) {
                        exitY = image.getCurrentImage().getHeight();
                    }
                    if (exitX < 0) {
                        exitX = 0;
                    }
                    if (exitY < 0) {
                        exitY = 0;
                    }
                }
                rect = new Rectangle(Math.min(enterX, exitX), Math.min(enterY, exitY), Math.abs(exitX - enterX),
                        Math.abs(exitY - enterY));
                repaint();
<<<<<<< HEAD
                line = new Line2D.Double(enterX, enterY, exitX, exitY);
                repaint();

=======
>>>>>>> 32732ca4a46dfa37e819e2b0400c184a8684f7d3
            }

        });

        addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (tool == drawRect) {
                    image.apply(new DrawRec(scale, rect, false));
                    Andie.imagePanel.repaint();
                    Andie.imagePanel.getParent().revalidate();
                    deselect();
                }
                if (tool == drawCircle) {
                    int x = Math.min(enterX, exitX);
                    int y = Math.min(enterY, exitY);
                    int width = Math.abs(enterX - exitX);
                    int height = Math.abs(enterY - exitY);
                    image.apply(new DrawCircle(scale, x, y, height, width, false));
                    Andie.imagePanel.repaint();
                    Andie.imagePanel.getParent().revalidate();
                    deselect();
                }
                if (tool == drawLine) {
                    image.apply(new DrawLine(scale, enterX, enterY, exitX, exitY));
                    Andie.imagePanel.repaint();
                    Andie.imagePanel.getParent().revalidate();
                    deselect();
                }
                if (tool == drawRectOutline) {
                    image.apply(new DrawRec(scale, rect, true));
                    Andie.imagePanel.repaint();
                    Andie.imagePanel.getParent().revalidate();
                    deselect();
                }
                if (tool == drawCircOutline) {
                    int x = Math.min(enterX, exitX);
                    int y = Math.min(enterY, exitY);
                    int width = Math.abs(enterX - exitX);
                    int height = Math.abs(enterY - exitY);
                    image.apply(new DrawCircle(scale, x, y, height, width, true));
                    Andie.imagePanel.repaint();
                    Andie.imagePanel.getParent().revalidate();
                    deselect();
                }
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
             * Sets other point values to 0, so that you cannot crop a shape when there is
             * no region selected
             * </p>
             * 
             * @param e Mouse event
             */
            public void mouseClicked(MouseEvent e) {
                clickX = e.getX();
                clickY = e.getY();
                if (image.hasImage() && clickX != 0) {
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
     * Create a new ImagePanel, purely for the use of JUnit tests.
     * </p>
     * 
     * <p>
     * Newly created ImagePanels have a default zoom level of 100%.
     * Note, this will construct an ImagePanel with no frame or mouse handler.
     * So, do not use it for any purpose other then JUnit tests.
     * </p>
     * 
     */
    public ImagePanel() {
        scale = 1.0;
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
        image.updateFrameTitle();
    }

    /**
     * <p>
     * Get the current zoom level as a percentage.
     * </p>
     * 
     * <p>
     * The percentage zoom is used for the external interface, where 100% is the
     * original size, 50% is half-size, etc.
     * </p>
     * 
     * @return The current zoom level as a percentage.
     */
    public double getZoom() {
        return 100 * scale;
    }

    /**
     * <p>
     * Set the current zoom level as a percentage.
     * </p>
     * 
     * <p>
     * The percentage zoom is used for the external interface, where 100% is the
     * original size, 50% is half-size, etc.
     * The zoom level is restricted to the range [50, 200].
     * </p>
     * 
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
     * The preferred size is the size of the image (scaled by zoom level), or a
     * default size if no image is present.
     * </p>
     * 
     * @return The preferred size of this component.
     */
    @Override
    public Dimension getPreferredSize() {
        if (image.hasImage()) {
            return new Dimension((int) Math.round(image.getCurrentImage().getWidth() * scale),
                    (int) Math.round(image.getCurrentImage().getHeight() * scale));
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
            Graphics2D g2 = (Graphics2D) g.create();
            g2.scale(scale, scale);
            g2.drawImage(image.getCurrentImage(), null, 0, 0);
            g2.dispose();
        }
        Graphics2D g2d = (Graphics2D) g.create();
        if (image.hasImage()) {
            if (rect != null && getTool() == selection) {
                float[] dash = new float[] { 4.0f, 4.0f };
                BasicStroke solidStroke = new BasicStroke(1.0f);
                BasicStroke dashStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0.0f, dash,
                        0);
                g2d.setStroke(solidStroke);
                g2d.setColor(Color.white);
                g2d.draw(rect);
                g2d.setStroke(dashStroke);
                g2d.setColor(Color.black);
                g2d.draw(rect);
            }
            if (rect != null && getTool() == drawRect) {
                g2d.setColor(DrawActions.userColour);
                g2d.fillRect(rect.x, rect.y, rect.width, rect.height);
            }
            if (enterX != 0 && getTool() == drawCircle) {
                g2d.setColor(DrawActions.userColour);
                int x = Math.min(enterX, exitX);
                int y = Math.min(enterY, exitY);
                int width = Math.abs(enterX - exitX);
                int height = Math.abs(enterY - exitY);
                g2d.fillOval(x, y, width, height);
            }
            if (enterX != 0 && getTool() == drawLine) {
                g2d.setColor(DrawActions.userColour);
                g2d.setStroke(DrawActions.stroke);
                g2d.drawLine(enterX, enterY, exitX, exitY);
            }
            if (rect != null && getTool() == drawRectOutline) {
                g2d.setColor(DrawActions.userColour);
                g2d.setStroke(DrawActions.stroke);
                g2d.draw(rect);
            }
            if (enterX != 0 && getTool() == drawCircOutline) {
                g2d.setColor(DrawActions.userColour);
                g2d.setStroke(DrawActions.stroke);
                int x = Math.min(enterX, exitX);
                int y = Math.min(enterY, exitY);
                int width = Math.abs(enterX - exitX);
                int height = Math.abs(enterY - exitY);
                g2d.drawOval(x, y, width, height);
            }
        }
        g2d.dispose();
    }

    public void deselect() {
        rect = null;
        ImagePanel.enterX = 0;
        ImagePanel.enterY = 0;
        ImagePanel.exitX = 0;
        ImagePanel.exitY = 0;
    }

    public void setTool(int i) {
        tool = i;
    }

    public int getTool() {
        return tool;
    }

    /**
     * <p>
     * Class that implements Mouse Listener and Motion Listener
     * Includes override methods that are used in ImagePanel
     * </p>
     */
    public class MouseHandler implements MouseListener, MouseMotionListener {
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
