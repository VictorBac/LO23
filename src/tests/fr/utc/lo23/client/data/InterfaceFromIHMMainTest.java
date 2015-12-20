package fr.utc.lo23.client.data;

import fr.utc.lo23.common.data.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jianghan on 01/11/2015.
 */
public class InterfaceFromIHMMainTest {
    public InterfaceDataFromIHMMain interfaceDataFromIHMMain;


    @Test
    public void testLogUser() throws Exception {
        // Create an test localUser with username and password
        User localUser = new User("testLocalUser", "testpsw");
        // Write test localUser into the local data file
        Serialization.serializationObject(localUser, Serialization.dirLocalSavedFiles + "testUserLocal");
        // Call logUser in interfaceDataFromIHMMain
        interfaceDataFromIHMMain.logUser("testLocalUser", "testpsw");
    }
}