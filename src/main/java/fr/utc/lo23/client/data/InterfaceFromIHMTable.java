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
    public void tableToCreate(String name, boolean acceptSpectator, boolean acceptChatSpectator, int nbPlayerMax, int nbPlayerMin, boolean abandonAmiable, int maxMise, int timeforAction) {
        Table table = new Table(name, acceptSpectator, acceptChatSpectator, nbPlayerMax, nbPlayerMin, abandonAmiable, maxMise, timeforAction);
    }

    public void playGame(UUID idTable) {

    }

    public void askStopGame() {

    }

    public void saveGame() {

    }


    public void getUser(UserLight user) {

    }

    public void confirmationCardRecieved() {

    }

    public void replayAction(Action action) {

    }

    public void confirmationActionRecieved(Action action) {

    }

    public void confirmationEndTurn() {

    }
}
