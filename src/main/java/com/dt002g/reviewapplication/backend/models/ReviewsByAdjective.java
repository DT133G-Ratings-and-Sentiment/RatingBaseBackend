package com.dt002g.reviewapplication.backend.models;

import java.util.ArrayList;
import java.util.List;

public class ReviewsByAdjective {
	public String adjective;
	public List<Review> reviews;
	
	public ReviewsByAdjective(String adjective, List<Review> reviews) {
		this.adjective = adjective;
		this.reviews = new ArrayList<>();
		this.reviews.addAll(reviews);
	}
	
	public String getAdjective() {
		return adjective;
	}
	public void setAdjective(String adjective) {
		this.adjective = adjective;
	}
	public List<Review> getReviews() {
		return reviews;
	}
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	
	
	
}
