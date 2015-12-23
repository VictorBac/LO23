package fr.utc.lo23.common.network;

import fr.utc.lo23.client.data.InterfaceDataFromCom;
import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.data.InterfaceServerDataFromCom;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Message to be sent to every users connected to notify them
 * That there is a new player
 * Created by rbonneau on 18/11/2015.
 */
public class NotifyNewPlayerMessage extends Message {

    private UserLight newUser;

    public NotifyNewPlayerMessage(UserLight u) {
        newUser = u;
    }

    @Override
    public void process(ConnectionThread threadServer) {

    }

    @Override
    public void process(ServerLink threadClient) {
        InterfaceDataFromCom dataInterface = threadClient.getNetworkManager().getDataInstance();

        if( newUser.getIdUser().equals(dataInterface.getUserLightLocal().getIdUser()) ) {
            Console.log("NotifyNewPlayerMessage abbandoné : newUser = moi");
            return;
        }

        dataInterface.remoteUserConnected(newUser);
        Console.log("NotifyNewPlayerMessage, lLogin du nouvel utilisteur : " + newUser.getPseudo());

    }
}
