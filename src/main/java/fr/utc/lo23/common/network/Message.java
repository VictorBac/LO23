package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.server.network.threads.ConnectionThread;

import java.io.Serializable;

/**
 * Created by raphael on 03/11/15.
 */
public abstract class Message implements Serializable {

    /**
     * Server-side process
     * @param threadServer
     */
    public abstract void process(ConnectionThread threadServer);

    /**
     * Client-side process
     * @param threadClient
     */
    public abstract void process(ServerLink threadClient);
}