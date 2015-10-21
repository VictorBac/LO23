package fr.utc.lo23.common.data;

import javafx.scene.image.Image;

/**
 * Created by R�my on 20/10/2015.
 */
public class ImageAvatar {

    /**
     * img = l'image de l'avatar
     * pathToImage = le chemin local de l'image sur la machine
     */
     private  Image img;
     private String pathToImage;

    /**
     * m�thode permettant de r�cup�rer une image � partir de son path
     * @param path : le chemin local du fichier image
     * TODO : g�rer les exceptions
     */
    public Image searchImageWithPath(String path){
        return new Image(path);
    }
}
