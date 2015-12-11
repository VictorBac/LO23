package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
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

    public LeaveTableMessage(UserLight userLocal, UUID idTable) {
        super();
        this.user = userLocal;
        this.tab = idTable;
    }

    @Override
    public void process(ConnectionThread threadServer) {
        PokerServer myServ = threadServer.getMyServer();
        NotifyPlayerLeftTable message = new NotifyPlayerLeftTable(user,tab);
        myServ.sendToListOfUsers(myServ.getNetworkManager().getDataInstance().getPlayersByTable(tab), message);
    }

    @Override
    public void process(ServerLink threadClient) {

    }
}
