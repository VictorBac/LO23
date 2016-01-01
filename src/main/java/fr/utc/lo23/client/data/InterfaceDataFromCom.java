package fr.utc.lo23.client.data;

import fr.utc.lo23.common.data.*;
import fr.utc.lo23.common.data.Table;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Mar on 20/10/2015.
 *
 * This is the interface which will be used by the Network module on the client's side
 */
public interface InterfaceDataFromCom {

    /**
     * Method to add new statistics to the local user
     *
     * @param statsLocalUser stats that need to be added into the user stats data
     */
    void updateStats(Stats statsLocalUser);

    /**
     * Method to update the list of UserLight connected with a new Remote userLight
     *
     * @param userLightDistant new UserLight that need to be add
     */
    void remoteUserConnected(UserLight userLightDistant);

    /**
     * Method to update the list of UserLight disconnected  removing a userLight
     *
     * @param userLightDistant UserLight that need to be removed
     */
    void remoteUserDisonnected(UserLight userLightDistant);

    /**
     * Method to update the Table List with a new Table created on the server
     *
     * @param tableCreatedOnServer new Table that was created on the server
     */
    void notifyNewTable(Table tableCreatedOnServer);


    /**
     * Method to call when a remote user is connected on a specific Table,
     * Warning :  DO Not use it for local user (=userWhoJoinTheTable)
     *
     * @param idTable                Table on which the remote user connect to
     * @param userWhoJoinTheTable    the UserLight for the remote User
     * @param typeOfUserWhoJoinTable type of User Spectator/Player
     */
    void userJoinedTable(UUID idTable, UserLight userWhoJoinTheTable, EnumerationTypeOfUser typeOfUserWhoJoinTable);


    /**
     * Method to notify that a user left the game
     *
     * @param idTable                id of the table the user left
     * @param userLightLeavingGame       UserLight of a user who left a Game
     * @param typeOfUserWhoLeftTable the user is a spectator or a player
     */
    void transmitLeaveGame(UUID idTable, UserLight userLightLeavingGame, EnumerationTypeOfUser typeOfUserWhoLeftTable);


    /**
     * Method to confirm that the local user has actually correctly join the table
     *
     * @param idTableLocalUserJoined the id of the Table the User joined
     * @param modeUserLocal          an EnumerationTypeOfUser the mode which he has chosen to adopt spectator or player
     */
    void tableJoinAccepted(UUID idTableLocalUserJoined, EnumerationTypeOfUser modeUserLocal);

    /**
     * Method to notify that the local user is not able to join the table
     *
     * @param idTableLocalUserJoined the id of the Table the User has not joined
     * @param modeUserLocal          an EnumerationTypeOfUser the mode which he has chosen to adopt spectator or player
     */
    void tableJoinRefused(UUID idTableLocalUserJoined, EnumerationTypeOfUser modeUserLocal);

    /**
     * Method to keep locally the list of UserLight connected to the server
     *
     * @param listUserLightConnectedOnServer ArrayList of  UserLight connected on the server
     */
    void currentConnectedUser(ArrayList<UserLight> listUserLightConnectedOnServer);

    /**
     * Method to keep locally the list of Table currently on the Server
     *
     * @param listOfTableListOnServer ArrayList of Table from the server
     */
    void currentTables(ArrayList<Table> listOfTableListOnServer);

    /**
     * Method to get the UserLight of the local user
     *
     * @return UserLight that corresponds to the local user
     */
    UserLight getUserLightLocal();

    /**
     * Method to notify the player that the Game starts
     * @param idTable UUID of the Table the user is connected to
     */
    void startGame(UUID idTable);

    /**
     * Method that has to be called if the creator of a Table decide to start the Game but the server has rejected the request
     */
    void tableCreatorRequestToStartGameRejected();

    /**
     * Method that has to be called if the creator of a Table decide to start the Game and the server has accepted the request
     */
    void tableCreatorRequestToStartGameAccepted();

    /**
     * Method to ask the local Player for his money he is going to start with
     */
    void askAmountMoney();

    /**
     * Method that returns the confirmation from the server that its amount was valid
     * @param player User light of the local User (Player)
     * @param amount Integer amount asked by IHMTable, -1 if error >0 if valid
     */
    void notifyMoneyAmountAnswerFromServer(UserLight player, Integer amount);

    /**
     * Method used to ask to each player if he accept the money of each player and if he is ready to start the game
     */
    void askReadyGame();

    /**
     * Method to receive playersHand which contain the cards
     * If there is only one PlayerHand it corresponds either to the card of the local User or to the card of the field (if the UserLight is null)
     * If there are more than one PlayerHand then it corresponds to the cards of the other player
     * @param playerHandsOfTheGame ArrayList<PlayerHand> of the Game
     */
    void stockCards(ArrayList<PlayerHand> playerHandsOfTheGame);


    /**
     * Method to propose for the local user the list of Action that he can do, and use this list to fill the Action
     * @param actionEmpty an emptyAction that the Player has to fill
     * @param listActionPossibleForUserLocal Array of EnumerationAction that are possible to do for this turn (not the object Turn)
     */
    void askAction(Action actionEmpty, EnumerationAction[] listActionPossibleForUserLocal);

    /**
     * Method to notify the local User of the Action of a Player
     * @param action Action that a user played
     */
    void notifyAction(Action action);

    /**
     * Method to inform the user that a new Hand had started
     */
    void informNewHand();

    /**
     * Method to inform the user that a new turn had started
     */
    void informNewTurn();

    /**
     * Method to inform the local user that the Hand ended with a list of Seat, their amount of money has changed depending on how won
     * @param listSeat ArrayList of Seat with their latest amount of money
     * @param listOfPlayer ArrayList<PlayerHand>
     */
    void informEndHand(ArrayList<Seat> listSeat, ArrayList<PlayerHand> listOfPlayer);

    /**
     * Method to inform the local user that the Turn ended with the Pot at the end of Turn
     * @param potForThisTurn Integer containing the amount of money at the end of the Turn in the pot
     */
    void informEndTurn(Integer potForThisTurn);

    /**
     * Method to save a game
     * @param tableThatContainGameToSave : table on which the game was played
     */
    void saveLogGame(Table tableThatContainGameToSave);


    /**
     * Method to receive a message send by a remote UserLight to display it to the local user
     *
     * @param messageSendByRemoteUser MessageChat received by the local User
     */
    void transmitMessage(MessageChat messageSendByRemoteUser);

    /**
     * Method that allow the local User to retrieve the profile of a remote User
     *
     * @param profileReturnedByTheServer User of a remote User which contain the profile of the user
     */
    void remoteUserProfile(User profileReturnedByTheServer);


    /**
     * Method to call when the Game the user is playing on is stopped
     */
    void stopGame();

    /**
     * Method to notify the local User of the answer of remotePlayer concerning the AskReady
     * @param player UserLight of the remote User who voted
     * @param answer Boolean true if the player is Ready, false if not
     */
    void infosReadyAnswerFromOtherPlayer(UserLight player, Boolean answer);

    /**
     * Method to notify that a Table was destroyed on the server
     * @param idTableDestroyed  UUID of the Table which is destroyed
     */
    void deleteTable(UUID idTableDestroyed);

    /**
     * Method to notify the local User that the connection to the server was accepted
     */
    void acceptLogin();

    /**
     * Method to notify the local User that the connection to the server was refused
     */
    void refuseLogin(String reason);

    /**
     * Method which is called when a remote User has change his profile. User to inform other user of this modification
     * @param newProfileRemoteUser UserLight with its modification
     */
    void profileRemoteUserChange( UserLight newProfileRemoteUser);

    /**
     * Method to notify spectators that cards have been distributed among players
     */
    void notifyCardsReceived();
}