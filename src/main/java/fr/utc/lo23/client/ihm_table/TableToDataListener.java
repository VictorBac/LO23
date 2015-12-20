package fr.utc.lo23.client.ihm_table;

import fr.utc.lo23.client.ihm_table.interfaces.ITableToDataListener;
import fr.utc.lo23.common.data.*;
import javafx.application.Platform;

import java.util.ArrayList;

public class TableToDataListener implements ITableToDataListener {
    private IHMTable ihmtable;

    public IHMTable getIhmtable() {
        return ihmtable;
    }

    public void setIhmtable(IHMTable ihmtable) {
        this.ihmtable = ihmtable;
    }

    //Constructeur
    public TableToDataListener(IHMTable ihmtable){
        setIhmtable(ihmtable);
    }

    // Fonctions de l'interface

    /*
	 * Fonction à appeler après l'envoi de la table vers le serveur, et réception de celle-ci.
	 * Permet à IHM-Table d'afficher la table.
	 */
    public void showTable(Table table){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(getIhmtable().getFormController()!=null) {
                    getIhmtable().getFormController().goToTable(table);
                    getIhmtable().setFormController(null);
                }
                else
                {
                    System.out.println("Error showTable: Il ne faut pas appeler cette fonction en dehors de ses cas d'utilisation.");
                    System.exit(0);
                }
            }
        });
    }

    /*
     * Fonction à appeler après avoir reçu un nouveau message chat.
     * Permet à IHM-Table d'afficher le message
     */
    public void notifyNewChatMessage(MessageChat message){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getIhmtable().getTableController().addChatMessage(message);
            }
        });
    }

    /*
     * Fonction à appeler après l'arrivée d'un nouveau utilisteur sur la partie
     * isPlayer (true) -> le user est un joueur
     * isPlayer(false) -> le user est un spectateur
     */
    public void notifyNewUser(UserLight user, boolean isPlayer){
        if(isPlayer) {
            if(ihmtable.getTableController() != null) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ihmtable.getTableController().addPlayer(user);
                    }
                });
            }
        }
    }

    /*
     * Fonction à appeler après le départ d'un nouveau utilisteur sur la partie
     * isPlayer (true) -> le user est un joueur
     * isPlayer(false) -> le user est un spectateur
     */
    public void notifyUserLeft(UserLight user, boolean isPlayer){
        if(isPlayer) {
            if(ihmtable.getTableController() != null) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ihmtable.getTableController().removePlayer(user);
                    }
                });

            }
        }
    }

    /*
    * Fonction à appeler après réception de la confirmation du serveur pour lancer la partie
    * permet à IHM-Table de savoir que les joueurs vont devoir mettre leurs montants de départ
    */
    public void notifyPreparationPhase(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getIhmtable().getTableController().reorderPlayers();
                for(UserLight user : getIhmtable().getTableController().getTable().getListPlayers().getListUserLights())
                {
                    getIhmtable().getTableController().getPlayerControllerOf(user).updateMoney(-1);
                }
            }
        });

    }

    /*
     * Fonction à appeler après le lancement d'un jeu
     * Permet à IHM-Table d'afficher la demande de quantité d'argent de départ à l'utilisateur
     */
    public void askMoneyAmount(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ihmtable.getTableController().showPopupAmount();
                ihmtable.getTableController().setPopupAmountMaxMoney(ihmtable.getTableController().getTable().getCurrentGame().getMaxStartMoney());
            }
        });

    }

    /*
     * Fonction à appeler après la réception du montant d'un utilisateur
     * Permet à IHM-Table d'afficher le montant d'un utilisateur
     */
    public void notifyMoneyAmountAnswer(UserLight player,Integer amount){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getIhmtable().getTableController().getPlayerControllerOf(player).updateMoney(amount);
            }
        });

    }

    /*
     * Fonction à appeler après avoir reçu les montants de tous les joueurs
     * Permet à IHM-Table de demander à l'utilisateur s'il est prêt et accepte les montants des autres joueurs
     */
    public void askReadyGame(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getIhmtable().getTableController().showPopupReady();
            }
        });
    }

    /*
     * Fonction à appeler après avoir reçu une réponse d'un joueur
     * Permet à IHM-Table d'afficher la réponse d'un joueur
     */
    public void notifyReadyGameAnswer(UserLight player, boolean hasValidated){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getIhmtable().getTableController().getPlayerControllerOf(player).setReadyStatus(hasValidated);
            }
        });

    }

    /*
     * Fonction à appeler après les ready de tous les joueurs.
     * Permet de prévenir IHM-Table que la Game a été lancée.
     */
    public void notifyStartGame(Table table){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getIhmtable().getTableController().addLogEntry("La partie a été lancée ! Préparez-vous !");
                for(UserLight player : getIhmtable().getTableController().getTable().getListPlayers().getListUserLights()) {
                    getIhmtable().getTableController().getPlayerControllerOf(player).clearReadyStatus();
                }
            }
        });
    }

    /*
     * Fonction à appeler après le début d'une manche.
     * Permet à IHM-Table de savoir que la manche est lancée.
     */
    public void notifyStartHand(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ihmtable.getTableController().beginHand();
            }
        });
    }

    /*
     * Fonction à appeler après le début d'un tour.
     * Permet à IHM-Table de savoir que le tour a été lancé.
     */
    public void notifyStartTour(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ihmtable.getTableController().beginTurn();
            }
        });
    }

    /*
     * Fonction à appeler en deux temps:
     *  - une fois en début de manche, après réception de nos propres cartes: la ArrayList sera alors de taille 1
     *  - une fois en fin de manche, après réception des cartes des autres joueurs
     * Permet à IHM-Table d'afficher les cartes des joueurs.
     */
    public void notifyPlayersCards(ArrayList<PlayerHand> playerHands){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ihmtable.getTableController().setPlayerCards(playerHands);
            }
        });
    }

    /*
     * Fonction à appeler après avoir reçu une demande d'action
     * Permet à IHM-Table de demander l'action au joueur
     */
    public void askAction(Action actionToFill, EnumerationAction[] listPossible) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(actionToFill.getUserLightOfPlayer()==ihmtable.getDataInterface().getUser())
                {
                    ihmtable.getTableController().saveActionToFill(actionToFill);
                    ihmtable.getTableController().showActionBox();
                    ihmtable.getTableController().disableAllActions();
                    for (EnumerationAction action : listPossible)
                        ihmtable.getTableController().enableAction(action);
                }
                else
                {
                    ihmtable.getTableController().setThinkingForAction(actionToFill.getUserLightOfPlayer());
                }
            }
        });
    }

    /*
     * Fonction à appeler après avoir reçu une action faite par un joueur
     * Permet à iHM-Table d'afficher cette action
     */
    public void notifyAction(Action action){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                UserLight player = action.getUserLightOfPlayer();
                System.out.println(player);
                System.out.println(ihmtable.getDataInterface().getUser());
                if(action.getName().equals(EnumerationAction.ALLIN)) {
                    if(player==ihmtable.getDataInterface().getUser())
                        ihmtable.getTableController().addLogEntry("Vous avez fait tapis !");
                    else
                        ihmtable.getTableController().addLogEntry(player.getPseudo() + " a fait tapis !");
                    ihmtable.getTableController().setLastRaise(action.getAmount());
                    // ATTENTION: vérifier si l'argent que l'on affiche correspond à la totalité de l'argent mis, ou si il correspond seulement à l'argent de ce tour, ou meme de la relance.
                    ihmtable.getTableController().getPlayerControllerOf(player).addBetMoneyAmount(action.getAmount());
                }
                else if(action.getName().equals(EnumerationAction.BET)) {
                    if(player==ihmtable.getDataInterface().getUser())
                        ihmtable.getTableController().addLogEntry("Vous avez relancé de " + action.getAmount() + "$.");
                    else
                        ihmtable.getTableController().addLogEntry(player.getPseudo() + " a relancé de " + action.getAmount() + "$.");
                    ihmtable.getTableController().setLastRaise(action.getAmount());
                    // ATTENTION: vérifier si l'argent que l'on affiche correspond à la totalité de l'argent mis, ou si il correspond seulement à l'argent de ce tour, ou meme de la relance.
                    ihmtable.getTableController().getPlayerControllerOf(player).addBetMoneyAmount(action.getAmount());
                }
                else if(action.getName().equals(EnumerationAction.CALL)) {
                    if(player==ihmtable.getDataInterface().getUser())
                        ihmtable.getTableController().addLogEntry("Vous avez suivi.");
                    else
                        ihmtable.getTableController().addLogEntry(player.getPseudo() + " a suivi.");
                    ihmtable.getTableController().setLastRaise(action.getAmount());
                    // ATTENTION: vérifier si l'argent que l'on affiche correspond à la totalité de l'argent mis, ou si il correspond seulement à l'argent de ce tour, ou meme de la relance.
                    ihmtable.getTableController().getPlayerControllerOf(player).addBetMoneyAmount(action.getAmount());
                }
                else if(action.getName().equals(EnumerationAction.CHECK)) {
                    if(player==ihmtable.getDataInterface().getUser())
                        ihmtable.getTableController().addLogEntry("Parole.");
                    else
                        ihmtable.getTableController().addLogEntry(player.getPseudo() + " check.");
                    //if zero then useless ihmtable.getTableController().getPlayerControllerOf(player).addBetMoneyAmount(0);
                }
                else if(action.getName().equals(EnumerationAction.FOLD)) {
                    if(player==ihmtable.getDataInterface().getUser())
                        ihmtable.getTableController().addLogEntry("Vous vous couchez.");
                    else
                        ihmtable.getTableController().addLogEntry(player.getPseudo() + " se couche.");
                    ihmtable.getTableController().graphicFoldUser(player);
                }
                else
                {
                    System.out.println("ERROR: L'action renseignée ne possède pas d'action de poker.");
                }
            }
        });
    }

    /*
     * Fonction à appeler après que tous les joueurs aient fait leur action de ce tour
     * Permet à IHM-Table de savoir que le tour est terminé et qu'un prochain peut avoir lieu
     */
    public void notifyEndTour(Integer pot){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ihmtable.getTableController().endTurn(pot);
            }
        });
    }

    /*
     * Fonction à appeler après avoir reçu des cartes du milieu
     * permet à IHM-Table de les afficher
     */
    public void notifyCommonCards(ArrayList<Card> listCards){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ihmtable.getTableController().setCards(listCards);
            }
        });
    }

    /*
     * Fonction à appeler après la résolution d'une manche
     * permet à IHM-Table d'afficher les nouvelles informations
     */
    public void notifyEndHand(ArrayList<Seat> seatPlayers){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ihmtable.getTableController().endHand(seatPlayers);
            }
        });
    }

    /*
     * Fonction à appeler après la confirmation du serveur qu'un vote pour quitter la partie a été lancé
     * Permet à IHM-Table d'afficher la demande de fin de jeu auprès des utilisateurs
     */
    public void askEndGameVote(Game game){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ihmtable.getTableController().showPopupEndGameVote();
            }
        });
    }

    /*
     * Fonction à appeler après la confirmation du serveur que le jeu s'arrête
     * Permet à IHM-Table d'arrêter le jeu.
     */
    public void stopGame(Game game){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ihmtable.getTableController().stopGame(game);
            }
        });
    }

    /*
     * Fonction à appeler après avoir reçu la réponse d'un utilisateur
     * Permet à IHM-Table d'afficher la réponse d'un utilisateur au vote de fin de jeu
     */
    public void notifyPlayerVoteEndGameAnswer(UserLight player,boolean accept){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ihmtable.getTableController().notifyPlayerVoteEndGameAnswer(player, accept);
            }
        });
    }

    public void notifyRefuseStartGame(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ihmtable.getTableController().notifyFailStartGame();
            }
        });
    }

    public void notifySuccessStartGame(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ihmtable.getTableController().notifySuccessStartGame();
            }
        });
    }

    /*
     * Fonction à appeler après la fin normale d'un jeu
     * Permet à ihm-table d'afficher le gagnant et de clore la game
     */
    public  void notifyEndGame(UserLight winner){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ihmtable.getTableController().notifyEndGame(winner);
            }
        });
    }

}
