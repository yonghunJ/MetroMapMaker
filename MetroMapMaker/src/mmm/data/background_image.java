/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.data;

import javafx.scene.shape.Rectangle;

/**
 *
 * @author jyh
 */
public class background_image extends Rectangle{
    String address;
    double Width;
    double Height;
    public background_image(){
        
    }
    public void setBackgroundAddress(String address){
        this.address  = address;
    }
    public String getBackgroundAddress(){
        return address;
    }
    public void setImageight(double Height){
        this.Height  = Height;
    }
    public String getImageHeight(){
        return address;
    }
    public void seImagetWidth(double Width){
        this.Width  = Width;
    }
    public String getImageWidth(){
        return address;
    }
}
