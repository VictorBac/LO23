package fr.utc.lo23.client.network.threads;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.main.TestSerialize;
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

    public void connect() throws Exception{
        if (null != socket) throw new NetworkFailureException("La socket client existe déjà");

        socket = new Socket();
        Console.lognl("Le client tente de se connecter...");
        socket.connect(new InetSocketAddress(serverAddress, serverPort));
        Console.log("Done");
        Console.log("Client connecté sur: " + serverAddress + ":" + serverPort + "\n");

        //Test to receive serialized object from the server
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        TestSerialize testS = new TestSerialize(1,"salut");
        System.out.println("Object to send : " + testS);
        outputStream.writeObject(testS);
        System.out.println("Object sent");

        //Ending the conversation
        disconnect();


    }


    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Console.log("Client ok");
        }
    }

    public void disconnect() throws IOException {
        socket.close();
    }
}