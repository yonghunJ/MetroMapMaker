/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.transactions;

import jtps.jTPS_Transaction;
import mmm.data.DraggableStation;
import mmm.data.DraggableText;
import mmm.data.MMMData;
import mmm.gui.MMMWorkspace;

/**
 *
 * @author jyh
 */
public class ChangeMoveLabel_Transaction implements jTPS_Transaction {
    private DraggableStation node;
    private int curruent;
    public ChangeMoveLabel_Transaction(DraggableStation initNode, int current){//double betTextx,double betTexty,double AftTextx,double AftTexty) {
        node = initNode;
        this.curruent = curruent;

    }

    @Override
    public void doTransaction() {
        node.changeMoveLabel();
    }

    @Override
    public void undoTransaction() {
        node.changeMoveLabel();
        node.changeMoveLabel();
        node.changeMoveLabel();    
    }
}