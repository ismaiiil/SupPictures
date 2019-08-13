package com.supinfo.suppictures.Model.Database.ValueObjects;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;

/**
 * Represents a user entity and is used to create 'Users' table in the corresponding database.
 */
@Entity
@Table(name="Users")
public class User implements Serializable {

    /**
     * Default constructor for the JPA to create a user.
     */
    public User(){ }

    @Expose
    @Id
    private String username;

    /**
     * Creates a user object with the specified details.
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param username The username of the user.
     * @param password The password of the user.
     * @param email The email address of the user.
     * @param tel The phone number of the user.
     * @param address The postal address/locality of the user.
     * @param administrator Represents whether the user is an administrator.
     */
    public User(String firstName,String lastName,String username,String password,String email,String tel,String address, Boolean administrator) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = tel;
        this.emailAddress = email;
        this.password = password;
        this.postalAddress = address;
        this.administrator = administrator;
    }

    /**
     * Gets the username of the user.
     * @return A string representing the username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     * @param username A string representing the username of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    @Expose
    private String firstName;

    /**
     * Gets the first name of the user.
     * @return A string representing the first name of the user.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set's the first name of the user.
     * @param firstName A string representing the first name of the user.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Expose
    private String lastName;

    /**
     * Gets the last name of the user.
     * @return A string representing the last name of the user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     * @param lastName A string representing the last name of the user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Expose
    private String phoneNumber;

    /**
     * Gets the phone number of the user.
     * @return A string representing the phone number of the user.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the user.
     * @param phoneNumber A string representing the phone number of the user.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Expose
    @Email
    private String emailAddress;

    /**
     * Gets the email address of the user.
     * @return A string representing the email address of the user.
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the email address of the user.
     * @param emailAddress A string representing the email address of the user.
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Expose(serialize = false)
    private String password;

    /**
     * Gets the password of the user.
     * @return A string representing the password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     * @param password A string representing the password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Expose
    private String postalAddress;

    /**
     * Gets the postal address/locality of the user.
     * @return A string representing the postal address/locality of the user.
     */
    public String getPostalAddress() {
        return postalAddress;
    }

    /**
     * Sets the postal address/locality of the user.
     * @param postalAddress A string representing the postal address/locality of the user.
     */
    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    @Expose
    private Boolean administrator;

    /**
     * Gets whether the user is an administrator.
     * @return A boolean representing if the user is an administrator.
     */
    public Boolean getAdministrator() {
        return administrator;
    }

    /**
     * Sets whether the user is an administrator.
     * @param administrator A boolean representing if the user is an administrator.
     */
    public void setAdministrator(Boolean administrator) {
        this.administrator = administrator;
    }

}
