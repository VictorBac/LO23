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

    public void vote(){

    }

    public void saveGame() {

    }


    public void sendMessage(MessageChat message){

    }

    public void playGame(UUID idTable) {
        //dManagerClient.getInterToCom().requestPlayGame();
    }


    public void confirmationCardReceived() {

    }

    public void replayAction(Action action, UserLight player) {

    }

    public void confirmationActionReceived(Action action) {

    }

    public void confirmationEndTurn() {

    }


    public void transmitRequest(){

    }
}
