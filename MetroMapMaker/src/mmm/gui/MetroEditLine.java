
package mmm.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import mmm.data.DraggablePolyline;

/**
 * This class help an user to eidt the metro line. 
 * @author Yonghun Jeong
 * @version 1.0
 */
public class MetroEditLine
{
    private TextInputDialog dialog ;
    private VBox vbox;
    private HBox hbox;
    private HBox hbox2;
    private Label label1;  
    private TextField tx;
    private Circle colorPickerCircle;
    private ColorPicker colorPicker;
    private CheckBox checkbox;
    
    /**
     * Constructor for initializing the MetroEditLine, note that this constructor
     * will fully setup the MetroEditLine for use.
     * @param line
     */
    public MetroEditLine(DraggablePolyline line) {
        
        dialog = new TextInputDialog(line.getLeftText().getText());
        vbox = new VBox();
        hbox = new HBox();
        hbox2 = new HBox();
        label1 = new Label("Name: ");
        tx = new TextField();
        colorPickerCircle = new Circle();
        colorPicker = new ColorPicker();
        checkbox = new CheckBox();
        
        
        if(line.getCircular() == true) {
                checkbox.setSelected(true);
            }else
                checkbox.setSelected(false);
        colorPickerCircle.setFill((line.getStroke()));
        

    }
    /**
     * This function set the basic formation of the metroEditLine dialog.
     */
    public void init(){
       dialog.setTitle("Text Input Dialog");
       dialog.setContentText("Please enter your name:");
       colorPicker.setShape(colorPickerCircle);
       colorPickerCircle.setFill(Color.YELLOW);
       String text =tx.getText();
       System.err.println(text);
       hbox.getChildren().addAll(label1,tx);
       hbox2.getChildren().addAll(colorPicker,checkbox);
       vbox.getChildren().addAll(hbox,hbox2);
       dialog.getDialogPane().setContent(vbox);
       
       
    }
    /**
     * This function return TextInputDialog to get the result values.
     * @return dialog
     */
    public TextInputDialog getDialog(){
        return dialog;
    }
    /**
     * This function access the text of the line.
     * @param string text of the line
     */
    public void setTextLine(String string){
        tx.setText(string);
    }
    /**
     * This function get the text of the line.
     * @return tx.getText() 
     */
    public String getTextLine(){
        return tx.getText();
    }
    /**
     * This function set the color of the line.
     * @param cp return the colorPicker to set the color of the line
     */
    public void setColorPickerLine(ColorPicker cp){
        colorPicker =cp;
    }
    /**
     * This function get the color of the line.
     * @return colorPicker.getVaule() get the current color of the line
     */
    public Color getColorPickerLine(){
        return colorPicker.getValue();
    }

    /**
     * This function get the text of the line.
     * @return tx.getText() 
     */
    public boolean getCheckboxLine(){
        if(checkbox.isSelected())
            return true;
        else
            return false;
    }
}