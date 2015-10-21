package fr.utc.lo23.client.data;

import fr.utc.lo23.common.data.Action;
import fr.utc.lo23.common.data.Groupe;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.common.data.UserLight;

import java.util.UUID;

/**
 * Created by Haroldcb on 21/10/2015.
 */
public class InterfaceFromIHMTable implements InterfaceDataFromIHMTable {

    /**
     * Constructeur
     */
    public InterfaceFromIHMTable() {
    }

    public void logUser(String login, String password) {

    }

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

    public void saveLogGame(Table table) {

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
