package com.dt002g.reviewapplication.backend.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import com.dt002g.reviewapplication.backend.models.Reviews;
import org.springframework.stereotype.Component;

@Component
public class ReviewRepositoryImpl {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<Reviews> customQuery(String queryString){
		String hql = queryString;
		TypedQuery<Reviews> query = (TypedQuery<Reviews>) entityManager.createNativeQuery(hql, Reviews.class);
		return query.getResultList();
	}
}
