package fr.utc.lo23.common.network;

import fr.utc.lo23.client.data.InterfaceDataFromCom;
import fr.utc.lo23.client.data.Userlight;
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
public class RequestListUserMessage extends Message {

    public RequestListUserMessage() {
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
     * @param thread
     */
    @Override
    public void process (PokerServer myServ, ConnectionThread thread){

        Console.log("Request list of users");
        //ArrayList<User> list =myServ.getUserList(); appeler l'interface de data ?


       /* SendListUserMessage listUserMess = new SendListUserMessage(list);
        try {
            out.writeObject(listUserMess);
        }catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void process(InterfaceDataFromCom dataInterface) {

    }

}
