package com.dt002g.reviewapplication.backend.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table
public class Reviews {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name="rating")
    int rating;

    @Column(name = "comment")
    String comment;

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
