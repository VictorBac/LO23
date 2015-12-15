package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.Game;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.network.threads.ConnectionThread;
import fr.utc.lo23.server.network.threads.PokerServer;

/**
 * Created by raphael on 15/12/15.
 */
public class RequestLogGameMessage extends Message {

    private UserLight u;

    public RequestLogGameMessage(UserLight u) {
        this.u = u;
    }

    @Override
    public void process(ConnectionThread threadServer) {
        PokerServer myServ = threadServer.getMyServer();

        Console.log("SendChatMessage message received");
        try {
            // Attente de modif de data
          //Table LogGame =  myServ.getNetworkManager().getDataInstance().sendLogGame(u);
           // SendLogGameMessage LogM = new SendLogGameMessage(LogGame);
           // threadServer.send(LogM);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void process(ServerLink threadClient) {

    }
}
