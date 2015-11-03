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
    private Timestamp timeStamp;
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
     * @param timeStamp TODO see if needed or generated
     * @param ante
     * @param statusOfTheGame
     * @param listUserSpectator
     */
    public Game(UUID idGame, ArrayList<Hand> listHand, int blind, ArrayList<Seat> listSeatPlayerWithPeculeDepart, Chat chatGame, Timestamp timeStamp, int ante, EnumerationStatusGame statusOfTheGame, ArrayList<UserLight> listUserSpectator) {
        this.idGame = idGame;
        this.listHand = listHand;
        this.blind = blind;
        this.listSeatPlayerWithPeculeDepart = listSeatPlayerWithPeculeDepart;
        this.chatGame = chatGame;
        this.timeStamp = timeStamp;
        this.ante = ante;
        this.statusOfTheGame = statusOfTheGame;
        this.listUserSpectator = listUserSpectator;
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
    private void playAction(Action newActionDoneByPlayer){}
}

