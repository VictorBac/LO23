package fr.utc.lo23.client.ihm_main;

import fr.utc.lo23.client.data.DataManagerClient;
import fr.utc.lo23.client.data.InterfaceDataFromIHMMain;
import fr.utc.lo23.client.ihm_main.controllers.ConnectionController;
import fr.utc.lo23.client.ihm_main.controllers.MainControllerClient;
import fr.utc.lo23.client.ihm_main.controllers.MainWindowController;
import fr.utc.lo23.client.ihm_main.interfaces.InterData;
import fr.utc.lo23.client.ihm_main.interfaces.InterTable;
import fr.utc.lo23.client.ihm_main.interfaces.InterfaceMainToData;
import fr.utc.lo23.client.ihm_main.interfaces.InterfaceMainToTable;
import fr.utc.lo23.client.ihm_table.IHMTable;
import fr.utc.lo23.client.ihm_table.interfaces.ITableToMainListener;
import fr.utc.lo23.client.network.NetworkManagerClient;
import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.common.data.UserLight;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leclercvictor on 24/11/2015.
 */
public class IHMMainClientManager {

    /**
     * Nos interfaces
     */
    private InterfaceMainToData interMainToData;
    private InterfaceMainToTable interMainToTable;

    /*
     * Interfacesà récupérer
     */
    private ITableToMainListener interTableToMain;
    private InterfaceDataFromIHMMain interDataToMain;

    /**
     * Managers
     */
    private DataManagerClient managerData;
    private NetworkManagerClient managerNetwork;
    private IHMTable managerTable;


    // Attributs privés

    private ConnectionController controllerConnection;
    private MainControllerClient controllerMain;

    private List<UserLight> connectedUsers;
    private List<Table> tables;

    public IHMMainClientManager() {

        //Création des managers
        managerData = new DataManagerClient();
        managerNetwork = new NetworkManagerClient(managerData.getInterFromCom());
        managerTable = new IHMTable();

        //Création de nos interfaces
        interMainToData = new InterData(this);
        interMainToTable = new InterTable(this);

        //Link des interfaces de Data
        managerData.setInterToCom(managerNetwork);
        managerData.setInterToIHMTable(managerTable.getTableToDataListener());
        managerData.setInterToIHMMain(interMainToData);

        //Link des interfaces de Table
        managerTable.setDataInterface(managerData.getInterFromIHMTable());
        managerTable.setMainInterface(this.getInterMainToTable());

        interTableToMain = managerTable.getTableToMainListener();
        interDataToMain = managerData.getInterFromIHMMain();

    }

    public List<UserLight> getConnectedUsers() {
        if (connectedUsers == null) {
            connectedUsers = new ArrayList<>();
        }
        return connectedUsers;
    }

    public void setConnectedUsers(List<UserLight> connectedUser) {
        this.connectedUsers = connectedUser;
        updateMainWindowConnectedUsersList();
    }

    public void addConnectedUser(UserLight u) {
        if (!this.connectedUsers.contains(u)) {
            this.connectedUsers.add(u);
        }
        updateMainWindowConnectedUsersList();
    }


    public void removeConnectedUser(UserLight u) {
        this.connectedUsers.remove(u);
        updateMainWindowConnectedUsersList();
    }

    public List<Table> getTables() {
        if (tables == null) {
            tables = new ArrayList<>();
        }
        return tables;
    }

    public void setTables(List<Table> listTables) {
        this.tables = listTables;
        updateMainWindowTableList();
    }

    public void addTable(Table table) {
        if (!tables.contains(table)) {
            tables.add(table);
        }
        updateMainWindowTableList();
    }

    public void removeTable(Table table) {
        if (tables.contains(table)) {
            tables.remove(table);
        }
        updateMainWindowTableList();
    }
    //Getters et setters de nos interfaces

    public InterfaceMainToData getInterMainToData() {
        return interMainToData;
    }

    public InterfaceMainToTable getInterMainToTable() {
        return interMainToTable;
    }


    //Getters et setters des interfaces à récupérer

    public InterfaceDataFromIHMMain getInterDataToMain() {
        return interDataToMain;
    }

    public ITableToMainListener getInterTableToMain() {
        return interTableToMain;
    }


    //Getters et setters des managers

    public IHMTable getManagerTable() {
        return managerTable;
    }

    public NetworkManagerClient getManagerNetwork() {
        return managerNetwork;
    }

    public DataManagerClient getManagerData() {
        return managerData;
    }





    //Getters et setters de nos attributs privés


    public ConnectionController getControllerConnection() throws NullPointerException {
        if (controllerConnection == null) {
            throw  new NullPointerException("controllerConnection is NULL");
        }
        return controllerConnection;
    }

    public MainControllerClient getControllerMain() throws NullPointerException {
        if (controllerMain == null) {
            throw new NullPointerException("controllerMain is NULL");
        }
        return controllerMain;
    }

    public void setControllerConnection(ConnectionController controllerConnection) {
        this.controllerConnection = controllerConnection;
    }

    public void setControllerMain(MainControllerClient controllerMain) {
        this.controllerMain = controllerMain;
    }






    private void updateMainWindowConnectedUsersList () {
        MainWindowController mainWinController = getControllerMain().getMainWindowController();
        if (mainWinController != null) {
            Platform.runLater(() -> mainWinController.setConnectedUsers(getConnectedUsers()));
        }
    }
    private void updateMainWindowTableList() {
        MainWindowController mainWinController = getControllerMain().getMainWindowController();
        if (mainWinController != null) {
            Platform.runLater(() -> mainWinController.setTables(getTables()));
        }
    }

    public void updateTable(Table tableTheUserhaveleft) {
        tables.removeIf(table -> table.getIdTable().equals(tableTheUserhaveleft.getIdTable()));
        updateMainWindowTableList();
        tables.add(tableTheUserhaveleft);
        updateMainWindowTableList();
    }

    public void updateConnectedUser(UserLight newProfileRemoteUser) {
        connectedUsers.removeIf(user -> user.getIdUser().equals(newProfileRemoteUser.getIdUser()));
        updateMainWindowConnectedUsersList();
        connectedUsers.add(newProfileRemoteUser);
        updateMainWindowConnectedUsersList();
    }
}
