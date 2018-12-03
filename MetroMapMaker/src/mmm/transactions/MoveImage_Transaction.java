/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.transactions;

import jtps.jTPS_Transaction;
import mmm.data.DraggableImage;

/**
 *
 * @author jyh
 */
public class MoveImage_Transaction  implements jTPS_Transaction {
    private DraggableImage node;
    private double newX;
    private double newY;
    private double oldX;
    private double oldY;
    
    public MoveImage_Transaction(DraggableImage initNode, double initNewX, double initNewY, double initOldX, double initOldY) {
        node = initNode;
        newX = initNewX;
        newY = initNewY;
        oldX = initOldX;
        oldY = initOldY;
    }
    @Override
    public void doTransaction() {
        node.setX(oldX);
        node.setY(oldY);
    }

    @Override
    public void undoTransaction() {
        node.setX(newX);
        node.setY(newY);
    }    
}