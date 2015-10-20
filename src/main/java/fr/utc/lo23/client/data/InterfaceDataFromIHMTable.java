package fr.utc.lo23.client.data;

/**
 * Created by Haroldcb on 20/10/2015.
 */
public interface InterfaceDataFromIHMTable {
    public void logUser(String login, String password);
    public void addContact(UserLight contact, category);
    public void deleteContact(UserLight contact);
    public void createContactList(String name);
    public void deleteContactList(String name);
    public void contactAddedNotificationReceived(UserLight contact);
    public void saveLogGame(Table table);
    public void tableToCreate(Table table);
    public void playGame(int idTable);
    public void askStopGame();
    public void saveGame();
    public void saveLogGame(Table table);
    public void getUser(Userlight user);
    public void confirmationCardRecieved();
    public void replayAction(Action action);
    public void confirmationActionRecieved(Action action);
    public void confirmationEndTurn();
}
