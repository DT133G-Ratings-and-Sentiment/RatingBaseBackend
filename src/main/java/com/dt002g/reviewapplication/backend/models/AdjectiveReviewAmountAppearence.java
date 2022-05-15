package com.dt002g.reviewapplication.backend.models;

import java.util.ArrayList;

public class AdjectiveReviewAmountAppearence {
    String adjective;
    int numberOfAppearancesPerReview;
    ArrayList<ReviewRatingByScore> reviewRatingByScoreMatrix;

    public AdjectiveReviewAmountAppearence(){}

    public AdjectiveReviewAmountAppearence(String adjective, int numberOfAppearancesPerReview, ArrayList<ReviewRatingByScore> reviewRatingByScoreMatrix){
        this.adjective = adjective;
        this.numberOfAppearancesPerReview = numberOfAppearancesPerReview;
        this.reviewRatingByScoreMatrix = new ArrayList<>();
        this.reviewRatingByScoreMatrix.addAll(reviewRatingByScoreMatrix);
    }

    public String getAdjective() {
        return this.adjective;
    }

    public void setAdjective(String adjective) {
        this.adjective = adjective;
    }

    public int getNumberOfAppearancesPerReview() {
        return this.numberOfAppearancesPerReview;
    }

    public void setNumebrOfAppearancesPerReview(int numberOfAppearancesPerReview) {
        this.numberOfAppearancesPerReview = numberOfAppearancesPerReview;
    }

    public ArrayList<ReviewRatingByScore> getReviewRatingByScoreMatrix() {
        return reviewRatingByScoreMatrix;
    }

    public void setReviewRatingByScoreMatrix(ArrayList<ReviewRatingByScore> reviewRatingByScoreMatrix) {
        this.reviewRatingByScoreMatrix= reviewRatingByScoreMatrix;
    }
}
