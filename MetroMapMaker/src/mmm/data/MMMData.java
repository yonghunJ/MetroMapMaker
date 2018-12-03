package mmm.data;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import static mmm.data.MMMState.SELECTING_SHAPE;
import mmm.gui.MMMWorkspace;
import djf.components.AppDataComponent;
import djf.AppTemplate;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Rectangle;
import javax.imageio.ImageIO;
import jtps.jTPS;
import mmm.transactions.AddPolyline_Transaction;
import mmm.transactions.AddStationToLine_Transaction;
import mmm.transactions.AddStation_Transaction;


/**
 * This class serves as the data management component for this application.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class MMMData implements AppDataComponent {
    // FIRST THE THINGS THAT HAVE TO BE SAVED TO FILES
    
    // THESE ARE THE SHAPES TO DRAW
    ObservableList<Node> shapes;
    
    // THE BACKGROUND COLOR
    Color backgroundColor;
    
    // AND NOW THE EDITING DATA

    // THIS IS THE SHAPE CURRENTLY BEING SIZED BUT NOT YET ADDED
    Shape newShape;

    // THIS IS THE SHAPE CURRENTLY SELECTED
    Shape selectedShape;

    // FOR FILL AND OUTLINE
    Color currentFillColor;
    Color currentOutlineColor;
    double currentBorderWidth;

    // CURRENT STATE OF THE APP
    MMMState state;

    // THIS IS A SHARED REFERENCE TO THE APPLICATION
    AppTemplate app;
    
    // USE THIS WHEN THE SHAPE IS SELECTED
    Effect highlightedEffect;

    public static final String WHITE_HEX = "#FFFFFF";
    public static final String BLACK_HEX = "#000000";
    public static final String YELLOW_HEX = "#EEEE00";
    public static final Paint DEFAULT_BACKGROUND_COLOR = Paint.valueOf(WHITE_HEX);
    public static final Paint HIGHLIGHTED_COLOR = Paint.valueOf(YELLOW_HEX);
    public static final int HIGHLIGHTED_STROKE_THICKNESS = 3;

    /**
     * THis constructor creates the data manager and sets up the data management components
     *
     *
     * @param initApp The application within which this data manager is serving.
     */
    public MMMData(AppTemplate initApp) {
	// KEEP THE APP FOR LATER
	app = initApp;

	// NO SHAPE STARTS OUT AS SELECTED
	newShape = null;
	selectedShape = null;

	// INIT THE COLORS
	currentFillColor = Color.web(WHITE_HEX);
	currentOutlineColor = Color.web(BLACK_HEX);
	currentBorderWidth = 1;
	
	// THIS IS FOR THE SELECTED SHAPE
	DropShadow dropShadowEffect = new DropShadow();
	dropShadowEffect.setOffsetX(0.0f);
	dropShadowEffect.setOffsetY(0.0f);
	dropShadowEffect.setSpread(1.0);
	dropShadowEffect.setColor(Color.YELLOW);
	dropShadowEffect.setBlurType(BlurType.GAUSSIAN);
	dropShadowEffect.setRadius(15);
	highlightedEffect = dropShadowEffect;
    }
    /**
     * This function return the ObeservableList to get shapes that is saved
     * 
     * @return ObservableList<Node> : the list of the shapes
     */
    public ObservableList<Node> getShapes() {
	return shapes;
    }
    /**
     * This method processes a user request to take a snapshot of the
     * current scene.
     */
    public void processSnapshot(String filePath) {
	MMMWorkspace workspace = (MMMWorkspace)app.getWorkspaceComponent();
	Pane canvas = workspace.getCanvas();
	WritableImage image = canvas.snapshot(new SnapshotParameters(), null);
	File file = new File(filePath+" Metro.png");
	try {
	    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
	}
	catch(IOException ioe) {
	    ioe.printStackTrace();
	}
    }
    /**
     * This function return the color of te background
     * 
     * @return Color : background color
     */
    public Color getBackgroundColor() {
	return backgroundColor;
    }
    
    /**
     * This function set the initial shapes
     * 
     * @param initShapes : the list of the shapes
     */
    public void setShapes(ObservableList<Node> initShapes) {
	shapes = initShapes;
    }
    
    /**
     * This function set the background color
     * 
     * @param initBackgroundColor : background color
     */
    public void setBackgroundColor(Color initBackgroundColor) {
	backgroundColor = initBackgroundColor;
	MMMWorkspace workspace = (MMMWorkspace)app.getWorkspaceComponent();
//        StackPane canvas = workspace.getStackPane();
	Pane canvas = workspace.getCanvas();
	BackgroundFill fill = new BackgroundFill(backgroundColor, null, null);
	Background background = new Background(fill);
	canvas.setBackground(background);
        canvas.setOpacity(0.9);
    }

    /**
     * This function removed selected shape
     */
    public void removeSelectedShape() {
	if (selectedShape != null) {
	    shapes.remove(selectedShape);
	    selectedShape = null;
	}
    }
    /**
     * This function move the selected shape to back
     */
    public void moveSelectedShapeToBack() {
	if (selectedShape != null) {
	    shapes.remove(selectedShape);
	    if (shapes.isEmpty()) {
		shapes.add(selectedShape);
	    }
	    else {
		ArrayList<Node> temp = new ArrayList<>();
		temp.add(selectedShape);
		for (Node node : shapes)
		    temp.add(node);
		shapes.clear();
		for (Node node : temp)
		    shapes.add(node);
	    }
	}
    }
    /**
     * This function move the selected shape to front
     */
    public void moveSelectedShapeToFront() {
	if (selectedShape != null) {
	    shapes.remove(selectedShape);
	    shapes.add(selectedShape);
	}
    }
 
    /**
     * This function clears out the shapes.
     */
    @Override
    public void resetData() {
	setState(SELECTING_SHAPE);
	newShape = null;
	selectedShape = null;

	// INIT THE COLORS
	currentFillColor = Color.web(WHITE_HEX);
	currentOutlineColor = Color.web(BLACK_HEX);
	
	shapes.clear();
	((MMMWorkspace)app.getWorkspaceComponent()).getCanvas().getChildren().clear();
    }
    /**
     * This function let the new selected shape as highlight and selected shapes as unhighlighte.
     */
    public void selectSizedShape() {
	if (selectedShape != null)
	    unhighlightShape(selectedShape);
	selectedShape = newShape;
	highlightShape(selectedShape);
	newShape = null;

    }
    /**
     * This function make the shape unhighlight.
     * 
     * @param shape : to be unhighlighted
     */
    public void unhighlightShape(Shape shape) {
	selectedShape.setEffect(null);
    }
    /**
     * This function make the shape highlight.
     * @param shape : to be highlighted.
     */
    public void highlightShape(Shape shape) {
	if(shape == null){
        }else{
            shape.setEffect(highlightedEffect);
        }
    }
    /**
     * This function make the new line.
     * @param x : the x coordinate.
     * @param y : the y coordinate.
     * @param color : the color of the line
     */
    DraggablePolyline newLine ;
    Color lineColor;
    public void SetLineColor(Color color){this.lineColor = color;}
    String LineName; 
    public void SetLineName(String name){this.LineName = name;}
    public void startNewPolyline(int x, int y) {
        newLine = null;
	newLine = new DraggablePolyline();
        newLine.setLineName(LineName);
        newLine.setLineColor(lineColor);
        
	newLine.makenew(x, y);
        newShape = newLine;
        //initNewShape();
        setState(MMMState.POLYLINE_DWARING);
        jTPS tps = app.getTPS();
        MMMData data = (MMMData)app.getDataComponent();
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        AddPolyline_Transaction newTransaction = new AddPolyline_Transaction(workspace, data, (DraggablePolyline)newShape);
        tps.addTransaction(newTransaction);
//        jTPS tps = app.getTPS();
//        MMMData data = (MMMData)app.getDataComponent();
//        AddNode_Transaction newTransaction = new AddNode_Transaction(data, newShape);
//        tps.addTransaction(newTransaction);
    }
    public void NewPolylineDrawing(int x, int y){
        newLine.makenew(x,y); 
    }
    public void NewPolylineEnd(int x, int y){    
        
        
        newShape = newLine.getLeftText();
        ((DraggableText)newShape).starts(newLine.getPoints().get(0), newLine.getPoints().get(1));
        ((DraggableText)newShape).setWhoseText("POLYLINE");
        //initNewShape();
//        jTPS tps = app.getTPS();
//        MMMData data = (MMMData)app.getDataComponent();
//        AddNode_Transaction newTransaction = new AddNode_Transaction(data, newShape);
//        tps.addTransaction(newTransaction);
        
         newShape = newLine.getRightText();
         ((DraggableText)newShape).starts(newLine.getPoints().get(newLine.getPoints().size()-2), newLine.getPoints().get(newLine.getPoints().size()-1));
         ((DraggableText)newShape).setWhoseText("POLYLINE");
        //initNewShape();
//        AddNode_Transaction newTransaction1 = new AddNode_Transaction(data, newShape);
//        tps.addTransaction(newTransaction1);

        


        newLine.bindfunc();
    }
    
    
    /**
     * This function make the station .
     * @param x : the x coordinate.
     * @param y : the y coordinate.
     */
    
    public void startNewStation(int x, int y) {
	DraggableStation newStation = new DraggableStation();

        newStation.startx(x, y);
	newShape = newStation;
        ((DraggableStation)newShape).getStationName().setText(stationName);

        Scene scene = app.getGUI().getPrimaryScene();
	scene.setCursor(Cursor.DEFAULT);
        
        jTPS tps = app.getTPS();
        MMMData data = (MMMData)app.getDataComponent();
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        AddStation_Transaction newTransaction = new AddStation_Transaction(workspace, data, (DraggableStation)newShape);
        tps.addTransaction(newTransaction);
        scene.setCursor(Cursor.DEFAULT);
        
    }
    public String stationName = "null!";
    public void setStationName(String string){
        stationName = string;
    }
    DraggablePolyline selectedLine;
    public void setaddStationToLline(DraggablePolyline selectedLine){
        this.selectedLine = selectedLine;
    }
    String string;
    public void setBackground(String address){
        string  = address;
    }
    public String getBackground(){
        return string;
    }
    DraggableStation station;
    public void selectedStationToLine(){
        station = (DraggableStation)getSelectedShape();
    }
    double stationBeforeX;
    double stationBeforeY;
    double stationAfterX;
    double stationAfterY;
    public void addStationToLineMove(int x, int y){
        if(getSelectedShape().equals(selectedLine)){
//            jTPS tps = app.getTPS();
//            MMMData data = (MMMData)app.getDataComponent();
////            stationBeforeX = station.getCenterX();
////            stationBeforeY = station.getCenterY();
////            stationAfterX = x;
////            stationAfterY = y;
//            AddStationToLine_Transaction newTransaction = new AddStationToLine_Transaction(data,(DraggablePolyline)selectedLine, (DraggableStation)station,stationBeforeX,stationBeforeY,stationAfterX,stationAfterY);
//            tps.addTransaction(newTransaction);
            station.addPolyline(selectedLine);
            station.startx(x, y);
            selectedLine.addStation2(station,x,y);
            System.err.println(selectedLine.getPoints().get(0) + "   ," + selectedLine.getPoints().get(0));
            
        }
    }
    public void removeStationFromTheLine(){
        station.getStationWhichLineInclude().remove(selectedLine);
//        for(int i=0; i<station.getStationWhichLineInclude().size();i++){
//            if(selectedLine.equals(station.getStationWhichLineInclude().get(i))){
//                System.err.println("무슨 라인이니?   " + station.getStationWhichLineInclude().get(i));
//                station.getStationWhichLineInclude().remove(i);
//            }
//        }
        selectedLine.removeStationFromtheLine(station);
//        for(int j=0 ;j<selectedLine.getPoints().size(); j+=2){
//            if(selectedLine.getPoints().get(j) -station.getCenterX()<60 && selectedLine.getPoints().get(j+1) - station.getCenterY()<60){
//                    selectedLine.getPoints().remove(station);
////                    selectedLine.getPoints().remove();
//                    selectedLine.getStation().remove(station);
//                    selectedLine.Sortedstation.remove(station);
//                    selectedLine.stationList();
// 
//                    //System.err.println("removed");
//                }
//        }
    }
    
    /**
     * This function deselect the selected shape if there is one.
     */
    public void initNewShape() {
	// DESELECT THE SELECTED SHAPE IF THERE IS ONE
	if (selectedShape != null) {
	    unhighlightShape(selectedShape);
	    selectedShape = null;
	}

	// USE THE CURRENT SETTINGS FOR THIS NEW SHAPE
	MMMWorkspace workspace = (MMMWorkspace)app.getWorkspaceComponent();
	//newShape.setStroke(workspace.getOutlineColorPicker().getValue());
	//newShape.setStrokeWidth(workspace.getOutlineThicknessSlider().getValue());

	// ADD THE SHAPE TO THE CANVAS
	addShape(newShape);
	// GO INTO SHAPE SIZING MODE
	state = MMMState.DRAGGING_NOTHING;
        app.getGUI().setSaveButton(false);
        
//        jTPS tps = app.getTPS();
//        MMMData data = (MMMData)app.getDataComponent();
//        AddNode_Transaction newTransaction = new AddNode_Transaction(data, newShape);
//        tps.addTransaction(newTransaction);
    }
    public void changeStageTitle(String string){
          app.getGUI().getWindow().setTitle(string.substring(0, string.length()-5));
    }
    
    /**
     * This function return the new shape.
     * @return newShape
     */
    public Shape getNewShape() {
	return newShape;
    }
    public void setNewShape(Shape shape) {
	newShape = shape;
    }
    public void load_SetLineCombobox(DraggablePolyline shape){
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        workspace.getmMetroLine().getItems().add(shape.getLeftText().getText());
    }
    public void load_SetStationCombobox(DraggableStation shape){
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        workspace.getmMetroStation().getItems().add(shape.getStationName().getText());
        workspace.getStartStation().getItems().add(shape.getStationName().getText());
        workspace.getArrivalStation().getItems().add(shape.getStationName().getText());
    }
    public void reset_SetStationCombobox(){
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        workspace.getmMetroStation().getItems().clear();
        workspace.getStartStation().getItems().clear();
        workspace.getArrivalStation().getItems().clear();
        workspace.getmMetroLine().getItems().clear();
    }
    /**
     * This function return the selected shape.
     * @return selectedShape
     */
    public Shape getSelectedShape() {
	return selectedShape;
    }
     /**
     * This function set the selected shape.
     * 
     * @param initSelectedShape 
     */
    public void setSelectedShape(Shape initSelectedShape) {
        if(selectedShape != null){
            unhighlightShape(selectedShape);}
	selectedShape = initSelectedShape;
        
    }
     /**
     * This function return the shape to select the top shape of the canvas.
     * 
     * @param x coordinate of x
     * @param y coordinate of y
     * 
     * @return shape 
     */ 
    public Shape selectTopShape(int x, int y) {
        Shape shape = getTopShape(x, y);
        if(!(shape instanceof DraggableImage) && shape instanceof Rectangle)
            return null;
	if (shape == selectedShape)
	    return shape;
	
	if (selectedShape != null) {
	    unhighlightShape(selectedShape);
	}
	if (shape != null) {
	    highlightShape(shape);
	    MMMWorkspace workspace = (MMMWorkspace)app.getWorkspaceComponent();
	}
	selectedShape = shape;
	if (shape != null) {
	    ((Draggable)shape).start(x, y);
	}
	return shape;
    }
    /**
     * This function return the shape to select the top .
     * 
     * @param x coordinate of x
     * @param y coordinate of y
     * 
     * @return shape 
     */ 
    public Shape getTopShape(int x, int y) {
	for (int i = shapes.size() - 1; i >= 0; i--) {
	    Shape shape = (Shape)shapes.get(i);
	    if (shape.contains(x, y)) {
		return shape;
	    }
	}
	return null;
    }
    /**
     * This function add shape into the Observablelist.
     * 
     * @param shapeToAdd to be added
     */ 
    
    public void addShape(Shape shapeToAdd) {
        shapes.add(shapeToAdd);
    }
    public int getIndexOfNode(Node node) {
        return shapes.indexOf(node);
    }
    public void addNodeAtIndex(Node node, int nodeIndex) {
        shapes.add(nodeIndex, node);    
    }
    /**
     * This function remove shape from the Observablelist.
     * 
     * @param shapeToRemove to be removed
     */
    public void removeShape(Shape shapeToRemove) {
        int currentIndex = shapes.indexOf(shapeToRemove);
        if (currentIndex >= 0) {
	    shapes.remove(currentIndex);
        }
	//shapes.remove(shapeToRemove);
    }
    /**
     * This function return the current state.
     * 
     * @return state current working state.
     */
    public MMMState getState() {
	return state;
    }
    /**
     * This function access the current state.
     * 
     * @param initState current working state.
     */
    public void setState(MMMState initState) {
	state = initState;
    }
    /**
     * This function compare the current state with other state.
     * 
     * @param testState test the current state to testState.
     */
    public boolean isInState(MMMState testState) {
	return state == testState;
    }
    /**
     * This function is to undo.
     */
    public void processUndo() {
        //RedoUndoStack redo = new RedoUndoStack(shapes);
    }
    /**
     * This function is to redo.
     */
    public void processRedo() {
    }
    
}
