package com.dt002g.reviewapplication.backend.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.dt002g.reviewapplication.backend.models.Rating;
import com.dt002g.reviewapplication.backend.models.RatingInterface;
import com.dt002g.reviewapplication.backend.models.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewRepositoryImpl {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<Review> customQuery(String queryString){
		String hql = queryString;
		TypedQuery<Review> query = (TypedQuery<Review>) entityManager.createNativeQuery(hql, Review.class);
		return query.getResultList();
	}
	
	public Number customNumberOfResultQuery(String queryString){
		String hql = queryString;
		TypedQuery<Number> query = (TypedQuery<Number>) entityManager.createNativeQuery(hql);
		return query.getSingleResult();
	}
	
	public List<RatingInterface> customQueryRatingInterface(String queryString){
		String hql = queryString;
		TypedQuery<RatingInterface> query = (TypedQuery<RatingInterface>) entityManager.createNativeQuery(hql, Rating.class);
		return query.getResultList();
	}
}
