package fr.utc.lo23.common.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import fr.utc.lo23.common.data.exceptions.ExistingUserException;
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
     * @param petiteBlinde
     * @param timeForAction
     * @param ante
     * @param startMoneyMax
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
            if(iter.next().getStatusOfTheGame() == EnumerationStatusGame.Playing || iter.next().getStatusOfTheGame() == EnumerationStatusGame.Finished){
                throw new TableException("Impossible to start a new game");
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

        Iterator<Game> iter = this.listGames.iterator();
        while (iter.hasNext())
        {
            //if a game in the list is already started, impossible to start a new game
            if(iter.next().getStatusOfTheGame() == EnumerationStatusGame.Playing || iter.next().getStatusOfTheGame() == EnumerationStatusGame.Finished){
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

    public void playGame(){
        Game game = this.getCurrentGame();
        Hand hand;
        Turn turn;

        if(game.getListHand().size()==0)
        {
            //On se situe au tout début d'une game
            hand = new Hand();
            game.getListHand().add(hand);

            //Choix du joueur initial
            //On choisit de prendre le premier joueur dans la liste (l'host s'il n'a pas quitté la table, d'ailleurs je sais pas comment on gère ce cas).
            UserLight firstPlayer = this.getListPlayers().getListUserLights().get(0);
            hand.setFirstPlayer(firstPlayer);

        }
        else
        {
            //La game est déjà commencée
            hand = game.getCurrentHand();
        }


        if(hand.getListTurn().size()==0)
        {
            //On se situe au début d'un tour
            turn = new Turn(game);
            hand.getListTurn().add(turn);
        }
        else
        {
            turn = hand.getCurrentTurn();
        }

        if(turn.getListAction().size()==0)
        {
            //Faire les actions de base

        }
        else
        {
            //Vérifier si le tour est finit
            if(false)
            {
                //S'il est finit, résoudre ce tour


                // puis vérifier si la manche est finie
                if(false)
                {
                    //Si elle est finit résoudre la manche

                    //puis vérifier si la game est finie
                    if(false)
                    {
                        //Si la game est finie, résoudre la game

                        //Puis clore la game

                    }
                    else
                    {
                        //sinon créer une nouvelle manche et faire ce qui doit etre fait
                    }
                }
                else
                {
                    //sinon créer un nouveau tour et appeler la première action
                }
            }
            else
            {
                //appeler les actions du joueur prochain

            }

        }




        /*

        Je démarre une manche.

        Je demande les ante à tous les joueurs si elles sont definies
        Je commence par mettre l'icone Dealer au premier joueur, et demander les blindes au joueur 2 puis au joueur 3

        Je demande aux joueurs dans l'ordre de réaliser des actions, tant que tous n'ont pas joué au moins une fois, et tant qu'il reste un joueur qui ne soit pas couché ou qui n'ait pas la même somme que les autres.
        La relance a une mise minimum, elle est du minimum de la dernière relance

        Le tour est finit, je notifie les clients avec les valeurs du pot, j'envoi le flop, puis je lance un nouveau tour

        nouveau tour finit, je notifie les clients avec les valeurs du pot, j'envoi le turn, puis je lance un nouveau

        nouveau tour finit, je notifie les clients avec les valeurs du pot, j'envoi la river, puis je lance un nouveau tour

        nouveau tour finit, je résoud les cartes, définit les vainqueurs, puis informe tout le monde.

        J'informe que je finis la manche.

        S'il ne reste plus qu'un seul joueur avec de l'argent, je termine la game. sinon je décale le premier joueur et je relance une manche.


        En cas de vote pour demander la fin de la partie, cette fonction n'est pas appelée, sauf s'il y a un vote négatif lorsque tous les votes ont été faits.


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
