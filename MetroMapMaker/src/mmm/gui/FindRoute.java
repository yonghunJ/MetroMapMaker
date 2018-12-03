/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.gui;

import mmm.data.DraggableLine;
import mmm.data.DraggableStation;
import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * This class help an user to fine the shortest way of two stations. 
 * @author Yonghun Jeong
 * @version 1.0
 */
public class FindRoute  extends Stage{
    static FindRoute singleton = null;
    

    private  Scene scene;
    private GridPane creatNewPane;
    private Stage stage;
    
    private DraggableStation origin;
    private DraggableStation destination;
    private DraggableStation intersection;
    private ArrayList<DraggableLine> DraggableLine;
    /**
     * Constructor for initializing the FindRoute, note that this constructor
     * will fully setup the FindRoute for use.
     * 
     * @param ori original station
     * @param des destination statino
     * @param data the lines to find the way
     */
    public FindRoute(DraggableStation ori,DraggableStation des,ArrayList<DraggableLine> data){
        origin = ori;
        destination = des;
        DraggableLine = data;
        intersection = new DraggableStation();
    }
    
    /**
     * This function calculate the shortest way using algorithm.
     */
    public void routeAlgorithm(){
        
    }
    /**
     * This function calculate the total stop station from two stations.
     */
    public void calTotalStop(){
        
    }
    /**
     * This function calculate the total time station to arrived at destination station from orinal station.
     */
    public void EstimatedTime(){
        
    }
    /**
     * This function find the minimum routes that came from routeAlgorirgm fuction.
     */
    public int findMinRoute(){
        
        return 1;
    }
    /**
     * This function estimate the total time from start to destination.
     */
    public void estimateTime(){
        
    }
    /**
     * This function print the intersection information of two stations.
     */
    public void IntersectionInformation(){
        
    }
    /**
     * This function return the intersection of the line
     */
    public DraggableStation getIntersection(){
        
        return intersection;
    }
    /**
     * This function make the user interface scene.
     */
    public void init(){
        stage = new Stage();
//        initModality(Modality.WINDOW_MODAL);
//        initOwner(stage);
         

        creatNewPane = new GridPane();
        
        scene = new Scene(creatNewPane,300,300);
        
        this.setScene(scene);
        showAndWait();
    }
}
