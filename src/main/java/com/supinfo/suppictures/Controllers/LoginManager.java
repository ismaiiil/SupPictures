package com.supinfo.suppictures.Controllers;

import com.supinfo.suppictures.Core.ValueObjects.JpaUserDao;
import com.supinfo.suppictures.Core.ValueObjects.User;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@ManagedBean
@ViewScoped
public class LoginManager {
    private String username;
    private String password;


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

    public void login() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        JpaUserDao dao = new JpaUserDao();

        if(dao.verifyUser(username,password)){
            externalContext.getSessionMap().put("user", username);
            externalContext.redirect("/");
        }else{
            System.out.println("Error login");
            externalContext.getSessionMap().put("user", username);
            externalContext.redirect("/");

        }

    }
}
