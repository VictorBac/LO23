package fr.utc.lo23.common.data;

import java.io.Serializable;
import java.util.ArrayList;
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
     * acceptSpectator : true if accept, false otherwhise
     * acceptChatSpectator : true if accept, false otherwhise
     * nbPlayerMax : maximum number of players on the table
     * nbPlayerMin : minimum number of players on the table required to launch a game
     * listPlayers: list of players actually on the table
     * listSpectators : list of spectators
     * currentGame : number of the actual game in the game array
     * abandonAmiable : true if authorised, false otherwhise
     * maxMise : maximum amount that players can give themselves at the beginning
     * listGames : list of games that were played on this table (last = actual)
     * timeforAction : time between actions for the replay
     */
    private UUID idTable;
    private String name;
    private boolean acceptSpectator;
    private boolean acceptChatSpectator;
    private int nbPlayerMax;
    private int nbPlayerMin;
    private UserLightList listPlayers;
    private UserLightList listSpectators;
    private int currentGame;
    private boolean abandonAmiable;
    private int maxMise;
    private ArrayList<Game> listGames;
    private int timeforAction;

    /**
     * Constructor
     * Creates a new game in games list
     * @param name
     * @param acceptSpectator
     * @param acceptChatSpectator
     * @param nbPlayerMax
     * @param nbPlayerMin
     * @param abandonAmiable
     * @param maxMise
     * @param timeforAction
     */
    public Table(String name, boolean acceptSpectator, boolean acceptChatSpectator, int nbPlayerMax, int nbPlayerMin, boolean abandonAmiable, int maxMise, int timeforAction) {
        this.setIdTable(UUID.randomUUID());
        this.setName(name);
        this.setAcceptSpectator(acceptSpectator);
        this.setAcceptChatSpectator(acceptChatSpectator);
        this.listPlayers = new UserLightList();
        this.listSpectators = new UserLightList();
        this.setNbPlayerMax(nbPlayerMax);
        this.setNbPlayerMin(nbPlayerMin);
        this.setCurrentGame(0);
        this.setAbandonAmiable(abandonAmiable);
        this.setMaxMise(maxMise);
        this.listGames = new ArrayList<Game>();
        listGames.add(new Game());
        this.setTimeforAction(timeforAction);
    }

    /**
     * method to add a player to the table, with checking if it is possible
     * Call checkConditionPlayerJoin()
     * @param player : player
     */
    public void playerJoinTable(UserLight player) throws TableException {
        // number of players on the table < max number of players AND player not already in the table
        if (checkConditionPlayerJoin() && !this.listPlayers.getListUserLights().contains(player)){
            this.listPlayers.getListUserLights().add(player);
        }
        else {
            throw new TableException("Impossible to add this new player");
        }
    }

    //TODO => rôle abandon amiable?
    /**
     * Delete player from the table
     * @param player
     * @throws TableException
     */
    public void playerLeaveTable(UserLight player) throws  TableException{
        //if abandonAmiable = true and player already on the table
        if(this.listPlayers.getListUserLights().contains(player) && this.abandonAmiable){
            this.listPlayers.getListUserLights().remove(player);
        }
        else if (!this.abandonAmiable){
            throw new TableException("Leaving table is not authorised!");
        }
        else{
            throw new TableException("Player you want to delete is not on the table!");
        }
    }

    /**
     * Check if a player can join a table
     */
    public boolean checkConditionPlayerJoin(){
        // number of players on the table < max number of players
        if (this.listPlayers.getListUserLights().size() < this.nbPlayerMax){
            return true;
        }
        else return false;
    }

    /**
     * Add a spectator to the table
     * @param spectator
     * @throws TableException
     */
    //TODO vérifier si suffisant
    public void spectatorJoinTable(UserLight spectator) throws TableException {
        //  spectator not on the table yet
        if (checkConditionSpectatorJoin() && !this.listSpectators.getListUserLights().contains(spectator)){
            this.listSpectators.getListUserLights().add(spectator);
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
    //TODO vérifier si suffisant
    public void spectatorLeaveTable(UserLight spectator) throws TableException{
        //spectator already on the table
        if(this.listSpectators.getListUserLights().contains(spectator)){
            this.listSpectators.getListUserLights().remove(spectator);
        }
        else{
            throw new TableException("Spectator you want to delete is not on the table!");
        }
    }

    /**
     * Check if a spectator can join the table
     */
    //TODO vérifier si suffisant
    public boolean checkConditionSpectatorJoin(){
        if(isAcceptSpectator())
            return true;
        else
            return false;
    }


    //TODO
    /**
     * Save the game
     */
    public void saveGame(){

    }

    //TODO
    /**
     * Open a game in order to replay it
     * @param idGame
     */
    public void openGame(UUID idGame){

    }


    /**
     * Add a new game in the table's games list
     * @param game : new game to add to the list
     */
    public void addNewGameToList(Game game){
        this.listGames.add(game);
    }

    /**
     * Start a waiting game
     * @param game : game to start
     */
    //TODO
    public void startGame(Game game){
        /*
        if game en attente start
        else pas start
         */
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

    public int getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(int currentGame) {
        this.currentGame = currentGame;
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

    public int getTimeforAction() {
        return timeforAction;
    }

    public void setTimeforAction(int timeforAction) {
        this.timeforAction = timeforAction;
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
}

