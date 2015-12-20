package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.EnumerationTypeOfUser;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.network.threads.ConnectionThread;

import java.util.UUID;

/**
 * Created by Jean-Côme on 11/12/2015.
 */
public class NotifyPlayerLeftTableMessage extends Message {

    UserLight user;
    UUID tab ;

    public NotifyPlayerLeftTableMessage(UserLight userLocal, UUID idTable) {
        super();
        this.user = userLocal;
        this.tab = idTable;
    }

    @Override
    public void process(ConnectionThread threadServer) {

    }

    @Override
    public void process(ServerLink threadClient) {
        //TODO: Voir pour passer le type d'utilisateur
        threadClient.getNetworkManager().getDataInstance().transmitLeaveGame(tab, user, EnumerationTypeOfUser.PLAYER);
    }
}
