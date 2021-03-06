package fr.utc.lo23.common.data;

import org.junit.Test;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Created by Mar on 01/11/2015.
 */
public class SerializationTest {

    @Test
    public void testSerializationObject() throws Exception {
        // Create an localUser with username and password
        User localUser = new User("testLocalUser", "testpsw");
        // Write localUser into the local data file
        Serialization.serializationObject(localUser, Serialization.dirLocalSavedFiles + "testUserLocal");
        // Read the local data file into localUser2
        User localUser2 = (User) Serialization.deserializationObject(Serialization.dirLocalSavedFiles + "testUserLocal");
        // Get the username of localUser and localUser2
        String inputPseudo = localUser.getUserLight().getPseudo();
        String outputPseudo = localUser2.getUserLight().getPseudo();
        // Compare the input and output of username
        assertEquals("The comparison between serialization input and output", inputPseudo, outputPseudo);
    }

    @Test
    public void testFileMove() throws Exception {
        Serialization.moveFiles(Serialization.dirLocalSavedFiles + "src", Serialization.dirLocalSavedFiles + "dest");
    }
}