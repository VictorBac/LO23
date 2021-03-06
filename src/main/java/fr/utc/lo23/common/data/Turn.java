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
     * currentGame : the current game with witch this turn is associated
     * timeStampOfTurn : the interval to make the next action
     */
    private ArrayList<Action> listAction;
    private Hand currentHand;
    private Timestamp timeStampOfTurn;
    private Integer turnPot = 0;

    /**
     *  Constructor
     */
    public Turn(Hand currentHandToAdd){
        this.listAction = new ArrayList<Action>();
        this.timeStampOfTurn = new Timestamp(Calendar.getInstance().getTime().getTime());
        this.currentHand = currentHandToAdd;
    }

    /**
     * Method to add a new action to the turn, update the current amount and the amount in the pot
     * @param newAction
     * @throws ActionInvalidException
     */

    public void addAction(Action newAction) throws ActionInvalidException{

        System.out.println("VERIF ACTIONS: "+availableActions(newAction.getUserLightOfPlayer()));
        System.out.println("acion player: "+newAction.getName());
        if(availableActions(newAction.getUserLightOfPlayer()).contains(newAction.getName()))
        {
            if (newAction.getName().equals(EnumerationAction.FOLD)) {
                getCurrentHand().getPlayer(newAction.getUserLightOfPlayer()).setFold();
            } else if (newAction.getName().equals(EnumerationAction.CHECK)) {

            } else if (newAction.getName().equals(EnumerationAction.CALL)) {
                //Mettre l'argent dans l'action
                newAction.setAmount(getMaxMoneyBetInTheTurn()-getMoneyBetInTheTurnForAUser(newAction.getUserLightOfPlayer()));
                //TODO: Ajouter vérifications d'argent pour lutter contre la triche, on ne le fera pas en LO23.
            } else if (newAction.getName().equals(EnumerationAction.BET)) {
                //TODO: Ajouter vérifications d'argent pour lutter contre la triche, on ne le fera pas en LO23.
            } else if (newAction.getName().equals(EnumerationAction.ALLIN)) {
                //TODO: Ajouter vérifications d'argent pour lutter contre la triche, on ne le fera pas en LO23.
                getCurrentHand().getPlayer(newAction.getUserLightOfPlayer()).setAllin();
            }
            this.getListAction().add(newAction);

            //On met à jour l'argent total du joueur
            if(newAction.getAmount()!=0)
            {
                getCurrentHand().getCurrentGame().getSeatOfUser(newAction.getUserLightOfPlayer()).addCurrentMoney(-newAction.getAmount());
            }
        }
        else
        {
            System.out.println("ALERTE TRICHE: un player a envoyé une action interdite au serveur. Action: "+newAction);
            throw new ActionInvalidException("Action interdite");
        }

    }

    /**
     * Method to test if the action is available for a specific user
     * @param user
     * @return the list of available action
     */
    public ArrayList<EnumerationAction> availableActions(UserLight user){
        // if the user have made "all in" or "fold" action, he cannot do anything after
        if ( !getCurrentHand().getPlayer(user).isAllin() && !getCurrentHand().getPlayer(user).isFold() )
        {
            ArrayList<EnumerationAction> tempArray = new ArrayList<EnumerationAction>();
            // We can do "fold" and "all in" at any time
            tempArray.add(EnumerationAction.FOLD);
            tempArray.add(EnumerationAction.ALLIN);
            int money = currentHand.getCurrentGame().getMoneyOfPlayer(user);

            // when the money bet by the player is equal to the max amount bet by another one, the player can check
            if (listAction.size() == 0 || getMoneyBetInTheTurnForAUser(user).equals(getMaxMoneyBetInTheTurn())) {
                tempArray.add(EnumerationAction.CHECK);
            }
            if (money > getMaxMoneyBetInTheTurn() && getMaxMoneyBetInTheTurn() != 0 && getMaxMoneyBetInTheTurn()!=getMoneyBetInTheTurnForAUser(user)) {
                tempArray.add(EnumerationAction.CALL);
            }
            if (money > getMaxMoneyBetInTheTurn()) {
                tempArray.add(EnumerationAction.BET);
            }
            System.out.println(tempArray);
            return tempArray;
        }
        else
        {
            return null;
        }
    }

    public ArrayList<UserLight> getListActiveUsers(){
        ArrayList<UserLight> users = new ArrayList<UserLight>();
        for(PlayerHand player : getCurrentHand().getListPlayerHand())
        {
            if(player.isActive())
                users.add(player.getPlayer());
        }
        return users;
    }

    public ArrayList<Action> askFirstAction(){
        //TODO: Gestion de l'ante (qui va se révéler relou, à bien réfléchir)
        ArrayList<Action> arrayAc = new ArrayList<Action>();
        if(getCurrentHand().getListTurn().size()==1)
        {
            Action ac1 = new Action();
            Action ac2 = new Action();
            Action ac3 = new Action();

            ac1.setUserLightOfPlayer(getListActiveUsers().get(0));
            ac1.setName(EnumerationAction.BLINDE);
            ac1.setAmount(getCurrentHand().getCurrentGame().getBlind());
            getCurrentHand().getCurrentGame().getSeatOfUser(ac1.getUserLightOfPlayer()).addCurrentMoney(-ac1.getAmount());
            getListAction().add(ac1);
            //TODO: ac1.setTime ??

            ac2.setUserLightOfPlayer(getListActiveUsers().get(1));
            ac2.setName(EnumerationAction.BIGBLINDE);
            ac2.setAmount(getCurrentHand().getCurrentGame().getBlind()*2);
            getCurrentHand().getCurrentGame().getSeatOfUser(ac2.getUserLightOfPlayer()).addCurrentMoney(-ac2.getAmount());
            getListAction().add(ac2);
            //TODO: ac2.setTime ??

            ac3.setUserLightOfPlayer(getNextActiveUserAfterUser(getListActiveUsers().get(1)));

            arrayAc.add(ac1);
            arrayAc.add(ac2);
            arrayAc.add(ac3);
        }
        else
        {
            Action ac = new Action();
            ac.setUserLightOfPlayer(getListActiveUsers().get(0));
            arrayAc.add(ac);
        }
        return arrayAc;
    }

    public UserLight getNextActiveUser(){
        UserLight lastActionner = getCurrentAction().getUserLightOfPlayer();

        for(UserLight user : getListActiveUsers())
        {
            if(user.equals(lastActionner))
                lastActionner = user;
        }


        Integer currentIndex = getListActiveUsers().indexOf(lastActionner)+1;
        if(currentIndex>=getListActiveUsers().size())
        {
            currentIndex = 0;
        }
        System.out.println("getNextActive: "+getListActiveUsers().get(currentIndex));
        return getListActiveUsers().get(currentIndex);
    }

    public UserLight getNextActiveUserAfterUser(UserLight user){
        Integer currentIndex = getListActiveUsers().indexOf(user)+1;
        if(currentIndex>=getListActiveUsers().size())
        {
            currentIndex = 0;
        }
        return getListActiveUsers().get(currentIndex);
    }

    public Boolean isFinished(){
        //Si tout le monde n'a pas joué, alors c'est sur que le tour n'est pas finit

        System.out.println(getCurrentHand().getListTurn().size());
        if(getCurrentHand().getListTurn().size()==1)
        {
            if(getListAction().size()-2<getListActiveUsers().size())
                return false;
        }
        else
        {
            if(getListAction().size()<getListActiveUsers().size())
                return false;
        }

        System.out.println("on a pas renvoyé false");
        //sinon il faut vérifier que tous les joueurs actifs aient le même montant
        Integer val = -1;
        for(UserLight user: getListActiveUsers())
        {
            System.out.println("MONEY BET PAR USER :"+getMoneyBetInTheTurnForAUser(user));
            if(val==-1)
            {
                val = getMoneyBetInTheTurnForAUser(user);
            }
            else if(!getMoneyBetInTheTurnForAUser(user).equals(val))
                return false;
        }
        return true;
    }

    public ArrayList<Action> getActionsOfUser(UserLight user) {
        ArrayList<Action> rs = new ArrayList<Action>();
        for(Action ac : getListAction())
        {
            if(ac.getUserLightOfPlayer().equals(user))
                rs.add(ac);
        }
        return rs;
    }

    public void resolve(){
        for(Action action : getListAction())
        {
            turnPot += action.getAmount();
        }
        getCurrentHand().addInPot(turnPot);
    }


    public Integer getMaxMoneyBetInTheTurn(){
        Integer money = 0;
        for (Action a : getListAction()) {
            if(a.getAmount()>money)
                money = a.getAmount();
        }
        return money;
    }

    public Integer getMoneyBetInTheTurnForAUser(UserLight user){
        Integer money = 0;
        for (Action a : getListAction()) {
            if(a.getUserLightOfPlayer().equals(user))
                money += a.getAmount();
        }
        return money;
    }

    /**
     * Method to calculate the total amount in the hand of a user
     * @param user get the user information from the new action
     * @return the result of the total amount
     */
    public Integer getTotalAmountForAUser ( UserLight user ){
        int amount = 0;
        // need to add all the turns
        for (Turn eachTurn : currentHand.getListTurn()) {
            for (Action a : eachTurn.getListAction()) {
                if ( a.getUserLightOfPlayer().equals(user)){
                    amount += a.getAmount();
                }
            }
        }
        return amount;
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
     * Setter to modify the interval of making the next action
     * @param timeStampOfTurn
     */
    public void setTimeStampOfTurn(Timestamp timeStampOfTurn) { this.timeStampOfTurn = timeStampOfTurn; }

    public Hand getCurrentHand() {
        return currentHand;
    }

    public void setCurrentHand(Hand currentHand) {
        this.currentHand = currentHand;
    }

    public Integer getTurnPot() {
        return turnPot;
    }

    public void setTurnPot(Integer turnPot) {
        this.turnPot = turnPot;
    }
}
