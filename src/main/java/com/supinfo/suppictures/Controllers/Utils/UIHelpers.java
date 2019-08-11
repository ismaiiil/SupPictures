package com.supinfo.suppictures.Controllers.Utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class UIHelpers {
    public static void showUIMsg(String content){
        FacesMessage msg = new FacesMessage(content);
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }

}
