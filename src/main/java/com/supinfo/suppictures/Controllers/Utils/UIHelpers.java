package com.supinfo.suppictures.Controllers.Utils;


import com.supinfo.suppictures.Model.Database.ValueObjects.User;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;

public class UIHelpers {


    public static void showUIMsg(String content) {
        FacesMessage msg = new FacesMessage(content);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * Get the object for currently signed in user
     * @return User
     */
    public static User getLoggedInUser() {

        return (User) getCookie("user");
    }

    public static ExternalContext getContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    /**
     * Get cookie with given key
     * @param key
     * @return Object stored in session
     */
    public static Object getCookie(Object key){
        return getContext().getSessionMap().get(key);
    }

    /**
     * Store an object with key
     * @param key
     * @param val
     */
    public static void putCookie(String key, Object val){
        getContext().getSessionMap().put(key,val);
    }








}
