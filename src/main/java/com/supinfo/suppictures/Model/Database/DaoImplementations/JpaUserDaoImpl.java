package com.supinfo.suppictures.Model.Database.DaoImplementations;

import com.supinfo.suppictures.Model.Database.ValueObjects.User;
import com.supinfo.suppictures.Model.Database.Utils.JPAFactory;
import com.supinfo.suppictures.Model.Database.Daos.UserDao;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import java.util.List;

public class JpaUserDaoImpl implements UserDao {

    private EntityManager entityManager = JPAFactory.getEntityManagerFactory().createEntityManager();

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

    @Override
    public List<User> listUsers() {
        Query query = entityManager.createQuery("SELECT u FROM User u");
        List<User> userList = query.getResultList();
        return userList;
    }

    @Override
    public Long countUsers() {
        Query query = entityManager.createQuery("SELECT COUNT(u.username) FROM User u");
        Long userCount = (Long) query.getSingleResult();
        return userCount;
    }


    @Override
    public void deleteUser(String username) throws RollbackException, Exception{
        transaction = entityManager.getTransaction();

        // Begin the transaction
        transaction.begin();

        // Find and delete the User object
        User user = entityManager.find(User.class,username);
        entityManager.remove(user);

        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void updateUser(User updatedUser) throws RollbackException, Exception {
        transaction = entityManager.getTransaction();

        // Begin the transaction
        transaction.begin();

        User existingUser = entityManager.find(User.class, updatedUser.getUsername());


        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setAdministrator(updatedUser.getAdministrator());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setEmailAddress(updatedUser.getEmailAddress());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setPostalAddress(updatedUser.getPostalAddress());

        entityManager.flush();

        // Commit the transaction
        transaction.commit();
    }
}
