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
     * @param timeStampOfAction  to hold the time when the Action was created
     */
    public Action(EnumerationAction name, int amount, UserLight userLightOfPlayer, Timestamp timeStampOfAction) {
        this.name = name;
        //check if action is bet first
        if(this.name.equals(EnumerationAction.bet)){ //TODO check if there is only bet
            this.amount = amount;
        }
        else{
            this.amount = 0; //if it is not bet then there is no point to have an amount

        }
        this.userLightOfPlayer = userLightOfPlayer;
        this.timeStampOfAction = timeStampOfAction;
    }

    /**
     * Constructor to copy anotherAction
     * @param originalAction action that we want to make a copy of
     */
    public Action(Action originalAction){
        this.name = originalAction.name;
        if(this.name.equals(EnumerationAction.bet)){ //TODO check if there is only bet
            this.amount = originalAction.amount;        }
        else{
            this.amount = 0; //if it is not bet then there is no point to have an amount
        }
 //TODO need a copy constructor for UserLight
       // this.userLightOfPlayer = new UserLight(originalAction.userLightOfPlayer);
        this.timeStampOfAction = originalAction.timeStampOfAction;
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
