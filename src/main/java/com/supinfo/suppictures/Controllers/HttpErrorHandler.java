package com.supinfo.suppictures.Controllers;



import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class HttpErrorHandler {

    public String getStatusCode(){
        return String.valueOf((Integer)FacesContext.getCurrentInstance().getExternalContext().
                getRequestMap().get("javax.servlet.error.status_code")) ;
    }
}