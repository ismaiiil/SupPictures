package com.supinfo.suppictures.Controllers;

import com.supinfo.suppictures.Model.ImageFileApi.ImageManager;
import com.supinfo.suppictures.Model.ImageFileApi.VerifyStatus;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import java.io.*;

@ManagedBean
@ViewScoped
public class UploadController {
    private String message;
    private Part file1;
    private String filename;
    public Part getFile1() {
        return file1;
    }
    public void setFile1(Part file1) {
        this.file1 = file1;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void uploadFile(){
        VerifyStatus verifiedStatus = ImageManager.verify(file1);
        if(verifiedStatus == VerifyStatus.VALID){
            try{
                String path = ImageManager.savePartAs(file1,filename);
                setMessage(path);
            }catch (IOException e){
                setMessage("an exception has occured");
            }

        }else{
            if(verifiedStatus == VerifyStatus.ERROR_INVALID_EXTENSION){
                setMessage("file has invalid extension");
            }
            if(verifiedStatus == VerifyStatus.ERROR_TOO_BIG){
                setMessage("file too big");
            }
        }
    }


}
