package fr.utc.lo23.client.data;

import fr.utc.lo23.common.data.*;
import fr.utc.lo23.common.data.Table;

import java.util.UUID;

/**
 * This is the interface which will be used by the IHMTable module on the client's side
 * Created by Haroldcb on 21/10/2015.
 */
public class InterfaceFromIHMTable implements InterfaceDataFromIHMTable {

    public void addContact(UserLight contact, Groupe category) {

    }

    public void deleteContact(UserLight contact) {

    }

    public void createContactList(String name) {

    }

    public void deleteContactList(String name) {

    }

    public void contactAddedNotificationReceived(UserLight contact) {

    }

    public void tableToCreate(Table table) {

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
