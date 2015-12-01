package fr.utc.lo23.server.data;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.TableList;
import fr.utc.lo23.common.data.UserList;
import fr.utc.lo23.server.network.InterfaceServer;

/**
 * Created by R�my on 17/11/2015.
 */
public class DataManagerServer {

    private static final String TAG = "DataManagerServer";
    private InterfaceServer interfaceToCom;
    private ServerDataFromCom interfaceFromCom;
    private UserList users;
    private TableList tables;

    public DataManagerServer(){
    this.interfaceToCom = null;
        this.interfaceFromCom = new ServerDataFromCom(this);
        users = new UserList();
        tables = new TableList();
        Console.log(TAG + "\tObject created.");
    }
    public UserList getUsers(){
        return this.users;
    }
    public TableList getTables(){
        return this.tables;
    }
    public InterfaceServer getInterfaceToCom(){
        return interfaceToCom;
    }

    public void setInterfaceFromCom(ServerDataFromCom server){
        this.interfaceFromCom = server;
    }

    public ServerDataFromCom getInterfaceFromCom() {
        return interfaceFromCom;
    }
}
