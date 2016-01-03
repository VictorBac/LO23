package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.EnumerationTypeOfUser;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.network.threads.ConnectionThread;
import fr.utc.lo23.server.network.threads.PokerServer;

import java.util.UUID;

/**
 * Created by Jean-Côme on 11/12/2015.
 */
public class LeaveTableMessage extends Message {
    UserLight user;
    UUID tab ;
    EnumerationTypeOfUser userType;

    public LeaveTableMessage(UserLight userLocal, UUID idTable, EnumerationTypeOfUser usertype) {
        super();
        this.user = userLocal;
        this.tab = idTable;
        this.userType = usertype;
    }

    @Override
    public void process(ConnectionThread threadServer) {
        PokerServer myServ = threadServer.getMyServer();
        NotifyPlayerLeftTableMessage message = new NotifyPlayerLeftTableMessage(user,tab,userType);
        myServ.sendToAll(message);
        myServ.getNetworkManager().getDataInstance().removePlayerFromTable(tab, user,userType);
    }

    @Override
    public void process(ServerLink threadClient) {
        //No need for a client-side usage (yet)
    }
}
