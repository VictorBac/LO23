package fr.utc.lo23.common.data;

import fr.utc.lo23.common.data.exceptions.StatsSizeException;
import javafx.scene.image.Image;

import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Remy on 20/10/2015.
 */

public class User implements Serializable{

    /**
     * core : the UserLight contained in the User
     * pwd : the user's password
     * firstname, lastname, age, email
     * stats : the current stats of the user
     * ContactUser : the list of contacts of the user
     * SerialVersionUID : a unique ID of the user for the serialization
     */
    private UserLight core;
    private String pwd;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private Stats statsUser;
    private Contact contactUser;

    private static final long serialVersionUID = 1L;

    /**
     * Default Constructor
     */
    public User(){};

    /**
     * Constructor to initialize a User with its login and its password
     * @param login
     * @param password
     */
    public User(String login, String password){

        this.core = new UserLight(login);
        this.pwd = password;
    }

    /**
     * Constructor that perform deep copy (not for Contact and Stats)
     * @param toCopy the User to copy from
     */
    public User(User toCopy){
        this.core = new UserLight(toCopy.core);
        this.pwd = toCopy.pwd;
        this.firstName = toCopy.firstName;
        this.lastName = toCopy.lastName;
        this.age = toCopy.age;
        this.email = toCopy.email;
        this.statsUser = toCopy.statsUser;
        this.contactUser = toCopy.contactUser;
    }

    /**
     * Get the core which is the UserLight
     * @return UserLight of the User
     */
    public UserLight  getUserLight(){
        return this.core;
    }

    /**
     * Get the password of the User
     * @return String password of the User
     */
    public String getPwd() {
        return this.pwd;
    }

    /**
     * Get the FirstName of the User
     * @return String firstName of the User
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Get the lastName of the User
     * @return String lastName of the User
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Get the age of the User
     * @return int age of the User
     */
    public int getAge() {
        return this.age;
    }

    /**
     * Get the email address of the User
     * @return String email of the User
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Get the Stats of the User
     * @return Stats of the User
     */
    public Stats getStats() {
        return this.statsUser;
    }

    /**
     * Get the contacts of the User
     * @return Contact of the User
     */
    public Contact getContactUser() {
        return this.contactUser;
    }

    /**
     * Set the UserLight of the User
     * @param core UserLight used to change the User's one
     */
    public void setCore(UserLight core) {
        this.core = core;
    }

    /**
     * Set the password  dof the User
     * @param pwd String used to change the User's one
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * Set the firstName of the User
     * @param firstName String used to change the User's one
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Set the lastName of the User
     * @param lastName String used to change the User's one
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Set the age of the User
     * @param age int used to change the User's one
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Set the email of the User
     * @param email String used to change the User's one
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Set the stats of the User
     * @param statsUser Stats used to change the User's one
     */
    public void setStatsUser(Stats statsUser) {
        this.statsUser = statsUser;
    }

    /**
     * Set the Contact of the User
     * @param contactUser Contact used to change the User's one
     */
    public void setContactUser(Contact contactUser) {
        this.contactUser = contactUser;
    }

    /**
     * Method to update the Stats of the User
     * @param beginMse : the money that has the player at the beginning of the Game
     * @param points : his money at the end of the Game
     */
    public void updateStats(Integer beginMse, Integer points) throws StatsSizeException {
        statsUser.addNewStat(beginMse, points);
    };

    /**
     * Get the login of the userLight contained in the User
     * @return the login as a String, it corresponds to the pseudo of the UserLight core
     */
    public String getLogin(){
        return core.getPseudo();
    }

    /**
     * Get the Image from the ImageAvatar contained in the UserLight core
     * @return  Image in the ImageAvatar from the User
     * @throws IOException can trow exception if the creation of the temporary file shows a mistake
     */
    public Image getImg() throws IOException {
        return core.getAvatar().getImageAvatar();
    }

    /**
     * Other method option to password anonymization, just replaces it with a blank string
     */
    private void hidePassword(){
        this.pwd = "";
    }


    /**
     * Overriding the equal method, only the core is used to compare
     * @param o Object to Compare with, needs to be a User
     * @return true if this is the equivalent object else it returns false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return this.core.equals(user.core);

    }

    /**
     * Overriding the hashCode method to keep the contract between the equal() and hashCode(). When using HashMap it is relevant to do so.
     * @return the hash obtained by the hash of the core UserLight
     */
    @Override
    public int hashCode() {
        return core.hashCode();
    }

    /**
     * Overriding the to String method
     * @return String obtained by the concatenation of the different attribut
     */
    @Override
    public String toString() {
        return "User{" +
                "core=" + core +
                ", pwd='" + pwd + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", statsUser=" + statsUser +
                ", contactUser=" + contactUser +
                '}';
    }
}
