/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.transactions;

import jtps.jTPS_Transaction;
import mmm.data.DraggableStation;

/**
 *
 * @author jyh
 */
public class MoveStation_Transaction implements jTPS_Transaction {
    private DraggableStation node;
    private double beforeX;
    private double beforeY;
    private double afterX;
    private double afterY;
    private double station_text_x;
    private double station_text_y;
    private double beforeX_text;
    private double beforeY_text;
    private double afterX_text;
    private double afterY_text;
    public MoveStation_Transaction(DraggableStation initNode,double beforeX, double beforeY, double afterX, double afterY,double beforeX_text,double beforeY_text,double afterX_text,double afterY_text) {

        this.node = initNode;
        this.beforeX = beforeX;
        this.beforeY = beforeY;
        this.afterX = afterX;
        this.afterY = afterY;
        this.station_text_x = station_text_x;
        this.station_text_y = station_text_y;
        this.beforeX_text =  beforeX_text;
        this.beforeY_text = beforeY_text;
        this.afterX_text = afterX_text;
        this.afterY_text = afterY_text;
    }

    @Override
    public void doTransaction() {
        ((DraggableStation)node).setCenterX(afterX);
        ((DraggableStation)node).setCenterY(afterY);
        ((DraggableStation)node).getStationName().setX(afterX_text);
        ((DraggableStation)node).getStationName().setY(afterY_text);
    }

    @Override
    public void undoTransaction() {
        ((DraggableStation)node).setCenterX(beforeX);
        ((DraggableStation)node).setCenterY(beforeY);
        ((DraggableStation)node).getStationName().setX(beforeX_text);
        ((DraggableStation)node).getStationName().setY(beforeY_text);
    }
}