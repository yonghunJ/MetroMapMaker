/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.transactions;

import jtps.jTPS_Transaction;
import mmm.data.DraggableStation;
import mmm.data.DraggableText;

/**
 *
 * @author jyh
 */
public class MoveText_Transaction  implements jTPS_Transaction {
    private DraggableText node;

    private double beforeX_text;
    private double beforeY_text;
    private double afterX_text;
    private double afterY_text;
    public MoveText_Transaction(DraggableText initNode,double beforeX_text,double beforeY_text,double afterX_text,double afterY_text) {

        this.node = initNode;
        this.beforeX_text =  beforeX_text;
        this.beforeY_text = beforeY_text;
        this.afterX_text = afterX_text;
        this.afterY_text = afterY_text;
    }

    @Override
    public void doTransaction() {

        ((DraggableText)node).setX(afterX_text);
        ((DraggableText)node).setY(afterY_text);
    }

    @Override
    public void undoTransaction() {

        ((DraggableText)node).setX(beforeX_text);
        ((DraggableText)node).setY(beforeY_text);
    }
    
}