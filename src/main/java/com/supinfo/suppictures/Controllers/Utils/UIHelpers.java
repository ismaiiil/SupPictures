package com.supinfo.suppictures.Controllers.Utils;

import com.supinfo.suppictures.Model.Database.ValueObjects.User;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class UIHelpers {
    public static void showUIMsg(String content) {
        FacesMessage msg = new FacesMessage(content);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public static User getLoggedInUser() {

        return (User) getContext().getSessionMap().get("user");
    }

    public static ExternalContext getContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

}
