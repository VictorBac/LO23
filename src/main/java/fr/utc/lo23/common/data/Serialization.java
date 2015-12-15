package fr.utc.lo23.common.data;

import java.io.*;

/**
 * Created by Mar on 27/10/2015.
 * http://www.codingeek.com/java/io/object-streams-serialization-deserialization-java-example-serializable-interface/
 */
public class Serialization {
    private final String TAG ="Serialization";
    private static final String dirLocalSavedFiles ="./tmp/";
    public static final String pathSavedGame ="SavedGame";
    public static final String pathServerList ="ServerList";

    /**
     * Method to serializable an Object of a class that implements Serializable at the path that is specified
     * @param objectToSerialize the object to Serialize (its class has to implement Serializable)
     * @param nameFile the path where the object is going to be saved
     */
    public static void serializationObject(Object objectToSerialize, String nameFile){

        try
        {
            FileOutputStream fileOut = new FileOutputStream(dirLocalSavedFiles +nameFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(objectToSerialize);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in "+nameFile);
        }catch(IOException i)
        {
            i.printStackTrace();
        }

    }

    /**
     * Method to deserialize an Object that was Serialized in the file at the path that is specified
     * @param nameFile name of the file where the object has to be deserialize
     * @return an Object from a Class that implements Serializable, Warning it returns null if the file doesn't exist
     */
    public static Object deserializationObject( String nameFile){
        Object objectDeserialized = null;
        if(!new File(dirLocalSavedFiles +nameFile).isFile()){
            System.out.println("file not found it returns null");
        }
        else {
            try
            {
                FileInputStream fileIn = new FileInputStream(dirLocalSavedFiles +nameFile);
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
        }


        return objectDeserialized;
    }


}
