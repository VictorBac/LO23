package fr.utc.lo23.server.data;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.*;
import fr.utc.lo23.common.data.exceptions.*;
import fr.utc.lo23.common.data.exceptions.UserNotFoundException;
import fr.utc.lo23.common.network.AskMoneyMessage;
import fr.utc.lo23.exceptions.network.NetworkFailureException;

import java.util.ArrayList;
import java.util.Map;
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
            if(mode.equals(EnumerationTypeOfUser.PLAYER)) {
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

            //Envoyer ses cartes à chaque joueur
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
            System.out.println("LISTE ACTION ENVOYEE FIRST TURN: "+arrayAc);
            if(arrayAc.size()==1)
            {
                EnumerationAction[] ref = new EnumerationAction[turn.availableActions(arrayAc.get(0).getUserLightOfPlayer()).size()];
                ref = turn.availableActions(arrayAc.get(0).getUserLightOfPlayer()).toArray(ref);
                myManager.getInterfaceToCom().askActionToPLayer(table.getListPlayers().getListUserLights(),arrayAc.get(0),ref);
            }
            else
            {
                //Elle doit etre égale à 3 alors, donc les deux blindes plus l'action à demander
                try {
                    myManager.getInterfaceToCom().notifyOtherPlayerAction(table.getListPlayers().getListUserLights(),arrayAc.get(0));
                    myManager.getInterfaceToCom().notifyOtherPlayerAction(table.getListPlayers().getListUserLights(),arrayAc.get(1));
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
            //Vérifier si le tour est finit
            if(turn.isFinished())
            {
                System.out.println("Le tour est finit");
                //S'il est finit, résoudre ce tour
                turn.resolve();

                //Prévenir les utilisateurs
                myManager.getInterfaceToCom().endTurn(table.getListPlayers().getListUserLights(),hand.getPot());

                // puis vérifier si la manche est finie
                if(hand.isFinished())
                {
                    System.out.println("La hand est finit");
                    //Si elle est finit résoudre la manche

                    //S'il faut encore afficher des cartes communes, les envoyer pour affichage
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
                                myManager.getInterfaceToCom().sendCards(table.getListPlayers().getListUserLights(),players);
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

                    // Il est nécessaire de recréer des Seat, sinon la déserialization de java va donner les instances des seat déjà créés par le client local et non ceux qu'on lui envoie.
                    ArrayList<Seat> sts = new ArrayList<>();
                    for(Seat seat : game.getListSeatPlayerWithPeculeDepart()) {
                        Seat st = new Seat();
                        st.setPlayer(seat.getPlayer());
                        st.setCurrentAccount(seat.getCurrentAccount());
                        sts.add(st);
                    }

                    if(hand.getListPerformersUsers().size()>1)
                    {
                        //Envoyer les résultats du tour et les cartes des joueurs (les couchés enverront des listes vides) , TODO à optimiser en virant les playerhand des joueurs couchés
                        myManager.getInterfaceToCom().endRound(table.getListPlayers().getListUserLights(), sts, hand.getListPlayerHand());
                    }
                    else
                    {
                        //Envoyer les résultats du tour mais pas les cartes des joueurs
                        myManager.getInterfaceToCom().endRound(table.getListPlayers().getListUserLights(), sts, null);
                    }

                    //puis vérifier si la game est finie
                    if(game.isFinished())
                    {
                        System.out.println("La partie est finie !");
                        //Si la game est finie, résoudre la game

                        //Puis clore la game

                    }
                    else
                    {
                        try {
                            Thread.sleep(8000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        //sinon créer une nouvelle manche
                        hand = new Hand(game);
                        game.getListHand().add(hand);

                        //Envoyer ses cartes à chaque joueur
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

                        //et relancer l'algorithme
                        playGame(table);
                    }
                }
                else
                {

                    System.out.println("La manche n'est pas finie");
                    //sinon créer un nouveau tour
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
                        myManager.getInterfaceToCom().sendCards(table.getListPlayers().getListUserLights(),players);
                    } catch (NetworkFailureException e) {
                        e.printStackTrace();
                    }


                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //et relancer l'algorithme
                    playGame(table);
                }
            }
            else
            {
                System.out.println("Le tour n'est pas finit");
                //appeler les actions du joueur prochain
                EnumerationAction[] ref = new EnumerationAction[turn.availableActions(turn.getNextActiveUser()).size()];
                ref = turn.availableActions(turn.getNextActiveUser()).toArray(ref);
                askAction(table,turn.getNextActiveUser(),ref);
            }
        }
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

    public void replyAction(UUID table, Action playedAction) {
        try {
            myManager.getTables().getTable(table).getCurrentGame().getCurrentHand().getCurrentTurn().addAction(playedAction);
            //TODO: notifier users
            try {
                myManager.getInterfaceToCom().notifyOtherPlayerAction(myManager.getTables().getTable(table).getListPlayers().getListUserLights(),playedAction);
            } catch (NetworkFailureException e) {
                e.printStackTrace();
            }

            playGame(myManager.getTables().getTable(table));
        }
        catch(ActionInvalidException a) {
            //TODO erreur dans la reprise
            Turn turn = myManager.getTables().getTable(table).getCurrentGame().getCurrentHand().getCurrentTurn();
            EnumerationAction[] ref = new EnumerationAction[turn.availableActions(turn.getNextActiveUser()).size()];
            ref = turn.availableActions(turn.getNextActiveUser()).toArray(ref);
            askAction(myManager.getTables().getTable(table),turn.getNextActiveUser(), ref);
        }
    }

    /**
     * Updates a user info with a whole new User
     * @param newUser the new information
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
    public Table getTableFromId(UUID idTable){
        // Inutile ?  Table wantedTable = null;
        ArrayList<Table> tableList = getTableList();
        for (Table cur : tableList){
            if (cur.getIdTable().equals(idTable))
                return cur;
        }
        return null;
    }

    /**
     * asks all the players to give their max amount of money through server com
     */
    public void askMoneyMax(UUID idTable) {
        Table toAsk = getTableFromId(idTable);
       //TODO:integ5 myManager.getInterfaceToCom().askPlayersMoney(toAsk.getListPlayers().getListUserLights());
    }

    /**
     * asks the player which action he wants to perform
     * @param player the UserLight of the player
     */
    public void askAction(Table table,UserLight player, EnumerationAction[] availableActions) {
        Action emptyAction = new Action();
        emptyAction.setUserLightOfPlayer(player);
        //TODO: insérer un action time dedans ?
        myManager.getInterfaceToCom().askActionToPLayer(table.getListPlayers().getListUserLights(),emptyAction,availableActions);
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
    }

    public void checkIfEverybodyIsReady(UUID idTable){
        //Si cet utilisateur est le dernier à répondre, lancer la partie
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
                //Réordonnancement des Seat pour correspondre au positionnement des joueurs (et virer les joueurs qui se seraient déconnectés entre temps, est-ce un bien ou un mal ?)
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