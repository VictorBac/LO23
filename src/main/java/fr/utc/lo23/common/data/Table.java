package fr.utc.lo23.common.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import fr.utc.lo23.common.data.exceptions.TableException;

/**
 * Class representing a table, on which games are played
 * Created by Haroldcb on 21/10/2015.
 */
public class Table implements Serializable {
    /*
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
    private int maxMise;
    private ArrayList<Game> listGames;
    private int timeForAction;
    private static final long serialVersionUID = 1L;
    private ArrayList<Boolean> vote;
    private int nbPlayerSelectedMoney;

    /**
     * Constructor
     * @param name
     * @param creator
     * @param acceptSpectator
     * @param acceptChatSpectator
     * @param nbPlayerMax
     * @param nbPlayerMin
     * @param abandonAmiable
     * @param maxMise
     * @param timeForAction
     */
    public Table(String name, UserLight creator, boolean acceptSpectator, boolean acceptChatSpectator, int nbPlayerMax, int nbPlayerMin, boolean abandonAmiable, int maxMise, int timeForAction, int ante, int blind){
        this.setIdTable(UUID.randomUUID());
        this.setName(name);
        this.setCreator(creator);
        this.setAcceptSpectator(acceptSpectator);
        this.setAcceptChatSpectator(acceptChatSpectator);
        this.listPlayers = new UserLightList();
        this.listSpectators = new UserLightList();
        this.setNbPlayerMax(nbPlayerMax);
        this.setNbPlayerMin(nbPlayerMin);
        this.setAbandonAmiable(abandonAmiable);
        this.setMaxMise(maxMise);
        this.listGames = new ArrayList<Game>();
        //Add new waiting game to the list
        this.listGames.add(new Game(this, ante, blind));
        this.setTimeForAction(timeForAction);
        this.vote = new ArrayList<Boolean>();
        nbPlayerSelectedMoney = 0;
    }

    /**
     * method to add a player to the table, with checking if it is possible
     * Check if it's possible (call checkConditionPlayerJoin())
     * @param player : player
     * @throws fr.utc.lo23.common.data.exceptions.TableException
     */
    public void playerJoinTable(UserLight player) throws TableException {
        // number of players on the table < max number of players AND player not already in the table
        if (checkConditionPlayerJoin() && !this.listPlayers.getListUserLights().contains(player)){
            this.listPlayers.getListUserLights().add(player);
            this.getCurrentGame().addPlayer(player);
        }
        else {
            throw new TableException("Impossible to add this new player");
        }
    }

    /**
     * Delete player from the table
     * @param player
     * @throws TableException
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
     * @param spectator
     * @throws TableException
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
     * @param spectator
     * @throws TableException
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
     */
    public boolean checkConditionSpectatorJoin(){
        return isAcceptSpectator();
    }

    /**
     * Add a new game in the table's games list
     * @param game : new game to add to the list
     */
    public void addNewGameToList(Game game) throws TableException {
        Iterator<Game> iter = this.listGames.iterator();
        while (iter.hasNext())
        {
            //if a game in the list is already started, impossible to start a new game
            if(iter.next().getStatusOfTheGame() == EnumerationStatusGame.playing || iter.next().getStatusOfTheGame() == EnumerationStatusGame.finished){
                throw new TableException("Impossible to start a new game");
            }
        }
        this.listGames.add(game);
    }


    /**
     * Create a new game and add it in the table's games list
     * @param ante int ante used for this new Game
     * @param blind int blind used for this new Game
     * @throws TableException This exception is thrown if a Game is still on play then impossible to start a new game
     */
    public void addNewGameToList(int ante, int blind) throws TableException {
        Game gameToAdd = new Game(this,ante, blind);

        Iterator<Game> iter = this.listGames.iterator();
        while (iter.hasNext())
        {
            //if a game in the list is already started, impossible to start a new game
            if(iter.next().getStatusOfTheGame() == EnumerationStatusGame.playing || iter.next().getStatusOfTheGame() == EnumerationStatusGame.finished){
                throw new TableException("Impossible to start a new game");
            }
        }
        this.listGames.add(gameToAdd);
    }


    /**
     * Start a waiting game
     * @param game : game to start
     */
    public void startGame(Game game) throws TableException{
        if (this.listGames.contains(game) && canStartGame(game)){
            if(game.getStatusOfTheGame() == EnumerationStatusGame.closed || game.getStatusOfTheGame() == EnumerationStatusGame.waitingForPlayer){
                game.startGame();
            }
        }
        else throw new TableException("Game not in the list");
    }

    /**
     * Return the index of the game if it's in the tables list
     * @param game : game to check
     * @return : index of the game if contained, -1 if not
     */
    public int getIDTGame(Game game){
        return this.getListGames().indexOf(game);
    }

    /**
     * Function that checks if the number of players in the game is between nbPlayerMin and nbPlayerMax
     * @return : true if ok, false otherwise
     */
    public boolean canStartGame(Game gameToStart){
        //size of the players' list in the gameToStart
        if(this.listGames.get(getIDTGame(gameToStart)).getListSeatPlayerWithPeculeDepart().size() > this.nbPlayerMin && this.listGames.get(getIDTGame(gameToStart)).getListSeatPlayerWithPeculeDepart().size() < this.nbPlayerMax)
            return true;
        else
            return false;
    }


    /******************* GETTERS AND SETTERS ********************************/

    public UUID getIdTable() {
        return idTable;
    }

    public void setIdTable(UUID idTable) {
        this.idTable = idTable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserLight getCreator() {return creator;}

    public void setCreator(UserLight creator) {this.creator = creator;}

    public boolean isAcceptSpectator() {
        return acceptSpectator;
    }

    public void setAcceptSpectator(boolean acceptSpectator) {
        this.acceptSpectator = acceptSpectator;
    }

    public boolean isAcceptChatSpectator() {
        return acceptChatSpectator;
    }

    public void setAcceptChatSpectator(boolean acceptChatSpectator) {
        this.acceptChatSpectator = acceptChatSpectator;
    }

    public int getNbPlayerMax() {
        return nbPlayerMax;
    }

    public void setNbPlayerMax(int nbPlayerMax) {
        this.nbPlayerMax = nbPlayerMax;
    }

    public int getNbPlayerMin() {
        return nbPlayerMin;
    }

    public void setNbPlayerMin(int nbPlayerMin) {
        this.nbPlayerMin = nbPlayerMin;
    }

    public Game getCurrentGame(){
        return listGames.get(listGames.size()-1);
    }

    public boolean isAbandonAmiable() {
        return abandonAmiable;
    }

    public void setAbandonAmiable(boolean abandonAmiable) {
        this.abandonAmiable = abandonAmiable;
    }

    public int getMaxMise() {
        return maxMise;
    }

    public void setMaxMise(int maxMise) {
        this.maxMise = maxMise;
    }

    public ArrayList<Game> getListGames() {
        return listGames;
    }

    public void setListGames(ArrayList<Game> listGames) {
        this.listGames = listGames;
    }

    public int getTimeForAction() {
        return timeForAction;
    }

    public void setTimeForAction(int timeForAction) {
        this.timeForAction = timeForAction;
    }

    public UserLightList getListPlayers() {
        return listPlayers;
    }

    public void setListPlayers(UserLightList listPlayers) {
        this.listPlayers = listPlayers;
    }

    public UserLightList getListSpectators() {
        return listSpectators;
    }

    public void setListSpectators(UserLightList listSpectators) {
        this.listSpectators = listSpectators;
    }

    public ArrayList<Boolean> getVote() {
        return vote;
    }

    public void setVote(ArrayList<Boolean> vote) {
        this.vote = vote;
    }

    public int getNbPlayerSelectedMoney() {
        return nbPlayerSelectedMoney;
    }

    public void setNbPlayerSelectedMoney(int nbPlayerSelectedMoney) {
        this.nbPlayerSelectedMoney = nbPlayerSelectedMoney;
    }
}
