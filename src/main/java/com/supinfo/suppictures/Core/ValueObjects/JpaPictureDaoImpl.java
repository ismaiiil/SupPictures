package com.supinfo.suppictures.Core.ValueObjects;

import com.supinfo.suppictures.Core.Utils.JPAUtil;
import com.supinfo.suppictures.Daos.PictureDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import java.util.List;

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


    public List<Picture> listPictures(){
        Query query = entityManager.createQuery("SELECT p FROM Picture p ");
        List<Picture> pictureList = query.getResultList();
        return pictureList;
    }

    public List<Picture> searchPictureByName(String name){
        Query query = entityManager.createQuery("SELECT p FROM Picture p WHERE p.name LIKE '%"+ name + "%'");
        List<Picture> pictureList = query.getResultList();
        return pictureList;
    }

    public List<Picture> searchPictureByCategory(Category category){
        Query query = entityManager.createQuery("SELECT p from Picture p WHERE p.category = ?1");
        query.setParameter(1,category);
        List<Picture> pictureList = query.getResultList();
        return pictureList;
    }
}
