package fr.utc.lo23.client.network;

import fr.utc.lo23.client.data.InterfaceDataFromCom;
import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.*;
import fr.utc.lo23.common.network.*;
import fr.utc.lo23.exceptions.network.*;
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
    public void consultProfile(UserLight u) throws ProfileNotFoundOnServerException {
        Console.log("Creation d'un Request Profile message\n");
        RequestProfileMessage reqProf = new RequestProfileMessage(u);
        serverLink.send(reqProf);
    }

    @Override
    public void createTable(UserLight maker, Table tabletoCreate) throws TooManyTablesException {
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
    public void joinTable(UserLight userLocal, UUID tableToJoin, EnumerationTypeOfUser mode) throws FullTableException {
        Console.log("Tentative de connection à une table");
        RequestJoinTableMessage requestJoinTableMes = new RequestJoinTableMessage(userLocal,tableToJoin,mode);
        serverLink.send(requestJoinTableMes);
    }

    @Override
    public void sendHeartbeat() throws NetworkFailureException {
        HeartbeatMessage message = new HeartbeatMessage();
        serverLink.send(message);
    }

    @Override
    public void sendAction(Action act, UUID idTable) throws IncorrectActionException {
        SendActionMessage actMsg = new SendActionMessage(act, idTable);
        serverLink.send(actMsg);
    }

    @Override
    public void leaveTable(UserLight userLocal, UUID idTable, EnumerationTypeOfUser type) throws NetworkFailureException {
        LeaveTableMessage leaveT = new LeaveTableMessage(userLocal,idTable,type);
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
        Console.log("Envoi ready à server");
        serverLink.send(answerM);
    }

    @Override
    public void addNewGameToTable(UUID idTable, UserLight userLight) {
        Console.log("Add new game\n");
        AddGameToTableMessage agttMessage = new AddGameToTableMessage(idTable, userLight);
        serverLink.send(agttMessage);
    }

    @Override
    public void LaunchGame(UUID idTable, UserLight userInit) throws NetworkFailureException {
        Console.log("Creation d'un LaunchGame message\n");
        LaunchGameMessage lgMess = new LaunchGameMessage(idTable,userInit);
        serverLink.send(lgMess);
    }

    @Override
    public void notifyDisconnection(User maker) throws NetworkFailureException {
        Console.log("Creation d'un notifyDisconnection message\n");
        NotifyDisconnectionMessage notifyD = new NotifyDisconnectionMessage(maker);
        serverLink.send(notifyD);
    }

     /* =========================================== EMPTY METHODS =========================================== */

    @Override
    public void sendProfile(User u) throws NetworkFailureException {
        //To be continued
    }

    @Override
    public void launchSavedGame() throws IncorrectFileException {
        //To be continued
    }

    @Override
    public void sendPacket() throws NetworkFailureException {
        //To be continued
    }

    @Override
    public void requestUserStats(UserLight userLocal) throws NetworkFailureException {
        //To be continued
    }

    @Override
    public void askStopGame() throws NetworkFailureException {
        //To be continued
    }

    @Override
    public void requestPlayGame(UserLight userLocal, UUID tableId) throws NetworkFailureException {
        //To be continued
    }

    @Override
    public void transmitRequestServer(UserLight player) {
        //To be continued
    }

    @Override
    public void replayAction(Action action, UserLight player) {
        //To be continued
    }
}