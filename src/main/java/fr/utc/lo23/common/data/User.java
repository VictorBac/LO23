package fr.utc.lo23.common.data;

import jdk.internal.org.objectweb.asm.commons.SerialVersionUIDAdder;
import sun.security.provider.MD5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Rémy on 20/10/2015.
 */

public class User extends UserLight{


    private String pwd;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private Stats statsUser;
    private Contact contactUser;
    private long SerialVersionUID;


    public String getPwd() {
        return pwd;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public Stats getStats() {
        return statsUser;
    }


    public Contact getContactUser() {
        return contactUser;
    }

    public long getSerialVersionUID() {
        return SerialVersionUID;
    }

    /**

     * met à jour les statsUser du joueur
     * @param beginMse : la mise de départ du joueur
     * @param points : son score par partie
     */
    public void UpdateStats(int beginMse, int points){
        statsUser.updateStats(beginMse, points);
    };

    /**
     * hashe le password pwd du joueur en MD5 et le replace dans la string pwd
     */
    private void anonymizeUserPassword(){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            pwd = md.digest(pwd.getBytes()).toString();
        }
        catch(NoSuchAlgorithmException e){
        }
    };


}
