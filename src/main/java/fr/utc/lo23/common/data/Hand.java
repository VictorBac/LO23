package fr.utc.lo23.common.data;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by Mar on 20/10/2015.
 *
 * Class used to represent a hand (manche) in the game
 */
public class Hand {

    private ArrayList<Turn> listTurn;
    private ArrayList<Card> listCardField;
    private ArrayList<PlayerHand> listPlayerHand;
    private ArrayList<UserLight> listPlayerWin;
    private Timestamp timeStampStartOfTheHand;
    private UserLight firstPlayer;
    private ArrayList<Pot> listPotForTheHand;


    /**
     * Constructor with only timeStamp argument to initialize the Hand
     * @param timeStampStartOfTheHand the current time when the Hand started
     */
    public Hand(Timestamp timeStampStartOfTheHand){
        this.listTurn = new ArrayList<Turn>();
        this.listCardField =  new ArrayList<Card>();
        this.listPlayerHand =  new ArrayList<PlayerHand>();
        this.listPlayerWin =  new ArrayList<UserLight>();
        this.firstPlayer =  null;
        this.listPotForTheHand =  new ArrayList<Pot>();
        this.timeStampStartOfTheHand = timeStampStartOfTheHand;
    }

    public Hand(ArrayList<Turn> listTurn, ArrayList<Card> listCardField, ArrayList<PlayerHand> listPlayerHand, ArrayList<UserLight> listPlayerWin, UserLight firstPlayer, ArrayList<Pot> listPotForTheHand, Timestamp timeStampStartOfTheHand ) {
        this.listTurn = listTurn;
        this.listCardField = listCardField;
        this.listPlayerHand = listPlayerHand;
        this.listPlayerWin = listPlayerWin;
        this.firstPlayer = firstPlayer;
        this.listPotForTheHand = listPotForTheHand;
        this.timeStampStartOfTheHand = timeStampStartOfTheHand;
    }

    /**
     * Getter that return the lis of turn of the Hand
     * @return ArrayList of Turn
     */
    public ArrayList<Turn> getListTurn() {return listTurn;}

    /**
     * Getter tha t return the list of card currently on the field
     * @return Arraylist of Card (all cards should be set there when the turn begin)
     */
    public ArrayList<Card> getListCardField() {return listCardField;}

    /**
     * Getter that return the list of Player Hand
     * @return a list of PlayerHand
     */
    public ArrayList<PlayerHand> getListPlayerHand() {return listPlayerHand;}

    /**
     * Getter that return the list of the play who have win on the Hand
     * @return a list of UserLight which can be used to distribute the amount of point won
     */
    public ArrayList<UserLight> getListPlayerWin() {return listPlayerWin;}

    /**
     * Getter that return the Time when the Hand starts
     * @return a Timestamp relative to the Time when the Hand starts
     */
    public Timestamp getTimeStampStartOfTheHand() {return timeStampStartOfTheHand;}

    /**
     * Getter that return the UserLight of the First player of the Hand
     * @return a UserLight
     */
    public UserLight getFirstPlayer() {return firstPlayer;}

    /**
     * Getter that return the list of Pot for the Hand, there can be multiple Por for one Hand and for each Pot some players associated
     * @return an ArrayList of Pot
     */
    public ArrayList<Pot> getListPotForTheHand() {return listPotForTheHand;}





    /**
     * Method returning the current turn of the Hand
     * @return current Turn, it corresponds to the last Turn of the ArrayList
     */
    private Turn getCurrentTurn(){
        return listTurn.get(listTurn.size()-1); //size()-1 to get the last element
    }


    /**
     * Method used to give to each player (PlayerHand) its card and to put cards on the Fields
     */
    private void distributeCard(){}

    /**
     * Method that take an action that has been played and give it to the current Turn
     * @param actionNeededToBePlayed Action played on this Turn
     */
    public void playAction(Action actionNeededToBePlayed){
        //TODO need to do some check First
        //TODO change the behaviour it is not the best way to do it
        listTurn.get(listTurn.size()-1).addActionToTheTurn(actionNeededToBePlayed);
    }

    /**
     * Method to know an order between player according to the combination of their cards and the card on the Field
     * @return an ArrayList of UserLight where the firstone had the best cards and the last had the worst cards
     */
    private ArrayList<UserLight> orderedWinner(){
        return null; //TODO change behaviour
    }
}
