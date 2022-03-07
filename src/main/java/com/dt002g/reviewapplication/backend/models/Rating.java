package com.dt002g.reviewapplication.backend.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Reviews")
public class Rating implements RatingInterface {
	@Id   
	@Column(name="rating")
	    int rating;
	    
	    @Column(name="amount")
	    int amount;

	    
	    public void setRating(int rating) {
	        this.rating = rating;
	    }

	    public void setAmount(int amount) {
	        this.amount = amount;
	    }
	    
		@Override
		public int getRating() {
			return rating;
		}
	
		@Override
		public int getAmount() {
			return amount;
		}
}
