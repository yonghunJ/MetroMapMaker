/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.transactions;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import jtps.jTPS_Transaction;
import mmm.data.DraggableStation;
import mmm.data.DraggableText;

/**
 *
 * @author jyh
 */
public class FontFamilyText_Transaction implements jTPS_Transaction {
    private DraggableText node;
    private String Font_Family;
    private String BeforeFont_Family;
    private String afterFont_Family;


    public FontFamilyText_Transaction(DraggableText initNode,String BeforeFont_Family,String afterFont_Family) {

        this.node = initNode;
        this.Font_Family =Font_Family;
        this.BeforeFont_Family = BeforeFont_Family;
        this.afterFont_Family = afterFont_Family;
    }

    @Override
    public void doTransaction() {
        ((DraggableText)node).setFontFamilyChange(afterFont_Family);
    }

    @Override
    public void undoTransaction() {
        ((DraggableText)node).setFontFamilyChange(BeforeFont_Family);
    }
}