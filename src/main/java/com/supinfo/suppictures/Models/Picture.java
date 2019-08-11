package com.supinfo.suppictures.Models;

import com.supinfo.suppictures.Enums.Category;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Pictures")
public class Picture {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;

    private String description;

    private String path;


    private Category category;

    private Integer visitorsCount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username")
    private User user;


    public Integer getVisitorsCount() {
        return visitorsCount;
    }

    public void setVisitorsCount(Integer visitorsCount) {
        this.visitorsCount = visitorsCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getCreated() {
        return created;
    }

    private Date created;

    public Date getUpdated() {
        return updated;
    }

    private Date updated;

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
