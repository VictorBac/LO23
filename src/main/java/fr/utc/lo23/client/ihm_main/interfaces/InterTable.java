package fr.utc.lo23.client.ihm_main.interfaces;

import fr.utc.lo23.client.ihm_main.IHMMainClientManager;

/**
 * Interface for IHM-Table
 * Created by leclercvictor on 24/11/2015.
 */
public class InterTable implements InterfaceMainToTable {

    /**
     * Instance of our IHMMainClientManager
     */
    private IHMMainClientManager managerMain;

    /**
     * Constructor of InterTable
     * @param mngMain Instance of our IHMClientManager
     */
    public InterTable(IHMMainClientManager mngMain) {
        managerMain = mngMain;
    }

    /**
     * Called after the local user quits the table to show the main window
     */
    public void quitGame() {
        managerMain.getControllerMain().getMainWindowController().backFromGame();
    }
}
