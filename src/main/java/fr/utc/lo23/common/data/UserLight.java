package fr.utc.lo23.common.data;

import java.util.UUID;

/**
 * Created by Rémy on 20/10/2015.
 */
public class UserLight {

    private UUID idUser;
    private String pseudo;
    private ImageAvatar avatar;

    /**
     * empty constructor
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

    public UUID getIdUser(){
        return idUser;
    }

    public String getPseudo(){
        return pseudo;
    }

    public ImageAvatar getAvatar(){ return avatar; }
}
