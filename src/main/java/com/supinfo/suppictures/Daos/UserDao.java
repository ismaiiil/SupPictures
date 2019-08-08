package com.supinfo.suppictures.Daos;

import com.supinfo.suppictures.Core.ValueObjects.User;

import java.sql.SQLIntegrityConstraintViolationException;

public interface UserDao {

    void createUser(User user) throws SQLIntegrityConstraintViolationException, Exception;


    User verifyUser(String username, String password );

}
