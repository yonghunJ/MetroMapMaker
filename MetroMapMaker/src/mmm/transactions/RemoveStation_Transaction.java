/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.transactions;

import jtps.jTPS_Transaction;
import mmm.data.DraggableStation;
import mmm.data.MMMData;
import mmm.gui.MMMWorkspace;

/**
 *
 * @author jyh
 */
public class RemoveStation_Transaction implements jTPS_Transaction {
    private MMMWorkspace workspace;
    private MMMData data;
    private DraggableStation node;
    
    public RemoveStation_Transaction(MMMWorkspace workspace, MMMData initData, DraggableStation initNode) {
        data = initData;
        node = initNode;
        this.workspace = workspace;
    }

    @Override
    public void doTransaction() {
        data.removeShape(node);    
        data.removeShape(node.getStationName()); 
        workspace.getmMetroStation().getItems().remove(node.getStationName().getText());
        workspace.getStartStation().getItems().remove(node.getStationName().getText());
        workspace.getArrivalStation().getItems().remove(node.getStationName().getText());
        
    }

    @Override
    public void undoTransaction() {
        data.addShape(node);
        data.addShape(node.getStationName());
        workspace.getmMetroStation().getItems().add(node.getStationName().getText());
        workspace.getStartStation().getItems().add(node.getStationName().getText());
        workspace.getArrivalStation().getItems().add(node.getStationName().getText());
    }
}