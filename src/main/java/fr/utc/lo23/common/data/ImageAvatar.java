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
    private final String pathTemporaryImageFile = "C:/Users/Public/kouloukouloukoukoustashstash";

    /**
     * Constructor
     * @param pathToImage String for the path of the image that the User want to use
     * @throws IOException can throw exception if the file doesn't exist or is not correct
     */
    public ImageAvatar(String pathToImage) throws IOException {
        setPath(pathToImage);
    }

    /**
     * Constructor that perform deep copy
     * @param toCopy ImageAvatar that we want to copy
     */
    public ImageAvatar(ImageAvatar toCopy){
        this.pathToImage = toCopy.getPath();

        this.imageDataByteArray = new byte[toCopy.imageDataByteArray.length];
        System.arraycopy(toCopy.imageDataByteArray,0,this.imageDataByteArray,0,toCopy.imageDataByteArray.length);
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
     * toString() Override that show the path of the Image
     * @return
     */
    @Override
    public String toString() {
        return "ImageAvatar{" +
                "path=" + pathToImage+
                "}";
    }
}
