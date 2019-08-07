package com.supinfo.suppictures.Core.ValueObjects;

import com.supinfo.suppictures.Core.Utils.JPAUtil;
import com.supinfo.suppictures.Daos.PictureDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class JpaPictureDaoImpl implements PictureDao {

    private EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

    private EntityTransaction transaction = null;

    @Override
    public void createPicture(Picture picture) throws Exception {
        try {
            transaction = entityManager.getTransaction();

            transaction.begin();

            entityManager.persist(picture);

            transaction.commit();

        } finally {

        }
    }
}
