package fr.utc.lo23.server.network.threads;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.main.TestSerialize;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionThread extends Thread {
    private PokerServer myServer;
    private Socket soClient;

    //To send objects between client and server
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;

    public ConnectionThread(Socket socket, PokerServer pokerServer) {
        myServer = pokerServer;
        soClient = socket;
        Console.log("Nouveau Client: " + socket.getInetAddress());
    }

    @Override
    public synchronized void run() {

        Console.log("Client: Démarré\n");


        try {

            //Test to receive serialized object from the server
            inputStream = new ObjectInputStream(soClient.getInputStream());
            TestSerialize testS = (TestSerialize) inputStream.readObject();
            Console.log("Received : " + testS);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
