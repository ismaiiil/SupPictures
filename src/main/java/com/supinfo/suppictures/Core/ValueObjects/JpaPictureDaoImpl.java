package com.supinfo.suppictures.Core.ValueObjects;

import com.supinfo.suppictures.Core.Utils.JPAUtil;
import com.supinfo.suppictures.Daos.PictureDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

public class JpaPictureDaoImpl implements PictureDao {

    private EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

    public EntityTransaction transaction = null;

    private void rollbackTransaction(EntityTransaction transaction){
        if (transaction != null) {
            transaction.rollback();
        }
    }

    @Override
    public void createPicture(Picture picture) throws RollbackException,Exception {
        transaction = entityManager.getTransaction();

        transaction.begin();

        entityManager.persist(picture);

        transaction.commit();
    }
}
