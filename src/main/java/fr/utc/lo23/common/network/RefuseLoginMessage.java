package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.server.network.threads.ConnectionThread;

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
     * @param threadServer
     */
    @Override
    public void process (ConnectionThread threadServer){
    }

    /**
     * Client-side process
     * @param threadClient
     */
    @Override
    public void process(ServerLink threadClient) {

    }
}
