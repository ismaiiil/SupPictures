package com.supinfo.suppictures.Core.ValueObjects;

import com.supinfo.suppictures.Core.Utils.JPAUtil;
import com.supinfo.suppictures.Daos.UserDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class JpaUserDao implements UserDao {

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
    public Boolean verifyUser(User user) {

        User userEntity = entityManager.find(User.class,user.getUsername());

        if (userEntity == null){
            return false;
        }
        else{
            if(userEntity.getPassword().equals(user.getPassword())){
                return true;
            }
            else {
                return false;
            }
        }

    }


}
