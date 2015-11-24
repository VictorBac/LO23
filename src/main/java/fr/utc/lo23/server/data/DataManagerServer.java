package fr.utc.lo23.server.data;

import fr.utc.lo23.common.data.TableList;
import fr.utc.lo23.common.data.UserList;
import fr.utc.lo23.server.network.InterfaceServer;

/**
 * Created by Rémy on 17/11/2015.
 */
public class DataManagerServer {

    private InterfaceServer interfaceToCom;
    private ServerDataFromCom interfaceFromCom;
    private UserList users;
    private TableList tables;

    public DataManagerServer(){
    this.interfaceToCom = null;
        this.interfaceFromCom = new ServerDataFromCom(this);
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


}
