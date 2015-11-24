package fr.utc.lo23.client.network;

import fr.utc.lo23.client.data.InterfaceDataFromCom;
import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.Action;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.common.network.RequestListTableMessage;
import fr.utc.lo23.common.network.RequestListUserMessage;
import fr.utc.lo23.common.network.RequestLoginMessage;
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
    public void RequestUserList() throws NetworkFailureException {
        //Request user list
        Console.logn("Creation d'un Request de list des users");
        RequestListUserMessage reqUseList = new RequestListUserMessage();
        localClient.send(reqUseList);
    }

    /**
     * Request la liste des tables actives
     * @throws NetworkFailureException
     */
    public void RequestTableList() throws NetworkFailureException {
        //Request table list
        Console.logn("Creation d'un Request de list des users");
        RequestListTableMessage reqTabList = new RequestListTableMessage();
        localClient.send(reqTabList);
    }

    public void consultProfile(UserLight u) throws NetworkFailureException, ProfileNotFoundOnServerException {

    }

    public void createTable() throws NetworkFailureException, TooManyTablesException {

    }

    public void updateProfile(User userLocal) throws NetworkFailureException {

    }

    public void leaveRoom(UserLight userLocal) throws NetworkFailureException {

    }

    public void joinTable(UserLight userLocal, int IdTable) throws NetworkFailureException, FullTableException {

    }

    public void hearthBeat() throws NetworkFailureException {

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
}
