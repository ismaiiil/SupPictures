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

    private Integer numOfPictures = 15;

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
        Query query = entityManager.createQuery("SELECT p FROM Picture p ORDER BY p.created DESC");
        List<Picture> pictureList = query.setMaxResults(numOfPictures).getResultList();
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


    //TODO UPDATE THIS TO THE CORRECT SQL @vashist8
    /**
     * will return a search based on the searchQuery, will match the search query
     * based on {@link Picture#getName()} and {@link Picture#getDescription()} and
     * {@link Picture#getLocality()}
     * @param searchQuery is the search query
     * @param category category supplied by user
     * @return returns a list of pictures
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

    //172.21.3.21
}
