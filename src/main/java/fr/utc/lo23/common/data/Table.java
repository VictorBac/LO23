package fr.utc.lo23.common.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import fr.utc.lo23.common.data.exceptions.ExistingUserException;
import fr.utc.lo23.common.data.exceptions.TableException;

/**
 * Class representing a table, on which games are played
 * Created by Haroldcb on 21/10/2015.
 */
public class Table implements Serializable {
    /**
     * idTable : unique id of the table
     * name : name of the table
     * creator : UserLight who created the table (can launch game...)
     * acceptSpectator : true if accept, false otherwise
     * acceptChatSpectator : true if accept, false otherwise
     * nbPlayerMax : maximum number of players on the table
     * nbPlayerMin : minimum number of players on the table required to launch a game
     * listPlayers: list of players actually on the table
     * listSpectators : list of spectators
     * abandonAmiable : true if authorised, false otherwise
     * maxMise : maximum amount that players can give themselves at the beginning
     * listGames : list of games that were played on this table (last = actual)
     * timeForAction : time between actions
     * vote : stores the players' votes
     * nbPlayerSelectedMoney : number of player who have already selected their money
     */
    private UUID idTable;
    private String name;
    private UserLight creator;
    private boolean acceptSpectator;
    private boolean acceptChatSpectator;
    private int nbPlayerMax;
    private int nbPlayerMin;
    private UserLightList listPlayers;
    private UserLightList listSpectators;
    private boolean abandonAmiable;
    private ArrayList<Game> listGames;
    private int timeForAction;
    private static final long serialVersionUID = 1L;
    private ArrayList<Boolean> vote;
    private int nbPlayerSelectedMoney;

    /**
     * Constructor
     * @param name : name of the table
     * @param creator UserLight who created the table (can launch game...)
     * @param acceptSpectator : true if accept, false otherwise
     * @param acceptChatSpectator : true if accept, false otherwise
     * @param nbPlayerMax : maximum number of players on the table
     * @param nbPlayerMin : minimum number of players on the table
     * @param abandonAmiable : true if authorised, false otherwise
     * @param petiteBlinde : value of small blinde
     * @param timeForAction : time to play
     * @param ante : ante's value
     * @param startMoneyMax : start money
     */
    public Table(String name, UserLight creator, boolean acceptSpectator, boolean acceptChatSpectator, int nbPlayerMax, int nbPlayerMin, boolean abandonAmiable, int petiteBlinde, int timeForAction, int ante, int startMoneyMax){
        this.setIdTable(UUID.randomUUID());
        this.setName(name);
        this.setCreator(creator);
        this.setAcceptSpectator(acceptSpectator);
        this.setAcceptChatSpectator(acceptChatSpectator);
        this.listPlayers = new UserLightList();
        try {
            listPlayers.addUser(creator);
        } catch (ExistingUserException e) {
            e.printStackTrace();
        }
        this.listSpectators = new UserLightList();
        this.setNbPlayerMax(nbPlayerMax);
        this.setNbPlayerMin(nbPlayerMin);
        this.setAbandonAmiable(abandonAmiable);
        this.listGames = new ArrayList<Game>();
        //Add new waiting game to the list
        this.listGames.add(new Game(this, ante, petiteBlinde, startMoneyMax));
        this.setTimeForAction(timeForAction);
        this.vote = new ArrayList<Boolean>();
        nbPlayerSelectedMoney = 0;
    }

    /**
     * method to add a player to the table, with checking if it is possible
     * Check if it's possible (call checkConditionPlayerJoin())
     * @param player : player
     * @throws fr.utc.lo23.common.data.exceptions.TableException : throws exception if player can't be added
     */
    public void playerJoinTable(UserLight player) throws TableException {
        // number of players on the table < max number of players AND player not already in the table
        if (checkConditionPlayerJoin() && !this.listPlayers.getListUserLights().contains(player)){
            this.listPlayers.getListUserLights().add(player);
            //Remove : the player is not added yet to the Game this.getCurrentGame().addPlayer(player);
        }
        else {
            throw new TableException("Impossible to add this new player");
        }
    }

    /**
     * Delete player from the table
     * @param player : player who wants to leave table
     * @throws TableException : throws exception if player is not in the table
     */
    public void playerLeaveTable(UserLight player) throws  TableException{
        //if abandonAmiable = true and player already on the table
        if(this.listPlayers.getListUserLights().contains(player) && this.abandonAmiable){
            this.listPlayers.getListUserLights().remove(player);
            this.getCurrentGame().deletePlayer(player);
        }
        else if (!this.abandonAmiable){
            this.listPlayers.getListUserLights().remove(player);
            this.getCurrentGame().deletePlayer(player);
        }
        else{
            throw new TableException("Player you want to delete is not on the table!");
        }
    }

    /**
     * Check if a player can join a table
     * @return true if possible, false otherwise
     */
    public boolean checkConditionPlayerJoin(){
        // number of players on the table < max number of players
        return this.listPlayers.getListUserLights().size() < this.nbPlayerMax;
    }

    /**
     * Add a spectator to the table
     * Check if it's possible (call checkConditionSpectatorJoin())
     * @param spectator : spectator who wants to join the table
     * @throws TableException : throws exception if spectator can't be added
     */
    public void spectatorJoinTable(UserLight spectator) throws TableException {
        //  spectator not on the table yet
        if (checkConditionSpectatorJoin() && !this.listSpectators.getListUserLights().contains(spectator)){
            this.listSpectators.getListUserLights().add(spectator);
            this.getCurrentGame().addSpectator(spectator);
        }
        else {
            throw new TableException("Impossible to add this spectator");
        }
    }

    /**
     * Delete a spectator from the table
     * @param spectator : spectator who wants to leave the table
     * @throws TableException : throws exception if spectator is not on the table
     */
    public void spectatorLeaveTable(UserLight spectator) throws TableException{
        //spectator already on the table
        if(this.listSpectators.getListUserLights().contains(spectator)){
            this.listSpectators.getListUserLights().remove(spectator);
            this.getCurrentGame().deleteSpectator(spectator);
        }
        else{
            throw new TableException("Spectator you want to delete is not on the table!");
        }
    }

    /**
     * Check if a spectator can join the table
     * @return : true if spectator can join, false otherwise
     */
    public boolean checkConditionSpectatorJoin(){
        return isAcceptSpectator();
    }

    /**
     * Method to determine the type of User in a Table
     * @param userToDetermineType UserLight of the the user to determine his type spectator or player
     * @return player or spectator or null if not found
     */
    public EnumerationTypeOfUser determineTypeUser(UserLight userToDetermineType){
        EnumerationTypeOfUser userType =  null;
        if(listPlayers.getListUserLights().contains(userToDetermineType))
            userType = EnumerationTypeOfUser.PLAYER;
        else if (listSpectators.getListUserLights().contains(userToDetermineType))
            userType = EnumerationTypeOfUser.SPECTATOR;
        return userType;
    }

    /**
     * Add a new game in the table's games list
     * @param game : new game to add to the list
     * @throws TableException : throws exception if game can't be added
     */
    public void addNewGameToList(Game game) throws TableException {
        for(int i=0; i <listGames.size(); i++){
            //if a game in the list is already started, impossible to start a new game
            if(listGames.get(i).getStatusOfTheGame().equals(EnumerationStatusGame.Playing) || listGames.get(i).getStatusOfTheGame().equals(EnumerationStatusGame.Finished)){
                throw new TableException("Impossible to add a new game");
            }
        }
        this.listGames.add(game);
    }


    /**
     * Create a new game and add it in the table's games list
     * @param ante int ante used for this new Game
     * @param blind int blind used for this new Game
     * @param maxStartMoney int moneyMax a user can choose at the beginning
     * @throws TableException This exception is thrown if a Game is still on play then impossible to start a new game
     */
    public void addNewGameToList(int ante, int blind, int maxStartMoney) throws TableException {
        Game gameToAdd = new Game(this,ante, blind, maxStartMoney);
        for(int i=0; i <listGames.size(); i++){
            //if a game in the list is already started, impossible to start a new game
            if(listGames.get(i).getStatusOfTheGame().equals(EnumerationStatusGame.Playing) || listGames.get(i).getStatusOfTheGame().equals(EnumerationStatusGame.Finished)){
                throw new TableException("Impossible to start a new game");
            }
        }
        this.listGames.add(gameToAdd);
    }

    /**
     * Return the index of the game if it's in the tables list
     * @param game : game to check
     * @return : index of the game if contained, -1 if not
     */
    public int getIDTGame(Game game){
        return this.getListGames().indexOf(game);
    }


    /******************* GETTERS AND SETTERS ********************************/

    /**
     * get the table's id
     * @return : table's id
     */
    public UUID getIdTable() {
        return idTable;
    }

    /**
     * set the table's id
     * @param idTable : table's id
     */
    public void setIdTable(UUID idTable) {
        this.idTable = idTable;
    }

    /**
     * get table's name
     * @return : table's name
     */
    public String getName() {
        return name;
    }

    /**
     * set table's name
     * @param name : name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * return creator of the table
     * @return : table's creator
     */
    public UserLight getCreator() {return creator;}

    /**
     * set table's creator
     * @param creator : player who created the table
     */
    public void setCreator(UserLight creator) {this.creator = creator;}

    /**
     * return if accpet spectator is true
     * @return : true if yes, false otherwise
     */
    public boolean isAcceptSpectator() {
        return acceptSpectator;
    }

    /**
     * set accaptSpectator
     * @param acceptSpectator : value to set
     */
    public void setAcceptSpectator(boolean acceptSpectator) {
        this.acceptSpectator = acceptSpectator;
    }

    /**
     * return if acceptChatSpectator is true
     * @return : true or false
     */
    public boolean isAcceptChatSpectator() {
        return acceptChatSpectator;
    }

    /**
     * set acceptChatSpectator's value
     * @param acceptChatSpectator : value to set
     */
    public void setAcceptChatSpectator(boolean acceptChatSpectator) {
        this.acceptChatSpectator = acceptChatSpectator;
    }

    /**
     * return nbPlayerMax
     * @return : nbPlayerMax
     */
    public int getNbPlayerMax() {
        return nbPlayerMax;
    }

    /**
     * set nbplayerMax
     * @param nbPlayerMax : value to set
     */
    public void setNbPlayerMax(int nbPlayerMax) {
        this.nbPlayerMax = nbPlayerMax;
    }

    /**
     * get nbplayerMax
     * @return : nbplayerMax
     */
    public int getNbPlayerMin() {
        return nbPlayerMin;
    }

    /**
     * set nbplayerMax
     * @param nbPlayerMin : value to set
     */
    public void setNbPlayerMin(int nbPlayerMin) {
        this.nbPlayerMin = nbPlayerMin;
    }

    /**
     * get the current Game in the list (last one)
     * @return : current game
     */
    public Game getCurrentGame(){
        return listGames.get(listGames.size()-1);
    }

    /**
     * return abandonamiable's value
     * @return : true or false
     */
    public boolean isAbandonAmiable() {
        return abandonAmiable;
    }

    /**
     * set abandonamiable's value
     * @param abandonAmiable : valeu to set
     */
    public void setAbandonAmiable(boolean abandonAmiable) {
        this.abandonAmiable = abandonAmiable;
    }

    /**
     * return games' list
     * @return : games' list
     */
    public ArrayList<Game> getListGames() {
        return listGames;
    }

    /**
     * set games' list value
     * @param listGames : value to set
     */
    public void setListGames(ArrayList<Game> listGames) {
        this.listGames = listGames;
    }

    /**
     * return timeForAction's value
     * @return : timeForAction
     */
    public int getTimeForAction() {
        return timeForAction;
    }

    /**
     * set timeForAction's value
     * @param timeForAction : value to set
     */
    public void setTimeForAction(int timeForAction) {
        this.timeForAction = timeForAction;
    }

    /**
     * return Players' list
     * @return : players' list
     */
    public UserLightList getListPlayers() {
        return listPlayers;
    }


    /**
     * return spectators' list
     * @return : listSpectators
     */
    public UserLightList getListSpectators() {
        return listSpectators;
    }

    /**
     * return vote's value
     * @return : vote's value
     */
    public ArrayList<Boolean> getVote() {
        return vote;
    }

    /**
     * set vote's value
     * @param vote : value to set
     */
    public void setVote(ArrayList<Boolean> vote) {
        this.vote = vote;
    }

    /**
     * return the number of players who selected their money
     * @return : number of players
     */
    public int getNbPlayerSelectedMoney() {
        return nbPlayerSelectedMoney;
    }

    /**
     * set the number of players who selected their money
     * @param nbPlayerSelectedMoney : value to set
     */
    public void setNbPlayerSelectedMoney(int nbPlayerSelectedMoney) {
        this.nbPlayerSelectedMoney = nbPlayerSelectedMoney;
    }
}
