package fr.utc.lo23.common.data;

import fr.utc.lo23.common.data.exceptions.ActionInvalidException;

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
     * listPlayerNoAllinNoFold : the list of the players still alive in this turn
     * listPlayerAllin :
     * currentGame : the current game with witch this turn is associated
     * timeStampOfTurn : the interval to make the next action
     */
    private ArrayList<Action> listAction;
    private ArrayList<UserLight> listPlayerNoAllinNoFold;
    private ArrayList<UserLight> listPlayerAllin;
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

        // initializer listPlayerNoAllinNoFold
        if (currentGameToAdd.getCurrentHand().getListTurn().size() == 1){
            // initializer the list of player by adding one by one the players of each seat in this game
            for (Seat item : currentGameToAdd.getListSeatPlayerWithPeculeDepart()
             ) {
                this.listPlayerNoAllinNoFold.add(item.getPlayer());
            }
        }else{
            // get the information from the last turn
            for (UserLight item : currentGameToAdd.getCurrentHand().getListTurn().get(currentGameToAdd.getCurrentHand().getListTurn().size()-2).getListPlayerNoAllinNoFold()
                 ) {
                this.listPlayerNoAllinNoFold.add(item);
            }
        }

        // initializer listPlayerAllin from the last turn
        for (UserLight item : currentGameToAdd.getCurrentHand().getListTurn().get(currentGameToAdd.getCurrentHand().getListTurn().size()-2).getListPlayerAllin()
             ) {
                this.getListPlayerAllin().add(item);
        }

    }

    /**
     * Method to add a new action to the turn, update the current amount and the amount in the pot
     * @param newAction action that a player made and that needs to be added to the turn
     * @throws ActionInvalidException
     */
    public void addAction(Action newAction) throws ActionInvalidException{

        Hand currentHand = currentGame.getCurrentHand();
        ArrayList<Pot> pot = currentHand.getListPotForTheHand();

        if ( availableActions(newAction.getUserLightOfPlayer()).contains(newAction.getName())){
            // Fold
            if (newAction.getName().equals(EnumerationAction.FOLD)){
                listAction.add(newAction);
                listPlayerNoAllinNoFold.remove(newAction.getUserLightOfPlayer());
                // if the player fold, add the amount to the first pot
                if ( pot.size() == 0){
                    currentHand.addNewPot();
                    currentHand.setFirstPot(getTotalAmountForAUser(newAction.getUserLightOfPlayer()));
                }
                else
                    currentHand.setFirstPot(getTotalAmountForAUser(newAction.getUserLightOfPlayer()));
            }
            // Check
            else if (newAction.getName().equals(EnumerationAction.CHECK)){
                listAction.add(newAction);
            }
            // Call
            else if (newAction.getName().equals(EnumerationAction.CALL)){
                if (newAction.getAmount() != getMaxMoneyBetInTheHand() - getTotalAmountForAUser(newAction.getUserLightOfPlayer())){
                    throw new ActionInvalidException("Invalid amount");
                }else{
                    int newCurrentMoney = currentGame.getMoneyOfPlayer(newAction.getUserLightOfPlayer()) - newAction.getAmount();
                    currentGame.setMoneyOfPlayer(newAction.getUserLightOfPlayer(), newCurrentMoney);
                    listAction.add(newAction);
                }
            }
            // Bet
            else if (newAction.getName().equals(EnumerationAction.BET)){
                if (newAction.getAmount() + getTotalAmountForAUser(newAction.getUserLightOfPlayer()) < getMaxMoneyBetInTheHand() )
                    throw new ActionInvalidException("The total amount must be greater than the minimum.");
                else {
                    int newCurrentMoney = currentGame.getMoneyOfPlayer(newAction.getUserLightOfPlayer()) - newAction.getAmount();
                    currentGame.setMoneyOfPlayer(newAction.getUserLightOfPlayer(), newCurrentMoney);
                    if (newCurrentMoney == 0){
                        newAction.setName(EnumerationAction.ALLIN);
                        listPlayerAllin.add(newAction.getUserLightOfPlayer());
                        listPlayerNoAllinNoFold.remove(newAction.getUserLightOfPlayer());
                    }
                    listAction.add(newAction);
                }
            }
            // AllIn
            else if (newAction.getName().equals(EnumerationAction.ALLIN)){
                listAction.add(newAction);
                currentGame.setMoneyOfPlayer(newAction.getUserLightOfPlayer(), 0);
                listPlayerAllin.add(newAction.getUserLightOfPlayer());
                listPlayerNoAllinNoFold.remove(newAction.getUserLightOfPlayer());
            }

            // if a turn is over, should distribute pots
            if ( isOver() ){
                ArrayList<UserLight> listPlayer = new ArrayList<UserLight>();
                for (UserLight user : listPlayerNoAllinNoFold
                        ) {
                    listPlayer.add(user);
                }
                for (UserLight user : listPlayerAllin
                        ) {
                    listPlayer.add(user);
                }
                distributePot(listPlayer, 0, 0);
            }
        }else{
            throw new ActionInvalidException("Invalid Action!");
        }
    }


    public void distributePot(ArrayList<UserLight> listAllIn, int index, int min){
        ArrayList<UserLight> list = listAllIn;
        int sizeOrigin = list.size();

        if (currentGame.getCurrentHand().getListPotForTheHand().size() < index +1 )
            currentGame.getCurrentHand().addNewPot();

        for (UserLight user : list
                ) {
            currentGame.getCurrentHand().getListPotForTheHand().get(index).addPlayer(user);
        }

        if (isSameAmount(list)) {
            if (index==0){
                for (UserLight user : list
                        ) {
                    currentGame.getCurrentHand().getListPotForTheHand().get(index).addValue(getTotalAmountForAUser(user));
                }
            }else if (index!=0){
                int addValue = sizeOrigin * ( getTotalAmountForAUser(list.get(0)) - min );
                currentGame.getCurrentHand().getListPotForTheHand().get(index).addValue(addValue);
            }
        }else if (!isSameAmount(list)){
            int minAmount = getTotalAmountForAUser(list.get(0));
            UserLight minUser = list.get(0);
            ArrayList<UserLight> minUserList = new ArrayList<UserLight>();
            for (UserLight user : list
                 ) {
                if (getTotalAmountForAUser(user) < minAmount){
                    minAmount = getTotalAmountForAUser(user);
                    minUser = user;
                }
            }
            minUserList.add(minUser);
            list.remove(minUser);
            for (UserLight user : list
                    ) {
                if (getTotalAmountForAUser(user) == minAmount){
                    minUserList.add(user);
                    list.remove(user);
                }
            }

            int addValue = ( sizeOrigin - minUserList.size() ) * ( minAmount - min );
            currentGame.getCurrentHand().getListPotForTheHand().get(index).addValue(addValue);

            if (list.size() != 0)
                distributePot(list, index+1, minAmount);

        }


    }

    /**
     * Method to test if the action is available for a specific user
     *
     */
    public ArrayList<EnumerationAction> availableActions(UserLight user) throws ActionInvalidException{

        if ( !isAllIn(user) ) {
            ArrayList<EnumerationAction> tempArray = new ArrayList<EnumerationAction>();
            tempArray.add(EnumerationAction.FOLD);
            tempArray.add(EnumerationAction.ALLIN);
            int money = currentGame.getMoneyOfPlayer(user);

            // when a new turn starts, the first one can check
            //    because only when all the amounts are the same, we can display a new card
            // when the money bet by me is equal to the max amount bet by another one, we can check
            if (listAction.size() == 0 || getTotalAmountForAUser(user) == getMaxMoneyBetInTheHand()) {
                tempArray.add(EnumerationAction.CHECK);
            }
            if (money + getTotalAmountForAUser(user) > getMaxMoneyBetInTheHand() && getMaxMoneyBetInTheHand() != 0) {
                tempArray.add(EnumerationAction.CALL);
            }
            if (money + getTotalAmountForAUser(user) > getMaxMoneyBetInTheHand()) {
                tempArray.add(EnumerationAction.BET);
            }
            return tempArray;
        }
        else{
            throw new ActionInvalidException("This player cannot make any action now!");
        }

    }

    /**
     * Method to test if the player have made the action AllIn
     * If this case, we pass this player automatically
     * @param user
     * @return
     */
    public boolean isAllIn( UserLight user ){
        for (UserLight item : listPlayerAllin
             ) {
            if ( item == user ){
                return true;
            }
        }
        return false;
    }

    /**
     * Method to test if all players of listPlayerNoAllinNoFold bet the same amount
     * It this case, we display a new card
     * @return
     */
    public boolean isSameAmount(ArrayList<UserLight> userList){
        int temp = getTotalAmountForAUser(userList.get(0));
        for (UserLight user : userList
                ) {
            if (getTotalAmountForAUser(user) != temp){
                return false;
            }
        }
        return true;
    }

    /**
     * Method to calculate the total amount in this hand of a user
     * @param user get the user information from this new action
     * @return the result of the total amount
     */
    private int getTotalAmountForAUser ( UserLight user ){
        int amount = 0;
        for (Turn eachTurn : currentGame.getCurrentHand().getListTurn()
             ) {
            for (Action a : eachTurn.getListAction()
                    ) {
                if ( a.getUserLightOfPlayer() == user ){
                    amount += a.getAmount();
                }
            }
        }
        return amount;
    }

    /**
     * Method to return the max amount bet by someone
     * @return the max amount for an specific player in these two lists
     */
    private int getMaxMoneyBetInTheHand(){
        int max = 0;
        for (UserLight total : listPlayerNoAllinNoFold
                ) {
            if( getTotalAmountForAUser(total) > max )
                max = getTotalAmountForAUser(total);
        }
        for (UserLight total : listPlayerAllin
             ) {
            if ( getTotalAmountForAUser(total) > max)
                max = getTotalAmountForAUser(total);
        }
        return max;
    }

    /**
     * Method to test if a turn is over
     * @return
     */
    public boolean isOver(){
        if (listPlayerNoAllinNoFold.size() != 0 ){
            if ( isSameAmount(listPlayerNoAllinNoFold) )
                return true;
            else
                return false;
        }else if (listPlayerNoAllinNoFold.size() == 0) {
            if (listPlayerAllin.size() != 0)
                return true;
            else
                return false;
        }
        return false;
    }

    /**
     * Method to get the current action
     * @return the last action
     */
    public Action getCurrentAction(){
        return listAction.get(listAction.size()-1);
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

    public ArrayList<UserLight> getListPlayerNoAllinNoFold() {
        return listPlayerNoAllinNoFold;
    }

    public ArrayList<UserLight> getListPlayerAllin() {
        return listPlayerAllin;
    }

    /**
     * Setter to modify the value of the list of action
     * @param listAction
     */
    public void setListAction(ArrayList<Action> listAction) { this.listAction = listAction; }

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
