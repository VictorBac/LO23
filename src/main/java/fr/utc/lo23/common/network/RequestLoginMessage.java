package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.Params;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.common.data.exceptions.ExistingUserException;
import fr.utc.lo23.server.data.InterfaceServerDataFromCom;
import fr.utc.lo23.server.network.NetworkManagerServer;
import fr.utc.lo23.server.network.threads.ConnectionThread;
import fr.utc.lo23.server.network.threads.PokerServer;

import java.util.ArrayList;

/**
 * Message permettant de demander au serveur si la
 * connection est possible
 * Created by rbonneau on 14/11/2015.
 */
public class RequestLoginMessage extends Message {

    private User user;

    public RequestLoginMessage(User userLocal) {
        user = userLocal;
    }

    public User getUser() {
        return user;
    }

    /**
     * Check if we can login in the server, and send a confirmation (or not ?)
     * We put in the acceptloginmessage all users currently connected to have them directly at the confirmation
     * We also notify all other users that now we're in da place
     * @param threadServer
     */
    @Override
    public void process (ConnectionThread threadServer){
        PokerServer pokerServer = threadServer.getMyServer();
        InterfaceServerDataFromCom dataInterface = pokerServer.getNetworkManager().getDataInstance();

        Console.log("RequestLoginMessage, reçu du login: "+user.getLogin());

        if (pokerServer.getNbUsers() >= Params.NB_MAX_USER) {
            Console.err("There is no room for new user ! " + pokerServer.getNbUsers() + " users are already connected.");
            sendConnectionRejection(threadServer);
            return;
        }
        //Give new user to data
        try {
            UserLight ul = dataInterface.userConnection(user);
            threadServer.setUserId(user.getUserLight().getIdUser());
            ArrayList<UserLight> userList = dataInterface.getConnectedUsers();
            ArrayList<Table> tableList = dataInterface.getTableList();

            //On envoie un message au client pour accepter sa connexion
            sendConnectionConfirmation(userList, tableList, threadServer);
            //On envoie à tous les autres clients un notify new client.
            notifyNewUserToCurrentPlayers(ul, pokerServer);

            Console.log("Connection reussi avec le login: "+user.getLogin());
            Console.log("Nouvelle liste des utilisateurs connectés: ");
            for(UserLight oneUser : dataInterface.getConnectedUsers()){
                Console.log("- " +oneUser.getPseudo());
        }
        } catch (ExistingUserException e) {
            e.printStackTrace();
        }
    }

    /**
     * Client-side process
     * @param threadClient
     */
    @Override
    public void process(ServerLink threadClient) {

    }

    /**
     * Notifie aux joueurs connecté la présence
     * d'un nouveau joueur
     * @param ul
     */
    private void notifyNewUserToCurrentPlayers(UserLight ul, PokerServer myServ) {
        NotifyNewPlayerMessage newPlayerM = new NotifyNewPlayerMessage(ul);
        myServ.sendToAll(newPlayerM);
    }

    /**
     * Envoie la confirmation de connection
     * au nouveau joueur ainsi que la liste
     * des joueurs actuels
     * @param aUsers
     */
    private void sendConnectionConfirmation(ArrayList<UserLight> aUsers, ArrayList<Table> aTables, ConnectionThread threadServer) {
        AcceptLoginMessage acceptL = new AcceptLoginMessage(aUsers, aTables);
        threadServer.send(acceptL);
    }

    private void sendConnectionRejection(ConnectionThread threadServer) {
        RefuseLoginMessage refuseL = new RefuseLoginMessage();
        threadServer.send(refuseL);
    }

}
