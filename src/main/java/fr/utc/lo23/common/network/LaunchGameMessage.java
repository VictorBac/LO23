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
        //TODO: en fonction du retour de startGame, envoyer une erreur ou une confirmation du lancement de la game
        PokerServer myServ = threadServer.getMyServer();
        Console.log("Launch Game message received");
        //TODO : décommenter quand la fonction startGame renverra bien un boolean
        //if(myServ.getNetworkManager().getDataInstance().startGame(idTable,UserInit)){
            LaunchGameMessage launchGameM = new LaunchGameMessage(idTable,UserInit);
            myServ.sendToListOfUsers(threadServer.getMyServer().getNetworkManager().getDataInstance().getPlayersByTable(idTable),launchGameM);
            AskMoneyMessage askMoneyMess = new AskMoneyMessage();
            myServ.sendToListOfUsers(threadServer.getMyServer().getNetworkManager().getDataInstance().getPlayersByTable(idTable),launchGameM);
    }

    @Override
    public void process(ServerLink threadClient) {
        Console.log("Launch Game message received");
        Console.log("Game is starting");
        //TODO : Decommenter quand la fonction existe
        //threadClient.getNetworkManager().getDataInstance().tableCreatorRequestToStartGameAccepted();
    }
}
