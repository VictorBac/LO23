package fr.utc.lo23.server.network.threads;

import fr.utc.lo23.client.network.main.Console;

import java.net.Socket;

public class ConnectionThread extends Thread {
    private PokerServer myServer;

    public ConnectionThread(Socket socket, PokerServer pokerServer) {
        myServer = pokerServer;
        Console.log("Nouveau Client: " + socket.getInetAddress());
    }

    @Override
    public synchronized void run() {
        Console.log("Client: Démarré\n");
    }
}
