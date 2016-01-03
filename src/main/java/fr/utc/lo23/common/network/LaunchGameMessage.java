package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.network.threads.ConnectionThread;
import fr.utc.lo23.server.network.threads.PokerServer;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Message to launch the game
 * Created by raphael on 30/11/15.
 */
public class LaunchGameMessage extends Message {
    private UUID idTable;
    private UserLight userInit;

    public LaunchGameMessage(UUID idTable,UserLight u) {
        this.idTable = idTable;
        this.userInit = u;
    }

    @Override
    public void process(ConnectionThread threadServer) {
        PokerServer myServ = threadServer.getMyServer();
        Console.log("Launch Game message received");
        if (myServ.getNetworkManager().getDataInstance().startGame(idTable, userInit)) {
            LaunchGameMessage launchGameM = new LaunchGameMessage(idTable, userInit);
            myServ.sendToListOfUsers(threadServer.getMyServer().getNetworkManager().getDataInstance().getUsersByTable(idTable), launchGameM);
            AskMoneyMessage askMoneyMess = new AskMoneyMessage();
            myServ.sendToListOfUsers(threadServer.getMyServer().getNetworkManager().getDataInstance().getPlayersByTable(idTable), askMoneyMess);
            System.out.println("accepte envoyé");
        } else {
            RefuseStartGameMessage refuseStartGameM = new RefuseStartGameMessage();
            ArrayList<UserLight> temp = new ArrayList<>();
            temp.add(userInit);
            myServ.sendToListOfUsers(temp, refuseStartGameM);
            System.out.println("refus envoyé");
        }
    }

    @Override
    public void process(ServerLink threadClient) {
        Console.log("Launch Game message received");
        Console.log("Game is starting");
        threadClient.getNetworkManager().getDataInstance().tableCreatorRequestToStartGameAccepted();
    }
}
