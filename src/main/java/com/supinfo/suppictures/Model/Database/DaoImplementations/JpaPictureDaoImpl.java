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

/**
 * An implementation for PictureDao methods based on JPA.
 */
public class JpaPictureDaoImpl implements PictureDao {

    private EntityManager entityManager = JPAFactory.getEntityManagerFactory().createEntityManager();

    public EntityTransaction transaction = null;

    private Integer numOfPictures = 15;

    /**
     * Discards changes made from a transaction if applicable.
     * @param transaction The transaction made on the database.
     */
    private void rollbackTransaction(EntityTransaction transaction){
        if (transaction != null) {
            transaction.rollback();
        }
    }

    @Override
    /**
     * {@link PictureDao#createPicture(String, String, Category, String, User)}
     */
    public Picture createPicture(String name,String description,Category category,String path,User user) throws RollbackException,Exception {
        Picture picture = new Picture(name,description,category,path,user);

        transaction = entityManager.getTransaction();

        transaction.begin();

        entityManager.persist(picture);

        transaction.commit();
        return picture;
    }

    /**
     * {@link PictureDao#listPictures()}
     */
    public List<Picture> listPictures(){
        Query query = entityManager.createQuery("SELECT p FROM Picture p ORDER BY p.created DESC");
        List<Picture> pictureList = query.setMaxResults(numOfPictures).getResultList();
        return pictureList;
    }

    /**
     * {@link PictureDao#searchPictureByName(String)}
     */
    public List<Picture> searchPictureByName(String name){
        Query query = entityManager.createQuery("SELECT p FROM Picture p WHERE p.name LIKE '%"+ name + "%'");
        List<Picture> pictureList = query.getResultList();
        return pictureList;
    }

    /**
     * {@link PictureDao#searchPictureByCategory(Category)}
     */
    public List<Picture> searchPictureByCategory(Category category){
        Query query = entityManager.createQuery("SELECT p from Picture p WHERE p.category = ?1");
        query.setParameter(1,category);
        List<Picture> pictureList = query.getResultList();
        return pictureList;
    }

    /**
     * {@link PictureDao#findPictureByUser(User)}
     */
    public List<Picture> findPictureByUser(User user){
        Query query = entityManager.createQuery("SELECT p FROM Picture p WHERE p.user = ?1");
        query.setParameter(1,user);
        List<Picture> picturesList = query.getResultList();
        return picturesList;
    }

    @Override
    /**
     * {@link PictureDao#countPictures()}
     */
    public Long countPictures() {
        Query query = entityManager.createQuery("SELECT COUNT(p.id) FROM Picture p");
        Long pictureCount = (Long) query.getSingleResult();
        System.out.println("RUNNING COUNT");
        return pictureCount;
    }

    @Override
    /**
     * {@link PictureDao#updatePicture(Picture)}
     */
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
    /**
     * {@link PictureDao#deletePicture(Integer)}
     */
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

    /**
     * {@link PictureDao#searchByAll(String, Category)}
     */
    public List<Picture> searchByAll(String searchQuery, Category category) throws Exception{
        List<Picture> pictureList = null;
        if(category == Category.NONE){
            Query query = entityManager.createQuery("SELECT p from Picture p JOIN User u on p.user.username = u.username WHERE p.name LIKE '%" + searchQuery +"%' OR p.description LIKE '%" + searchQuery +"%' OR p.user.username = (SELECT u.username FROM User u WHERE u.postalAddress LIKE '%" + searchQuery + "%')");
            //query.setParameter(1,category);
            pictureList = query.getResultList();
        }else {
            Query query = entityManager.createQuery("SELECT p from Picture p JOIN User u on p.user.username = u.username WHERE p.category = ?1 AND ( p.name LIKE '%" + searchQuery +"%' OR p.description LIKE '%" + searchQuery +"%' OR p.user.username = (SELECT u.username FROM User u WHERE u.postalAddress LIKE '%" + searchQuery + "%'))");
            query.setParameter(1,category);
            pictureList = query.getResultList();
        }

        return pictureList;
    }
}
