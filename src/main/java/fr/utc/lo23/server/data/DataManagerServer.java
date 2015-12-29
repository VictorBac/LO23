package fr.utc.lo23.server.data;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.TableList;
import fr.utc.lo23.common.data.UserList;
import fr.utc.lo23.server.network.InterfaceServer;

/**
 * Created by Remy on 17/11/2015.
 * Class that manage the interface for data module on the server side
 */
public class DataManagerServer {

    private static final String TAG = "DataManagerServer";

    //Interface proposed by com server to data server (data module use the method from this interface to communicate with com server)
    private InterfaceServer interfaceToCom;

    //Interface proposed by data server to Com server
    private InterfaceServerDataFromCom interfaceFromCom;
    private UserList users;
    private TableList tables;

    /**
     * Default constructor
     */
    public DataManagerServer(){
        this.interfaceToCom = null;
        this.interfaceFromCom = new ServerDataFromCom(this);
        users = new UserList();
        tables = new TableList();
        Console.log(TAG + "\tObject created.");
    }

    /**
     * Get the list of User connected on the server
     * @return UserList
     */
    public UserList getUsers(){
        return this.users;
    }

    /**
     * Get the list of Table on the Server
     * @return TableList
     */
    public TableList getTables(){
        return this.tables;
    }

    /**
     * Get the Interface that is proposed by Com server to Data server
     * @return InterfaceServer
     */
    public InterfaceServer getInterfaceToCom(){
        return interfaceToCom;
    }

    /**
     * Set the Interface that is proposed by Com server to Data server
     * @param interfaceToCom InterfaceServer
     */
    public void setInterfaceToCom(InterfaceServer interfaceToCom) {
        this.interfaceToCom = interfaceToCom;
    }

    /**
     * Get the Interface that is proposed by Data server to Com server
     * @return InterfaceServerDataFromCom
     */
    public InterfaceServerDataFromCom getInterfaceFromCom() {
        return interfaceFromCom;
    }

}
