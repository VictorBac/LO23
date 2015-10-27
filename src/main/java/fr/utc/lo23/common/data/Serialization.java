package fr.utc.lo23.common.data;

import java.io.Serializable;

/**
 * Created by Mar on 27/10/2015.
 */
public class Serialization {
    public static final String pathUserLocal ="userLocalSave";
    public static final String pathSavedGame ="userLocalSavedGame";

    /**
     * Method to serializable an Object of a class that implements Serializable at the path that is specified
     * @param objectToSerialize the object to Serialize (its class has to implement Serializable)
     * @param namePathFile the path where the object is going to be saved
     */
    public void serializationObject(Serializable objectToSerialize, String namePathFile){}

    /**
     * Method to deserialize an Object that was Serialized in the file at the path that is specified
     * @param namePathFile path where is the object to deserialize
     * @return an Object from a Class that implements Serializable
     */
    public Serializable deserializationObject( String namePathFile){
        return null;
    }


}
