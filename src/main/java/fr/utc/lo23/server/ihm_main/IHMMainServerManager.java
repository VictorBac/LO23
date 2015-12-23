package fr.utc.lo23.server.ihm_main;

import fr.utc.lo23.server.data.DataManagerServer;
import fr.utc.lo23.server.ihm_main.controllers.MainWindowController;
import fr.utc.lo23.server.ihm_main.interfaces.InterMain;
import fr.utc.lo23.server.network.InterfaceComToMain;
import fr.utc.lo23.server.network.NetworkManagerServer;
import javafx.application.Platform;

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

    /**
     * MainWindowController
     */
    private MainWindowController windowController;

    /**
     * Managers creation and interfaces linkage
     */
    public IHMMainServerManager() {
        managerData = new DataManagerServer();
        managerCom = new NetworkManagerServer();

        interfaceMainToCom = new InterMain(this);

        //Link des interfaces
        managerCom.setInterMain(interfaceMainToCom);
        managerCom.setDataInstance(managerData.getInterfaceFromCom());
        managerData.setInterfaceToCom(managerCom);


        interfaceComToMain = managerCom;
    }

    // Getters et Setters

    /**
     * Getter interfaceMainToCom
     * @return interfaceMainToCom
     */

    public InterMain getInterfaceMainToCom() {
        return interfaceMainToCom;
    }

    /**
     * Getter interfaceComToMain
     * @return interfaceComToMain
     */

    public InterfaceComToMain getInterfaceComToMain() {
        return interfaceComToMain;
    }

    /**
     * Getter managerCom
     * @return managerCom
     */

    public NetworkManagerServer getManagerCom() {
        return managerCom;
    }

    /**
     * Setter windowController
     * @param windowController
     */

    public void setWindowController(MainWindowController windowController) {
        this.windowController = windowController;
    }


    public void addLogLine(String msg) {
        Platform.runLater(() -> windowController.addTextLog(msg));
    }
}
