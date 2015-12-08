package fr.utc.lo23.server.network.threads;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.network.Message;
import fr.utc.lo23.exceptions.network.NetworkFailureException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.UUID;

public class ConnectionThread extends Thread {
    private boolean running;
    private PokerServer myServer;
    private Socket socketClient;

    //Heartbeat
    private long last_message_timestamp;

    //To send objects between client and server
    private UUID userId;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;

    private long HEARTBEAT_TIMEOUT = 1000; // en ms

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }


    public ConnectionThread(Socket socket, PokerServer pokerServer) {
        myServer = pokerServer;
        socketClient = socket;
        last_message_timestamp = System.currentTimeMillis();

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

        /* Heartbeat a tester
        Console.log("Timer pour le heartbeat démarré");
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        checkHeatBeat();
                    }
                },
                10000
        );*/

        running = true;
        try {
            while (running) {
                try {
                    // Call suitable processing method
                    this.socketClient.setSoTimeout(100);// en ms
                    Message msg = (Message) inputStream.readObject();
                    msg.process(this);
                } catch (SocketTimeoutException e) {
                    this.checkHeartBeat();
                } catch (Exception e) {
                    e.printStackTrace();
                    this.shutdown();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            myServer.userDisconnect(userId);
        }
        catch(Exception e){
            Console.err("Impossible d'enlever le thread du l'user de la liste des threads");
        }
    }


    public void send(Message message){
        try {
            outputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() throws NetworkFailureException {
        try {
            running = false;
            socketClient.close();
        }
        catch (Exception e){
            throw new NetworkFailureException("Impossible de fermer la socket proprement");
        }
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


    /**
     * Décrémente le heartbeat et regarde s'il en a reçu depuis
     * Si ce n'est pas le cas, déconnecte
     */
    private void checkHeartBeat() throws NetworkFailureException {
        //Console.log("Valeur HB : " + last_message_timestamp);
        if (System.currentTimeMillis() - last_message_timestamp > HEARTBEAT_TIMEOUT) {
            Console.log("HB nul, on doit deconnecter le client ici");
            this.shutdown();
        }
    }

    /**
     * Met à jour le compteur de heartbeat
     * Suite à la réception d'un message de ce type
     */
    public void updateHeartbeat() {
        //Console.log("Update HB" + last_message_timestamp);
        last_message_timestamp = System.currentTimeMillis();
    }
}
