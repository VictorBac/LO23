package fr.utc.lo23.client.data;

import fr.utc.lo23.client.ihm_main.interfaces.InterfaceMainToData;
import fr.utc.lo23.client.ihm_table.interfaces.ITableToDataListener;
import fr.utc.lo23.client.network.InterfaceClient;
import fr.utc.lo23.common.data.*;

import java.util.ArrayList;

/**
 * Created by Mar on 01/11/2015.
 */
public class DataManagerClient {
    private InterfaceDataFromCom interFromCom;
    private InterfaceDataFromIHMTable interFromIHMTable;
    private InterfaceDataFromIHMMain interFromIHMMain;

    private ITableToDataListener interToIHMTable;
    private InterfaceClient  interToCom;
    private InterfaceMainToData interToIHMMain;

    private User userLocal;
    private UserLightList listUsersLightLocal;
    private TableList listTablesLocal;

    private Table tableLocal;

    private ArrayList<Server> listServers;
    private static final String pathServers = "servers";


    public DataManagerClient() {
        this.userLocal = null;
        this.listUsersLightLocal = new UserLightList();
        this.listTablesLocal = new TableList();
        this.tableLocal = null;
        //this.listServers = (ArrayList<Server>) Serialization.deserializationObject(pathServers);

        this.interToIHMTable = null;
        this.interToCom = null;
        this.interToIHMMain = null;

        this.interFromIHMTable = new InterfaceFromIHMTable(this);
        this.interFromIHMMain = new InterfaceFromIHMMain(this);
        this.interFromCom = new InterfaceFromCom(this);

    }

    public ArrayList<Server> getListServers() {
        return listServers;
    }

    public void setListServers(ArrayList<Server> listServers) {
        this.listServers = listServers;
    }

    public InterfaceDataFromCom getInterFromCom() {
        return interFromCom;
    }

    public void setInterFromCom(InterfaceDataFromCom interFromCom) {this.interFromCom = interFromCom;}

    public InterfaceDataFromIHMTable getInterFromIHMTable() {
        return interFromIHMTable;
    }

    public void setInterFromIHMTable(InterfaceDataFromIHMTable interFromIHMTable) {this.interFromIHMTable = interFromIHMTable;}

    public InterfaceDataFromIHMMain getInterFromIHMMain() {
        return interFromIHMMain;
    }

    public void setInterFromIHMMain(InterfaceDataFromIHMMain interFromIHMMain) {
        this.interFromIHMMain = interFromIHMMain;
    }

    public ITableToDataListener getInterToIHMTable() {
        return interToIHMTable;
    }

    public void setInterToIHMTable(ITableToDataListener interToIHMTable) {
        this.interToIHMTable = interToIHMTable;
    }

    public InterfaceClient getInterToCom() {
        return interToCom;
    }

    public void setInterToCom(InterfaceClient interToCom) {
        this.interToCom = interToCom;
    }

    public InterfaceMainToData getInterToIHMMain() {return interToIHMMain;}

    public void setInterToIHMMain(InterfaceMainToData interToIHMMain) {
        this.interToIHMMain = interToIHMMain;
    }

    public User getUserLocal() {
        return userLocal;
    }

    public void setUserLocal(User userLocal) {
        this.userLocal = userLocal;
    }

    public UserLightList getListUsersLightLocal() {return listUsersLightLocal;}

    public void setListUsersLightLocal(UserLightList listUsersLightLocal) {this.listUsersLightLocal = listUsersLightLocal;}

    public TableList getListTablesLocal() {
        return listTablesLocal;
    }

    public void setListTablesLocal(TableList listTablesLocal) {
        this.listTablesLocal = listTablesLocal;
    }

    public Table getTableLocal() {
        return tableLocal;
    }

    public void setTableLocal(Table tableLocal) {
        this.tableLocal = tableLocal;
    }
}
