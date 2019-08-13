package com.supinfo.suppictures.Controllers;

import com.supinfo.suppictures.Controllers.Utils.UIHelpers;
import com.supinfo.suppictures.Model.Database.Enums.Category;
import com.supinfo.suppictures.Model.Database.Utils.JPAFactory;
import com.supinfo.suppictures.Model.Database.ValueObjects.Picture;
import com.supinfo.suppictures.Model.ImageFileApi.ImageManager;
import com.supinfo.suppictures.Model.ImageFileApi.VerifyStatus;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import java.io.IOException;

@ManagedBean
@ViewScoped
public class UploadController {
    private String message;
    private String description;
    private Part file1;
    private String filename;
    public Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    public Category[] getCategories() {
        return Category.values();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


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

    public void uploadFile() {


        VerifyStatus verifiedStatus = ImageManager.verify(file1);
        if (verifiedStatus == VerifyStatus.VALID) {
            try {
                String path = ImageManager.savePartAs(file1, filename);
                setMessage(path);
                Picture pic = JPAFactory.getJpaPictureDaoImpl().createPicture(filename,description,category,path,UIHelpers.getLoggedInUser());
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                externalContext.redirect("/user/userProfile.xhtml");

            } catch (IOException e) {
                //setMessage("an exception has occured");
                UIHelpers.showUIMsg("an error has occured");
            } catch (Exception e) {
                UIHelpers.showUIMsg("an error has occured while saving to database");
            }

        } else {
            if (verifiedStatus == VerifyStatus.ERROR_INVALID_EXTENSION) {
                //setMessage("file has invalid extension");
                UIHelpers.showUIMsg("file has invalid extension");

            }
            if (verifiedStatus == VerifyStatus.ERROR_TOO_BIG) {
                //setMessage("file too big");
                UIHelpers.showUIMsg("file too big");

            }
        }
    }


}
