/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.utc.lo23.client.network;

import fr.utc.lo23.common.data.Action;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.exceptions.network.*;

/**
 *
 * @author Jean-Côme
 */
public class IntClient implements InterfaceClient  {

    @Override
    public void sendProfile(User u) throws NetworkFailureException {

    }

    @Override
    public void consultProfile(UserLight u) throws NetworkFailureException, ProfileNotFoundOnServerException {

    }

    @Override
    public void createTable() throws NetworkFailureException, TooManyTablesException {

    }

    @Override
    public void updateProfile(User userLocal) throws NetworkFailureException {

    }

    @Override
    public void leaveRoom(UserLight userLocal) throws NetworkFailureException {

    }

    @Override
    public void joinTable(UserLight userLocal, int IdTable) throws NetworkFailureException, FullTableException {

    }

    @Override
    public void hearthBeat() throws NetworkFailureException {

    }

    @Override
    public void sendAction(Action act, UserLight userLocal) throws NetworkFailureException, IncorrectActionException {

    }

    @Override
    public void leaveTable(UserLight userLocal, int IdTable) throws NetworkFailureException {

    }

    @Override
    public void requestLogGame(UserLight userLocal) throws NetworkFailureException {

    }

    @Override
    public void launchSavedGame() throws NetworkFailureException, IncorrectFileException {

    }

    @Override
    public void sensPacket() throws NetworkFailureException {

    }

    @Override
    public void requestUserStats(UserLight userLocal) throws NetworkFailureException {

    }

    @Override
    public void queryNextStepReplay() throws NetworkFailureException {

    }

    @Override
    public void askStopGame() throws NetworkFailureException {

    }

    @Override
    public void requestPlayGame(UserLight userLocal, Table activeTable) throws NetworkFailureException {

    }
}
