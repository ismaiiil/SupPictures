package com.supinfo.suppictures.Core.ValueObjects;

import com.supinfo.suppictures.Core.Utils.JPAUtil;
import com.supinfo.suppictures.Daos.UserDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Console;

public class JpaUserDaoImpl implements UserDao {

    private EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

    private EntityTransaction transaction = null;

    @Override
    public void createUser(User user) {
        try {
            // Get a transaction
            transaction = entityManager.getTransaction();

            // Begin the transaction
            transaction.begin();

            // Save the User object
            entityManager.persist(user);

            // Commit the transaction
            transaction.commit();

        } catch (Exception ex) {

            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }

            // Print the Exception
            ex.printStackTrace();

        } finally {

            // Close the EntityManager
            entityManager.close();

        }
    }



    @Override
    public User verifyUser(String username, String password) {

        User userEntity = entityManager.find(User.class,username);

        if(userEntity != null){
            if(!(userEntity.getPassword().equals(password))){
                return null;
            }
        }

        return userEntity;
    }


}
