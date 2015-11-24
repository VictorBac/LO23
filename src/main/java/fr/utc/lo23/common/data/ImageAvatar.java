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

    public void storeImg(String path) throws IOException {
        File image = new File(path);
        FileOutputStream fos = new FileOutputStream(image);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.defaultWriteObject();
        ImageIO.write(SwingFXUtils.fromFXImage(img, null), "png", oos);
    }

    public Image readImg(String path) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        Image tempImage = SwingFXUtils.toFXImage(ImageIO.read(s), null);
        return tempImage;
    }

    public Image getImg() {
        return img;
    }
}
