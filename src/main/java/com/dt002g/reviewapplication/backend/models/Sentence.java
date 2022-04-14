package com.dt002g.reviewapplication.backend.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

@Entity
@Table(name = "sentence")
public class Sentence {
	
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column(name = "text")
	String text;
	
	@Column(name = "very_positive")
	double veryPositive;
	
	@Column(name="positive")
	double positive;
	
	@Column(name="neutral")
	double neutral;
	
	@Column(name="negative")
	double negative;
	
	@Column(name="very_negative")
	double veryNegative;
	
	@Column(name="score")
	int score;
	
	@Column(name="normalised_score")
	double normalisedScore;
	
	@JsonManagedReference
	@OneToMany(mappedBy="id.sentence", cascade = CascadeType.ALL)
	List<SentenceToAdjective> sentenceToAdjectives;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="reviews_id")
	Review review;

	public Sentence() {
		
	}
	
	public Sentence(String text, List<Double> grades, int score, double normalisedScore) {
		this.text = text;
		this.score = score;
		if(grades.size() == 5) {
			this.veryPositive = grades.get(0);
			this.positive = grades.get(1);
			this.neutral = grades.get(2);
			this.negative = grades.get(3);
			this.veryNegative = grades.get(4);
		}
		this.normalisedScore = normalisedScore;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public double getVeryPositive() {
		return veryPositive;
	}

	public void setVeryPositive(double veryPositive) {
		this.veryPositive = veryPositive;
	}

	public double getPositive() {
		return positive;
	}

	public void setPositive(double positive) {
		this.positive = positive;
	}

	public double getNeutral() {
		return neutral;
	}

	public void setNeutral(double neutral) {
		this.neutral = neutral;
	}

	public double getNegative() {
		return negative;
	}

	public void setNegative(double negative) {
		this.negative = negative;
	}

	public double getVeryNegative() {
		return veryNegative;
	}

	public void setVeryNegative(double veryNegative) {
		this.veryNegative = veryNegative;
	}

	public Review getReview() {
		return this.review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public List<SentenceToAdjective> getSentenceToAdjectives() {
		return sentenceToAdjectives;
	}

	public void setSentenceToAdjectives(List<SentenceToAdjective> sentenceToAdjectives) {
		this.sentenceToAdjectives = sentenceToAdjectives;
	}

	public double getNormalisedScore() {
		return normalisedScore;
	}

	public void setNormalisedScore(double normalisedScore) {
		this.normalisedScore = normalisedScore;
	}
	
	
	

	
}
