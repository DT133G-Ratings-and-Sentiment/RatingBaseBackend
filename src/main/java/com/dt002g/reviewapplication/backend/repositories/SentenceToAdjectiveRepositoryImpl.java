package com.dt002g.reviewapplication.backend.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import com.dt002g.reviewapplication.backend.models.Review;
import com.dt002g.reviewapplication.backend.models.SentenceToAdjective;

@Component
public class SentenceToAdjectiveRepositoryImpl {
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<SentenceToAdjective> customQuery(String queryString){
		TypedQuery<SentenceToAdjective> query = (TypedQuery<SentenceToAdjective>) entityManager.createNativeQuery(queryString, SentenceToAdjective.class);
		return query.getResultList();
	}
}
