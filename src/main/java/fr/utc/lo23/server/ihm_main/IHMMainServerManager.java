package fr.utc.lo23.server.ihm_main;

import fr.utc.lo23.server.data.DataManagerServer;
import fr.utc.lo23.server.ihm_main.controllers.MainWindowController;
import fr.utc.lo23.server.ihm_main.interfaces.InterMain;
import fr.utc.lo23.server.network.InterfaceComToMain;
import fr.utc.lo23.server.network.NetworkManagerServer;

/**
 * Created by jbmartin on 01/12/2015.
 */
public class IHMMainServerManager {

    // Nos interfaces
    private InterMain interfaceMainToCom;

    // Interfaces à récupérer
    private InterfaceComToMain interfaceComToMain;

    // Managers
    private DataManagerServer managerData;
    private NetworkManagerServer managerCom;


    // Getters et Setters

    public InterMain getInterfaceMainToCom() {
        return interfaceMainToCom;
    }

    public InterfaceComToMain getInterfaceComToMain() {
        return interfaceComToMain;
    }

    public NetworkManagerServer getManagerCom() {
        return managerCom;
    }

    /**
     * MainWindowController
     */
    private MainWindowController windowController;

    public void setWindowController(MainWindowController windowController) {
        this.windowController = windowController;
    }



    public IHMMainServerManager() {
        // création des managers
        managerData = new DataManagerServer();
        managerCom = new NetworkManagerServer();

        interfaceMainToCom = new InterMain(this);

        //Link des interfaces
        managerCom.setInterMain(interfaceMainToCom);


        interfaceComToMain = managerCom;
    }


    public void addLogLine(String msg)
    {
        windowController.addTextLog(msg);
    }
}
