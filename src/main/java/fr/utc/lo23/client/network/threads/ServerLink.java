package fr.utc.lo23.client.network.threads;

import fr.utc.lo23.client.data.InterfaceDataFromCom;
import fr.utc.lo23.client.data.Userlight;
import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.common.network.Message;
import fr.utc.lo23.common.network.RequestListUserMessage;
import fr.utc.lo23.common.network.RequestLoginMessage;
import fr.utc.lo23.common.network.SendListUserMessage;
import fr.utc.lo23.exceptions.network.NetworkFailureException;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

public class ServerLink extends Thread {
    static final String serverAddress = "localhost";
    static final int serverPort = 1904;

    private boolean connected = false;
    private Socket socket;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
    private InterfaceDataFromCom dataInterface;

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

        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());

        //Reception du message lie au login
        /*Message msg = (Message) inputStream.readObject();
        msg.process();*/

        //TODO: Ca doit pas etre la, c'est coté apllicatif ca -> a mettre dans la fct connect de l'interface
       //RequestListUserMessage reqList = new RequestListUserMessage();
       // outputStream.writeObject(reqList);

        //Reception du message requestListUser
       // Message msg2 = (Message) inputStream.readObject();
        //msg2.process();

        //Ending the conversation
        //disconnect();
    }

    /**
     * Run method (thread)
     */
    @Override
    public void run() {
        while (connected) {
            try {
                Message msg = (Message) inputStream.readObject();
                msg.process(dataInterface);

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

    public void sendLoginRequest(User userLocal){
        //Test to send serialized object to the server
        try {
            Console.log("Creation d'un Request Login message\n");
            RequestLoginMessage reqLog = new RequestLoginMessage(userLocal);
            outputStream.writeObject(reqLog);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the data interface we will be using
     * @param dataInterface
     */
    public void setDataInterface(InterfaceDataFromCom dataInterface) {
        this.dataInterface = dataInterface;
    }
}