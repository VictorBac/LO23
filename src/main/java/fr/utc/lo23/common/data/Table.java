package fr.utc.lo23.common.data;

import java.util.ArrayList;

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
    private int currentGame;
    private boolean abandonAmiable;
    private int maxMise;
    private ArrayList<Game> listGames;
    private int timeforAction;

    /**
     * méthode récupérant la partie se déroulant actuellement sur la table
     */
    public void getCurrentGame(){}

    /**
     * méthode permettant d'ajouter un joueur à une table, en vérifiant que cela est possible
     * Appelelle
     * @param idPlayer : id du joueur
     */
    public void joinTable(UUID idPlayer){}

    /**
     * méthode permettant de sauvegarder la partie
     */
    public void saveGame(){}

    /**
     * méthode permettant d'ouvrir une partie pour la rejouer
     */
    public void openGame(){}

    /**
     * méthode permettant de mettre à jour la liste des tables actuellement disponibles
     */
    public void updateTableList(){}

    /**
     * méthode permettant d'ajouter une nouvelle partie dans la liste des parties sur la table
     * @param partie : nouvelle partie à ajouter à la liste
     */
    public void addNewGameToList(Game partie){}

    /**
     * méthode permettant de vérifier les conditions d'ajout d'un joueur à la table
     */
    public void checkConditionsPlayerJoin(){}
}

