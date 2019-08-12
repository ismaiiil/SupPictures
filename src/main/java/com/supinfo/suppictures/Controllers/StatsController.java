package com.supinfo.suppictures.Controllers;

import com.supinfo.suppictures.Model.Database.DaoImplementations.JpaPictureDaoImpl;
import com.supinfo.suppictures.Model.Database.DaoImplementations.JpaUserDaoImpl;
import com.supinfo.suppictures.Model.Database.Utils.JPAFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

@ViewScoped
@ManagedBean
public class StatsController {
    private JpaPictureDaoImpl picDao;
    private JpaUserDaoImpl userDao;
    private long userCount;
    private long picCount;

    @PostConstruct
    public void init(){
        picDao = JPAFactory.getJpaPictureDaoImpl();
        userDao = JPAFactory.getJpaUserDaoImpl();
        picCount = picDao.countPictures();
        userCount = userDao.countUsers();
    }
    public Long getPicCount() {
        return picCount;
    }

    public Long getUsersCount() {
        return userCount;
    }

    public JpaPictureDaoImpl getPicDao() {
        return picDao;
    }

    public JpaUserDaoImpl getUserDao() {
        return userDao;
    }

}
