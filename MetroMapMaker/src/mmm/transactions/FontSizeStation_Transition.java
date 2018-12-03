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
public class FontSizeStation_Transition implements jTPS_Transaction {
    private DraggableStation node;
    private double BeforeFont_size;
    private double afterFont_size;


    public FontSizeStation_Transition(DraggableStation initNode,double BeforeFont_size,double afterFont_size) {

        this.node = initNode;
        this.BeforeFont_size = BeforeFont_size;
        this.afterFont_size = afterFont_size;
    }

    @Override
    public void doTransaction() {
        ((DraggableStation)node).getStationName().setFontFontsizeChange(afterFont_size);
    }

    @Override
    public void undoTransaction() {
        ((DraggableStation)node).getStationName().setFontFontsizeChange(BeforeFont_size);
    }
}