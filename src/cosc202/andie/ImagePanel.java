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

    public int tool;

    private static int drawRect = 1;
    private static int drawCircle = 2;
    private static int drawLine = 3;

    public Point enter;
    public Point exit;
    public Point current;
    public Point last;
    public boolean circle;

    public Point[] positions = null;
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
    private double scale;

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
                circle = true;
                // Point[] positions;
                repaint();
            }

        });

        addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                int releaseX = e.getX();
                int releaseY = e.getY();
                Rectangle rect2 = new Rectangle(Math.min(enterX, releaseX), Math.min(enterY, releaseY),
                        Math.abs(releaseX - enterX),
                        Math.abs(releaseY - enterY));
                if (tool == drawRect) {
                    image.apply(new DrawRec(rect2, DrawActions.userColour));
                    rect = null;
                }
                if (tool == drawCircle) {
                    enter = new Point(Math.min(enterX, releaseX), Math.min(enterY, releaseY));
                    exit = new Point(Math.abs(releaseX - enterX), Math.abs(releaseY - enterY));
                    image.apply(new DrawCircle(enter, exit));
                    circle = false;
                }
                // if (tool == drawLine) {
                // Point[] poitions;
                // for(int )

                // image.apply(new DrawLine(DrawActions.userColour, width))

                // }
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
        Graphics2D g2d = (Graphics2D) g;
        if (image.hasImage()) {
            if (rect != null && getTool() == 0) {
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
            if (rect != null && getTool() == 1) {
                g2d.setColor(DrawActions.userColour);
                g2d.fillRect(rect.x, rect.y, rect.width, rect.height);
            }
            if (circle && getTool() == 2) {
                g2d.setColor(DrawActions.userColour);
                g2d.fillOval(Math.min(enterX, enterY), Math.min(enterY, exitY),
                        Math.abs((exitX - 20) - (enterX - 20)),
                        Math.abs((exitY - 20) - (enterY - 20)));
            }
            // if (positions != null && getTool() == 3) {
            // g2d.setColor(DrawActions.userColour);
            // g2d.drawLine(current.x, current.y, last.x, last.y);
            // }
        }
    }

    public void deselect() {
        rect = null;
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
