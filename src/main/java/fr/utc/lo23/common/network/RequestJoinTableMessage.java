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
            System.out.println("players dedans TABLE avant envoi: "+myServ.getNetworkManager().getDataInstance().getTableFromId(idTab).getListPlayers().getListUserLights());
            myServ.getNetworkManager().getDataInstance().addPlayerToTable(idTab, user, mode);
            System.out.println("ENVOI TABLE: "+myServ.getNetworkManager().getDataInstance().getTableFromId(idTab).getIdTable());
            System.out.println("players dedans TABLE: "+myServ.getNetworkManager().getDataInstance().getTableFromId(idTab).getListPlayers().getListUserLights());
            AcceptJoinTableMessage accept = new AcceptJoinTableMessage(idTab,mode);
            threadServer.send(accept);
            //TODO : Informer le serveur qu'un client vient de se connecter a la table. Manque une interface?
            NotifyNewPlayerInTableMessage notifNewPlayTable = new NotifyNewPlayerInTableMessage(user,idTab,mode);
            //Test d'envoi seulement à la liste des joueurs concernés.
            /*for(UserLight user:myServ.getNetworkManager().getDataInstance().getPlayersByTable(idTab))
            {
                System.out.println(user);
            }*/
            myServ.sendToAll(notifNewPlayTable);
            //myServ.sendToListOfUsers(myServ.getNetworkManager().getDataInstance().getPlayersByTable(idTab),notifNewPlayTable);
         }else {
            RefuseJoinTableMessage refuse = new RefuseJoinTableMessage(idTab,mode);
            threadServer.send(refuse);
        }
    }

    @Override
    public void process(ServerLink threadClient) {

    }
}
