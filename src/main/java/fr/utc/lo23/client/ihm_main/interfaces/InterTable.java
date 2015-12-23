package fr.utc.lo23.client.ihm_main.interfaces;

import fr.utc.lo23.client.ihm_main.IHMMainClientManager;

/**
 * Created by leclercvictor on 24/11/2015.
 */
public class InterTable implements InterfaceMainToTable {

    private IHMMainClientManager managerMain;

    public InterTable(IHMMainClientManager mngMain) {
        managerMain = mngMain;
    }

    public void quitGame() {
        managerMain.getControllerMain().getMainWindowController().backFromGame();
    }
}
