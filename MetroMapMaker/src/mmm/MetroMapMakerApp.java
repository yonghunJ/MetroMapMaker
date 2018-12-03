package mmm;

import java.util.Locale;
import mmm.data.MMMData;
import mmm.file.MMMFiles;
import mmm.gui.MMMWorkspace;
import djf.AppTemplate;
import mmm.gui.WelcomeDialogSingleton;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;

/**
 * This class serves as the application class for our goLogoLoApp program. 
 * Note that much of its behavior is inherited from AppTemplate, as defined in
 * the Desktop Java Framework. This app starts by loading all the app-specific
 * messages like icon files and tooltips and other settings, then the full 
 * User Interface is loaded using those settings. Note that this is a 
 * JavaFX application.
 * 
 * @author Richard McKenna
 * @author Yonghun Jeong
 * @version 1.0
 */
public class MetroMapMakerApp extends AppTemplate {
     
    File file;
    /**
     * This hook method must initialize all three components in the
     * proper order ensuring proper dependencies are respected, meaning
     * all proper objects are already constructed when they are needed
     * for use, since some may need others for initialization. 
     * It also prsenet a welcomedialog box to load recent work.
     */
    @Override
    public void buildAppComponentsHook() {
        // CONSTRUCT ALL THREE COMPONENTS. NOTE THAT FOR THIS APP
        // THE WORKSPACE NEEDS THE DATA COMPONENT TO EXIST ALREADY
        // WHEN IT IS CONSTRUCTED, AND THE DATA COMPONENT NEEDS THE
        // FILE COMPONENT SO WE MUST BE CAREFUL OF THE ORDER
        fileComponent = new MMMFiles();
        dataComponent = new MMMData(this);
        workspaceComponent = new MMMWorkspace(this);
        WelcomeDialogSingleton welcome = new WelcomeDialogSingleton();
        welcome.init();
        
        file = welcome.getRetrun_fileName();
        
        
        System.err.println("file"+file);
        if(file == null){
            System.err.println("null");
        }else{
            try {
                // LOAD THE FILE INTO THE DATA
                workspaceComponent.resetWorkspace();
                dataComponent.resetData();
                
                fileComponent.loadData(this.getDataComponent(), file.getAbsolutePath(),file);
                } catch (IOException ex) {
                    Logger.getLogger(MetroMapMakerApp.class.getName()).log(Level.SEVERE, null, ex);
                }

                // MAKE SURE THE WORKSPACE IS ACTIVATED
                workspaceComponent.activateWorkspace(this.getGUI().getAppPane());

                // AND MAKE SURE THE FILE BUTTONS ARE PROPERLY ENABLED
                this.getGUI().updateToolbarControls(true);
                //this.getGUI().getWindow().setTitle(file.getName().substring(0, file.getName().length()-5));
        }
        
        if(welcome.getNewWork().equals("new Work")){
            try {
                this.getGUI().getFileController().handleNewRequest();
   
            } catch (IOException ex) {
                Logger.getLogger(MetroMapMakerApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * This is where program execution begins. Since this is a JavaFX app
     * will simply call launch, which gets JavaFX rolling, resulting in sending
     * the properly initialized Stage (i.e. window) to the start method inherited
     * from AppTemplate, defined in the Desktop Java Framework.
     */
    public static void main(String[] args) {
	Locale.setDefault(Locale.US);
	launch(args);
    }
}