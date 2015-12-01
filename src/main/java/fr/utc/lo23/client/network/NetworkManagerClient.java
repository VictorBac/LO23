package fr.utc.lo23.client.network;

import fr.utc.lo23.client.data.InterfaceDataFromCom;
import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.Action;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.common.network.*;
import fr.utc.lo23.exceptions.network.*;

/**
 *
 * @author Jean-Côme
 */
public class NetworkManagerClient implements InterfaceClient  {
    /* Modules instance, initiate by IHM module with setters */
    private InterfaceDataFromCom dataInstance;

    /* Attributes */
    private ServerLink localClient;

    /* =========================================== METHODES =========================================== */
    public NetworkManagerClient(InterfaceDataFromCom dataInter) throws NetworkFailureException{
        this.dataInstance = dataInter;
        localClient = new ServerLink(this);
        localClient.start();

        User user = new User();
        requestLoginServer(user);
    }

    /* == GETTERS AND SETTERS == */
    public InterfaceDataFromCom getDataInstance() { return dataInstance; }

    /* == METHODES IMPLEMENTATION == */
    /**
     * Envoi la reclamation de connexion du client.
     * @param u
     * @throws NetworkFailureException
     */
    public void requestLoginServer(User u){
        //Send the login request over the server
        Console.log("Creation d'un Request Login message\n");
        RequestLoginMessage reqLog = new RequestLoginMessage(u);
        localClient.send(reqLog);
    }

    /**
     *
     * @param u
     * @throws NetworkFailureException
     */
    public void sendProfile(User u) throws NetworkFailureException {

    }

    /**
     * Request de la liste des users connectes
     * @throws NetworkFailureException
     */
    public void requestUserList() throws NetworkFailureException {
        //Request user list
        Console.logn("Creation d'un Request de list des users");
        RequestListUserMessage reqUseList = new RequestListUserMessage();
        localClient.send(reqUseList);
    }

    /**
     * Request la liste des tables actives
     * @throws NetworkFailureException
     */
    public void requestTableList() throws NetworkFailureException {
        //Request table list
        Console.logn("Creation d'un Request de list des users");
        RequestListTableMessage reqTabList = new RequestListTableMessage();
        localClient.send(reqTabList);
    }

    /**
     * Envoi d'une requête pour avoir des informations détaillées d'un profil
     * @param u
     * @throws NetworkFailureException
     * @throws ProfileNotFoundOnServerException
     */
    public void consultProfile(UserLight u) throws NetworkFailureException, ProfileNotFoundOnServerException {
        Console.log("Creation d'un Request Profile message\n");
        RequestProfileMessage reqProf = new RequestProfileMessage(u);
        localClient.send(reqProf);
    }

    /**
     * Envoi d'une table à créer sur le serveur
     * @throws NetworkFailureException
     * @throws TooManyTablesException
     */
    public void createTable(UserLight maker, Table tabletoCreate) throws NetworkFailureException, TooManyTablesException {
        Console.log("Creation d'un Send New Table message\n");
        CreateTableMessage createTableMsg = new CreateTableMessage(maker, tabletoCreate);
        localClient.send(createTableMsg);
    }

    /**
     * Envoi du profil modifié de l'user au serveur
     * @param userLocal
     * @throws NetworkFailureException
     */
    public void updateProfile(User userLocal) throws NetworkFailureException {
        Console.log("Creation d'un Update Profile message\n");
        UpdateProfileMessage reqProf = new UpdateProfileMessage(userLocal);
        localClient.send(reqProf);
    }

    public void leaveRoom(UserLight userLocal) throws NetworkFailureException {

    }

    public void joinTable(UserLight userLocal, int IdTable) throws NetworkFailureException, FullTableException {

    }

    /**
     * Envoyer le heartbeat pour ne pas se faire déco
     * @throws NetworkFailureException
     */
    public void heartBeat() throws NetworkFailureException {
        Console.log("Creation d'un Heartbeat message\n");
        HeartbeatMessage hBeat = new HeartbeatMessage();
        localClient.send(hBeat);
    }

    public void sendAction(Action act, UserLight userLocal) throws NetworkFailureException, IncorrectActionException {

    }

    public void leaveTable(UserLight userLocal, int IdTable) throws NetworkFailureException {

    }


    public void requestLogGame(UserLight userLocal) throws NetworkFailureException {

    }

    public void launchSavedGame() throws NetworkFailureException, IncorrectFileException {

    }

    public void sendPacket() throws NetworkFailureException {

    }


    public void requestUserStats(UserLight userLocal) throws NetworkFailureException {

    }

    public void queryNextStepReplay() throws NetworkFailureException {

    }

    public void askStopGame() throws NetworkFailureException {

    }

    public void requestPlayGame(UserLight userLocal, Table activeTable) throws NetworkFailureException {

    }
    /**
     * Envoi d'une notification de déconnexion
     * @throws NetworkFailureException
     */
    public void notifyDisconnection(User maker) throws NetworkFailureException {
        Console.log("Creation d'un notifyDisconnection message\n");
        NotifyDisconnectionMessage NotifyD = new NotifyDisconnectionMessage(maker);
        localClient.send(NotifyD);
    }

}