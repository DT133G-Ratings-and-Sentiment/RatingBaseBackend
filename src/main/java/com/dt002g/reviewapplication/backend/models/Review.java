package com.dt002g.reviewapplication.backend.models;

import javax.persistence.*;

@Entity
@Table(name = "Reviews")
public class Review {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name="rating")
    int rating;

    @Column(name = "comment")
    String comment;

    public Review() {}
    
    public Review(int rating, String comment) {
    	this.rating = rating;
    	this.comment = comment;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public String getFreeText() {
        return comment;
    }
    public void setFreeText(String freeText) {
        this.comment = freeText;
    }
}
