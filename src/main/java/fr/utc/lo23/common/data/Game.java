package fr.utc.lo23.common.data;

import fr.utc.lo23.common.data.exceptions.ActionInvalidException;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Mar on 20/10/2015.
 *
 * Class used to represent a game
 */
public class Game implements Serializable{
    private static final long serialVersionUID = 1L;
    private UUID idGame;
    private ArrayList<Hand> listHand;
    private int blind;
    private ArrayList<Seat> listSeatPlayerWithPeculeDepart;
    private Chat chatGame;
    private Timestamp timeStampStartOfTheGame;
    private int ante;
    private EnumerationStatusGame statusOfTheGame;
    private ArrayList<UserLight> listUserSpectator;
    private Table tableOfTheGame;
    private HashMap<UserLight,Boolean> readyUserAnswers;
    private Integer maxStartMoney;


    /**
     * Constructor used to initialize a game for the table, it initialize the uuid, and the status of the game to waiting for players,
     * @param tableOfTheGame Table of Game it is referred to
     * @param ante small amount of money that each player has to pay at each Hand
     * @param blind amount of money that the small blind user has to pay at the beginning of the Hand
     * @param maxStartMoney max Money to start with
     */
    public Game(Table tableOfTheGame, int ante, int blind, int maxStartMoney) {
        this.idGame = UUID.randomUUID();
        this.timeStampStartOfTheGame = null;
        this.blind = blind;
        this.listSeatPlayerWithPeculeDepart = new ArrayList<Seat>();
        this.ante = ante;
        this.listUserSpectator =  new ArrayList<UserLight>();
        this.listHand = new ArrayList<Hand>();
        this.chatGame = new Chat();
        this.statusOfTheGame = EnumerationStatusGame.Waiting;
        this.tableOfTheGame = tableOfTheGame;
        this.readyUserAnswers = new HashMap<UserLight,Boolean>();
        this.maxStartMoney = maxStartMoney;
    }

    /*
     * Method to get ArrayList of Seat of all players that still have money in their account and are not disconnected
     */
    public ArrayList<Seat> getSeatOfPlayersStillHavingMoney(){
        ArrayList<Seat> sendSeat = new ArrayList<>();
        for(Seat seat : getListSeatPlayerWithPeculeDepart())
        {
            if(seat.getStatusPlayer().equals(EnumerationStatusPlayer.CONNECTED) && seat.getCurrentAccount()>0)
            {
                sendSeat.add(seat);
            }
        }
        return sendSeat;
    }

    /**
     * Method to know the current Hand
     * @return the last Hand of the Array
     */
    public Hand getCurrentHand(){
        return this.listHand.get(this.listHand.size()-1);
    }

    /**
     * Method to add a player to the Game, it creates a new Seat with the UserLight inside
     * @param newUserLightPlayerJoinGame UserLight of the Player we want to add to the list of Seat
     */
    public void addPlayer(UserLight newUserLightPlayerJoinGame){
        this.listSeatPlayerWithPeculeDepart.add(new Seat(newUserLightPlayerJoinGame));
    }

    /**
     * Method to set a player as disconnected on the Game
     * @param userLightPlayerToRemoveFromTheGame UserLight of the player who is disconnected
     */
    public void deletePlayer(UserLight userLightPlayerToRemoveFromTheGame){
        int sizeListSeat = this.listSeatPlayerWithPeculeDepart.size();
        for(int index = 0 ; index <sizeListSeat; index++ ){
            //search the player who needs to be disconnected
            if (this.listSeatPlayerWithPeculeDepart.get(index).getPlayer().equals(userLightPlayerToRemoveFromTheGame)){
                this.listSeatPlayerWithPeculeDepart.get(index).setStatusPlayer(EnumerationStatusPlayer.DISCONNECTED);
                break;
            }
        }
    }


    /**
     * Method to remove a spectator from the Game
     * @param userLightSpectatorToRemoveFromTheGame
     */
    public void deleteSpectator(UserLight userLightSpectatorToRemoveFromTheGame ){
        int sizeListSpectator = this.listUserSpectator.size();
        for(int index = 0 ; index <sizeListSpectator; index++ ){
            //search the spectator who needs to be disconnected
            if (this.listUserSpectator.get(index).equals(userLightSpectatorToRemoveFromTheGame)){
                this.listUserSpectator.remove(index);
                break;
            }
        }
    }

    /**
     * Method to add a new spectator to the Game
     * @param newUserLightSpectatorJoinGame
     */
    public void addSpectator(UserLight newUserLightSpectatorJoinGame){
        this.listUserSpectator.add(newUserLightSpectatorJoinGame);
    }

    /**
     * Method that checks if all conditions are filled to start the game, if they are then set the timestamp of the Game and change its status to connected
     * @return true if there are a number of player corresponding to the interval of value set by the creator of the Table
     */
    public Boolean startGame(){
        if(tableOfTheGame.getListPlayers().getListUserLights().size()>=tableOfTheGame.getNbPlayerMin() && tableOfTheGame.getListPlayers().getListUserLights().size()<=tableOfTheGame.getNbPlayerMax()) {
            this.timeStampStartOfTheGame = new Timestamp(Calendar.getInstance().getTime().getTime());
            this.statusOfTheGame = EnumerationStatusGame.Playing;
            return true;
        }
        else
            return false;
    }

    /**
     * Method to stop the game, it change the status to Finished
     */
    public void stopGame(){
        this.statusOfTheGame = EnumerationStatusGame.Finished;
    }

    /**
     * Method that search the current amount of money of a Player
     * @param user UserLight of the player
     * @return current amount of money of the Player or -1 if no player was found that corresponds to the specified UserLight
     */
    public int getMoneyOfPlayer(UserLight user){
        for(Seat seat: getListSeatPlayerWithPeculeDepart())
        {
            if(user.equals(seat.getPlayer()))
                return seat.getCurrentAccount();
        }
        return -1;
    }

    /**
     * Method that change the current amount of money of the specified player
     * @param user UserLight of the player
     * @param value new current amount of money of the player
     */
    public void setMoneyOfPlayer(UserLight user, int value){
        for(Seat seat: getListSeatPlayerWithPeculeDepart())
        {
            if(user.equals(seat.getPlayer()))
                seat.setCurrentAccount(value);
        }
    }

    /**
     * Method userd to search a player and initialize its current amount and its start amount with a value
     * @param user UserLight of the Player
     * @param value int that corresponds to the amount of money
     */
    public void intializeMoneyOfPlayer(UserLight user, int value){
        for(Seat seat: getListSeatPlayerWithPeculeDepart())
        {
            if(user.equals(seat.getPlayer())){
                seat.setCurrentAccount(value);
                seat.setStartAmount(value);
            }
        }
    }

    /**
     * Method that is used to create a Seat for a player
     * @param user UserLight of a player that join the Game
     * @param startMoney smaount of money the player is gonna start with
     */
    public void createPlayerSeat(UserLight user,Integer startMoney){
        Seat seat =new Seat();
        seat.setPlayer(user);
        seat.setStartAmount(startMoney);
        seat.setCurrentAccount(startMoney);
        this.getListSeatPlayerWithPeculeDepart().add(seat);
    }

    /**
     * Method that determine if the Game is finished, it determines if there are only one player with money
     * @return true if there is only one player with money, false if there is more than one player that has money
     */
    public boolean isFinished(){
        boolean finished;
        int numberOfPlayerAlive =0;
        for(Seat seatPlayer : listSeatPlayerWithPeculeDepart){
            if(seatPlayer.getCurrentAccount()>0 && seatPlayer.getStatusPlayer().equals(EnumerationStatusPlayer.CONNECTED)){
                numberOfPlayerAlive++;
            }
        }
        if(numberOfPlayerAlive==1)
            finished = true;
        else
            finished = false;
        return finished;
    }

    /**
     * Method to search and return the Seat of a player
     * @param user UserLight of a player
     * @return Seat of a player, null if no Seat was found
     */
    public Seat getSeatOfUser(UserLight user){
        for(Seat seat : getListSeatPlayerWithPeculeDepart()){
            if(seat.getPlayer().equals(user))
                return seat;
        }
        return null;
    }


///////////////////////GETTER and SETTER
    /**
     * Get the if of the Game
     * @return int id of the Game
     */
    public UUID getIdGame() {return idGame;}

    /**
     * Get the list of Hand of the Game
     * @return ArrayList of Hand
     */
    public ArrayList<Hand> getListHand() {return listHand;}

    /**
     * Get the blind for the Game
     * @return int representing the blind
     */
    public int getBlind() {return blind;}

    /**
     * Get the list of seat
     * @return ArrayList of Seat
     */
    public ArrayList<Seat> getListSeatPlayerWithPeculeDepart() {return listSeatPlayerWithPeculeDepart;}

    /**
     * Get the chat of the Game
     * @return a Chat
     */
    public Chat getChatGame() {return chatGame;}

    /**
     *
     * @return the time when the Game starts
     */
    public Timestamp getTimeStampStartOfTheGame() {return timeStampStartOfTheGame;}

    /**
     * Get the ante which is the minimal amount of point that everyone has to pay each on each Hand
     * @return int for the ante
     */
    public int getAnte() {return ante;}

    /**
     * Get the current status of the Game
     * @return an EnumerationStatusGame (e.g : finished)
     */
    public EnumerationStatusGame getStatusOfTheGame() {return statusOfTheGame;}

    /**
     * Get the list of user which are actually Spectator
     * @return an ArrayList of Userlight
     */
    public ArrayList<UserLight> getListUserSpectator() {return listUserSpectator;}

    /**
     * Set the blind for the Game
     * @param blind int corresponding to the blind
     */
    public void setBlind(int blind) {this.blind = blind;}

    /**
     * Set the ante for the Game
     * @param ante int corresponding to the ante
     */
    public void setAnte(int ante) {this.ante = ante;}

    /**
     * Set the status of the Game
     * @param statusOfTheGame EnumerationStatusGame
     */
    public void setStatusOfTheGame(EnumerationStatusGame statusOfTheGame) {this.statusOfTheGame = statusOfTheGame;}

    /**
     * Get the maxStartMoney of the Game
     * @return Integer maxStartMoney of the Game
     */
    public Integer getMaxStartMoney() {
        return maxStartMoney;
    }

    /**
     * Set the maxStartMoney of the Game
     * @param maxStartMoney Integer
     */
    public void setMaxStartMoney(Integer maxStartMoney) {
        this.maxStartMoney = maxStartMoney;
    }

    /**
     * Get the list of vote of each player contained in an HashMap for the Ready to Start Game question
     * @return HashMap<UserLight,Boolean> containing the answer of each player
     */
    public HashMap<UserLight, Boolean> getReadyUserAnswers() {
        return readyUserAnswers;
    }

    /**
     * Get the Table of this Game
     * @return Table associated with the Game
     */
    public Table getTableOfTheGame() {
        return tableOfTheGame;
    }

    /**
     * Set the Table of this Game
     * @param tableOfTheGame Table that needs to be associated with the Game
     */
    public void setTableOfTheGame(Table tableOfTheGame) {
        this.tableOfTheGame = tableOfTheGame;
    }

    /**
     * set the list of Seat of Player for this Game
     * @param listSeatPlayerWithPeculeDepart ArrayList<Seat> containing the list of Seat for each Player
     */
    public void setListSeatPlayerWithPeculeDepart(ArrayList<Seat> listSeatPlayerWithPeculeDepart) {
        this.listSeatPlayerWithPeculeDepart = listSeatPlayerWithPeculeDepart;
    }
}

