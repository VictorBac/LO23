package fr.utc.lo23.client.network.threads;

import fr.utc.lo23.client.network.NetworkManagerClient;
import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.Params;
import fr.utc.lo23.common.network.Message;
import fr.utc.lo23.exceptions.network.NetworkFailureException;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServerLink extends Thread {
    private NetworkManagerClient networkManager;

    /* ============================ ATTRIBUTES ============================ */
    private Socket socket;
    private boolean running = false;
    private boolean connected = false;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;

    private int HEARTBEAT_PERIODE = 1000; // en ms

    /* ============================ METHODS ============================ */
    public ServerLink(NetworkManagerClient networkManagerClient) {
        this.networkManager = networkManagerClient;
        running = true;
        connected = false;
    }

    /**
     * Try to connect to the server
     * @throws Exception
     */
    public void connect() throws Exception{
        if (null != socket) throw new NetworkFailureException("La socket client existe déjà");

        socket = new Socket();
        Console.log("Le client tente de se connecter sur: " + Params.DEFAULT_SERVER_ADDRESS + ":" + Params.DEFAULT_SERVER_PORT);
        socket.connect(new InetSocketAddress(Params.DEFAULT_SERVER_ADDRESS, Params.DEFAULT_SERVER_PORT));
        connected = true;
        Console.log("Le client est connecté");

        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * Disconnect from the server
     * @throws IOException
     */
    public void disconnect() throws IOException {
        Console.log("Disconnected"); //TODO: reconnect ?
        running = false;
        connected = false;
        socket.close();
    }

    /**
     * Run method (thread)
     */
    @Override
    public void run() {
        while (running) {
            while(connected) {
                try {
                    try {
                        this.socket.setSoTimeout(HEARTBEAT_PERIODE);
                        //Console.log("Waiting for message...");
                        Message msg = (Message) inputStream.readObject();
                        msg.process(this);
                    } catch (SocketTimeoutException e) {
                        this.networkManager.sendHeartbeat();
                    }
                    catch (EOFException e) {
                        this.disconnect();
                    }
                } catch (Exception e) {
                    Console.err("Erreur de traitement de message: ");
                    e.printStackTrace();
                }
            }
            if(!connected) {
                try {
                    this.sleep(100);
                } catch (InterruptedException e) {
                    Console.log("Thread exception");
                }
            }
        }
    }



    /**
     * Send a Message Object to the server
     * @param message Message to send
     */
    public void send(Message message){
        try {
            outputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /* ============================ GETTERS AND SETTERS ============================ */
    public NetworkManagerClient getNetworkManager() { return networkManager; }
}