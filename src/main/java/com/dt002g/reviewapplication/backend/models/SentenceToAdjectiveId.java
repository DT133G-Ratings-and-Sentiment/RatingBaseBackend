package com.dt002g.reviewapplication.backend.models;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class SentenceToAdjectiveId implements Serializable{
	private Sentence sentence;
	private Adjective adjective;
	
	public SentenceToAdjectiveId() {
		
	}
	
	public SentenceToAdjectiveId(Sentence s, Adjective a) {
		this.sentence = s;
		this.adjective = a;
	}
	
	@ManyToOne
	@JoinColumn(name = "sentence_id")
	public Sentence getSentence() {
		return sentence;
	}
	
	public void setSentence(Sentence s) {
		this.sentence = s;
	}
	
	@ManyToOne
	@JoinColumn(name = "adjective_id")
	public Adjective getAdjective() {
		return adjective;
	}
	
	public void setAdjective(Adjective a) {
		this.adjective = a;
	}
	
}
