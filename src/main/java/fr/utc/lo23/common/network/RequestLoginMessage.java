package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.server.network.threads.PokerServer;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Message permettant de demander au serveur si la
 * connection est possible
 * Created by rbonneau on 14/11/2015.
 */
public class RequestLoginMessage extends Message {

    public RequestLoginMessage() {
    }

    /**
     * Generic process
     */
    @Override
    public void process() {

    }

    /**
     * Check if we can login in the server, and send a confirmation (or not ?)
     * @param myServ
     * @param out
     */
    @Override
    public void process (PokerServer myServ, ObjectOutputStream out){
        Console.log("Request login message received");

        Console.log("Checking if there is room for one more user");
        boolean result = myServ.checkIfUserCanConnect();
        if (result) {
            Console.log("There is room for one more user.\n"+ myServ.getNbUsers() + " users are connected.");

            AcceptLoginMessage acceptL = new AcceptLoginMessage();
            try {
                out.writeObject(acceptL);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Console.log("Connection impossible ! ");
        }
    }

}
