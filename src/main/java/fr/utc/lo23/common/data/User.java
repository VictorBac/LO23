package fr.utc.lo23.common.data;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Rémy on 20/10/2015.
 */

public class User implements Serializable{

    private UserLight core;
    private String pwd;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private Stats statsUser;
    private Contact contactUser;
    private long SerialVersionUID;

    public User(){

    };

    public User(String login, String password){

        this.core = new UserLight(login);
        this.pwd = password;
    }

    public UserLight  getUserLight(){
        return this.core;
    }

    public String getPwd() {
        return this.pwd;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public int getAge() {
        return this.age;
    }

    public String getEmail() {
        return this.email;
    }

    public Stats getStats() {
        return this.statsUser;
    }

    public Contact getContactUser() {
        return this.contactUser;
    }

    public long getSerialVersionUID() {
        return this.SerialVersionUID;
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
            System.out.println(e.toString());
        }
    }


}
