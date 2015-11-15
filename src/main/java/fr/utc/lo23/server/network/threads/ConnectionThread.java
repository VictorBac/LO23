package fr.utc.lo23.server.network.threads;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.network.Message;

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

        try {
            inputStream = new ObjectInputStream(soClient.getInputStream());
            outputStream = new ObjectOutputStream(soClient.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Console.log("Nouveau Client: " + socket.getInetAddress());
    }

    /**
     * Run method (thread)
     */
    @Override
    public synchronized void run() {

        Console.log("Client: Démarré\n");


        try {
            Message msg = (Message) inputStream.readObject();
            msg.process(myServer, outputStream);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
