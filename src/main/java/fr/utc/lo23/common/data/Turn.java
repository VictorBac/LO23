package fr.utc.lo23.common.data;

import fr.utc.lo23.common.data.exceptions.ActionInvalidException;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
//import java.util.ListIterator;

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
     *  @param currentGameToAdd The current game from which we can the information to add to the new turn
     */
    public Turn(Game currentGameToAdd){
        this.listAction = new ArrayList<Action>();
        this.timeStampOfTurn = new Timestamp(Calendar.getInstance().getTime().getTime());
        this.currentGame = currentGameToAdd;
        for (Seat item : currentGameToAdd.getListSeatPlayerWithPeculeDepart()
             ) {
            this.listPlayerInThisTurn.add(item.getPlayer());
        }
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
     * Method which is designed to add a new action to the turn
     * @param newAction Action that a Player made and that needs to be added to the turn
     */
    public void addAction(Action newAction) throws ActionInvalidException{
        if ( newAction == null )
            throw new NullPointerException("Action is null");
        if ( newAction.getName().name() == "fold" ){
            listAction.add(newAction);
            listPlayerInThisTurn.remove(getCurrentAction().getUserLightOfPlayer());
        }
        if ( newAction.getName().name() == "check" ){
            if ( newAction.getAmount() < minimalBet() )
                throw new ActionInvalidException("You cannot check when your amount is less than the minimum.");
            else
                listAction.add(newAction);
        }
        if ( newAction.getName().name() == "call" ){
            if ( newAction.getAmount() != minimalBet() )
                throw new ActionInvalidException("You cannot call when your amount is different from the minimum.");
            else
                listAction.add(newAction);
        }

        //TODO need to do some check first in the case the Action is not valid

    }




    /*********************Getters*********************/

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

}
