package fr.utc.lo23.common.data;

import fr.utc.lo23.common.data.exceptions.ActionInvalidException;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
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

    /**
     * Constructor with all parameter
     * @param idGame
     * @param listHand
     * @param blind
     * @param listSeatPlayerWithPeculeDepart
     * @param chatGame
     * @param timeStampStartOfTheGame
     * @param ante
     * @param statusOfTheGame
     * @param listUserSpectator
     */
    public Game(UUID idGame, ArrayList<Hand> listHand, int blind, ArrayList<Seat> listSeatPlayerWithPeculeDepart, Chat chatGame, Timestamp timeStampStartOfTheGame, int ante, EnumerationStatusGame statusOfTheGame, ArrayList<UserLight> listUserSpectator) {
        this.idGame = idGame;
        this.listHand = listHand;
        this.blind = blind;
        this.listSeatPlayerWithPeculeDepart = listSeatPlayerWithPeculeDepart;
        this.chatGame = chatGame;
        this.timeStampStartOfTheGame = timeStampStartOfTheGame;
        this.ante = ante;
        this.statusOfTheGame = statusOfTheGame;
        this.listUserSpectator = listUserSpectator;
    }

    /**
     * Constructor used to initialize a game for the table, it initialize the uuid, and the status of the game to waiting for players
     */
    public Game(Table tableOfTheGame) {
        this.idGame = UUID.randomUUID();
        this.timeStampStartOfTheGame = null;
        this.blind = 0;
        this.listSeatPlayerWithPeculeDepart = new ArrayList<Seat>();
        this.ante = 0;
        this.listUserSpectator =  new ArrayList<UserLight>();
        this.listHand = new ArrayList<Hand>();
        this.chatGame = new Chat();
        this.statusOfTheGame = EnumerationStatusGame.waitingForPlayer;
        this.tableOfTheGame = tableOfTheGame;
    }

    //TODO comment those method

    /**
     * Method to know the current Hand
     * @return
     */
    public Hand getCurrentHand(){
        return this.listHand.get(this.listHand.size()-1);
    }
    private ArrayList<UserLight> getPlayerList(){
        return null;//TODO remove this line, method useless
    }


    public void addPlayer(UserLight newUserLightPlayerJoinGame){
        this.listSeatPlayerWithPeculeDepart.add(new Seat(newUserLightPlayerJoinGame));
    }

    /**
     * Method to set a player as disconnected on the Game
     * @param userLightPlayerToRemoveFromTheGame µUserLight of the player who is disconnected
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


    private UserLight getCurrentPlayer(){
        return null;//TODO remove this line
    }
    private UserLight getNextPlayer(){
        return null; //TODO remove this line
    }


    /**
     * Method that take an action that has been played and give it to the current Hand
     * @param newActionDoneByPlayer Action played on this Hand
     */
    private void playAction(Action newActionDoneByPlayer) throws ActionInvalidException {
        //TODO need to do some check First
        //TODO change the behaviour it is not the best way to do it
        getCurrentHand().playAction(newActionDoneByPlayer);
    }


    /**
     * Method to start the Game
      */
    public void startGame(){
        this.timeStampStartOfTheGame = new Timestamp(Calendar.getInstance().getTime().getTime());
        this.statusOfTheGame = EnumerationStatusGame.playing;
//TODO begin to distribute the cards
    }


    public void stopGame(){

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


    public void setBlind(int blind) {this.blind = blind;}
    public void setAnte(int ante) {this.ante = ante;}
    public void setStatusOfTheGame(EnumerationStatusGame statusOfTheGame) {this.statusOfTheGame = statusOfTheGame;}
}

