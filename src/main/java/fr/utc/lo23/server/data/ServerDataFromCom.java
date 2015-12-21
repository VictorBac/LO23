package fr.utc.lo23.server.data;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.*;
import fr.utc.lo23.common.data.exceptions.*;
import fr.utc.lo23.common.data.exceptions.UserNotFoundException;
import fr.utc.lo23.exceptions.network.NetworkFailureException;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Remy on 03/11/2015.
 */
public class ServerDataFromCom implements InterfaceServerDataFromCom {

    private DataManagerServer myManager;
    private final String TAG = "ServerDataFromCom";

    public ServerDataFromCom(DataManagerServer manager) {
        this.myManager = manager;
    }

    /**
     * Notifies a new user connection and creates a user
     *
     * @param connectingUser
     * @return the new user created
     * @throws ExistingUserException
     */
    public UserLight userConnection(User connectingUser) throws ExistingUserException {
        myManager.getUsers().addUser(connectingUser);
        //Console.log(TAG + "\tUser connected.");
        return connectingUser.getUserLight();
    }

    /**
     * creates a list of userlights from the list of users
     *
     * @return the list of currently connected users
     */
    public ArrayList<UserLight> getConnectedUsers() {
        ArrayList<UserLight> created = new ArrayList<UserLight>();
        for (User current : myManager.getUsers().getList()) {
            created.add(current.getUserLight());
        }
        //Console.log(TAG + "\tListe des joueurs.");
        return created;
    }

    /**
     * @return the list of current tables
     */
    public ArrayList<Table> getTableList() {
        return myManager.getTables().getListTable();
    }

    /**
     * creates a new table
     * @param maker
     * @param newTb TODO : remove the maker attribute ?
     */
    public void createTable(UserLight maker, Table newTb) {

        myManager.getTables().newTable(newTb);
        Console.log(TAG + "\tTable created.");
    }

    /**
     * notifies the tables list has been modified to all observers
     */
    public void updateTableList() {
        myManager.getTables().getListTable().notifyAll();
    }

    /**
     * checks if a user can join a table
     * @param joiner the user trying to join
     * @param idTable the id of the table to join
     * @param mode the connection mode (player / spectator)
     * @return a boolean
     */
    public boolean canJoinTableUser(UserLight joiner, UUID idTable, EnumerationTypeOfUser mode) {
        boolean ok;
        Table wantedTable = getTableFromId(idTable);
        if (wantedTable.getListPlayers().getListUserLights().size() > wantedTable.getNbPlayerMax()) {
            ok = false;
            Console.log(TAG + "\tPlayer can't join, table full ");
        }
        else if (mode.equals(EnumerationTypeOfUser.SPECTATOR) && !wantedTable.isAcceptSpectator()) {
            ok = false;
            Console.log(TAG + "\tPlayer can't join, no spectators allowed");
        }
        else
            ok = true;
        Console.log(TAG + "\tPlayer can join : " + Boolean.toString(ok));
        return ok;
    }

    public void addPlayerToTable(UUID idTable, UserLight player, EnumerationTypeOfUser mode) {
        Table toAdd = getTableFromId(idTable);
        try {
            if(mode == EnumerationTypeOfUser.PLAYER) {
                toAdd.playerJoinTable(player);
            } else {
                toAdd.spectatorJoinTable(player);
            }
            Console.log(TAG + "\tPlayer joined table");
        }
        catch(Exception e)
        {
            Console.log(TAG + "\tPlayer could't join table");
        }
    }

    public void validateMessage(UserLight sender, MessageChat msgSent) {

    }

    public Game sendLogGame(UserLight player) {
        return null;
    }

    /*
     * starts a game with a given ID
     *
     * @param idTable the id of the table of the game
     * @param player the player launching the game
     * @return the created game
     */
    public Boolean startGame(UUID idTable, UserLight player) {
        Table toStart = getTableFromId(idTable);
        if(toStart.getCurrentGame().startGame() && player.equals(toStart.getCreator()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void playGame(Table table){
        Game game = table.getCurrentGame();
        Hand hand;
        Turn turn;

        if(game.getListHand().size()==0)
        {
            //On se situe au tout début d'une game
            hand = new Hand(game);
            game.getListHand().add(hand);
        }
        else
        {
            //La game est déjà commencée
            hand = game.getCurrentHand();
        }

        if(hand.getListTurn().size()==0)
        {
            //On se situe au début d'un tour
            turn = new Turn(hand);
            hand.getListTurn().add(turn);
        }
        else
        {
            turn = hand.getCurrentTurn();
        }

        if(turn.getListAction().size()==0)
        {
            //Faire les actions de base
            ArrayList<Action> arrayAc = turn.askFirstAction();
            if(arrayAc.size()==1)
            {
                myManager.getInterfaceToCom().askActionToPLayer(arrayAc.get(0),(EnumerationAction[])turn.availableActions(arrayAc.get(0).getUserLightOfPlayer()).toArray());
            }
            else
            {
                //Elle doit etre égale à 3 alors, donc les deux blindes plus l'action à demander
                try {
                    myManager.getInterfaceToCom().notifyOtherPlayerAction(table.getListPlayers().getListUserLights(),arrayAc.get(0));
                    myManager.getInterfaceToCom().notifyOtherPlayerAction(table.getListPlayers().getListUserLights(),arrayAc.get(1));
                } catch (NetworkFailureException e) {
                    e.printStackTrace();
                }
                myManager.getInterfaceToCom().askActionToPLayer(arrayAc.get(2),(EnumerationAction[])turn.availableActions(arrayAc.get(2).getUserLightOfPlayer()).toArray());
            }
        }
        else
        {
            //Vérifier si le tour est finit
            if(turn.isFinished())
            {
                //S'il est finit, résoudre ce tour
                turn.resolve();
                //Prévenir les utilisateurs
                myManager.getInterfaceToCom().endTurn(table.getListPlayers().getListUserLights(),turn.getTurnPot());
                // puis vérifier si la manche est finie
                if(hand.isFinished())
                {
                    //Si elle est finit résoudre la manche
                    hand.resolve();
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
                    turn = new Turn(hand);
                    hand.getListTurn().add(turn);
                    // -------------------------------------------------------------
                    // CE CODE EST UN COPIER COLLER D'AU DESSUS, IL FAUDRA OPTIMISER
                    ArrayList<Action> arrayAc = turn.askFirstAction();
                    if(arrayAc.size()==1)
                    {
                        myManager.getInterfaceToCom().askActionToPLayer(arrayAc.get(0),(EnumerationAction[])turn.availableActions(arrayAc.get(0).getUserLightOfPlayer()).toArray());
                    }
                    else
                    {
                        //Elle doit etre égale à 3 alors, donc les deux blindes plus l'action à demander
                        try {
                            myManager.getInterfaceToCom().notifyOtherPlayerAction(table.getListPlayers().getListUserLights(),arrayAc.get(0));
                            myManager.getInterfaceToCom().notifyOtherPlayerAction(table.getListPlayers().getListUserLights(),arrayAc.get(1));
                        } catch (NetworkFailureException e) {
                            e.printStackTrace();
                        }
                        myManager.getInterfaceToCom().askActionToPLayer(arrayAc.get(2),(EnumerationAction[])turn.availableActions(arrayAc.get(2).getUserLightOfPlayer()).toArray());
                    }
                    // -------------------------------------------------------------
                }
            }
            else
            {
                //appeler les actions du joueur prochain
                askAction(turn.getNextActiveUser(),(EnumerationAction[])turn.availableActions(turn.getNextActiveUser()).toArray());
            }
        }
    /*   Je démarre une manche.
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
    En cas de vote pour demander la fin de la partie, cette fonction n'est pas appelée, sauf s'il y a un vote négatif lorsque tous les votes ont été faits.    */
    }

    public void nextStepReplay() {

    }

    /**
     * deletes a player from the list connectedusers
     * @param deletedUsr the user to delete
     */
    public void deletePlayer(UserLight deletedUsr) throws UserNotFoundException {
        User userToDelete = null;
        for (User current : myManager.getUsers().getList()) {
            if (current.getUserLight().equals(deletedUsr)) {
                userToDelete = current;
                break;
            }
        }

        if (userToDelete == null) {
            throw new fr.utc.lo23.common.data.exceptions.UserNotFoundException(deletedUsr.getIdUser());
        }
        else {
            myManager.getUsers().getList().remove(userToDelete);
            //Console.log(TAG + "\tPlayer deleted.");
        }
    }

    public void confirmationCardReceived(UserLight player) {

    }

    public void replyAction(UUID table, Action playedAction) {
        try {
            myManager.getTables().getTable(table).getCurrentGame().getCurrentHand().getCurrentTurn().addAction(playedAction);
            playGame(myManager.getTables().getTable(table));
        }
        catch(ActionInvalidException a) {
            Turn turn = myManager.getTables().getTable(table).getCurrentGame().getCurrentHand().getCurrentTurn();
            askAction(turn.getNextActiveUser(), (EnumerationAction[]) turn.availableActions(turn.getNextActiveUser()).toArray());
        }
    }

    public void confirmationActionReceived(UserLight sender) {
        // ??? C'est inutile ce truc
    }

    public void endTurnConfirmation(UserLight player) {

    }

    /**
     * updates a user info with a whole new User
     * @param newUser the new information
     */
    public void updateProfile(User newUser) {
        for (User cur : myManager.getUsers().getList()) {
            if (cur.equals(newUser)) {
                myManager.getUsers().getList().remove(cur);
                myManager.getUsers().getList().add(newUser);
            }
        }
    }

    /**
     * returns the user corresponding to a userlight in the Userlist of mymanager
     * @param core the userlight to compare to
     * @return the corresponding user, null if not found
     */
    public User getProfile(UserLight core) {
        for (User cur : myManager.getUsers().getList()) {
            if (cur.getUserLight().equals(core))
                return cur;
        }
        return null;
    }

    public User getUserById(UUID userId)
    {
        for (User cur : myManager.getUsers().getList()){
            if (cur.getUserLight().getIdUser().equals(userId))
                return cur;
        }
        return null;
    }

    /**
     * returns a list of all the players of the given table
     * @param tableID the UUID of the table to look for
     * @return an arrayList of Userlight with all the players
     */
    public ArrayList<UserLight> getPlayersByTable(UUID tableID){
        // Inutile ?    ArrayList<UserLight> players = new ArrayList<UserLight>();
        Table current = getTableFromId(tableID);
        return current.getListPlayers().getListUserLights();
    }

    /**
     * looks for the table with the corresponding UUID
     * @param idTable the id to look for
     * @return the table if found, else null
     */
    private Table getTableFromId(UUID idTable){
        // Inutile ?  Table wantedTable = null;
        ArrayList<Table> tableList = getTableList();
        for (Table cur : tableList){
            if (cur.getIdTable().equals(idTable))
                return cur;
        }
        return null;
    }


    /**
     * checks if a game can be launched and notifies the creator
     * if it can, starts the askMoney method
     * @param idTable id of the table to check
     * @param host the creator of the game
     * @return true if it can be launched, else false
     */
    /* *******************************************
        INTEG 5 : CETTE FONCTION EST DEPRECIEE
    *********************************************
    public void canStartGame(UUID idTable, UserLight host) {
        boolean canLaunch;
        Table curTable =this.getTableFromId(idTable);
        if (curTable.getCurrentGame().startGame()) {
            canLaunch = true;
            //TODO:integ5 myManager.getInterfaceToCom().tableCreatorRequestToStartGameAccepted(idTable);
            askMoneyMax(idTable);
        }
        else {
            canLaunch = false;
            //TODO:integ5 myManager.getInterfaceToCom().tableCreatorRequestToStartGameRejected(idTable);
        }
    }*/

    /**
     * asks all the players to give their max amount of money through server com
     */
    public void askMoneyMax(UUID idTable) {
        Table toAsk = getTableFromId(idTable);
       //TODO:integ5 myManager.getInterfaceToCom().askPlayersMoney(toAsk.getListPlayers().getListUserLights());
    }

    /**
     * checks if the amount of money sent by a user is valid
     * @param money the amount of money chosen
     * @param player the userLight who answers
     * @return
     * //todo : add ints in Table and remove hashmaps
     */
    public boolean checkMoneyMax(UserLight player, int money, UUID idTable) {
        boolean valid = false;

        Table playingTable = getTableFromId(idTable);
        if (money > 0 && money < playingTable.getCurrentGame().getMaxStartMoney()) {
            playingTable.getCurrentGame().setMoneyOfPlayer(player,money);   //sets the money of the player in his seat
            playingTable.setNbPlayerSelectedMoney(playingTable.getNbPlayerSelectedMoney()+1);

            //TODO:integ5 myManager.getInterfaceToCom().moneyPlayers(player, money);
            //TODO:integ5 if (moneyPlayers.get(playingTable).equals(playingTable.getListPlayers().getListUserLights().size())){ // if all players confirmed their money
            //TODO:integ5    this.askReady(playingTable);    //begins the next phase
            //TODO:integ5 }

            //TODO:integ5 myManager.getInterfaceToCom().notifyNewSeat(playingTable.getListPlayers().getListUserLights(), player, money);  //transmits the new amount to every player
            valid = true;
        }
        else{
            //TODO:integ5 myManager.getInterfaceToCom().askMoneySinglePlayer(player);
        }
        return valid;
    }

    /**
     * asks all the users if they are ready to start the game through com server
     *
     */
    public void askReady(Table playingTable) {
        //TODO:integ5 myManager.getInterfaceToCom().askReady(playingTable.getListPlayers().getListUserLights());


    }

    /**
     * checks if everyone selected their starting money through the attribute nbVotes
     * @param idTable the id of the table to check
     * @return
     */
    /*
    INTEG 5: CETTE FONCTION EST INUTILE
    public boolean isEveryoneAmountMoneySelected(UUID idTable){
        Table playingTable = getTableFromId(idTable);
        return (playingTable.getNbPlayerSelectedMoney() == playingTable.getListPlayers().getListUserLights().size());
    }*/

    /**
     * receives the answers about readiness of the players
     * counts the votes to check if everyone is ready
     * if one is not, restart the process from askMoneyMax
     * @param player the UserLight of the answering player
     * @param ready true if ready, else false
     */
    public void getAnswerReady(UUID idTable, UserLight player, boolean ready) {
        //myManager.getInterfaceToCom().notifyVoteReady(ready);
        Table playingTable = getTableFromId(idTable);
        playingTable.getVote().add(ready);
        if (playingTable.getVote().size() == playingTable.getListPlayers().getListUserLights().size()){
            if (playingTable.getVote().contains(false)){
                playingTable.getVote().clear();
                //TODO:integ5 myManager.getInterfaceToCom().notifyVoteFailed(playingTable);
                //TODO:integ5 playingTable.setNbPlayerelectedMoney(0);
                askMoneyMax(idTable);
            }
            else{
                playingTable.getCurrentGame().startGame();
                //TODO:integ5 myManager.getInterfaceToCom().notifyGameStart(playingTable);
            }
        }
    }

    /**
     * asks the player which action he wants to perform
     * @param player the UserLight of the player
     */
    public void askAction(UserLight player, EnumerationAction[] availableActions) {
        Action emptyAction = new Action();
        emptyAction.setUserLightOfPlayer(player);
        //TODO: insérer un action time dedans ?
        myManager.getInterfaceToCom().askActionToPLayer(emptyAction,availableActions);
    }

    /**
     * treats the action wanted by a user
     * @param player the UserLight of the player
     * @param attempt the chosen action
     */
    public void getAction(UserLight player, Action attempt) {

    }

    public void updateStats(Game idGame){

    };

    public ArrayList<Seat> endHand(Table idTable) {
        return null;
    }

    /*
     * Permet à com de transmettre les start money des utilisateurs pour validation ou refus, et sauvegarde coté server si validation
     */
    public boolean setMoneyPlayer(UUID idTable, UserLight user, Integer startAmount){
        if(myManager.getTables().getTable(idTable).getCurrentGame().getMaxStartMoney()<startAmount && startAmount<myManager.getTables().getTable(idTable).getCurrentGame().getBlind()*2) {
            return false;
        }
        else {
            myManager.getTables().getTable(idTable).getCurrentGame().createPlayerSeat(user, startAmount);
            //Si cet utilisateur est le dernier à répondre, lancer les demandes de ready
            if(myManager.getTables().getTable(idTable).getCurrentGame().getListSeatPlayerWithPeculeDepart().size()==myManager.getTables().getTable(idTable).getListPlayers().getListUserLights().size())
            {
                myManager.getInterfaceToCom().askIfReady(myManager.getTables().getTable(idTable).getListPlayers().getListUserLights());
            }
            return true;
        }
    }

    /*
     * Permet à com de transmettre les ready answer des utilisateurs pour sauvegarde coté server
     */
    public void setReadyAnswer(UUID idTable, UserLight user, Boolean answer){
        myManager.getTables().getTable(idTable).getCurrentGame().getReadyUserAnswers().put(user,answer);
        //Si cet utilisateur est le dernier à répondre, lancer la partie
        if(myManager.getTables().getTable(idTable).getCurrentGame().getListSeatPlayerWithPeculeDepart().size()==myManager.getTables().getTable(idTable).getCurrentGame().getReadyUserAnswers().size())
        {
            //Réordonnancement des Seat pour correspondre au positionnement des joueurs (et virer les joueurs qui se seraient déconnectés entre temps, est-ce un bien ou un mal ?)
            Game game = myManager.getTables().getTable(idTable).getCurrentGame();
            ArrayList<Seat> newSeat = new ArrayList<>();
            for(UserLight usr : game.getTableOfTheGame().getListPlayers().getListUserLights())
            {
                for(Seat seat : game.getListSeatPlayerWithPeculeDepart())
                {
                    if(seat.getPlayer().equals(usr)) {
                        newSeat.add(seat);
                        break;
                    }
                }
            }
            game.setListSeatPlayerWithPeculeDepart(newSeat);

            game.startGame();
            playGame(myManager.getTables().getTable(idTable));
        }
    }



}