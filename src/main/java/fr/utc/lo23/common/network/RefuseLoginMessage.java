package fr.utc.lo23.common.network;

import fr.utc.lo23.client.data.InterfaceDataFromCom;
import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.server.network.threads.PokerServer;

import java.io.ObjectOutputStream;

/**
 * Created by rbonneau on 15/11/2015.
 */
public class RefuseLoginMessage extends Message {

    public RefuseLoginMessage() {
    }

    /**
     * Generic process (both server and client)
     */
    @Override
    public void process() {
        Console.log("Login refused");
        //TODO
    }

    /**
     * For message processed server-side
     * @param myServ
     * @param out
     */
    @Override
    public void process (PokerServer myServ,  ObjectOutputStream out){
    }

    /**
     * Client-side process
     * @param dataInterface
     */
    @Override
    public void process(InterfaceDataFromCom dataInterface) {

    }
}
