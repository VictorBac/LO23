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
        Action actionToSerialize = new Action(EnumerationAction.allIn,0,new UserLight(),new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
        //Serialization.serializationObject(actionToSerialize,Serialization.pathUserLocal);
    }

    @Test
    public void testDeserializationObject() throws Exception {

    }
}