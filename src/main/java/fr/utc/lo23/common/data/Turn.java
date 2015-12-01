package fr.utc.lo23.common.data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by Ying on 20/10/2015.
 *
 * Class used to represent a turn in the game
 */
public class Turn implements Serializable {

    private ArrayList<Action> listAction;
    private Timestamp timeStampOfTurn;

    /**
     * Constructor to create a Table with every fields
     * @param aListOfAction the List of Action to added
     */
    public Turn(ArrayList<Action> aListOfAction,Timestamp timeStampOfTurn){
        this.listAction = aListOfAction;
        this.timeStampOfTurn = timeStampOfTurn;
    }

    /**
     *  Constructor mainly used when we start a new Turn in a normal Game
     * @param timeStampOfTurn currentTime
     */
    public Turn(Timestamp timeStampOfTurn){
        this.listAction = new ArrayList<Action>();
        this.timeStampOfTurn = timeStampOfTurn;
    }

    /**
     * Constructor used to get a new Turn that is a copy from an other one
     * @param originalTurn Turn original that we want to copy
     */
    public Turn (Turn originalTurn){
        this.listAction = copyListOfActionToAvoidShallowCopy(originalTurn.listAction);
        this.timeStampOfTurn = originalTurn.timeStampOfTurn;
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
     * Getter that return the list of action that is associated to this turn
     * @return an ArrayList of Action from the Turn
     */
    public ArrayList<Action> getListAction() {
        return listAction;
    }

    /**
     * Getter that return the time when this turn started
     * @return a Timestamp returned that represent when the Turn started
     */
    public Timestamp getTimeStampOfTurn() {
        return timeStampOfTurn;
    }

    /**
     * Minimal bet that a player has to do, calculated according to previous Action
     * @return an integer that a player has to pay
     */
    private int minimalBet(){
        return 0; //TODO change this to the real number
    }

    /**
     * Method to check of either an action is possible or not considering previous Action and the amount of money
     * @param actionToCheck The action action that we want to test
     * @return return true if the action is possible and false if not
     */
    private boolean checkActionValid(Action actionToCheck){

        return false;//TODO change the behaviour
    }

    /**
     * Method which is aimed to return the list of players that have not yet quit the game, ou qui se sont couch� , for this turn
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
