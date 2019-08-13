package com.supinfo.suppictures.Controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.supinfo.suppictures.Model.Database.Enums.Category;
import com.supinfo.suppictures.Model.Database.ValueObjects.Picture;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.util.List;

@ManagedBean
@ViewScoped
public class PictureLoader {
    public Category category;
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public Category[] getCategories() {
        return Category.values();
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    private List<Picture> pictures;
    private String query;

    @PostConstruct
    public void init(){
        pictures = getRecentPictures();
    }

    /**
     * Get a list of recent pictures from Webservice
     * @return a list of pictures
     */
    public List<Picture> getRecentPictures() {
        return getPictures("http://localhost:8080/rest/pictures/recent");
    }

    private List<Picture> getPictures(String url) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(url);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        String pictures =  response.readEntity(String.class);
        Gson gson=new Gson();
        Type type = new TypeToken<List<Picture>>(){}.getType();
        List<Picture> result   = gson.fromJson(pictures, type);
        return result;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
    public boolean getNoImageFound(){
        return pictures.size() == 0;
    }

    public void getPicturesByQueryAndCategory(){
        String url = "http://localhost:8080/rest/pictures/search/?query=" + query + "&category=" + category;
        List<Picture> result  =  getPictures(url);
        if(result == null){
            this.pictures.clear();
            return;
        }
        this.pictures = result;

    }

}
