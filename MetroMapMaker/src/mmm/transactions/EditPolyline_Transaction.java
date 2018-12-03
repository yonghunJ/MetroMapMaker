/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.transactions;

import javafx.scene.paint.Color;
import jtps.jTPS_Transaction;
import mmm.data.DraggablePolyline;
import mmm.gui.MMMWorkspace;

/**
 *
 * @author jyh
 */
public class EditPolyline_Transaction implements jTPS_Transaction {
    private MMMWorkspace workspace;
    private DraggablePolyline node;
    private String afterName;
    private Color afterColor;
    private String beforeName;
    private Color beforeColor;
    public EditPolyline_Transaction(MMMWorkspace workspace, DraggablePolyline initNode, String beforeName, String afterName,
            Color beforeColor, Color afterColor) {
        node = initNode;
        this.workspace = workspace;
        this.afterName = afterName;
        this.afterColor = afterColor;
        this.beforeName = beforeName;
        this.beforeColor = beforeColor;
    }

    @Override
    public void doTransaction() {
        workspace.getmMetroLine().getItems().remove(beforeName);
        workspace.getmMetroLine().getItems().add(afterName);
        node.setStroke(afterColor);
        node.getLeftText().setText(afterName);
        node.getRightText().setText(afterName);
    }

    @Override
    public void undoTransaction() {
        workspace.getmMetroLine().getItems().remove(afterName);
        workspace.getmMetroLine().getItems().add(beforeName);
        node.getLeftText().setText(beforeName);
        node.getRightText().setText(beforeName);
        node.setStroke(beforeColor);
    }
}