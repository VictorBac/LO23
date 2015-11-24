package fr.utc.lo23.server.network.main;

import fr.utc.lo23.server.network.NetworkManagerServer;

/**
 * Created by guixii on 03/11/15.
 * Main for test launch a server without GUI
 */
public class Main {
    public static void main(String[] args){
        int portToListen = 1904;
        NetworkManagerServer manager = new NetworkManagerServer(portToListen);

    }
}
