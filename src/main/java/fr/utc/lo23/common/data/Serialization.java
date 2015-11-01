package fr.utc.lo23.common.data;

import java.io.*;

/**
 * Created by Mar on 27/10/2015.
 * http://www.codingeek.com/java/io/object-streams-serialization-deserialization-java-example-serializable-interface/
 */
public class Serialization {
    public static final String pathUserLocal ="./tmp/userLocalSave";
    public static final String pathSavedGame ="userLocalSavedGame";

    /**
     * Method to serializable an Object of a class that implements Serializable at the path that is specified
     * @param objectToSerialize the object to Serialize (its class has to implement Serializable)
     * @param namePathFile the path where the object is going to be saved
     */
    public static void serializationObject(Object objectToSerialize, String namePathFile){

        try
        {
            FileOutputStream fileOut = new FileOutputStream(namePathFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(objectToSerialize);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in "+namePathFile);
        }catch(IOException i)
        {
            i.printStackTrace();
        }

    }

    /**
     * Method to deserialize an Object that was Serialized in the file at the path that is specified
     * @param namePathFile path where is the object to deserialize
     * @return an Object from a Class that implements Serializable
     */
    public Object deserializationObject( String namePathFile){
        Object objectDeserialized = null;
        try
        {
            FileInputStream fileIn = new FileInputStream(namePathFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            objectDeserialized = in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i)
        {
            i.printStackTrace();
        }catch(ClassNotFoundException c)
        {
            System.out.println("class not found");
            c.printStackTrace();
        }
        return objectDeserialized;
    }


}
