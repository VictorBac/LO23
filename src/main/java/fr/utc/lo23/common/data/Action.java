package fr.utc.lo23.common.data;

import java.sql.Timestamp;

/**
 * Created by Mar on 20/10/2015.
 *
 * Class used to represent an action of a player in the game
 */
public class Action {
    private EnumerationAction name;
    private int amount;
    private UserLight userLightOfPlayer;
    private Timestamp timeStampOfAction;

    /**
     * Constructor to create an Action
     * @param name an EnumerationAction to specify the type of action
     * @param amount an int to specify how much money is bet (when action is bet)
     * @param userLightOfPlayer  attribute to characterize the player who made the action
     */
    public Action(EnumerationAction name, int amount, UserLight userLightOfPlayer) {
        this.name = name;
        //TODO check if action is bet first
        this.amount = amount;
        this.userLightOfPlayer = userLightOfPlayer;
        //TODO intialize time
    }

    /**
     * Constructor to copy anotherAction
     * @param originalAction action that we want to make a copy of
     */
    public Action(Action originalAction){
        this.name = originalAction.getName();
        //TODO check if action is bet first
        this.amount = originalAction.getAmount();
        this.userLightOfPlayer = originalAction.getUserLightOfPlayer();
        this.timeStampOfAction = originalAction.getTimeStampOfAction();
    }
    /**
     * Getter to get the type of action
     * @return type of the Action of type EnumerationAction (check, bet,...)
     */
    public EnumerationAction getName() {return name;}

    /**
     * Getter for the amount of points when the Action is to bet
     * @return amount of points bet
     */
    public int getAmount() {return amount;}

    /**
     * Getter to know who made the Action
     * @return return the UserLight associated to this Action
     */
    public UserLight getUserLightOfPlayer() {return userLightOfPlayer;}

    /**
     * Getter to know when was made the Action
     * @return time as a Timestamp
     */
    public Timestamp getTimeStampOfAction() {return timeStampOfAction;}
}
