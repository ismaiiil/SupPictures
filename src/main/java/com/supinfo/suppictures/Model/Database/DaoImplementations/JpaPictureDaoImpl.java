package com.supinfo.suppictures.Model.Database.DaoImplementations;

import com.supinfo.suppictures.Model.Database.Enums.Category;
import com.supinfo.suppictures.Model.Database.ValueObjects.Picture;
import com.supinfo.suppictures.Model.Database.Utils.JPAFactory;
import com.supinfo.suppictures.Model.Database.Daos.PictureDao;
import com.supinfo.suppictures.Model.Database.ValueObjects.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import java.util.List;

public class JpaPictureDaoImpl implements PictureDao {

    private EntityManager entityManager = JPAFactory.getEntityManagerFactory().createEntityManager();

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

    public List<Picture> findPictureByUser(User user){
        Query query = entityManager.createQuery("SELECT p FROM Picture p WHERE p.user = ?1");
        query.setParameter(1,user);
        List<Picture> picturesList = query.getResultList();
        return picturesList;
    }

    @Override
    public Long countPictures() {
        Query query = entityManager.createQuery("SELECT COUNT(p.id) FROM Picture p");
        Long pictureCount = (Long) query.getSingleResult();
        return pictureCount;
    }

    @Override
    public void updatePicture(Picture updatedPicture) throws RollbackException,Exception {
        transaction = entityManager.getTransaction();

        // Begin the transaction
        transaction.begin();

        Picture existingPicture = entityManager.find(Picture.class, updatedPicture.getId());


        existingPicture.setUser(updatedPicture.getUser());
        existingPicture.setCategory(updatedPicture.getCategory());
        existingPicture.setDescription(updatedPicture.getDescription());
        existingPicture.setName(updatedPicture.getName());
        existingPicture.setPath(updatedPicture.getPath());
        existingPicture.setId(updatedPicture.getId());
        existingPicture.setVisitorsCount(updatedPicture.getVisitorsCount());

        entityManager.flush();

        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void deletePicture(Integer id) throws RollbackException,Exception {
        transaction = entityManager.getTransaction();

        // Begin the transaction
        transaction.begin();

        // Find and delete the User object
        Picture picture = entityManager.find(Picture.class,id);
        entityManager.remove(picture);

        // Commit the transaction
        transaction.commit();
    }


}
