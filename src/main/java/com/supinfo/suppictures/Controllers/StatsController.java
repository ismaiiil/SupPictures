package com.supinfo.suppictures.Controllers;

import com.supinfo.suppictures.Model.Database.DaoImplementations.JpaPictureDaoImpl;
import com.supinfo.suppictures.Model.Database.DaoImplementations.JpaUserDaoImpl;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class StatsController {
    private JpaPictureDaoImpl picDao = new JpaPictureDaoImpl();
    private JpaUserDaoImpl userDao = new JpaUserDaoImpl();
    public Long getPicCount(){
        return getPicDao().countPictures();

    }

    public Long getUsersCount(){
        return getUserDao().countUsers();
    }

    public JpaPictureDaoImpl getPicDao() {
        return picDao;
    }

    public JpaUserDaoImpl getUserDao() {
        return userDao;
    }
}
