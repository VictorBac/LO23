package fr.utc.lo23.client.ihm_table.controllers;

import fr.utc.lo23.client.ihm_table.IHMTable;
import fr.utc.lo23.client.ihm_table.TableUtils;
import fr.utc.lo23.client.ihm_table.views.BetMoneyView;
import fr.utc.lo23.client.ihm_table.views.PlayerView;
import fr.utc.lo23.common.data.*;
import fr.utc.lo23.common.data.exceptions.CardFormatInvalidException;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.sql.Timestamp;
import java.util.*;

public class TableController {

    private IHMTable ihmTable;
    private Table table;
    private HashMap<UserLight,PlayerController> playerControllerMap;
    private HashMap<UserLight,BetMoneyController> betMoneyControllerMap;
    private ArrayList<PlayerController> controllersList; //This is used to find where you can seat
    private Image defaultImage;
    private Action actionToFill;
    private String turnStatus = null; // null,warmup,flop,turn,river
    private Integer potMoneyInt;

    private boolean isHost = true;

    public Table getTable() {
        return table;
    }

    public PlayerController getPlayerControllerOf(UserLight user){
        return playerControllerMap.get(user);
    }

    public void setInterface(IHMTable ihmTable){
        this.ihmTable = ihmTable;
        ihmTable.setTableController(this);
    }

    public void setTable(Table table) {
        this.table = table;
        playerInitializer();
        chatInitializer();
        //betMoneyBoxInitializer();

        // Commenter ce qu'il y a dessous pour virer les tests
        try {
            Card card = new Card(5,EnumerationCard.CLUB);
            ArrayList<Card> cards = new ArrayList<Card>();
            cards.add(card);
            cards.add(card);

            PlayerHand player = new PlayerHand();
            player.setPlayer(controllersList.get(0).getUserLight());

            player.setListCardsHand(cards);
            ArrayList<PlayerHand> players = new ArrayList<PlayerHand>();
            players.add(player);

            showCommonCards();
            cards.add(card);
            setFlopCards(cards);
            //setTurnCard(card);
            //setRiverCard(card);

            setPlayerCards(players);

            Action action = new Action();
            action.setUserLightOfPlayer(controllersList.get(0).getUserLight());

            EnumerationAction[] enumac = {EnumerationAction.ALLIN};

            ihmTable.getTableToDataListener().askAction(action,enumac );


        } catch (CardFormatInvalidException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Slider actionBetMoneySelector;
    @FXML
    private Label betLabel;

    public TableController(){
        playerControllerMap = new HashMap<UserLight,PlayerController>();
        betMoneyControllerMap = new HashMap<UserLight,BetMoneyController>();
        defaultImage = new Image(getClass().getResource("../images/default.png").toExternalForm());
    }

    public void initialize(){
        betLabel.setText(Math.round(actionBetMoneySelector.getValue()) + "");
        actionBetMoneySelector.valueProperty().addListener(new ChangeListener<Number>() {

            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                if (newValue == null) {
                    betLabel.setText("");
                    return;
                }
                betLabel.setText(Math.round(newValue.intValue()) + "");
            }
        });

        hideActionBox();
        disableAllActions();
        addLogEntry("Vous avez rejoint la salle.");


    }

    public void playerInitializer(){

        //Initialisation de la liste des sièges
        controllersList = new ArrayList<PlayerController>(table.getNbPlayerMax());
        for(int ite = 0; ite < table.getNbPlayerMax(); ++ite) {
            controllersList.add(ite, null);
        }

        //Affichage des joueurs déjà présents, dont moi
        for(int i=0;i<table.getListPlayers().getListUserLights().size();i++)
        {
            addPlayer(i, table.getListPlayers().getListUserLights().get(i), false);
        }
    }

    public void addPlayer(UserLight user) {
        addPlayer(getFirstAvailableSeat(), user, true);
    }

    /**
     * Add a player to the game
     * @param id
     * @param user
     * @param showLog : this boolean is used to show log only after the player joined the game
     */
    public void addPlayer(int id, UserLight user, boolean showLog) {
        Point2D coords = TableUtils.getPlayerPosition(id, table.getNbPlayerMax());
        PlayerView playerView = new PlayerView();
        PlayerController playerController = playerView.createPlayer(tablePane, user, coords, defaultImage);
        playerControllerMap.put(user, playerController);
        controllersList.set(id, playerController);
        if (showLog) addLogEntry(user.getPseudo()+" a rejoint la salle.");
        if(isHost && table.getListPlayers().getListUserLights().size() >= table.getNbPlayerMin())
            btnLaunchGame.setVisible(true);
    }

    public void chatInitializer(){
        for(MessageChat msg : table.getCurrentGame().getChatGame().getListMessages())
        {
            addChatMessage(msg);
        }
    }

    public void betMoneyBoxInitializer(){
        Point2D betPlayerBoxWidthHeight = new Point2D(585.0, 155.0);
        Point2D betPlayerBoxCenter = new Point2D(510.0, 215.0);
        int i=0;
        for(PlayerController playerController : controllersList)
        {
            if(playerController!=null)
            {
                Point2D coords = TableUtils.getPlayerPosition(i,table.getListPlayers().getListUserLights().size(),betPlayerBoxCenter,betPlayerBoxWidthHeight);
                BetMoneyView betMoneyView = new BetMoneyView();
                BetMoneyController betMoneyController = betMoneyView.createBetMoneyBox(tablePane, coords);
                betMoneyControllerMap.put(playerController.getUserLight(),betMoneyController);
            }
            i++;
        }

    }

    public int getFirstAvailableSeat() {
        for(int ite = 0; ite < table.getNbPlayerMax(); ++ite) {
            if(controllersList.get(ite) == null) {
                return ite;
            }
        }
        //Should never happen because player should not be added if table is full
        System.out.println("ERROR: Il y a plus de joueurs que le max, crash intercontinental de l'univers !! Poulpe.");
        System.exit(0);
        return -1;
    }

    public void removePlayer(UserLight user){
        removePlayer(user, true);
    }

    public void removePlayer(UserLight user, boolean showLog) {
        PlayerController playerController = playerControllerMap.get(user);
        if(playerController == null) {
            //This user doesn't exist
            System.out.println("ERROR: Cet utilisateur a déjà été supprimé.");
            System.exit(0);
            return;
        }
        playerControllerMap.remove(user);
        if (showLog) addLogEntry(user.getPseudo() + " a quitté la partie.");
        controllersList.set(controllersList.indexOf(playerController), null);
        if(isHost && table.getListPlayers().getListUserLights().size() < table.getNbPlayerMin())
            btnLaunchGame.setVisible(false);
        //Si on est au milieu d'une partie, on cache juste le betMoneyBox correspondant à l'utilisateur
        if(betMoneyControllerMap.get(user)!=null)
            betMoneyControllerMap.get(user).hideBetMoneyBox();
        playerController.destroyGraphic();
    }

    public void reorderPlayers(){
        //On vire les sièges vides
        controllersList.removeAll(Collections.singleton(null));
        //On réorganise les sièges équitablement.
        int i=0;
        for(PlayerController playerController : controllersList)
        {
            Point2D coords = TableUtils.getPlayerPosition(i, table.getListPlayers().getListUserLights().size());
            playerController.setPositions(coords);
            i++;
        }
        //On crée les betMoneyBox
        betMoneyBoxInitializer();
    }

    @FXML
    private TextField messageToSend;
    @FXML
    private ListView<String> chatList;
    @FXML
    private Button btnLaunchGame;
    @FXML
    private Pane tablePane;
    @FXML
    private ListView<String> logView;

    @FXML
    private Pane actionBox;
    @FXML
    private Button actionFold;
    @FXML
    private Button actionCheck;
    @FXML
    private Button actionCall;
    @FXML
    private Button actionBet;
    @FXML
    private Button actionAllin;

    @FXML
    private TitledPane popupAmount;
    @FXML
    private Button popupAmountButton;
    @FXML
    private TextField popupAmountInput;


    @FXML
    private TitledPane popupLeave;
    @FXML
    private Button popupLeaveAccept;
    @FXML
    private Button popupLeaveRefuse;

    @FXML
    private TitledPane popupReady;
    @FXML
    private Button popupReadyAccept;
    @FXML
    private Button popupReadyRefuse;

    @FXML
    private TitledPane popupEndGameVote;
    @FXML
    private Button popupEndGameVoteAccept;
    @FXML
    private Button popupEndGameVoteRefuse;

    @FXML
    private Pane commonCardPane;
    @FXML
    private ImageView commonCardFlop1;
    @FXML
    private ImageView commonCardFlop2;
    @FXML
    private ImageView commonCardFlop3;
    @FXML
    private ImageView commonCardTurn;
    @FXML
    private ImageView commonCardRiver;
    @FXML
    private ImageView commonCardBack;

    @FXML
    private Label potMoney;

    /**
     * Send message for chat
     * @param event
     */
	@FXML
	private void sendMessage(javafx.event.ActionEvent event) {
        if(messageToSend.getText().isEmpty())
            return;

        Timestamp time = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        MessageChat message = new MessageChat(ihmTable.getDataInterface().getUser(),time,messageToSend.getText());
        ihmTable.getDataInterface().sendMessage(message, getTable().getIdTable());

        messageToSend.clear();
	}

    /**
     * Launch game => Start poker algorithm
     * @param event
     */
    @FXML
    private void launchGame(javafx.event.ActionEvent event){
        btnLaunchGame.setVisible(false);
        ihmTable.getDataInterface().playGame(table.getIdTable());
    }

    /**
     * Add chat message to view associated and notify user (using css)
     * @param message
     */
    public void addChatMessage(MessageChat message){
        chatList.getItems().add(message.getSender().getPseudo() + " : " + message.getText());
        chatList.setStyle("-fx-graphic:red;");
    }

    /**
     * Show action box
     */
    public void showActionBox(){
        actionBox.setVisible(true);
    }

    /**
     * Hide action box
     */
    public void hideActionBox(){
        actionBox.setVisible(false);
    }

    /**
     * Save action to fill (when selecting action)
     * @param actionToFill
     */
    public void saveActionToFill(Action actionToFill) { this.actionToFill = actionToFill; }

    /**
     * Enable action fold (view)
     */
    public void enableActionFold(){
        actionFold.getStyleClass().remove("action_fold_off");
        actionFold.getStyleClass().add("active");
        actionFold.getStyleClass().add("action_fold_on");
    }

    /**
     * Disable action fold (view)
     */
    public void disableActionFold(){
        actionFold.getStyleClass().remove("action_fold_on");
        actionFold.getStyleClass().remove("active");
        actionFold.getStyleClass().add("action_fold_off");
    }

    /**
     * Enable action check (view)
     */
    public void enableActionCheck(){
        actionCheck.getStyleClass().remove("action_check_off");
        actionCheck.getStyleClass().add("active");
        actionCheck.getStyleClass().add("action_check_on");
    }

    /**
     * Disable action check (view)
     */
    public void disableActionCheck(){
        actionCheck.getStyleClass().remove("action_check_on");
        actionCheck.getStyleClass().remove("active");
        actionCheck.getStyleClass().add("action_check_off");
    }

    /**
     * Enable action call (view)
     */
    public void enableActionCall(){
        actionCall.getStyleClass().remove("action_call_off");
        actionCall.getStyleClass().add("active");
        actionCall.getStyleClass().add("action_call_on");
    }

    /**
     * Disable action call (view)
     */
    public void disableActionCall(){
        actionCall.getStyleClass().remove("action_call_on");
        actionCall.getStyleClass().remove("active");
        actionCall.getStyleClass().add("action_call_off");
    }

    /**
     * Enable action bet (view)
     */
    public void enableActionBet(){
        actionBet.getStyleClass().remove("action_bet_off");
        actionBet.getStyleClass().add("active");
        actionBet.getStyleClass().add("action_bet_on");
        actionBetMoneySelector.setMin(0);   //TODO : get last raise
        actionBetMoneySelector.setMax(150); //TODO: get allin value (self.money)
        //ihmTable.getDataInterface().getUser().getMoney ?
        actionBetMoneySelector.setVisible(true);
    }

    /**
     * Disable action bet (view)
     */
    public void disableActionBet(){
        actionBet.getStyleClass().remove("action_bet_on");
        actionBet.getStyleClass().remove("active");
        actionBet.getStyleClass().add("action_bet_off");
        actionBetMoneySelector.setVisible(false);
    }

    /**
     * Enable action all-in
     */
    public void enableActionAllin(){
        actionAllin.getStyleClass().remove("action_allin_off");
        actionAllin.getStyleClass().add("active");
        actionAllin.getStyleClass().add("action_allin_on");
    }

    /**
     * Disable action all-in
     */
    public void disableActionAllin(){
        actionAllin.getStyleClass().remove("action_allin_on");
        actionAllin.getStyleClass().remove("active");
        actionAllin.getStyleClass().add("action_allin_off");
    }

    /**
     * Enable any action
     * @param action ALLIN | BET | CHECK | FOLD | CALL
     */
    public void enableAction(EnumerationAction action) {
        if(action == EnumerationAction.ALLIN) enableActionAllin();
        else if(action == EnumerationAction.BET) enableActionBet();
        else if(action == EnumerationAction.CHECK) enableActionCheck();
        else if(action == EnumerationAction.FOLD) enableActionFold();
        else if(action == EnumerationAction.CALL) enableActionCall();
    }

    /**
     * Disable all actions
     */
    public void disableAllActions() {
        disableActionAllin();
        disableActionBet();
        disableActionCheck();
        disableActionFold();
        disableActionCall();
    }

    /**
     * Called when fold action is clicked
     * @param event
     */
    @FXML
    public void fold(javafx.event.ActionEvent event) {
        if(actionFold.getStyleClass().contains("active")) {
            actionToFill.setName(EnumerationAction.FOLD);
            ihmTable.getDataInterface().replyAction(actionToFill);
            System.out.println("DODO");

        }
    }

    /**
     * Called when check action is clicked
     * @param event
     */
    @FXML
    public void check(javafx.event.ActionEvent event) {
        if (actionCheck.getStyleClass().contains("active")) {
            actionToFill.setName(EnumerationAction.CHECK);
            ihmTable.getDataInterface().replyAction(actionToFill);
            System.out.println("CHECK");

        }
    }

    /**
     * Called when call action is clicked
     * @param event
     */
    @FXML
    public void call(javafx.event.ActionEvent event) {
        if (actionCall.getStyleClass().contains("active")) {
            actionToFill.setName(EnumerationAction.CALL);
            //TODO : store previous max bet
            //ihmTable.getDataInterface().replyAction(actionToFill);
            System.out.println("APPEL");

        }
    }

    /**
     * Called when all-in action is clicked
     * @param event
     */
    @FXML
    public void allIn(javafx.event.ActionEvent event) {
        if (actionAllin.getStyleClass().contains("active")) {
            actionToFill.setName(EnumerationAction.ALLIN);
            ihmTable.getDataInterface().replyAction(actionToFill);
            System.out.println("ALLIN");

        }
    }

    /**
     * Called when bet action is clicked
     * @param event
     */
    @FXML
    public void bet(javafx.event.ActionEvent event) {
        if(actionBet.getStyleClass().contains("active")) {
            //TODO: afficher slider puis recliquer pour envoyer
            actionToFill.setName(EnumerationAction.BET);
            //TODO: envoyer à data
            //actionToFill.setAmount((int) actionBetMoneySelector.getValue());
        }
    }

    /**
     * Add log in log view
     * @param msg
     */
    public void addLogEntry(String msg){
        logView.getItems().add(msg);
    }

    /**
     * Show popup amount
     */
    public void showPopupAmount(){
        popupAmount.setVisible(true);
    }

    /**
     * Hide popup amound
     */
    public void hidePopupAmount(){
        popupAmount.setVisible(false);
    }

    /**
     * Set popup amount max money
     * @param maxAmount
     */
    public void setPopupAmountMaxMoney(Integer maxAmount){
        popupAmount.setText("Montant Initial (Max: " + maxAmount + "€)");
    }

    /*
     * Should send popAmound value to data (with actionToFill) TODO: Please verify
     * @param event
     */
    @FXML
    public void sendMoneyAmount(javafx.event.ActionEvent event){
        int amount;
        if(popupAmountInput.getText().isEmpty())
            return;
        amount = Integer.parseInt(popupAmountInput.getText());
        if(amount <= 0 || amount > table.getCurrentGame().getMaxStartMoney())
            return;
        ihmTable.getDataInterface().setStartAmount(Integer.parseInt(popupAmountInput.getText()));
        hidePopupAmount();
    }

    public void showPopupReady(){
        popupReady.setVisible(true);
    }

    public void hidePopupReady(){
        popupReady.setVisible(false);
    }

    /**
     * Button to accept when player is ready
     * @param event
     */
    @FXML
    public void sendReadyAccept(javafx.event.ActionEvent event){
        ihmTable.getDataInterface().isReady(true);
        hidePopupReady();
    }

    /**
     * Button to refuse if player is not ready
     * @param event
     */
    @FXML
    public void sendReadyRefuse(javafx.event.ActionEvent event){
        ihmTable.getDataInterface().isReady(false);
        hidePopupReady();
    }

    /**
     * Show popup to leave game
     * @param event
     */
    @FXML
    public void showPopupLeave(javafx.event.ActionEvent event) { popupLeave.setVisible(true);}

    /**
     * Accept leaving
     * @param event
     */
    @FXML
    public void sendLeaveAccept(javafx.event.ActionEvent event) {
        ihmTable.getDataInterface().quitGame();
        popupLeave.setVisible(false);
        ihmTable.getMainInterface().quitGame();
    }

    /**
     * Refuse leaving
     * @param event
     */
    @FXML
    public void sendLeaveRefuse(javafx.event.ActionEvent event) {
        popupLeave.setVisible(false);
    }

    /**
     * Affiche la réponse d'un utilisateur au vote de fin de jeu en rajoutant une bordure verte (accepte)
     * ou rouge (refuse) autour de la box du player
     * @param player
     * @param accept
     */
    public void notifyPlayerVoteEndGameAnswer(UserLight player,boolean accept) {
        if(accept) {
            if(player==ihmTable.getDataInterface().getUser())
                addLogEntry("Vous avez accepté de mettre fin au jeu.");
            else
                addLogEntry(player.getPseudo() + " a accepté de mettre fin au jeu.");
        } else {
            if(player==ihmTable.getDataInterface().getUser())
                addLogEntry("Vous avez refusé de mettre fin au jeu.");
            else
                addLogEntry(player.getPseudo() + " a refusé de mettre fin au jeu.");
        }
        getPlayerControllerOf(player).showVoteEndGame(accept);
    }

    /**
     * Arrête la partie en cours en réinitialisant les joueurs et en masquant les actions
     * @param game
     */
    public void stopGame(Game game) {
        for(PlayerController p : controllersList){ // Suppression des joueurs
            removePlayer(p.getUserLight(), false);
        }
        playerInitializer(); // Réinitialisation des joueurs
        hideActionBox();
        disableAllActions();
        addLogEntry("Partie terminée.");
    }

    /**
     *
     */
    public void showPopupEndGameVote() {
        popupEndGameVote.setVisible(true);
        btnLaunchGame.setVisible(false);
    }

    /**
     *
     */
    public void hidePopupEndGameVote(){
        popupEndGameVote.setVisible(false);
    }

    /**
     *
     * @param event
     */
    @FXML
    public void sendEndGameVoteAccept(javafx.event.ActionEvent event){
        ihmTable.getDataInterface().vote(true);
        hidePopupEndGameVote();
    }

    /**
     *
     * @param event
     */
    @FXML
    public void sendEndGameVoteRefuse(javafx.event.ActionEvent event){
        ihmTable.getDataInterface().vote(false);
        hidePopupEndGameVote();
    }

    /**
     * Hide common cards (common cards are cards on the table not player's hand)
     */
    public void hideCommonCards(){
        commonCardPane.setVisible(false);
    }

    /**
     * Show common cards
     */
    public void showCommonCards(){
        commonCardPane.setVisible(true);
    }

    /**
     * Reset views associated to cards (on table)
     */
    public void resetCommonCards(){
        commonCardFlop1.setImage(null);
        commonCardFlop1.setVisible(false);
        commonCardFlop2.setImage(null);
        commonCardFlop2.setVisible(false);
        commonCardFlop3.setImage(null);
        commonCardFlop3.setVisible(false);
        commonCardTurn.setImage(null);
        commonCardTurn.setVisible(false);
        commonCardRiver.setImage(null);
        commonCardRiver.setVisible(false);
    }

    /**
     * Set cards according to turnStatus
     * @param cards
     */
    public void setCards(ArrayList<Card> cards){
        if(turnStatus.equals("turn"))
        {
            //Afficher les cartes du river
            if(cards.size()>1)
            {
                System.out.println("Comportement anormal, il ne devrait y avoir qu'une seule carte dans la river");
                System.exit(0);
            }
            setRiverCard(cards.get(0));
            turnStatus = "river";
        }
        else if(turnStatus.equals("flop"))
        {
            //Afficher les cartes du turn
            if(cards.size()>1)
            {
                System.out.println("Comportement anormal, il ne devrait y avoir qu'une seule carte dans le turn");
                System.exit(0);
            }
            setTurnCard(cards.get(0));
            turnStatus = "turn";
        }
        else if(turnStatus.equals("warmup"))
        {
            //Afficher les cartes du flop
            if(cards.size()!=3)
            {
                System.out.println("Comportement anormal, il devrait y avoir 3 cartes dans le flop");
                System.exit(0);
            }
            setFlopCards(cards);
            turnStatus = "flop";
        }
        else
        {
            System.out.println("Comportement anormal, cette fonction ne devrait être appelée que si turnStatus est bien complété. (ie warmup, flop, turn)");
            System.exit(0);
        }
    }

    /**
     * Define where player cards should be on the GUI according to the player view position
     * @param playerHands
     */
    public void setPlayerCards(ArrayList<PlayerHand> playerHands){
        int wait = 1;
        if(playerHands.size()==1)
        {
            for(PlayerController playerController :controllersList)
            {
                if(playerController!=null)
                {
                    ImageView img1 = new ImageView();
                    img1.setVisible(false);
                    img1.setFitWidth(40);
                    img1.setFitHeight(60);
                    img1.setX(playerController.getNode().getLayoutX() + 85);
                    img1.setY(playerController.getNode().getLayoutY() + 30);
                    tablePane.getChildren().add(img1);
                    playerController.setCard1(img1);
                    if(playerController.getUserLight()==playerHands.get(0).getPlayer())
                    {
                        setPlayerCardAnimation(img1,getImageFromCard(playerHands.get(0).getListCardsHand().get(0)),wait);
                    }
                    else
                    {
                        setPlayerCardAnimation(img1,getBackCardImage(),wait);
                    }
                    wait+=200;
                }
            }
            for(PlayerController playerController :controllersList)
            {
                if(playerController!=null)
                {
                    ImageView img2 = new ImageView();
                    img2.setVisible(false);
                    img2.setFitWidth(40);
                    img2.setFitHeight(60);
                    img2.setX(playerController.getNode().getLayoutX() + 130);
                    img2.setY(playerController.getNode().getLayoutY() + 30);
                    tablePane.getChildren().add(img2);
                    playerController.setCard2(img2);
                    if(playerController.getUserLight()==playerHands.get(0).getPlayer())
                    {
                        setPlayerCardAnimation(img2,getImageFromCard(playerHands.get(0).getListCardsHand().get(1)),wait);
                    }
                    else
                    {
                        setPlayerCardAnimation(img2,getBackCardImage(),wait);
                    }
                    wait+=200;
                }
            }
        }
    }

    /**
     * Define animation for a card
     * @param img ImageView of card
     * @param image Image associated to the card
     * @param waitTime Time until card is in player's hand
     */
    public void setPlayerCardAnimation(final ImageView img, final Image image, final int waitTime){
        if(waitTime==0)
            System.out.println("ERROR: Une animation n'a pas eut lieu, il faut mettre waitTime à plus de 0 !!");
        img.setImage(getBackCardImage());
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(false);
        KeyValue kv = new KeyValue(img.xProperty(), img.getX());
        KeyValue ky = new KeyValue(img.yProperty(), img.getY());
        EventHandler onFinished = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                img.setImage(image);
            }
        };
        KeyFrame kf = new KeyFrame(Duration.millis(300), onFinished, kv, ky);
        timeline.getKeyFrames().add(kf);
        img.setX(commonCardPane.getLayoutX()+commonCardBack.getX());
        img.setY(commonCardPane.getLayoutY()+commonCardBack.getY());
        img.setVisible(true);

        //Waiting timeline
        Timeline timelineWait = new Timeline();
        EventHandler onFinishedWait = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                timeline.play();
            }
        };
        KeyFrame kfWait = new KeyFrame(Duration.millis(waitTime), onFinishedWait);
        timelineWait.getKeyFrames().add(kfWait);
        timelineWait.play();
    }

    /**
     * This happens when players folds
     * @param img
     * @param waitTime
     */
    public void setPlayerFoldCardAnimation(final ImageView img, final int waitTime){
        if(waitTime==0)
            System.out.println("ERROR: Une animation n'a pas eut lieu, il faut mettre waitTime à plus de 0 !!");
        img.setImage(getBackCardImage());
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(false);
        KeyValue kv = new KeyValue(img.xProperty(), commonCardBack.getX()+commonCardPane.getLayoutX());
        KeyValue ky = new KeyValue(img.yProperty(), commonCardBack.getY()+commonCardPane.getLayoutY());
        EventHandler onFinished = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                tablePane.getChildren().remove(img);
            }
        };
        KeyFrame kf = new KeyFrame(Duration.millis(300), onFinished, kv, ky);
        timeline.getKeyFrames().add(kf);

        //Waiting timeline
        Timeline timelineWait = new Timeline();
        EventHandler onFinishedWait = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                timeline.play();
            }
        };
        KeyFrame kfWait = new KeyFrame(Duration.millis(waitTime), onFinishedWait);
        timelineWait.getKeyFrames().add(kfWait);
        timelineWait.play();
    }

    /**
     * Animation of cards on the table
     * @param img
     * @param image
     */
    public void setCommonCardAnimation(final ImageView img, final Image image){
        img.setImage(getBackCardImage());

        double svgWidth = img.getFitWidth();
        double cardPosition = img.getX() + svgWidth / 2;

        img.preserveRatioProperty().set(false);

        final Timeline timeline3 = new Timeline();
        timeline3.setCycleCount(1);
        timeline3.setAutoReverse(false);
        KeyValue kv3 = new KeyValue(img.fitWidthProperty(), svgWidth);
        KeyFrame kf3 = new KeyFrame(Duration.millis(200), kv3);
        timeline3.getKeyFrames().add(kf3);

        final Timeline timelineXBackward = new Timeline();
        timelineXBackward.setCycleCount(1);
        timelineXBackward.setAutoReverse(false);
        KeyValue kvXB = new KeyValue(img.xProperty(), img.getX());
        KeyFrame kfXB = new KeyFrame(Duration.millis(200), kvXB);
        timeline3.getKeyFrames().add(kfXB);

        final Timeline timeline2 = new Timeline();
        timeline2.setCycleCount(1);
        timeline2.setAutoReverse(false);
        KeyValue kv2 = new KeyValue(img.fitWidthProperty(), 1);
        EventHandler onFinished2 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                img.setImage(image);
                timeline3.play();
            }
        };
        KeyFrame kf2 = new KeyFrame(Duration.millis(200), onFinished2, kv2);
        timeline2.getKeyFrames().add(kf2);

        final Timeline timelineXForward = new Timeline();
        timeline2.setCycleCount(1);
        timeline2.setAutoReverse(false);
        KeyValue kvXF = new KeyValue(img.xProperty(), cardPosition);
        EventHandler onFinished2XF = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                timelineXBackward.play();
            }
        };
        KeyFrame kfXF = new KeyFrame(Duration.millis(200), onFinished2XF, kvXF);
        timelineXForward.getKeyFrames().add(kfXF);

        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(false);
        KeyValue kv = new KeyValue(img.xProperty(), img.getX());
        EventHandler onFinished = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                timeline2.play();
                timelineXForward.play();
            }
        };
        KeyFrame kf = new KeyFrame(Duration.millis(2000), onFinished, kv);
        timeline.getKeyFrames().add(kf);
        img.setX(commonCardBack.getX());
        img.setVisible(true);
        timeline.play();
    }

    /**
     * Cards in flop
     * @param cards
     */
    public void setFlopCards(ArrayList<Card> cards){
        setCommonCardAnimation(commonCardFlop1, getImageFromCard(cards.get(0)));
        setCommonCardAnimation(commonCardFlop2, getImageFromCard(cards.get(1)));
        setCommonCardAnimation(commonCardFlop3, getImageFromCard(cards.get(2)));
    }

    /**
     * Turn card show
     * @param card
     */
    public void setTurnCard(Card card){
        setCommonCardAnimation(commonCardTurn, getImageFromCard(card));
    }

    /**
     * River card show
     * @param card
     */
    public void setRiverCard(Card card){
        setCommonCardAnimation(commonCardRiver, getImageFromCard(card));

    }

    /**
     * Returns image associated to card
     * @param card Card
     * @return
     */
    public Image getImageFromCard(Card card){
        return new Image(getClass().getResource("../images/cards/"+card.getSymbol().toString().toLowerCase()+"s_"+card.getValue().toString()+".png").toExternalForm());
    }

    /**
     * Return backcard image
     * @return
     */
    public Image getBackCardImage(){
        return new Image(getClass().getResource("../images/cards/back.png").toExternalForm());
    }

    /**
     * animation to get player cards (2)
     * @param user
     */
    public void graphicFoldUser(UserLight user){
        setPlayerFoldCardAnimation(playerControllerMap.get(user).getCard1(),1);
        setPlayerFoldCardAnimation(playerControllerMap.get(user).getCard2(),100);
        playerControllerMap.get(user).setCard1(null);
        playerControllerMap.get(user).setCard2(null);
        playerControllerMap.get(user).setBetMoneyAmount(-1);
    }

    /**
     *
     * @return money in pot
     */
    public Integer getPotMoney(){
        return potMoneyInt;
    }

    /**
     * Set money in pot
     * @param money Money to set to pot
     */
    public void setPotMoney(Integer money){
        potMoneyInt = money;
        potMoney.setText(potMoneyInt.toString() + " $");
    }

    /**
     * Add money to pot
     * @param money Money to add to pot
     */
    public void addPotMoney(Integer money){
        potMoneyInt += money;
        potMoney.setText(potMoneyInt.toString()+" $");
    }

    /**
     * Set state where waiting for player action
     * @param user
     */
    public void setThinkingForAction(UserLight user){
        playerControllerMap.get(user).setThinkingStatus();
    }

    public void notifyFailStartGame(){
        btnLaunchGame.setVisible(true);
        addLogEntry("ERREUR LANCEMENT PARTIE: IL N'Y A PAS ASSEZ DE JOUEURS DANS LA TABLE.");
    }

    public void notifySuccessStartGame(){
        addLogEntry("La partie va se lancer d'ici quelques instants.");
    }
}
