package com.supinfo.suppictures.Daos;

import com.supinfo.suppictures.Core.ValueObjects.Category;
import com.supinfo.suppictures.Core.ValueObjects.Picture;

import java.util.List;

public interface PictureDao {

    void createPicture(Picture picture) throws Exception;

    List<Picture> listPictures();
    List<Picture> searchPictureByName(String name);
    List<Picture> searchPictureByCategory(Category category);



}
