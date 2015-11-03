package fr.utc.lo23.client.network.threads;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.exceptions.network.NetworkFailureException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ServerLink extends Thread {
    static final String serverAddress = "localhost";
    static final int serverPort = 1904;

    public Socket socket;

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

    public void disconnnect() throws IOException {
        socket.close();
    }
}
