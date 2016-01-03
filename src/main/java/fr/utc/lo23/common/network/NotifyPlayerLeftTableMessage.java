package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.EnumerationTypeOfUser;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.network.threads.ConnectionThread;

import java.util.UUID;

/**
 * Created by Jean-Côme on 11/12/2015.
 * Notifies the other players that a player left the tables
 */
public class NotifyPlayerLeftTableMessage extends Message {

    UserLight user;
    UUID tab ;
    EnumerationTypeOfUser userType;

    public NotifyPlayerLeftTableMessage(UserLight userLocal, UUID idTable, EnumerationTypeOfUser usertype) {
        super();
        this.user = userLocal;
        this.tab = idTable;
        this.userType = usertype;
    }

    @Override
    public void process(ConnectionThread threadServer) {
        //No need for a server-side usage (yet)
    }

    @Override
    public void process(ServerLink threadClient) {
        threadClient.getNetworkManager().getDataInstance().transmitLeaveGame(tab, user, userType);
    }
}
