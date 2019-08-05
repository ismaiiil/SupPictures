package com.supinfo.suppictures.rest;

import com.supinfo.suppictures.Core.Utils.JPAUtil;
import com.supinfo.suppictures.Core.ValueObjects.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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
        create("TestUser");

        String output = "<h1>Hello World!<h1>" +
                "<p>RESTful Service is running ... <br>Ping @ " + new Date().toString() + "</p<br>"
                + readAll().toString();
        return Response.status(200).entity(output).build();
    }

    public static void create(String name) {
        // Create an EntityManager
        EntityManager manager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            // Create a new User object
            User user = new User();
            user.setName(name);

            // Save the User object
            manager.persist(user);

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
            userToString.add(((User) u).getName());
        }
        return userToString;
    }
}
