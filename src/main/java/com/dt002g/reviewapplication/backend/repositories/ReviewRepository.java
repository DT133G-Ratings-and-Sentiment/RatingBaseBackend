package com.dt002g.reviewapplication.backend.repositories;


import com.dt002g.reviewapplication.backend.models.Review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
@Transactional

	List<Reviews> customQuery(String queryString);
}
