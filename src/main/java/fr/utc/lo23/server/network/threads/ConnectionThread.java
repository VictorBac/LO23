package fr.utc.lo23.server.network.threads;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.network.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

public class ConnectionThread extends Thread {
    private boolean running;
    private PokerServer myServer;
    private Socket socketClient;

    //To send objects between client and server
    private UUID userId;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }


    public ConnectionThread(Socket socket, PokerServer pokerServer) {
        myServer = pokerServer;
        socketClient = socket;

        try {
            inputStream = new ObjectInputStream(socketClient.getInputStream());
            outputStream = new ObjectOutputStream(socketClient.getOutputStream());
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
        Console.log("Client: Démarré");
        running = true;
        while (running) {
            try {
                // Call suitable processing method
                Message msg = (Message) inputStream.readObject();
                msg.process(this);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void send(Message message){
        try {
            outputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() throws IOException {
        running = false;
        socketClient.close();
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public PokerServer getMyServer() {
        return myServer;
    }
}
