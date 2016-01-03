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
 * Manager of IHM-Main
 * Created by leclercvictor on 24/11/2015.
 */
public class IHMMainClientManager {

    /**
     * Interfaces
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

    private MainControllerClient controllerMain;

    /**
     * List of UserLight which contains the connected users
     */
    private List<UserLight> connectedUsers;
    /**
     * List of Table which contains the current tables
     */
    private List<Table> tables;

    /**
     * Default constructor
     */
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

    /**
     * Getter for connected users list
     * @return the list of connected users
     */
    public List<UserLight> getConnectedUsers() {
        if (connectedUsers == null) {
            connectedUsers = new ArrayList<>();
        }
        return connectedUsers;
    }

    /**
     * Setter for connected users list and refresh ListViews
     * @param connectedUser the list of connected users
     */
    public void setConnectedUsers(List<UserLight> connectedUser) {
        this.connectedUsers = connectedUser;
        updateMainWindowConnectedUsersList();
    }

    /**
     * Add a connected user to the list of connected users and refresh ListViews
     * @param u UserLight of the user we want to add
     */
    public void addConnectedUser(UserLight u) {
        if (!this.connectedUsers.contains(u)) {
            this.connectedUsers.add(u);
        }
        updateMainWindowConnectedUsersList();
    }

    /**
     * Remove a connected user from the list of connected users and refresh ListViews
     * @param u UserLight of the user we want to remove
     */
    public void removeConnectedUser(UserLight u) {
        this.connectedUsers.remove(u);
        updateMainWindowConnectedUsersList();
    }

    /**
     * Getter for current tables list
     * @return List of Table of current tables
     */
    public List<Table> getTables() {
        if (tables == null) {
            tables = new ArrayList<>();
        }
        return tables;
    }

    /**
     * Setter for current tables list and refresh ListViews.
     * @param listTables List of Table of current tables
     */
    public void setTables(List<Table> listTables) {
        this.tables = listTables;
        updateMainWindowTableList();
    }

    /**
     * Add a table to the current tables list and refresh ListViews.
     * @param table Table we want to add
     */
    public void addTable(Table table) {
        if (!tables.contains(table)) {
            tables.add(table);
        }
        updateMainWindowTableList();
    }

    /**
     * Remove a table from the current tables list and refresh ListViews.
     * @param table Table we want to remove
     */
    public void removeTable(Table table) {
        if (tables.contains(table)) {
            tables.remove(table);
        }
        updateMainWindowTableList();
    }

    /**
     * Getter for InterfaceMainToTable
     * @return interMainToTable
     */
    public InterfaceMainToTable getInterMainToTable() {
        return interMainToTable;
    }


    //Getters et setters des interfaces à récupérer

    /**
     * Getter for InterfaceDataFromIHMMain
     * @return interDataToMain
     */
    public InterfaceDataFromIHMMain getInterDataToMain() {
        return interDataToMain;
    }

    /**
     * Getter for ITableToMainListener
     * @return interTableToMain
     */
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

    /**
     * Getter for the MainControllerClient instance
     * @return MainControllerClient instance
     * @throws NullPointerException when the MainControllerClient is null
     */
    public MainControllerClient getControllerMain() throws NullPointerException {
        if (controllerMain == null) {
            throw new NullPointerException("controllerMain is NULL");
        }
        return controllerMain;
    }

    /**
     * Setter for the MainControllerClient
     * @param controllerMain MainControllerClient instance
     */
    public void setControllerMain(MainControllerClient controllerMain) {
        this.controllerMain = controllerMain;
    }


    /**
     * Update ListView of connected users on the main window if needed
     */
    private void updateMainWindowConnectedUsersList () {
        MainWindowController mainWinController = getControllerMain().getMainWindowController();
        if (mainWinController != null) {
            Platform.runLater(() -> mainWinController.setConnectedUsers(getConnectedUsers()));
        }
    }

    /**
     * Update TableView of current tables on the main window if needed
     */
    private void updateMainWindowTableList() {
        MainWindowController mainWinController = getControllerMain().getMainWindowController();
        if (mainWinController != null) {
            Platform.runLater(() -> mainWinController.setTables(getTables()));
        }
    }

    /**
     * Update a specific table in the List of current tables and refresh TableView
     * @param tableTheUserhaveleft Table we want to update
     */
    public void updateTable(Table tableTheUserhaveleft) {
        tables.removeIf(table -> table.getIdTable().equals(tableTheUserhaveleft.getIdTable()));
        updateMainWindowTableList();
        tables.add(tableTheUserhaveleft);
        updateMainWindowTableList();
    }

    /**
     * Update a specific remote user in the List of connected users and refresh Listview
     * @param newProfileRemoteUser UserLight of the user we want to update
     */
    public void updateConnectedUser(UserLight newProfileRemoteUser) {
        connectedUsers.removeIf(user -> user.getIdUser().equals(newProfileRemoteUser.getIdUser()));
        updateMainWindowConnectedUsersList();
        connectedUsers.add(newProfileRemoteUser);
        updateMainWindowConnectedUsersList();
    }
}
