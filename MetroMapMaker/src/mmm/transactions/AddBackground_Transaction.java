/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.transactions;

import javafx.scene.shape.Rectangle;
import jtps.jTPS_Transaction;
import mmm.data.MMMData;
import mmm.gui.MMMWorkspace;

/**
 *
 * @author jyh
 */
public class AddBackground_Transaction implements jTPS_Transaction {
    private MMMWorkspace workspace;
    private MMMData data;
    private Rectangle node;
    
    public AddBackground_Transaction(MMMWorkspace workspace, MMMData initData, Rectangle initNode) {
        data = initData;
        node = initNode;
        this.workspace = workspace;
    }

    @Override
    public void doTransaction() {
        data.addShape(node);
        data.setSelectedShape(node);
        data.moveSelectedShapeToBack();
    }

    @Override
    public void undoTransaction() {
        data.removeShape(node);    
    }
}