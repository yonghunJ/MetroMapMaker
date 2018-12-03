
package mmm.data;
import static mmm.data.Draggable.POLYLINE;
import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import javafx.scene.shape.Line;


/**
 * This is a DraggableLine class for MetroMapMaker application.
 * 
 * @author Yonghun Jeong
 * @version 1.0
 */
public class DraggableLine extends Line implements Draggable {
    double startX;
    double startY;
    double EndX;
    double EndY;
    private DraggableText LineNameLeft;
    private DraggableText LineNameRight;
    private ArrayList<DraggableStation> station[];
    private int StationNum;
    
     /**
     * Constructor for initializing the DraggableLine object, note that this constructor
     * will fully setup the DraggableLine for use.
     * @param name : line name
     * @param color : line color
     */
    public DraggableLine(String name,Color color) {
	setStartX(10.0);
	setStartY(10.0);
        setOpacity(1.0);
	setEndX(100.0);
	setEndY(100.0);
	startX = getStartX();
	startY = getStartY();
	EndX = getEndX();
	EndY = getEndY();
        LineNameLeft = new DraggableText();
        LineNameRight = new DraggableText();

        StationNum =0;
        this.setStroke(color);
        System.err.println(color);
        setLineName(name);
        LineNameLeft.setX(startX);
        LineNameLeft.setY(startY);
        LineNameRight.setX(EndX);
        LineNameRight.setY(EndY);
        this.setStrokeWidth(5); 
    }
    
    /**
     * This function return the current state of the data to draw or process
     * a job.
     * @return STARTING_LINE start to make line.
     */
    @Override
    public MMMState getStartingState() {
	return MMMState.STARTING_LINE;
    }
    /**
     * This function designate the initial coordinate of the data to make.
     * @param x : x coordinate
     * @param y : y coordinate 
     */
    @Override
    public void start(int x, int y) {
	startX = x;
	startY = y;
    }
    /**
     * This function change the current coordinate of the line when user dragged line. 
     * @param x : x coordinate
     * @param y : y coordinate 
     */
    @Override
    public void drag(int x, int y) {
        System.err.println("2222");
	double diffX1 = x - startX;
	double diffY1 = y - startY;
        

        double startx =  getStartX() + diffX1;
        double starty =  getStartY() + diffY1;
        double endx =  getEndX() + diffX1;
        double endy =  getEndY() + diffY1;


        startXProperty().set(startx);
	startYProperty().set(starty);
	
        endXProperty().set(endx);
	endYProperty().set(endy);

        startX = x;
        startY = y;
        EndX = x;
        EndY = y;

        LineNameLeft.setX(startx);
        LineNameLeft.setY(starty);
        LineNameRight.setX(endx);
        LineNameRight.setY(endy);
    }
    /**
     * This function calculate the location of the station in the line using mathmetics method. 
     */
    public void calculateStationLocation(){
        
    }   
    /**
     * This function print the current coordinate. 
     *@return string return the coordinate
     */
    public String cT(double x, double y) {
	return "(x,y): (" + x + "," + y + ")";
    }
    /**
     * This function change the size of the label.
     * @param x : x coordinate
     * @param y : y coordinate
     */
    @Override
    public void size(int x, int y) {
	//double width = x - getX();
	//double height = y - getY();
        
        
	double width = x - startX;
        double height = y - startY;
        double newX = x - width;
        double newY = y - height;
        //heightProperty().set(height);
        //widthProperty().set(width);
   
    }
    /**
     * This function show the curren coordinate and size of the line when load the file in to the workspace. 
     * @param initX  : initial x coorinate
     * @param initY :  initial y coorinate
     * @param initWidth :  initial width
     * @param initHeight :  intial height
     */
    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
	startXProperty().set(initX);
	startYProperty().set(initY);
//	widthProperty().set(initWidth);
//	heightProperty().set(initHeight);
    }
    /**
     * This function return the shape type as line to do a job.
     * @return LINE
     */
    @Override
    public String getShapeType() {
	return POLYLINE;
    }
    /**
     * This function return to cooredinate x of Line. 
     * @return 2  not use
     */
    @Override
    public double getX() {
        return 2;
    }
    
    /**
     * This function return to cooredinate y of line. 
     * @return 2  not use
     */
    @Override
    public double getY() {        
        return 2;
    }
    /**
     * This function return to width of the line. 
     * @return 2  not use
     */
    @Override
    public double getWidth() {       
        return 2;
    }
    /**
     * This function return to height of the line. 
     * @return 2  not use
     */ 
    @Override
    public double getHeight() {       
        return 2;
    }
   
    /**
     * This function set the position of the text to the top. 
     */ 
    public void setLabelPostionTop(){}
    /**
     * This function set the position of the text to the bottom. 
     */ 
    public void setLabelPostionBottom(){}
    /**
     * This function set the position of the text to the right. 
     */ 
    public void setLabelPostionRight(){}
    /**
     * This function set the position of the text to the left. 
     */ 
    public void setLabelPostionLeft(){}
    /**
     * This function return the leftsize text of the line. 
     * @return LineNameLeft : line name
     */ 
    public DraggableText getLeftText(){
        return LineNameLeft;
    }
    /**
     * This function return the right size text of the line. 
     * @return LineNameRight : line name
     */ 
    public DraggableText getRightText(){
        return LineNameRight;
    }
    /**
     * This function return the number of the station that the line has. 
     * @return 1 : number of station
     */ 
    public int PosseessStationNum(){
        
        return 1;
    }
    /**
     * This function set the coordinate of the station to the specific line. 
     */ 
    public void StationCoordinateSetting(){
        
    }
    /**
     * This function set the color of the line.
     * @param color : line color
     */ 
    public void setLineColor(Color color){
        this.setStroke(color);

    }
    /**
     * This function set the linename. 
     * @param string : line name
     */ 
    public void setLineName(String string){
        LineNameLeft.setText(string);
        LineNameRight.setText(string);

    }
}