package fr.utc.lo23.common.data;

import com.sun.org.apache.xpath.internal.operations.Bool;
import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.exceptions.ActionInvalidException;
import fr.utc.lo23.common.data.exceptions.CardFormatInvalidException;
import fr.utc.lo23.server.data.CombinationCalculator;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Mar on 20/10/2015.
 *
 * Class used to represent a hand (manche) in the game
 */
public class Hand implements Serializable{
    private static final long serialVersionUID = 1L;

    private ArrayList<Turn> listTurn;
    private ArrayList<Card> listCardField;
    private ArrayList<PlayerHand> listPlayerHand;
    private Timestamp timeStampStartOfTheHand;
    private Integer pot = 0;
    private Game currentGame;

    private final int NUMBER_OF_CARD_PER_PLAYER_HAND = 2;
    private final int NUMBER_OF_CARD_ON_FIELD = 5;
    private final int NUMBER_OF_CARD_IN_DECK = 52;


    /**
     * Constructor with only timeStamp argument to initialize the Hand
     * @param timeStampStartOfTheHand the current time when the Hand started
     */
    public Hand(Timestamp timeStampStartOfTheHand){
        this.listTurn = new ArrayList<Turn>();
        this.listCardField =  new ArrayList<Card>();
        this.listPlayerHand =  new ArrayList<PlayerHand>();
        this.timeStampStartOfTheHand = timeStampStartOfTheHand;
    }


    /**
     * Constructor
     * @param game Game associated with the Game
     */
    public Hand(Game game){
        this.currentGame = game;
        this.listTurn = new ArrayList<Turn>();
        this.listCardField =  new ArrayList<Card>();
        this.listPlayerHand =  new ArrayList<PlayerHand>();
        for(Seat seat : getCurrentGame().getListSeatPlayerWithPeculeDepart())
        {
            if(seat.getStatusPlayer().equals(EnumerationStatusPlayer.CONNECTED) && seat.getCurrentAccount()>0)
            {
                listPlayerHand.add(new PlayerHand(seat.getPlayer()));
            }
        }

        //Si on est pas au premier round, on met le premier joueur en dernier
        if(getCurrentGame().getListHand().size()!=0)
        {
            getListPlayerHand().add(getListPlayerHand().get(0));
            getListPlayerHand().remove(0);
        }

        this.timeStampStartOfTheHand = new Timestamp(Calendar.getInstance().getTime().getTime());

        distributeCardv2();
    }

    /**
     * Method returning the current turn of the Hand
     * @return current Turn, it corresponds to the last Turn of the ArrayList
     */
    public Turn getCurrentTurn(){
        return listTurn.get(listTurn.size()-1); //size()-1 to get the last element
    }


    /**
     * Method used to give to the each player (PlayerHand) of the Hand its card and to put cards on the Fields
     */
    public void distributeCard(){
        int[] deckCards = new int[NUMBER_OF_CARD_IN_DECK];

        int cardDrawn = 0;
        boolean nextDraw = true;
        int numberCardToDistribute = this.listPlayerHand.size() * NUMBER_OF_CARD_PER_PLAYER_HAND + NUMBER_OF_CARD_ON_FIELD;
        int newCardValue =0;
        Card[] cardsDistributed = new Card[numberCardToDistribute];

        //First Step distribution of the cards, we fill cardsDistributed (an array of cards) with new Card
        for (int indexOfCardToChooseInTheAmountOfTotalCardToDistribute = 0; indexOfCardToChooseInTheAmountOfTotalCardToDistribute < numberCardToDistribute; indexOfCardToChooseInTheAmountOfTotalCardToDistribute++){
            //until we haven't found a card that wasn't already distributed we search for a new one
            do {

                cardDrawn = randomInt(0,NUMBER_OF_CARD_IN_DECK-1);
                if(deckCards[cardDrawn]==0){ //check if the card was not already taken
                    //lock the card
                    deckCards[cardDrawn]=1;
                    // give to the player or the field its card
                    //0 to 12 for spade & 13 to 25 heart & 26 to 38 diamond & 39 to 51 club
                    newCardValue = (cardDrawn+1)%13;
                    if(newCardValue==0)
                        newCardValue = 13;
                    if(newCardValue==1) //because the as is no more the first card, it is the last one (14)
                        newCardValue = 14;
                    try {
                        if(0 <= cardDrawn && cardDrawn < 13  ){ // spade card
                            cardsDistributed[indexOfCardToChooseInTheAmountOfTotalCardToDistribute] = new Card(newCardValue,EnumerationCard.SPADE);
                        }
                        else if(13 <= cardDrawn && cardDrawn < 26 ) {// heart card
                            cardsDistributed[indexOfCardToChooseInTheAmountOfTotalCardToDistribute] = new Card(newCardValue,EnumerationCard.HEART);
                        }
                        else if (26 <= cardDrawn && cardDrawn < 39 ){// diamond card
                            cardsDistributed[indexOfCardToChooseInTheAmountOfTotalCardToDistribute] = new Card(newCardValue,EnumerationCard.DIAMOND);
                        }
                        else if(39 <= cardDrawn && cardDrawn < 52 ){// club card
                            cardsDistributed[indexOfCardToChooseInTheAmountOfTotalCardToDistribute] = new Card(newCardValue,EnumerationCard.CLUB);
                        }
                        else{
                            Console.log("Hand class- error when distribute cards");
                        }
                    } catch (CardFormatInvalidException e) {
                        e.printStackTrace();
                    }
                    nextDraw = false;
                }

            }while (nextDraw);
            nextDraw = true;
        }


        // Second Step assign each card to either a player or the field
        int indexPlus = 0;
        for(int index = 0; index < numberCardToDistribute; index++){
            // last cards for the flop
            if(index >= numberCardToDistribute - NUMBER_OF_CARD_ON_FIELD){
                this.listCardField.add(cardsDistributed[index]);
            }
            else{
                indexPlus = index+1;
                this.getListPlayerHand().get(((indexPlus/2)+(indexPlus%2))-1).addNewCard(cardsDistributed[index]);
            }
        }
    }

    /**
     * Method used to give to the each player (PlayerHand) of the Hand its card and to put cards on the Fields, upgraded
     */
    public void distributeCardv2(){
        //Mise en place des structures nécessaires
        int numberCardToDistribute = this.listPlayerHand.size() * NUMBER_OF_CARD_PER_PLAYER_HAND + NUMBER_OF_CARD_ON_FIELD;
        ArrayList<Card> deck = new ArrayList<>();

        //Création du deck de jeu
        for(Integer j=0;j<4;j++)
        {
            for(Integer i=2;i<=14;i++)
            {
                try {
                    switch(j)
                    {
                        case 0:
                            deck.add(new Card(i,EnumerationCard.CLUB));
                            break;
                        case 1:
                            deck.add(new Card(i,EnumerationCard.DIAMOND));
                            break;
                        case 2:
                            deck.add(new Card(i,EnumerationCard.SPADE));
                            break;
                        case 3:
                            deck.add(new Card(i,EnumerationCard.HEART));
                            break;
                    }
                } catch (CardFormatInvalidException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Deck au début:");
        System.out.println(deck);

        //Mise en place de données
        Integer commonCardDistributed = 0;
        PlayerHand currentPlayerHand = null;
        Integer currentIndexPlayerHand = 0;
        Integer iteration = 1;

        //tant qu'il reste des cartes à distribuer
        while(numberCardToDistribute!=0)
        {
            Integer randInt = randomInt(0,NUMBER_OF_CARD_IN_DECK-iteration);
            //Soit elles sont pour les cartes communes
            if(commonCardDistributed<5) {
                this.listCardField.add(deck.get(randInt));
                commonCardDistributed++;
            }
            //Soit c'est la première carte d'un joueur
            else if(numberCardToDistribute%2==0)
            {
                currentPlayerHand = getListPlayerHand().get(currentIndexPlayerHand);
                currentIndexPlayerHand++;
                currentPlayerHand.addNewCard(deck.get(randInt));
            }
            //Soit c'est la deuxième
            else if(numberCardToDistribute%2==1)
            {
                currentPlayerHand.addNewCard(deck.get(randInt));
            }
            //On retire la carte du paquet
            System.out.println("Carte choisie:"+deck.get(randInt));
            deck.remove(deck.get(randInt));
            System.out.println("Nouveau Deck:");
            System.out.println(deck);
            numberCardToDistribute--;
            iteration++;
        }
    }

    /**
     * Method that return a random number between min and max
     * @param min First number that can be return from the interval
     * @param max Last number that can be return from the interval
     * @return a random number
     */
    private int randomInt(int min, int max){
        Random random = new Random();

        //return a pseudo random number between max and min
        int result = random.nextInt(max - min + 1) + min;
        return result;
    }

    /**
     * Method to find the PlayerHand corresponding to a specific UserLight for a Hand
     * @param userLightFromPlayer the UserLight which we search its PlayerHand
     * @return the PlayerHand from the UserLight given in parameter
     */
    public PlayerHand getPlayer(UserLight userLightFromPlayer){
        for(PlayerHand p :this.listPlayerHand){
            if(p.getPlayer().equals(userLightFromPlayer)){
                return p;
            }
        }
        return null;
    }

    /**
     * Method to add a value to the pot
     * @param potAdd Integer that needs to be added to the pot
     */
    public void addInPot(Integer potAdd){
        pot += potAdd;
    }

    /**
     * Method that search for all UserLight that are still able to win monney
     * @return ArrayList<UserLight> with the UserLight of all players that can win money
     */
    public ArrayList<UserLight> getListPerformersUsers(){
        ArrayList<UserLight> users = new ArrayList<>();
        for(PlayerHand player : getListPlayerHand())
        {
            if(!player.isFold())
                users.add(player.getPlayer());
        }
        return users;
    }

    /**
     * Method that search for all UserLight that are still able to play
     * @return ArrayList<UserLight> with the UserLight of all players that can perform an Action
     */
    public ArrayList<UserLight> getListActiveUsers(){
        ArrayList<UserLight> users = new ArrayList<>();
        for(PlayerHand player : getListPlayerHand())
        {
            if(player.isActive())
                users.add(player.getPlayer());
        }
        return users;
    }

    /**
     * Method that checks if the Hand is finished
     * @return true if the Turn id finished (4 Turn were played or no more player is active)
     */
    public Boolean isFinished(){
        //If we have already played the 4 Turn or if everyone have FOLD or ALLIN then true else false
        if(getListTurn().size()==4 || getListActiveUsers().size()<=1)
            return true;
        else
            return false;
    }

    /**
     * Method that return the amount of money that a player has bet for all the Turn of the Hand
     * @param user UserLight of the specified player
     * @return Integer that corresponds to the sum of all the player's bet for this Hand
     */
    public Integer getTotalAmountForAUser(UserLight user){
        Integer rs = 0;
        for(Turn turn: getListTurn())
        {
            rs += turn.getMoneyBetInTheTurnForAUser(user);
        }
        return rs;
    }




    public class SeatComparator implements Comparator<Seat> {
        @Override
        public int compare(Seat o1, Seat o2) {
            if(o1.getCurrentAccount()>o2.getCurrentAccount())
                return 1;
            else if(o1.getCurrentAccount()<o2.getCurrentAccount())
                return -1;
            else
                return 0;
        }
    }


    /**
     * Method used to resolve the end of a Hand, distribute money to the winners
     */
    public void resolve(){
        //A RETIRER, DEBUG ONLY
        Boolean already = false;

        HashMap<UserLight,Integer> playerMoneyBet = new HashMap<>();
        HashMap<UserLight,Integer> effectiveWinningMoney = new HashMap<>();
        ArrayList<PlayerHand> calculatedPlayerHandList = new ArrayList<>();
        for(PlayerHand player : getListPlayerHand())
        {
            playerMoneyBet.put(player.getPlayer(), getTotalAmountForAUser(player.getPlayer()));
            if(!player.isFold()) {
                calculatedPlayerHandList.add(player);
            }
        }

        System.out.println("playerMoneybet:");
        System.out.println(playerMoneyBet);

        CombinationCalculator cc = new CombinationCalculator();

        //On effectue cet algorithme tant qu'il y a de l'argent à répartir
        while(playerMoneyBet.size()!=0) {
            ArrayList<PlayerHand> winners = cc.getWinner(calculatedPlayerHandList, listCardField);

            //S'il y a des ex-aequo, on fait l'algo bien compliqué dessous, sinon on en fait un plus simple
            if (winners.size() > 1) {
                System.out.println(" /////// Ex-aequo ! ///////");

                //on définit quelques stuctures de données nécessaires aux calcules préliminairs et au tri par ordre croissant
                ArrayList<Seat> maxWinningMoney = new ArrayList<>();

                System.out.println("Winners:");
                System.out.println(winners);

                //Pour tous les ex-aequo, on calcule la somme max que chacun peut gagner
                //On utilise des Seat car ils possèdent un userlight et de l'argent (#flemme de créer une autre classe spécifique), et ça permet de trier simplement en créant un comparateur custom
                for (PlayerHand player : winners) {
                    Integer maxWin = 0;
                    for (Map.Entry<UserLight, Integer> entry : playerMoneyBet.entrySet()) {
                        if (entry.getValue() <= playerMoneyBet.get(player.getPlayer())) {
                            maxWin += entry.getValue();
                        } else {
                            maxWin += playerMoneyBet.get(player.getPlayer());
                        }
                    }
                    Seat seat = new Seat();
                    seat.setPlayer(player.getPlayer());
                    seat.setCurrentAccount(maxWin);
                    maxWinningMoney.add(seat);
                }


                //On trie dans l'ordre croissant chaque gagnant
                Collections.sort(maxWinningMoney, new SeatComparator());

                for(Seat st : maxWinningMoney)
                {
                    System.out.println(st.getPlayer().getPseudo()+" va gagner au maximum "+st.getCurrentAccount()+"€");
                }


                //On va donner au plus petit sa valeur de gain, puis aux suivants, ... en retirant cet argent de chaque joueur.
                for (Integer i=0;i<maxWinningMoney.size();i++)
                {
                    Seat seat = maxWinningMoney.get(i);
                    if(seat.getCurrentAccount()>0)
                    {
                        for (Integer j=i;j<maxWinningMoney.size();j++) {
                            //On assigne ou ajoute la valeur gagnée par le joueur
                            Seat seat2 = maxWinningMoney.get(j);
                            if (effectiveWinningMoney.containsKey(seat2.getPlayer()))
                                effectiveWinningMoney.replace(seat2.getPlayer(), seat.getCurrentAccount() / (maxWinningMoney.size()-i) + effectiveWinningMoney.get(seat2.getPlayer()));
                            else
                                effectiveWinningMoney.put(seat2.getPlayer(), seat.getCurrentAccount() / (maxWinningMoney.size()-i));
                            System.out.println(seat2.getPlayer().getPseudo()+" gagne "+(seat.getCurrentAccount() / (maxWinningMoney.size()-i))+" €");
                        }

                        //On retire de l'argent max possible que va gagner ce vainqueur l'argent qui est partagée
                        Integer moneyToRemove = seat.getCurrentAccount();
                        for(Integer j=i;j<maxWinningMoney.size();j++)
                        {
                            Seat seat2 = maxWinningMoney.get(j);
                            seat2.addCurrentMoney(-moneyToRemove);
                            System.out.println("Il reste " + seat2.getCurrentAccount() + "€ possible à gagner à " + seat2.getPlayer().getPseudo());
                        }

                        //On retire cet argent de tous les parieurs
                        for (Map.Entry<UserLight, Integer> entry3 : playerMoneyBet.entrySet()) {
                            System.out.println("joueur: "+entry3.getKey().getPseudo()+" à qui on retire "+moneyToRemove+" €");
                            playerMoneyBet.replace(entry3.getKey(), playerMoneyBet.get(entry3.getKey()) - moneyToRemove);

                        }

                    }
                }

                //On retire désormais de la liste des joueurs ayant pariés ceux qui n'ont plus d'argent parié
                HashMap<UserLight,Integer> tempHash = new HashMap<>();
                for(Map.Entry<UserLight, Integer> entry : playerMoneyBet.entrySet())
                {
                    tempHash.put(entry.getKey(),entry.getValue());
                }

                for (Map.Entry<UserLight, Integer> entry : tempHash.entrySet()) {
                    if (playerMoneyBet.get(entry.getKey())<= 0) {
                        System.out.println(entry.getKey().getPseudo()+" est retiré de la liste des parieurs");
                        playerMoneyBet.remove(entry.getKey());
                    }
                }

            } else {
                System.out.println(" ////// Un seul vainqueur !! ////// ");
                //On calcule l'argent max gagnable
                UserLight player = winners.get(0).getPlayer();
                Integer maxWin = 0;
                for (Map.Entry<UserLight, Integer> entry : playerMoneyBet.entrySet()) {
                    if (entry.getValue() <= playerMoneyBet.get(player)) {
                        maxWin += entry.getValue();
                    } else {
                        maxWin += playerMoneyBet.get(player);
                    }
                }
                //On se donne cet argent
                effectiveWinningMoney.put(player, maxWin);

                //On retire désormais l'argent des joueurs ayant pariés, et on retire de la liste des joueurs ceux qui n'ont plus d'argent parié
                HashMap<UserLight,Integer> tempHash = new HashMap<>();
                for(Map.Entry<UserLight, Integer> entry : playerMoneyBet.entrySet())
                {
                    tempHash.put(entry.getKey(), entry.getValue());
                }

                for (Map.Entry<UserLight, Integer> entry : tempHash.entrySet()) {
                    playerMoneyBet.replace(entry.getKey(), playerMoneyBet.get(entry.getKey()) - maxWin);
                    if (playerMoneyBet.get(entry.getKey()) - maxWin <= 0)
                        playerMoneyBet.remove(entry.getKey());
                }
            }

            //On supprime les vainqueurs de l'ArrayList<PlayerHand>
            for(PlayerHand player : winners)
            {
                calculatedPlayerHandList.remove(player);
            }
        }

        //On assigne les argents finalement gagnés aux Seat
        for (Map.Entry<UserLight, Integer> entry : effectiveWinningMoney.entrySet()) {
            getCurrentGame().getSeatOfUser(entry.getKey()).addCurrentMoney(entry.getValue());
        }
    }


////GETTER And SETTER

    /**
     * Getter that return the lis of turn of the Hand
     * @return ArrayList of Turn
     */
    public ArrayList<Turn> getListTurn() {return listTurn;}

    /**
     * Getter tha t return the list of card currently on the field
     * @return ArrayList of Card (all cards should be set there when the turn begin)
     */
    public ArrayList<Card> getListCardField() {return listCardField;}

    /**
     * Getter that return the list of Player Hand
     * @return a list of PlayerHand
     */
    public ArrayList<PlayerHand> getListPlayerHand() {return listPlayerHand;}

    /**
     * Getter that return the Time when the Hand starts
     * @return a Timestamp relative to the Time when the Hand starts
     */
    public Timestamp getTimeStampStartOfTheHand() {return timeStampStartOfTheHand;}

    /**
     * Get the pot of the Hand
     * @return Integer
     */
    public Integer getPot() {return pot;}

    /**
     * Get the Game associated with the Hand
     * @return Game
     */
    public Game getCurrentGame() {return currentGame;}

    /**
     * Set the list of Turn for the Hand
     * @param listTurn ArrayList<Turn>
     */
    public void setListTurn(ArrayList<Turn> listTurn) {
        this.listTurn = listTurn;
    }

    /**
     * Set the list of Card on the Field for the Hand
     * @param listCardField ArrayList<Card>
     */
    public void setListCardField(ArrayList<Card> listCardField) {
        this.listCardField = listCardField;
    }

    /**
     * Set the list of PlayerHand for the Hand
     * @param listPlayerHand ArrayList<PlayerHand>
     */
    public void setListPlayerHand(ArrayList<PlayerHand> listPlayerHand) {
        this.listPlayerHand = listPlayerHand;
    }

    /**
     * Set the pot for the Hand
     * @param pot Integer
     */
    public void setPot(Integer pot) {
        this.pot = pot;
    }

    /**
     * Set the Game associated with the Hand
     * @param currentGame Game
     */
    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }
}
