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
public class FontFamilyStation_Transaction implements jTPS_Transaction {
    private DraggableStation node;
    private String Font_Family;
    private String BeforeFont_Family;
    private String afterFont_Family;


    public FontFamilyStation_Transaction(DraggableStation initNode,String BeforeFont_Family,String afterFont_Family) {

        this.node = initNode;
        this.Font_Family =Font_Family;
        this.BeforeFont_Family = BeforeFont_Family;
        this.afterFont_Family = afterFont_Family;
    }

    @Override
    public void doTransaction() {
        node.getStationName().setFontFamilyChange(afterFont_Family);
    }

    @Override
    public void undoTransaction() {
         node.getStationName().setFontFamilyChange(BeforeFont_Family);
    }
}