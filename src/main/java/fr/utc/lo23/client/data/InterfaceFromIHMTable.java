package fr.utc.lo23.client.data;

import fr.utc.lo23.common.data.*;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.exceptions.network.IncorrectActionException;
import fr.utc.lo23.exceptions.network.NetworkFailureException;
import fr.utc.lo23.exceptions.network.TooManyTablesException;

import java.util.UUID;

/**
 * This is the interface which will be used by the IHMTable module on the client's side
 * Created by Haroldcb on 21/10/2015.
 */
public class InterfaceFromIHMTable implements InterfaceDataFromIHMTable {

    private DataManagerClient dManagerClient;

    public InterfaceFromIHMTable(DataManagerClient dManagerClient) {

        this.dManagerClient = dManagerClient;
    }


    /**
     * Table is created by IHM Table, which transmit it as parameter
     * This function transmit the table to COM to create it on the server.
     * @param table : Table to transmit
     */
    public void tableToCreate(Table table){
        try {
            //initialize local table
            dManagerClient.setTableLocal(table);
            dManagerClient.getInterToCom().createTable(getUser(),table);
        } catch (NetworkFailureException e) {
            e.printStackTrace();
        } catch (TooManyTablesException e) {
            e.printStackTrace();
        }
    }

    /**
     * forwards the request to communication module : askStopGame()
     */
    public void askStopGame() {
        try {
            dManagerClient.getInterToCom().askStopGame();
        } catch (NetworkFailureException e) {
            e.printStackTrace();
        }
    }

    //TODO Com must implement vote()
    public void vote(boolean answer){
        //dManagerClient.getInterToCom().vote(answer);
    }

    //TODO
    //params : idGame, idTable?
    public void saveGame() {
        //TODO pour com cloner table sans liste games, ins�rer juste le bon game, retourner
    }


    public void sendMessage(MessageChat message, UUID idTableLocale){
        dManagerClient.getInterToCom().sendMessage(message, idTableLocale);
    }

    public void playGame(UUID idTable) {
        try {
            dManagerClient.getInterToCom().LaunchGame(idTable,dManagerClient.getUserLocal().getUserLight());
        } catch (NetworkFailureException e) {
            e.printStackTrace();
        }
    }

    public void replyAction(Action action) {
        try {
            dManagerClient.getInterToCom().sendAction(action);
        } catch (NetworkFailureException e) {
            e.printStackTrace();
        } catch (IncorrectActionException e) {
            e.printStackTrace();
        }
    }


    public void transmitRequest(UserLight player){
        dManagerClient.getInterToCom().transmitRequestServer(player);
    }

    public UserLight getUser(){
        return dManagerClient.getUserLocal().getUserLight();
    }


    //TODO
    //save stats, return to table page?
    public void quitGame(){
        try {
            dManagerClient.getInterToCom().leaveTable(getUser(),dManagerClient.getTableLocal().getIdTable());
        } catch (NetworkFailureException e) {
            e.printStackTrace();
        }
    }

    public void setStartAmount(int amount){
        dManagerClient.getInterToCom().sendMoneyAmount(dManagerClient.getTableLocal().getIdTable(),getUser(),amount);
    }

    public void isReady(boolean status) {
        dManagerClient.getInterToCom().notifyAnswerAskReadyGame(dManagerClient.getTableLocal().getIdTable(),getUser(),status);
    }
}
