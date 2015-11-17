package fr.utc.lo23.common.network;

import fr.utc.lo23.server.network.threads.PokerServer;

import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by raphael on 03/11/15.
 */
public abstract class Message implements Serializable {

    public abstract void process();
    public abstract void process(PokerServer myServ,  ObjectOutputStream out);
}