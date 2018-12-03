package mmm.gui;

import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javax.imageio.ImageIO;
import mmm.data.MMMData;
import mmm.data.MMMState;
import djf.AppTemplate;
import java.util.ArrayList;
import java.util.Optional;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import jtps.jTPS;
import mmm.data.DraggableImage;
import mmm.data.DraggablePolyline;
import mmm.data.DraggableStation;
import mmm.data.DraggableText;
import mmm.data.Graph;
import mmm.data.SnapToGrid;
import mmm.data.background_image;
import mmm.transactions.AddImage_Transaction;
import mmm.transactions.AddLabel_Transaction;
import mmm.transactions.AddPolyline_Transaction;
import mmm.transactions.BoldStation_Transaction;
import mmm.transactions.BoldText_Transaction;
import mmm.transactions.ChangeMoveLabel_Transaction;
import mmm.transactions.EditPolyline_Transaction;
import mmm.transactions.FontFamilyStation_Transaction;
import mmm.transactions.FontFamilyText_Transaction;
import mmm.transactions.FontSizeStation_Transition;
import mmm.transactions.FontSizeText_Transition;
import mmm.transactions.ItalicsStation_Transaction;
import mmm.transactions.ItalicsText_Transaction;
import mmm.transactions.RemoveImage_Transaction;
import mmm.transactions.RemoveLabel_Transaction;
import mmm.transactions.RemovePolyline_Transaction;
import mmm.transactions.RemoveStation_Transaction;


/**
 * This class responds to interactions with other UI logo editing controls.
 * 
 * @author Yonghunh Jeong
 * @version 1.0
 */
public class MMMEditController {
    AppTemplate app;
    MMMData dataManager;
    
    /**
     * Constructor for initializing the LogoEditController, note that this constructor
     * will fully setup the LogoEditController for use.
     * 
     * @param initApp initializse the AppTemplate
     */
    public MMMEditController(AppTemplate initApp) {
	app = initApp;
	dataManager = (MMMData)app.getDataComponent();
    }
    
    /**
     * This method manage the line to edit the color and name.
     */
    public void processEditLine() {
        MetroEditLine metroEdit = new MetroEditLine((DraggablePolyline)dataManager.getSelectedShape());
        metroEdit.init();
        
        Optional<String> result = metroEdit.getDialog().showAndWait();
        if (result.isPresent()) {
        // ok was pressed.
            
            Shape shape = dataManager.getSelectedShape();
            String beforeName = ((DraggablePolyline)shape).getLeftText().getText();
            jTPS tps = app.getTPS();
            MMMData data = (MMMData)app.getDataComponent();
            MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
            EditPolyline_Transaction newTransaction = new EditPolyline_Transaction(workspace, (DraggablePolyline)shape,beforeName,metroEdit.getTextLine(),
                                                                                    (Color)((DraggablePolyline)shape).getStroke(),metroEdit.getColorPickerLine());
            tps.addTransaction(newTransaction);
            System.err.println(metroEdit.getCheckboxLine());
            
            if(metroEdit.getCheckboxLine() ==true){
                double InitX =((DraggablePolyline)shape).getPoints().get(0);
                double InitY =((DraggablePolyline)shape).getPoints().get(0);
                double FinalX =((DraggablePolyline)shape).getPoints().get(((DraggablePolyline)shape).getPoints().size()-2);
                double FinalY =((DraggablePolyline)shape).getPoints().get(((DraggablePolyline)shape).getPoints().size()-1);
                double X = (InitX + FinalX) / 2;
                double Y = (InitY + FinalY) / 2;
                        
                ((DraggablePolyline)shape).getPoints().add(0,X);
                ((DraggablePolyline)shape).getPoints().add(1,Y);
                
                ((DraggablePolyline)shape).getPoints().add(X);
                ((DraggablePolyline)shape).getPoints().add(Y);
                
            }
            
            
        } else {
        // cancel might have been pressed.metroEdit.getTextLine(),metroEdit.getColorPickerLine()
        
        }
        
    }
    /**
     * This method manage the combobox of the line to select the specifir line.
     */
    public void processComboBoxLine() {
        ComboBox<String> cb=  ((MMMWorkspace)app.getWorkspaceComponent()).getmMetroLine();
        DraggablePolyline shape;
        MMMData goldata = (MMMData) app.getDataComponent();
        for(int i=0; i<goldata.getShapes().size();i++){
            if((goldata.getShapes().get(i)) instanceof DraggablePolyline){;
                shape = (DraggablePolyline)goldata.getShapes().get(i);

                if(cb.getValue() != null && cb.getValue().equals(shape.getLeftText().getText())){
                    goldata.setSelectedShape(((DraggablePolyline)goldata.getShapes().get(i)));
                    goldata.highlightShape(((DraggablePolyline)goldata.getShapes().get(i)));
                    MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
                    workspace.getCircle().setFill(shape.getStroke());
                }
            }
        }
    }
    /**
     * This method manage the combobox of the station.
     */
    public void processComboBoxStation() {
        ComboBox<String> cb=  ((MMMWorkspace)app.getWorkspaceComponent()).getmMetroStation();
        DraggableStation station;
        MMMData goldata = (MMMData) app.getDataComponent();
        for(int i=0; i<goldata.getShapes().size();i++){
            if((goldata.getShapes().get(i)) instanceof DraggableStation){
                station = (DraggableStation)goldata.getShapes().get(i);

                if(cb.getValue() != null && cb.getValue().equals(station.getStationName().getText())){
                    goldata.setSelectedShape(((DraggableStation)goldata.getShapes().get(i)));
                    goldata.highlightShape(((DraggableStation)goldata.getShapes().get(i)));
                    }
            }
        }
    }
    /**
     * This method processes a user request to make a Line on the canvas.
     */

    public void processPolyLineToDraw() {

        MMMData dataManager = (MMMData) app.getDataComponent();
        AddLineDialog addLineDialog = new AddLineDialog();
        addLineDialog.init();
        
        Optional<String> result = addLineDialog.getDialog().showAndWait();
        if (result.isPresent()) {
          // ok was pressed.
            dataManager.SetLineName(addLineDialog.getTextLine());
            dataManager.SetLineColor(addLineDialog.getColorPickerLine());


        } else {
        // cancel might have been pressed.
        }
        Scene scene = app.getGUI().getPrimaryScene();
        scene.setCursor(Cursor.CROSSHAIR);
        dataManager.setState(MMMState.STARTING_POLYLINE);
    }
    
    /**
     * This method let a user to remove the selected shape.
     */
    public void processRemoveSelectedShape() {
	// REMOVE THE SELECTED SHAPE IF THERE IS ONE
        if(dataManager.getSelectedShape() instanceof DraggablePolyline ){
            String deleted = ((DraggablePolyline)dataManager.getSelectedShape()).getLeftText().getText();
            Shape shape = dataManager.getSelectedShape();
            jTPS tps = app.getTPS();
           
            MMMData data = (MMMData)app.getDataComponent();
            MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
            RemovePolyline_Transaction newTransaction = new RemovePolyline_Transaction(workspace, data, (DraggablePolyline)shape);
            tps.addTransaction(newTransaction);
            
            
            // ENABLE/DISABLE THE PROPER BUTTONS
            ComboBox<String> cb=  ((MMMWorkspace)app.getWorkspaceComponent()).getmMetroLine();
                    for( int i=0;i< cb.getItems().size();i++){
                        if(cb.getItems().get(i).equals(deleted)){
                            cb.getItems().remove(i);
                        }
                    }
            workspace.reloadWorkspace(dataManager);
            app.getGUI().updateToolbarControls(false);
        }
    }
    /**
     * This method let a user to add the station to the line.
     */
    DraggablePolyline selectedLine;
    public void processAddStationToLine(){
        if(dataManager.getSelectedShape() instanceof DraggablePolyline){
            selectedLine = (DraggablePolyline)dataManager.getSelectedShape();
            dataManager.setaddStationToLline(selectedLine);
            Scene scene = app.getGUI().getPrimaryScene();
            scene.setCursor(Cursor.HAND);
        }
    }
    /**
     * This method let a user to remove the station from the line.
     */
    public void processRemoveStationFromLine(){
        if(dataManager.getSelectedShape() instanceof DraggablePolyline){
            selectedLine = (DraggablePolyline)dataManager.getSelectedShape();
            dataManager.setaddStationToLline(selectedLine);
            Scene scene = app.getGUI().getPrimaryScene();
            scene.setCursor(Cursor.OPEN_HAND);
        }
    }
    /**
     * This method show the all station to the user.
     */
    public void processlistAllStations(){
        String string ="";
        Shape shape = dataManager.getSelectedShape();
        if(shape instanceof DraggablePolyline){
            
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Metro Map Maker - Metro Line Stops");
            alert.setHeaderText(((DraggablePolyline)shape).getLeftText().getText() +" Stops");

            string = ((DraggablePolyline)shape).wholeResult();
            TextArea textArea = new TextArea(string);
            textArea.setEditable(false);
            textArea.setWrapText(true);

            GridPane expContent = new GridPane();
            expContent.add(textArea, 0, 0);

            alert.getDialogPane().setExpandableContent(expContent);
            alert.showAndWait();
        }
        
    }
    /**************************************************************************/
    /**
     * This method process a user to change the color of the station.
     */
    public void processChangeStationColor(){}
    
    public String sationName;
    public void setStationName(String string){ this.sationName = string; }
    public String getStationName(){ return sationName; }
    /**
     * This method process a user to add the station.
     */
    
    public void processAddStation(){
                
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("input station name");
        dialog.setHeaderText("Enter some text, or use default value.");
        
        Optional<String> result = dialog.showAndWait();
        String entered = "";
        if (result.isPresent()) {
            entered = result.get();
            setStationName(entered);
        }
        ComboBox<String> cb=  ((MMMWorkspace)app.getWorkspaceComponent()).getmMetroStation();
        //cb.getItems().add(entered);
        MMMWorkspace workspace = (MMMWorkspace)app.getWorkspaceComponent();
        //when user add station, route conboboxs are updated
//        workspace.getStartStation().getItems().add(entered);
//        workspace.getArrivalStation().getItems().add(entered);
        
        
        dataManager.setStationName(entered);
        //should get the name of the station using dialogbox
        Scene scene = app.getGUI().getPrimaryScene();
	scene.setCursor(Cursor.CROSSHAIR);
	
	// CHANGE THE STATE
	dataManager.setState(MMMState.STARTING_STATION);
	// ENABLE/DISABLE THE PROPER BUTTONS
	
	workspace.reloadWorkspace(dataManager);
    }
    /**
     * This method process a user to remove the station.
     */
    public void processRemoveStation(){
        // REMOVE THE SELECTED SHAPE IF THERE IS ONE
	Shape newShape = dataManager.getSelectedShape();
        jTPS tps = app.getTPS();
        MMMData data = (MMMData)app.getDataComponent();
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        RemoveStation_Transaction newTransaction = new RemoveStation_Transaction(workspace, data, (DraggableStation)newShape);
        tps.addTransaction(newTransaction);
        
	workspace.reloadWorkspace(dataManager);
	app.getGUI().updateToolbarControls(false);
    }
    /**
     * This method process a user to take a snap shot of the canvas.
     */
    public void processSnap(int Pixelsize){
        //ObservableList<Node> shapes  = dataManager.getShapes();
//        for(int i=0;i<shapes.size();i++){
//            Node shape =shapes.get(i);
        Shape shape = dataManager.getSelectedShape();

        if(shape instanceof DraggableStation){
            double x = ((DraggableStation)shape).getCenterX();
            double y = ((DraggableStation)shape).getCenterY();
            SnapToGrid snap = new SnapToGrid(Pixelsize);
            snap.nearlistPostion(((DraggableStation)shape).getCenterX(),((DraggableStation)shape).getCenterY());
            ((DraggableStation)shape).setCenterX(snap.GridAppliedLocationX());
            ((DraggableStation)shape).setCenterY(snap.GridAppliedLocationY());
            
            for(int i=0;i<((DraggableStation)shape).getStationWhichLineInclude().size();i++){
                for(int j=0;j<((DraggableStation)shape).getStationWhichLineInclude().get(i).getPoints().size();j+=2){

                    if(((DraggableStation)shape).getStationWhichLineInclude().get(i).getPoints().get(j) == x &&
                            ((DraggableStation)shape).getStationWhichLineInclude().get(i).getPoints().get(j+1) == y){
                        
                        ((DraggableStation)shape).getStationWhichLineInclude().get(i).getPoints().set(j,(double)snap.GridAppliedLocationX());
                        ((DraggableStation)shape).getStationWhichLineInclude().get(i).getPoints().set(j+1,(double)snap.GridAppliedLocationY());
                    }
                }
            }
        }if(shape instanceof DraggableText){
//            if(((DraggableText) shape).getWhoseText().equals("POLYLINE")){
                 SnapToGrid snap = new SnapToGrid(Pixelsize);
            snap.nearlistPostion(((DraggableText)shape).getX(),((DraggableText)shape).getY());
            ((DraggableText)shape).setX(snap.GridAppliedLocationX());
            ((DraggableText)shape).setY(snap.GridAppliedLocationY());
            }
//        }
        
    
    }
    /**
     * This method process a user to remove the label.
     */
    public void processmoveLabel(){
        Shape shape = dataManager.getSelectedShape();
        if(shape instanceof DraggableStation){
            
        jTPS tps = app.getTPS();
        MMMData data = (MMMData)app.getDataComponent();
        ChangeMoveLabel_Transaction newTransaction = new ChangeMoveLabel_Transaction((DraggableStation)shape, ((DraggableStation) shape).getstationLabelStatus());
        tps.addTransaction(newTransaction);
                
        }
    }
    /**
     * This method process a user to rotate the station.
     */
    public void processRotationStation(){
        Shape shape = dataManager.getSelectedShape();
        if(shape instanceof DraggableStation){
            if(((DraggableStation)shape).getStationName().getRotate() == 0)
                ((DraggableStation)shape).getStationName().setRotate(-90);
            else
                ((DraggableStation)shape).getStationName().setRotate(0);
        }
    }
    /******************************************************************************/
    /**
     * This method process a user to find the shortest way from two stations.
     */ 
    
    public void processFindLocation(String departure, String arrival){
        ObservableList<Node> shapes  = dataManager.getShapes();
        DraggableStation departureStation = new DraggableStation();
        DraggableStation ArrivalStation = new DraggableStation();
        int j=0;
        Node shape =null;
        //FIND THE DEPARTURE, ARRIVAL STATION 
        for( int i=0; i<shapes.size();i++){
            shape = shapes.get(i);
           if(shape instanceof DraggableStation){
               if(((DraggableStation)shape).getStationName().getText().equals(departure))
                   departureStation = (DraggableStation)shape;
               if(((DraggableStation)shape).getStationName().getText().equals(arrival))
                   ArrivalStation = (DraggableStation)shape;
           }
        }
        
        //TO SET THE STATION INHERENT NUMBER
        DraggableStation station = new DraggableStation();
        for( int i=0; i<shapes.size();i++){
            shape = shapes.get(i);
           if(shape instanceof DraggableStation){
               station = (DraggableStation)shape;
               j++;
               station.setMatrixNum(j);//
               
           }
        }
        //MAKE A GRAPH BASED ON STATION NUMBER : j IS THE NUMBER OF STATIONS
        Graph g = new Graph(100);
//        Graph g = new Graph(j);
        
        //SET THE VERTEX FROM x->y, the weight is always 1
        DraggablePolyline polyLine = new DraggablePolyline();
        for( int i=0; i<shapes.size();i++){
            shape = shapes.get(i);
           if(shape instanceof DraggablePolyline){
               polyLine = (DraggablePolyline)shape;//폴리라인 하나씩 가줘오거라
//               System.err.println("뭐냐 이거 영이냐 설마?"+ polyLine.Sortedstation.size());
               for(int m=0;m<polyLine.Sortedstation.size()-1;m++){
                   g.setMatrix(polyLine.Sortedstation.get(m).getMatrixNum(),polyLine.Sortedstation.get(m+1).getMatrixNum());
                   g.setMatrix(polyLine.Sortedstation.get(m+1).getMatrixNum(),polyLine.Sortedstation.get(m).getMatrixNum());
               }
           }
        }
        g.dikstra(departureStation.getMatrixNum(),ArrivalStation.getMatrixNum());
        g.result();
    
        
        int LineNumber=0;
        for( int i=0; i<shapes.size();i++){
            if(shapes.get(i) instanceof DraggablePolyline){
                LineNumber++;
            }
        }
        
        ArrayList<DraggableStation> stationList = new ArrayList<>();
        for(int i=0;i<g.getResultStationNum();i++){
            //TO GET THE LIST OF STATION
            for( int q=0; q<shapes.size();q++){
                if(shapes.get(q) instanceof DraggableStation){
                    if(g.resultPath()[i] == ((DraggableStation)shapes.get(q)).getMatrixNum()){//해당 번호의 역을 찾았다.
//                        System.err.println( ((DraggableStation)shapes.get(q)).getStationName().getText());
                       stationList.add((DraggableStation)shapes.get(q));
                    }
                }
            }
        }
        
        String LineName[] = new String[stationList.size()];
        System.err.println("지금 결과 스테이션 수 : "+stationList.size());
        for(int i=0;i<stationList.size()-1;i++){//0~2
            
            for(int m=0;m<stationList.get(i).getStationWhichLineInclude().size();m++){
                for(int n=0;n<stationList.get(i+1).getStationWhichLineInclude().size();n++){
                    if(stationList.get(i).getStationWhichLineInclude().get(m).getLeftText().getText().equals(stationList.get(i+1).getStationWhichLineInclude().get(n).getLeftText().getText())){
                        LineName[i+1]  = stationList.get(i).getStationWhichLineInclude().get(m).getLeftText().getText();
//                        System.err.println(LineName[i+1]);
                    }
                }
            }   
        }
        for(int m=0;m<stationList.get(stationList.size()-1).getStationWhichLineInclude().size();m++){
                for(int n=0;n<stationList.get(stationList.size()-2).getStationWhichLineInclude().size();n++){
        if(stationList.get(stationList.size()-1).getStationWhichLineInclude().get(m).getLeftText().getText().equals(
                            stationList.get(stationList.size()-2).getStationWhichLineInclude().get(n).getLeftText().getText())){}
                }
                LineName[stationList.size()-1] = stationList.get(stationList.size()-1).getStationWhichLineInclude().get(m).getLeftText().getText();
        }
        LineName[0] = LineName[1];
//        for(int i=0;i<LineName.length;i++)
//        {
//            System.err.println("=====");//included line
//            System.err.println(LineName[i]);//included line                 //이게 aabbcc순서대로 역
//        }
        
        int LineNum=1;
        for(int i=0;i<LineName.length-1;i++){
            if(!(LineName[i].equals(LineName[i+1]))){ //                       이게 라인 개수
                LineNum++;
            } 
        }
        int index[] = new int[LineNum];
        String LineSortedName[] = new String[LineNum];
        int LineSortedNum[] = new int[LineNum];
        
        LineSortedName[0] = LineName[0];
        int k=1;
        int inde =0;
        for(int i=0;i<LineName.length-1;i++){
            if(!(LineName[i].equals(LineName[i+1]))){
                LineSortedName[k] = LineName[i+1];
//                System.err.println(LineSortedName[k]);
                k++;
                index[inde] = i;
                inde++;
                
            }
        }


        int forname=0;
        for(int i=0;i<LineSortedName.length;i++)
        {
            for(int km=0;km<LineName.length;km++){
                if(LineSortedName[i].equals(LineName[km])){
                    LineSortedNum[i]++;
                }            
            }  
        }
        
//        for(int i=0;i<LineSortedName.length;i++){                   //sorted station
//            System.err.println(LineSortedName[i] + "," + LineSortedNum[i]);
//            System.err.println("*************");   
//        }
        

//        LineName[stationList.size()-1] =  LineName[stationList.size()-2];
//        System.err.println(LineName[stationList.size()-1]);
        
        
        
        
        
        
        
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Metro Map Maker - Route");
        alert.setHeaderText("Route from "+departure+" to "+ arrival);
        String total = "";
        String string1 = "Origin : "+departure+"\n";
        String string2 = "Destination: "+ arrival+"\n";
        String string3= "";
        for(int i=0;i<LineNum;i++){
            if(i ==0 || i==LineNum-1)
                string3 +=  LineSortedName[i] +" Line ("+(LineSortedNum[i]-1)+" stops)\n";
            else
                string3 +=  LineSortedName[i] +" Line ("+(LineSortedNum[i])+" stops)\n";
        }
        String string4 = "Total Stops: "+(LineName.length-2)+"\n";
        String string5 = "Estimated Time :: "+((LineName.length-2)*3+3)+"\n";
        String string6 = "Board "+LineName[0]+"Line: "+"at"+departure+"\n";
        String string7 = "";
        for(int i=0;i<index.length-1;i++){
            string7 +=  "Transfer to "+ LineSortedName[i+1]+" at "+stationList.get(index[i]).getStationName().getText()+"\n";
        }
        String string8 = "Disembark "+LineName[LineName.length-1]+"Line: "+"at"+arrival+"\n";
        total = string1+string2+string3+string4+string5+string6+string7+string8;
        
        alert.setContentText(total);


        alert.showAndWait();
    
    }
    public void processFindStartLocation(){
        ComboBox<String> cb=  ((MMMWorkspace)app.getWorkspaceComponent()).getStartStation();
        DraggableStation station;
        MMMData goldata = (MMMData) app.getDataComponent();
        for(int i=0; i<goldata.getShapes().size();i++){
            if((goldata.getShapes().get(i)) instanceof DraggableStation){;
                station = (DraggableStation)goldata.getShapes().get(i);

                if(cb.getValue() != null && cb.getValue().equals(station.getStationName().getText())){
                    goldata.setSelectedShape(((DraggableStation)goldata.getShapes().get(i)));
                    goldata.highlightShape(((DraggableStation)goldata.getShapes().get(i)));
                    }
            }
        }
    }
    public void processFindArrivalLocation(){
        ComboBox<String> cb=  ((MMMWorkspace)app.getWorkspaceComponent()).getArrivalStation();
        DraggableStation station;
        MMMData goldata = (MMMData) app.getDataComponent();
        for(int i=0; i<goldata.getShapes().size();i++){
            if((goldata.getShapes().get(i)) instanceof DraggableStation){;
                station = (DraggableStation)goldata.getShapes().get(i);

                if(cb.getValue() != null && cb.getValue().equals(station.getStationName().getText())){
                    goldata.setSelectedShape(((DraggableStation)goldata.getShapes().get(i)));
                    goldata.highlightShape(((DraggableStation)goldata.getShapes().get(i)));
                    }
            }
        }
    }
    /**
     * This method process a user to set the background as an image.
     */
    public void processBackground(Color color){
       if (color != null) {
	    dataManager.setBackgroundColor(color);
            
	    app.getGUI().updateToolbarControls(false);
	}
    }
    public void processStationColor(Color color){
       if (color != null) {
           Shape shape = dataManager.getSelectedShape();
           if(shape instanceof DraggableStation)
               ((DraggableStation)shape).setFill(color);
	}
    }

    public void processSetImageBackground(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("set Image Background");
        alert.setHeaderText("Do you want to add Background Image?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File("./images"));
            File selectedFile = fc.showOpenDialog(app.getGUI().getWindow());
            
            if(selectedFile != null){
                
                
                background_image rec = new background_image();
   
                Image img = new Image(selectedFile.toURI().toString());
                String addr = selectedFile.toURI().toString();
                rec.setFill(new ImagePattern(img));
                rec.setWidth(img.getWidth());
                rec.setHeight(img.getHeight());
                MMMWorkspace workspace = (MMMWorkspace)app.getWorkspaceComponent();

                //location of the picture as center
                rec.setLayoutX(workspace.getCanvas().getWidth()/2 - rec.getWidth()/2);
                rec.setLayoutY(workspace.getCanvas().getHeight()/2 - rec.getHeight()/2);

                //make image transparent
                rec.setOpacity(0.5);
                rec.setBackgroundAddress(addr);
                
                int i = 0;
                for(Node shape : dataManager.getShapes()){//if there is one picture in the shapes, change the picture
                    if((shape instanceof Rectangle) &&!(shape instanceof DraggableImage) ){
                        ((Rectangle)shape).setFill(rec.getFill());
                        ((Rectangle)shape).setWidth(img.getWidth());
                        ((Rectangle)shape).setHeight(img.getHeight());
                        ((Rectangle)shape).setLayoutX(workspace.getCanvas().getWidth()/2 - rec.getWidth()/2);
                        ((Rectangle)shape).setLayoutY(workspace.getCanvas().getHeight()/2 - rec.getHeight()/2);
                        i++;
                    }
                }
                if(i==0){                                    //if there is no picture in the shapes, add the picture
                    dataManager.addShape(rec);
                    dataManager.setSelectedShape(rec);
                    dataManager.moveSelectedShapeToBack();
                }
                workspace.reloadWorkspace(dataManager);
                
                
                selectedFile =null;
            }
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }
    /**
     * This method process a user to add the Image to the canvas.
     */
    
    public void processAddImage(){
    
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Landmark");
        alert.setHeaderText("Do you want to add Landmark Image?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File("./images"));
            File selectedFile = fc.showOpenDialog(app.getGUI().getWindow());
            if(selectedFile != null){
                
                
                DraggableImage draggableimage= new DraggableImage();
                Image img = new Image(selectedFile.toURI().toString());
                draggableimage.setFill(new ImagePattern(img));
                draggableimage.setWidth(img.getWidth());
                draggableimage.setHeight(img.getHeight());
                draggableimage.setImageAdress(selectedFile.toURI().toString());
                MMMWorkspace workspace = (MMMWorkspace)app.getWorkspaceComponent();

                //location of the picture as center
                draggableimage.setLayoutX(0);
                draggableimage.setLayoutY(0);

                //make image transparent
                draggableimage.setOpacity(1.0);

                jTPS tps = app.getTPS();
                MMMData data = (MMMData)app.getDataComponent();
                AddImage_Transaction newTransaction = new AddImage_Transaction(workspace, data, (DraggableImage)draggableimage);
                tps.addTransaction(newTransaction);
//                dataManager.addShape(draggableimage);
  
//                workspace.reloadWorkspace(dataManager);

            }
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    
    }
    /**
     * This method process a user to add the label.
     */
    public void processAddLabel(){
        TextInputDialog dialog = new TextInputDialog("Label");
        dialog.setTitle("Label Input Dialog");
        dialog.setHeaderText("Add Labe");
        dialog.setContentText("Please Input:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        DraggableText draggableText = new DraggableText();
        if (result.isPresent()){
            draggableText.setText(result.get());
            
            jTPS tps = app.getTPS();
            MMMData data = (MMMData)app.getDataComponent();
            MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
            AddLabel_Transaction newTransaction = new AddLabel_Transaction(workspace, data, (DraggableText)draggableText);
            tps.addTransaction(newTransaction);
//            dataManager.setNewShape(draggableText);
//            dataManager.initNewShape();
        }else{
        }
    }
    
    
    /**
     * This method process a user to remove the label that is selected.
     */
    public void processRemoveElment(){
        Shape shape = dataManager.getSelectedShape();
        if(shape instanceof DraggableText){
            jTPS tps = app.getTPS();
            MMMData data = (MMMData)app.getDataComponent();
            MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
            RemoveLabel_Transaction newTransaction = new RemoveLabel_Transaction(workspace, data, (DraggableText)shape);
            tps.addTransaction(newTransaction); 
        }
        if(shape instanceof DraggableImage){
            jTPS tps = app.getTPS();
            MMMData data = (MMMData)app.getDataComponent();
            MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
            RemoveImage_Transaction newTransaction = new RemoveImage_Transaction(workspace, data, (DraggableImage)shape);
            tps.addTransaction(newTransaction); 
        }
    }
    /******************************************************************************/
    /**
     * This method give an information about the application.
     */
    public void ProcessLearnAbout(){
        LearnAboutApp learn = LearnAboutApp.getSingleton();
        learn.init();
    }
    public void processBackgroundFont(Color color){
        if (color != null) {
            Shape shape = dataManager.getSelectedShape();
            if(shape instanceof DraggableText){
                ((DraggableText)shape).setFill(color);
            }
            if(shape instanceof DraggableStation){
                ((DraggableStation)shape).getStationName().setFill(color);
            }
	}
    }
    /******************************************************************************/
    /**
     * This method process a label to be bold.
     * @param button
     */
    public void processSetFontBoldChange(Button button){
            Shape shape = dataManager.getSelectedShape();
            if(shape instanceof DraggableText){
                boolean is_bold = ((DraggableText)shape).getIsBold();
                if(is_bold){
                    jTPS tps = app.getTPS();
                    BoldText_Transaction newTransaction = new BoldText_Transaction((DraggableText)shape,false,button,true);
                    tps.addTransaction(newTransaction);

                    
                }else{
                    jTPS tps = app.getTPS();
                    BoldText_Transaction newTransaction = new BoldText_Transaction((DraggableText)shape,true,button,false);
                    tps.addTransaction(newTransaction);
                }
            }
            if(shape instanceof DraggableStation){
                boolean is_bold = ((DraggableStation)shape).getStationName().getIsBold();
                
                if(is_bold){
                    jTPS tps = app.getTPS();
                    BoldStation_Transaction newTransaction = new BoldStation_Transaction((DraggableStation)shape,false,button,true);
                    tps.addTransaction(newTransaction);

                }else{
                    jTPS tps = app.getTPS();
                    BoldStation_Transaction newTransaction = new BoldStation_Transaction((DraggableStation)shape,true,button,false);
                    tps.addTransaction(newTransaction);
                }
            }
   }
    /**
     * This method process a label to be Italics.
     * @param button
     */
    public void processSetFontItalicsChange(Button button){
        Shape shape = dataManager.getSelectedShape();
            DropShadow shadow = new DropShadow();
            if(shape instanceof DraggableText){
                boolean is_italics = ((DraggableText)shape).getIsItalic();
                if(is_italics){
                    jTPS tps = app.getTPS();
                    ItalicsText_Transaction newTransaction = new ItalicsText_Transaction((DraggableText)shape,false,button,true);
                    tps.addTransaction(newTransaction);
                }else{
                    jTPS tps = app.getTPS();
                    ItalicsText_Transaction newTransaction = new ItalicsText_Transaction((DraggableText)shape,true,button,false);
                    tps.addTransaction(newTransaction);
                }
            }
            if(shape instanceof DraggableStation){
                boolean is_italics = ((DraggableStation)shape).getStationName().getIsItalic();
                
                if(is_italics){
                    jTPS tps = app.getTPS();
                    ItalicsStation_Transaction newTransaction = new ItalicsStation_Transaction((DraggableStation)shape,false,button,true);
                    tps.addTransaction(newTransaction);
                }else{
                    jTPS tps = app.getTPS();
                    ItalicsStation_Transaction newTransaction = new ItalicsStation_Transaction((DraggableStation)shape,true,button,false);
                    tps.addTransaction(newTransaction);
                }
            }
    }
    /**
     * This method process a label to change the font family.
     * @param value
     */
    public void processSetFontFamilyChange(String value){
        if (value != null) {
            Shape shape = dataManager.getSelectedShape();
            if(shape instanceof DraggableText){
                
                jTPS tps = app.getTPS();
                FontFamilyText_Transaction newTransaction = new FontFamilyText_Transaction((DraggableText)shape,((DraggableText) shape).getFamily(),value);
                tps.addTransaction(newTransaction);
            }
            if(shape instanceof DraggableStation){
                jTPS tps = app.getTPS();
                FontFamilyStation_Transaction newTransaction = new FontFamilyStation_Transaction((DraggableStation)shape,((DraggableStation) shape).getStationName().getFamily(),value);
                tps.addTransaction(newTransaction);
            }
	}
    }
    /**
     * This method process a label to change the font size.
     * @param value
     */
    public void processSetFontSizeChange(double value){
        Shape shape = dataManager.getSelectedShape();
        if(shape instanceof DraggableText){
            jTPS tps = app.getTPS();
                FontSizeText_Transition newTransaction = new FontSizeText_Transition((DraggableText)shape,((DraggableText) shape).getFontSize(),value);
                tps.addTransaction(newTransaction);
            
        }
        if(shape instanceof DraggableStation){
            jTPS tps = app.getTPS();
            FontSizeStation_Transition newTransaction = new FontSizeStation_Transition((DraggableStation)shape,((DraggableStation) shape).getStationName().getFontSize(),value);
                tps.addTransaction(newTransaction);
        }
    }
    /******************************************************************************/
    /**
     * This method process a canvas to show the grid on the canvas.
     */
    public void processShowGrid(){}

    /**
     * This method process user to zoom in/out the canvas.
     * @param node
     * @param factor
     * @param x
     * @param y
     */ 
    public void processZoom(Node node, double factor){//, double x, double y){
        Timeline timeline = new Timeline(60);
         double oldScale = node.getScaleX();
        double scale = oldScale * factor;
        double f = (scale / oldScale) - 1;

//        Bounds bounds = node.localToScene(node.getBoundsInLocal());
//        double dx = (x - (bounds.getWidth() / 2 + bounds.getMinX()));
//        double dy = (y - (bounds.getHeight() / 2 + bounds.getMinY()));
        
        // timeline that scales and moves the node
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().addAll(
//            new KeyFrame(Duration.millis(200), new KeyValue(node.translateXProperty(), node.getTranslateX() - f*dx)),
//            new KeyFrame(Duration.millis(200), new KeyValue(node.translateYProperty(), node.getTranslateY() - f*dy)),
            new KeyFrame(Duration.millis(200), new KeyValue(node.translateXProperty(), node.getTranslateX())),
            new KeyFrame(Duration.millis(200), new KeyValue(node.translateYProperty(), node.getTranslateY())),
            new KeyFrame(Duration.millis(200), new KeyValue(node.scaleXProperty(), scale)),
            new KeyFrame(Duration.millis(200), new KeyValue(node.scaleYProperty(), scale))
        );
        timeline.play();
    }
    /**
     * This method process user to decrease the size of canvas.
     */ 
    public void processDecreaseMapSize(){
    MMMWorkspace workspace = (MMMWorkspace)app.getWorkspaceComponent();
//        StackPane canvas = workspace.getStackPane();
	       StackPane canvas = workspace.getStackPane();
        canvas.setMinSize(canvas.getWidth()/1.1, canvas.getHeight()/1.1);
        canvas.setMaxSize(canvas.getWidth()/1.1, canvas.getHeight()/1.1);
    }
    /**
     * This method process user to increase the size of canvas.
     */ 
    public void processIncreaseMapSize(){
        MMMWorkspace workspace = (MMMWorkspace)app.getWorkspaceComponent();
//        StackPane canvas = workspace.getStackPane();
	       StackPane canvas = workspace.getStackPane();
        canvas.setMinSize(canvas.getWidth()*1.1, canvas.getHeight()*1.1);
        canvas.setMaxSize(canvas.getWidth()*1.1, canvas.getHeight()*1.1);
    }
    public void ProcessWkeypressed(){
        double move = 5.0;
        ObservableList<Node> shapes  = dataManager.getShapes();
        for(Node shape : shapes){
            if(shape instanceof DraggableText){
                ((DraggableText)shape).Wpressed(move);
            }
            if(shape instanceof DraggablePolyline){
                    ((DraggablePolyline)shape).Wpressed(move);
            }
            if(shape instanceof DraggableStation){
                ((DraggableStation)shape).Wpressed(move);
            }
            if(shape instanceof DraggableImage){
                ((DraggableImage)shape).Wpressed(move);
            }
        }
    }
    public void ProcessAkeypressed(){
        double move = 5.0;
        ObservableList<Node> shapes  = dataManager.getShapes();
        for(Node shape : shapes){
            if(shape instanceof DraggableText){
                ((DraggableText)shape).Apressed(move);
            }
            if(shape instanceof DraggablePolyline){
                    ((DraggablePolyline)shape).Apressed(move);
            }
            if(shape instanceof DraggableStation){
                ((DraggableStation)shape).Apressed(move);
            }
            if(shape instanceof DraggableImage){
                ((DraggableImage)shape).Apressed(move);
            }
        }
    }
    public void ProcessSkeypressed(){
        double move = 5.0;
        ObservableList<Node> shapes  = dataManager.getShapes();
        for(Node shape : shapes){
            if(shape instanceof DraggableText){
                ((DraggableText)shape).Spressed(move);
            }
            if(shape instanceof DraggablePolyline){
                    ((DraggablePolyline)shape).Spressed(move);
            }
            if(shape instanceof DraggableStation){
                ((DraggableStation)shape).Spressed(move);
            }
            if(shape instanceof DraggableImage){
                ((DraggableImage)shape).Spressed(move);
            }
        }
    }
    public void ProcessDkeypressed(){
        double move = 5.0;
        ObservableList<Node> shapes  = dataManager.getShapes();
        for(Node shape : shapes){
            if(shape instanceof DraggableText){
                ((DraggableText)shape).Dpressed(move);
            }
            if(shape instanceof DraggablePolyline){
                    ((DraggablePolyline)shape).Dpressed(move);
            }
            if(shape instanceof DraggableStation){
                ((DraggableStation)shape).Dpressed(move);
            }
            if(shape instanceof DraggableImage){
                ((DraggableImage)shape).Dpressed(move);
            }
        }
    }
    
    /******************************************************************************/
    
    
    
    /**
     * This method processes a user request to move the selected shape
     * down to the back layer.
     */
    public void processMoveSelectedShapeToBack() {
	dataManager.moveSelectedShapeToBack();
	app.getGUI().updateToolbarControls(false);
    }
    
    /**
     * This method processes a user request to move the selected shape
     * up to the front layer.
     */
    public void processMoveSelectedShapeToFront() {
	dataManager.moveSelectedShapeToFront();
	app.getGUI().updateToolbarControls(false);
    }

    /**
     * This method processes a user request to change the station circle radius.
     */
    public void processChanageStationCircleRadius(double value) {
	Shape shape = dataManager.getSelectedShape();
        if(shape instanceof DraggableStation){
            ((DraggableStation)shape).setRadius(value);
        }
    }
    /**
     * This method processes a user request to change the outline
     * thickness for shape drawing.
     */
    public void processSelectOutlineThickness(double value) {
        Shape shape = dataManager.getSelectedShape();
        if(shape instanceof DraggablePolyline){
            ((DraggablePolyline)shape).setStrokeWidth(value);
        }
    }
    
    /**
     * This method processes a user request to take a snapshot of the
     * current scene.
     */
    public void processSnapshot(String fileName) {
	MMMWorkspace workspace = (MMMWorkspace)app.getWorkspaceComponent();
	Pane canvas = workspace.getCanvas();
	WritableImage image = canvas.snapshot(new SnapshotParameters(), null);
	File file = new File("snapshot.png");
	try {
	    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
	}
	catch(IOException ioe) {
	    ioe.printStackTrace();
	}
    }
}
