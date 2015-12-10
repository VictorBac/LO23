package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.EnumerationTypeOfUser;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.network.threads.ConnectionThread;
import fr.utc.lo23.server.network.threads.PokerServer;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Jean-Côme on 01/12/2015.
 * Demande de connection a une table par un user
 */
public class RequestJoinTableMessage extends Message {
    private UserLight user;
    private UUID idTab;
    private EnumerationTypeOfUser mode;

    public RequestJoinTableMessage(UserLight userLocal, UUID tableToJoinID, EnumerationTypeOfUser mode) {
        super();
        this.user = userLocal;
        this.idTab=tableToJoinID;
        this.mode=mode;
    }

    @Override
    public void process(ConnectionThread threadServer) {
        PokerServer myServ = threadServer.getMyServer();
        Console.log("Request table connexion");
        if(myServ.getNetworkManager().getDataInstance().canJoinTableUser(user,idTab,mode)){
            AcceptJoinTableMessage accept = new AcceptJoinTableMessage(idTab,mode);
            threadServer.send(accept);
            //TODO Avertir les autres clients
         }else {
            RefuseJoinTableMessage refuse = new RefuseJoinTableMessage(idTab,mode);
            threadServer.send(refuse);
        }
    }

    @Override
    public void process(ServerLink threadClient) {

    }
}
