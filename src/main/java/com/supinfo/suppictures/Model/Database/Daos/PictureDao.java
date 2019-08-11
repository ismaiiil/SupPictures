package com.supinfo.suppictures.Model.Database.Daos;

import com.supinfo.suppictures.Model.Database.Enums.Category;
import com.supinfo.suppictures.Model.Database.ValueObjects.Picture;

import java.util.List;

public interface PictureDao {

    void createPicture(Picture picture) throws Exception;

    List<Picture> listPictures();
    List<Picture> searchPictureByName(String name);
    List<Picture> searchPictureByCategory(Category category);



}
