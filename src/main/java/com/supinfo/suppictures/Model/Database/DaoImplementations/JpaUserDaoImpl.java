package com.supinfo.suppictures.Model.Database.DaoImplementations;

import com.google.common.hash.Hashing;
import com.supinfo.suppictures.Model.Database.ValueObjects.User;
import com.supinfo.suppictures.Model.Database.Utils.JPAFactory;
import com.supinfo.suppictures.Model.Database.Daos.UserDao;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JpaUserDaoImpl implements UserDao {

    private EntityManager entityManager = JPAFactory.getEntityManagerFactory().createEntityManager();

    public EntityTransaction transaction = null;

    @Override
    public User createUser(String firstName,String lastName,String username,String password, String email,String tel,String address)throws RollbackException, Exception {
        //hash password here before sending to db
        User user = new User(firstName,lastName,username,hashPassword(password), email,tel,address,false);
        // Get a transaction
        transaction = entityManager.getTransaction();

        // Begin the transaction
        transaction.begin();

        // Save the User object
        entityManager.persist(user);

        // Commit the transaction
        transaction.commit();
        return user;
    }

    private void rollbackTransaction(EntityTransaction transaction){
        if (transaction != null) {
            transaction.rollback();
        }
    }

    private String hashPassword(String password){
        return Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
    }


    @Override
    public User verifyUser(String username, String password) {
        //hash password before verifying
        User userEntity = entityManager.find(User.class,username);

        if(userEntity != null){
            if(!(userEntity.getPassword().equals(hashPassword(password)))){
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
        if(existingUser.getPassword().equals(updatedUser.getPassword())){
            //hash if the new password is different
            updatedUser.setPassword(hashPassword(updatedUser.getPassword()));
        }
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setEmailAddress(updatedUser.getEmailAddress());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setPostalAddress(updatedUser.getPostalAddress());
        entityManager.flush();

        // Commit the transaction
        transaction.commit();
    }

    @Override
    public User findUserByUsername(String username) throws Exception {
        User userEntity = entityManager.find(User.class,username);
        return userEntity;
    }
}
