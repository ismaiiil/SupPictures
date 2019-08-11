package com.supinfo.suppictures.Controllers;

import com.supinfo.suppictures.Model.Database.Utils.JPAFactory;
import com.supinfo.suppictures.Model.Database.DaoImplementations.JpaUserDaoImpl;
import com.supinfo.suppictures.Controllers.Utils.UIHelpers;
import com.supinfo.suppictures.Model.Database.ValueObjects.User;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@ManagedBean
@ViewScoped
public class UserManager {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String tel;
    private String passConfirm;
    private String address;
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public JpaUserDaoImpl getDao() {
        return dao;
    }

    private JpaUserDaoImpl dao = new JpaUserDaoImpl();

    public String getAddress() {
        return address;
    }

    public void setAddress(String addr) {
        this.address = addr;
    }



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassConfirm() {
        return passConfirm;
    }

    public void setPassConfirm(String passConfirm) {
        this.passConfirm = passConfirm;
    }





    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Log in user and store in session
     * @throws IOException
     */
    public void login() throws IOException {

//        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        /*if(dao.verifyUser(username,password)){
            externalContext.getSessionMap().put("user", username);
            externalContext.redirect("/");
        }else{
            System.out.println("Error login");
            externalContext.getSessionMap().put("user", username);
            externalContext.redirect("/");

        }*/

        User currUser = getDao().verifyUser(username,password);
        if(currUser != null){
            UIHelpers.getContext().getSessionMap().put("user", currUser);
            UIHelpers.getContext().redirect("/");
        }else{
            UIHelpers.showUIMsg("Sorry, we couldn't find an account with those credentials. Please check you username and password and try again!");
        }


    }

    /**
     * Invalidates the session to logout user
     * @throws IOException
     */
    public void logout() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();
        externalContext.redirect("/");

    }

    /**
     * Checks if a user is loggin in
     * @return true if a user is stored in session, false otherwise
     */
    public boolean isUserLoggedIn (){
        return getCurrentUser() != null;
    }

    /**
     * Gets the current signed in used
      * @return the user
     */

    public User getCurrentUser(){
        return UIHelpers.getLoggedInUser();

    }

    /**
     * Checks if current logged in user is has admin privileges
     * */
    public boolean isUserAdmin(){
        if (getCurrentUser() != null){
            return getCurrentUser().getAdministrator();
        }
        return false;
    }


    public void signUp(){
        if(!password.equals(passConfirm)){
            UIHelpers.showUIMsg("The entered passwords do not match!");
            return;
        }

        User user = createUser();

        try {
            JPAFactory.getJpaUserDaoImpl().createUser(user);
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.getSessionMap().put("user", user);
            externalContext.redirect("/");
        } catch (RollbackException e) {
            UIHelpers.showUIMsg("Sorry, this username is already taken!");
        } catch (Exception e) {
            UIHelpers.showUIMsg("Sorry, an error occurred while registering! Please try again");
        }


    }

    /**
     * Creates a user with the current set details
     * @return User
     */
    private User createUser() {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setPassword(password);
        user.setEmailAddress(email);
        user.setPhoneNumber(tel);
        user.setPostalAddress(address);
        return user;
    }

    /**
     * Get a list of all users
     * @return a list of all users
     */
    public List<User> getAllUsers(){
        return getDao().listUsers();
    }

    public String editUserRecord(String username){
        try {
            User userToEdit = getDao().findUserByUsername(username);
            UIHelpers.getContext().getSessionMap().put("editUser",userToEdit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/editProfile.xhtml?faces-redirect=true";
    }

    /**
     * Get user from session
     * @return User to be edited
     */
    public User getEditUserDetails(){
        return (User) UIHelpers.getContext().getSessionMap().get("editUser");
    }

    /**
     * update user details
     */
    public void updateUser(){

        User user = getEditUserDetails();
        update(user);

    }

    /**
     * Change the user password
     */
    public void updateUserPassword(){
        if(!password.equals(passConfirm)){
            UIHelpers.showUIMsg("The entered passwords do not match!");
            return;
        }

        User user = getEditUserDetails();

        user.setPassword(password);

        update(user);


    }

    /**
     * Helper update method
     * @param user
     */
    private void update(User user) {
        try {
            getDao().updateUser(user);
            if(user.getUsername().equals(getCurrentUser().getUsername())){
                UIHelpers.getContext().getSessionMap().put("user",user);
            }
            //todo: redirect to previous page
            UIHelpers.getContext().redirect("/editProfile.xhtml");
        } catch (RollbackException e){
            UIHelpers.showUIMsg("Could not save changes!");
        }
        catch (Exception e) {
            UIHelpers.showUIMsg("An error occurred while saving changes!");
        }
    }


}
