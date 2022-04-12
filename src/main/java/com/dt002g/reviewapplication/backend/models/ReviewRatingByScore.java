package com.dt002g.reviewapplication.backend.models;

public class ReviewRatingByScore {
	private int rating;
	private double minScore;
	private double maxScore;
	private long amount;
	
	public ReviewRatingByScore() {
		
	}
	public ReviewRatingByScore(int rating, double minScore, double maxScore, long amount) {
		this.rating = rating;
		this.minScore = minScore;
		this.maxScore = maxScore;
		this.amount = amount;
	}
	public ReviewRatingByScore(int rating, double minScore, double maxScore) {
		this.rating = rating;
		this.minScore = minScore;
		this.maxScore = maxScore;
		this.amount = 0;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public double getMinScore() {
		return minScore;
	}
	public void setMinScore(double minScore) {
		this.minScore = minScore;
	}
	public double getMaxScore() {
		return maxScore;
	}
	public void setMaxScore(double maxScore) {
		this.maxScore = maxScore;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	
	
	
}
