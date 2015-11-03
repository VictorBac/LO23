package fr.utc.lo23.common.data;

/**
 * Created by Rémy on 31/10/2015.
 */
public class GroupeNotFoundException extends Exception {

    public GroupeNotFoundException(String name){
        System.out.println("Aucun groupe trouvé pour le nom "+ name);
    }
}
