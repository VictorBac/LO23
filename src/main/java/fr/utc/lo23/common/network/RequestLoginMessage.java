package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.server.network.threads.ConnectionThread;
import fr.utc.lo23.server.network.threads.PokerServer;

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
        if (myServ.getNbUsers() < PokerServer.NB_MAX_USER) {
            Console.log("There is room for one more user.\n"+ myServ.getNbUsers() + " users are connected.");

            threadServer.setUserId(user.getUserLight().getIdUser());
           //ArrayList<UserLight> aUsers = myServ.stockUserAndNotifyOthers(user.getUserLight());


            /*AcceptLoginMessage acceptL = new AcceptLoginMessage(aUsers);
            try {
                threadServer.getOutputStream().writeObject(acceptL);
            } catch (IOException e) {
                e.printStackTrace();
            }*/

        } else {
            Console.log("Connection impossible ! ");
        }
    }

    /**
     * Client-side process
     * @param threadClient
     */
    @Override
    public void process(ServerLink threadClient) {

    }

}
