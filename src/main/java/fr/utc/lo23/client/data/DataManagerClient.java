package fr.utc.lo23.client.data;

import fr.utc.lo23.client.ihm_main.interfaces.InterfaceMainToData;
import fr.utc.lo23.client.ihm_table.interfaces.ITableToDataListener;
import fr.utc.lo23.client.network.InterfaceClient;
import fr.utc.lo23.common.data.*;

import java.util.ArrayList;

/**
 * Created by Mar on 01/11/2015.
 * Class that manage the interface for data module on the client side
 */
public class DataManagerClient {
    //Interface from data module
    private InterfaceDataFromCom interFromCom;
    private InterfaceDataFromIHMTable interFromIHMTable;
    private InterfaceDataFromIHMMain interFromIHMMain;

    //Interface from other module (data call method from those interface to communicate with other module)
    private ITableToDataListener interToIHMTable;
    private InterfaceClient  interToCom;
    private InterfaceMainToData interToIHMMain;

    private User userLocal;
    private UserLightList listUsersLightLocal;
    private TableList listTablesLocal;
    private Table tableLocal;
    private ArrayList<Server> listServers;


    /**
     * Default Constructor
     */
    public DataManagerClient() {
        this.userLocal = null;
        this.listUsersLightLocal = new UserLightList();
        this.listTablesLocal = new TableList();
        this.tableLocal = null;
        this.listServers = (ArrayList<Server>) Serialization.deserializationObject(Serialization.dirLocalSavedFiles + Serialization.pathServerList);
        if(this.listServers==null){// we have this case if the file doesn't exist
            this.listServers = new ArrayList<Server>();
        }

        this.interToIHMTable = null;
        this.interToCom = null;
        this.interToIHMMain = null;

        this.interFromIHMTable = new InterfaceFromIHMTable(this);
        this.interFromIHMMain = new InterfaceFromIHMMain(this);
        this.interFromCom = new InterfaceFromCom(this);

    }

    /**
     * Get the list of server
     * @return ArrayList<Server>
     */
    public ArrayList<Server> getListServers() {
        return listServers;
    }

    /**
     * Set the list of server
     * @param listServers ArrayList<Server>
     */
    public void setListServers(ArrayList<Server> listServers) {
        this.listServers = listServers;
    }

    /**
     * Get the Interface that is proposed by Data to Com
     * @return InterfaceDataFromCom
     */
    public InterfaceDataFromCom getInterFromCom() {
        return interFromCom;
    }

    /**
     * Get the Interface that is proposed by Data to IHMTable
     * @return InterfaceDataFromIHMTable
     */
    public InterfaceDataFromIHMTable getInterFromIHMTable() {
        return interFromIHMTable;
    }

    /**
     * Get the Interface that is proposed by Data to IHMMain
     * @return InterfaceDataFromIHMMain
     */
    public InterfaceDataFromIHMMain getInterFromIHMMain() {
        return interFromIHMMain;
    }

    /**
     * Get the Interface that is proposed by IHMTable to Data
     * @return ITableToDataListener
     */
    public ITableToDataListener getInterToIHMTable() {
        return interToIHMTable;
    }

    /**
     * Set the Interface that is proposed by IHMTable to Data
     * @param interToIHMTable ITableToDataListener
     */
    public void setInterToIHMTable(ITableToDataListener interToIHMTable) {
        this.interToIHMTable = interToIHMTable;
    }

    /**
     * Get the Interface that is proposed by Com to Data
     * @return InterfaceClient
     */
    public InterfaceClient getInterToCom() {
        return interToCom;
    }

    /**
     * Set the Interface that is proposed by Com to Data
     * @param interToCom InterfaceClient
     */
    public void setInterToCom(InterfaceClient interToCom) {
        this.interToCom = interToCom;
    }

    /**
     * Get the Interface that is proposed by IHMMain to Data
     * @return InterfaceMainToData
     */
    public InterfaceMainToData getInterToIHMMain() {return interToIHMMain;}

    /**
     * Set the Interface that is proposed by IHMMain to Data
     * @param interToIHMMain InterfaceMainToData
     */
    public void setInterToIHMMain(InterfaceMainToData interToIHMMain) {
        this.interToIHMMain = interToIHMMain;
    }

    /**
     * Get the local User
     * @return User
     */
    public User getUserLocal() {
        return userLocal;
    }

    /**
     * Set the local User
     * @param userLocal User
     */
    public void setUserLocal(User userLocal) {
        this.userLocal = userLocal;
    }

    /**
     * Get the list of UserLight that is local
     * @return UserLightList
     */
    public UserLightList getListUsersLightLocal() {return listUsersLightLocal;}

    /**
     * Set the list of UserLight that is local
     * @param listUsersLightLocal UserLightList
     */
    public void setListUsersLightLocal(UserLightList listUsersLightLocal) {this.listUsersLightLocal = listUsersLightLocal;}

    /**
     * Get the list of Table that is local
     * @return TableList
     */
    public TableList getListTablesLocal() {
        return listTablesLocal;
    }

    /**
     * Set the list of Table that is local
     * @param listTablesLocal TableList
     */
    public void setListTablesLocal(TableList listTablesLocal) {
        this.listTablesLocal = listTablesLocal;
    }

    /**
     * Get the local Table
     * @return Table local
     */
    public Table getTableLocal() {
        return tableLocal;
    }

    /**
     * Set the local Table
     * @param tableLocal Table
     */
    public void setTableLocal(Table tableLocal) {
        this.tableLocal = tableLocal;
    }
}
