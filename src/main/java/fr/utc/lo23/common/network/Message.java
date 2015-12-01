package fr.utc.lo23.common.network;

import fr.utc.lo23.client.data.InterfaceDataFromCom;
import fr.utc.lo23.server.network.threads.ConnectionThread;
import fr.utc.lo23.server.network.threads.PokerServer;

import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by raphael on 03/11/15.
 */
public abstract class Message implements Serializable {

    /**
     * Basic process
     */
    public abstract void process();

    /**
     * Server-side process
     * @param myServ
     * @param thread
     */
    public abstract void process(PokerServer myServ,  ConnectionThread thread);

    /**
     * Client-side process
     * @param dataInterface
     */
    public abstract void process(InterfaceDataFromCom dataInterface);
}