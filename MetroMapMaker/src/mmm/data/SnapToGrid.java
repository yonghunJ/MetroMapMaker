/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.data;

import java.awt.geom.Point2D;
import static java.lang.Math.floor;

/**
 *
 * @author jyh
 */
public class SnapToGrid {

    double before_x;
    double before_y;
    int interval;
    int []valueX;
    int []valueY;
    double []locationTo;
    public SnapToGrid(int interval){
        valueX = new int[4];
        valueY = new int[4];
        locationTo = new double[4];
        this.interval = interval;
    }
    public void nearlistPostion(double x, double y){
        before_x = x;
        before_y = y;
        
        setLocation();
}   
    public void setLocation(){
        int mulValueX = (int)(before_x - before_x%interval)/interval; 
        int mulValueY = (int)(before_y - before_y%interval)/interval;
        

        valueX[0] = mulValueX * interval;
        valueY[0] = mulValueY * interval;
                
        valueX[1] = mulValueX * interval + interval;
        valueY[1] = mulValueY * interval;
        
        valueX[2] = mulValueX * interval;
        valueY[2] = mulValueY * interval + interval;
        
        valueX[3] = mulValueX * interval + interval;
        valueY[3] = mulValueY * interval + interval;
        

        girdToPointDistanceCal();
        
    }
    public void girdToPointDistanceCal(){
        for( int i=0;i<4;i++){
             locationTo[i] = Point2D.distance(before_x, before_y, valueX[i], valueY[i]);
//             System.err.println("find value   " + locationTo[i]);
        }
    }
    public int GridAppliedLocationX(){
        int index = shortestIndex();

            return valueX[index];
    }
    public int GridAppliedLocationY(){
        int index = shortestIndex();

            return valueY[index];
    }
    public int shortestIndex(){
        //CALCULATE THE DISTANCE OF EACH POINT
        int index=0;
        
        double  min = locationTo[0];
        
        for(int i=0;i<3;i++){
            if(min>locationTo[i+1]){
                min = locationTo[i+1];
                index =0;
                index = i+1;
            }
        }
//        System.err.println(index + "index??");
        return index;
    }
    
}
