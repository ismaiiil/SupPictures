package com.supinfo.suppictures.Daos;

import com.supinfo.suppictures.Models.User;

import javax.persistence.RollbackException;
import java.util.List;


public interface UserDao {

    void createUser(User user) throws RollbackException, Exception;


    User verifyUser(String username, String password );

    List<User> listUsers();

    Long countUsers();

    void DeleteUsers(String username) throws RollbackException,Exception;

}
