package com.supinfo.suppictures.Model.Database.Utils;

import com.supinfo.suppictures.Model.Database.DaoImplementations.JpaPictureDaoImpl;
import com.supinfo.suppictures.Model.Database.DaoImplementations.JpaUserDaoImpl;
import com.supinfo.suppictures.Model.Database.ValueObjects.User;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Represents a factory pattern allowing several Dao Implementations for different data sources.
 */
public class JPAFactory {
    private static final String PERSISTENCE_UNIT_NAME = "supPersistenceUnit";
    private static EntityManagerFactory factory;
    private static JpaUserDaoImpl jpaUserDaoImpl;
    private static JpaPictureDaoImpl jpaPictureDaoImpl;

    /**
     * Implements a singleton pattern for the EntityManagerFactory to return only one instance per application.
     * @return A unique EntityManager object for each application.
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        //checking if another instance of factory already exists
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return factory;
    }

    /**
     * Closes any EntityManager object that is open.
     */
    public static void shutdown() {
        if (factory != null) {
            factory.close();
        }
    }

    /**
     * Gives an instance of the JPA UserDao Implementation.
     * @return An object of {@link JpaUserDaoImpl}.
     */
    public static JpaUserDaoImpl getJpaUserDaoImpl(){
        if(jpaUserDaoImpl == null){
            jpaUserDaoImpl = new JpaUserDaoImpl();
        }
        return jpaUserDaoImpl;
    }

    /**
     * Gives an instance of the JPA PictureDao Implementation.
     * @return An object of {@link JpaPictureDaoImpl}.
     */
    public static JpaPictureDaoImpl getJpaPictureDaoImpl(){
        if(jpaPictureDaoImpl == null){
            jpaPictureDaoImpl = new JpaPictureDaoImpl();
        }
        return jpaPictureDaoImpl;
    }
}