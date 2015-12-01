package fr.utc.lo23.server.data;

import fr.utc.lo23.common.data.*;
import fr.utc.lo23.common.data.exceptions.*;

import java.util.ArrayList;
import java.util.UUID;

//A mettre c�t� server, les autres classes vont dans common data
public interface InterfaceServerDataFromCom {


    UserLight userConnection(User connectingUser) throws ExistingUserException;

    ArrayList<UserLight> getConnectedUsers();

    ArrayList<Table> getTableList();

    void createTable(UserLight maker, Table newTb);

    void updateTableList();

    boolean canJoinTableUser(UserLight joiner, Table wantedTable, EnumerationTypeOfUser mode);

    void validateMessage(UserLight sender, MessageChat msgSent);

    Game sendLogGame(UserLight player);

    Game startGame(UUID idTable, UserLight player);

    void nextStepReplay();

    void deletePlayer(UserLight deletedUsr) throws fr.utc.lo23.common.data.exceptions.UserNotFoundException;

    void confirmationCardReceived(UserLight player);

    Action replyAction(Action playedAction, UserLight player);

    void confirmationActionReceived(UserLight sender);

    void endTurnConfirmation(UserLight player);

    User getProfile(UserLight core);

    void updateProfile(User newUser);

    User getUserById(UUID idUser);
}
