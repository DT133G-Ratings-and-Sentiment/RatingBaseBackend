package com.dt002g.reviewapplication.backend.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dt002g.reviewapplication.backend.models.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
@Transactional

	List<Review> customQuery(String queryString);
}
