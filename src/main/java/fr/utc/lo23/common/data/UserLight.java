package fr.utc.lo23.common.data;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Rémy on 20/10/2015.
 */
public class UserLight implements Serializable {

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

    public UserLight(String nickname)
    {
        this.idUser = UUID.randomUUID();
        this.pseudo = nickname;
        this.avatar = null;
    }


    public UUID getIdUser(){
        return idUser;
    }

    public String getPseudo(){
        return pseudo;
    }

    public ImageAvatar getAvatar(){ return avatar; }

    /**
     *
     * pas de comparaison d'image pour l'instant
     * @param toCompare
     * @return
     */
    public boolean equals(UserLight toCompare){
        boolean result = false;
        if (this.idUser.equals(toCompare.getIdUser()) && this.pseudo.equals(toCompare.getPseudo()))
            result = true;
        return result;
    }
}
