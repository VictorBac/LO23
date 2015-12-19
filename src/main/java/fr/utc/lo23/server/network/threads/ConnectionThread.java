package fr.utc.lo23.server.network.threads;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.Params;
import fr.utc.lo23.common.network.Message;
import fr.utc.lo23.exceptions.network.NetworkFailureException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.UUID;
import java.util.concurrent.Exchanger;

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
        running = true;
        last_message_timestamp = System.currentTimeMillis();
        try {
            while (running) {
                try {
                    this.socketClient.setSoTimeout(Params.HEARTBEAT_PERIODE);// en ms

                    Message msg = (Message) inputStream.readObject();
                    this.updateHeartbeat();
                    msg.process(this);
                } catch (SocketTimeoutException e) {
                    this.checkHeartbeat();
                } catch (java.io.EOFException e) {
                    Console.log("Le client s'est déconnecté sans prévenir !");
                    this.shutdown();
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
     * La méthode est appelé regulierement pour verifier que le dernier message reçu n'est pas trop vieux,
     */
    private void checkHeartbeat() throws NetworkFailureException {
        //Console.log("Valeur HB : " + last_message_timestamp);
        int heartbeat_timeout = Params.HEARTBEAT_PERIODE*10;
        if (System.currentTimeMillis() - last_message_timestamp > heartbeat_timeout) {
            Console.log(
                    "Heartbeat: on a pas recu de message depuis plus de "+
                    heartbeat_timeout+
                    " ms donc on deconnect le client avec le login: "+
                    this.myServer.getNetworkManager().getDataInstance().getUserById(userId).getLogin()
            );
            this.shutdown();
        }
    }

    /**
     * A chaque reception de message, heartbeat ou autre, on met à jour la date du dernier message.
     */
    public void updateHeartbeat() {
        //Console.log("Update HB" + last_message_timestamp);
        last_message_timestamp = System.currentTimeMillis();
    }
}
