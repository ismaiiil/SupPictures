package com.supinfo.suppictures.Model.rest.Resources;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.supinfo.suppictures.Model.Database.Utils.JPAFactory;
import com.supinfo.suppictures.Model.Database.Enums.Category;
import com.supinfo.suppictures.Model.Database.ValueObjects.Picture;
import com.supinfo.suppictures.Model.Database.ValueObjects.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/pictures")
public class PicturesResources
{
    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByQueryAndCategory(@QueryParam("query") @DefaultValue("") String query,@QueryParam("category") @DefaultValue("NONE") Category category)
    {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Picture>>() {}.getType();
        List<Picture> searched = searchByAll(query,category);
        if(searched == null || searched.isEmpty()){
            return Response.status(404).entity(gson.toJson("Couldnt find what your are looking for")).build();
        }
        return Response.status(200).entity(gson.toJson(searched,listType)).build();
    }

    private List<Picture> searchByAll(String query,Category category) {
        return JPAFactory.getJpaPictureDaoImpl().searchByAll(query,category);
    }

    private Long userCount() {
        return JPAFactory.getJpaUserDaoImpl().countUsers();
    }

    private void deleteUser(String username){
        try {
            JPAFactory.getJpaUserDaoImpl().deleteUser(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateUser(){
        User user = new User();
        user.setUsername("TRiddle");
        user.setFirstName("Tommy");
        user.setLastName("Riddle");
        user.setEmailAddress("t.riddle@gmail.com");

        try {
            JPAFactory.getJpaUserDaoImpl().updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchByCategory(Category category) {
        List<Picture> pictureList = JPAFactory.getJpaPictureDaoImpl().searchPictureByCategory(category);
        for(Picture p:pictureList){
            System.out.println(p.getId() + "," + p.getName() + "," + p.getDescription());
        }
    }

    public static void create(String firstName,String lastName, String username,String password) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);

        user.setPassword(password);

        try {
            JPAFactory.getJpaUserDaoImpl().createUser(user);
        } catch (RollbackException e) {
            System.out.println("Rollback Exception");
        } catch (Exception e) {
            System.out.println("General Exception");
        }
    }

    public static void createPicture(String name, String description, Category category){
        Picture picture = new Picture();
        picture.setName(name);
        picture.setDescription(description);
        picture.setCategory(category);

        try {
            JPAFactory.getJpaPictureDaoImpl().createPicture(picture);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static User verifyUser(){
        return JPAFactory.getJpaUserDaoImpl().verifyUser("Tiddle","password1234");
    }

    public static void listUsers(){
        List<User> userList = JPAFactory.getJpaUserDaoImpl().listUsers();
        for(User u:userList){
            System.out.println(u.getUsername() + "," + u.getFirstName() + "," + u.getLastName());
        }
    }

    public void printPictureList(){
        List<Picture> pictureList = JPAFactory.getJpaPictureDaoImpl().listPictures();
        for(Picture p:pictureList){
            System.out.println(p.getId() + "," + p.getName() + "," + p.getDescription());
        }
    }

    public void searchByName(String name){
        List<Picture> pictureList = JPAFactory.getJpaPictureDaoImpl().searchPictureByName(name);
        for(Picture p:pictureList){
            System.out.println(p.getId() + "," + p.getName() + "," + p.getDescription());
        }    }

    public static ArrayList readAll() {

        List users = null;

        // Create an EntityManager
        EntityManager manager = JPAFactory.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            // Get a List of Users
            users = manager.createQuery("SELECT u FROM User u",
                    User.class).getResultList();

            // Commit the transaction
            transaction.commit();
        } catch (Exception ex) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            ex.printStackTrace();
        } finally {
            // Close the EntityManager
           // manager.close();
        }
        ArrayList<String> userToString = new ArrayList<>();
        for (Object u:users) {
            userToString.add(((User) u).getFirstName());
        }
        return userToString;
    }
}