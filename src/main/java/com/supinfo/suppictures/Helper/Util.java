package com.supinfo.suppictures.Helper;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public   class Util {
    public static void showUIMsg(String content){
        FacesMessage msg = new FacesMessage(content);
        FacesContext.getCurrentInstance().addMessage(null,msg);
    }

}
