package mmm.file;

import djf.AppTemplate;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import java.awt.Stroke;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javax.json.JsonObjectBuilder;
import mmm.data.MMMData;
import mmm.data.Draggable;
import mmm.data.DraggableImage;
import mmm.data.DraggablePolyline;
import mmm.data.DraggableStation;
import mmm.data.DraggableText;
import mmm.data.Graph;
import mmm.data.background_image;
import mmm.gui.MMMWorkspace;

/**
 * This class serves as the file management component for this application,
 * providing all I/O services.
 *
 * @author Richard McKenna
 * @author Yonghun Jeong
 * @version 1.0
 */
public class MMMFiles implements AppFileComponent {
    // FOR JSON LOADING
    static final String JSON_BG_COLOR = "background_color";
    
    static final String JSON_APPNAME = "appname";
    static final String JSON_LINES = "lines";
    static final String JSON_SHAPE = "shape";
    static final String JSON_NAME = "name";
    static final String JSON_CIRCULAR = "circular";
    static final String JSON_COLOR = "color";
    static final String JSON_RED = "red";
    static final String JSON_GREEN = "green";
    static final String JSON_BLUE = "blue";
    static final String JSON_ALPHA = "alpha";
    static final String STATION_NAMES = "station_names";
    static final String JSON_STATIONS = "stations";
    static final String JSON_TEXT = "texts";
    static final String JSON_ADDRESS = "address";
    static final String JSON_IMAGE = "images";
    static final String JSON_LINE = "lines";
    static final String JSON_LINE_INCLUDED_STATION = "station_names";
    static final String JSON_LINE_THICKNESS = "line_thickness";
    static final String JSON_GET_POINTS = "line_getpoints";
    static final String JSON_BACKGROUND = "background";
    static final String JSON_BACKGROUNDCOLOR = "backgroundColor";
    
    static final String JSON_LEFT_TEXT = "polyline_leftText";
    static final String JSON_RIGHT_TEXT = "polyline_rightText";
    static final String JSON_STATION_TEXT = "station_text";


    static final String TEXT_ROTATE = "text_ratation";
    static final String TEXT_FONTFAMILY = "text_font_family";
    static final String TEXT_FONTSIZE = "text_font_size";
    static final String TEXT_BOLD = "text_font_bold";
    static final String TEXT_ITALICS = "text_font_italics";
    static final String IS_BELONG = "text_is_belong";
    
    
    ArrayList<DraggableStation> stationlist = new ArrayList<>();
    ArrayList<DraggablePolyline> polylinelist = new ArrayList<>();

    static final String JSON_X = "x";
    static final String JSON_Y = "y";
    static final String JSON_WIDTH = "width";
    static final String JSON_HEIGHT = "height";
    static final String JSON_FILL_COLOR = "color";
    static final String JSON_OUTLINE_COLOR = "outline_color";
    static final String JSON_OUTLINE_THICKNESS = "outline_thickness";
    
    static final String DEFAULT_DOCTYPE_DECLARATION = "<!doctype html>\n";
    static final String DEFAULT_ATTRIBUTE_VALUE = "";
    
 
    /**
     * This method is for saving user work, which in the case of this
     * application means the data that together draws the logo.
     * 
     * @param data The data management component for this application.
     * 
     * @param filePath Path (including file name/extension) to where
     * to save the data to.
     * 
     * @throws IOException Thrown should there be an error writing 
     * out data to the file.
     */
    @Override
    public void saveData(AppDataComponent data, String filePath, String fileName, File selectedFile) throws IOException {
//        System.err.println("svae savedata called");
	MMMData dataManager = (MMMData)data;

	// NOW BUILD THE JSON OBJCTS TO SAVE
	
	ObservableList<Node> shapes = dataManager.getShapes();   

        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
	for (Node node : shapes) {

	    Shape shape = (Shape)node;
//	    Draggable draggableShape = ((Draggable)shape);     
    
            if(shape instanceof DraggablePolyline){
                shape = (DraggablePolyline)shape;
                JsonArray fillStationNameJson = makeJssonStationsObject((DraggablePolyline)shape);
                double thinknessJson = ((DraggablePolyline)shape).getStrokeWidth();
                JsonObject fillColorJson = makeJsonColorObject(((DraggablePolyline)shape).polyLineColor());
                String line_Name = ((DraggablePolyline)shape).getLeftText().getText();
                boolean line_Circular = ((DraggablePolyline)shape).getCircular();
               JsonObject PolylineLeftText = makeJsonPolylineText(((DraggablePolyline)shape).getLeftText());
               JsonObject PolylineRightText = makeJsonPolylineText(((DraggablePolyline)shape).getRightText());
                JsonArray getPoints = makeJsonPointsObject((DraggablePolyline)shape);
                    JsonObject shapeJson = Json.createObjectBuilder()
                        .add(JSON_NAME, line_Name)
                        .add(JSON_CIRCULAR, line_Circular)
                        .add(JSON_FILL_COLOR, fillColorJson)
                        .add(JSON_LINE_THICKNESS, thinknessJson)
                        .add(JSON_GET_POINTS, getPoints)
                        .add(JSON_LEFT_TEXT,PolylineLeftText)
                        .add(JSON_RIGHT_TEXT,PolylineRightText)
                        .add(JSON_LINE_INCLUDED_STATION,fillStationNameJson).build();
                arrayBuilder.add(shapeJson);
            }
        }
        
        
        
        
        
        //DraggableStation
        JsonArrayBuilder arrayBuilderforstation = Json.createArrayBuilder();
	for (Node node : shapes) {

	    Shape shape = (Shape)node;

            if((shape instanceof DraggableStation)){
                shape = (DraggableStation)shape;
                
                
                JsonObject fillColorJson = makeJsonColorObject((Color)((DraggableStation)shape).getFill());
                double x = ((DraggableStation)shape).getCenterX();
                double y = ((DraggableStation)shape).getCenterY();
                String line_Name = ((DraggableStation)shape).getStationName().getText();
               JsonObject StationText = makeJsonPolylineText(((DraggableStation)shape).getStationName());
                JsonObject shapeJson = Json.createObjectBuilder()
                        .add(JSON_NAME, line_Name)
                        .add(JSON_FILL_COLOR, fillColorJson)
                        .add(JSON_X, x)
                        .add(JSON_Y, y)
                        .add(JSON_STATION_TEXT,StationText).build();
                arrayBuilderforstation.add(shapeJson);
            }
        }
        
        
     
        
        
        //DraggableText
        JsonArrayBuilder arrayBuilderforText = Json.createArrayBuilder();
	for (Node node : shapes) {
            if(node instanceof DraggableText){
                Shape shape = (Shape)node;
                String name = ((DraggableText)shape).getText();
                double x = ((DraggableText)shape).getX();
                double y = ((DraggableText)shape).getY();
                double textRotate = ((DraggableText)shape).getRotate();
                String text_font_family = ((DraggableText)shape).getFamily();
                double text_fontsize = ((DraggableText)shape).getFontSize();
                boolean text_bold = ((DraggableText)shape).getIsBold();
                boolean text_italic = ((DraggableText)shape).getIsItalic();
                JsonObject fillColorJson = makeJsonColorObject((Color)((DraggableText)shape).getFill());
                boolean is_belong = ((DraggableText)shape).getIs_belong();


                if((shape instanceof DraggableText)){
                    if(((DraggableText)shape).getIs_belong() == false){
                        shape = (DraggableText)shape;
                       JsonObject text = makeJsonPolylineText((DraggableText)shape);
                        JsonObject shapeJson = Json.createObjectBuilder()
                                .add(JSON_NAME, name)
                                .add(JSON_X, x)
                                .add(JSON_Y, y)
                                .add(TEXT_FONTFAMILY, text_font_family)
                                .add(TEXT_FONTSIZE, text_fontsize)
                                .add(TEXT_ROTATE, textRotate)
                                .add(TEXT_BOLD, text_bold)
                                .add(TEXT_ITALICS, text_italic)
                                .add(JSON_COLOR, fillColorJson)
                                .add(IS_BELONG, is_belong)
                                .add(JSON_FILL_COLOR, fillColorJson).build();
                        arrayBuilderforText.add(shapeJson);
                    }
                }
            }
        }
        
        //DraggableImage
        JsonArrayBuilder arrayBuilderforImage = Json.createArrayBuilder();
	for (Node node : shapes) {
	    Shape shape = (Shape)node;
            if((shape instanceof DraggableImage)){
                    shape = (DraggableImage)shape;
                    double x = ((DraggableImage)shape).getX();
                    double y = ((DraggableImage)shape).getY();
                   // System.err.println(shape.get);
                    double width = ((DraggableImage)shape).getWidth();
                    double height = ((DraggableImage)shape).getHeight();
                    String imageAdress = ((DraggableImage)shape).getImageAdress();
                    JsonObject shapeJson = Json.createObjectBuilder()
                            .add(JSON_X, x)
                            .add(JSON_Y, y)
                            .add(JSON_WIDTH, width)
                            .add(JSON_HEIGHT, height)
                            .add(JSON_ADDRESS,imageAdress).build();
                    arrayBuilderforImage.add(shapeJson);
                
                }
        }
        
        
        JsonArrayBuilder arrayBuilderforBackgroundImage = Json.createArrayBuilder();
	for (Node node : shapes) {
	    Shape shape = (Shape)node;
            if((shape instanceof background_image)){
                    shape = (background_image)shape;
                    double x = ((background_image)shape).getLayoutX();
                    double y = ((background_image)shape).getLayoutY();
                   // System.err.println(shape.get);
                    double width = ((background_image)shape).getWidth();
                    double height = ((background_image)shape).getHeight();
                    String imageAdress = ((background_image)shape).getBackgroundAddress();
                    JsonObject shapeJson = Json.createObjectBuilder()
                            .add(JSON_X, x)
                            .add(JSON_Y, y)
                            .add(JSON_WIDTH, width)
                            .add(JSON_HEIGHT, height)
                            .add(JSON_ADDRESS,imageAdress).build();
                    arrayBuilderforBackgroundImage.add(shapeJson);
                
                }
        }
        //JsonObject background_image = Json.createObjectBuilder().add(JSON_BACKGROUND, "")
        //background_image
//        JsonArrayBuilder arrayBuilderforImage = Json.createArrayBuilder();        
//	for (Node node : shapes) {
//	    Shape shape = (Shape)node;
//            if((shape instanceof background_image)){
//                
//                    background_image bgImage = (background_image)shape;
//                    double x = bgImage.getLayoutX();
//                    double y = bgImage.getLayoutY();
//                    double width = bgImage.getWidth();
//                    double height = bgImage.getHeight();
//                    String imageAdress = bgImage.getBackgroundAddress();
//                    JsonObject shapeJson3= Json.createObjectBuilder()
//                            .add(JSON_X, x)
//                            .add(JSON_Y, y)
//                            .add(JSON_WIDTH, width)
//                            .add(JSON_HEIGHT, height)
//                            .add(JSON_ADDRESS,imageAdress).build();
//                  
//            }
//        }
        if(null == dataManager.getBackgroundColor())
            dataManager.setBackgroundColor(Color.WHITE);
        JsonObject BackgroundColor = makeJsonColorObject(dataManager.getBackgroundColor());

        JsonArray shapesArray = arrayBuilder.build();
        JsonArray stationsArray = arrayBuilderforstation.build();
        JsonArray TextArray = arrayBuilderforText.build();
        JsonArray ImageArray = arrayBuilderforImage.build();
        JsonArray BackgroundArray = arrayBuilderforBackgroundImage.build();
//        JsonObject Background = arrayBuilderforBackground.build();
            	JsonObject dataManagerJSO = Json.createObjectBuilder()
		.add(JSON_BG_COLOR, BackgroundColor)
		.add(JSON_NAME, fileName)
		.add(JSON_LINE, shapesArray)
		.add(JSON_STATIONS, stationsArray)
		.add(JSON_TEXT, TextArray)
		.add(JSON_IMAGE, ImageArray)
		.add(JSON_BACKGROUND, BackgroundArray).build();
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();
        
	// INIT THE WRITER
//	OutputStream os = new FileOutputStream(filePath);
//	JsonWriter jsonFileWriter = Json.createWriter(os);
//	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath+" Metro.m3");
	pw.write(prettyPrinted);
	pw.close();

    }
    
    private JsonObject makeJsonColorObject(Color color) {
	JsonObject colorJson = Json.createObjectBuilder()
		.add(JSON_RED, color.getRed())
		.add(JSON_GREEN, color.getGreen())
		.add(JSON_BLUE, color.getBlue())
		.add(JSON_ALPHA, color.getOpacity()).build();
	return colorJson;
    }
      
    /**
     * This method loads data from a JSON formatted file into the data 
     * management component and then forces the updating of the workspace
     * such that the user may edit the data.
     * 
     * @param data Data management component where we'll load the file into.
     * 
     * @param filePath Path (including file name/extension) to where
     * to load the data from.
     * 
     * @throws IOException Thrown should there be an error reading
     * in data from the file.
     */
    JsonObject json;
    @Override
    public void loadData(AppDataComponent data, String filePath,File file) throws IOException {
	// CLEAR THE OLD DATA OUT
        MMMData dataManager = (MMMData)data;
	dataManager.resetData();
        dataManager.reset_SetStationCombobox();
	// LOAD THE JSON FILE WITH ALL THE DATA
	json = loadJSONFile(filePath);
	// LOAD THE BACKGROUND COLOR
        
	Color bgColor = loadColor(json, JSON_BG_COLOR);
	dataManager.setBackgroundColor(bgColor);
	// AND NOW LOAD ALL THE SHAPES
	JsonArray jsonShapeArray = json.getJsonArray(JSON_LINES);
        if(null !=jsonShapeArray){
            for (int i = 0; i < jsonShapeArray.size(); i++) {
                JsonObject jsonShape = jsonShapeArray.getJsonObject(i);
                DraggablePolyline shape;
                shape = loadShape(jsonShape,dataManager);
                dataManager.addShape(shape);
                dataManager.load_SetLineCombobox(shape);
                
                
                dataManager.addShape(shape.getLeftText());
                dataManager.addShape(shape.getRightText());
                shape.getLeftText().setWhoseText("PLOYLINE");
                shape.getRightText().setWhoseText("PLOYLINE");
                
            }
        }
        
        //TEXT LOAD
        JsonArray jsonStationText = json.getJsonArray(JSON_TEXT);
        if(null !=jsonStationText){
            for (int i = 0; i < jsonStationText.size(); i++) {
                JsonObject jsonShape = jsonStationText.getJsonObject(i);
                DraggableText text;
                text = loadtext(jsonShape,dataManager);
                dataManager.addShape(text); 
            }
        }
        ///IMAGE LOAD
        JsonArray jsonStationImage = json.getJsonArray(JSON_IMAGE);
        if(null !=jsonStationImage){
            for (int i = 0; i < jsonStationImage.size(); i++) {
                JsonObject jsonShape = jsonStationImage.getJsonObject(i);
                DraggableImage  image;
                image = loadImage(jsonShape,dataManager);
                dataManager.addShape(image); 
            }
        }
        
        
        JsonArray jsonbackgroundimage = json.getJsonArray(JSON_BACKGROUND);
        if(null !=jsonbackgroundimage){
            for (int i = 0; i < jsonbackgroundimage.size(); i++) {
                JsonObject jsonShape = jsonbackgroundimage.getJsonObject(i);
                double x = jsonShape.getInt(JSON_X);
                double y= jsonShape.getInt(JSON_Y);
                double width = jsonShape.getInt(JSON_WIDTH);
                double height = jsonShape.getInt(JSON_HEIGHT);
                String address = jsonShape.getString(JSON_ADDRESS);
                
                background_image bg = new background_image();
                bg.setLayoutX(x);
                bg.setLayoutY(y);
                
                bg.setWidth(width);
                bg.setHeight(height);
                Image img = new Image(address);
                bg.setBackgroundAddress(address);
                bg.setFill(new ImagePattern(img));
                bg.setOpacity(0.5);
            
                dataManager.addShape(bg); 
                dataManager.setSelectedShape(bg);
                dataManager.moveSelectedShapeToBack();
            }
        
    
        //BACKGROUND LAOD
//        JsonObject jsonStationBackground = json.getJsonObject(JSON_BACKGROUND);
//        System.err.println(jsonStationBackground);
//        if(null== jsonStationBackground && !jsonStationBackground.isEmpty() ){
//            double x = jsonStationBackground.getInt(JSON_X);
//            double y= jsonStationBackground.getInt(JSON_Y);
//            double width = jsonStationBackground.getInt(JSON_WIDTH);
//            double height = jsonStationBackground.getInt(JSON_HEIGHT);
//            String address = jsonStationBackground.getString(JSON_ADDRESS);
//
//            
//            background_image background = new background_image();
//            background.setX(x);
//            background.setY(y);
//            background.setWidth(width);
//            background.setHeight(height);
//            Image img = new Image(address);
//            background.setBackgroundAddress(address);
//            background.setFill(new ImagePattern(img));
            
        }
         
        
        ObservableList<Node> shapes = dataManager.getShapes(); 
        Node shape;
//        int j=0;
//        DraggableStation station = new DraggableStation();
//        for( int i=0; i<shapes.size();i++){
//            shape = shapes.get(i);
//           if(shape instanceof DraggableStation){
//               station = (DraggableStation)shape;
//               j++;
//               station.setMatrixNum(j);//
//               
//           }
//        }
        Graph g = new Graph(100);
        
        
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
        
        
        
          //dataManager.changeStageTitle(file.getName().substring(0, file.getName().length()-5));
    }
    
    private double getDataAsDouble(JsonObject json, String dataName) {
	JsonValue value = json.get(dataName);
	JsonNumber number = (JsonNumber)value;
        if(null == number){
            return 1;
        }else
            return number.bigDecimalValue().doubleValue();	
    }
    private DraggableText loadtext(JsonObject jsonShape ,MMMData datamanager) {
        DraggableText text = new DraggableText();
        String name = jsonShape.getString(JSON_NAME);
        double x = jsonShape.getInt(JSON_X);
        double y= jsonShape.getInt(JSON_Y);
        String font_family = jsonShape.getString(TEXT_FONTFAMILY);
            double font_size = jsonShape.getInt(TEXT_FONTSIZE);
            double font_rotate = jsonShape.getInt(TEXT_ROTATE);
            boolean font_bold = jsonShape.getBoolean(TEXT_BOLD);
            boolean font_italics = jsonShape.getBoolean(TEXT_ITALICS);
            Color fillColor = loadColor(jsonShape, JSON_FILL_COLOR);
            boolean is_belong = jsonShape.getBoolean(IS_BELONG);
        
            text.setText(name);
            text.setX(x);
            text.setY(y);
            text.setFontFamilyChange(font_family);
            text.setFontSize(font_size);
            text.setRotate(font_rotate);
            text.setFontBoldChange(font_bold);
            text.setFontItalicsChange(font_italics);
            text.setFill(fillColor);
            text.setIs_belong(is_belong);
            System.err.println(text);
            
        return text;
    }
    private DraggableImage loadImage(JsonObject jsonShape ,MMMData datamanager) {
        DraggableImage Image = new DraggableImage();
        double x = jsonShape.getInt(JSON_X);
        double y= jsonShape.getInt(JSON_Y);
        double width = jsonShape.getInt(JSON_WIDTH);
        double height = jsonShape.getInt(JSON_HEIGHT);
        String address = jsonShape.getString(JSON_ADDRESS);
        
        Image.setX(x);
        Image.setY(y);
        Image.setWidth(width);
        Image.setHeight(height);
        Image img = new Image(address);
        Image.setImageAdress(address);
        Image.setFill(new ImagePattern(img));
        return Image;
    }
    private background_image loadBackground(JsonObject jsonShape ,MMMData datamanager) {
        background_image background = new background_image();
        double x = jsonShape.getInt(JSON_X);
        double y= jsonShape.getInt(JSON_Y);
        double width = jsonShape.getInt(JSON_WIDTH);
        double height = jsonShape.getInt(JSON_HEIGHT);
        String address = jsonShape.getString(JSON_ADDRESS);
        
        background.setX(x);
        background.setY(y);
        background.setWidth(width);
        background.setHeight(height);
        Image img = new Image(address);
        background.setBackgroundAddress(address);
        background.setFill(new ImagePattern(img));
        background.setOpacity(0.5);
        return background;
    }
    private DraggablePolyline loadShape(JsonObject jsonShape ,MMMData datamanager) {
	// FIRST BUILD THE PROPER SHAPE TYPE
	DraggablePolyline shape = new DraggablePolyline();
        
        String type = jsonShape.getString(JSON_NAME);
        boolean circular = jsonShape.getBoolean(JSON_CIRCULAR);
        shape.setCircular(circular);
        
        shape.setOpacity(1.0);
        
	// THEN LOAD ITS FILL AND OUTLINE PROPERTIES
	Color fillColor = loadColor(jsonShape, JSON_FILL_COLOR);
	shape.setStroke(fillColor);
        double outlineThickness = getDataAsDouble(jsonShape, JSON_LINE_THICKNESS);
        shape.setStrokeWidth(outlineThickness);
        
        JsonArray jsonGetPointsArray = jsonShape.getJsonArray(JSON_GET_POINTS);
        for(int i=0;i<jsonGetPointsArray.size();i++){
            double getpoint = jsonGetPointsArray.getInt(i);
            shape.getPoints().add(getpoint);
        }
        //for left,right text on the line
        DraggableText leftText = loadText(jsonShape, JSON_LEFT_TEXT);
        DraggableText rightText = loadText(jsonShape, JSON_RIGHT_TEXT);
        
        shape.DraggableLeftText(leftText);
        shape.DraggableRightText(rightText);
        
	// Station add for loop
        ObservableList<Node> shapes = datamanager.getShapes();
        
        JsonArray jsonShapeArray = jsonShape.getJsonArray(STATION_NAMES);
        for (int i = 0; i < jsonShapeArray.size(); i++) {

            String jsonShape1 = jsonShapeArray.getString(i);
            DraggableStation shape1 = loadShape1(jsonShape1);
            DraggableText shape1_text =  loadShape1_text(jsonShape1);///////////////////////////////////////////////////////////////////
  
            shape1_text.setWhoseText("STAION");
                shape1.setDraggbleText(shape1_text);
               shape1.addPolyline(shape);
               
               //Add station here
               datamanager.addShape((DraggableStation)shape1);
   //            System.err.println(shape1_text);
               datamanager.addShape((DraggableText)shape1_text);
               datamanager.load_SetStationCombobox(shape1);
               shape.addStation3(shape1);

               shape.setLineName(type);
               shape.getLeftText().starts(shape.getPoints().get(0),shape.getPoints().get(1));
               shape.getRightText().starts( shape.getPoints().get((shape.getPoints().size()-2)), shape.getPoints().get(shape.getPoints().size()-1));           
               shape.bindfunc();
	}
	return shape;
    }
    /**
      * To call the DraggableStation in the m3 file
      * @param stationName
      * @return 
      */
     private DraggableStation loadShape1(String stationName) {
        JsonArray jsonStationArray = json.getJsonArray(JSON_STATIONS);
        DraggableStation draggableShape =null;
        for (int i = 0; i < jsonStationArray.size(); i++) {
            JsonObject jsonShape = jsonStationArray.getJsonObject(i);
            if(jsonShape.getString(JSON_NAME).equals(stationName)){
                String string = jsonShape.getString(JSON_NAME);
                Color color = loadColor(jsonShape,JSON_FILL_COLOR);
                double x = getDataAsDouble(jsonShape, JSON_X);
                double y = getDataAsDouble(jsonShape, JSON_Y);
                DraggableText dt = loadText(jsonShape,JSON_STATION_TEXT);

                draggableShape = new DraggableStation();
                draggableShape.getStationName().setText(string);
                draggableShape.setFill(color);
                draggableShape.resetLocationAndSize(x, y, stationName );
            }
	}
                return draggableShape;
    }
     /**
      * To call the DraggableText in the Draggabl Station the m3 file
      * @param stationName
      * @return 
      */
     private DraggableText loadShape1_text(String stationName) {
        JsonArray jsonStationArray = json.getJsonArray(JSON_STATIONS);
        DraggableText dt =new DraggableText();
        for (int i = 0; i < jsonStationArray.size(); i++) {
            JsonObject jsonShape = jsonStationArray.getJsonObject(i);
            if(jsonShape.getString(JSON_NAME).equals(stationName)){
                dt = loadText(jsonShape,JSON_STATION_TEXT);

            }
	}
//         System.err.println(dt.getX()+"   " +dt.getX());
                return dt;
    }

   /**
      * To call the Color in the m3 file
      * @param stationName
      * @return 
      */
    private Color loadColor(JsonObject json, String colorToGet) {
	if(!(json.getJsonObject(colorToGet) ==null)){
            JsonObject jsonColor = json.getJsonObject(colorToGet);
            double red = getDataAsDouble(jsonColor, JSON_RED);
            double green = getDataAsDouble(jsonColor, JSON_GREEN);
            double blue = getDataAsDouble(jsonColor, JSON_BLUE);
            double alpha = getDataAsDouble(jsonColor, JSON_ALPHA);
            Color loadedColor = new Color(red, green, blue, alpha);
            return loadedColor;
        }else
            return Color.TRANSPARENT;
    }
    
   /**
      * To call the Polyline text in the m3 file
      * @param stationName
      * @return 
      */
    private DraggableText loadText(JsonObject json, String TextToGet) {
            JsonObject jsonCText = json.getJsonObject(TextToGet);
            String name = jsonCText.getString(JSON_NAME);
            double x = jsonCText.getInt(JSON_X);
            double y = jsonCText.getInt(JSON_Y);
            String font_family = jsonCText.getString(TEXT_FONTFAMILY);
            double font_size = jsonCText.getInt(TEXT_FONTSIZE);
            double font_rotate = jsonCText.getInt(TEXT_ROTATE);
            boolean font_bold = jsonCText.getBoolean(TEXT_BOLD);
            boolean font_italics = jsonCText.getBoolean(TEXT_ITALICS);
            Color fillColor = loadColor(jsonCText, JSON_FILL_COLOR);
            boolean is_belong = jsonCText.getBoolean(IS_BELONG);
        
            DraggableText text = new DraggableText();
            text.setText(name);
            text.setX(x);
            text.setY(y);
            text.setFontFamilyChange(font_family);
            text.setFontSize(font_size);
            text.setFontBoldChange(font_bold);
            text.setFontItalicsChange(font_italics);
            text.setRotate(font_rotate);
            text.setFill(fillColor);
            text.setIs_belong(is_belong);
            return text;
    }
    
//    private DraggableText loadTextforstation(JsonObject json) {;
//            String name = json.getString(JSON_NAME);
//            double x = json.getInt(JSON_X);
//            double y = json.getInt(JSON_Y);
//            String font_family = json.getString(TEXT_FONTFAMILY);
//            double font_size = json.getInt(TEXT_FONTSIZE);
//            double font_rotate = json.getInt(TEXT_ROTATE);
//            boolean font_bold = json.getBoolean(TEXT_BOLD);
//            boolean font_italics = json.getBoolean(TEXT_ITALICS);
//            Color fillColor = loadColor(json, JSON_FILL_COLOR);
//            boolean is_belong = json.getBoolean(IS_BELONG);
//        
//            DraggableText text = new DraggableText();
//            text.setX(x);
//            text.setY(y);
//            text.setFontFamilyChange(font_family);
//            text.setFontSize(font_size);
//            text.setFontBoldChange(font_bold);
//            text.setFontItalicsChange(font_italics);
//            text.setRotate(font_rotate);
//            text.setFill(fillColor);
//            text.setIs_belong(is_belong);
//            return text;
//    }

    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }
    
    /**
     * This method export data to a JSON formatted file that came from  the data 
     * management component and also export a snapshot. 
     * 
     * 
     * @param data Data management component where we'll export the data.
     * 
     * @param filePath Path (including file name/extension) to where
     * to export the data.
     * 
     * @param fileName Name (including file name/extension) to where
     * to export the data.
     * 
     * @throws IOException Thrown should there be an error reading
     * in data from the file.
     */
    @Override
    public void exportData(AppDataComponent data, String filePath, String fileName, File selectedFile) throws IOException {
	MMMData dataManager = (MMMData)data;

	// NOW BUILD THE JSON OBJCTS TO SAVE
	
	ObservableList<Node> shapes = dataManager.getShapes();   

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
	for (Node node : shapes) {

	    Shape shape = (Shape)node;
//	    Draggable draggableShape = ((Draggable)shape);     
    
            if(shape instanceof DraggablePolyline){
                shape = (DraggablePolyline)shape;
                JsonObject fillColorJson = makeJsonColorObject(Color.BLACK);
                JsonArray fillStationNameJson = makeJssonStationsObject((DraggablePolyline)shape);
                

                    fillColorJson = makeJsonColorObject(((DraggablePolyline)shape).polyLineColor());

                String line_Name = ((DraggablePolyline)shape).getLeftText().getText();
                boolean line_Circular = ((DraggablePolyline)shape).getCircular();

                if((shape instanceof DraggablePolyline)){
                    JsonObject shapeJson = Json.createObjectBuilder()
                        .add(JSON_NAME, line_Name)
                        .add(JSON_CIRCULAR, line_Circular)
                        .add(JSON_FILL_COLOR, fillColorJson)
                        .add(JSON_LINE_INCLUDED_STATION,fillStationNameJson).build();
                arrayBuilder.add(shapeJson);
                }

            }
        }
        
        
        JsonArrayBuilder arrayBuilderforstation = Json.createArrayBuilder();
	for (Node node : shapes) {

	    Shape shape = (Shape)node;

            if(shape instanceof DraggableStation){
                shape = (DraggableStation)shape;
                JsonObject fillColorJson = makeJsonColorObject(Color.BLACK);
                
                String station_Name = ((DraggableStation)shape).getStationName().getText();
                double x = ((DraggableStation)shape).getCenterX();
                double y = ((DraggableStation)shape).getCenterY();

               
                JsonObject shapeJson = Json.createObjectBuilder()
                    .add(JSON_NAME, station_Name)
                    .add(JSON_X, x)
                    .add(JSON_Y, y).build();
                
                arrayBuilderforstation.add(shapeJson);
            }
        }
        
        
        JsonArray shapesArray = arrayBuilder.build();
        JsonArray stationsArray = arrayBuilderforstation.build();
            	JsonObject dataManagerJSO = Json.createObjectBuilder()
		.add(JSON_NAME, fileName)
		.add(JSON_LINE, shapesArray)
		.add(JSON_STATIONS, stationsArray)
		.build();
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath+" Metro.json");
	pw.write(prettyPrinted);
	pw.close();
        dataManager.processSnapshot(filePath);
        
 
    }
    private JsonArray makeJssonStationsObject(DraggablePolyline line){
        ArrayList<DraggableStation> stations= line.getsortedStation();
        JsonArrayBuilder stationsArray = Json.createArrayBuilder();
        for(int i=0;i<stations.size();i++){
            if(stations.get(i) instanceof DraggableStation){
                String stationName = stations.get(i).getStationName().getText();
                stationsArray.add(stationName);
            }
        }
        return stationsArray.build();
    }
    private JsonArray makeJsonPointsObject(DraggablePolyline line){
        ObservableList getPoints= line.getPoints();
        JsonArrayBuilder PointsArray = Json.createArrayBuilder();
        for(int i=0;i<line.getPoints().size();i++){
            double value = line.getPoints().get(i);
                PointsArray.add(value);
            }
        return PointsArray.build();
    }
    
    private JsonObject makeJsonPolylineText(DraggableText text){
        String name = text.getText();
        double x = text.getX();
        double y = text.getY();
        double textRotate = text.getRotate();
        String text_font_family = text.getFamily();
        double text_fontsize = text.getFontSize();
        boolean text_bold = text.getIsBold();
        boolean text_italic = text.getIsItalic();
        JsonObject fillColorJson = makeJsonColorObject((Color)text.getFill());
        boolean is_belong = text.getIs_belong();
        
        JsonObject shapeJson = Json.createObjectBuilder()
                    .add(JSON_NAME, name)
                    .add(JSON_X, x)
                    .add(JSON_Y, y)
                    .add(TEXT_FONTFAMILY, text_font_family)
                    .add(TEXT_FONTSIZE, text_fontsize)
                    .add(TEXT_ROTATE, textRotate)
                    .add(TEXT_BOLD, text_bold)
                    .add(TEXT_ITALICS, text_italic)
                    .add(JSON_COLOR, fillColorJson)
                    .add(IS_BELONG, is_belong).build();
        return shapeJson;
    }
    /**
     * This method import data from a JSON formatted file that into the data 
     * management component. 
     * 
     * 
     * @param data Data management component where we'll import the data.
     * 
     * @param filePath Path (including file name/extension) to where
     * to import the data.
     * 
     * @throws IOException Thrown should there be an error reading
     * in data from the file.
     */
    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
	// AGAIN, WE ARE NOT USING THIS IN THIS ASSIGNMENT
    }
    
    /**
     * This method is for saving user work as a new name, which in the case of this
     * application means the data that together draws the logo.
     * 
     * @param data The data management component for this application.
     * 
     * @param filePath Path (including file name/extension) to where
     * to save the data to.
     * 
     * @throws IOException Thrown should there be an error writing 
     * out data to the file.
     */
    public void SaveAsData(AppDataComponent data, String filePath) throws IOException { //SaveAsData(AppDataComponent data, String filePath) app.getDataComponent(), selectedFile.getAbsolutePath()
        
    }
    
    
}
