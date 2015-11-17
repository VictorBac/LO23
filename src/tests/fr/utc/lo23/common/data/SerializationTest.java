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
        User localUser = new User("testLocalUser", "testpsw");
        Serialization.serializationObject(localUser, Serialization.pathUserLocal);
        User localUser2 = (User) Serialization.deserializationObject(Serialization.pathUserLocal);
        String inputPseudo = localUser.getUserLight().getPseudo();
        String outputPseudo = localUser.getUserLight().getPseudo();
        assertEquals("The comparasion between serialization input and output", inputPseudo, outputPseudo);‚
    }

    @Test
    public void testDeserializationObject() throws Exception {

    }
}