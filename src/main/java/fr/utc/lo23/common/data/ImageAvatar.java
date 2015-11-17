package fr.utc.lo23.common.data;

import javafx.scene.image.Image;

/**
 * Created by R�my on 20/10/2015.
 */
public class ImageAvatar {

    /**
     * Todo : implement serialization of img
     * img = l'image de l'avatar
     * pathToImage = le chemin local de l'image sur la machine
     */
//     private  Image img;
     private String pathToImage;

    /**
     * m�thode permettant de r�cup�rer une image � partir de son path
     * @param path : le chemin local du fichier image
     */
    public Image searchImageWithPath(String path) throws ImageNotFoundException{
        Image result = new Image(path);
        if (result == null)
            throw new ImageNotFoundException(path);
        else
            return new Image(path);
    }
}
