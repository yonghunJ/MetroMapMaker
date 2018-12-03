/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.gui;

import java.io.File;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * This class show the basic information about the application. 
 * @author Yonghun Jeong
 * @version 1.0
 */
public class LearnAboutApp extends Stage{
    static LearnAboutApp singleton = null;
    

    private  Scene scene;
    private GridPane creatNewPane;
    private Stage stage;
    
    private  Label labelAppName;
    private  Label labelAppNameAns;
    private  Label labelUsedFramework;
    private  Label labelUsedFrameworkAns;
    private  Label labelDevName;
    private  Label labelDevNameAns;
    private  Label labelDevYear;
    private  Label labelDevYearAns;
    /**
     * This is the static accessor for the singleton.
     * 
     * @return The singleton LearnAboutApp manager object.
     */
    public static LearnAboutApp getSingleton() {
        if(singleton == null)
            singleton = new LearnAboutApp();
        return singleton;
    }
    /**
     * This function set basic form of the class.
     */
    public void init(){
        stage = new Stage();
//        initModality(Modality.WINDOW_MODAL);
//        initOwner(stage);
         

        labelAppName = new Label("Application Name");
        labelAppNameAns = new Label("Metro Map Maker");
        labelUsedFramework = new Label("Used Framework");
        labelUsedFrameworkAns = new Label("I don tknow");
        labelDevName = new Label("Developer Name");
        labelDevNameAns = new Label("Yonghun Jeong");
        labelDevYear = new Label("Developed Year");
        labelDevYearAns = new Label("2017");
        
        creatNewPane = new GridPane();

        creatNewPane.add(labelAppName,0,0);
        creatNewPane.add(labelAppNameAns,1,0);
        creatNewPane.add(labelUsedFramework,0,1);
        creatNewPane.add(labelUsedFrameworkAns,1,1);
        creatNewPane.add(labelDevName,0,2);
        creatNewPane.add(labelDevNameAns,1,2);
        creatNewPane.add(labelDevYear,0,3);
        creatNewPane.add(labelDevYearAns,1,3);

        
        scene = new Scene(creatNewPane,300,300);
        
        this.setScene(scene);
        showAndWait();
    }
}
