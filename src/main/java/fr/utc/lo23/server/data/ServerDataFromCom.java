package fr.utc.lo23.server.data;

import com.sun.xml.internal.ws.developer.*;
import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.*;
import fr.utc.lo23.common.data.exceptions.*;
import fr.utc.lo23.common.data.exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by R�my on 03/11/2015.
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
        Console.log(TAG + "\tUser connected.");
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
        Console.log(TAG + "\tListe des joueurs.");
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
    public Game startGame(UUID idTable, UserLight player) {
        Table toStart = getTableFromId(idTable);
        try {
            toStart.startGame(toStart.getCurrentGame());

            //LANCEMENT DE L'ALGORITHME DE JEU
            playGame(toStart);

            Console.log(TAG + "\tGame started.");
            return toStart.getCurrentGame();
        }
        catch(TableException e){
            Console.log(TAG + "\tGame failed to start.");
            return null;
        }
    }

    public void playGame(Table table){
        Boolean play = true;
        Game game = table.getCurrentGame();

        //Choix du joueur initial
        //On choisit de prendre le premier joueur dans la liste (l'host s'il n'a pas quitté la table, d'ailleurs je sais pas comment on gère ce cas).
        UserLight firstPlayer = table.getListPlayers().getListUserLights().get(0);

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

         */



    }

    public void nextStepReplay() {

    }

    /**
     * deletes a player from the list connectedusers
     * @param deletedUsr the user to delete
     */
    public void deletePlayer(UserLight deletedUsr) throws UserNotFoundException {
        boolean found = false;
        for (User current : myManager.getUsers().getList()) {
            if (current.getUserLight().equals(deletedUsr))
                found = true;
            myManager.getUsers().getList().remove(current);
            Console.log(TAG + "\tPlayer deleted.");
        }
        if (found == false)
            throw new fr.utc.lo23.common.data.exceptions.UserNotFoundException(deletedUsr.getIdUser());
    }

    public void confirmationCardReceived(UserLight player) {

    }

    public Action replyAction(Action playedAction, UserLight player) {
        return null;
    }

    public void confirmationActionReceived(UserLight sender) {

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
        ArrayList<UserLight> players = new ArrayList<UserLight>();
        Table current = getTableFromId(tableID);
        return current.getListPlayers().getListUserLights();
    }

    /**
     * looks for the table with the corresponding UUID
     * @param idTable the id to look for
     * @return the table if found, else null
     */
    private Table getTableFromId(UUID idTable){
        Table wantedTable = null;
        ArrayList<Table> tableList = getTableList();
        for (Table cur : tableList){
            if (cur.getIdTable().equals(idTable))
                return cur;
        }
        return null;
    }
}