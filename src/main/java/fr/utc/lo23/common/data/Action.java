package fr.utc.lo23.common.data;

import fr.utc.lo23.common.data.exceptions.ActionInvalidException;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by Mar on 20/10/2015.
 *
 * Class used to represent an action of a player in the game
 */
public class Action implements Serializable{
    private static final long serialVersionUID = 1L;
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
    public Action(EnumerationAction name, int amount, UserLight userLightOfPlayer, Timestamp timeStampOfAction) throws ActionInvalidException {
        if(name==null || userLightOfPlayer == null || timeStampOfAction==null)
            throw new NullPointerException("No null argument");
        if(amount < 0)
            throw new ActionInvalidException("Amount cannot be less than zero");
        //check if action is bet first
        if(!(name.equals(EnumerationAction.bet)) && amount!=0)//TODO check if there is only bet than can have an amount (all-in)
            throw new ActionInvalidException("Amount cannot be different from zero when action is not bet");
        else
        {
            this.name = name;
            this.amount = amount;
            this.userLightOfPlayer = userLightOfPlayer;
            this.timeStampOfAction = timeStampOfAction;
        }

    }

    /**
     * @param name an EnumerationAction to specify the type of action
     * @param amount an int to specify how much money is bet (when action is bet)
     * @param userLightOfPlayer attribute to characterize the player who made the action
     */
    public Action(EnumerationAction name, int amount, UserLight userLightOfPlayer) throws ActionInvalidException {
        if(name==null || userLightOfPlayer == null)
            throw new NullPointerException("No null argument");
        if(amount < 0)
            throw new ActionInvalidException("Amount cannot be less than zero");
        //check if action is bet first
        if(!(name.equals(EnumerationAction.bet)) && amount!=0)//TODO check if there is only bet than can have an amount (all-in)
            throw new ActionInvalidException("Amount cannot be different from zero when action is not bet");
        else
        {
            this.name = name;
            this.amount = amount;
            this.userLightOfPlayer = userLightOfPlayer;
            this.timeStampOfAction = new Timestamp(Calendar.getInstance().getTime().getTime());
        }


    }

    /**
     * Constructor to copy anotherAction
     * @param originalAction action that we want to make a copy of
     */
    public Action(Action originalAction) throws ActionInvalidException {
        //TODO need a copy constructor for UserLight --> new UserLight(originalAction.userLightOfPlayer)
        this(originalAction.name, originalAction.amount, new UserLight(),originalAction.timeStampOfAction);
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
