/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.gui;

import java.util.Optional;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * This class help an user to make an new line with color and name. 
 * If the object of this class was made, it open the TextDialog and let a user to
 * input the name and color of the line.
 * @author Yonghun Jeong
 * @version 1.0
 */
public class AddLineDialog{
    private TextInputDialog dialog ;
    private VBox vbox;
    private HBox hbox;
    private Label label1;  
    private TextField tx;
    private Circle colorPickerCircle;
    private ColorPicker colorPicker;
    
    /**
     * Constructor for initializing the AddLineDialog, note that this constructor
     * will fully setup the AddLineDialog for use.
     */
    public AddLineDialog(){
        dialog = new TextInputDialog("walter");
        vbox = new VBox();
        hbox = new HBox();
        label1 = new Label("Name: ");
        tx = new TextField();
        colorPickerCircle = new Circle();
        colorPicker = new ColorPicker();
    }
    /**
     * This function set all infromation for making a new line.
     */
    public void init(){
//        initModality(Modality.WINDOW_MODAL);
//        initOwner(stage);
         

        dialog.setTitle("Text Input Dialog");
        dialog.setContentText("Please enter your name:");
        colorPicker.setShape(colorPickerCircle);
         colorPickerCircle.setFill(Color.YELLOW);
        String text =tx.getText();
        System.err.println(text);
        hbox.getChildren().addAll(label1,tx);
        vbox.getChildren().addAll(hbox,colorPicker);
        
        dialog.getDialogPane().setContent(vbox);
        
        
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
     * @return tx.getText() return the text 
     */
    public String getTextLine(){
        return tx.getText();
    }
    /**
     * This function access the colorpicker of the line.
     * @param cp set the colorpicker of the line.
     */
    public void setColorPickerLine(ColorPicker cp){
        colorPicker =cp;
    }
    
    /**
     * This function get the colorof the line.
     * @return colorPicker.getValue() get the value from the ColorPicker
     */
    public Color getColorPickerLine(){
        return colorPicker.getValue();
    }
    /**
     * This function get the TextInputdialog.
     * @return dialog
     */
    public TextInputDialog getDialog(){
        return dialog;
    }
    
    
}
