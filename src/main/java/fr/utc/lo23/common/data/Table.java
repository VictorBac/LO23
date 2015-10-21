package fr.utc.lo23.common.data;

import java.util.ArrayList;
import java.util.UUID;

/////////////////////////////////
// TODO Classe � d�placer !!
/////////////////////////////////
/*
class TableException extends Exception{
    public TableException(){
        System.out.println("Impossible de cr�er la table sans param�tres!");
    }
}
*/

/**
 * Classe repr�sentant une table, sur laquelle se d�roulent des parties
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
     * currentGame : jeu actuel
     * abandonAmiable : 1 si autoris�, 0 sinon
     * maxMise : ??????
     * listGames : liste des parties qui se sont d�roul�es sur la table
     * timeforAction : d�lai entre les actions lors du replay d'une partie
     */
    private UUID idTable;
    private String name;
    private boolean acceptSpectator;
    private boolean acceptChatSpectator;
    private int nbPlayerMax;
    private int nbPlayerMin;
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
        this.currentGame = currentGame;
        this.abandonAmiable = abandonAmiable;
        this.maxMise = maxMise;
        this.listGames = listGames;
        this.timeforAction = timeforAction;
    }

    /**
     * Constructeur par d�faut
     */
    public Table() {
        //TODO
        // throw new TableException();
    }

    /**
     * m�thode r�cup�rant la partie se d�roulant actuellement sur la table
     */
    public void getCurrentGame(){}

    /**
     * m�thode permettant d'ajouter un joueur � une table, en v�rifiant que cela est possible
     * Appelelle
     * @param idPlayer : id du joueur
     */
    public void joinTable(UUID idPlayer){}

    /**
     * m�thode permettant de sauvegarder la partie
     */
    public void saveGame(){}

    /**
     * m�thode permettant d'ouvrir une partie pour la rejouer
     */
    public void openGame(){}

    /**
     * m�thode permettant de mettre � jour la liste des tables actuellement disponibles
     */
    public void updateTableList(){}

    /**
     * m�thode permettant d'ajouter une nouvelle partie dans la liste des parties sur la table
     * @param partie : nouvelle partie � ajouter � la liste
     */
    public void addNewGameToList(Game partie){}

    /**
     * m�thode permettant de v�rifier les conditions d'ajout d'un joueur � la table
     */
    public void checkConditionsPlayerJoin(){}

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
}

