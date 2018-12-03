/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.transactions;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Shadow;
import jtps.jTPS_Transaction;
import mmm.data.DraggableText;
import mmm.data.MMMData;
import mmm.gui.MMMWorkspace;

/**
 *
 * @author jyh
 */
public class BoldText_Transaction implements jTPS_Transaction {
    private DraggableText node;
    private boolean is_bold;
    private Button button;
    private boolean is_shadow;
    private DropShadow shadow;
    public BoldText_Transaction(DraggableText initNode, boolean is_bold,Button button,boolean is_shadow) {

        this.node = initNode;
        this.is_bold = is_bold;
        this.button = button;
        this.is_shadow = is_shadow;
        shadow = new DropShadow();
    }

    @Override
    public void doTransaction() {
        node.setFontBoldChange(is_bold);
        if(is_shadow== true)
            this.button.setEffect(null);//이거
        else if(is_shadow ==false)
            this.button.setEffect(shadow);
    }

    @Override
    public void undoTransaction() {
        DropShadow shadow = new DropShadow();
        node.setFontBoldChange(!is_bold); 
        if(is_shadow== true)
            this.button.setEffect(shadow);//이거
        else if(is_shadow ==false)
            this.button.setEffect(null);

    }
}