package fr.utc.lo23.common.data;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Remy on 20/10/2015.
 */
public class UserLight implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID idUser;
    private String pseudo;
    private ImageAvatar avatar;

    /**
     * Empty constructor
     */
    public UserLight(){

    }

    /**
     * Copy constructor
     * @param toCopy the UserLight to copy.
     */
    public UserLight(UserLight toCopy)  {
        this.idUser = toCopy.getIdUser();
        this.pseudo = toCopy.getPseudo();
        this.avatar = toCopy.getAvatar();
    }

    /**
     * Constructor with only pseudo, the UUID is generated
     * @param nickname pseudo of the User
     */
    public UserLight(String nickname)
    {
        this.idUser = UUID.randomUUID();
        this.pseudo = nickname;
        this.avatar = null;
    }

    /**
     * Constructor deprecated
     * @deprecated
     * @param pseudo
     * @param avatar
     * @throws IOException
     */
    public UserLight(String pseudo, ImageAvatar avatar) throws IOException {
        this.pseudo = pseudo;
        this.avatar = new ImageAvatar(avatar.getPath());
    }

    /**
     * Constructor that takes the pseudo and the path to the Image
     * @param pseudo pseudo of the User
     * @param pathImageAvatar String of the Image used for the Avatar
     * @throws IOException error if no file was found for the Image at the Path specified
     */
    public UserLight(String pseudo, String pathImageAvatar) throws IOException {
        this.pseudo = pseudo;
        this.avatar = new ImageAvatar(pathImageAvatar);
    }

    /**
     * Getter to get the UUID
     * @return the UUID of the UserLight
     */
    public UUID getIdUser(){
        return idUser;
    }

    /**
     * Getter to get the pseudo
     * @return String that corresponds to the pseudo of the UserLight
     */
    public String getPseudo(){
        return pseudo;
    }

    /**
     * Getter to get the ImageAvatar of the UserLight
     * @return ImageAvatar of the UserLight
     */
    public ImageAvatar getAvatar(){ return avatar; }

    /**
     * Setter to set the UUID of the User, avoid to use
     * @param idUser the UUID of the UserLight
     */
    public void setIdUser(UUID idUser) {
        this.idUser = idUser;
    }

    /**
     *
     * @param pseudo
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setAvatar(ImageAvatar avatar) {
        this.avatar = avatar;
    }

    /**
     *
     * compares the ID of two UserLights
     * @param toCompare
     * @return
     */
    public boolean equals(UserLight toCompare){
        return this.idUser.equals(toCompare.getIdUser());
    }

    @Override
    public String toString() {
        return "UserLight{" +
                "idUser=" + idUser +
                ", pseudo='" + pseudo + '\'' +
                ", avatar=" + avatar +
                '}';
    }
}
