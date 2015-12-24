package fr.utc.lo23.client.network;

import fr.utc.lo23.client.data.DataManagerClient;
import fr.utc.lo23.client.data.InterfaceDataFromCom;
import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.*;
import fr.utc.lo23.common.network.*;
import fr.utc.lo23.exceptions.network.*;
import fr.utc.lo23.server.data.InterfaceServerDataFromCom;

import java.net.InetAddress;
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

    @Override
    public void requestLoginServer(User u, String socketIp, int socketPort) throws Exception {

        try
        {
            serverLink.connect(socketIp, socketPort);
        }
        catch (Exception e)
        {
            serverLink.reset();
            throw e;
        }
        //Send the login request to the server
        RequestLoginMessage reqLog = new RequestLoginMessage(u);
        //Console.log("requestelog"+reqLog.getUser().toString());
        serverLink.send(reqLog);
        Console.log("RequestLoginMessage envoyé");
    }

    @Override
    public void requestUserList() throws NetworkFailureException {
        //Request user list
        Console.logn("Creation d'un Request de list des users");
        RequestListUserMessage reqUseList = new RequestListUserMessage();
        serverLink.send(reqUseList);
    }

    @Override
    public void requestTableList() throws NetworkFailureException {
        //Request table list
        Console.logn("Creation d'un Request de list des users");
        RequestListTableMessage reqTabList = new RequestListTableMessage();
        serverLink.send(reqTabList);
    }

    @Override
    public void consultProfile(UserLight u) throws NetworkFailureException, ProfileNotFoundOnServerException {
        Console.log("Creation d'un Request Profile message\n");
        RequestProfileMessage reqProf = new RequestProfileMessage(u);
        serverLink.send(reqProf);
    }

    @Override
    public void createTable(UserLight maker, Table tabletoCreate) throws NetworkFailureException, TooManyTablesException {
        Console.log("Creation d'un Send New Table message\n");
        CreateTableMessage createTableMsg = new CreateTableMessage(maker, tabletoCreate);
        serverLink.send(createTableMsg);
    }

    @Override
    public void updateProfile(User userLocal) throws NetworkFailureException {
        Console.log("Creation d'un Update Profile message\n");
        UpdateProfileMessage reqProf = new UpdateProfileMessage(userLocal);
        serverLink.send(reqProf);
    }

    @Override
    public void joinTable(UserLight userLocal, UUID tableToJoin, EnumerationTypeOfUser mode) throws NetworkFailureException, FullTableException {
        Console.log("Tentative de connection à une table");
        RequestJoinTableMessage requestJoinTableMes = new RequestJoinTableMessage(userLocal,tableToJoin,mode);
        serverLink.send(requestJoinTableMes);
    }

    @Override
    public void sendHeartbeat() throws NetworkFailureException {
        //Console.log("Creation d'un Heartbeat message\n");
        HeartbeatMessage message = new HeartbeatMessage();
        serverLink.send(message);
    }

    @Override
    public void sendAction(Action act, UUID IdTable) throws NetworkFailureException, IncorrectActionException {
        SendActionMessage actMsg = new SendActionMessage(act, IdTable);
        serverLink.send(actMsg);
    }

    @Override
    public void leaveTable(UserLight userLocal, UUID IdTable, EnumerationTypeOfUser type) throws NetworkFailureException {
        LeaveTableMessage leaveT = new LeaveTableMessage(userLocal,IdTable,type);
        serverLink.send(leaveT);
    }

    @Override
    public void requestLogGame(UserLight userLocal) throws NetworkFailureException {
        RequestLogGameMessage logM = new RequestLogGameMessage(userLocal);
        serverLink.send(logM);
    }

    @Override
    public void sendMessage(MessageChat message, UUID tableId) {
        SendChatMessageMessage messageToSend = new SendChatMessageMessage(this.getDataInstance().getUserLightLocal(),message,tableId);
        serverLink.send(messageToSend);
    }

    @Override
    public void confirmationCardReceived(UserLight ul) {
        NotifyCardReceivedMessage cardReceivedMessage = new NotifyCardReceivedMessage(ul);
        serverLink.send(cardReceivedMessage);
    }

    @Override
    public void confirmationEndTurn(UserLight ul) {
        //TODO vu que l'interface est côté serveur, voir si on en a toujours besoin ici
        //NotifyEndTurnMessage endMsg = new NotifyEndTurnMessage(ul);
        //localClient.send(endMsg);
    }

    @Override
    public void sendMoneyAmount(UUID table, UserLight player, Integer money) {
        SetMoneyAmountMessage setMoneyAmountM = new SetMoneyAmountMessage(table, player, money);
        serverLink.send(setMoneyAmountM);
    }

    @Override
    public void notifyAnswerAskReadyGame(UUID table, UserLight player, boolean answer) {
        AnswerIfReadyGameMessage answerM = new AnswerIfReadyGameMessage(table, player, answer);
        System.out.println("Envoi ready à server");
        serverLink.send(answerM);
    }

    @Override
    public void LaunchGame(UUID idTable, UserLight userInit) throws NetworkFailureException {
        Console.log("Creation d'un LaunchGame message\n");
        LaunchGameMessage LGMess = new LaunchGameMessage(idTable,userInit);
        serverLink.send(LGMess);
    }

    @Override
    public void notifyDisconnection(User maker) throws NetworkFailureException {
        Console.log("Creation d'un notifyDisconnection message\n");
        NotifyDisconnectionMessage NotifyD = new NotifyDisconnectionMessage(maker);
        serverLink.send(NotifyD);
    }

     /* =========================================== EMPTY METHODS =========================================== */

    @Override
    public void sendProfile(User u) throws NetworkFailureException {

    }

    @Override
    public void launchSavedGame() throws NetworkFailureException, IncorrectFileException {

    }

    @Override
    public void sendPacket() throws NetworkFailureException {

    }

    @Override
    public void requestUserStats(UserLight userLocal) throws NetworkFailureException {

    }

    @Override
    public void askStopGame() throws NetworkFailureException {

    }

    @Override
    public void requestPlayGame(UserLight userLocal, UUID tableId) throws NetworkFailureException {

    }

    @Override
    public void transmitRequestServer(UserLight player) {

    }

    @Override
    public void replayAction(Action action, UserLight player) {

    }
}