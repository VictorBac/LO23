package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.server.network.threads.ConnectionThread;

import java.io.Serializable;

/**
 * Abstract class used to give
 * polymorphism to the messages
 * Created by raphael on 03/11/15.
 */
public abstract class Message implements Serializable {

    /**
     * Server-side process
     * @param threadServer server's thread
     */
    public abstract void process(ConnectionThread threadServer);

    /**
     * Client-side process
     * @param threadClient client's thread
     */
    public abstract void process(ServerLink threadClient);
}