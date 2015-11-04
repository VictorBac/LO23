package fr.utc.lo23.client.data;

import fr.utc.lo23.common.data.TableList;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.common.data.UserLightList;

/**
 * Created by Mar on 01/11/2015.
 */
public class DataManagerClient {
    private InterfaceFromCom interCom;
    private InterfaceFromIHMTable interFromIHMTable;
    private InterfaceFromIHMMain interFromIHMMain;

    private User userLocal;
    private UserLightList listUsers;
    private TableList listTables;

    public DataManagerClient() {
        this.interCom = null;
        this.interFromIHMTable = null;
        this.interFromIHMMain = null;
        this.userLocal = null;
        this.listUsers = null;
        this.listTables = null;
    }

}
