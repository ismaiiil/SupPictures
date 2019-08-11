package com.supinfo.suppictures.Controllers;

import com.supinfo.suppictures.Core.Utils.JPAUtil;
import com.supinfo.suppictures.DaoImplementations.JpaPictureDaoImpl;
import com.supinfo.suppictures.Enums.Category;
import com.supinfo.suppictures.Helper.Util;
import com.supinfo.suppictures.Model.ImageFileApi.ImageManager;
import com.supinfo.suppictures.Model.ImageFileApi.VerifyStatus;
import com.supinfo.suppictures.Models.Picture;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import java.io.*;
import java.util.Date;

@ManagedBean
@ViewScoped
public class UploadController {
    private String message;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category category;

    public Category[] getCategories(){
        return  Category.values();
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;
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
                Picture pic = new Picture();
                pic.setName(filename);
                pic.setDescription(description);
                pic.setCategory(category);
                pic.setPath(path);
                JPAUtil.getJpaPictureDaoImpl().createPicture(pic);
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                externalContext.redirect("/userProfile.xhtml");

            }catch (IOException e){
                //setMessage("an exception has occured");
                Util.showUIMsg("an error has occured");
            } catch (Exception e) {
                Util.showUIMsg("an error has occured while saving to database");
            }

        }else{
            if(verifiedStatus == VerifyStatus.ERROR_INVALID_EXTENSION){
                //setMessage("file has invalid extension");
                Util.showUIMsg("file has invalid extension");

            }
            if(verifiedStatus == VerifyStatus.ERROR_TOO_BIG){
                //setMessage("file too big");
                Util.showUIMsg("file too big");

            }
        }
    }




}
