package fr.utc.lo23.server.network;

import com.sun.corba.se.spi.activation.ActivatorOperations;
import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.Params;
import fr.utc.lo23.common.data.*;
import fr.utc.lo23.common.network.*;
import fr.utc.lo23.exceptions.network.NetworkFailureException;
import fr.utc.lo23.server.data.InterfaceServerDataFromCom;
import fr.utc.lo23.server.ihm_main.interfaces.InterMain;
import fr.utc.lo23.server.ihm_main.interfaces.ServerWindowInterface;
import fr.utc.lo23.server.network.threads.PokerServer;

import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author Jean-Côme
 */
public class NetworkManagerServer implements InterfaceServer,InterfaceComToMain{
    /* Modules instance, initiate by IHM module with setters */
    private InterfaceServerDataFromCom dataInstance;
    private ServerWindowInterface interMain;

    /* Attributes */
    private PokerServer server = null;
    private Integer listeningPort = null;

    /* =========================================== METHODES =========================================== */
    public NetworkManagerServer() {
        listeningPort = Params.DEFAULT_SERVER_PORT;
    }

    /* == GETTERS AND SETTERS == */
    public InterfaceServerDataFromCom getDataInstance() {
        return dataInstance;
    }

    public void setDataInstance(InterfaceServerDataFromCom dataInstance) {
        this.dataInstance = dataInstance;
    }

    public ServerWindowInterface getInterMain() {
        return interMain;
    }

    public void setInterMain(InterMain interMain) {
        this.interMain = interMain;
    }

    /* == METHODES IMPLEMENTATION == */
    public void start(int portToListen) {
        if(dataInstance == null)Console.log("Interface data non set dans network");
        try {
            server = new PokerServer(this, listeningPort);
        }
        catch (Exception e){
            Console.log(e.getMessage());
        }
        server.start();
    }

    public void stop() {
        try{
            server.shutdown();
        }
        catch (Exception e){
            Console.log(e.getMessage());
        }

    }

    public void sendTableList(ArrayList<Table> tableList) throws NetworkFailureException {

    }

    public void sendPlayers(ArrayList<UserLight> userList) throws NetworkFailureException {

    }

    public void sendLogGame() throws NetworkFailureException {

    }

    public void notifyNewPlayer(UserLight userDistant) throws NetworkFailureException {
        NotifyNewPlayerMessage newPMessage = new NotifyNewPlayerMessage(userDistant);
        server.sendToAll(newPMessage);
    }

    @Override
    public void notifyOtherPlayerAction(ArrayList<UserLight> tablePlayers, Action act) throws NetworkFailureException {
        NotifyOtherPlayerAction notifyActMsg = new NotifyOtherPlayerAction(act);
        //tablePlayers.remove(act.getUserLightOfPlayer());//Le joueur connait déjà son action, pas besoin de le notifier ? //TODO vérifier
        server.sendToListOfUsers(tablePlayers, notifyActMsg);
    }

    public void notifyNewTable(Table newTable) throws NetworkFailureException {
        NotifyNewTableMessage notifNewTable = new NotifyNewTableMessage(newTable);
        server.sendToAll(notifNewTable);
    }

    public void notifyDisconnection(User distantUser) throws NetworkFailureException {
        NotifyDisconnectionMessage NotifyD = new NotifyDisconnectionMessage(distantUser);
        server.sendToAll(NotifyD);
    }

    public void sendChatPacket() throws NetworkFailureException {

    }

    public void startGame(Table tableToStart) throws NetworkFailureException {

    }

    @Override
    public void newRound(ArrayList<UserLight> aPlayers) {
        NotifyNewRoundMessage newR = new NotifyNewRoundMessage();
        server.sendToListOfUsers(aPlayers, newR);
    }

    @Override
    public void newTurn(ArrayList<UserLight> aPlayers) {
        NotifyNewTurnMessage newT = new NotifyNewTurnMessage();
        server.sendToListOfUsers(aPlayers, newT);
    }

    @Override
    public void endRound(ArrayList<UserLight> aPlayers, ArrayList<Seat> aSeat) {
        NotifyEndRoundMessage endR = new NotifyEndRoundMessage(aSeat);
        server.sendToListOfUsers(aPlayers, endR);
    }

    @Override
    public void endTurn(ArrayList<UserLight> aPlayers, Integer pot) {
        NotifyEndTurnMessage endT = new NotifyEndTurnMessage(pot);
        server.sendToListOfUsers(aPlayers, endT);
    }

    @Override
    public void askIfReady(ArrayList<UserLight> aPlayers) {
        AskReadyGameMessage askReadyM = new AskReadyGameMessage();
        server.sendToListOfUsers(aPlayers, askReadyM);
    }

    @Override
    public void transmitAction(Action a,ArrayList<UserLight> aPlayers,UserLight user){
        TransmitActionMessage message = new TransmitActionMessage(a, user);
        server.sendToListOfUsers(aPlayers,message);
    }

    @Override
    public void askActionToPLayer(ArrayList<UserLight> aPlayers, Action a, EnumerationAction[] enAct) {
        //Envoyer la demande d'action au joueur lui-même
        AskActionMessage askActM = new AskActionMessage(a, enAct);
        server.sendToListOfUsers(aPlayers,askActM);
    }

    @Override
    public void sendLogGame(Game LogGame) throws NetworkFailureException {

    }


    @Override
    public void sendCards(ArrayList<UserLight> aPlayers,ArrayList<PlayerHand> ph) throws NetworkFailureException {

        SendCardMessage cardMessage = new SendCardMessage(ph);

        server.sendToListOfUsers(aPlayers,cardMessage);


    }
}
