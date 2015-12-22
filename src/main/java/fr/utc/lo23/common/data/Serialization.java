package fr.utc.lo23.common.data;

import java.io.*;

/**
 * Created by Mar on 27/10/2015.
 * http://www.codingeek.com/java/io/object-streams-serialization-deserialization-java-example-serializable-interface/
 */
public class Serialization {
    private final String TAG ="Serialization";
    public static final String dirLocalSavedFiles ="./tmp/";
    public static final String pathSavedGame ="SavedGame";
    public static final String pathServerList ="ServerList";

    /**
     * Method to serializable an Object of a class that implements Serializable at the path that is specified
     * @param objectToSerialize the object to Serialize (its class has to implement Serializable)
     * @param pathFile the path where the object is going to be saved
     */
    public static void serializationObject(Object objectToSerialize, String pathFile){

        try
        {
            FileOutputStream fileOut = new FileOutputStream(pathFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(objectToSerialize);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in "+pathFile);
        }catch(IOException i)
        {
            i.printStackTrace();
        }

    }

    /**
     * Method to deserialize an Object that was Serialized in the file at the path that is specified
     * @param pathFile name of the file where the object has to be deserialize
     * @return an Object from a Class that implements Serializable, Warning it returns null if the file doesn't exist
     */
    public static Object deserializationObject( String pathFile){
        Object objectDeserialized = null;
        if(!new File(pathFile).isFile()){
            System.out.println("file not found it returns null");
        }
        else {
            try
            {
                FileInputStream fileIn = new FileInputStream(pathFile);
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


    public static void moveFiles(String from, String to) throws Exception {
        System.out.println(to);
        try {
            File dir = new File(from);
            System.out.println(dir);
            // check files
            File[] files = dir.listFiles();
            if (files == null)
                return;
            // object dir
            File moveDir = new File(to);
            if (!moveDir.exists()) {
                moveDir.mkdirs();
            }
            // move files
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    moveFiles(files[i].getPath(), to + "/" + files[i].getName());
                    // success，delete source
                    //files[i].delete();
                }
                File moveFile = new File(moveDir.getPath() + "/"
                        + files[i].getName());
                // if the object file exists, delete it first
                if (moveFile.exists()) {
                    moveFile.delete();
                }
                files[i].renameTo(moveFile);
            }
        } catch (Exception e) {
            throw e;
        }
    }

}
