package com.dt002g.reviewapplication.backend.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "adjective")
public class Adjective {
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column(name = "word")
	String word;

	@JsonManagedReference
	@Transient
	@OneToMany(mappedBy="id.adjective", cascade = CascadeType.ALL)
	List<SentenceToAdjective> sentenceToAdjectives;
	
	public Adjective() {}
	
	public Adjective(String adjective){
		this.word = adjective;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public List<SentenceToAdjective> getSentenceToAdjectives() {
		return sentenceToAdjectives;
	}

	public void setSentenceToAdjectives(List<SentenceToAdjective> sentenceToAdjectives) {
		this.sentenceToAdjectives = sentenceToAdjectives;
	}
	
	
	
	
}
