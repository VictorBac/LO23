package fr.utc.lo23.common.data;

import fr.utc.lo23.common.data.exceptions.ActionInvalidException;
import fr.utc.lo23.common.data.exceptions.SeatException;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Ying on 20/10/2015.
 *
 * Class used to represent a turn in the game
 */
public class Turn implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * listAction : the list of action in this turn
     * listPlayerInThisTurn : the list of the players still alive in this turn
     * currentGame : the current game with witch this turn is associated
     * timeStampOfTurn : the interval to make the next action
     */
    private ArrayList<Action> listAction;
    private ArrayList<UserLight> listPlayerInThisTurn;
    private Game currentGame;
    private Timestamp timeStampOfTurn;

    /**
     *  Constructor with currentGame and listPlayerInThisTurn
     *  @param currentGameToAdd the current game from which we get the information to add to the new turn
     */
    public Turn(Game currentGameToAdd){
        this.listAction = new ArrayList<Action>();
        this.timeStampOfTurn = new Timestamp(Calendar.getInstance().getTime().getTime());
        this.currentGame = currentGameToAdd;
        // initializer the list of player by adding one by one the players of each seat in this game
        for (Seat item : currentGameToAdd.getListSeatPlayerWithPeculeDepart()
             ) {
            this.listPlayerInThisTurn.add(item.getPlayer());
        }
    }

    /**
     * Method to get the current action
     * @return the last action
     */
    public Action getCurrentAction(){
        return listAction.get(listAction.size()-1);
    }

    /**
     * Method the player that needs to play
     * @return UserLight of the player that has to make an Action
     */
    public UserLight getNextPlayer(){
        // if the current player is the last one in the list of action, we restart from the first one
        // else, we get directly the next index of the player
        if ( listPlayerInThisTurn.get(getListAction().size()-1) == getCurrentAction().getUserLightOfPlayer() ){
            return listPlayerInThisTurn.get(0);
        }else{
            return listPlayerInThisTurn.get( listPlayerInThisTurn.indexOf(getCurrentAction().getUserLightOfPlayer()) + 1 );
        }
    }

    /**
     * Method to get the minimal bet that a player has to call, calculated according to the previous action
     * @return an integer that a player has to pay at least
     */
    public int minimalBet(){
        // if the list of action is void, use the amount of the blind in this game
        if ( listAction.isEmpty() ){
            return currentGame.getBlind();
        }else{
            return getCurrentAction().getAmount();
        }
    }

    /**
     * Method to add a new action to the turn, but the action needs to be tested before
     * @param newAction action that a player made and that needs to be added to the turn
     * @throws ActionInvalidException
     */
    public void addAction(Action newAction) throws ActionInvalidException{
        int currentAccount = -1;
        int index = -1;
        for (Seat s:currentGame.getListSeatPlayerWithPeculeDepart()
             ) {
            if ( s.getPlayer() == newAction.getUserLightOfPlayer() ) {
                currentAccount = s.getCurrentAccount();
                index = currentGame.getListSeatPlayerWithPeculeDepart().indexOf(s);
            }
        }
        if ( currentAccount == -1 && index == -1 ){
            throw new ActionInvalidException("We cannot find the player in the list of seats of this game");
        }
        if ( newAction == null )
            throw new NullPointerException("Action is null");
        // if the action is FOLD, add directly, there is no condition, delete the current player in the list of player alive
        else if ( newAction.getName().equals(EnumerationAction.FOLD) ){
            listAction.add(newAction);
            listPlayerInThisTurn.remove(getCurrentAction().getUserLightOfPlayer());
        }
        //
        else if ( newAction.getName().equals(EnumerationAction.CHECK )){
            if ( getTotalAmountForAUser(newAction) != minimalBet() )
                throw new ActionInvalidException("You cannot CHECK when your amount is less than the minimum.");
            else
                listAction.add(newAction);
        }
        //
        else if ( newAction.getName().equals(EnumerationAction.CALL) ){
            if ( getTotalAmountForAUser(newAction) <= minimalBet() && getTotalAmountForAUser(newAction) + currentAccount >= minimalBet() )
                throw new ActionInvalidException("You cannot CALL when your amount is different from the minimum.");
            else{
                listAction.add(newAction);
                currentGame.getListSeatPlayerWithPeculeDepart().get(index).updateCurrentAccount(currentAccount + getTotalAmountForAUser(newAction) - minimalBet());
            }
        }
        // if the action is ALLIN, add directly
        else if ( newAction.getName().equals(EnumerationAction.ALLIN) ){
            listAction.add(newAction);
        }



        else if ( newAction.getName().equals(EnumerationAction.BET) ){
            if ( getTotalAmountForAUser(newAction) + newAction.getAmount() <= minimalBet()){
                throw new ActionInvalidException("You cannot BET then your total amount is less the the minimum.");
            }
            else
                listAction.add(newAction);
        }

    }

<<<<<<< Updated upstream
    /*
     * Ressort la liste des actions possibles pour un joueurs, se basant sur les actions précédentes et sur son argent restant
     */
    public EnumerationAction[] availableActions(UserLight user){
        ArrayList<EnumerationAction> tempArray = new ArrayList<EnumerationAction>();
        tempArray.add(EnumerationAction.FOLD);
        tempArray.add(EnumerationAction.ALLIN);
        Integer money = currentGame.getMoneyOfPlayer(user);

        if(money>getMaxMoneyBetInTheTurn() && getMaxMoneyBetInTheTurn()!=0)
        {
            tempArray.add(EnumerationAction.CALL);
        }
        if(getMaxMoneyBetInTheTurn()==0 || getTotalAmountForAUser(user)==getMaxMoneyBetInTheTurn())
        {
            tempArray.add(EnumerationAction.CHECK);
        }
        if(getCurrentGame().getMoneyOfPlayer(user)>getMaxMoneyBetInTheTurn())
        {
            tempArray.add(EnumerationAction.BET);
        }
        return (EnumerationAction[])tempArray.toArray();
    }

    //Cette fonction renvoi la quantité d'argent pariée par un joueur dans un tour.
    private int getTotalAmountForAUser ( Action newAction ){
=======
    /**
     * Method to calculate the total amount of a user
     * @param newAction get the user information from this new action
     * @return the result of the total amount
     */
    public int getTotalAmountForAUser ( Action newAction ){
>>>>>>> Stashed changes
        int amount = 0;
        for (Action a : listAction
             ) {
            if ( a.getUserLightOfPlayer() == newAction.getUserLightOfPlayer() ){
                amount += a.getAmount();
            }
        }
        return amount;
    }

    //Cette fonction renvoi la quantité d'argent pariée par un joueur dans un tour.
    private int getTotalAmountForAUser ( UserLight user ){
        int amount = 0;
        for (Action a : listAction
                ) {
            if ( a.getUserLightOfPlayer() == user ){
                amount += a.getAmount();
            }
        }
        return amount;
    }

    private int getMaxMoneyBetInTheTurn(){
        Integer max = 0;
        for(Action ac : getListAction())
        {
            if(ac.getAmount()>max)
                max=ac.getAmount();
        }
        return max;
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
     * Setter to modify the value of the list of action
     * @param listAction
     */
    public void setListAction(ArrayList<Action> listAction) { this.listAction = listAction; }

    /**
     * Setter to modify the value of the list of the current players
     * @param listPlayerInThisTurn
     */
    public void setListPlayerInThisTurn(ArrayList<UserLight> listPlayerInThisTurn) { this.listPlayerInThisTurn = listPlayerInThisTurn; }

    /**
     * Setter to modify the value of the current game
     * @param currentGame
     */
    public void setCurrentGame(Game currentGame) { this.currentGame = currentGame; }

    /**
     * Setter to modify the interval of making the next action
     * @param timeStampOfTurn
     */
    public void setTimeStampOfTurn(Timestamp timeStampOfTurn) { this.timeStampOfTurn = timeStampOfTurn; }
}
