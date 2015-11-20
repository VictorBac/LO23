package fr.utc.lo23.client.data;

import fr.utc.lo23.common.data.*;

import java.util.ArrayList;

/**
 * Created by Mar on 20/10/2015.
 *
 * This is the interface which will be used by the Network module on the client's side
 */
public interface InterfaceDataFromCom {

    /**
     * Method to add new statistics to the local user
     * @param statsLocalUser stats that need to be added into the user stats data
     */
    public void updateStats(Stats statsLocalUser);

    /**
     * Method to update the list of UserLight connected with a new Remote userLight
     * @param userLightDistant new Userlight that need to be add
     */
    public void remoteUserConnected(UserLight userLightDistant);

    /**
     * Method to update the list of UserLight disconnected  removing a userLight
     * @param userLightDistant UserLight that need to be reomved
     */
    public void remoteUserDisonnected(UserLight userLightDistant);

    /**
     *  Method to update the Table List with a new Table created on the server
     * @param tableCreatedOnServer new Table that was created on the server
     */
    public void notifyNewTable(Table tableCreatedOnServer);

    /**
     * ?????
     */
    public void userJoinedTable();

    /**
     * ?????
     * @param userLightDistant
     */
    public void addPlayer(UserLight userLightDistant);

    /**
     * Method to notify that a user left the game
     * @param userLightDistant UserLight of a user who left the Game
     */
    public void transmitLeaveGame(UserLight userLightDistant);

    /**
     * Method that return  a UserLightList that contain all UserLight that the Local user has locally
     * @return a UserLightList that contain an array with the list of UserLight
     */
    public UserLightList getPlayerList();

    /**
     * Method to confirm that the local user has actually correctly join the table
     * @param tableLocalUserJoined the Table the User joined
     * @param modeUserLocal the mode which he has chosen to adopt spectator or player
     */
    public void tableJoinAccepted(Table tableLocalUserJoined, String modeUserLocal);

    /**
     * Method to keep locally the list of UserLight connected to the server
     * @param listUserLightConnectedOnServer ArrayList of  UserLight connected on the server
     */
    public void currentConnectedUser(ArrayList<UserLight> listUserLightConnectedOnServer);

    /**
     * Method to keep locally the list of Table currently on the Server
     * @param tableListOnServer a TableList that contains an ArrayList of Table on the server
     */
    public void currentTables (TableList tableListOnServer);

    /**
     * Method to get the UserLight of the local user
     * @return UserLight that corresponds to the local user
     */
    public UserLight getUserLightLocal();

    /**
     * Method to keep the local player hand locally
     * @param playerHandUserLocal  PlayerHand of the local User
     */
    public void stockCards(PlayerHand playerHandUserLocal);

    /**
     * Method to propose for the local user the lis of Action that he can do
     * @param listActionPossibleForUserLocal ArrayList of Action that are possible to do for this turn (not the object Turn)
     */
    public void askAction(ArrayList<Action> listActionPossibleForUserLocal );

    /**
     * Method to notify the local User of the Action of a Player
     * @param action Action that a user played
     * @param userLight UserLight of the user who played
     */
    public void notifyAction(Action action, UserLight userLight);

    /**
     * Method to inform the local user that the Turn ended with a list of winner and the list of points they earned
     * @param listWinner ArrayList of UserLight that won
     * @param listGain ArrayList of Integer with the point that each one earned ordered in the same order as the list of winner
     */
    public void informEndTurn( ArrayList<UserLight> listWinner, ArrayList<Integer> listGain );


    /**
     * Method to save a game
     * @param table : table on which the game was played
    */
    public void saveLogGame(Table table);

}
