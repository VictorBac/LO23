package fr.utc.lo23.common.network;

import fr.utc.lo23.client.data.InterfaceDataFromCom;
import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.network.threads.ConnectionThread;
import fr.utc.lo23.server.network.threads.PokerServer;

import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by raphael on 03/11/15.
 */
public class AcceptLoginMessage extends Message{

    private ArrayList<UserLight> usersArray;

    public AcceptLoginMessage(ArrayList<UserLight> aUser) {usersArray=aUser;}

    /**
     * Generic process (both server and client)
     */
    @Override
    public void process() {
        Console.log("Login accepted");
        //TODO
    }

    /**
     * For message processed server-side
     * @param myServ
     * @param thread
     */
    @Override
    public void process (PokerServer myServ,  ConnectionThread thread){
    }

    /**
     * Client-side process
     * @param dataInterface
     */
    @Override
    public void process(InterfaceDataFromCom dataInterface) {
        //dataInterface.currentConnectedUser(usersArray);
    }

}