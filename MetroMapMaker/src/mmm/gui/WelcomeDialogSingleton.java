/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.gui;

import djf.components.AppDataComponent;
import djf.settings.AppStartupConstants;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import static djf.settings.AppPropertyType.WELCOMEDIALOG_ICON;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


/**
 * This class help an user to select the current work or open the new job before starting application. 
 * @author Yonghun Jeong
 * @version 1.0
 */
public class WelcomeDialogSingleton extends Stage{
    private Stage stage;
    private  Scene scene;
    private File filePath;
    private File[] files;
    private BorderPane borderpane;
    private Hyperlink[] link;
    private Hyperlink createNewMap;
    private VBox RecentWorkVBox;
    private  Label label;
    private BorderPane creatNewPane;
    private File returnFileName;
    private String newWork;
    /**
     * Constructor for initializing the WelcomeDialogSingleton, note that this constructor
     * will fully setup the WelcomeDialogSingleton for use.
     */
    public WelcomeDialogSingleton() {

    }
    /**
     * This function set all basic form of the WelcomeDialog.
     */
    public void init(){
        newWork = "";
        stage = new Stage();
        borderpane = new BorderPane();
        label = new Label("Recent Work");
        creatNewPane = new BorderPane();
        creatNewPane.setPadding(new Insets(80,80,80,80));
        RecentWorkVBox = new VBox();
        RecentWorkVBox.setPadding(new Insets(10,80,30,80));
        createNewMap = new Hyperlink();
        scene = new Scene(borderpane,700,500);
        returnFileName = null;
        RecentWorkVBox.setAlignment(Pos.CENTER);
        RecentWorkVBox.getChildren().add(label);
        recentWork();
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String icon = WELCOMEDIALOG_ICON.toString();
        String imagePath = AppStartupConstants.FILE_PROTOCOL + AppStartupConstants.PATH_IMAGES + props.getProperty(icon);
        Image BasicImage = new Image(imagePath);
        ImageView imageView= new ImageView(BasicImage);
        
        
        initModality(Modality.WINDOW_MODAL);
        initOwner(stage);
        setTitle("Welcome to the Metro Map Maker");
               
        createNewMap.setText("Create New Metro Map");
        createNewMap.setOnAction(e->{
            setNewWork("new Work");
            close();
        });
        RecentWorkVBox.setSpacing(20);
        //creatNewPane.add(image,1,1);
        //creatNewPane.add(NewWorkButton,10,10);
        creatNewPane.setTop(imageView);//A??
        creatNewPane.setCenter(createNewMap);
        borderpane.setLeft(RecentWorkVBox);
        
        borderpane.setRight(creatNewPane);

        RecentWorkVBox.setStyle("-fx-Background-color: yellow;");
        
        this.setScene(scene);
        showAndWait();
    }
    List<File> ar = new ArrayList<File>();
    private void listFiles(String string){

        File folder = new File(string);
        File[] files = folder.listFiles();
        for(File fi : files){
            if(fi.isFile()){
                if(fi.getName().endsWith(".m3"))
                    ar.add(fi);
            }
            else if(fi.isDirectory()){
                listFiles(fi.getAbsolutePath());
            }
        }   
    }
    /**
     * This function set the recent work list to call it.
     */
    private void recentWork(){
        filePath = new File("./work/");
        Hyperlink link[]  = new Hyperlink[6];

        files = filePath.listFiles();
        
        listFiles("./work");
        Collections.sort(ar, (f1, f2) -> {
            //return new Date(f2.lastModified()).compareTo(new Date(f1.lastModified()));
           return  Long.compare(f2.lastModified(),f1.lastModified());
        });
            
        String s[] = new String[6];
        int size = 0;
        if(ar.size()>6)
            size = 6;
        else
            size = ar.size();
            
        for(int i=0;i<size;i++){
            link[i] = new Hyperlink();
            s[i] = ar.get(i).getName();
            link[i].setText(s[i]);
            RecentWorkVBox.getChildren().add(link[i]);

            File f = ar.get(i);
            link[i].setOnAction(e->{
                setRetrun_fileName(f);
                close();    
            });
        }
    } 
    
    private void setNewWork(String newWork){
        this.newWork = newWork;
    }
    /**
     * This function return a newWork for create new map .
     * @return newWork 
     */
    public String getNewWork(){
        return newWork;
    }
    /**
     * This function return the selected recent work .
     * @return returnFileName to call it 
     */
    public File getRetrun_fileName(){
        return returnFileName;
    }
    /**
     * This function set the selected recent work.
     * @param fileName 
     */
    public void  setRetrun_fileName(File fileName){
        this.returnFileName = fileName;
    }
}
