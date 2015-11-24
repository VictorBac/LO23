package fr.utc.lo23.client.data;

import fr.utc.lo23.common.data.*;
import fr.utc.lo23.common.data.Table;

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

    //TODO envoyer table sur le serveur via interface com
    public void tableToCreate(Table table){
        
    }

    /**
     * forwards the request to communication module : askStopGame()
     */
    public void askStopGame() {

    }

    public void vote(){

    }

    public void saveGame() {

    }


    public void sendMessage(MessageChat message){

    }

    public void playGame(UUID idTable) {

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
