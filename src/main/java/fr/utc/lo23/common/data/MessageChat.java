package fr.utc.lo23.common.data;

import java.sql.Timestamp;

/**
 * Classe repr�sentant un message du chat
 * Created by Haroldcb on 21/10/2015.
 */
public class MessageChat {
    /**
     * sender : exp�diteur du message
     * time : moment de l'envoi du message, utile pour la coordination
     * text : contenu du message
     */
    private UserLight sender;
    private Timestamp time;
    private String text;
}
