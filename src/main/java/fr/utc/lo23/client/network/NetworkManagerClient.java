package fr.utc.lo23.client.network;

import fr.utc.lo23.client.data.DataManagerClient;
import fr.utc.lo23.client.data.InterfaceDataFromCom;
import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.*;
import fr.utc.lo23.common.network.*;
import fr.utc.lo23.exceptions.network.*;
import fr.utc.lo23.server.data.InterfaceServerDataFromCom;

import java.util.UUID;

/**
 *
 * @author Jean-Côme
 */

/* Modules instance, initiate by IHM module with setters */
public class NetworkManagerClient implements InterfaceClient  {

    /* Attributes */
    private InterfaceDataFromCom dataInstance;
    private ServerLink serverLink;

    /* =========================================== METHODES =========================================== */
    public NetworkManagerClient(InterfaceDataFromCom dataInter) {
        this.dataInstance = dataInter;
        serverLink = new ServerLink(this);
        serverLink.start();
    }

    /* == GETTERS AND SETTERS == */
    public InterfaceDataFromCom getDataInstance() {
        return dataInstance;
    }
    public void setDataInstance(InterfaceDataFromCom dataInstance) {
        this.dataInstance = dataInstance;
    }

    /* == METHODES IMPLEMENTATION == */
    /**
     * Envoi la reclamation de connexion du client.
     * @param u
     * @param socketIp
     * @param socketPort
     * @throws NetworkFailureException
     */
    public void requestLoginServer(User u, String socketIp, int socketPort){
        try {
            serverLink.connect(socketIp, socketPort);
        } catch (Exception e) {
            Console.err("La connection au serveur a echoué\n");
            e.printStackTrace();
        }
        //Send the login request to the server
        RequestLoginMessage reqLog = new RequestLoginMessage(u);
        //Console.log("requestelog"+reqLog.getUser().toString());
        serverLink.send(reqLog);
        Console.log("RequestLoginMessage envoyé");
    }

    /**
     * Request de la liste des users connectes
     * @throws NetworkFailureException
     */
    public void requestUserList() throws NetworkFailureException {
        //Request user list
        Console.logn("Creation d'un Request de list des users");
        RequestListUserMessage reqUseList = new RequestListUserMessage();
        serverLink.send(reqUseList);
    }

    /**
     * Request la liste des tables actives
     * @throws NetworkFailureException
     */
    public void requestTableList() throws NetworkFailureException {
        //Request table list
        Console.logn("Creation d'un Request de list des users");
        RequestListTableMessage reqTabList = new RequestListTableMessage();
        serverLink.send(reqTabList);
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
        serverLink.send(reqProf);
    }

    /**
     * Envoi d'une table à créer sur le serveur
     * @throws NetworkFailureException
     * @throws TooManyTablesException
     */
    public void createTable(UserLight maker, Table tabletoCreate) throws NetworkFailureException, TooManyTablesException {
        Console.log("Creation d'un Send New Table message\n");
        CreateTableMessage createTableMsg = new CreateTableMessage(maker, tabletoCreate);
        serverLink.send(createTableMsg);
    }

    /**
     * Envoi du profil modifié de l'user au serveur
     * @param userLocal
     * @throws NetworkFailureException
     */
    public void updateProfile(User userLocal) throws NetworkFailureException {
        Console.log("Creation d'un Update Profile message\n");
        UpdateProfileMessage reqProf = new UpdateProfileMessage(userLocal);
        serverLink.send(reqProf);
    }

    public void joinTable(UserLight userLocal, UUID tableToJoin, EnumerationTypeOfUser mode) throws NetworkFailureException, FullTableException {
        Console.log("Tentative de connection à une table");
        RequestJoinTableMessage requestJoinTableMes = new RequestJoinTableMessage(userLocal,tableToJoin,mode);
        serverLink.send(requestJoinTableMes);
    }

    /**
     * Envoi un heartbeat pour maintenir la connection
     * @throws NetworkFailureException
     */
    public void sendHeartbeat() throws NetworkFailureException {
        //Console.log("Creation d'un Heartbeat message\n");
        HeartbeatMessage message = new HeartbeatMessage();
        serverLink.send(message);
    }

    /**
     * l'action effectuée par le joueur est envoyée sur le serveur
     * @param act
     * @throws NetworkFailureException
     * @throws IncorrectActionException
     */
    public void sendAction(Action act) throws NetworkFailureException, IncorrectActionException {
        SendActionMessage actMsg = new SendActionMessage(act);
        serverLink.send(actMsg);
    }

    public void leaveTable(UserLight userLocal, UUID IdTable) throws NetworkFailureException {
        LeaveTableMessage leaveT = new LeaveTableMessage(userLocal,IdTable);
        serverLink.send(leaveT);
    }

    public void requestLogGame(UserLight userLocal) throws NetworkFailureException {
        RequestLogGameMessage logM = new RequestLogGameMessage(userLocal);
        serverLink.send(logM);
    }

    public void sendMessage(MessageChat message, UUID tableId) {
        SendChatMessageMessage messageToSend = new SendChatMessageMessage(this.getDataInstance().getUserLightLocal(),message,tableId);
        serverLink.send(messageToSend);
    }

    public void confirmationCardReceived(UserLight ul) {
        NotifyCardReceivedMessage cardReceivedMessage = new NotifyCardReceivedMessage(ul);
        serverLink.send(cardReceivedMessage);
    }

    public void confirmationEndTurn(UserLight ul) {
        //TODO vu que l'interface est côté serveur, voir si on en a toujours besoin ici
        //NotifyEndTurnMessage endMsg = new NotifyEndTurnMessage(ul);
        //localClient.send(endMsg);
    }

    public void sendMoneyAmount(UUID table, UserLight player, Integer money) {
        SetMoneyAmountMessage setMoneyAmountM = new SetMoneyAmountMessage(table, player, money);
        serverLink.send(setMoneyAmountM);
    }

    public void notifyAnswerAskReadyGame(UUID table, UserLight player, boolean answer) {
        AnswerIfReadyGameMessage answerM = new AnswerIfReadyGameMessage(table, player, answer);
        serverLink.send(answerM);
    }

    public void LaunchGame(UUID idTable, UserLight userInit) throws NetworkFailureException {
        Console.log("Creation d'un LaunchGame message\n");
        LaunchGameMessage LGMess = new LaunchGameMessage(idTable,userInit);
        serverLink.send(LGMess);
    }

    /**
     * Envoi d'une notification de déconnexion
     * @throws NetworkFailureException
     */
    public void notifyDisconnection(User maker) throws NetworkFailureException {
        Console.log("Creation d'un notifyDisconnection message\n");
        NotifyDisconnectionMessage NotifyD = new NotifyDisconnectionMessage(maker);
        serverLink.send(NotifyD);
    }

     /* =========================================== EMPTY METHODES =========================================== */

    /**
     *
     * @param u
     * @throws NetworkFailureException
     */
    public void sendProfile(User u) throws NetworkFailureException {

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

    public void requestPlayGame(UserLight userLocal, UUID tableId) throws NetworkFailureException {

    }

    public void leaveRoom(UserLight userLocal) throws NetworkFailureException {

    }

    public void transmitRequestServer(UserLight player) {

    }

    public void replayAction(Action action, UserLight player) {

    }
}