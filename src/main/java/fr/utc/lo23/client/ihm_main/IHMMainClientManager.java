package fr.utc.lo23.client.ihm_main;

import fr.utc.lo23.client.data.*;
import fr.utc.lo23.client.ihm_main.controllers.ConnectionController;
import fr.utc.lo23.client.ihm_main.controllers.MainControllerClient;
import fr.utc.lo23.client.ihm_main.interfaces.InterData;
import fr.utc.lo23.client.ihm_main.interfaces.InterTable;
import fr.utc.lo23.client.ihm_main.interfaces.InterfaceMainToData;
import fr.utc.lo23.client.ihm_main.interfaces.InterfaceMainToTable;
import fr.utc.lo23.client.ihm_table.*;
import fr.utc.lo23.client.ihm_table.interfaces.ITableToMainListener;
import fr.utc.lo23.client.network.NetworkManagerClient;

/**
 * Created by leclercvictor on 24/11/2015.
 */
public class IHMMainClientManager {

    public static InterfaceMainToData getInterfaceMainToData() {
        return interMainToData;
    }

    public static InterfaceMainToTable getInterfaceMainToTable() {
        return interMainToTable;
    }

    public static InterfaceDataFromIHMMain getInterfaceDataToMain() {
        return managerData.getInterFromIHMMain();
    }

    public static ITableToMainListener getInterfaceTableToMain() {
        return managerTable.getTableToMainListener();
    }



    /**
     * Interfaces from MAIN
     */
    private static InterfaceMainToData interMainToData;
    private static InterfaceMainToTable interMainToTable;


    /**
     * Managers
     */
    private static DataManagerClient managerData;
    private static NetworkManagerClient managerNetwork;
    private static IHMTable managerTable;




    private static ConnectionController controllerConnection;
    private static MainControllerClient controllerMain;
    // TODO ajouter profileController


    public static ConnectionController getControllerConnection() throws NullPointerException {
        if (controllerConnection == null) {
            throw  new NullPointerException("controllerConnection is NULL");
        }
        return controllerConnection;
    }

    public static MainControllerClient getControllerMain() throws NullPointerException {
        if (controllerMain == null) {
            throw new NullPointerException("controllerMain is NULL");
        }
        return controllerMain;
    }

    public static void setControllerConnection(ConnectionController controllerConnection) {
        controllerConnection = controllerConnection;
    }

    public static void setControllerMain(MainControllerClient controllerMain) {
        controllerMain = controllerMain;
    }





    public IHMMainClientManager() {

        managerData = new DataManagerClient();
        managerNetwork = new NetworkManagerClient(managerData.getInterFromCom());
        managerTable = new IHMTable();

        interMainToData = new InterData(this);
        interMainToTable = new InterTable(this);


        managerData.setInterToCom(managerNetwork);
        managerData.setInterToIHMTable(managerTable.getTableToDataListener());
        managerData.setInterToIHMMain(interMainToData);

        managerTable.setDataInterface((InterfaceFromIHMTable) managerData.getInterFromIHMTable());

        // TODO setInterMainToTable
        

        // TODO
//        interComToData = new
    }




}
