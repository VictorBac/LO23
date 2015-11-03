package fr.utc.lo23.common.data;

import java.util.ArrayList;
import java.util.UUID;

/////////////////////////////////
// TODO Classe à déplacer !!
/////////////////////////////////

class TableException extends Exception{
    public TableException(String message){
        System.out.println(message);
    }
}


/**
 * Classe représentant une table, sur laquelle se déroulent des parties
 * Created by Haroldcb on 21/10/2015.
 */
public class Table {
    /**
     * idTable : id unique de la table
     * name : nom de la table
     * accpetSpectator : 1 si oui, 0 sinon
     * acceptChatSpectator : 1 si oui, 0 sinon
     * nbPlayerMax : nombre maximum de joueurs sur la table
     * nbPlayerMin : nombre minimum de joueurs requis pour lancer une partie
     * playerList: liste des joueurs actuellement sur la table
     * spectatorList : liste des spectateurs sur la table
     * currentGame : jeu actuel
     * abandonAmiable : 1 si autorisé, 0 sinon
     * maxMise : ??????
     * listGames : liste des parties qui se sont déroulées sur la table
     * timeforAction : délai entre les actions lors du replay d'une partie
     */
    private UUID idTable;
    private String name;
    private boolean acceptSpectator;
    private boolean acceptChatSpectator;
    private int nbPlayerMax;
    private int nbPlayerMin;
    private UserLightList playerList;
    private UserLightList spectatorList;
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
        this.nbPlayerMax = nbPlayerMax;
        this.nbPlayerMin = nbPlayerMin;
        this.playerList = new UserLightList();
        if(this.acceptSpectator == true) {
            this.spectatorList = new UserLightList();
        }
        this.currentGame = currentGame;
        this.abandonAmiable = abandonAmiable;
        this.maxMise = maxMise;
        this.listGames = listGames;
        this.timeforAction = timeforAction;
    }

    /**
     * Constructeur par défaut
     */
    public Table() throws TableException {
        //TODO
        throw new TableException("Impossible de créer une tablesans paramètres");
    }

    /**
     * méthode récupérant la partie se déroulant actuellement sur la table
     */
    public UUID getCurrentGameID(){
        // TODO ajout exception : pas de current game
        int index = this.getCurrentGame();
        return this.listGames.get(index).getIdGame();
    }

    /**
     * m&eacute;thode permettant d'ajouter un joueur &agrave; une table, en v&eacute;rifiant que cela est possible
     * Appelle checkConditionPlayerJoin()
     * @param player : id du joueur
     */
    public void playerJoinTable(UserLight player) throws TableException {
        if (checkConditionsPlayerJoin()){
            // TODO ajouter méthode dans UserLightList
            this.playerList.getListUserLights().add(player);
        }
        else {
            throw new TableException("Impossible d'ajouter un nouveau joueur");
        }
    }

    /**
     * methode permettant de supprimer un joueur de la table
     * @param player : id du joueur à supprimer
     * @throws TableException
     */
    public void playerLeaveTable(UserLight player) throws TableException{
        //Si le joueur est dans la liste, on le supprime
        if (this.playerList.getListUserLights().contains(player)){
            this.playerList.getListUserLights().remove(player);
        }
        else{
            throw new TableException("Le joueur n'est pas dans la table");
        }
    }

    /**
     * methode permettant d'ajouter un spectateur à la table
     * @param spectator
     * @throws TableException
     */
    public void spectatorJoinTable(UserLight spectator) throws TableException {
        if (this.acceptSpectator == true){
            // TODO ajouter méthode dans UserLightList
            this.spectatorList.getListUserLights().add(spectator);
        }
        else {
            throw new TableException("Impossible d'ajouter un nouveau spectateur");
        }
    }

    /**
     * methode permettant de supprimer un spectateur de la table
     * @param spectator : id du spectateur à supprimer
     * @throws TableException
     */
    public void spectatorLeaveTable(UserLight spectator) throws TableException{
        //Si le joueur est dans la liste, on le supprime
        if (this.spectatorList.getListUserLights().contains(spectator)){
            this.spectatorList.getListUserLights().remove(spectator);
        }
        else{
            throw new TableException("Le spectateur n'est pas dans la table");
        }
    }

    /**
     * méthode permettant de vérifier les conditions d'ajout d'un joueur à la table
     */
    public boolean checkConditionsPlayerJoin(){
        if (this.playerList.getListUserLights().size() < this.nbPlayerMax){
            return true;
        }
        else return false;
    }

    /**
     * méthode permettant de sauvegarder la partie
     */
    public void saveGame(){
        /* TODO */
    }

    /**
     * méthode permettant d'ouvrir une partie pour la rejouer
     * @param idGame
     */
    public void openGame(UUID idGame){
        /* TODO */
    }

    /**
     * méthode permettant de mettre à jour la liste des tables actuellement disponibles
     */
    public void updateTableList(){
        /* TODO */
    }

    /**
     * méthode permettant d'ajouter une nouvelle partie dans la liste des parties sur la table
     * @param partie : nouvelle partie à ajouter à la liste
     */
    public void addNewGameToList(Game partie){
        this.listGames.add(partie);
    }



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

    public UserLightList getPlayerList() {
        return playerList;
    }

    public void setPlayerList(UserLightList playerList) {
        this.playerList = playerList;
    }

    public UserLightList getSpectatorList() {
        return spectatorList;
    }

    public void setSpectatorList(UserLightList spectatorList) {
        this.spectatorList = spectatorList;
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
}

