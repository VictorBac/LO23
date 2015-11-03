package fr.utc.lo23.common.data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Mar on 20/10/2015.
 *
 * Class used to represent a game
 */
public class Game {
    private UUID idGame;
    private ArrayList<Hand> listHand;
    private int blind;
    private ArrayList<Seat> listSeatPlayerWithPeculeDepart;
    private Chat chatGame;
    private Timestamp timeStampStartOfTheGame; //TODO should we start only when status game is on playing??
    private int ante;
    private EnumerationStatusGame statusOfTheGame;
    private ArrayList<UserLight> listUserSpectator;

    /**
     * Constructor with all parameter
     * @param idGame TODO see if needed or generated
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

    public Game(UUID idGame, Timestamp timeStampStartOfTheGame, int blind, ArrayList<Seat> listSeatPlayerWithPeculeDepart, int ante, ArrayList<UserLight> listUserSpectator) {
//TODO need to do a nullpointerexception and parameter exception just as in Action
        this.idGame = idGame;
        this.timeStampStartOfTheGame = timeStampStartOfTheGame;
        this.blind = blind;
        this.listSeatPlayerWithPeculeDepart = listSeatPlayerWithPeculeDepart;
        this.ante = ante;
        this.listUserSpectator = listUserSpectator;
        this.listHand = new ArrayList<Hand>();
        this.chatGame = new Chat();
        this.statusOfTheGame = EnumerationStatusGame.waitingForPlayer;

    }

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
    public Timestamp getTimeStamp() {return timeStamp;}

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


    //TODO comment those method

    private Hand getCurrentHand(){
        return null;//TODO remove this line
    }


    private ArrayList<UserLight> getPlayerList(){
        return null;//TODO remove this line
    }


    private void addPlayer(UserLight newUserLightPlayerJoinGame){}
    private void deletePlayer(UserLight userLightPlayerToRemoveFromTheGame){}

    private UserLight getNextPlayer(){
        return null; //TODO remove this line
    }

    private void deleteSpectator(UserLight newUserLightSpectatorJoinGame){}
    private void addSpectator(UserLight userLightSpectatorToRemoveFromTheGame){}
    private void getCurrentPlayer(){}

    /**
     * Method that take an action that has been played and give it to the current Hand
     * @param newActionDoneByPlayer Action played on this Hand
     */
    private void playAction(Action newActionDoneByPlayer){
        //TODO need to do some check First
        //TODO change the behaviour it is not the best way to do it
        listHand.get(listHand.size()-1).playAction(newActionDoneByPlayer);
    }
}

