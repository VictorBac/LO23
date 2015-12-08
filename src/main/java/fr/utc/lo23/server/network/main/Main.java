package fr.utc.lo23.server.network.main;

import fr.utc.lo23.server.data.DataManagerServer;
import fr.utc.lo23.server.network.NetworkManagerServer;

/**
 * Created by guixii on 03/11/15.
 * Main for test launch a server without GUI
 */
public class Main {
    public static void main(String[] args){
        NetworkManagerServer networkManager = new NetworkManagerServer();
        networkManager.start(1904);
        DataManagerServer dataManager = new DataManagerServer();
        networkManager.setDataInstance(dataManager.getInterfaceFromCom());
    }
}
