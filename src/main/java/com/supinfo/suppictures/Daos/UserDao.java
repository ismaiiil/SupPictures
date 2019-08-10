package com.supinfo.suppictures.Daos;

import com.supinfo.suppictures.Core.ValueObjects.User;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface UserDao {

    void createUser(User user) throws SQLIntegrityConstraintViolationException, Exception;


    User verifyUser(String username, String password );

    List<User> listUsers();

    Long countUsers();



}
