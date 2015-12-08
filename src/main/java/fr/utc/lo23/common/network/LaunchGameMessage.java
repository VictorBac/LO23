package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.network.threads.ConnectionThread;
import fr.utc.lo23.server.network.threads.PokerServer;

import java.util.UUID;

/**
 * Created by raphael on 30/11/15.
 */
public class LaunchGameMessage extends Message {
    private UUID idTable;
    private UserLight UserInit;
    public LaunchGameMessage(UUID idTable,UserLight u) {
        this.idTable = idTable;
    }

    @Override
    public void process(ConnectionThread threadServer) {
        PokerServer myServ = threadServer.getMyServer();
        Console.log("Launch Game message received");
        myServ.getNetworkManager().getDataInstance().startGame(idTable,UserInit);

        LaunchGameMessage LaunchGameM = new LaunchGameMessage(idTable,UserInit);

        myServ.sendToAll(LaunchGameM);


    }

    @Override
    public void process(ServerLink threadClient) {
        Console.log("Launch Game message received");

        Console.log("Game is starting");

        //threadClient.getNetworkManager().getDataInstance().
    }
}
