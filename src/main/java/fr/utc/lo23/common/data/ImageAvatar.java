package fr.utc.lo23.common.data;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.io.*;

/**
 * Created by Rémy on 20/10/2015.
 */
public class ImageAvatar {

    /**
     * Todo : implement serialization of img
     * img = l'image de l'avatar
     * pathToImage = le chemin local de l'image sur la machine
     */
     private  Image img;
     private String pathToImage;



    public String getPath(){
        return pathToImage;
    }


    public void setPath(String newpath){
        pathToImage = newpath;
    }


    /**
     * méthode permettant de récupérer une image à partir de son path
     * @param path : le chemin local du fichier image
     */
    public Image searchImageWithPath(String path) throws ImageNotFoundException{
        Image result = new Image(path);
        if (result == null)
            throw new ImageNotFoundException(path);
        else
            return new Image(path);
    }


    /**
     * stores this.img in a local file
     * @param path the path where the file is created/opened
     * @throws IOException if the file cannot be written.
     */
    public void storeImg(String path) throws IOException {
        File image = new File(path);
        FileOutputStream fos = new FileOutputStream(image);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.defaultWriteObject();
        ImageIO.write(SwingFXUtils.fromFXImage(img, null), "png", oos);
    }

    /**
     * reads an image from a local file
     * @param path the location of the image
     * @return the image created
     * @throws IOException
     */
    public Image readImg(String path) throws IOException {
        File image = new File(path);
        img = new Image(image.toURI().toString());
        return img;
    }

    /**
     * getter for the attribute img
     * @return the attribute img
     */
    public Image getImg() {
        return img;
    }
    public void setImg(Image newImg){
        img = newImg;
    }
}
