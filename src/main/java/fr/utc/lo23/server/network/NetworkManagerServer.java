package fr.utc.lo23.server.network;

import fr.utc.lo23.client.data.InterfaceDataFromCom;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.Action;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.exceptions.network.NetworkFailureException;
import fr.utc.lo23.server.network.threads.PokerServer;

import java.util.ArrayList;

/**
 *
 * @author Jean-Côme
 */
public class NetworkManagerServer implements InterfaceServer {
    /* Singleton -> Il faudrait passer le constructeur en privé et faire un getInstance() */
    private static NetworkManagerServer myInstance;

    /* Modules instance, initiate by IHM module with setters */
    private InterfaceDataFromCom dataInstance; //TODO: Mettre un DataManager plutot...
    //private IhmManagerServer IhmInstance; TODO: Avoir le manager !

    /* Attributes */
    private PokerServer server;

    /* =========================================== METHODES =========================================== */
    public NetworkManagerServer() {
        server = new PokerServer(null);
        server.start();
    }

    /* == GETTERS AND SETTERS == */
    public InterfaceDataFromCom getDataInstance() {
        return dataInstance;
    }

    public void setDataInstance(InterfaceDataFromCom dataInstance) {
        this.dataInstance = dataInstance;
    }

    /* TODO: Avoir le manager
    public IhmManager getIhmInstance() {
        return IhmInstance;
    }

    public void setIhmInstance(IhmManager ihmInstance) {
        IhmInstance = ihmInstance;
    }
    */


    /* == METHODES IMPLEMENTATION == */
    public void sendTableList(ArrayList<Table> tableList) throws NetworkFailureException {

    }

    public void sendPlayers(ArrayList<UserLight> userList) throws NetworkFailureException {

    }

    public void sendLogGame() throws NetworkFailureException {

    }

    public void notifyNewPlayer(UserLight userDistant) throws NetworkFailureException {

    }

    public void notifyAction(Action act) throws NetworkFailureException {

    }

    public void notifyNewTable(Table newTable) throws NetworkFailureException {

    }

    public void notifyDisconnection(UserLight distantUser) throws NetworkFailureException {

    }

    public void sendChatPacket() throws NetworkFailureException {

    }
}
