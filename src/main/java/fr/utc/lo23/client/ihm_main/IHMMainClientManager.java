package fr.utc.lo23.client.ihm_main;

import fr.utc.lo23.client.data.*;
import fr.utc.lo23.client.ihm_main.controllers.ConnectionController;
import fr.utc.lo23.client.ihm_main.controllers.MainController;
import fr.utc.lo23.client.ihm_main.interfaces.InterData;
import fr.utc.lo23.client.ihm_main.interfaces.InterTable;
import fr.utc.lo23.client.ihm_main.interfaces.InterfaceMainToData;
import fr.utc.lo23.client.ihm_main.interfaces.InterfaceMainToTable;
import fr.utc.lo23.client.ihm_table.*;
import fr.utc.lo23.client.network.InterfaceClient;
import fr.utc.lo23.client.network.NetworkManagerClient;

/**
 * Created by leclercvictor on 24/11/2015.
 */
public class IHMMainClientManager {

    public static InterfaceDataFromIHMMain getInterfaceDataToMain() {
        return interDataToMain;
    }

    public static InterfaceDataFromCom getInterfaceDataToCom() {
        return interDataToCom;
    }

    public static InterfaceDataFromIHMTable getInterfaceDataToTable() {
        return interDataToTable;
    }

    public static InterfaceMainToData getInterfaceMainToData() {
        return interMainToData;
    }

    public static InterfaceMainToTable getInterfaceMainToTable() {
        return interMainToTable;
    }

    public static InterfaceClient getInterfaceComToData() {
        return interComToData;
    }

    public static ITableToDataListener getInterTableToData() {
        return interTableToData;
    }

    public static ITableToMainListener getInterTableToMain() {
        return interTableToMain;
    }

    /**
     * Interfaces from DATA
     */
    private static InterfaceDataFromIHMMain interDataToMain;
    private static InterfaceDataFromCom interDataToCom;
    private static InterfaceDataFromIHMTable interDataToTable;

    /**
     * Interfaces from MAIN
     */
    private static InterfaceMainToData interMainToData;
    private static InterfaceMainToTable interMainToTable;

    /**
     * Interfaces from COM
     */
    private static InterfaceClient interComToData;


    /**
     * Interfaces from TABLE
     */
    private static ITableToDataListener interTableToData;
    private static ITableToMainListener interTableToMain;


    /**
     * Managers
     */
    private static DataManagerClient managerData;
    private static NetworkManagerClient managerNetwork;
    private static IHMTable managerTable;




    private static ConnectionController controllerConnection;
    private static MainController controllerMain;
    // TODO ajouter profileController


    public static ConnectionController getControllerConnection() throws NullPointerException {
        if (controllerConnection == null) {
            throw  new NullPointerException("controllerConnection is NULL");
        }
        return controllerConnection;
    }

    public static MainController getControllerMain() throws NullPointerException {
        if (controllerMain == null) {
            throw new NullPointerException("controllerMain is NULL");
        }
        return controllerMain;
    }

    public static void setControllerConnection(ConnectionController controllerConnection) {
        controllerConnection = controllerConnection;
    }

    public static void setControllerMain(MainController controllerMain) {
        controllerMain = controllerMain;
    }





    public IHMMainClientManager() {

        managerData = new DataManagerClient();
        managerNetwork = new NetworkManagerClient(managerData.getInterFromCom());
        managerTable = new IHMTable();

        interDataToMain = new InterfaceFromIHMMain(managerData);
        interDataToCom = new InterfaceFromCom(managerData);
        interDataToTable = new InterfaceFromIHMTable(managerData);

        interMainToData = new InterData(this);
        interMainToTable = new InterTable(this);

        interTableToData = new TableToDataListener(managerTable);
        interTableToMain = new TableToMainListener(managerTable);


        // TODO decommenter quand Data aura changé le type des params
//        managerData.setInterFromCom(interDataToCom);
//        managerData.setInterFromIHMMain(interDataToMain);
//        managerData.setInterFromIHMTable(interDataToTable);
        managerData.setInterToCom(interComToData);
        managerData.setInterToIHMTable(interTableToData);
        managerData.setInterToIHMMain(interMainToData);

        // TODO
//        interComToData = new
    }




}
