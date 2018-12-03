
package mmm.data;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * This is a DraggableStation class for MetroMapMaker application.
 * 
 * @author Yonghun Jeong
 * @version 1.0
 */
public class DraggableStation  extends Circle implements Draggable {
    double startCenterX;
    double startCenterY;;
    private ArrayList<DraggablePolyline> polyline;
    private Color color;
    public DraggableText stationName;
    public int stationLabelStatus;
    int matrixNum;
    /**
     * Constructor for initializing the DraggableStation object, note that this constructor
     * will fully setup the DraggableStation for use.
     */
    public DraggableStation() {
	setCenterX(0.0);
	setCenterY(0.0);
        setRadius(10.0);
	setOpacity(1.0);
	startCenterX = 0.0;
	startCenterY = 0.0;
        color = Color.BLACK;
        stationName = new DraggableText();
        stationName.setX(startCenterX+10);
        stationName.setY(startCenterY-30);
        polyline = new ArrayList<>();
        stationLabelStatus = 1;
    }
    public void setDraggbleText(DraggableText text){
        stationName = text;
    }
    public void setMatrixNum(int matrixNum){
        this.matrixNum = matrixNum;
    }
    public int getMatrixNum(){
        return this.matrixNum;
    }
    public void setstationLabelStatus(int matrixNum){
        this.stationLabelStatus = stationLabelStatus;
    }
    public int getstationLabelStatus(){
        return this.stationLabelStatus;
    }
    public void changeMoveLabel(){
        
        if(stationName.getX() < getCenterX() && stationName.getY() > getCenterY() ){
            //System.err.println(" Left Bottom -> Right Bottom");
            stationName.setX(getCenterX()+10);
            stationLabelStatus = 3;
        }else if(stationName.getX() > getCenterX() && stationName.getY() > getCenterY() ){
            //System.err.println("Right Bottom -> Left Top.");
            stationName.setX(getCenterX()-50);
            stationName.setY(getCenterY()-30);
            stationLabelStatus = 0;
        }else if(stationName.getX() < getCenterX() && stationName.getY() < getCenterY() ){
            //System.err.println("Left Top-> Right Top");
            stationName.setX(getCenterX()+10);
            stationLabelStatus = 1;
        }else if(stationName.getX() > getCenterX() && stationName.getY() < getCenterY() ){
            //System.err.println("Right Top-> Left Bottom");
            stationName.setX(getCenterX()-50);
            stationName.setY(getCenterY()+30);
            stationLabelStatus = 2;
        } 
    }
    
    public void zoomin(double zoomin){
        setCenterX(getCenterX() * zoomin);
	setCenterY(getCenterY() * zoomin);
    }
    public ArrayList<DraggablePolyline> getStationWhichLineInclude(){
        return polyline;
    }
    public void addPolyline(DraggablePolyline line){
        System.err.println(line.getLeftText().getText());
        int k=0;
        if(polyline.isEmpty())
            polyline.add(line);
        else{
            for(int i=0;i<polyline.size();i++){
                if((line.getLeftText().getText().equals(polyline.get(i).getLeftText().getText()))){
                    k++;
                }
                if(k ==0){
                    polyline.add(line);
                    System.err.println(line.getLeftText().getText());
                }
            }
        }
    }
    public void removePolyline(DraggablePolyline line){
        polyline.remove(line);
    }
    public void setStationName(String name){
        this.stationName.setText(name);
    }
    public DraggableText getStationName(){
        return this.stationName;
    }
    /**
     * This function return the current state of the data to draw or process
     * a job.
     * @return STARTING_STATION
     */
    @Override
    public MMMState getStartingState() {
	return MMMState.STARTING_STATION;
    }
    /**
     * This function designate the initial coordinate of the data to make.
     * @param x : x coordinate
     * @param y : y coordinate  
     */
    @Override
    public void start(int x, int y) {
//	startCenterX = x;
//	startCenterY = y;
//        setCenterX(x);
//        setCenterY(y);
        startCenterX = x;
	startCenterY = y;

    }
    public void startx(int x, int y) {
//        System.err.println(x+","+y);
        setCenterX(x);
        setCenterY(y);
        stationName.setX(x+10);
        stationName.setY(y-30);
    }
    public void startxx(double x, double y) {
//        System.err.println(x+","+y);
        setCenterX(x);
        setCenterY(y);
        stationName.setX(x+10);
        stationName.setY(y-30);
    }
    /**
     * This function change the current coordinate of the station when user dragged station. 
     * @param x : x coordinate
     * @param y : y coordinate  
     */
    @Override
    public void drag(int x, int y) {
	double diffX = x - startCenterX;
	double diffY = y - startCenterY;
	double newX = getCenterX() + diffX;
	double newY = getCenterY() + diffY;
	setCenterX(newX);
	setCenterY(newY);
	startCenterX = x;
	startCenterY = y;
        switch (stationLabelStatus) {
            case 3:
                //System.err.println(" Left Bottom -> Right Bottom");
                stationName.setX(getCenterX()+10);
                stationName.setY(y+30);
                break;
            case 0:
                //System.err.println("Right Bottom -> Left Top.");
                stationName.setX(getCenterX()-50);
                stationName.setY(getCenterY()-30);
                break;
            case 1:
                //System.err.println("Left Top-> Right Top");
                stationName.setX(getCenterX()+10);
                stationName.setY(y-30);
                break;
            case 2:
                //System.err.println("Right Top-> Left Bottom");
                stationName.setX(getCenterX()-50); 
                stationName.setY(getCenterY()+30);
                break;
            default:
                break;
        }
    }
    /**
     * This function print the current coordinate. 
     * @param x : x coordinate
     * @param y : y coordinate  
     */
    public String cT(double x, double y){
        return "(x,y): (" + x + "," + y + ")";
    }
    /**
     * This function change the size of the station.
     * @param x : x coordinate
     * @param y : y coordinate  
     */
    @Override
    public void size(int x, int y) {
	double width = x - startCenterX;
	double height = y - startCenterY;
	double centerX = startCenterX + (width / 2);
	double centerY = startCenterY + (height / 2);
	setCenterX(centerX);
	setCenterY(centerY);
	
    }
    /**
     * This function return to cooredinate x of station. 
     * @return  X coordinate  
     */    
    @Override
    public double getX() {
	return getCenterX();
    }
    /**
     * This function return to cooredinate y of station. 
     * @return  Y coordinate  
     */    
    @Override
    public double getY() {
	return getCenterY();
    }
    /**
     * This function return to width of the station. 
     * @return 2 not use
     */    
    @Override
    public double getWidth() {
	return 2;
    }
    /**
     * This function return to height of the station. 
     * @return 2 not use
     */  
    @Override
    public double getHeight() {
	return 2;
    }
    /**
     * This function show the curren coordinate and size of the station when load the file in to the workspace. 
     * @param initX  : initial x coorinate
     * @param initY :  initial y coorinate
     * @param initWidth :  initial width
     * @param initHeight :  intial height
     */    
    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
	setCenterX(initX + (initWidth/2));
	setCenterY(initY + (initHeight/2));
    }
    public void resetLocationAndSize(double initX, double initY, String name) {
        setStationName(name);
	this.startx((int)initX,(int)initY);
    }
    /**
     * This function return the shape type as station to do a job.
     * @return STATION
     */
    @Override
    public String getShapeType() {
	return STATION;
    }
    /**
     * This function set the name position of the station to the top-right. 
     */  
    public void setStationNamePostionTopRight(){}
    /**
     * This function set the name position of the station to the bottom-right. 
     */ 
    public void setStationNamePostionBottomRight(){}
    /**
     * This function set the name position of the station to the top-left. 
     */ 
    public void setStationNamePostionTopleft(){}
    /**
     * This function set the name position of the station to the bottom-left. 
     */ 
    public void setStationNamePostionBottomleft(){}
    /**
     * This function rotate the name position of the station . 
     */ 
    public void setStationNamePostioRotate(){}
    
    public void Wpressed(double movement){
        setCenterY(getCenterY()+movement);
    }
    public void Apressed(double movement){
        setCenterX(getCenterX()+movement);
    }
    public void Spressed(double movement){

        setCenterY(getCenterY()-movement);
    }
    public void Dpressed(double movement){
        setCenterX(getCenterX()-movement);
    }
}
