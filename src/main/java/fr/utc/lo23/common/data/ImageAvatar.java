package fr.utc.lo23.common.data;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.Base64;

/**
 * Created by Remy on 20/10/2015.
 * see http://myjeeva.com/convert-image-to-string-and-string-to-image-in-java.html
 * Class that represent the Image of a user
 */
public class ImageAvatar implements  Serializable{
    private static final long serialVersionUID = 1L;

    private String pathToImage; //path to the local image
    private byte[] imageDataByteArray; //image of the avatar which was converted to byte array
    private final String pathTemporaryImageFile = "./tmp/TemporaryFileImage";

    /**
     * Constructor
     * @param pathToImage String for the path of the image that the User want to use
     * @throws IOException can throw exception if the file doesn't exist or is not correct
     */
    public ImageAvatar(String pathToImage) throws IOException {
        setPath(pathToImage);
    }

    /**
     * Get the path of the file image
     * @return String containing the path to the Image
     */
    public String getPath(){
        return pathToImage;
    }





    /**
     * Method that sets the path to the image file and also initialize the imageDataByteArray variable with this new image
     * @param pathToImage path to the image file
     * @throws IOException can throw exception if the file doesn't exist or is not correct
     */
    public void setPath(String pathToImage) throws IOException {
        this.pathToImage = pathToImage;

        //file containing the image
        File fileImage = new File(this.pathToImage);

        // Open a FileInputStream
        FileInputStream imageInFile = new FileInputStream(fileImage);

        // Reading an Image file from file system and Filling imageDataByteArray with the Image
        this.imageDataByteArray = new byte[(int) fileImage.length()];
        imageInFile.read(this.imageDataByteArray);

        //close the FileInputStream
        imageInFile.close();
    }





    /**
     * Method that has to be used in order to get the Image of the Avatar, only byte[] is stored (it also creates a temporary file) (path of the temporary file: ./tmp/TemporaryFileImage)
     * @return the Image fx for the IHM
     * @throws IOException can trow exception if the creation of the temporary file shows a mistake
     */
    public Image getImageAvatar() throws IOException {

        // Write a image byte array into file system
        FileOutputStream imageOutFile = new FileOutputStream(pathTemporaryImageFile);
        imageOutFile.write(this.imageDataByteArray);
        imageOutFile.close();

        //Get the Image
        File imageFile = new File(pathTemporaryImageFile);
        Image imageFinalAvatarFx = new Image(imageFile.toURI().toString());
        return imageFinalAvatarFx;
    }


    /**
     * toString Override that show the path of the Image
     * @return
     */
    @Override
    public String toString() {
        return "ImageAvatar{" +
                "path=" + pathToImage+
                "}";
    }




































    /**
     * Method that search an image from its path
     * @param path : local path of the image file
     *             @deprecated
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
     * @deprecated
     */
    public void storeImg(String path) throws IOException {
       /*
        File image = new File(path);
        FileOutputStream fos = new FileOutputStream(image);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.defaultWriteObject();
        ImageIO.write(SwingFXUtils.fromFXImage(img, null), "png", oos);*/
    }

    /**
     * reads an image from a local file
     * @param path the location of the image
     * @return the image created
     * @throws IOException
     */
    /*public Image readImg(String path) throws IOException {
        File image = new File(path);
        img = new Image(image.toURI().toString());
        return img;
    }*/

    /**
     * getter for the attribute img
     * @return the attribute img
     */
   /* public Image getImg() {
        return img;
    }
    public void setImg(Image newImg){
        img = newImg;
    }*/




}
