
package mmm.data;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import static mmm.data.Draggable.POLYLINE;

/**
 * This is a DraggableLabelclass for MetroMapMaker application.
 * 
 * @author Yonghun Jeong
 * @version 1.0
 */
public class DraggablePolyline extends Polyline implements Draggable {
    double startX;
    double startY;

    private DraggableText LineNameLeft;
    private DraggableText LineNameRight;
    public ArrayList<DraggableStation> station;
    public  ArrayList<DraggableStation> Sortedstation;
    private int StationNum;
    private boolean circular;
    String stationList;
     /**
     * Constructor for initializing the DraggableLine object, note that this constructor
     * will fully setup the DraggableLine for use.
     */
    public DraggablePolyline() {
        
        setOpacity(1.0);

        setStrokeWidth(5); 
        setLayoutX(0.0);
        setLayoutY(0.0);
	startX = 0.0;
	startY = 0.0;
        LineNameLeft = new DraggableText();
        LineNameRight = new DraggableText();

        station = new ArrayList<DraggableStation>();
        Sortedstation = new ArrayList<DraggableStation>();
        circular = false;
        
    }
    public void DraggableLeftText(DraggableText text){
        LineNameLeft = text;
    }
    public void DraggableRightText(DraggableText text){
        LineNameRight = text;
    }
    public ArrayList<DraggableStation> getStation(){
        return station;
    }
    public ArrayList<DraggableStation> getsortedStation(){
        return Sortedstation;
    }
    public void addStation2(DraggableStation addStation, int x, int y){
        stationList = "";
        Sortedstation.clear();
        station.add(addStation);
        HashMap<Double, DraggableStation> hmap = new HashMap<Double, DraggableStation>();
        for( int i=0;i<station.size();i++){
            hmap.put(Point2D.distance(getPoints().get(0),getPoints().get(1), station.get(i).getCenterX(), station.get(i).getCenterX()),station.get(i));
        }
        Map<Double, DraggableStation> map = new TreeMap<Double, DraggableStation>(hmap);
        Set set2 = map.entrySet();
         Iterator iterator2 = set2.iterator();
         while(iterator2.hasNext()) {
              Map.Entry me2 = (Map.Entry)iterator2.next();
              Sortedstation.add((DraggableStation)me2.getValue());
              stationList += ((DraggableStation)me2.getValue()).getStationName().getText()+"\n";
         }
    }
    public void addStation3(DraggableStation addStation){
        stationList = "";
        Sortedstation.clear();
        station.add(addStation);
        HashMap<Double, DraggableStation> hmap = new HashMap<Double, DraggableStation>();
        for( int i=0;i<station.size();i++){
            hmap.put(Point2D.distance(getPoints().get(0),getPoints().get(1), station.get(i).getCenterX(), station.get(i).getCenterX()),station.get(i));
        }
        Map<Double, DraggableStation> map = new TreeMap<Double, DraggableStation>(hmap);
        Set set2 = map.entrySet();
         Iterator iterator2 = set2.iterator();
         while(iterator2.hasNext()) {
              Map.Entry me2 = (Map.Entry)iterator2.next();
              Sortedstation.add((DraggableStation)me2.getValue());
              stationList += ((DraggableStation)me2.getValue()).getStationName().getText()+"\n";
         }
//         for(int i=0;i<station.size();i++){
//             System.err.println(station.get(i));
//         }
    }
    public void removeStationFromtheLine(DraggableStation addStation){
        stationList = "";
        Sortedstation.clear();
        station.remove(addStation);
        HashMap<Double, DraggableStation> hmap = new HashMap<Double, DraggableStation>();
        for( int i=0;i<station.size();i++){
            hmap.put(Point2D.distance(getPoints().get(0),getPoints().get(1), station.get(i).getCenterX(), station.get(i).getCenterX()),station.get(i));
        }
        Map<Double, DraggableStation> map = new TreeMap<Double, DraggableStation>(hmap);
        Set set2 = map.entrySet();
         Iterator iterator2 = set2.iterator();
         while(iterator2.hasNext()) {
              Map.Entry me2 = (Map.Entry)iterator2.next();
              Sortedstation.add((DraggableStation)me2.getValue());
              stationList += ((DraggableStation)me2.getValue()).getStationName().getText()+"\n";
         }
    }
//    public void addStations(DraggableStation addStation, int x, int y){
//
//        
//        station.add(addStation);
//        
//        for(int i=0;i< this.getPoints().size()-2;i+=2){
//            double FirstToSecond = Math.hypot(this.getPoints().get(i)-this.getPoints().get(i+2), this.getPoints().get(i+1)-this.getPoints().get(i+3));
//            double FirstToNew = Math.hypot(this.getPoints().get(i)-addStation.getX(), this.getPoints().get(i+1)-addStation.getY());
//            double SecondToNew = Math.hypot(this.getPoints().get(i+2)-addStation.getX(), this.getPoints().get(i+3)-addStation.getY());
//
//            
//            if( (int)FirstToSecond == (int)(FirstToNew + SecondToNew)   ){
//                this.getPoints().add(i+2, addStation.getCenterX());
//                this.getPoints().add(i+3, addStation.getCenterY());
////                System.err.println("succeess");
//                break;
//            }
//           
//        }
////station에 하나씩 순서에 상관없이 들어감 
//        stationList = "";
//        stationList();
//
//    }
    public void stationList(){
        Sortedstation.clear();
        stationList = "";
        for(int i=0; i<getPoints().size()-2 ; i+=2){
            for(int j=0;j<station.size();j++){
                if((getPoints().get(i) == station.get(j).getCenterX()) && (getPoints().get(i+1) == station.get(j).getCenterY())){
                    stationList += station.get(j).getStationName().getText()+"\n";
                    Sortedstation.add(station.get(j));
                }
            }  
        }
    }
    public String wholeResult(){
//        stationList();
        return stationList;
    }

    /**
     * This function return the current state of the data to draw or process
     * a job.
     * @return STARTING_LINE start to make line.
     */
    @Override
    public MMMState getStartingState() {
	return MMMState.STARTING_POLYLINE;
    }
    public boolean getCircular() {
	return circular;
    }
    public boolean setCircular(boolean circular) {
	return this.circular = circular;
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
    public Color polyLineColor(){
        return (Color)this.getStroke();
    }
    public void makenew(int x, int y) {
	startX = x;
	startY = y;
        
        getPoints().addAll(startX,startY);
    }
    public void bindfunc(){
        
        DoubleProperty startx = new SimpleDoubleProperty(getPoints().get(0));
        DoubleProperty starty = new SimpleDoubleProperty(getPoints().get(1));
        DoubleProperty endx = new SimpleDoubleProperty(getPoints().get(getPoints().size()-2));
        DoubleProperty endy = new SimpleDoubleProperty(getPoints().get(getPoints().size()-1));
        
        startx.bind(LineNameLeft.xProperty());
        starty.bind(LineNameLeft.yProperty());
        endx.bind(LineNameRight.xProperty());
        endy.bind(LineNameRight.yProperty());
        startx.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
           getPoints().set(0, (double)newValue);
        });
       
       starty.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
           getPoints().set(1, (double)newValue);

        });
       endx.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                getPoints().set(getPoints().size()-2, (double)newValue);
            }
        });
       endy.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                getPoints().set(getPoints().size()-1, (double)newValue);
            }
        });
    }
    /**
     * This function change the current coordinate of the line when user dragged line. 
     * @param x : x coordinate
     * @param y : y coordinate 
     */
    @Override
    public void drag(int x, int y) {
                        
/*
//        System.err.println("dragiing polyline");
	double diffX1 = x - startX;
	double diffY1 = y - startY;
        

        double startx =  diffX1;
        double starty =   diffY1;
        
        for(int i=0;i<getPoints().size();i++){
            if(i % 2==0)
                getPoints().set(i, getPoints().get(i)+startx);
            else
                getPoints().set(i, getPoints().get(i)+starty);
        }

        
        setLayoutX(startx);
        setLayoutY(starty);
        
        LineNameLeft.setX(getPoints().get(0));
        LineNameLeft.setY(getPoints().get(1));
        LineNameRight.setX(getPoints().get(getPoints().size()-2));
        LineNameRight.setY(getPoints().get(getPoints().size()-1));
        
        startX = x;
        startY = y;*/
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
//	startXProperty().set(initX);
//	startYProperty().set(initY);
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
    public void Wpressed(double movement){
        for(int i=0;i<this.getPoints().size();i++){
            if( i% 2 ==1){//if i means y coordinate
                    this.getPoints().set(i, (this.getPoints().get(i) +movement));
                }
        }
    }
    public void Apressed(double movement){
        for(int i=0;i<this.getPoints().size();i++){
            if( i% 2 ==0){//if i means x coordinate
                    this.getPoints().set(i, (this.getPoints().get(i) +movement));
                }
        }
    }
    public void Spressed(double movement){

        for(int i=0;i<this.getPoints().size();i++){
            if( i% 2 ==1){//if i means y coordinate
                    this.getPoints().set(i, (this.getPoints().get(i) -movement));
                }
        }
    }
    public void Dpressed(double movement){
        for(int i=0;i<this.getPoints().size();i++){
            if( i% 2 ==0){//if i means x coordinate
                    this.getPoints().set(i, (this.getPoints().get(i) -movement));
                }
        }
    }
}
