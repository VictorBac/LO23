package fr.utc.lo23.client.data;

import fr.utc.lo23.client.data.exceptions.*;
import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.Params;
import fr.utc.lo23.common.data.*;
import fr.utc.lo23.exceptions.network.FullTableException;
import fr.utc.lo23.exceptions.network.NetworkFailureException;
import fr.utc.lo23.exceptions.network.ProfileNotFoundOnServerException;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by Jianghan on 01-11-15.
 */
public class InterfaceFromIHMMain implements InterfaceDataFromIHMMain{

    private DataManagerClient dManagerClient;

    public InterfaceFromIHMMain(DataManagerClient dManagerClient) {
        this.dManagerClient = dManagerClient;
    }


    /**
     * Connexion with login and password, call com interface
     * @param login
     * @param password
     */
    public void logUser(String login, String password, String ip, Integer port) throws Exception {
        User userLocal = (User) Serialization.deserializationObject(Serialization.dirLocalSavedFiles + login);
        if (userLocal == null) {
            throw new LoginNotFoundException(login);
        }
        // Get the login and password local.
        String loginLocal = userLocal.getUserLight().getPseudo();
        String passwordLocal = userLocal.getPwd();
        // Check correctness of login and password
        if (!login.equals(loginLocal)) {
            throw new LoginNotFoundException(login);
        } else if (!password.equals(passwordLocal)) {
            throw new WrongPasswordException();
        } else {
            Console.log("loguser "+ userLocal.toString());
            //remove the psw and send userLocal to server
            dManagerClient.setUserLocal(userLocal);
            User userLogin = new User(userLocal);
            userLogin.setPwd(null);


            dManagerClient.getInterToCom().requestLoginServer(userLogin, ip, port);
        }
    }

    /**
     * Method to get the user's all information
     * @param userlight
     * @throws ProfileNotFoundOnServerException
     * @throws NetworkFailureException
     */
    public void getUser(UserLight userlight) throws ProfileNotFoundOnServerException, NetworkFailureException {
        dManagerClient.getInterToCom().consultProfile(userlight);
    }

    /**
     * Write userLocal into the local data file
     * @param userLocal
     */
    public void saveNewProfile(User userLocal) throws UserAlreadyExistsException {
        String login = userLocal.getLogin();
        dManagerClient.setUserLocal(userLocal);
        if (new File(Serialization.dirLocalSavedFiles + login).exists())
            throw new UserAlreadyExistsException(login);

        Serialization.serializationObject(userLocal, Serialization.dirLocalSavedFiles + login);
    }

    /**
     * Delete former user local and write the new userLocal into the local data file and notify the server
     * @param userLocal
     */
    public void updateProfile(User userLocal) throws NetworkFailureException, UserAlreadyExistsException {
        try {
            Files.deleteIfExists(Paths.get(Serialization.dirLocalSavedFiles + dManagerClient.getUserLocal().getLogin()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        saveNewProfile(userLocal);
        dManagerClient.getInterToCom().updateProfile(userLocal);
    }
    /**
     *
     * @param tableId
     * @param mode
     */
    public void joinTableWithMode(UUID tableId, EnumerationTypeOfUser mode) throws FullTableException, NetworkFailureException {
        System.out.println("ID TABLE REF: "+dManagerClient.getListTablesLocal().getTable(tableId));
        dManagerClient.getInterToCom().joinTable(dManagerClient.getUserLocal().getUserLight(), tableId, mode);
    }

    /**
     * Ask server to return UserLightList
     * @return
     */
    public void getPlayerList() throws NetworkFailureException {
        dManagerClient.getInterToCom().requestUserList();
    }

    /**
     * Ask server to return TableList
     * @throws NetworkFailureException  Network Failure
     */
    public void getTableList() throws NetworkFailureException {
        dManagerClient.getInterToCom().requestTableList();
    }

    /**
     * Get local saved game list.
     * @return
     */
    public TableList getSavedGamesList() {
        return (TableList)Serialization.deserializationObject(
                Serialization.dirLocalSavedFiles + dManagerClient.getUserLocal().getLogin() + Serialization.pathSavedGame);
    }

    /**
     * Ask server to play a game.
     * @param tableId
     */
    public void playGame(UUID tableId) throws NetworkFailureException {
        dManagerClient.getInterToCom().requestPlayGame(dManagerClient.getUserLocal().getUserLight(), tableId);
    }

    /**
     * Ask server for disconnection
     */
    public void exitAsked() throws NetworkFailureException {
        // Sometimes, the user has already been deleted
        if (dManagerClient.getUserLocal() != null) {
            if (dManagerClient.getTableLocal() != null)
            {
                dManagerClient.getInterFromIHMTable().quitGame();
            }
            dManagerClient.getInterToCom().notifyDisconnection(dManagerClient.getUserLocal());
        }
    }

    /**
     * get servers list
     * @return server list
     */
    public List<Server> getServersList() {
        return dManagerClient.getListServers();
    }

    /**
     * Add server
     * @param ip
     * @param port
     */
    public void addServer(String ip, Integer port) {
        ArrayList<Server> listServers = dManagerClient.getListServers();
        listServers.add(new Server(ip, port));
        Serialization.serializationObject(listServers, Serialization.dirLocalSavedFiles + Serialization.pathServerList);
        dManagerClient.setListServers(listServers);
    }

    /**
     * remove server from server list
     * @param server server to remove
     */
    public void removeServer(Server server) {
        ArrayList<Server> listServers = dManagerClient.getListServers();
        listServers.remove(server);
        Serialization.serializationObject(listServers, Serialization.dirLocalSavedFiles + Serialization.pathServerList);
        dManagerClient.setListServers(listServers);
    }

    /**
     * get local user profile
     * @return local user
     */
    public User getLocalUserProfile() {
        return dManagerClient.getUserLocal();
    }

    /**
     * send local user
     */
    public void sendLocalUser() throws NetworkFailureException {
        dManagerClient.getInterToCom().sendProfile(dManagerClient.getUserLocal());
    }


    public void importFiles(String folderPath) {
        try {
            Serialization.moveFiles(folderPath, Serialization.dirLocalSavedFiles);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportFiles(String folderPath) {
        try {
            Serialization.moveFiles(Serialization.dirLocalSavedFiles, folderPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void importProfileFile(String filePath) throws UserAlreadyExistsException {
        User profile = (User) Serialization.deserializationObject(filePath);
        saveNewProfile(profile);
    }


    public void exportProfileFile(String filePath) {
        Serialization.serializationObject(dManagerClient.getUserLocal(), filePath);
    }


}
