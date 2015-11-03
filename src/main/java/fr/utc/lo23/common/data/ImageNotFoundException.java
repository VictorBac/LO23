package fr.utc.lo23.common.data;

/**
 * Created by Rémy on 31/10/2015.
 */
public class ImageNotFoundException extends Exception {

    public ImageNotFoundException(String path){
        System.out.println("Aucune image n'existe pour le chemin : "+path);
    }
}
