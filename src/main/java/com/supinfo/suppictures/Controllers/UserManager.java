package com.supinfo.suppictures.Controllers;

import com.supinfo.suppictures.Core.Utils.JPAUtil;
import com.supinfo.suppictures.Core.ValueObjects.JpaUserDaoImpl;
import com.supinfo.suppictures.Core.ValueObjects.User;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.RollbackException;
import java.io.IOException;

@ManagedBean
@ViewScoped
public class UserManager {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    // todo: change to string once User data type has been modified
    private Integer tel;
    private String passConfirm;
    private String address;

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

    public Integer getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = Integer.parseInt(tel);
    }
    public  void setTel(Integer tel){
        this.tel = tel;
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
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        JpaUserDaoImpl dao = new JpaUserDaoImpl();

        /*if(dao.verifyUser(username,password)){
            externalContext.getSessionMap().put("user", username);
            externalContext.redirect("/");
        }else{
            System.out.println("Error login");
            externalContext.getSessionMap().put("user", username);
            externalContext.redirect("/");

        }*/

        User currUser = dao.verifyUser(username,password);
        if(currUser != null){
            externalContext.getSessionMap().put("user", currUser);
            externalContext.redirect("/");
        }else{
            showUIMsg("Sorry, we couldn't find an account with those credentials. Please check you username and password and try again!");
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
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        return (User) externalContext.getSessionMap().get("user");

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
            showUIMsg("The entered passwords do not match!");
            return;
        }

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setPassword(password);
        user.setEmailAddress(email);
        user.setPhoneNumber(tel);
        user.setPostalAddress(address);

        try {
            JPAUtil.getJpaUserDaoImpl().createUser(user);
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.getSessionMap().put("user", user);
            externalContext.redirect("/");
        } catch (RollbackException e) {
            showUIMsg("Sorry, this username is already taken!");
        } catch (Exception e) {
            showUIMsg("Sorry, an error occurred while registering! Please try again");
        }
//        User user = new User();
//        user.setFirstName(firstName);
//        user.setLastName(lastName);
//        user.setUsername(username);
//        user.setPassword(password);
//        JPAUtil.getJpaUserDaoImpl().createUser(user);



    }

    public void showUIMsg(String content){
        FacesMessage msg = new FacesMessage(content);
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }
}
