package fr.utc.lo23.client.network.threads;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.network.Message;
import fr.utc.lo23.common.network.RequestLoginMessage;
import fr.utc.lo23.exceptions.network.NetworkFailureException;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ServerLink extends Thread {
    static final String serverAddress = "localhost";
    static final int serverPort = 1904;

    private boolean connected = false;
    private Socket socket;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;

    //private InterfaceData dataInt;

    //TODO: Gerer le changement de port
    public ServerLink(){
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

        //Test to send serialized object to the server
        inputStream = new ObjectInputStream(socket.getInputStream());
        outputStream = new ObjectOutputStream(socket.getOutputStream());

        //TODO: Ca doit pas etre la, c'est coté apllicatif ca -> a mettre dans la fct connect de l'interface
        RequestLoginMessage reqLog = new RequestLoginMessage();
        outputStream.writeObject(reqLog);
    }

    /**
     * Disconnect from the server
     * @throws IOException
     */
    public void disconnect() throws IOException {
        socket.close();
        connected = false;
    }

    /**
     * Run method (thread)
     */
    @Override
    public void run() {
        while (connected) {
            try {
                Message msg = (Message) inputStream.readObject();
                msg.process();

            } catch (Exception e) {
                e.printStackTrace();
            }
            Console.log("Client ok");
        }
    }
}