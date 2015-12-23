package fr.utc.lo23.server.ihm_main.interfaces;

import fr.utc.lo23.server.ihm_main.IHMMainServerManager;

/**
 * Created by jbmartin on 01/12/2015.
 */
public class InterMain implements ServerWindowInterface {

    private  IHMMainServerManager managerMain;

    public InterMain(IHMMainServerManager manager) {
        managerMain = manager;
    }

    public void addLogLine(String line) {
        managerMain.addLogLine(line);
    }
}
