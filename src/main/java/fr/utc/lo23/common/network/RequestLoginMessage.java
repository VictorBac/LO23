package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.Params;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.common.data.exceptions.ExistingUserException;
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

    /**
     * Generic process
     */
    @Override
    public void process() {

    }

    /**
     * Check if we can login in the server, and send a confirmation (or not ?)
     * We put in the acceptloginmessage all users currently connected to have them directly at the confirmation
     * We also notify all other users that now we're in da place
     * @param threadServer
     */
    @Override
    public void process (ConnectionThread threadServer){
        PokerServer myServ = threadServer.getMyServer();

        Console.log("Request login message received");

        Console.log("Checking if there is room for one more user");
        if (myServ.getNbUsers() < Params.NB_MAX_USER) {
            Console.log("There is room for one more user.\n"+ myServ.getNbUsers() + " users are connected.");

            //Giving the user to data
            try {
                UserLight ul = myServ.getNetworkManager().getDataInstance().userConnection(user);
                threadServer.setUserId(user.getUserLight().getIdUser());
                ArrayList<UserLight> aUsers = myServ.getNetworkManager().getDataInstance().getConnectedUsers();

                //On envoie un message au client pour accepter sa connexion
                sendConnectionConfirmation(aUsers, threadServer);

                //On envoie à tous les autres clients un notify new client.
                notifyNewUserToCurrentPlayers(ul, myServ);
            } catch (ExistingUserException e) {
                e.printStackTrace();
            }

        } else {
            Console.log("Connection impossible ! ");
            sendConnectionRejection(threadServer);
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
    private void sendConnectionConfirmation(ArrayList<UserLight> aUsers, ConnectionThread threadServer) {
        AcceptLoginMessage acceptL = new AcceptLoginMessage(aUsers);
        threadServer.send(acceptL);
    }

    private void sendConnectionRejection(ConnectionThread threadServer) {
        RefuseLoginMessage refuseL = new RefuseLoginMessage();
        threadServer.send(refuseL);
    }

}
