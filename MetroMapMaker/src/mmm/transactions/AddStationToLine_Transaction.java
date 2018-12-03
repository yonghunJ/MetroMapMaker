/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.transactions;

import jtps.jTPS_Transaction;
import mmm.data.DraggablePolyline;
import mmm.data.DraggableStation;
import mmm.data.MMMData;
import mmm.gui.MMMWorkspace;

/**
 *
 * @author jyh
 */
public class AddStationToLine_Transaction implements jTPS_Transaction {
 
    private MMMData data;
    private DraggablePolyline polyline;
    private DraggableStation station;
    private double bfStationX;
    private double bfStationY;
    private double afStationX;
    private double afStationY;
    public AddStationToLine_Transaction(MMMData initData, DraggablePolyline polyline, DraggableStation station,double bfStationX,double bfStationY,double afStationX,double afStationY) {
        data = initData;
        this.polyline = polyline;
        this.station = station;
        this.bfStationX = bfStationX;
        this.bfStationY = bfStationY;
        this.afStationX = afStationX;
        this.afStationY = afStationY;
        }

    @Override
    public void doTransaction() {
            station.addPolyline(polyline);
            station.startx((int)afStationX, (int)afStationY);
            polyline.addStation2(station,(int)afStationX, (int)afStationY);
    }

    @Override
    public void undoTransaction() {
            station.removePolyline(polyline);
            station.startx((int)bfStationX, (int)bfStationY);
            polyline.removeStationFromtheLine(station);
            
            
            //polyline.addStations(station,(int)afStationX, (int)afStationY);
    }
}