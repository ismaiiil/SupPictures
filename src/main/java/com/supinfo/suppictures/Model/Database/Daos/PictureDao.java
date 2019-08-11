package com.supinfo.suppictures.Model.Database.Daos;

import com.supinfo.suppictures.Model.Database.Enums.Category;
import com.supinfo.suppictures.Model.Database.ValueObjects.Picture;
import com.supinfo.suppictures.Model.Database.ValueObjects.User;

import javax.persistence.RollbackException;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface PictureDao {

    void createPicture(Picture picture) throws Exception;

    List<Picture> listPictures();
    List<Picture> searchPictureByName(String name);
    List<Picture> searchPictureByCategory(Category category);
    List<Picture> findPictureByUser(User user);
    Long countPictures();
    void updatePicture(Picture updatedPicture) throws RollbackException,Exception;
    void deletePicture(Integer id) throws RollbackException,Exception;

}
