package com.supinfo.suppictures.Daos;

import com.supinfo.suppictures.Core.ValueObjects.Picture;

public interface PictureDao {

    void createPicture(Picture picture) throws Exception;
}
