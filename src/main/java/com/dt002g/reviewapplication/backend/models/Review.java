package com.dt002g.reviewapplication.backend.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    
    @JsonManagedReference
    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "reviews_id")
    private List<Sentence> sentences;

    @Column(name= "normalised_average_sentence_score")
    double normalisedAverageSentenceScore;
    
    @Column(name="normalised_median_sentence_score")
    double normalisedMedianSentenceScore;
    
    public Review() {}
    
    public Review(int rating, String comment, List<Sentence> pSentences, double normalisedAverageSentenceScore, double normalisedMedianSentenceScore) {
    	sentences = new ArrayList<Sentence>();
    	this.rating = rating;
    	this.comment = comment;
    	sentences.addAll(pSentences);
    	this.normalisedAverageSentenceScore = normalisedAverageSentenceScore;
    	this.normalisedMedianSentenceScore = normalisedMedianSentenceScore;
    	
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<Sentence> getSentences() {
		return sentences;
	}

	public void setSentences(List<Sentence> sentences) {
		this.sentences = sentences;
	}

	public double getNormalisedAverageSentenceScore() {
		return normalisedAverageSentenceScore;
	}

	public void setNormalisedAverageSentenceScore(double normalisedAverageSentenceScore) {
		this.normalisedAverageSentenceScore = normalisedAverageSentenceScore;
	}

	public double getNormalisedMedianSentenceScore() {
		return normalisedMedianSentenceScore;
	}

	public void setNormalisedMedianSentenceScore(double normalisedMedianSentenceScore) {
		this.normalisedMedianSentenceScore = normalisedMedianSentenceScore;
	}

	
    
}
