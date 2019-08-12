package com.supinfo.suppictures.Controllers;

import com.supinfo.suppictures.Controllers.Utils.UIHelpers;
import com.supinfo.suppictures.Model.Database.DaoImplementations.JpaPictureDaoImpl;
import com.supinfo.suppictures.Model.Database.Utils.JPAFactory;
import com.supinfo.suppictures.Model.Database.ValueObjects.Picture;

import javax.faces.bean.ManagedBean;
import java.io.IOException;
import java.util.List;


@ManagedBean
public class PictureController {
    private JpaPictureDaoImpl picDao = JPAFactory.getJpaPictureDaoImpl();
    private UserManager userManager = new UserManager();

    public List<Picture> getCurrentUserPictures() {
        return getPicDao().findPictureByUser(getUserManager().getCurrentUser());
    }

    public JpaPictureDaoImpl getPicDao() {
        return picDao;
    }


    public UserManager getUserManager() {
        return userManager;
    }

    public List<Picture> getAllPictures() {
        return getPicDao().listPictures();
    }

    /**
     * Save image to be edited in session
     *
     * @param pic
     */
    public void storeEditPicture(Picture pic) {

        UIHelpers.getContext().getSessionMap().put("pic", pic);
        try {
            UIHelpers.getContext().redirect("/editPicture.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Get image to be edited from session
     *
     * @return Picture
     */
    public Picture getEditPicture() {
        return (Picture) UIHelpers.getContext().getSessionMap().get("pic");
    }

    /**
     * Update Details
     */
    public void updatePicture() {
        try {
            getPicDao().updatePicture(getEditPicture());
            UIHelpers.getContext().redirect("/userProfile.xhtml");
        } catch (Exception e) {
            //todo: show msg on ui
            e.printStackTrace();
        }
    }

    /**
     * Delete image
     *
     * @param id
     */
    public void deletePicture(Integer id) {
        try {
            getPicDao().deletePicture(id);
            UIHelpers.getContext().redirect("/userProfile.xhtml");
        } catch (Exception e) {
            //todo: show msg on ui
            e.printStackTrace();
        }
    }


}
