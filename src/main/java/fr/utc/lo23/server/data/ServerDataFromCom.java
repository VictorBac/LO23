package fr.utc.lo23.server.data;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.*;
import fr.utc.lo23.common.data.exceptions.*;
import fr.utc.lo23.common.data.exceptions.UserNotFoundException;
import fr.utc.lo23.common.network.AskMoneyMessage;
import fr.utc.lo23.exceptions.network.NetworkFailureException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Remy on 03/11/2015.
 */
public class ServerDataFromCom implements InterfaceServerDataFromCom {

    private DataManagerServer myManager;
    private final String TAG = "ServerDataFromCom";

    /**
     * Constructor
     * @param manager DataManagerServer
     */
    public ServerDataFromCom(DataManagerServer manager) {
        this.myManager = manager;
    }

    /**
     * Notifies a new user connection and creates a UserLight
     * @param connectingUser User profile
     * @return the new UserLight connected
     * @throws ExistingUserException
     */
    public UserLight userConnection(User connectingUser) throws ExistingUserException {
        myManager.getUsers().addUser(connectingUser);
        //Console.log(TAG + "\tUser connected.");
        return connectingUser.getUserLight();
    }

    /**
     * Get a list of UserLight from the list of users
     * @return ArrayList<UserLight> the list of currently connected users
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
     * Get the list of current tables on the Server
     * @return ArrayList<Table> the list of current tables
     */
    public ArrayList<Table> getTableList() {
        return myManager.getTables().getListTable();
    }

    /**
     * Creates a new table on the Server
     * @param maker UserLight of the Creator of the Table
     * @param newTb Table that is created
     */
    public void createTable(UserLight maker, Table newTb) {

        myManager.getTables().newTable(newTb);
        Console.log(TAG + "\tTable created.");
    }

    /**
     * Notifies that the tables list has been modified to all observers
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
        else if (wantedTable.getCurrentGame() != null
                && wantedTable.getCurrentGame().getStatusOfTheGame().equals(EnumerationStatusGame.Playing))
        {
            ok = false;
            Console.log(TAG + "\tPlayer can't join, game has started");
        } else {
            ok = true;
        }
            Console.log(TAG + "\tPlayer can join : " + Boolean.toString(ok));
        return ok;
    }

    /**
     * Method to add a User to the Table. Throw an Exception if the User can't join the Table.
     * @param idTable UUID id of the Table the User want to join
     * @param userJoiningTable UserLight of a user that want to join the Table
     * @param mode EnumerationTypeOfUser the user can be a Spectator or a Player
     */
    public void addPlayerToTable(UUID idTable, UserLight userJoiningTable, EnumerationTypeOfUser mode) {
        Table toAdd = getTableFromId(idTable);
        try {
            if(mode.equals(EnumerationTypeOfUser.PLAYER)) {
                toAdd.playerJoinTable(userJoiningTable);
            } else {
                toAdd.spectatorJoinTable(userJoiningTable);
            }
            Console.log(TAG + "\tPlayer joined table");
        }
        catch(Exception e)
        {
            Console.log(TAG + "\tPlayer could't join table");
        }
    }

    /**
     * Method to remove a User from a Table.
     * @param idTable UUID id of the Table the User want to leave
     * @param userLeavingTable UserLight of a user that want to leave the Table
     * @param mode EnumerationTypeOfUser the user can be a Spectator or a Player
     */
    public void removePlayerFromTable(UUID idTable, UserLight userLeavingTable, EnumerationTypeOfUser mode) {
        Table toRemove = getTableFromId(idTable);
        try {
            if (mode.equals(EnumerationTypeOfUser.PLAYER)) {
                toRemove.playerLeaveTable(userLeavingTable);
                // is someone around the table ?
                if (toRemove.getListPlayers().getListUserLights().size() == 0)
                {
                    myManager.getTables().deleteTable(toRemove);
                    myManager.getInterfaceToCom().stopTable(idTable);
                }
            } else {
                toRemove.spectatorLeaveTable(userLeavingTable);
            }
        } catch (TableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to validate chat message
     * @param idTable UUId id of the Table this message is associated to
     * @param msgSent Message that is send by player or a spectator
     */
    public void saveMessageChat(UUID idTable, MessageChat msgSent){
        myManager.getTables().getTable(idTable).getCurrentGame().getChatGame().newMessage(msgSent);
    }

    /**
     * Method to send the Table containing only the Game asked to be saved
     * @param player UserLight of the user who want to save the Game locally
     * @return Table containing only the Game that matter (the Game where the User is)
     */
    public Table sendLogGame(UserLight player) {
        return null;
    }

    /**
     * Add game to table using current game
     * @param idTable UUID of the Table on which the creator of the Table wants to start a new Game
     * @param player UserLight of the Creator of the Table
     * @return True if the creation of the Table was a success, false if not
     */
    public Boolean addGameUsingCurrent(UUID idTable, UserLight player) {
        Table tableToAddGame = getTableFromId(idTable);
        if(tableToAddGame.getCurrentGame() != null && player.equals(tableToAddGame.getCreator())) {
            Game currentGame = tableToAddGame.getCurrentGame();
            try {
                tableToAddGame.addNewGameToList(currentGame.getAnte(), currentGame.getBlind(), currentGame.getMaxStartMoney());
            } catch (TableException e) {
                Console.err(e.getMessage());
                return false;
            }
            return true;
        } else {
            return false;
        }
    }


    /**
     * starts a game with a given id of the Table
     * @param idTable the id of the table of the game
     * @param creatorTable the User who has launching the game, needs to be the creator of the Table
     * @return true if the game was created, else false
     */
    public Boolean startGame(UUID idTable, UserLight creatorTable) {
        Table toStart = getTableFromId(idTable);
        if(toStart.getCurrentGame().startGame() && creatorTable.equals(toStart.getCreator()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Method that run the whole process (algorithm) of a Game
     * @param table Table where a the current Game is going to be played
     */
    public void playGame(Table table){
        Game game = table.getCurrentGame();
        Hand hand;
        Turn turn;

        if(game.getListHand().size()==0)
        {
            //At the beginning of a game
            hand = new Hand(game);
            game.getListHand().add(hand);

            //Send cards to each player
            for(PlayerHand player : hand.getListPlayerHand())
            {
                ArrayList<UserLight> users = new ArrayList<>();
                users.add(player.getPlayer());
                ArrayList<PlayerHand> players = new ArrayList<>();
                players.add(player);
                try {
                    myManager.getInterfaceToCom().sendCards(users,players);
                } catch (NetworkFailureException e) {
                    e.printStackTrace();
                }
            }
            myManager.getInterfaceToCom().notifyCardsSent(table.getListSpectators().getListUserLights());
        }
        else
        {
            //game already started
            hand = game.getCurrentHand();
        }

        if(hand.getListTurn().size()==0)
        {
            //beginning of a turn
            turn = new Turn(hand);
            hand.getListTurn().add(turn);



        }
        else
        {
            turn = hand.getCurrentTurn();
        }

        if(turn.getListAction().size()==0)
        {
            //do basics action
            ArrayList<Action> arrayAc = turn.askFirstAction();
            System.out.println("LISTE ACTION ENVOYEE FIRST TURN: "+arrayAc);
            if(arrayAc.size()==1)
            {
                EnumerationAction[] ref = new EnumerationAction[turn.availableActions(arrayAc.get(0).getUserLightOfPlayer()).size()];
                ref = turn.availableActions(arrayAc.get(0).getUserLightOfPlayer()).toArray(ref);
                myManager.getInterfaceToCom().askActionToPLayer(table.getListPlayers().getListUserLights(),arrayAc.get(0),ref);
            }
            else
            {
                //must be equal to 3 (2 blinds plus the action to ask)
                try {
                    ArrayList<UserLight> usersList = table.getListPlayers().getListUserLights();
                    usersList.addAll(table.getListSpectators().getListUserLights());
                    myManager.getInterfaceToCom().notifyOtherPlayerAction(usersList,arrayAc.get(0));
                    myManager.getInterfaceToCom().notifyOtherPlayerAction(usersList,arrayAc.get(1));
                    System.out.println("SUCCES ENVOI BLINDE");
                } catch (NetworkFailureException e) {
                    System.out.println("ERROR ENVOI BLINDE");
                    e.printStackTrace();
                }
                EnumerationAction[] ref = new EnumerationAction[turn.availableActions(arrayAc.get(2).getUserLightOfPlayer()).size()];
                ref = turn.availableActions(arrayAc.get(2).getUserLightOfPlayer()).toArray(ref);
                myManager.getInterfaceToCom().askActionToPLayer(table.getListPlayers().getListUserLights(),arrayAc.get(2),ref);
            }
        }
        else
        {
            //check if the turn is over
            if(turn.isFinished())
            {
                System.out.println("Le tour est finit");
                //if yes, resolve turn
                turn.resolve();
                ArrayList<UserLight> usersList = table.getListPlayers().getListUserLights();
                usersList.addAll(table.getListSpectators().getListUserLights());
                //alert players
                myManager.getInterfaceToCom().endTurn(usersList,hand.getPot());

                // then check again if turn is over
                if(hand.isFinished())
                {
                    System.out.println("La hand est finit");
                    //if yes, resolve turn

                    //if needed, send card to be displayed
                    if(hand.getListPerformersUsers().size()>1) {
                        if(hand.getListTurn().size()<4)
                        {
                            PlayerHand playSend = new PlayerHand();
                            playSend.setPlayer(null);
                            ArrayList<Card> cards = new ArrayList<>();

                            if(hand.getListTurn().size()==1)
                            {
                                cards.add(hand.getListCardField().get(0));
                                cards.add(hand.getListCardField().get(1));
                                cards.add(hand.getListCardField().get(2));
                            }
                            if(hand.getListTurn().size()<=2)
                            {
                                cards.add(hand.getListCardField().get(3));
                            }
                            cards.add(hand.getListCardField().get(4));

                            playSend.setListCardsHand(cards);
                            ArrayList<PlayerHand> players = new ArrayList<>();
                            players.add(playSend);

                            try {
                                myManager.getInterfaceToCom().sendCards(usersList,players);
                            } catch (NetworkFailureException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    hand.resolve();

                    try {

                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //it is necessary to create Seats, otherwise, JAVA deserilization will return already created Seats instances by local client
                    ArrayList<Seat> sts = new ArrayList<>();
                    for(Seat seat : game.getListSeatPlayerWithPeculeDepart()) {
                        Seat st = new Seat();
                        st.setPlayer(seat.getPlayer());
                        st.setCurrentAccount(seat.getCurrentAccount());
                        sts.add(st);
                    }

                    if(hand.getListPerformersUsers().size()>1)
                    {
                        //send results and players cards
                        //TODO optimize by excluding dropped players
                        myManager.getInterfaceToCom().endRound(usersList, sts, hand.getListPlayerHand());
                    }
                    else
                    {
                        //Send results but not the cards
                        myManager.getInterfaceToCom().endRound(usersList, sts, null);
                    }

                    //then check again if the turn is over
                    if(game.isFinished())
                    {
                        System.out.println("La partie est finie !");
                        //if game is over, resolve game
                        //TODO : Résoudre la game ?
                        //then close it

                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        game.stopGame();
                        myManager.getInterfaceToCom().endGame(usersList);
                    }
                    else
                    {
                        try {
                            Thread.sleep(8000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        //otherwise new turn
                        hand = new Hand(game);
                        game.getListHand().add(hand);

                        //Send card to each player
                        for(PlayerHand player : hand.getListPlayerHand())
                        {
                            ArrayList<UserLight> users = new ArrayList<>();
                            users.add(player.getPlayer());
                            users.addAll(table.getListSpectators().getListUserLights());
                            ArrayList<PlayerHand> players = new ArrayList<>();
                            players.add(player);
                            try {
                                myManager.getInterfaceToCom().sendCards(users,players);
                            } catch (NetworkFailureException e) {
                                e.printStackTrace();
                            }
                        }
                        myManager.getInterfaceToCom().notifyCardsSent(table.getListSpectators().getListUserLights());

                        //call the algorithm back
                        playGame(table);
                    }
                }
                else
                {

                    System.out.println("La manche n'est pas finie");
                    //otherwise new turn
                    turn = new Turn(hand);
                    hand.getListTurn().add(turn);

                    PlayerHand playSend = new PlayerHand();
                    playSend.setPlayer(null);
                    ArrayList<Card> cards = new ArrayList<>();

                    if(hand.getListTurn().size()==2)
                    {
                        cards.add(hand.getListCardField().get(0));
                        cards.add(hand.getListCardField().get(1));
                    }
                    cards.add(hand.getListCardField().get(hand.getListTurn().size()));

                    playSend.setListCardsHand(cards);
                    ArrayList<PlayerHand> players = new ArrayList<>();
                    players.add(playSend);
                    try {
                        myManager.getInterfaceToCom().sendCards(usersList,players);
                    } catch (NetworkFailureException e) {
                        e.printStackTrace();
                    }


                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //and relaunch algorithm
                    playGame(table);
                }
            }
            else
            {
                System.out.println("Le tour n'est pas finit");
                //call next player actions
                EnumerationAction[] ref = new EnumerationAction[turn.availableActions(turn.getNextActiveUser()).size()];
                ref = turn.availableActions(turn.getNextActiveUser()).toArray(ref);
                askAction(table,turn.getNextActiveUser(),ref);
            }
        }
    }

    /**
     * Method used to ask for the next step of the Game that is replayed
     * @param idTable UUID idTable on which is replayed the Game
     * @param idGame UUID of the Game which is replayed
     * @param master UserLight of the spectator who has decided to replay the Game
     */
    public void nextStepReplay(UUID idTable, UUID idGame, UserLight master) {
    }

    /**
     * Deletes a player from the list connected users
     * @param deletedUsr the user to delete
     * @throws fr.utc.lo23.common.data.exceptions.UserNotFoundException if the UserLight was not found in the list of connected users
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

    /**
     * Method that handles an Action that was played by a User
     * @param table UUID id of the Table on which the player has made his Action
     * @param playedAction Action that was played
     */
    public void replyAction(UUID table, Action playedAction) {
        try {
            Table t = myManager.getTables().getTable(table);
            t.getCurrentGame().getCurrentHand().getCurrentTurn().addAction(playedAction);
            //TODO notify users
            try {
                ArrayList<UserLight> usersList = t.getListPlayers().getListUserLights();
                usersList.addAll(t.getListSpectators().getListUserLights());
                myManager.getInterfaceToCom().notifyOtherPlayerAction(usersList,playedAction);
            } catch (NetworkFailureException e) {
                e.printStackTrace();
            }

            playGame(myManager.getTables().getTable(table));
        }
        catch(ActionInvalidException a) {
            //TODO fix error
            Turn turn = myManager.getTables().getTable(table).getCurrentGame().getCurrentHand().getCurrentTurn();
            EnumerationAction[] ref = new EnumerationAction[turn.availableActions(turn.getNextActiveUser()).size()];
            ref = turn.availableActions(turn.getNextActiveUser()).toArray(ref);
            askAction(myManager.getTables().getTable(table),turn.getNextActiveUser(), ref);
        }
    }

    /**
     * Method to update a user info with a whole new User
     * @param newUser the new profile of the user
     */
    public void updateProfile(User newUser) {
        myManager.getUsers().changeUserProfile(newUser);
        UserLight newUserLightProfile = null;
        try {
            newUserLightProfile = myManager.getUsers().getUser(newUser.getUserLight().getIdUser()).getUserLight();
        } catch (fr.utc.lo23.common.data.UserNotFoundException e) {
            e.printStackTrace();
        }
        myManager.getInterfaceToCom().notifyProfilUpdated(newUserLightProfile);
    }

    /**
     * Get the User corresponding to a UserLight in the Userlist of mymanager
     * @param core the UserLight corresponding to the Profile that is searched
     * @return the corresponding User, null if not found
     */
    public User getProfile(UserLight core) {
        for (User cur : myManager.getUsers().getList()) {
            if (cur.getUserLight().equals(core))
                return cur;
        }
        return null;
    }

    /**
     * Get a whole User which is connected on a server that corresponds to a specific UUID
     * @param userId UUID which is the id of the User
     * @return User containing the profile of the User, null if no User corresponds to the UUID
     */
    public User getUserById(UUID userId)
    {
        for (User cur : myManager.getUsers().getList()){
            if (cur.getUserLight().getIdUser().equals(userId))
                return cur;
        }
        return null;
    }

    /**
     * Get the list of all the players of the given table
     * @param tableID the UUID of the table to look for
     * @return an ArrayList of UserLight with all the players
     */
    public ArrayList<UserLight> getPlayersByTable(UUID tableID){
        Table current = getTableFromId(tableID);
        return current.getListPlayers().getListUserLights();
    }

    /**
     * Get the list of all the players and spectator of the given table
     * @param tableID the UUID of the table to look for
     * @return an ArrayList of UserLight with all the players and Spectators
     */
    public ArrayList<UserLight> getUsersByTable(UUID tableID){
        Table current = getTableFromId(tableID);
        ArrayList<UserLight> listOfUser = new ArrayList<>();
        listOfUser.addAll( current.getListPlayers().getListUserLights());
        listOfUser.addAll( current.getListSpectators().getListUserLights());
        return listOfUser;
    }

    /**
     * Get a Table corresponding to a specific UUID
     * @param idTable UUID id of the Table to look for
     * @return Table if found, else null
     */
    public Table getTableFromId(UUID idTable){
        ArrayList<Table> tableList = getTableList();
        for (Table cur : tableList){
            if (cur.getIdTable().equals(idTable))
                return cur;
        }
        return null;
    }


    /**
     * Asks a player which action he wants to perform, with a list of possible Action to perform
     * @param table Table where is the player
     * @param player UserLight of the player that needs to perform an action
     * @param availableActions EnumerationAction[] an array that contain the Action the player can perform
     */
    public void askAction(Table table,UserLight player, EnumerationAction[] availableActions) {
        Action emptyAction = new Action();
        emptyAction.setUserLightOfPlayer(player);
        //TODO: insérer un action time dedans ?
        myManager.getInterfaceToCom().askActionToPLayer(table.getListPlayers().getListUserLights(),emptyAction,availableActions);
    }

    /**
     * Method to update the stats of the players that have played a Game
     * @param idTable UUID id of the Table
     * @param idGame UUID id of the Game that will be used to updated the Stats of the Users
     */
    public void updateStats(UUID idTable,UUID idGame){
    };


    /**
     * Method to notify the players about the money a Player decide to start with.
     * It checks the amount if it is a valid amount then save it on the server else refuse it
     * Allow Com module to transmit the startmoney of the users for validation or rejection, and save it on the server side if ok
     * @param idTable UUID idTable
     * @param user UserLight of the player who has decided of its amount
     * @param startAmount Integer amount of money the player is going to start playing with
     * @return true if the amount is valid, else false
     */
    public boolean setMoneyPlayer(UUID idTable, UserLight user, Integer startAmount){
        if(myManager.getTables().getTable(idTable).getCurrentGame().getMaxStartMoney()<startAmount && startAmount<myManager.getTables().getTable(idTable).getCurrentGame().getBlind()*2) {
            return false;
        }
        else {
            myManager.getTables().getTable(idTable).getCurrentGame().createPlayerSeat(user, startAmount);
            //If this user is the last to answer then send the ready to start request
            if(myManager.getTables().getTable(idTable).getCurrentGame().getListSeatPlayerWithPeculeDepart().size()==myManager.getTables().getTable(idTable).getListPlayers().getListUserLights().size())
            {
                myManager.getInterfaceToCom().askIfReady(myManager.getTables().getTable(idTable).getListPlayers().getListUserLights());
            }
            return true;
        }
    }

    /**
     * Method to save the ready answer of the players on the server
     * @param idTable UUID id of the Table
     * @param user UserLight of a player who send its answer
     * @param answer Boolean answer of the player, true if he is ready to start the Game, else false
     */
    public void setReadyAnswer(UUID idTable, UserLight user, Boolean answer){
        myManager.getTables().getTable(idTable).getCurrentGame().getReadyUserAnswers().put(user,answer);
    }

    /**
     * Method to check if all the player of a Table are ready to start the Game
     * @param idTable UUID id of a Table
     */
    public void checkIfEverybodyIsReady(UUID idTable){
        //If this user is the last to answer then start the Game
        if(myManager.getTables().getTable(idTable).getCurrentGame().getListSeatPlayerWithPeculeDepart().size()==myManager.getTables().getTable(idTable).getCurrentGame().getReadyUserAnswers().size())
        {
            Boolean refuse = false;
            for (Map.Entry<UserLight, Boolean> entry : myManager.getTables().getTable(idTable).getCurrentGame().getReadyUserAnswers().entrySet())
            {
                if(!entry.getValue()) {
                    refuse = true;
                    break;
                }
            }

            if(!refuse) {
                //Re-order the Seat to correspond to the position of the players (and remove players that were disconnected during the preparation phase. Correct?)
                Game game = myManager.getTables().getTable(idTable).getCurrentGame();
                ArrayList<Seat> newSeat = new ArrayList<>();
                for (UserLight usr : game.getTableOfTheGame().getListPlayers().getListUserLights()) {
                    for (Seat seat : game.getListSeatPlayerWithPeculeDepart()) {
                        if (seat.getPlayer().equals(usr)) {
                            newSeat.add(seat);
                            break;
                        }
                    }
                }
                game.setListSeatPlayerWithPeculeDepart(newSeat);

                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                game.startGame();
                playGame(myManager.getTables().getTable(idTable));
            }
            else
            {
                myManager.getTables().getTable(idTable).getCurrentGame().getReadyUserAnswers().clear();
                myManager.getTables().getTable(idTable).getCurrentGame().getListSeatPlayerWithPeculeDepart().clear();
                myManager.getInterfaceToCom().askMoneyAmount(idTable);
            }
        }
    }



}