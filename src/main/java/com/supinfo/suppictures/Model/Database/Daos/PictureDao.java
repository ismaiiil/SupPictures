package com.supinfo.suppictures.Model.Database.Daos;

import com.supinfo.suppictures.Model.Database.Enums.Category;
import com.supinfo.suppictures.Model.Database.ValueObjects.Picture;
import com.supinfo.suppictures.Model.Database.ValueObjects.User;

import javax.persistence.RollbackException;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Contains a definition of all the functions that different Picture Daos should implement.
 */
public interface PictureDao {

    /**
     * Creates a {@link Picture} in the database.
     * @param name The name/title of the picture.
     * @param description The description of the picture.
     * @param category The category of the picture.
     * @param path The file path of the picture.
     * @param user The user(object) who uploaded the picture.
     * @return A Picture object representing the picture that has been created in the database.
     * @throws Exception
     */
    Picture createPicture(String name,String description,Category category,String path,User user) throws Exception;

    /**
     * List all the pictures that are stored in the database.
     * @return A list of all the pictures that are stored in the database.
     */
    List<Picture> listPictures();

    /**
     * Filters and returns all the pictures in the database corresponding to a {@link Picture#name}.
     * @param name The name filter to be applied on the pictures in the database.
     * @return A list of pictures matching the {@link Picture#name} filter applied.
     */
    List<Picture> searchPictureByName(String name);

    /**
     * Filters and returns all the pictures in the database corresponding to a specific {@link Picture#category}
     * @param category The category filter to be applied on the pictures in the database.
     * @return A list of pictures matching the {@link Picture#category} filter applied.
     */
    List<Picture> searchPictureByCategory(Category category);

    /**
     * Filters and returns all the pictures in the database corresponding to a specific {@link Picture#name},{@link Picture#description},{@link User#postalAddress} or {@link Picture#description}.
     * @param category The category filter to be applied on the pictures in the database.
     * @param searchQuery The name/description/locality filter to be applied on the pictures in the database.
     * @return A list of pictures matching the different filters applied.
     */
    List<Picture> searchByAll(String searchQuery, Category category) throws Exception;

    /**
     * Finds all the pictures uploaded by a specified {@link User}.
     * @param user The user whose pictures need to be searched for.
     * @return A list of pictures uploaded by the specified {@link User}.
     */
    List<Picture> findPictureByUser(User user);

    /**
     * Gets the total number of pictures stored on the database.
     * @return A long representing the total number of pictures stored on the database.
     */
    Long countPictures();

    /**
     * Updates details of an existing {@link Picture} on the database.
     * @param updatedPicture A {@link Picture} representing the updated picture details.
     * @throws RollbackException
     * @throws Exception
     */
    void updatePicture(Picture updatedPicture) throws RollbackException,Exception;

    /**
     * Deletes a {@link Picture} on the database.
     * @param id The id of the picture to be deleted.
     * @throws RollbackException
     * @throws Exception
     */
    void deletePicture(Integer id) throws RollbackException,Exception;

}
