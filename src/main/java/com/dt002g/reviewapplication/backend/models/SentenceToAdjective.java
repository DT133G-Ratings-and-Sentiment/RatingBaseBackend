package com.dt002g.reviewapplication.backend.models;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
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
	
	//private Sentence sentence;
	
	//private Adjective adjective;
	
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
	
	/*@JsonBackReference
	@Transient
	public Adjective getAdjective() {
		return id.getAdjective();
	}
	
	public void setAdjective(Adjective a) {
		this.id.setAdjective(a);
	}*/
	
	@JsonBackReference
	@EmbeddedId
	public SentenceToAdjectiveId getId() { return id;}
	public void setId(SentenceToAdjectiveId id) { this .id = id;}


	/*@ManyToOne
    @MapsId("sentence_id")
    @JoinColumn(name = "sentence_id")
	public Sentence getSentence() {
		return sentence;
	}


	public void setSentence(Sentence sentence) {
		this.sentence = sentence;
	}*/

	
	@ManyToOne
    @MapsId("adjective_id")
    @JoinColumn(name = "adjective_id")
	public Adjective getAdjective() {
		return id.getAdjective();
	}


	public void setAdjective(Adjective adjective) {
		this.id.setAdjective(adjective);
	}
	
	
}
