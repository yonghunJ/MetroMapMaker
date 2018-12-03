package mmm.gui;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.shape.Shape;
import mmm.data.MMMData;
import mmm.data.Draggable;
import mmm.data.MMMState;
import static mmm.data.MMMState.DRAGGING_SHAPE;
import static mmm.data.MMMState.SELECTING_SHAPE;
import static mmm.data.MMMState.POLYLINE_DWARING;
import djf.AppTemplate;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.DropShadow;
import jtps.jTPS;
import mmm.data.DraggableImage;
import mmm.data.DraggablePolyline;
import mmm.data.DraggableStation;
import mmm.data.DraggableText;
import static mmm.data.MMMState.DRAGGING_NOTHING;
import mmm.transactions.FontFamilyText_Transaction;
import mmm.transactions.MoveImage_Transaction;
import mmm.transactions.MoveStation_Transaction;
import mmm.transactions.MoveText_Transaction;

/**
 * This class responds to interactions with the rendering surface.
 *
 * @author Yonghun Jeong
 * @version 1.0
 */
public class CanvasController {

    AppTemplate app;
    double x_coordinate;
    double y_coordinate;
    double x_coordinate_text;
    double y_coordinate_text;
    /**
     * Constructor for initializing the CanvasController, note that this constructor
     * will fully setup the CanvasController for use.
     * 
     * @param initApp initializse the AppTemplate
     */
    public CanvasController(AppTemplate initApp) {
        app = initApp;
    }
    public void processCanvasMousePressLeft(int x, int y) {
        MMMData dataManager = (MMMData) app.getDataComponent();
        if (dataManager.isInState(POLYLINE_DWARING)) {
            Scene scene = app.getGUI().getPrimaryScene();
            scene.setCursor(Cursor.DEFAULT);
            dataManager.setState(SELECTING_SHAPE);
            dataManager.NewPolylineEnd(x, y);
        }
    }
    /**
     * Respond to mouse presses on the rendering surface, which we call canvas,
     * but is actually a Pane.
     * 
     * @param x coordinate x
     * @param y coordinate y
     */
    public void processCanvasMousePress(int x, int y) {
        MMMData dataManager = (MMMData) app.getDataComponent();
        if (dataManager.isInState(SELECTING_SHAPE)) {
            
            // SELECT THE TOP SHAPE
            Shape shape = dataManager.selectTopShape(x, y);
            Scene scene = app.getGUI().getPrimaryScene();
           
            
//Change the line combobox when user clicked the line on the canvas
            if(shape instanceof DraggablePolyline){
                //change the colorpicker when user click the line on the canvas
                MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
                workspace.getCircle().setFill(shape.getStroke());
                
                //communicate with combobox
                ComboBox<String> cb=  ((MMMWorkspace)app.getWorkspaceComponent()).getmMetroLine();
                for( int i=0;i< cb.getItems().size();i++){
                    if(cb.getItems().get(i).equals(((DraggablePolyline)shape).getLeftText().getText())){
                        cb.getSelectionModel().select(i);
                    }
                }
                //to communicate with line thickness slider   getStationRadius
                ((MMMWorkspace)app.getWorkspaceComponent()).getThicknessSlider().setValue(((DraggablePolyline)shape).getStrokeWidth());
            }
            
            if(shape instanceof DraggableStation){
                x_coordinate = ((DraggableStation)shape).getCenterX();
                y_coordinate = ((DraggableStation)shape).getCenterY();
                x_coordinate_text = ((DraggableStation)shape).getStationName().getX();
                y_coordinate_text = ((DraggableStation)shape).getStationName().getY();
                ComboBox<String> cb=  ((MMMWorkspace)app.getWorkspaceComponent()).getmMetroStation();
                for( int i=0;i< cb.getItems().size();i++){
                    if(cb.getItems().get(i).equals(((DraggableStation)shape).getStationName().getText())){
                        cb.getSelectionModel().select(i);
                    }
                } 
                MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
                workspace.getBackgroundColorText().setFill(((DraggableStation) shape).getStationName().getFill());
                //change the font family combo box
                workspace.getFontFamilyButton().getSelectionModel().select(((DraggableStation) shape).getStationName().getFamily());
                //change the font size combo box
                workspace.getFontSizeButton().getSelectionModel().select(((DraggableStation) shape).getStationName().getFontSize());
                //change the bold button
                DropShadow shadow = new DropShadow();
                if(((DraggableStation)shape).getStationName().getIsBold())
                    workspace.getFontBoldButton().setEffect(shadow);
                else
                    workspace.getFontBoldButton().setEffect(null);
                
                if(((DraggableStation)shape).getStationName().getIsItalic())
                    workspace.getFontItalicsButton().setEffect(shadow);
                else
                    workspace.getFontItalicsButton().setEffect(null);
                
                ((MMMWorkspace)app.getWorkspaceComponent()).getStationRadius().setValue(((DraggableStation)shape).getRadius());
                //If station is clicked then change the circle color in metro stations
                ((MMMWorkspace)app.getWorkspaceComponent()).getCircle2forStation().setFill(((DraggableStation)shape).getFill());
            }
            
            
            if(shape instanceof DraggableImage){
                x_coordinate = ((DraggableImage)shape).getX();
                y_coordinate = ((DraggableImage)shape).getY();
            }
            
            
            if(shape instanceof DraggableText){
                x_coordinate_text = ((DraggableText)shape).getX();
                y_coordinate_text = ((DraggableText)shape).getY();
                MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
                workspace.getBackgroundColorText().setFill(shape.getFill());
                workspace.getFontFamilyButton().getSelectionModel().select(((DraggableText) shape).getFamily());
                //change the font size combo box
                workspace.getFontSizeButton().getSelectionModel().select(((DraggableText) shape).getFontSize());
                //change the bold button
                DropShadow shadow = new DropShadow();
                if(((DraggableText) shape).getIsBold())
                    workspace.getFontBoldButton().setEffect(shadow);
                else
                    workspace.getFontBoldButton().setEffect(null);
                
                 if(((DraggableText)shape).getIsItalic())
                    workspace.getFontItalicsButton().setEffect(shadow);
                else
                    workspace.getFontItalicsButton().setEffect(null);
            }
            
            
            // AND START DRAGGING IT
            if (shape != null) {
                if(dataManager.getSelectedShape() instanceof DraggableStation && scene.getCursor() == Cursor.HAND){ 
                    dataManager.selectedStationToLine();//add stataion to the line if line is not included in the station arraylist
                }
                if(dataManager.getSelectedShape() instanceof DraggablePolyline && scene.getCursor() == Cursor.HAND){ 
                    dataManager.addStationToLineMove(x,y);
                }
                if(dataManager.getSelectedShape() instanceof DraggableStation && scene.getCursor() == Cursor.OPEN_HAND){ 
                    dataManager.selectedStationToLine();
                    dataManager.removeStationFromTheLine();//add stataion to the line if line is not included in the station arraylist
                }
                
                if(scene.getCursor() != Cursor.HAND && scene.getCursor() != Cursor.OPEN_HAND){ //for add station to the line
                    scene.setCursor(Cursor.MOVE);
                }
                 
                dataManager.setState(MMMState.DRAGGING_SHAPE);
                app.getGUI().updateToolbarControls(false);
            } else {
                scene.setCursor(Cursor.DEFAULT);
                dataManager.setState(DRAGGING_NOTHING);
                app.getWorkspaceComponent().reloadWorkspace(dataManager);
            }
        } else if (dataManager.isInState(MMMState.STARTING_STATION)) {
            dataManager.startNewStation(x, y);
            dataManager.setState(MMMState.SELECTING_SHAPE);
        } else if (dataManager.isInState(MMMState.STARTING_IMAGE)) {
//            dataManager.startNewStation(x, y);
        } else if (dataManager.isInState(MMMState.STARTING_TEXT)) {
//            dataManager.startNewStation(x, y);
        } else if (dataManager.isInState(MMMState.STARTING_POLYLINE)) {
            dataManager.startNewPolyline(x, y);
        } else if(dataManager.isInState(MMMState.POLYLINE_DWARING)) {
            dataManager.NewPolylineDrawing(x, y);
        } 
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);
    }

    /**
     * Respond to mouse dragging on the rendering surface, which we call canvas,
     * but is actually a Pane.
     * @param x coordinate x
     * @param y coordinate y
     */
    public void processCanvasMouseDragged(int x, int y) {
        
        MMMData dataManager = (MMMData) app.getDataComponent();
        
        
        if (dataManager.isInState(DRAGGING_SHAPE)) {
                Draggable selectedDraggableShape = (Draggable) dataManager.getSelectedShape();
               
//                if(selectedDraggableShape instanceof DraggableStation){
//                    if(((DraggableStation) selectedDraggableShape).getStationWhichLineInclude().isEmpty()){
//                        selectedDraggableShape.drag(x, y);
//                    }else{
//                        for(int i=0;i<((DraggableStation) selectedDraggableShape).getStationWhichLineInclude().size();i++){
//                            DraggablePolyline line = ((DraggableStation) selectedDraggableShape).getStationWhichLineInclude().get(i);
//                            if(line.contains(
//                            ((DraggableStation) selectedDraggableShape).getCenterX(), ((DraggableStation) selectedDraggableShape).getCenterX())){
//                                //((DraggableStation) selectedDraggableShape).drag(x, y);
//                            }
//                        } 
//                    }
//                }else
                    selectedDraggableShape.drag(x, y);
                app.getGUI().updateToolbarControls(false);
        }
    }

    /**
     * Respond to mouse button release on the rendering surface, which we call canvas,
     * but is actually a Pane.
     * @param x coordinate x
     * @param y coordinate y
     */
    public void processCanvasMouseRelease(int x, int y) {
        MMMData dataManager = (MMMData) app.getDataComponent();
        if (dataManager.isInState(MMMState.DRAGGING_SHAPE)) {
            dataManager.setState(SELECTING_SHAPE);
            Scene scene = app.getGUI().getPrimaryScene();
            if(scene.getCursor() != Cursor.HAND && scene.getCursor() != Cursor.OPEN_HAND){ //for add station to the line
                    scene.setCursor(Cursor.DEFAULT);
                }
            app.getGUI().updateToolbarControls(false);
            
            Shape aa =dataManager.selectTopShape(x, y);
            dataManager.highlightShape(aa);
            Shape shape = dataManager.getSelectedShape();
            
            if(shape instanceof DraggableStation){
                jTPS tps = app.getTPS();
                MoveStation_Transaction newTransaction = new MoveStation_Transaction((DraggableStation)shape,x_coordinate,y_coordinate,((DraggableStation)shape).getCenterX(),((DraggableStation)shape).getCenterY(),
                                                                    x_coordinate_text,y_coordinate_text,((DraggableStation)shape).getStationName().getX(),((DraggableStation)shape).getStationName().getY());
                tps.addTransaction(newTransaction);
            }
            if(shape instanceof DraggableText){
                jTPS tps = app.getTPS();
                MoveText_Transaction newTransaction = new MoveText_Transaction((DraggableText)shape,x_coordinate_text,y_coordinate_text,((DraggableText)shape).getX(),((DraggableText)shape).getY());
                tps.addTransaction(newTransaction);
            }
            if(shape instanceof DraggableImage){
                jTPS tps = app.getTPS();
                MoveImage_Transaction newTransaction = new MoveImage_Transaction((DraggableImage)shape,x_coordinate,y_coordinate,((DraggableImage)shape).getX(),((DraggableImage)shape).getY());
                tps.addTransaction(newTransaction);
            }
            
        } else if (dataManager.isInState(MMMState.DRAGGING_NOTHING)) {
            dataManager.setState(SELECTING_SHAPE);
        }
    }
}
