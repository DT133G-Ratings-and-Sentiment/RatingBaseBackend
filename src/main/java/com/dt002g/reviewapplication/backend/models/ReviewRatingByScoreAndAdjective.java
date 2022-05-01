package com.dt002g.reviewapplication.backend.models;

import java.util.ArrayList;
import java.util.List;

public class ReviewRatingByScoreAndAdjective {
	public String adjective;
	public List<ReviewRatingByScore> reviewRatingByScores;
	
	public ReviewRatingByScoreAndAdjective() {
		this.reviewRatingByScores = new ArrayList<>();
	}
	public ReviewRatingByScoreAndAdjective(String adjective, List<ReviewRatingByScore> reviewRatingByScores) {
		this.adjective = adjective;
		this.reviewRatingByScores = new ArrayList<>();
		this.reviewRatingByScores.addAll(reviewRatingByScores);
	}
	
	public String getAdjective() {
		return adjective;
	}
	public void setAdjective(String adjective) {
		this.adjective = adjective;
	}

	public List<ReviewRatingByScore> getReviewRatingByScores() {
		return reviewRatingByScores;
	}

	public void setReviewRatingByScores(List<ReviewRatingByScore> reviewRatingByScores) {
		this.reviewRatingByScores = reviewRatingByScores;
	}

	
	
}
