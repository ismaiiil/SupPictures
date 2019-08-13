package com.supinfo.suppictures.Model.Database.ValueObjects;

import com.google.gson.annotations.Expose;
import com.supinfo.suppictures.Model.Database.Enums.Category;

import javax.persistence.*;
import java.util.Date;

/**
 * Represents a picture entity and is used to create the 'Pictures' table in the corresponding database.
 */
@Entity
@Table(name="Pictures")
public class Picture {
        /**
         * Default Constructor for JPA to use to create a picture.
         */
        public Picture(){}

        /**
         * Constructor used to create a picture object with specified details.
         * @param name The name/title of the picture.
         * @param description The description of the picture.
         * @param category The category of the picture.
         * @param path The file path of the picture.
         * @param user The user(object, {@link User}) who uploaded the picture.
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

        /**
         * Sets the number of visitors on the picture.
         * @param visitorsCount An integer representing the number of visitors on the picture.
         */
        public void setVisitorsCount(Integer visitorsCount) {
            this.visitorsCount = visitorsCount;
        }

        /**
         * Gets the user who uploaded the picture.
         * @return A {@link User} representing the user who uploaded the picture
         */
        public User getUser() {
            return user;
        }

        /**
         * Sets the user who uploaded the picture.
         * @param user A {@link User} representing the user object who uploaded the picture.
         */
        public void setUser(User user) {
            this.user = user;
        }

        /**
         * Gets the id of the picture.
         * @return An integer representing the id of the picture.
         */
        public Integer getId() {
            return id;
        }

        /**
         * Sets the id of the picture.
         * @param id An integer representing the id of the picture.
         */
        public void setId(Integer id) {
            this.id = id;
        }

        /**
         * Gets the name of the picture.
         * @return A string representing the name of the picture.
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the name/title on the picture.
         * @param name An string representing the name of the picture.
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Gets the description of the picture.
         * @return A string representing the description of the picture.
         */
        public String getDescription() {
            return description;
        }

        /**
         * Sets the description of the picture.
         * @param description A string representing the description of the picture.
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * Gets the file path of the picture.
         * @return A string representing the file path of the picture.
         */
        public String getPath() {
            return path;
        }

        /**
         * Sets the file path of the picture.
         * @param path A string representing the file path of the picture.
         */
        public void setPath(String path) {
            this.path = path;
        }

        /**
         * Gets the date on which the picture was first uploaded.
         * @return A Date representing the date on which the picture was first uploaded.
         */
        public Date getCreated() {
            return created;
        }

        @Expose
        private Date created;

        /**
         * Gets the date on which the image was updated.
         * @return A Date representing the date on which the image was updated.
         */
        public Date getUpdated() {
                return updated;
            }

        @Expose
        private Date updated;


        @PrePersist
        /**
         * Sets the date on which the image was created.
         */
        protected void onCreate() {
            created = new Date();
        }

        @PreUpdate
        /**
         * Sets the date on which the image was updated.
         */
        protected void onUpdate() {
            updated = new Date();
        }

        /**
         * Gets the cateogry of the picture.
         * @return A {@link Category} representing the category of the picture.
         */
        public Category getCategory() {
            return category;
        }

        /**
         * Sets the category of the picture.
         * @param category A {@link Category} representing the category of the picture.
         */
        public void setCategory(Category category) {
            this.category = category;
        }

}
