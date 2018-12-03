/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package djf.ui;

import djf.AppTemplate;
import jtps.jTPS;

/**
 *
 * @author jyh
 */
public class UndoController {
    private AppTemplate app;
    
    public UndoController(AppTemplate initApp) {
        app = initApp;
    }
    
    public void processUndoRequest() {
        jTPS tps = app.getTPS();
        tps.undoTransaction();
        app.getGUI().updateToolbarControls(false);
    }
    
    public void processRedoRequest() {
        jTPS tps = app.getTPS();
        tps.doTransaction();
        app.getGUI().updateToolbarControls(false);
    }    
}
