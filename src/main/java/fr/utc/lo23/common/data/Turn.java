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
    private Timestamp timeStampOfTurn;
    private Game currentGame;

    /**
     *  Constructor void
     *  @param currentGameToAddToNewTurn The current game from which we can the information to add to the new turn
     */
    public Turn(Game currentGameToAddToNewTurn){
        this.listAction = new ArrayList<Action>();
        this.timeStampOfTurn = new Timestamp(Calendar.getInstance().getTime().getTime());
        this.currentGame = currentGameToAddToNewTurn;
    }

    /**
     * Constructor
     * @param aListOfAction A list of action to copy into the new turn
     * @param timeStampOfTurn The time stamp to copy into the new turn
     * @param currentGameToAddToNewTurn The current game from which we can the information to add to the new turn
     */
    public Turn(ArrayList<Action> aListOfAction, Timestamp timeStampOfTurn, Game currentGameToAddToNewTurn){
        this.listAction = copyListOfActionToAvoidShallowCopy(aListOfAction);
        this.timeStampOfTurn = timeStampOfTurn;
        this.currentGame = currentGameToAddToNewTurn;
    }

    /**
     * Copy Constructor
     * @param originalTurn The original turn that we want to copy
     */
    public Turn (Turn originalTurn){
        this.listAction = copyListOfActionToAvoidShallowCopy(originalTurn.listAction);
        this.timeStampOfTurn = originalTurn.timeStampOfTurn;
        this.currentGame = originalTurn.currentGame;
    }

    /**
     * Method that take an ArrayList of Action and then return a copy of it
     * @param originalListAction ArrayList of Action original that we want to get a complete copy from without shallow copy
     * @return copy of the original ArrayList of Action
     */
    private ArrayList<Action> copyListOfActionToAvoidShallowCopy(ArrayList<Action> originalListAction){
        ListIterator<Action> listIterator = originalListAction.listIterator();
        ArrayList<Action> copyListAction = new ArrayList<Action>();
        while(listIterator.hasNext()) {
            Action elementActionOfTheArray = listIterator.next();
            copyListAction.add(elementActionOfTheArray);
        }
        return copyListAction;
    }

    /**
     * Getter to return the list of action that is associated to this turn
     * @return an ArrayList of Action from the Turn
     */
    public ArrayList<Action> getListAction() {
        return listAction;
    }

    /**
     * Getter to return the time when this turn started
     * @return a Timestamp returned that represent when the Turn started
     */
    public Timestamp getTimeStampOfTurn() {
        return timeStampOfTurn;
    }

    /**
     * Getter to return the current game with which this turn is associated
     * @return
     */
    public Game getCurrentGame() {
        return currentGame;
    }
    /**
     * Method to get the minimal bet that a player has to do, calculated according to previous Action
     * If the list of action is void, use the amount of the blinde
     * @return an integer that a player has to pay
     */
    private int minimalBet(){
        if ( getListAction().isEmpty() ){
            return currentGame.getBlind();
        }else{
            return getListAction().get(getListAction().size()-1).getAmount();
        }
    }

    /**
     * Method to check either an action is possible or not considering previous Action and the amount of money
     * @param actionToCheck The action action that we want to test
     * @return return true if the action is possible and false if not
     */
    private boolean checkActionValid(Action actionToCheck){
    //assez d'argent pour jouer
        //
        return false;//TODO change the behaviour
    }

    /**
     * Method to get the list of players that have not yet quit the game, ou qui se sont couch� , for this turn
     * @return an arrayList of UserLight representing players that can still make action on this turn
     */
    private ArrayList<UserLight> getTheListOfPlayersStillAlive(){
        return null; //TODO change behaviour
    }

    /**
     * Method the player that needs to play
     * @return UserLight of the player that has to make an Action
     */
    private UserLight nextPlayerToPlay(){
        return null; //TODO change behaviour
    }

    /**
     * Method which is designed to add a new action to the turn
     * @param newActionToAddToTheTurn Action that a Player made and that needs to be added to the turn
     */
    public void addActionToTheTurn(Action newActionToAddToTheTurn){
        //TODO need to do some check first in the case the Action is not valid
        if(newActionToAddToTheTurn==null)
            throw new NullPointerException("Action is null");
        else
            listAction.add(newActionToAddToTheTurn);
    }
}
