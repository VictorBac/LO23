package fr.utc.lo23.client.network.threads;

import fr.utc.lo23.client.network.NetworkManagerClient;
import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.Params;
import fr.utc.lo23.common.network.Message;
import fr.utc.lo23.exceptions.network.NetworkFailureException;

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

    private int HEARTBEAT_PERIODE = 100; // en ms

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
        Console.logn("Le client tente de se connecter...");
        socket.connect(new InetSocketAddress(Params.DEFAULT_SERVER_ADDRESS, Params.DEFAULT_SERVER_PORT));
        connected = true;
        Console.log("Done");
        Console.log("Client connecté sur: " + Params.DEFAULT_SERVER_ADDRESS + ":" + Params.DEFAULT_SERVER_PORT + "\n");

        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * Disconnect from the server
     * @throws IOException
     */
    public void disconnect() throws IOException {
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
                        this.socket.setSoTimeout(1000);
                        //Console.log("Waiting for message...");
                        Message msg = (Message) inputStream.readObject();
                        msg.process(this);
                    } catch (SocketTimeoutException e) {
                        this.networkManager.sendHeartbeat();
                    }
                } catch (Exception e) {
                    Console.err("Erreur de traitement de message: ");
                    e.printStackTrace();
                }
            }
            if(!connected) {
                try {
            // TODO vérifier quelle est la solution (fix de conflit, je ne suis pas sur)
          /*        this.socket.setSoTimeout(HEARTBEAT_PERIODE);
                    Message msg = (Message) inputStream.readObject();
                    msg.process(this);
                } catch (SocketTimeoutException e) {
                    this.networkManager.sendHeartbeat();
            */

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