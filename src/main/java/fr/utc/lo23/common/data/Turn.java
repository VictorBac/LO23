package fr.utc.lo23.common.data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ListIterator;

/**
 * Created by Ying on 20/10/2015.
 *
 * Class used to represent a turn in the game
 */
public class Turn implements Serializable {

    private static final long serialVersionUID = 1L;
    private ArrayList<Action> listAction;
    private ArrayList<UserLight> listPlayerInThisTurn;
    private Game currentGame;
    private Timestamp timeStampOfTurn;
    /**
     *  Constructor void
     *  @param currentGameToAddToNewTurn The current game from which we can the information to add to the new turn
     */
    public Turn(Game currentGameToAddToNewTurn){
        //TODO add a copy constructor in the class Game
        this.listAction = new ArrayList<Action>();
        this.timeStampOfTurn = new Timestamp(Calendar.getInstance().getTime().getTime());
        this.currentGame = currentGameToAddToNewTurn;
        for (Seat item : currentGameToAddToNewTurn.getListSeatPlayerWithPeculeDepart()
             ) {
            this.listPlayerInThisTurn.add(item.getPlayer());
        }
    }

    /**
     * Copy Constructor
     * @param originalTurn The original turn that we want to copy
     */
    public Turn (Turn originalTurn){
        this.listAction = originalTurn.listAction;
        this.listPlayerInThisTurn = originalTurn.listPlayerInThisTurn;
        this.timeStampOfTurn = originalTurn.timeStampOfTurn;
        this.currentGame = originalTurn.currentGame;
    }

    /**
     * Method to get the current action
     * @return the action now
     */
    public Action getCurrentAction(){
        return listAction.get(listAction.size()-1);
    }

    /**
     * Method the player that needs to play
     * @return UserLight of the player that has to make an Action
     */
    public UserLight getNextPlayer(){
        if ( listPlayerInThisTurn.get(getListAction().size()-1) == getCurrentAction().getUserLightOfPlayer() ){
            return listPlayerInThisTurn.get(0);
        }else{
            return listPlayerInThisTurn.get( listPlayerInThisTurn.indexOf(getCurrentAction().getUserLightOfPlayer()) + 1 );
        }
    }

    /**
     * Method to update the list of players that have not folded for this turn
     * @return an arrayList of UserLight representing players that can still make action on this turn
     */
    public ArrayList<UserLight> updateListPlayerInThisTurn(UserLight currentPlayer) {
        listPlayerInThisTurn.remove(currentPlayer);
        return listPlayerInThisTurn;
    }

    /**
     * Method to get the minimal bet that a player has to call, calculated according to previous Action
     * If the list of action is void, use the amount of the blinde
     * @return an integer that a player has to pay at least
     */
    public int minimalBet(){
        if ( listAction.isEmpty() ){
            return currentGame.getBlind();
        }else{
            return getCurrentAction().getAmount();
        }
    }

    /**
     * Method to check either an action is possible or not considering previous Action and the amount of money
     * @param actionToCheck The action action that we want to test
     * @return return true if the action is possible and false if not
     */
    private boolean checkActionValid(Action actionToCheck){
        if ( actionToCheck.getName().name() == "fold" ){
            addAction(actionToCheck);
            updateListPlayerInThisTurn(actionToCheck.getUserLightOfPlayer());
            return true;
        }else if ( actionToCheck.getName().name() == "bet" ){
            return true;
        }
        return true;
    }

    /**
     * Method which is designed to add a new action to the turn
     * @param newAction Action that a Player made and that needs to be added to the turn
     */
    public void addAction(Action newAction){
        //TODO need to do some check first in the case the Action is not valid
        if(newAction==null)
            throw new NullPointerException("Action is null");
        else
            listAction.add(newAction);
    }




    /*********************Getters & Setters*********************/

    /**
     * Getter to return the list of action that is associated to this turn
     * @return an ArrayList of Action in this Turn
     */
    public ArrayList<Action> getListAction() {
        return listAction;
    }

    /**
     * Getter to return the list of player who can still make an action in this turn
     * @return an ArrayList of UserLight
     */
    public ArrayList<UserLight> getListPlayerInThisTurn() {
        return listPlayerInThisTurn;
    }

    /**
     * Getter to return the current game with which this turn is associated
     * @return the information of current game
     */
    public Game getCurrentGame() {
        return currentGame;
    }

    /**
     * Getter to return the time when this turn started
     * @return a Timestamp returned that represent when the Turn started
     */
    public Timestamp getTimeStampOfTurn() {
        return timeStampOfTurn;
    }

    /**
     * Constructor
     * @param aListOfAction A list of action to copy into the new turn
     * @param timeStampOfTurn The time stamp to copy into the new turn
     * @param currentGameToAddToNewTurn The current game from which we can the information to add to the new turn
     */
    /*
    public Turn(ArrayList<Action> aListOfAction, Timestamp timeStampOfTurn, Game currentGameToAddToNewTurn){
        this.listAction = copyListOfActionToAvoidShallowCopy(aListOfAction);
        this.timeStampOfTurn = timeStampOfTurn;
        this.currentGame = currentGameToAddToNewTurn;
        for (Seat item : currentGameToAddToNewTurn.getListSeatPlayerWithPeculeDepart()
                ) {
            this.listPlayerActif.add(item.getPlayer());
        }
    }
    */

    /**
     * Method that take an ArrayList of Action and then return a copy of it
     * @param originalListAction ArrayList of Action original that we want to get a complete copy from without shallow copy
     * @return copy of the original ArrayList of Action
     */
    /*
    private ArrayList<Action> copyListOfActionToAvoidShallowCopy(ArrayList<Action> originalListAction){
        ListIterator<Action> listIterator = originalListAction.listIterator();
        ArrayList<Action> copyListAction = new ArrayList<Action>();
        while(listIterator.hasNext()) {
            Action elementActionOfTheArray = listIterator.next();
            copyListAction.add(elementActionOfTheArray);
        }
        return copyListAction;
    }
    */
}
