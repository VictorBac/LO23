package fr.utc.lo23.server.ihm_main.interfaces;

import fr.utc.lo23.server.ihm_main.IHMMainServerManager;
import fr.utc.lo23.server.ihm_main.controllers.MainWindowController;

/**
 * Created by jbmartin on 01/12/2015.
 */
public class InterMain implements ServerWindowInterface {

    private  IHMMainServerManager managerMain;


    @Override
    public void addLogLine(String line) {
        managerMain.addLogLine(line);
    }


    public InterMain(IHMMainServerManager manager) {
        managerMain = manager;
    }
}
