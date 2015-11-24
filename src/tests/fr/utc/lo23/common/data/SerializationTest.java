package fr.utc.lo23.common.data;

import org.junit.Test;

import java.sql.Timestamp;
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
        Serialization.serializationObject(localUser, Serialization.pathUserLocal);
        // Read the local data file into localUser2
        User localUser2 = (User) Serialization.deserializationObject(Serialization.pathUserLocal);
        // Get the username of localUser and localUser2
        String inputPseudo = localUser.getUserLight().getPseudo();
        String outputPseudo = localUser2.getUserLight().getPseudo();
        // Compare the usernames
        assertEquals("The comparison between serialization input and output", inputPseudo, outputPseudo);
    }

    @Test
    public void testDeserializationObject() throws Exception {

    }
}&