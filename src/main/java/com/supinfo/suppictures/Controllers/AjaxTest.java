package com.supinfo.suppictures.Controllers;

import com.supinfo.suppictures.Model.Database.ValueObjects.Picture;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class AjaxTest {
    public List<Picture> getPictures() {
        return pictures;
    }
    @PostConstruct
    public void init(){
        pictures = new ArrayList<Picture>();
        Picture pic = new Picture();
        pic.setName("Ajax");
        pic.setPath("https://images.pexels.com/photos/1274640/pexels-photo-1274640.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        pictures.add(pic);
        pictures.add(pic);
        pictures.add(pic);
        pictures.add(pic);

    }
    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    private List<Picture> pictures;
    public void add(){

        Picture pic = new Picture();
        pic.setName("Ajax");
        pic.setPath("https://images.pexels.com/photos/1274640/pexels-photo-1274640.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        pictures.add(pic);
        pictures.add(pic);

    }

}
