package fr.utc.lo23.server.network;

import fr.utc.lo23.common.data.Action;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.common.network.NotifyDisconnectionMessage;
import fr.utc.lo23.common.network.NotifyNewPlayerMessage;
import fr.utc.lo23.exceptions.network.NetworkFailureException;
import fr.utc.lo23.server.data.InterfaceServerDataFromCom;
import fr.utc.lo23.server.network.threads.PokerServer;

import java.util.ArrayList;

/**
 *
 * @author Jean-Côme
 */
public class NetworkManagerServer implements InterfaceServer {
    /* Modules instance, initiate by IHM module with setters */
    private InterfaceServerDataFromCom dataInstance; //TODO: Mettre un DataManager plutot...
    //private IhmManagerServer IhmInstance; TODO: Avoir le manager !

    /* Attributes */
    private PokerServer server;

    /* =========================================== METHODES =========================================== */
    public NetworkManagerServer(int portToListen) throws NetworkFailureException{
        server = new PokerServer(portToListen);
        server.start();
    }

    /* == GETTERS AND SETTERS == */
    public InterfaceServerDataFromCom getDataInstance() {
        return dataInstance;
    }

    public void setDataInstance(InterfaceServerDataFromCom dataInstance) {
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
        NotifyNewPlayerMessage newPMessage = new NotifyNewPlayerMessage(userDistant);
        server.sendToAll(newPMessage);
    }

    public void notifyAction(Action act) throws NetworkFailureException {

    }

    public void notifyNewTable(Table newTable) throws NetworkFailureException {

    }

    public void notifyDisconnection(User distantUser) throws NetworkFailureException {
        NotifyDisconnectionMessage NotifyD = new NotifyDisconnectionMessage(distantUser);
        server.sendToAll(NotifyD);
    }

    public void sendChatPacket() throws NetworkFailureException {

    }
}
