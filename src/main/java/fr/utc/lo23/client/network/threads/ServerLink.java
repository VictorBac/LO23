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

    public Socket socket;

    private BufferedReader br;
    private PrintWriter pw;
    private boolean running;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;

    //private InterfaceData dataInt;

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
        Console.lognl("Le client tente de se connecter...");
        socket.connect(new InetSocketAddress(serverAddress, serverPort));
        Console.log("Done");
        Console.log("Client connecté sur: " + serverAddress + ":" + serverPort + "\n");

        //Test to send serialized object to the server
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        RequestLoginMessage reqLog = new RequestLoginMessage();
        outputStream.writeObject(reqLog);

        inputStream = new ObjectInputStream(socket.getInputStream());

        //Ending the conversation
        //disconnect();


    }

    /**
     * Run method (thread)
     */
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10000);
                try {
                    Message msg = (Message) inputStream.readObject();
                    msg.process();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
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
}