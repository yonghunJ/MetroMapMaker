package mmm.gui;

import com.sun.javafx.scene.control.skin.ColorPalette;
import com.sun.javafx.scene.control.skin.CustomColorDialog;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mmm.data.MMMData;
import mmm.data.MMMState;
import mmm.data.Graph;
import djf.ui.AppYesNoCancelDialogSingleton;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppGUI;
import djf.AppTemplate;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import static djf.settings.AppPropertyType.EXPORT_WORK_TITLE;
import static djf.settings.AppPropertyType.ADDLINE_ICON;
import static djf.settings.AppPropertyType.ADDLINE_TOOLTIP;
import static djf.settings.AppPropertyType.REMOVELINE_ICON;
import static djf.settings.AppPropertyType.REMOVELINE_TOOLTIP;
import static djf.settings.AppPropertyType.LISTSTATION_ICON;
import static djf.settings.AppPropertyType.LISTSTATION_TOOLTIP;
import static djf.settings.AppPropertyType.ADDSTATION_ICON;
import static djf.settings.AppPropertyType.ADDSTATION_TOOLTIP;
import static djf.settings.AppPropertyType.REMOVESTATION_ICON;
import static djf.settings.AppPropertyType.REMOVESTATION_TOOLTIP;
import static djf.settings.AppPropertyType.ROTATE_ICON;
import static djf.settings.AppPropertyType.ROTATE_TOOLTIP;
import static djf.settings.AppPropertyType.FINDLOCATION_ICON;
import static djf.settings.AppPropertyType.FINDLOCATION_TOOLTIP;
import static djf.settings.AppPropertyType.BOLD_ICON;
import static djf.settings.AppPropertyType.BOLD_TOOLTIP;
import static djf.settings.AppPropertyType.ITALICS_ICON;
import static djf.settings.AppPropertyType.ITALICS_TOOLTIP;
import static djf.settings.AppPropertyType.ZOOMOUT_ICON;
import static djf.settings.AppPropertyType.ZOOMOUT_TOOLTIP;
import static djf.settings.AppPropertyType.ZOOMIN_ICON;
import static djf.settings.AppPropertyType.ZOOMIN_TOOLTIP;
import static djf.settings.AppPropertyType.INCREASE_ICON;
import static djf.settings.AppPropertyType.INCREASE_TOOLTIP;
import static djf.settings.AppPropertyType.DECREASE_ICON;
import static djf.settings.AppPropertyType.DECREASE_TOOLTIP;
import static djf.settings.AppPropertyType.ADDSTATIONTOLINE_TOOLTIP;
import static djf.settings.AppPropertyType.REMOVESTATIONFROMLINE_TOOLTIP;
import static djf.settings.AppPropertyType.EXPORT_ICON;
import static djf.settings.AppPropertyType.EXPORT_TOOLTIP;
import static djf.settings.AppPropertyType.REDO_ICON;
import static djf.settings.AppPropertyType.REDO_TOOLTIP;
import static djf.settings.AppPropertyType.UNDO_ICON;
import static djf.settings.AppPropertyType.UNDO_TOOLTIP;
import static djf.settings.AppPropertyType.ABOUT_ICON;
import static djf.settings.AppPropertyType.ABOUT_TOOLTIP;
import java.awt.GraphicsEnvironment;
import static mmm.css.MMMStyle.*;
import mmm.file.MMMFiles;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import properties_manager.PropertiesManager;
/**
 * This class serves as the workspace component for this application, providing
 * the user interface controls for editing work.
 *
 * @author Yongjun Jeong
 * @version 1.0
 */
public class MMMWorkspace extends AppWorkspaceComponent {
    // HERE'S THE APP
    AppTemplate app;

    // IT KNOWS THE GUI IT IS PLACED INSIDE
    AppGUI gui;

    // HAS ALL THE CONTROLS FOR EDITING
    VBox editToolbar;
    GridPane grid;
    ScrollPane scrollPane;
    ScrollPane scrollPan;
// FIRST ROW++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    VBox row1Box;
    // FIRST ROW_1
    HBox row1Box_1;
    Label metroLineToolbar;
    ComboBox<String> metroLine;
    Pane spacer;
    StackPane EditLineButton;
    Circle circle;
    
    Text text;
    
    
    //First Row_2
    HBox row1Box_2;
    Button AddLine;
    Button RemoveLine;
    Button AddStationtToLine;
    Button RemoveStationFromLine;
    Button listAllStations;
    //First Row_3
    Slider ThicknessSlider;
     Label infoLabel;
    /******************************/

    
// SECOND ROW+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    VBox row2Box;
    // Second ROW_1
    HBox row2Box_1;
    Label metroStationsToolbar;
    ComboBox<String> metroStations;
    Pane spacer2;
    Text text2;
    Circle circle2;
    ColorPicker ChangeStationColorPicker;
    StackPane ChangeStationColor;
    // Second ROW_2
    HBox row2Box_2;
    Button AddStation;
    Button RemoveStation;
    Button Snap;
    Button moveLabel;
    Button RotationStation;
    Slider StationRadius;


// THIRD ROW++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*
    VBox row3BoxVB;
    HBox row3Box;
    ComboBox<String> StartStation;
    ComboBox<String> ArrivalStation;;
    Button FindLocation;

    // FORTH ROWW++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*
    VBox row4Box;
    HBox row4Box_hbox1;
    HBox row4Box_hbox2;
    Label DecorLabel;
    
    ColorPicker CPbackground;
    Pane spacer3;
    
    StackPane Pbg;
    Circle setBackgroundColor;
    Button SetImageBackground;
    Button AddImage;
    Button AddLabel;
    Button RemoveElment;
//    Label fillColorLabel;
//    ColorPicker fillColorPicker;
    
    // FIFTH ROWW++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*
    VBox row5Box;
    HBox row5Box_hbox1;
    HBox row5Box_hbox2;
    HBox row5Box_hbox3;
    Label FontLabel;
    
    Pane spacerText;
    ColorPicker CPbackgroundText;
    StackPane PbgText;
    Circle setBackgroundColorText;

    DropShadow shadow; 
    Button fontBoldButton;
    Button fontItalicsButton;
    ComboBox<Double> fontSizeButton;
    ComboBox<String> fontFamilyButton;
    
    // SIXTH ROWW+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*
    VBox row6Box;

    HBox row6Box_hbox1;
    HBox row6Box_hbox2;
    Label NavigationLabel;
    CheckBox ShowGrid;
    
    Button ZoomIn;
    Button Zoomout;
    Button DecreaseMapSize;
    Button IncreaseMapSize;
    //**************************************************************************
    
    
    

    Button aboutButton;
        
    // THIS IS WHERE WE'LL RENDER OUR DRAWING, NOTE THAT WE
    // CALL THIS A CANVAS, BUT IT'S REALLY JUST A Pane
    Pane canvas;
    StackPane stackPane;
    // HERE ARE THE CONTROLLERS
    CanvasController canvasController;
    MMMEditController logoEditController;    

    // HERE ARE OUR DIALOGS
    AppMessageDialogSingleton messageDialog;
    AppYesNoCancelDialogSingleton yesNoCancelDialog;
    
    // FOR DISPLAYING DEBUG STUFF
    Text debugText;
    int KEYBOARD_MOVEMENT_DELTA;
    int SnapToGrip;
    int initialCanvasX;
    int initialCanvasY;
    /**
     * Constructor for initializing the workspace, note that this constructor
     * will fully setup the workspace user interface for use.
     *
     * @param initApp The application this workspace is part of.
     *
     */
    public MMMWorkspace(AppTemplate initApp) {
	// KEEP THIS FOR LATER
	app = initApp;

	// KEEP THE GUI FOR LATER
	gui = app.getGUI();

        // LAYOUT THE APP
        initLayout();
        
        // HOOK UP THE CONTROLLERS
        initControllers();
        // AND INIT THE STYLE FOR THE WORKSPACE
        initStyle();  
     
    }
    public Slider getThicknessSlider(){
        return ThicknessSlider;
    }
    public Slider getStationRadius(){
        return StationRadius;
    }
    public ComboBox<String> getFontFamilyButton(){
        return fontFamilyButton;
    }
    public ComboBox<Double> getFontSizeButton(){
        return fontSizeButton;
    }
    public Button getFontBoldButton(){
        return fontBoldButton;
    }
    public Button getFontItalicsButton(){
        return fontItalicsButton;
    }
    public StackPane getPbg(){
        return Pbg;
    }
    public Circle getBackgroundColorText(){
        return setBackgroundColorText;
    }
    public Circle getCircle(){
        return circle;
    }
    public Circle getCircle2forStation(){
        return circle2;
    }
    /**
     * Note that this is for displaying text during development.
     * @param text
     */
    public void setDebugText(String text) {
	debugText.setText(text);
    }
    
    /**
     * This function return the canavs to manipulate.
     * @return canvas
     */
    public Pane getCanvas() {
	return canvas;
    }
    /**
     * This function return the ComboBox to add and delete the list.
     * @return metroLine
     */
    public ComboBox<String> getmMetroLine(){
        return metroLine;
    }    
    /**
     * This function return the ComboBox to add and delete the station.
     * @return metroStations
     */
    public ComboBox<String> getmMetroStation(){
        return metroStations;
    }
    /**
     * This function return the start stationComboBox to find the route.
     * @return FindLine
     */
    public ComboBox<String> getStartStation(){
        return StartStation;
    }       
    /**
     * This function return the start stationComboBox to find the route.
     * @return FindStation
     */
    public ComboBox<String> getArrivalStation(){
        return ArrivalStation;
    }    
    public StackPane getStackPane(){
        return stackPane;
    }    
    // HELPER SETUP METHOD
    private void initLayout() {
	// THIS WILL GO IN THE LEFT SIDE OF THE WORKSPACE
	editToolbar = new VBox();
	
	// ROW 1********************************************************************************************************************************
        row1Box = new VBox();
	row1Box_1 = new HBox(20);
        metroLineToolbar = new Label("Metro Lines");
        metroLine = new ComboBox<String>();
        spacer = new Pane();
        spacer2 = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMinSize(10, 1);
        EditLineButton = new StackPane();
        circle = new Circle(15);
        Text text = new Text("okay");
	EditLineButton.getChildren().addAll(circle,text);
        

        circle.setFill(Color.YELLOW);
        
        
        
        /************************************/
        row1Box_2 = new HBox();
        AddLine = new Button();
        RemoveLine= new Button();
        AddStationtToLine= new Button();
        RemoveStationFromLine= new Button();
        listAllStations= new Button();

        
        
        /************************************/
        
        ThicknessSlider = new Slider();
        row1Box_1.getChildren().addAll(metroLineToolbar,metroLine,spacer,EditLineButton);
        
        AddLine = gui.initChildButton(row1Box_2,ADDLINE_ICON.toString(),ADDLINE_TOOLTIP.toString(),false);
        RemoveLine = gui.initChildButton(row1Box_2,REMOVELINE_ICON.toString(),REMOVELINE_TOOLTIP.toString(),false);
        AddStationtToLine = initChildButton(row1Box_2,ADDSTATIONTOLINE_TOOLTIP.toString());
        RemoveStationFromLine = initChildButton(row1Box_2,REMOVESTATIONFROMLINE_TOOLTIP.toString());
        listAllStations = gui.initChildButton(row1Box_2,LISTSTATION_ICON.toString(),LISTSTATION_TOOLTIP.toString(),false);

        AddStationtToLine.setText("Add\nStation");
        RemoveStationFromLine.setText("Remove\nStation");
        //row1Box_1.getChildren().add(spacer);
        
        //EditLineButton = initChildButton(row1Box_1,SAVE_TOOLTIP.toString());
        
        row1Box.getChildren().addAll(row1Box_1,row1Box_2,ThicknessSlider);
        //aboutButton = gui.initChildButton(gui.getAboutToolbar(),SAVE_ICON.toString(),SAVE_TOOLTIP.toString(),true);
        

        // ROW 2********************************************************************************************************************************
	row2Box = new VBox();
        row2Box_1 = new HBox(20);
        metroStationsToolbar = new Label("metro Stations");
        metroStations = new ComboBox<String>();
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        spacer2.setMinSize(10, 1);
        ChangeStationColor = new StackPane();
        ChangeStationColorPicker = new ColorPicker(Color.BLACK);
        ChangeStationColorPicker.setMaxSize(10,10);
        circle2 = new Circle(15,Color.BLACK);
        Text text2 = new Text("okay");
        ChangeStationColor.getChildren().addAll(ChangeStationColorPicker,circle2);

        // Second ROW_2
        row2Box_2 = new HBox(8);
        
        AddStation = new Button();
        AddStation.setText("+");
        RemoveStation = new Button();
        RemoveStation.setText("-");
        Snap = new Button();
        Snap.setText("Snap\n");
        Snap.setPrefSize(45, 38);
        moveLabel = new Button();
        moveLabel.setText("Move\nLabel");
        RotationStation = new Button();
        StationRadius = new Slider();
        
        row2Box_1.getChildren().addAll(metroStationsToolbar,metroStations,spacer2,ChangeStationColor);
        
        AddStation = gui.initChildButton(row2Box_2,ADDSTATION_ICON.toString(),ADDSTATION_TOOLTIP.toString(),false);
        RemoveStation = gui.initChildButton(row2Box_2,REMOVESTATION_ICON.toString(),REMOVESTATION_TOOLTIP.toString(),false);
        row2Box_2.getChildren().addAll(Snap,moveLabel);
        RotationStation = gui.initChildButton(row2Box_2,ROTATE_ICON.toString(),ROTATE_TOOLTIP.toString(),false);
        
        row2Box.getChildren().addAll(row2Box_1,row2Box_2,StationRadius);


	// ROW 3********************************************************************************************************************************
	row3Box = new HBox();
        row3BoxVB = new VBox();
        StartStation = new ComboBox<String>();
        ArrivalStation = new ComboBox<String>();
        
	row3BoxVB.getChildren().addAll(StartStation, ArrivalStation);
        row3Box.getChildren().addAll(row3BoxVB);
        FindLocation = gui.initChildButton(row3Box,FINDLOCATION_ICON.toString(),FINDLOCATION_TOOLTIP.toString(),false);
	// ROW 4********************************************************************************************************************************
	row4Box = new VBox();
        
        row4Box_hbox1 = new HBox();
        DecorLabel = new Label("Decor");
        spacer3 = new Pane();
        HBox.setHgrow(spacer3, Priority.ALWAYS);
        spacer3.setMinSize(10, 1);
        Pbg = new StackPane();
        CPbackground = new ColorPicker(Color.RED);
        //CPbackground.setVisible(false);
        CPbackground.setMaxSize(10,10);
	setBackgroundColor = new Circle(15,Color.BLACK);
        Pbg.getChildren().addAll(CPbackground,setBackgroundColor);
        row4Box_hbox1.getChildren().addAll(DecorLabel,spacer3,Pbg);
        
        
        row4Box_hbox2 = new HBox();
        SetImageBackground = new Button();
        SetImageBackground.setText("Set Image\nBackground");
        
        AddImage = new Button();
        AddImage.setText("Add\nImage");
        AddLabel = new Button();
        AddLabel.setText("Add\nLabel");
        RemoveElment = new Button();
        RemoveElment.setText("Remove\nElemnt");
        row4Box_hbox2.getChildren().addAll(SetImageBackground,AddImage,AddLabel,RemoveElment);
        
        row4Box.getChildren().addAll(row4Box_hbox1,row4Box_hbox2);
        
	// ROW 5********************************************************************************************************************************
	row5Box = new VBox();
        
//	outlineColorLabel = new Label("Outline Color");
//	outlineColorPicker = new ColorPicker(Color.valueOf(BLACK_HEX));
//	row5Box.getChildren().add(outlineColorLabel);
//	row5Box.getChildren().add(outlineColorPicker);

        row5Box_hbox1 = new HBox();
        row5Box_hbox2 = new HBox();
        row5Box_hbox3 = new HBox();
        FontLabel = new Label("Font");
        
        spacerText = new Pane();
        HBox.setHgrow(spacerText, Priority.ALWAYS);
        spacerText.setMinSize(10, 1);
        CPbackgroundText = new ColorPicker();
        PbgText = new StackPane();
        setBackgroundColorText = new Circle(15,Color.BLACK);
        CPbackgroundText.setMaxSize(10,10);
        PbgText.getChildren().addAll(CPbackgroundText,setBackgroundColorText);
        
        row5Box_hbox1.getChildren().addAll(FontLabel,spacerText,PbgText);
        
        shadow = new DropShadow();
        fontBoldButton = new Button();
        fontItalicsButton = new Button("/");
        fontSizeButton = new ComboBox<Double> ();
        fontSizeButton.setValue(11.0);
        for(double i=0;i<40;i++){
            fontSizeButton.getItems().add(i);
        }
        fontFamilyButton = new ComboBox<String>();
        String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for ( int i = 0; i < fonts.length; i++ )
        {
            fontFamilyButton.getItems().add(fonts[i]);
        }
        
        fontBoldButton = gui.initChildButton(row5Box_hbox2,BOLD_ICON.toString(),BOLD_TOOLTIP.toString(),false);
        fontItalicsButton = gui.initChildButton(row5Box_hbox2,ITALICS_ICON.toString(),ITALICS_TOOLTIP.toString(),false);
        
        row5Box_hbox2.getChildren().addAll(fontSizeButton);
        row5Box_hbox3.getChildren().addAll(fontFamilyButton);
        
        row5Box.getChildren().addAll(row5Box_hbox1,row5Box_hbox2,row5Box_hbox3);
	// ROW 6********************************************************************************************************************************
	row6Box = new VBox();
//	outlineThicknessLabel = new Label("Outline Thickness");
//	outlineThicknessSlider = new Slider(0, 10, 1);
//	row6Box.getChildren().add(outlineThicknessLabel);
//	row6Box.getChildren().add(outlineThicknessSlider);

        row6Box_hbox1 = new HBox();  
        NavigationLabel = new Label("Navigation");
        ShowGrid = new CheckBox();
        row6Box_hbox1.getChildren().addAll(NavigationLabel,ShowGrid);
        
        row6Box_hbox2 = new HBox();
        ZoomIn = new Button(); 
        Zoomout = new Button();
        DecreaseMapSize = new Button();
        IncreaseMapSize = new Button();
        ZoomIn = gui.initChildButton(row6Box_hbox2,ZOOMIN_ICON.toString(),ZOOMIN_TOOLTIP.toString(),false);
        Zoomout = gui.initChildButton(row6Box_hbox2,ZOOMOUT_ICON.toString(),ZOOMOUT_TOOLTIP.toString(),false);
        DecreaseMapSize = gui.initChildButton(row6Box_hbox2,DECREASE_ICON.toString(),DECREASE_TOOLTIP.toString(),false);
        IncreaseMapSize = gui.initChildButton(row6Box_hbox2,INCREASE_ICON.toString(),INCREASE_TOOLTIP.toString(),false);
        
        row6Box.getChildren().addAll(row6Box_hbox1,row6Box_hbox2);
	//********************************************************************************************************************************
        
        
        
        
    
        
        

        aboutButton = gui.initChildButton(gui.getAboutToolbar(),ABOUT_ICON.toString(),ABOUT_TOOLTIP.toString(),true);

        
        

        aboutButton.setDisable(false);
    
    
    
	// NOW ORGANIZE THE EDIT TOOLBAR
	editToolbar.getChildren().add(row1Box);
	editToolbar.getChildren().add(row2Box);
	editToolbar.getChildren().add(row3Box);
	editToolbar.getChildren().add(row4Box);
	editToolbar.getChildren().add(row5Box);
	editToolbar.getChildren().add(row6Box);
        
        canvas = new Pane();
        canvas.setPrefSize(500, 500);

        canvas.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        canvas.setMinSize(500, 500);

	grid = new GridPane();
        grid.setPrefSize(500,500);
	grid.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
	stackPane = new StackPane();
        stackPane.setPrefSize(500,500);
	stackPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); 
        
        
        Background background = new Background(new BackgroundFill(Color.WHITE, null, null));
	stackPane.setBackground(background);
        
        
        grid.setGridLinesVisible(true);
        
//        stackPane.getChildren().addAll(grid,canvas);
        
        
        
        scrollPane = new ScrollPane(stackPane);
        scrollPane.setPrefSize(500, 500);
        scrollPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
//        scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
//        scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        stackPane.getChildren().addAll(canvas);
        

	
        debugText = new Text();
        canvas.getChildren().addAll(debugText);
        
        debugText.setX(100);
	debugText.setY(100);
        
        // AND MAKE SURE THE DATA MANAGER IS IN SYNCH WITH THE PANE
	MMMData data = (MMMData)app.getDataComponent();
        data.setShapes(canvas.getChildren());
        
	// AND NOW SETUP THE WORKSPACE
	workspace = new BorderPane();
	((BorderPane)workspace).setLeft(editToolbar);
	((BorderPane)workspace).setCenter(scrollPane);
        
        //stackPane.toBack();
	KEYBOARD_MOVEMENT_DELTA = 5;
    }
    
    // HELPER SETUP METHOD
    private void initControllers() {
	// MAKE THE EDIT CONTROLLER
	
        logoEditController = new MMMEditController(app);
	

        /*************************Metro line********************************************************/
        metroLine.setOnAction(e->{
            logoEditController.processComboBoxLine();
        });
        EditLineButton.setOnMouseClicked(e->{
            logoEditController.processEditLine();
        });
        AddLine.setOnAction(e->{
            logoEditController.processPolyLineToDraw();
        });
	RemoveLine.setOnAction(e->{
	    logoEditController.processRemoveSelectedShape();
	});
	AddStationtToLine.setOnAction(e->{
            logoEditController.processAddStationToLine();
        });
	RemoveStationFromLine.setOnAction(e->{
            logoEditController.processRemoveStationFromLine();
        });
        listAllStations.setOnAction(e->{
            logoEditController.processlistAllStations();
        });
        ThicknessSlider.valueProperty().addListener(e-> {
	    logoEditController.processSelectOutlineThickness(ThicknessSlider.getValue());
      });
        /*************************Metro Stations*****************************************************/
        metroStations.setOnAction(e->{
            logoEditController.processComboBoxStation();
        });
        ChangeStationColor.setOnMouseClicked(e->{
            logoEditController.processChangeStationColor();
        });
        AddStation.setOnAction(e->{
            logoEditController.processAddStation();
        });
        
        RemoveStation.setOnAction(e->{
            logoEditController.processRemoveStation();
        });
        Snap.setOnAction(e->{
            logoEditController.processSnap(SnapToGrip);
        });
        moveLabel.setOnAction(e->{
            logoEditController.processmoveLabel();
        });
        RotationStation.setOnAction(e->{
            logoEditController.processRotationStation();
        });
        StationRadius.valueProperty().addListener(e-> {
	    logoEditController.processChanageStationCircleRadius(StationRadius.getValue());
      });
        /*************************Find Stations*****************************************************/
        FindLocation.setOnAction(e->{
            logoEditController.processFindLocation(StartStation.getValue(),ArrivalStation.getValue());
        }); 
        StartStation.setOnAction(e->{
            logoEditController.processFindStartLocation();
        });
        ArrivalStation.setOnAction(e->{
            logoEditController.processFindArrivalLocation();
        });
	/*************************Decor Stations*****************************************************/
        Pbg.setOnMousePressed(e->{
            CPbackground.show();
        });
        CPbackground.setOnAction(e->{
            //System.err.println(CPbackground.getValue());
            logoEditController.processBackground(CPbackground.getValue());
            setBackgroundColor.setFill(CPbackground.getValue());
            
        });
        ChangeStationColor.setOnMousePressed(e->{
            ChangeStationColorPicker.show();
        });
        ChangeStationColorPicker.setOnAction(e->{
            logoEditController.processStationColor(ChangeStationColorPicker.getValue());
            circle2.setFill(ChangeStationColorPicker.getValue());
        });

        SetImageBackground.setOnAction(e->{
            logoEditController.processSetImageBackground();
        });
        AddImage.setOnAction(e->{
            logoEditController.processAddImage();
        });    
        AddLabel.setOnAction(e->{
            logoEditController.processAddLabel();
        });    
        RemoveElment.setOnAction(e->{
            logoEditController.processRemoveElment();
        });    
        
        /*************************Font Change***********************************/
        PbgText.setOnMousePressed(e->{
            CPbackgroundText.show();
        });
        CPbackgroundText.setOnAction(e->{
            logoEditController.processBackgroundFont(CPbackgroundText.getValue());
            setBackgroundColorText.setFill(CPbackgroundText.getValue());
        });
        fontBoldButton.setOnAction(e->{
            logoEditController.processSetFontBoldChange(fontBoldButton);            
        });
        fontItalicsButton.setOnAction(e->{
            logoEditController.processSetFontItalicsChange(fontItalicsButton);
        });
        fontSizeButton.setOnAction(e->{
                logoEditController.processSetFontSizeChange(fontSizeButton.getValue());
        });
        fontFamilyButton.setOnAction(e->{
            logoEditController.processSetFontFamilyChange(fontFamilyButton.getValue());
        });
        /*************************Zoomin Zoomout***********************************/
        ZoomIn.setOnAction(e->{
            double zoomFactor = 1.1;
            logoEditController.processZoom(stackPane,zoomFactor);
            System.err.println(stackPane.getScaleX()+"    "+stackPane.getScaleY());
            //stackPane.setMinSize(stackPane.getMinWidth()*1.1, stackPane.getMinHeight()*1.1);
        });
        Zoomout.setOnAction(e->{
            double zoomFactor = 1/1.1;
//            logoEditController.processZoom(stackPane,zoomFactor,0,0);
            logoEditController.processZoom(stackPane,zoomFactor);

        });
        DecreaseMapSize.setOnAction(e->{
            logoEditController.processDecreaseMapSize();
        });
        IncreaseMapSize.setOnAction(e->{
            logoEditController.processIncreaseMapSize();
        });
        /*************************Grid***********************************/

        ShowGrid.setOnAction(e->{

        SnapToGrip = 40;
        int columns = (int)(canvas.getWidth()/SnapToGrip);
        int rows = (int)(canvas.getHeight()/SnapToGrip);
        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();
         for(int i = 0; i < columns; i++) {
            ColumnConstraints column = new ColumnConstraints(SnapToGrip);
            grid.getColumnConstraints().add(column);                    
        }
        for(int i = 0; i < rows; i++) {
            RowConstraints row = new RowConstraints(SnapToGrip);
            grid.getRowConstraints().add(row);
        }
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                Pane pane = new Pane();
                
                pane.getStyleClass().add("grid_cell");
                if (i == 0) {
                    pane.getStyleClass().add("first-column");
                }
                if (j == 0) {
                    pane.getStyleClass().add("first_column");
                }
                grid.add(pane, i, j);
            }
        }
            
            //logoEditController.processIncreaseMapSize();
            if(ShowGrid.isSelected())
                stackPane.getChildren().add(0,grid);
            else
                stackPane.getChildren().remove(0);
        });
        
        /****************************************************************//****************************************************************/
        EventHandler<KeyEvent> keyListener = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN ||
                event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT) {
                    //your code for moving the ship
                } 
                else if(event.getCode() == KeyCode.SPACE) {
                    //your code for shooting the missile
                }
                event.consume();
            }
        };
        /****************************************************************//****************************************************************/
        
        MMMData dataManager = (MMMData)app.getDataComponent();
        BackgroundFill fill = new BackgroundFill(Color.WHITE, null, null);
	Background background = new Background(fill);
        canvas.setBackground(background);
        MMMFiles fileComponent = new MMMFiles();
        //***************aboutToolbar******************//
        aboutButton.setOnAction(e -> {
            logoEditController.ProcessLearnAbout();
        });
        // MAKE THE CANVAS CONTROLLER	
	canvasController = new CanvasController(app);
	canvas.setOnMousePressed(e->{
            if (e.isPrimaryButtonDown()) {
                canvasController.processCanvasMousePress((int)e.getX(), (int)e.getY());
            }
            if (e.isSecondaryButtonDown()) {
                canvasController.processCanvasMousePressLeft((int)e.getX(), (int)e.getY());
            }
	    
	});
	canvas.setOnMouseReleased(e->{
	    canvasController.processCanvasMouseRelease((int)e.getX(), (int)e.getY());
	});
	canvas.setOnMouseDragged(e->{
	    canvasController.processCanvasMouseDragged((int)e.getX(), (int)e.getY());
	});
        
        
        /*app.getGUI().getPrimaryScene().setOnKeyPressed((KeyEvent event) -> {

            switch (event.getCode()) {
                case W: logoEditController.ProcessWkeypressed(); break; 
//                case W: logoEditController.ProcessWkeypressed(); break; 
                case A: logoEditController.ProcessAkeypressed();  break;
                case S: logoEditController.ProcessSkeypressed();  break;
                case D: logoEditController.ProcessDkeypressed();  break;
                
            }
        });*/
        app.getGUI().getPrimaryScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent event) {
            switch (event.getCode()) {
              case S:   stackPane.setTranslateY(stackPane.getTranslateY() - KEYBOARD_MOVEMENT_DELTA); break;
              case A:   stackPane.setTranslateX(stackPane.getTranslateX() + KEYBOARD_MOVEMENT_DELTA); break;
              case W:   stackPane.setTranslateY(stackPane.getTranslateY() + KEYBOARD_MOVEMENT_DELTA); break;
              case D:   stackPane.setTranslateX(stackPane.getTranslateX() - KEYBOARD_MOVEMENT_DELTA); break;
            }
          }
        });
        /*canvas.setOnKeyReleased((KeyEvent event) -> {
            System.err.println("222");
            switch (event.getCode()) {
                case W:    System.err.println("222");//canvas.setLayoutY(canvas.getLayoutY() - KEYBOARD_MOVEMENT_DELTA); break;
            }
        });
        canvas.setOnKeyTyped((KeyEvent event) -> {
            System.err.println("333");
            switch (event.getCode()) {
                case W:    System.err.println("333");//canvas.setLayoutY(canvas.getLayoutY() - KEYBOARD_MOVEMENT_DELTA); break;
            }
        });*/
    }
    /**
     * This function set the updateAboutButton as disabled.
     * @param saved boolean form
     */
    public void updateAboutButton(boolean saved) {
        aboutButton.setDisable(false);
    }
    /**
     * This function specifies the CSS style classes for all the UI components
     * known at the time the workspace is initially constructed. Note that the
     * tag editor controls are added and removed dynamicaly as the application
     * runs so they will have their style setup separately.
     */
    public void initStyle() {
	// NOTE THAT EACH CLASS SHOULD CORRESPOND TO
	// A STYLE CLASS SPECIFIED IN THIS APPLICATION'S
	// CSS FILE
	canvas.getStyleClass().add(CLASS_RENDER_CANVAS);
	
	// COLOR PICKER STYLE

	editToolbar.getStyleClass().add(CLASS_EDIT_TOOLBAR);
	row1Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
	row2Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
	row3Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);

	row4Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
	row5Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
	row6Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
    }

    /**
     * This function reloads all the controls for editing logos
     * the workspace.
     * @param data : get mmmdata
     */
    @Override
    public void reloadWorkspace(AppDataComponent data) {
	MMMData dataManager = (MMMData)data;
	if (dataManager.isInState(MMMState.SELECTING_SHAPE) 
		|| dataManager.isInState(MMMState.DRAGGING_SHAPE)
		|| dataManager.isInState(MMMState.DRAGGING_NOTHING)) {
	    boolean shapeIsNotSelected = dataManager.getSelectedShape() == null;
	}	
    }
    /**
     * This function reset all the controls for editing logos
     * the workspace.
     */
    @Override
    public void resetWorkspace() {
        // WE ARE NOT USING THIS, THOUGH YOU MAY IF YOU LIKE
    }
    /**
     * This is a public helper method for initializing a simple button with
     * an tooltip and placing it into a toolbar.
     * 
     * @param toolbar Toolbar pane into which to place this button.
     * 
     * @param tooltip Tooltip to appear when the user mouses over the button.
     * 
     * @return button A constructed, fully initialized button placed into its appropriate
     * pane container.
     */
    public Button initChildButton(Pane toolbar, String tooltip) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
	
	
	// NOW MAKE THE BUTTON
        Button button = new Button();
        Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip));
        button.setTooltip(buttonTooltip);
	
	// PUT THE BUTTON IN THE TOOLBAR
        toolbar.getChildren().add(button);
	
	// AND RETURN THE COMPLETED BUTTON
        return button;
    }
}