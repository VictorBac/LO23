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

            setPlayerCards(players);

            Action action = new Action();
            action.setUserLightOfPlayer(controllersList.get(0).getUserLight());
            action.setAmount(50);
            action.setName(EnumerationAction.allIn);


            ihmTable.getTableToDataListener().notifyAction(action);


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

	@FXML
	private void sendMessage(javafx.event.ActionEvent event) {
        if(messageToSend.getText().isEmpty())
            return;

        Timestamp time = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        MessageChat message = new MessageChat(ihmTable.getDataInterface().getUser(),time,messageToSend.getText());
        ihmTable.getDataInterface().sendMessage(message);

        messageToSend.clear();
	}

    @FXML
    private void launchGame(javafx.event.ActionEvent event){
        btnLaunchGame.setVisible(false);
        ihmTable.getDataInterface().playGame(table.getIdTable());
    }

    public void addChatMessage(MessageChat message){
        chatList.getItems().add(message.getSender().getPseudo() + " : " + message.getText());
        chatList.setStyle("-fx-graphic:red;");
    }

    public void showActionBox(){
        actionBox.setVisible(true);
    }

    public void hideActionBox(){
        actionBox.setVisible(false);
    }

    public void saveActionToFill(Action actionToFill) { this.actionToFill = actionToFill; }

    public void enableActionFold(){
        actionFold.getStyleClass().remove("action_fold_off");
        actionFold.getStyleClass().add("active");
        actionFold.getStyleClass().add("action_fold_on");
    }

    public void disableActionFold(){
        actionFold.getStyleClass().remove("action_fold_on");
        actionFold.getStyleClass().remove("active");
        actionFold.getStyleClass().add("action_fold_off");
    }

    public void enableActionCheck(){
        actionCheck.getStyleClass().remove("action_check_off");
        actionCheck.getStyleClass().add("active");
        actionCheck.getStyleClass().add("action_check_on");
    }

    public void disableActionCheck(){
        actionCheck.getStyleClass().remove("action_check_on");
        actionCheck.getStyleClass().remove("active");
        actionCheck.getStyleClass().add("action_check_off");
    }

    public void enableActionCall(){
        actionCall.getStyleClass().remove("action_call_off");
        actionCall.getStyleClass().add("active");
        actionCall.getStyleClass().add("action_call_on");
    }

    public void disableActionCall(){
        actionCall.getStyleClass().remove("action_call_on");
        actionCall.getStyleClass().remove("active");
        actionCall.getStyleClass().add("action_call_off");
    }

    public void enableActionBet(){
        actionBet.getStyleClass().remove("action_bet_off");
        actionBet.getStyleClass().add("active");
        actionBet.getStyleClass().add("action_bet_on");
        actionBetMoneySelector.setMin(0);   //TODO : get last raise
        actionBetMoneySelector.setMax(150); //TODO: get allin value (self.money)
        actionBetMoneySelector.setVisible(true);
    }

    public void disableActionBet(){
        actionBet.getStyleClass().remove("action_bet_on");
        actionBet.getStyleClass().remove("active");
        actionBet.getStyleClass().add("action_bet_off");
        actionBetMoneySelector.setVisible(false);
    }

    public void enableActionAllin(){
        actionAllin.getStyleClass().remove("action_allin_off");
        actionAllin.getStyleClass().add("active");
        actionAllin.getStyleClass().add("action_allin_on");
    }

    public void disableActionAllin(){
        actionAllin.getStyleClass().remove("action_allin_on");
        actionAllin.getStyleClass().remove("active");
        actionAllin.getStyleClass().add("action_allin_off");
    }

    public void enableAction(EnumerationAction action) {
        if(action == EnumerationAction.allIn) enableActionAllin();
        else if(action == EnumerationAction.bet) enableActionBet();
        else if(action == EnumerationAction.check) enableActionCheck();
        else if(action == EnumerationAction.fold) enableActionFold();
        else if(action == EnumerationAction.call) enableActionCall();
    }

    public void disableAllActions() {
        disableActionAllin();
        disableActionBet();
        disableActionCheck();
        disableActionFold();
        disableActionCall();
    }

    @FXML
    public void fold(javafx.event.ActionEvent event) {
        if(actionFold.getStyleClass().contains("active")) {
            //TODO : "quand ces **** auront mis des setters"
            //actionToFill.setAction(EnumerationAction.fold);
            System.out.println("DODO");

        }
    }

    @FXML
    public void check(javafx.event.ActionEvent event) {
        if (actionCheck.getStyleClass().contains("active")) {
            //TODO : "quand ces *** auront mis des setters"
            //actionToFill.setAction(EnumerationAction.check);
            System.out.println("CHECK");

        }
    }

    @FXML
    public void call(javafx.event.ActionEvent event) {
        if (actionCall.getStyleClass().contains("active")) {
            //TODO : "quand ces *** auront mis des setters"
            //actionToFill.setAction(EnumerationAction.call);
            System.out.println("APPEL");

        }
    }

    @FXML
    public void allIn(javafx.event.ActionEvent event) {
        if (actionAllin.getStyleClass().contains("active")) {
            //TODO : "quand ces *** auront mis des setters"
            //actionToFill.setAction(EnumerationAction.allIn);
            System.out.println("ALLIN");

        }
    }

    @FXML
    public void bet(javafx.event.ActionEvent event) {
        if(actionBet.getStyleClass().contains("active")) {
            //TODO : "quand ces *** auront mis des setters"
            //actionToFill.setAction(EnumerationAction.bet);
            //actionToFill.setAmount((int) actionBetMoneySelector.getValue());
        }
    }


    public void addLogEntry(String msg){
        logView.getItems().add(msg);
    }

    public void showPopupAmount(){
        popupAmount.setVisible(true);
    }

    public void hidePopupAmount(){
        popupAmount.setVisible(false);
    }

    public void setPopupAmountMaxMoney(Integer maxAmount){
        popupAmount.setText("Montant Initial (Max: " + maxAmount + "€)");
    }

    @FXML
    public void sendMoneyAmount(javafx.event.ActionEvent event){
        int amount;
        if(popupAmountInput.getText().isEmpty())
            return;
        amount = Integer.parseInt(popupAmountInput.getText());
        if(amount <= 0 || amount > table.getMaxMise())
            return;
        //TODO: Envoyer Integer.parseInt(popupAmountInput.getText()); à data

        hidePopupAmount();
    }

    public void showPopupReady(){
        popupReady.setVisible(true);
    }

    public void hidePopupReady(){
        popupReady.setVisible(false);
    }

    @FXML
    public void sendReadyAccept(javafx.event.ActionEvent event){
        //TODO: Envoyer true à data
        //ihmTable.getDataInterface().
        hidePopupReady();
    }

    @FXML
    public void sendReadyRefuse(javafx.event.ActionEvent event){
        //TODO: Envoyer false à data
        //ihmTable.getDataInterface().
        hidePopupReady();
    }

    @FXML
    public void showPopupLeave(javafx.event.ActionEvent event) { popupLeave.setVisible(true);}

    @FXML
    public void sendLeaveAccept(javafx.event.ActionEvent event) {
        ihmTable.getDataInterface().quitGame();
        popupLeave.setVisible(false);
        ihmTable.getMainInterface().quitGame();
    }

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

    public void showPopupEndGameVote() {
        popupEndGameVote.setVisible(true);
        btnLaunchGame.setVisible(false);
    }

    public void hidePopupEndGameVote(){
        popupEndGameVote.setVisible(false);
    }

    @FXML
    public void sendEndGameVoteAccept(javafx.event.ActionEvent event){
        ihmTable.getDataInterface().vote(true);
        hidePopupEndGameVote();
    }

    @FXML
    public void sendEndGameVoteRefuse(javafx.event.ActionEvent event){
        ihmTable.getDataInterface().vote(false);
        hidePopupEndGameVote();
    }

    public void hideCommonCards(){
        commonCardPane.setVisible(false);
    }

    public void showCommonCards(){
        commonCardPane.setVisible(true);
    }

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


    public void setCommonCardAnimation(final ImageView img, final Image image){
        img.setImage(getBackCardImage());
        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(false);
        KeyValue kv = new KeyValue(img.xProperty(), img.getX());
        EventHandler onFinished = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                img.setImage(image);
            }
        };
        KeyFrame kf = new KeyFrame(Duration.millis(2000), onFinished, kv);
        timeline.getKeyFrames().add(kf);
        img.setX(commonCardBack.getX());
        img.setVisible(true);
        timeline.play();
    }

    public void setFlopCards(ArrayList<Card> cards){
        setCommonCardAnimation(commonCardFlop1, getImageFromCard(cards.get(0)));
        setCommonCardAnimation(commonCardFlop2, getImageFromCard(cards.get(1)));
        setCommonCardAnimation(commonCardFlop3, getImageFromCard(cards.get(2)));
    }

    public void setTurnCard(Card card){
        setCommonCardAnimation(commonCardTurn, getImageFromCard(card));
    }

    public void setRiverCard(Card card){
        setCommonCardAnimation(commonCardRiver, getImageFromCard(card));

    }

    public Image getImageFromCard(Card card){
        return new Image(getClass().getResource("../images/cards/"+card.getSymbol().toString().toLowerCase()+"s_"+card.getValue().toString()+".png").toExternalForm());
    }

    public Image getBackCardImage(){
        return new Image(getClass().getResource("../images/cards/back.png").toExternalForm());
    }

    public void graphicFoldUser(UserLight user){
        setPlayerFoldCardAnimation(playerControllerMap.get(user).getCard1(),1);
        setPlayerFoldCardAnimation(playerControllerMap.get(user).getCard2(),100);
        playerControllerMap.get(user).setCard1(null);
        playerControllerMap.get(user).setCard2(null);
        playerControllerMap.get(user).setBetMoneyAmount(-1);
    }

    public Integer getPotMoney(){
        return potMoneyInt;
    }

    public void setPotMoney(Integer money){
        potMoneyInt = money;
        potMoney.setText(potMoneyInt.toString() + " $");
    }

    public void addPotMoney(Integer money){
        potMoneyInt += money;
        potMoney.setText(potMoneyInt.toString()+" $");
    }

    public void setThinkingForAction(UserLight user){
        playerControllerMap.get(user).setThinkingStatus();
    }
}
