package com.supinfo.suppictures.Controllers;

import com.supinfo.suppictures.Controllers.Utils.UIHelpers;
import com.supinfo.suppictures.Model.Database.DaoImplementations.JpaPictureDaoImpl;
import com.supinfo.suppictures.Model.Database.Utils.JPAFactory;
import com.supinfo.suppictures.Model.Database.ValueObjects.Picture;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.util.List;


@ManagedBean
@RequestScoped
public class PictureController {
    public String getSourcePage() {
        return sourcePage;
    }

    public void setSourcePage(String sourcePage) {
        this.sourcePage = sourcePage;
    }

    private String sourcePage;
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
    public String storeEditPicture(Picture pic) {

        UIHelpers.putCookie("pic", pic);

        return "/user/editPicture.xhtml" + FacesContext.getCurrentInstance().getViewRoot().getViewId();


    }

    /**
     * Get image to be edited from session
     *
     * @return Picture
     */
    public Picture getEditPicture() {
        return (Picture) UIHelpers.getCookie("pic");
    }

    /**
     * Update Details
     */
    public void updatePicture() {
        try {
            getPicDao().updatePicture(getEditPicture());
            UIHelpers.getContext().redirect(getSourcePage());

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
            UIHelpers.getContext().redirect("/user/userProfile.xhtml");
        } catch (Exception e) {
            //todo: show msg on ui
            e.printStackTrace();
        }
    }


}
