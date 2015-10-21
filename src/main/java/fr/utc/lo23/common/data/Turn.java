package fr.utc.lo23.common.data;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by Mar on 20/10/2015.
 *
 * Class used to represent a turn in the game
 */
public class Turn {

    private ArrayList<Action> listAction;
    private Timestamp timeStampOfTurn;

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
     * Method which is aimed to return the list of players that have not yet quit the game, ou qui se sont couché , for this turn
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
    private void addActionToTheTurn(Action newActionToAddToTheTurn){}
}
