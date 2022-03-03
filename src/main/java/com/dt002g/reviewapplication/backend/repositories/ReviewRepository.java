package com.dt002g.reviewapplication.backend.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dt002g.reviewapplication.backend.models.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
@Transactional

	List<Review> customQuery(String queryString);

	@Query(value = "SELECT * FROM Reviews WHERE rating = :rating AND comment LIKE :comment", nativeQuery = true )
	List<Review> getByRatingAndComment(@Param("rating") int rating, @Param("comment") String comment);
	
	List<Review> findByCommentContaining(String comment);
	List<Review> findByRating(int rating);
	
	public List<Review> findTop100ByOrderByIdAsc();
}
