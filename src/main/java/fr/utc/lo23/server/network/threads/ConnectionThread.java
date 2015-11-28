package fr.utc.lo23.server.network.threads;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.network.Message;
import fr.utc.lo23.exceptions.network.NetworkFailureException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

public class ConnectionThread extends Thread {
    private boolean running;
    private PokerServer myServer;
    private Socket socketClient;

    //Heartbeat
    private int nbHeartBeat;

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
        nbHeartBeat = 5;

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
                    Message msg = (Message) inputStream.readObject();
                    msg.process(this);

                } catch (Exception e) {
                    e.printStackTrace();
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
            socketClient.close();
        }
        catch (Exception e){
            throw new NetworkFailureException("Impossible de fermer la socket proprement");
        }
        running = false;
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
    private void checkHeatBeat() {
        nbHeartBeat--;
        Console.log("Valeur HB : "+nbHeartBeat);
        if (nbHeartBeat == 0) {
            Console.log("HB nul, déconnexion");
            //Procéder à la déconnection
        }
    }

    /**
     * Met à jour le compteur de heartbeat
     * Suite à la réception d'un message de ce type
     */
    public void updateHeartbeat() {
        nbHeartBeat++;
    }
}
