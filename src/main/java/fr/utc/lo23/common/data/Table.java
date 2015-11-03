package fr.utc.lo23.common.data;

import java.util.ArrayList;
import java.util.UUID;
import fr.utc.lo23.common.data.exceptions.TableException;

/**
 * Classe représentant une table, sur laquelle se déroulent des parties
 * Created by Haroldcb on 21/10/2015.
 */
public class Table {
    /**
     * idTable : id unique de la table
     * name : nom de la table
     * acceptSpectator : 1 si oui, 0 sinon
     * acceptChatSpectator : 1 si oui, 0 sinon
     * nbPlayerMax : nombre maximum de joueurs sur la table
     * nbPlayerMin : nombre minimum de joueurs requis pour lancer une partie
     * listPlayers: liste des joueurs actuellement sur la table
     * listSpectators : liste des spectateurs
     * currentGame : indice du jeu actuel dans le tableau de parties
     * abandonAmiable : 1 si autorisé, 0 sinon
     * maxMise : mise maximum que les joeurs peuvent s'attribuer au début
     * listGames : liste des parties qui se sont déroulées sur la table (dernier = actuel)
     * timeforAction : délai entre les actions lors du replay d'une partie
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
     * Constructeur
     * @param name
     * @param acceptSpectator
     * @param acceptChatSpectator
     * @param nbPlayerMax
     * @param nbPlayerMin
     * @param currentGame
     * @param abandonAmiable
     * @param maxMise
     * @param listGames
     * @param timeforAction
     */
    public Table(String name, boolean acceptSpectator, boolean acceptChatSpectator, int nbPlayerMax, int nbPlayerMin, int currentGame, boolean abandonAmiable, int maxMise, ArrayList<Game> listGames, int timeforAction) {
        this.idTable = UUID.randomUUID();
        this.name = name;
        this.acceptSpectator = acceptSpectator;
        this.acceptChatSpectator = acceptChatSpectator;
        this.listPlayers = new UserLightList();
        this.listSpectators = new UserLightList();
        this.nbPlayerMax = nbPlayerMax;
        this.nbPlayerMin = nbPlayerMin;
        this.currentGame = currentGame;
        this.abandonAmiable = abandonAmiable;
        this.maxMise = maxMise;
        this.listGames = new ArrayList<Game>();
        //TODO listGames.add(new Game(EnumerationStatusGame.waitingForPlayer));
        this.timeforAction = timeforAction;
    }

    /**
     * Constructeur par défaut
     */
    public Table() throws TableException {
        //TODO
        throw new TableException("Impossible de créer une table sans paramètres");
    }

    /**
     * m&eacute;thode permettant d'ajouter un joueur &agrave; une table, en v&eacute;rifiant que cela est possible
     * Appelle checkConditionPlayerJoin()
     * @param player : player
     */
    public void playerJoinTable(UserLight player) throws TableException {
        if (checkConditionPlayerJoin()){
            this.listPlayers.getListUserLights().add(player);
        }
        else {
            throw new TableException("Impossible d'ajouter un nouveau joueur");
        }
    }

    //TODO

    /**
     * Delete player frome table
     * @param player
     * @throws TableException
     */
    public void playerLeaveTable(UserLight player) throws  TableException{

    }

    /**
     * Check if a player can join a table
     */
    public boolean checkConditionPlayerJoin(){
        if (this.listPlayers.getListUserLights().size() < this.nbPlayerMax){
            return true;
        }
        else return false;
    }

    /**
     * Add a spectator to the table
     * @param player
     * @throws TableException
     */
    //TODO
    public void spectatorJoinTable(UserLight player) throws TableException {
        /*if (checkConditionSpectatorJoin()){
            this.listPlayers.getListUserLights().add(player);
        }
        else {
            throw new TableException("Impossible d'ajouter un nouveau spectateur");
        }*/
    }

    /**
     * Delete a spectator from the table
     * @param player
     * @throws TableException
     */
    //TODO
    public void spectatorLeaveTable(UserLight player) throws TableException{

    }

    /**
     * Check if a spectator can join the table
     */
    //TODO
    public void checkConditionSpectatorJoin(){
    }


    //TODO
    /**
     * Save the game
     */
    public void saveGame(){

    }

    /**
     * Open a game in order to replay it
     * @param idGame
     */
    public void openGame(UUID idGame){
        /* TODO */
    }


    /**
     * Add a new game in the list of games of the table
     * @param partie : new game to add to the list
     */
    public void addNewGameToList(Game partie){
        this.listGames.add(partie);
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
    };

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

