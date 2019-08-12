package com.supinfo.suppictures.Model.Database.ValueObjects;

import com.google.gson.annotations.Expose;
import com.supinfo.suppictures.Model.Database.Enums.Category;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Pictures")
public class Picture {
    /**
     * default Constructor for JPA to use
     */
    public Picture(){}

    /**
     * Constructor for client use
     */
    public Picture(String name,String description,Category category,String path,User user) {
        this.name = name;
        this.description = description;
        this.path = path;
        this.category = category;
        this.user = user;
    }

    @Expose
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Expose
    private String name;

    @Expose
    private String description;

    @Expose
    private String path;

    @Expose
    private Category category;

    @Expose
    private Integer visitorsCount;

    @Expose
    @ManyToOne(cascade = CascadeType.MERGE)
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

    @Expose
    private Date created;

    public Date getUpdated() {
        return updated;
    }

    @Expose
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
