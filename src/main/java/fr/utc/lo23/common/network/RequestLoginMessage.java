package fr.utc.lo23.common.network;

import fr.utc.lo23.client.data.InterfaceDataFromCom;
import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.network.threads.ConnectionThread;
import fr.utc.lo23.server.network.threads.PokerServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
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
     * @param myServ
     * @param thread
     */
    @Override
    public void process (PokerServer myServ, ConnectionThread thread){
        Console.log("Request login message received");

        Console.log("Checking if there is room for one more user");
        if (true) {
            Console.log("There is room for one more user.\n"+ myServ.getNbUsers() + " users are connected.");
            Console.log("pseudo " + user.getPseudo());

            ArrayList<User> aUsers = myServ.stockUserAndNotifyOthers(user);

            AcceptLoginMessage acceptL = new AcceptLoginMessage(aUsers);
            try {
                thread.getOutputStream().writeObject(acceptL);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Console.log("Connection impossible ! ");
        }
    }

    /**
     * Client-side process
     * @param dataInterface
     */
    @Override
    public void process(InterfaceDataFromCom dataInterface) {

    }

}
