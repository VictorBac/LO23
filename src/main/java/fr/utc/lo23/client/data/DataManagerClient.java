package fr.utc.lo23.client.data;

import fr.utc.lo23.client.ihm_table.ITableToDataListener;
import fr.utc.lo23.client.network.InterfaceClient;
import fr.utc.lo23.common.data.TableList;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.common.data.UserLightList;

/**
 * Created by Mar on 01/11/2015.
 */
public class DataManagerClient {
    private InterfaceFromCom interFromCom;
    private InterfaceFromIHMTable interFromIHMTable;
    private InterfaceFromIHMMain interFromIHMMain;

    private ITableToDataListener interToIHMTable;
    private InterfaceClient  interToCom;
    //private InterfaceData interToIHMMain;

    private User userLocal;
    private UserLightList listUsers;
    private TableList listTables;

    public DataManagerClient() {

        this.userLocal = new User();
        this.listUsers = new UserLightList();
        this.listTables = new TableList();

        this.interToIHMTable = null;
        this.interToCom = null;
        //this.interToIHMMain = interToIHMMain;

        this.interFromIHMTable = new InterfaceFromIHMTable();
        this.interFromIHMMain = new InterfaceFromIHMMain();
        this.interFromCom = new InterfaceFromCom(this);

    }

    public InterfaceFromCom getInterFromCom() {
        return interFromCom;
    }

    public void setInterFromCom(InterfaceFromCom interFromCom) {
        this.interFromCom = interFromCom;
    }

    public InterfaceFromIHMTable getInterFromIHMTable() {
        return interFromIHMTable;
    }

    public void setInterFromIHMTable(InterfaceFromIHMTable interFromIHMTable) {
        this.interFromIHMTable = interFromIHMTable;
    }

    public InterfaceFromIHMMain getInterFromIHMMain() {
        return interFromIHMMain;
    }

    public void setInterFromIHMMain(InterfaceFromIHMMain interFromIHMMain) {
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

    public User getUserLocal() {
        return userLocal;
    }

    public void setUserLocal(User userLocal) {
        this.userLocal = userLocal;
    }

    public UserLightList getListUsers() {
        return listUsers;
    }

    public void setListUsers(UserLightList listUsers) {
        this.listUsers = listUsers;
    }

    public TableList getListTables() {
        return listTables;
    }

    public void setListTables(TableList listTables) {
        this.listTables = listTables;
    }
}
