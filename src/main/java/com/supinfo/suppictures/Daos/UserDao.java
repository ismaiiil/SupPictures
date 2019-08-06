package com.supinfo.suppictures.Daos;

import com.supinfo.suppictures.Core.ValueObjects.User;

public interface UserDao {

    void createUser(User user);


    Boolean verifyUser(String username, String password );

}
