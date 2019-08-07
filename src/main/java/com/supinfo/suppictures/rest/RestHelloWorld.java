package com.supinfo.suppictures.rest;

import com.supinfo.suppictures.Core.Utils.JPAUtil;
import com.supinfo.suppictures.Core.ValueObjects.JpaUserDaoImpl;
import com.supinfo.suppictures.Core.ValueObjects.Picture;
import com.supinfo.suppictures.Core.ValueObjects.User;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/sayhello")
public class RestHelloWorld
{
    @GET
    @Produces("text/html")
    public Response getStartingPage()
    {
        //create("Tom","Riddle", "TRiddle323", "password1234");
        createPicture("Pic 1","Zafr la feu");
        String output = "<h1>Hello World!<h1>" +
                "<p>RESTful Service is running ... <br>Ping @ " + new Date().toString() + "</p<br>" + String.valueOf(verifyUser());
        return Response.status(200).entity(output).build();
    }

    public static void create(String firstName,String lastName, String username,String password) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);

        user.setPassword(password);

        try {
            JPAUtil.getJpaUserDaoImpl().createUser(user);
        } catch (RollbackException e) {
            System.out.println("Rollback Exception");
        } catch (Exception e) {
            System.out.println("General Exception");
        }
    }

    public static void createPicture(String name, String description){
        Picture picture = new Picture();
        picture.setName(name);
        picture.setDescription(description);

        try {
            JPAUtil.getJpaPictureDaoImpl().createPicture(picture);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static User verifyUser(){
        return JPAUtil.getJpaUserDaoImpl().verifyUser("Tiddle","password1234");
    }

    public static ArrayList readAll() {

        List users = null;

        // Create an EntityManager
        EntityManager manager = JPAUtil.getEntityManagerFactory().createEntityManager();
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
            manager.close();
        }
        ArrayList<String> userToString = new ArrayList<>();
        for (Object u:users) {
            userToString.add(((User) u).getFirstName());
        }
        return userToString;
    }
}

// TODO - Create Image, List Image, SearchByName, SearchByCategory