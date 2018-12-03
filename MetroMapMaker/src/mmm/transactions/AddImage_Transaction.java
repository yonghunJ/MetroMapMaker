/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.transactions;

import jtps.jTPS_Transaction;
import mmm.data.DraggableImage;
import mmm.data.DraggableText;
import mmm.data.MMMData;
import mmm.gui.MMMWorkspace;

/**
 *
 * @author jyh
 */
public class AddImage_Transaction implements jTPS_Transaction {
    private MMMWorkspace workspace;
    private MMMData data;
    private DraggableImage node;
    
    public AddImage_Transaction(MMMWorkspace workspace, MMMData initData, DraggableImage initNode) {
        data = initData;
        node = initNode;
        this.workspace = workspace;
    }

    @Override
    public void doTransaction() {
        data.addShape(node);
    }

    @Override
    public void undoTransaction() {
        data.removeShape(node);    
    }
}