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
    private int betAmountThisTurn;

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
        if(!(name.equals(EnumerationAction.BET)) && amount!=0)//TODO check if there is only bet than can have an amount (all-in)
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
     * Constructor to initialise all attributes except the Timestamp which is setting to the current Date
     * @param name  EnumerationAction to specify the type of Action
     * @param amount  int to specify how much money is bet (when action is bet or other actions that needs money), the value must be 0
     * @param userLightOfPlayer UserLight attribute to characterize the player who made the action
     * @throws ActionInvalidException returned if the amount is less than 0 or if the amount cannot corresponds to the Action
     */
    public Action(EnumerationAction name, int amount, UserLight userLightOfPlayer) throws ActionInvalidException {
        if(name==null || userLightOfPlayer == null)
            throw new NullPointerException("No null argument");
        if(amount < 0)
            throw new ActionInvalidException("Amount cannot be less than zero");
        //check if action is bet first
        if(!(name.equals(EnumerationAction.BET)) && amount!=0)//TODO check if there is only bet than can have an amount (all-in)
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
     * Constructor without argument
     */
    public Action(){

        this.name = null;
        this.amount = 0;
        this.userLightOfPlayer = null;
        this.timeStampOfAction = null;
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

    /**
     * Setter to change the type of an Action
     * @param name EnumerationAction for the Action
     */
    public void setName(EnumerationAction name) {
        this.name = name;
    }

    /**
     * Setter to change the Amount of money spend for the Action
     * @param amount int that must be higher than 0 (if it is not the case the amount will be set to 0)
     */
    public void setAmount(int amount) {
        if(amount < 0)
            this.amount = 0;
        else
            this.amount = amount;
    }

    /**
     * setter that change the UserLight associated to an Action
     * @param userLightOfPlayer UserLight of the Player associated to an Action
     */
    public void setUserLightOfPlayer(UserLight userLightOfPlayer) {
        this.userLightOfPlayer = userLightOfPlayer;
    }

    /**
     *  method to set the time manually, avoid to use and use instead setActionTime()
     *  @deprecated
     * @param timeStampOfAction
     */
    public void setTimeStampOfAction(Timestamp timeStampOfAction) {
        this.timeStampOfAction = timeStampOfAction;
    }

    /**
     * Method to set the time to the current date
     */
    public void setActionTime(){
        this.timeStampOfAction = new Timestamp(Calendar.getInstance().getTime().getTime());
    }

    public int getBetAmountThisTurn() {
        return betAmountThisTurn;
    }

    public void setBetAmountThisTurn(int betAmountThisTurn) {
        this.betAmountThisTurn = betAmountThisTurn;
    }
}
