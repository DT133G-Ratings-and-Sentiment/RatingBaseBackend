package com.dt002g.reviewapplication.backend.models;

public class AdjectiveByReviewRatingAndScore {
	public String adective;
	public long amount;
	public int minRating;
	public int maxRating;
	public double minScore;
	public double maxScore;
	
	public AdjectiveByReviewRatingAndScore(){}
	
	public AdjectiveByReviewRatingAndScore(String adective, long amount, int minRating, int maxRating, double minScore,
			double maxScore) {
		super();
		this.adective = adective;
		this.amount = amount;
		this.minRating = minRating;
		this.maxRating = maxRating;
		this.minScore = minScore;
		this.maxScore = maxScore;
	}
	public String getAdective() {
		return adective;
	}
	public void setAdective(String adective) {
		this.adective = adective;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public int getMinRating() {
		return minRating;
	}
	public void setMinRating(int minRating) {
		this.minRating = minRating;
	}
	public int getMaxRating() {
		return maxRating;
	}
	public void setMaxRating(int maxRating) {
		this.maxRating = maxRating;
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
	
	
}
