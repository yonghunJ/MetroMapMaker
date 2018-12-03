/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.transactions;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import jtps.jTPS_Transaction;
import mmm.data.MMMData;
import mmm.gui.MMMWorkspace;

/**
 *
 * @author jyh
 */
public class ChangeBackground_Transaction implements jTPS_Transaction {
    private MMMWorkspace workspace;
    private MMMData data;
    private Rectangle beforeRec;
    private Rectangle afterRec;
    private Image beforeImage;
    private Image afterImage;
    
    public ChangeBackground_Transaction(MMMWorkspace workspace, MMMData initData, Rectangle beforeRec, Rectangle afterRec,Image beforeImage, Image afterImage) {
        data = initData;
        this.beforeRec = beforeRec;
        this.afterRec = afterRec;
        this.beforeImage = beforeImage;
        this.afterImage = afterImage;
        this.workspace = workspace;
    }

    @Override
    public void doTransaction() {
        //data.addShape(afterRec);
        //data.setSelectedShape(afterRec);
        //data.moveSelectedShapeToBack();
        
        ((Rectangle)beforeRec).setFill(new ImagePattern(afterImage));
        ((Rectangle)beforeRec).setWidth(afterImage.getWidth());
        ((Rectangle)beforeRec).setHeight(afterImage.getHeight());
        ((Rectangle)beforeRec).setLayoutX(workspace.getCanvas().getWidth()/2 - afterRec.getWidth()/2);
        ((Rectangle)beforeRec).setLayoutY(workspace.getCanvas().getHeight()/2 - afterRec.getHeight()/2);
    }

    @Override
    public void undoTransaction() {
        //data.removeShape(beforeRec);    
        ((Rectangle)afterRec).setFill(new ImagePattern(beforeImage));
        ((Rectangle)afterRec).setWidth(beforeImage.getWidth());
        ((Rectangle)afterRec).setHeight(beforeImage.getHeight());
        ((Rectangle)afterRec).setLayoutX(workspace.getCanvas().getWidth()/2 - beforeRec.getWidth()/2);
        ((Rectangle)afterRec).setLayoutY(workspace.getCanvas().getHeight()/2 - beforeRec.getHeight()/2);
    }
}