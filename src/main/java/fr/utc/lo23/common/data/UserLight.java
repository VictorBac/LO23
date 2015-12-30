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
     * Copy constructor that perform deep copy
     * @param toCopy the UserLight to copy from
     */
    public UserLight(UserLight toCopy)  {
        this.idUser = toCopy.getIdUser();
        this.pseudo = toCopy.getPseudo();
        if(toCopy.getAvatar() != null)
            this.avatar = new ImageAvatar(toCopy.getAvatar());
        else
            this.avatar = null;
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
     * Set the pseudo of the UserLight
     * @param pseudo String for the UserLight
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * Set the Avatar of the UserLight
     * @param avatar ImageAvatar for the UserLight
     */
    public void setAvatar(ImageAvatar avatar) {
        this.avatar = avatar;
    }


    /**
     * Overriding the equal method, only the UUID is used to compare
     * @param o Object to Compare with, needs to be a UserLight
     * @return true if this is the equivalent object else it returns false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserLight userLight = (UserLight) o;

        return this.idUser.equals(userLight.idUser);

    }

    /**
     * Overriding the hashCode method to keep the contract between the equal() and hashCode(). When using HashMap it is relevant to do so.
     * @return the hash obtained by the hash of the UUID
     */
    @Override
    public int hashCode() {
        return idUser.hashCode();
    }

    /**
     * Overriding the to String method
     * @return String obtained by the concatenation of the UUID, the pseudo and the path of the image Avatar
     */
    @Override
    public String toString() {
        return "UserLight{" +
                "idUser=" + idUser +
                ", pseudo='" + pseudo + '\'' +
                ", avatar=" + avatar +
                '}';
    }
}
