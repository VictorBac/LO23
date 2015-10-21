package fr.utc.lo23.common.data;

import java.util.ArrayList;

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
}

