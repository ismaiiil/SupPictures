package com.supinfo.suppictures.Core.ValueObjects;

import com.supinfo.suppictures.Core.Utils.JPAUtil;
import com.supinfo.suppictures.Daos.UserDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;
import java.io.Console;
import java.sql.SQLIntegrityConstraintViolationException;

public class JpaUserDaoImpl implements UserDao {

    private EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

    public EntityTransaction transaction = null;

    @Override
    public void createUser(User user) throws RollbackException, Exception {
        // Get a transaction
        transaction = entityManager.getTransaction();

        // Begin the transaction
        transaction.begin();

        // Save the User object
        entityManager.persist(user);

        // Commit the transaction
        transaction.commit();
    }

    private void rollbackTransaction(EntityTransaction transaction){
        if (transaction != null) {
            transaction.rollback();
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
