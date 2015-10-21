package fr.utc.lo23.common.data;

import java.util.UUID;

/**
 * Created by Rémy on 20/10/2015.
 */
public class UserLight {

    private UUID idUser;
    private String pseudo;
    private ImageAvatar avatar;

    public UUID getIdUser(){
        return idUser;
    }

    public String getPseudo(){
        return pseudo;
    }

    public ImageAvatar getAvatar(){

        return avatar;
    }
}
