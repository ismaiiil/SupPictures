package com.supinfo.suppictures.Daos;

import com.supinfo.suppictures.Enums.Category;
import com.supinfo.suppictures.Models.Picture;

import java.util.List;

public interface PictureDao {

    void createPicture(Picture picture) throws Exception;

    List<Picture> listPictures();
    List<Picture> searchPictureByName(String name);
    List<Picture> searchPictureByCategory(Category category);



}
