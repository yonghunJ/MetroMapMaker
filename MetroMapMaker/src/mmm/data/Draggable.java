package mmm.data;

/**
 * This interface represents a family of draggable shapes.
 * 
 * @author Richard McKenna
 * @author Yonghun Jeong
 * @version 1.0
 */
public interface Draggable {
    public static final String RECTANGLE = "RECTANGLE";
    public static final String POLYLINE = "POLYLINE";
    public static final String STATION = "STATION";
    public static final String IMAGE = "IMAGE";
    public static final String TEXT = "TEXT";
    /**
     * This function return the current state of the data to draw or process
     * a job.
     * @return MMMState.STARTING_IMAGE 
     * 
     */
    public MMMState getStartingState();
    /**
     * This function designate the initial coordinate of the data to make.
     * @param x : x coordinate
     * @param y : y coordinate 
     */
    public void start(int x, int y);
    /**
     * This function change the current coordinate of the Image when user dragged image. 
     * @param x : x coordinate
     * @param y : y coordinate
     */
    public void drag(int x, int y);
    /**
     * This function change the size of the image.
     * @param x : x coordinate
     * @param y : y coordinate
     */
    public void size(int x, int y);
    /**
     * This function return x coordinate
     * @return double
     */
    public double getX();
    /**
     * This function return y coordinate
     * @return double
     */
    public double getY();
    /**
     * This function return width of the shape
     * @return double
     */
    public double getWidth();
    /**
     * This function return height of the shape
     * @return double
     */
    public double getHeight();
    /**
     * This function return height of the shape
     * @param initX  : initial x coorinate
     * @param initY :  initial y coorinate
     * @param initWidth :  initial width
     * @param initHeight :  intial height
     */
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight);
    /**
     * This function return the shape type as IMAGE to do a job. 
     * @return string
     */
    public String getShapeType();
}
