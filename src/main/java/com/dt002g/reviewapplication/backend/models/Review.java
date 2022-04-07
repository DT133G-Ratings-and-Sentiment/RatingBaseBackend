package com.dt002g.reviewapplication.backend.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

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
    
    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "reviews_id")
    private List<Sentence> sentences;
    
    @Column(name="score")
    int score;

    public Review() {}
    
    public Review(int rating, String comment, List<Sentence> pSentences, int score) {
    	sentences = new ArrayList<Sentence>();
    	this.rating = rating;
    	this.comment = comment;
    	sentences.addAll(pSentences);
    	this.score = score;
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

    
}
