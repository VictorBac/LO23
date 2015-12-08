package fr.utc.lo23.common.data;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.exceptions.ActionInvalidException;
import fr.utc.lo23.common.data.exceptions.CardFormatInvalidException;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by Mar on 20/10/2015.
 *
 * Class used to represent a hand (manche) in the game
 */
public class Hand implements Serializable{
    private static final long serialVersionUID = 1L;

    private ArrayList<Turn> listTurn;
    private ArrayList<Card> listCardField;
    private ArrayList<PlayerHand> listPlayerHand;
    private ArrayList<UserLight> listPlayerWin;
    private Timestamp timeStampStartOfTheHand;
    private UserLight firstPlayer;
    private ArrayList<Pot> listPotForTheHand;

    private final int NUMBER_OF_CARD_PER_PLAYER_HAND = 2;
    private final int NUMBER_OF_CARD_ON_FIELD = 5;
    private final int NUMBER_OF_CARD_IN_DECK = 52;


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


    public Hand(){
        this.listTurn = new ArrayList<Turn>();
        this.listCardField =  new ArrayList<Card>();
        this.listPlayerHand =  new ArrayList<PlayerHand>();
        this.listPlayerWin =  new ArrayList<UserLight>();
        this.firstPlayer =  null;
        this.listPotForTheHand =  new ArrayList<Pot>();
        this.timeStampStartOfTheHand = new Timestamp(Calendar.getInstance().getTime().getTime());
    }

    /**
     * Method returning the current turn of the Hand
     * @return current Turn, it corresponds to the last Turn of the ArrayList
     */
    private Turn getCurrentTurn(){
        return listTurn.get(listTurn.size()-1); //size()-1 to get the last element
    }


    /**
     * Method used to give to the each player (PlayerHand) of the Hand its card and to put cards on the Fields
     */
    public void distributeCard(){
        int[] deckCards = new int[NUMBER_OF_CARD_IN_DECK];

        int cardDrawn = 0;
        boolean nextDraw = true;
        int numberCardToDistribute = this.listPlayerHand.size() * NUMBER_OF_CARD_PER_PLAYER_HAND + NUMBER_OF_CARD_ON_FIELD;
        int newCardValue =0;
        Card[] cardsDistributed = new Card[numberCardToDistribute];

        //First Step distribution of the cards, we fill cardsDistributed (an array of cards) with new Card
        for (int indexOfCardToChooseInTheAmountOfTotalCardToDistribute = 0; indexOfCardToChooseInTheAmountOfTotalCardToDistribute < numberCardToDistribute; indexOfCardToChooseInTheAmountOfTotalCardToDistribute++){
            //until we haven't found a card that wasn't already distributed we search for a new one
            do {

                cardDrawn = randomInt(0,NUMBER_OF_CARD_IN_DECK-1);
                if(deckCards[cardDrawn]==0){ //check if the card was not already taken
                    //lock the card
                    deckCards[cardDrawn]=1;
                    // give to the player or the field its card
                    //0 to 12 for spade & 13 to 25 heart & 26 to 38 diamond & 39 to 51 club
                    newCardValue = (cardDrawn+1)%13;
                    if(newCardValue==0)
                        newCardValue = 13;
                    if(newCardValue==1) //because the as is no more the first card, it is the last one (14)
                        newCardValue = 14;
                    try {
                        if(0 <= cardDrawn && cardDrawn < 13  ){ // spade card
                            cardsDistributed[indexOfCardToChooseInTheAmountOfTotalCardToDistribute] = new Card(newCardValue,EnumerationCard.SPADE);
                        }
                        else if(13 <= cardDrawn && cardDrawn < 26 ) {// heart card
                            cardsDistributed[indexOfCardToChooseInTheAmountOfTotalCardToDistribute] = new Card(newCardValue,EnumerationCard.HEART);
                        }
                        else if (26 <= cardDrawn && cardDrawn < 39 ){// diamond card
                            cardsDistributed[indexOfCardToChooseInTheAmountOfTotalCardToDistribute] = new Card(newCardValue,EnumerationCard.DIAMOND);
                        }
                        else if(39 <= cardDrawn && cardDrawn < 52 ){// club card
                            cardsDistributed[indexOfCardToChooseInTheAmountOfTotalCardToDistribute] = new Card(newCardValue,EnumerationCard.CLUB);
                        }
                        else{
                            Console.log("Hand class- error when distribute cards");
                        }
                    } catch (CardFormatInvalidException e) {
                        e.printStackTrace();
                    }
                    nextDraw = false;
                }

            }while (nextDraw);
            nextDraw = true;
        }


        // Second Step assign each card to either a player or the field
        int indexPlus = 0;
        for(int index = 0; index < numberCardToDistribute; index++){
            Console.log("card index :"+index +" chosen"+cardsDistributed[index].getId());
            // last cards for the flop
            if(index >= numberCardToDistribute - NUMBER_OF_CARD_ON_FIELD){
                this.listCardField.add(cardsDistributed[index]);
            }
            else{
                indexPlus = index+1;
                this.getListPlayerHand().get(((indexPlus/2)+(indexPlus%2))-1).addNewCard(cardsDistributed[index]);
            }
        }


    }

    /**
     * Method that return a random number between min and max
     * @param min First number that can be return from the interval
     * @param max Last number that can be return from the interval
     * @return a random number
     */
    private int randomInt(int min, int max){
        Random random = new Random();

        //return a pseudo random number between max and min
        int result = random.nextInt(max - min + 1) + min;
        return result;
    }

    /**
     * Method that take an action that has been played and give it to the current Turn
     * @param actionNeededToBePlayed Action played on this Turn
     */
    public void playAction(Action actionNeededToBePlayed) throws ActionInvalidException {
        //TODO need to do some check First
        //TODO change the behaviour it is not the best way to do it
        listTurn.get(listTurn.size()-1).addAction(actionNeededToBePlayed);
    }

    /**
     * Method to know an order between player according to the combination of their cards and the card on the Field
     * @return an ArrayList of UserLight where the firstone had the best cards and the last had the worst cards
     */
    private ArrayList<UserLight> orderedWinner(){
        return null; //TODO change behaviour
    }


    /**
     * Method to find the PlayerHand corresponding to a specific UserLight for a Hand
     * @param userLightFromPlayer the UserLight which we search its PlayerHand
     * @return the PlayerHand from the UserLight given in parameter
     */
    public PlayerHand getPlayer(UserLight userLightFromPlayer){
        PlayerHand playerFound = null;
        for(PlayerHand p :this.listPlayerHand){
            if(p.getPlayer().equals(userLightFromPlayer)){
                playerFound = p;
                break;
            }
        }
        return playerFound;
    }


////GETTER And SETTER

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

    public void setFirstPlayer(UserLight firstPlayer) {this.firstPlayer = firstPlayer;}


    public void setListTurn(ArrayList<Turn> listTurn) {
        this.listTurn = listTurn;
    }

    public void setListCardField(ArrayList<Card> listCardField) {
        this.listCardField = listCardField;
    }

    public void setListPlayerHand(ArrayList<PlayerHand> listPlayerHand) {
        this.listPlayerHand = listPlayerHand;
    }

    public void setListPlayerWin(ArrayList<UserLight> listPlayerWin) {
        this.listPlayerWin = listPlayerWin;
    }

    public void setListPotForTheHand(ArrayList<Pot> listPotForTheHand) {
        this.listPotForTheHand = listPotForTheHand;
    }
}
