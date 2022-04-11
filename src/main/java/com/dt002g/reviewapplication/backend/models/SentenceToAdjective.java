package com.dt002g.reviewapplication.backend.models;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name= "sentence_2_adjective")
@AssociationOverrides({
	@AssociationOverride(name="id.sentence", joinColumns = @JoinColumn(name = "sentence_id")),
	@AssociationOverride(name="id.adjective", joinColumns = @JoinColumn(name = "adjective_id"))
})
public class SentenceToAdjective {
	

	SentenceToAdjectiveId id = new SentenceToAdjectiveId();
	
	private double numberOfOccurence;
	
	public SentenceToAdjective() {
		
	}
	
	
	public SentenceToAdjective(Sentence s, Adjective a, double nOO) {
		this.id = new SentenceToAdjectiveId(s, a);
		this.numberOfOccurence = nOO;
		
	}
	
	@Column(name= "number_of_occurence")
	public double getNumberOfOccurence() {
		return this.numberOfOccurence;
	}
	
	public void setNumberOfOccurence(double nOO) {
		this.numberOfOccurence = nOO;
	}
	
	@JsonBackReference
	@Transient
	public Sentence getSentence() {
		return id.getSentence();
	}
	
	public void setSentence(Sentence s) {
		this.id.setSentence(s);
	}
	
	@JsonBackReference
	@Transient
	public Adjective getAdjective() {
		return id.getAdjective();
	}
	
	public void setAdjective(Adjective a) {
		this.id.setAdjective(a);
	}
	
	@JsonBackReference
	@EmbeddedId
	public SentenceToAdjectiveId getId() { return id;}
	public void setId(SentenceToAdjectiveId id) { this .id = id;}
}
