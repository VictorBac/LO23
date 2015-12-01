package fr.utc.lo23.client.network.threads;

import fr.utc.lo23.client.network.NetworkManagerClient;
import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.common.network.Message;
import fr.utc.lo23.common.network.RequestLoginMessage;
import fr.utc.lo23.exceptions.network.NetworkFailureException;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ServerLink extends Thread {
    static final String serverAddress = "localhost";
    static final int serverPort = 1904;

    private NetworkManagerClient networkManager;
    private boolean connected = false;
    private Socket socket;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;

    //TODO: Gerer le changement de port
    public ServerLink(NetworkManagerClient networkManagerClient){
        this.networkManager = networkManagerClient;

        try {
            connect();
        } catch (Exception e) {
            Console.err(e.getStackTrace().toString());
        }
    }

    /**
     * Try to connect to the server
     * @throws Exception
     */
    public void connect() throws Exception{
        if (null != socket) throw new NetworkFailureException("La socket client existe déjà");

        socket = new Socket();
        Console.logn("Le client tente de se connecter...");
        socket.connect(new InetSocketAddress(serverAddress, serverPort));
        connected = true;
        Console.log("Done");
        Console.log("Client connecté sur: " + serverAddress + ":" + serverPort + "\n");

        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * Run method (thread)
     */
    @Override
    public void run() {
        while (connected) {
            try {
                Message msg = (Message) inputStream.readObject();
                msg.process(networkManager.getDataInstance());

            } catch (Exception e) {
                e.printStackTrace();
            }
            Console.log("Client ok");
        }
    }

    /**
     * Disconnect from the server
     * @throws IOException
     */
    public void disconnect() throws IOException {
        socket.close();
    }

    public void send(Message message){
        try {
            outputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* == GETTERS AND SETTERS == */
    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }
}