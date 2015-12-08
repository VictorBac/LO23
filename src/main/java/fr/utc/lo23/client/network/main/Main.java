package fr.utc.lo23.client.network.main;

import fr.utc.lo23.client.data.DataManagerClient;
import fr.utc.lo23.client.network.NetworkManagerClient;

/**
 * Created by guixii on 03/11/15.
 * Main for test with 2 clients virtually launched
 */
public class Main {
    public static void main(String[] args){
        Console.log("Main: Lancement client 1");
        DataManagerClient dataManCli = new DataManagerClient();
        NetworkManagerClient clientInterface = new NetworkManagerClient(dataManCli.getInterFromCom());
    }
}

