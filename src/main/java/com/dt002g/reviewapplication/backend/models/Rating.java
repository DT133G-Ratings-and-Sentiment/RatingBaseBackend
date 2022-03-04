package com.dt002g.reviewapplication.backend.models;

import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


public class Rating {
    int rating;
    int amount;
    
    public Rating(HashMap<Integer, Integer> values) {
    	this.rating = values.get(rating);
    	this.amount = values.get(amount);
    }
    
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
