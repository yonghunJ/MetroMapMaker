/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.transactions;

import jtps.jTPS_Transaction;
import mmm.data.DraggableText;
import mmm.data.MMMData;
import mmm.gui.MMMWorkspace;

/**
 *
 * @author jyh
 */
public class RemoveLabel_Transaction  implements jTPS_Transaction {
    private MMMWorkspace workspace;
    private MMMData data;
    private DraggableText node;
    
    public RemoveLabel_Transaction(MMMWorkspace workspace, MMMData initData, DraggableText initNode) {
        data = initData;
        node = initNode;
        this.workspace = workspace;
    }

    @Override
    public void doTransaction() {
        data.removeShape(node);
    }

    @Override
    public void undoTransaction() {
        data.addShape(node);  
    }
}