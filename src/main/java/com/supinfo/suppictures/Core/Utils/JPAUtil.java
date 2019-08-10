package com.supinfo.suppictures.Core.Utils;

import com.supinfo.suppictures.DaoImplementations.JpaPictureDaoImpl;
import com.supinfo.suppictures.DaoImplementations.JpaUserDaoImpl;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
    private static final String PERSISTENCE_UNIT_NAME = "supPersistenceUnit";
    private static EntityManagerFactory factory;
    private static JpaUserDaoImpl jpaUserDaoImpl;
    private static JpaPictureDaoImpl jpaPictureDaoImpl;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return factory;
    }

    public static void shutdown() {
        if (factory != null) {
            factory.close();
        }
    }

    public static JpaUserDaoImpl getJpaUserDaoImpl(){
        if(jpaUserDaoImpl == null){
            jpaUserDaoImpl = new JpaUserDaoImpl();
        }
        return jpaUserDaoImpl;
    }

    public static JpaPictureDaoImpl getJpaPictureDaoImpl(){
        if(jpaPictureDaoImpl == null){
            jpaPictureDaoImpl = new JpaPictureDaoImpl();
        }
        return jpaPictureDaoImpl;
    }
}