
package mmm.data;

import javafx.scene.shape.Rectangle;

/**
 * This is a draggable Image for our MetroMapMaker application.
 * 
 * @author Yonghun Jeong
 * @version 1.0
 */
public class DraggableImage extends Rectangle implements Draggable {
    double startX;
    double startY;
    String imageName;
    String imageAdress;
     /**
     * Constructor for initializing the Draggable object, note that this constructor
     * will fully setup the DraggableImage for use.
     */
    public DraggableImage() {
	setX(0.0);
	setY(0.0);
	setWidth(0.0);
	setHeight(0.0);
	setOpacity(1.0);
	startX = 0.0;
	startY = 0.0;
    }

    /**
     * This function return the current state of the data to draw or process
     * a job.
     * @return MMMState.STARTING_IMAGE 
     * 
     */
    @Override
    public MMMState getStartingState() {
	return MMMState.STARTING_IMAGE;
    }
    /**
     * This function designate the initial coordinate of the data to make.
     */
    @Override
    public void start(int x, int y) {
	startX = x;
	startY = y;
	//setX(x);
	//setY(y);
    }
    /**
     * This function change the current coordinate of the Image when user dragged image. 
     */
    @Override
    public void drag(int x, int y) {
	//double diffX = x - (getX() + (getWidth()/2));
	//double diffY = y - (getY() + (getHeight()/2));
	double diffX = x - startX;
        double diffY = y - startY;
        double newX = getX() + diffX;
	double newY = getY() + diffY;
	xProperty().set(newX);
	yProperty().set(newY);
        startX = x;
	startY = y;
    }
    /**
     * This function print the current coordinate. 
     * @param x  : x coorinate
     * @param y :  y coorinate
     * @return string of coordinate.
     */
    public String cT(double x, double y) {
	return "(x,y): (" + x + "," + y + ")";
    }
    /**
     * This function change the size of the image.
     */
    @Override
    public void size(int x, int y) {
	
        //double width = x - getX();
        //double height = y - getY();
        //setX(x);
	//setY(y);
        double width = x - startX;
        double height = y - startY;
        double newX = x - width;
        double newY = y - height;
        
        widthProperty().set(width);
	heightProperty().set(height);
        setX(newX);
	setY(newY);
    }
    /**
     * This function show the curren coordinate and size of the image when load the file in to the workspace. 
     * @param initX  : initial x coorinate
     * @param initY :  initial y coorinate
     * @param initWidth :  initial width
     * @param initHeight :  intial height
     */
    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
	xProperty().set(initX);
	yProperty().set(initY);
	widthProperty().set(initWidth);
	heightProperty().set(initHeight);
    }
    /**
     * This function return the shape type as IMAGE to do a job.
     * 
     * @return shapeType of Image
     */
    @Override
    public String getShapeType() {
	return IMAGE;
    }
    /**
     * This function return to name of image to save and load the file. 
     * 
     * @return string of imageName
     */
//    public String getImageName(){
//        return imageName;
//    }
//    /**
//     * This function set the name of the image to load the file.
//     */
//    public void setImageName(String name){
//        imageName = name;
//    }
    /**
     * This function return to address of image to save and load the file. 
     */
    public String getImageAdress(){
        return imageAdress;
    }
    /**
     * This function return to address of image to load the file. 
     */
    public void setImageAdress(String adress ){
        imageAdress = adress;
    }
    public void Wpressed(double movement){
        setY(getY()+movement);
    }
    public void Apressed(double movement){
        setX(getX()+movement);
    }
    public void Spressed(double movement){

        setY(getY()-movement);
    }
    public void Dpressed(double movement){
        setX(getX()-movement);
    }
}
