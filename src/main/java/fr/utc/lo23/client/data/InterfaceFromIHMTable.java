package fr.utc.lo23.client.data;

import fr.utc.lo23.common.data.*;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.exceptions.network.NetworkFailureException;

import java.util.UUID;

/**
 * This is the interface which will be used by the IHMTable module on the client's side
 * Created by Haroldcb on 21/10/2015.
 */
public class InterfaceFromIHMTable implements InterfaceDataFromIHMTable {

    private DataManagerClient dManagerClient;

    public InterfaceFromIHMTable(DataManagerClient dManagerClient) {

        this.dManagerClient = dManagerClient;
    }

    //TODO handle exceptions
    public void tableToCreate(Table table){
        //dManagerClient.getInterToCom().createTable(table);
    }

    /**
     * forwards the request to communication module : askStopGame()
     */
    public void askStopGame() {
        try {
            dManagerClient.getInterToCom().askStopGame();
        } catch (NetworkFailureException e) {
            e.printStackTrace();
        }
    }

    //TODO Com must implement vote()
    public void vote(boolean answer){
        //dManagerClient.getInterToCom().vote(answer);
    }

    public void saveGame() {

    }

    //TODO Com must implement sendMessage(MessageChat message)
    public void sendMessage(MessageChat message){
        //dManagerClient.getInterToCom().sendMessage(message);
    }

    //TODO Com must implement sendMessage(MessageChat message)
    public void playGame(UUID idTable) {
        //dManagerClient.getInterToCom().requestPlayGame();
    }

    //TODO Com must implement confirmationCardReceived()
    public void confirmationCardReceived() {
        //dManagerClient.getInterToCom().confirmationCardReceived();
    }

    //TODO Com must implement replayAction(Action action, UserLight player)
    public void replayAction(Action action, UserLight player) {
        //dManagerClient.getInterToCom().replayAction(action, player);
    }

    //TODO Com must implement confirmationActionReceived(Action action)
    public void confirmationActionReceived(Action action) {
        //dManagerClient.getInterToCom().confirmationActionReceived(action);
    }

    //TODO Com must implement confirmationEndTurn()
    public void confirmationEndTurn() {
        //dManagerClient.getInterToCom().confirmationEndTurn();
    }

    //TODO Com must implement transmitRequestServer(UserLight)
    //TODO Add UserLight in param? transmitRequest(UserLight player)
    public void transmitRequest(){
        //dManagerClient.getInterToCom().transmitRequestServer(player);
    }


    public User getUser(){
        return dManagerClient.getUserLocal();
    }
}
