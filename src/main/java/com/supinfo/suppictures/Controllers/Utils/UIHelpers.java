package com.supinfo.suppictures.Controllers.Utils;

import com.supinfo.suppictures.Model.Database.ValueObjects.User;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;

public class UIHelpers {

    public static final String GO_BACK = "goBack";

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

    /**
     * Helper method to store source page for redirect latter
     */
    public static void storeSourcePage(){
        String source = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        putCookie(GO_BACK,source);
    }

    /**
     * Helper method to go back to previously stored target page
     * @throws IOException
     */
    public static void goback() throws IOException {
        String redirectTarget = (String) UIHelpers.getCookie(GO_BACK);
        getContext().redirect(redirectTarget);
    }

}
