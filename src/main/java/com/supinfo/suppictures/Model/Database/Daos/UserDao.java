package com.supinfo.suppictures.Model.Database.Daos;

import com.supinfo.suppictures.Model.Database.ValueObjects.User;

import javax.persistence.RollbackException;
import java.util.List;

/**
 * Contains a definition of all the functions that different User Daos should implement.
 */
public interface UserDao {

    /**
     * Creates a {@link User} in the database.
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param username The username of the user.
     * @param password The password of the user.
     * @param email The email address of the user.
     * @param tel The phone number of the user.
     * @param address The postal address/locality of the user.
     * @return The {@link User} that has been created.
     * @throws RollbackException
     * @throws Exception
     */
    User createUser(String firstName,String lastName,String username,String password, String email,String tel,String address) throws RollbackException, Exception;

    /**
     * Checks if a pair of inputted {@link User#username} and {@link User#password} corresponds to a pair on the database.
     * @param username The username of the user.
     * @param password The password of the user.
     * @return A {@link User} corresponding to the given username if the username and password match.
     */
    User verifyUser(String username, String password );

    /**
     * List all the {@link User} present on the database.
     * @return A list of all {@link User} present on the database.
     */
    List<User> listUsers();

    /**
     * Finds the number of distinct users on the database.
     * @return A long representing the number of users on the database.
     */
    Long countUsers();

    /**
     * Deletes a {@link User} from the database.
     * @param username The username of the user to be deleted.
     * @throws RollbackException
     * @throws Exception
     */
    void deleteUser(String username) throws RollbackException,Exception;

    /**
     * Updates a {@link User} on the database.
     * @param updatedUser An object representing the user with the updated details.
     * @throws RollbackException
     * @throws Exception
     */
    void updateUser(User updatedUser) throws RollbackException,Exception;

    /**
     * Find a specific {@link User} by their {@link User#username}.
     * @param username
     * @return A {@link User} representing the users to find on the database.
     * @throws Exception
     */
    User findUserByUsername(String username) throws Exception;

}
