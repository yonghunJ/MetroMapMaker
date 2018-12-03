/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.transactions;

import jtps.jTPS_Transaction;
import mmm.data.DraggablePolyline;
import mmm.data.MMMData;
import mmm.gui.MMMWorkspace;

/**
 *
 * @author jyh
 */
public class RemovePolyline_Transaction implements jTPS_Transaction {
    private MMMWorkspace workspace;
    private MMMData data;
    private DraggablePolyline node;
    
    public RemovePolyline_Transaction(MMMWorkspace workspace, MMMData initData, DraggablePolyline initNode) {
        data = initData;
        node = initNode;
        this.workspace = workspace;
    }

    @Override
    public void doTransaction() {
        data.removeShape(node);    
        data.removeShape(node.getLeftText());    
        data.removeShape(node.getRightText());    
        workspace.getmMetroLine().getItems().remove(node.getLeftText().getText());
    }

    @Override
    public void undoTransaction() {
        data.addShape(node);
        data.addShape(node.getLeftText());
        data.addShape(node.getRightText());
        workspace.getmMetroLine().getItems().add(node.getLeftText().getText());
    }
}