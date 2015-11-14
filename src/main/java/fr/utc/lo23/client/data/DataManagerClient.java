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

    public DataManagerClient(ITableToDataListener interToIHMTable, InterfaceClient  interToCom ) {

        this.userLocal = new User();
        this.listUsers = new UserLightList();
        this.listTables = new TableList();

        this.interToIHMTable = interToIHMTable;
        this.interToCom = interToCom;
        //this.interToIHMMain = interToIHMMain;


        this.interFromIHMTable = new InterfaceFromIHMTable();
        this.interFromIHMMain = new InterfaceFromIHMMain();
        this.interFromCom = new InterfaceFromCom(interFromIHMTable,interFromIHMMain, interToIHMTable, interToCom, userLocal,listUsers,listTables);

    }



}
