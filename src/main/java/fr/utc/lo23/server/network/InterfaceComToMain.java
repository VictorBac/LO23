package fr.utc.lo23.server.network;

import fr.utc.lo23.exceptions.network.NetworkFailureException;

/**
 * Created by Zangdar on 03/12/2015.
 */
public interface InterfaceComToMain {

    public void start(int portToListen) throws NetworkFailureException;
}
