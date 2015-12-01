package fr.utc.lo23.common.data;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Rémy on 20/10/2015.
 */

public class User implements Serializable{

    /**
     * core : the UserLight contained in the User
     * pwd : the user's password
     * firstname, lastname, age, email
     * stats : the current stats of the user
     * ContactUser : the list of contacts of the user
     * SerialVersionUID : the unique ID of the user
     */
    private UserLight core;
    private String pwd;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private Stats statsUser;
    private Contact contactUser;

    public void setCore(UserLight core) {
        this.core = core;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStatsUser(Stats statsUser) {
        this.statsUser = statsUser;
    }

    public void setContactUser(Contact contactUser) {
        this.contactUser = contactUser;
    }

    private static final long serialVersionUID = 1L;

    public User(){

    };

    public User(String login, String password){

        this.core = new UserLight(login);
        this.pwd = password;
    }

    public User(User toCopy){
        this.core = toCopy.core;
        this.pwd = toCopy.pwd;
        this.firstName = toCopy.firstName;
        this.lastName = toCopy.lastName;
        this.age = toCopy.age;
        this.email = toCopy.email;
        this.statsUser = toCopy.statsUser;
        this.contactUser = toCopy.contactUser;
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


    /**
     * met à jour les statsUser du joueur
     * @param beginMse : la mise de départ du joueur
     * @param points : son score par partie
     */
    public void updateStats(int beginMse, int points){
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

    /**
     * gets the login of the userLight contained in the User
     * @return the login as a String
     */
    public String getLogin(){
        return core.getPseudo();
    }

    /**
     *
     * @return the Image in the ImageAvatar in the UserLight in the User
     */
    public Image getImg(){
        return core.getAvatar().getImg();
    }

    /**
     * other option to password anonymization, just replaces it with a blank string
     */
    private void hidePassword(){
        this.pwd = "";
    }


    /**
     * checks if another User is equal to this, using their UUIDs
     * @param other the other User
     * @return a boolean
     */
    public boolean equals(User other){
        boolean match;
        if (this.core.getIdUser().equals(other.core.getIdUser()))
            match = true;
        else match = false;
        return match;
    }

}
