package fr.utc.lo23.server.ihm_main;

import fr.utc.lo23.server.data.DataManagerServer;
import fr.utc.lo23.server.data.InterfaceServerDataFromCom;
import fr.utc.lo23.server.data.ServerDataFromCom;
import fr.utc.lo23.server.ihm_main.controllers.MainWindowController;
import fr.utc.lo23.server.ihm_main.interfaces.InterMain;
import fr.utc.lo23.server.ihm_main.interfaces.ServerWindowInterface;
import fr.utc.lo23.server.network.InterfaceServer;
import fr.utc.lo23.server.network.NetworkManagerServer;

/**
 * Created by jbmartin on 01/12/2015.
 */
public class IHMMainServerManager {

    public static ServerWindowInterface getInterMainToCom() {
        return interMainToCom;
    }

    public static InterfaceServer getInterfaceComToData() {
        return interfaceComToData;
    }

    public static InterfaceServerDataFromCom getInterfaceDataToCom() {
        return interfaceDataToCom;
    }

    public static DataManagerServer getManagerData() {
        return managerData;
    }

    public static NetworkManagerServer getManagerCom() {
        return managerCom;
    }

    /**
     * Interfaces from Main
     */
    private static ServerWindowInterface interMainToCom;

    /**
     * Interfaces from COM
     */
    private static InterfaceServer interfaceComToData;

    /**
     * Interfaces from DATA
     */
    private static InterfaceServerDataFromCom interfaceDataToCom;


    /**
     * Managers
     */
    private static DataManagerServer managerData;
    private static NetworkManagerServer managerCom;


    /**
     * MainWindowController
     */
    private static MainWindowController windowController;

    public static void setWindowController(MainWindowController windowController) {
        IHMMainServerManager.windowController = windowController;
    }

    public IHMMainServerManager() {

        managerData = new DataManagerServer();

        // TODO quand ils auront changé constructeur et ajouté setters (interfaces)
        // managerCom = new NetworkManagerServer();

        interMainToCom = new InterMain(this);

        // TODO voir précédent
//        interfaceComToData = new

        interfaceDataToCom = new ServerDataFromCom(managerData);


        // TODO quand ils auront changé le type
//        managerData.setInterfaceFromCom(interfaceDataToCom);

    }


    public void addLogLine(String msg)
    {
        windowController.addTextLog(msg);
    }
}
