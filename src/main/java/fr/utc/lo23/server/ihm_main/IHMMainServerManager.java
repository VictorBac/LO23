package fr.utc.lo23.server.ihm_main;

import fr.utc.lo23.server.data.DataManagerServer;
import fr.utc.lo23.server.data.InterfaceServerDataFromCom;
import fr.utc.lo23.server.data.ServerDataFromCom;
import fr.utc.lo23.server.ihm_main.controllers.MainWindowController;
import fr.utc.lo23.server.ihm_main.interfaces.InterMain;
import fr.utc.lo23.server.ihm_main.interfaces.ServerWindowInterface;
import fr.utc.lo23.server.network.InterfaceComToMain;
import fr.utc.lo23.server.network.InterfaceServer;
import fr.utc.lo23.server.network.NetworkManagerServer;

/**
 * Created by jbmartin on 01/12/2015.
 */
public class IHMMainServerManager {

    // Nos interfaces
    private InterMain interMain;

    // A quoi sert cette interface ??
    // private ServerWindowInterface interMainToCom;


    // Interfaces à récupérer
    private InterfaceComToMain interfaceComToMain;

    // Managers
    private DataManagerServer managerData;
    private NetworkManagerServer managerCom;


    // Getters et Setters

    public InterMain getInterMain() {
        return interMain;
    }

    public void setInterMain(InterMain interMain) {
        this.interMain = interMain;
    }

    public InterfaceComToMain getInterfaceComToMain() {
        return interfaceComToMain;
    }

    public void setInterfaceComToMain(InterfaceComToMain interfaceComToMain) {
        this.interfaceComToMain = interfaceComToMain;
    }

    public void setManagerCom(NetworkManagerServer managerCom) {
        this.managerCom = managerCom;
    }

    public void setManagerData(DataManagerServer managerData) {
        this.managerData = managerData;
    }

    public NetworkManagerServer getManagerCom() {
        return managerCom;
    }

    public DataManagerServer getManagerData() {
        return managerData;
    }

    /**
     * MainWindowController
     */
    private MainWindowController windowController;

    public void setWindowController(MainWindowController windowController) {
        this.windowController = windowController;
    }

    public IHMMainServerManager() {
        // TODO: au prochain merge

        //managerData = new DataManagerServer();
        //managerCom = new NetworkManagerServer();

    }


    public void addLogLine(String msg)
    {
        windowController.addTextLog(msg);
    }
}
