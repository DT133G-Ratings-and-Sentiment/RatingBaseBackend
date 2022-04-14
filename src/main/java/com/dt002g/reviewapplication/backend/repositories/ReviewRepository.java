package com.dt002g.reviewapplication.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dt002g.reviewapplication.backend.models.RatingInterface;
import com.dt002g.reviewapplication.backend.models.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	@Transactional
	List<Review> customQuery(String queryString);
	Integer customNumberOfResultQuery(String queryString);
	List<RatingInterface> customQueryRatingInterface(String queryString);
	List<Review> findByCommentContaining(String comment);
	List<Review> findByRating(int rating);
	List<Review> findTop100ByOrderByIdAsc();
	List<Review> findTop100ByIdGreaterThanOrderByIdAsc(Long id);

	@Query(value = "SELECT * FROM Reviews WHERE rating = :rating AND comment LIKE :comment", nativeQuery = true )
	List<Review> getByRatingAndComment(@Param("rating") int rating, @Param("comment") String comment);
	
	@Query(value = "SELECT rating as rating , COUNT(comment) as amount FROM reviews WHERE comment LIKE :comment GROUP BY rating ORDER BY rating ASC", nativeQuery = true)
	List<RatingInterface> getRatingByComment(@Param("comment") String comment);
	
	@Query(value = "SELECT TOP 100 * FROM Reviews WHERE rating = :rating and id > :id", nativeQuery = true )
	List<Review> getTop100ByRatingAndIdGreaterThanId(@Param("rating") int rating, @Param("id") long id);
	
	@Query(value = "SELECT COUNT(*) FROM Reviews WHERE comment LIKE :word", nativeQuery = true)
	long getCountOfReviewsWhereCommentContains(String word);

	@Query(value = "SELECT SUM(s.score)/COUNT(*) FROM reviews r INNER JOIN sentence s on r.id = s.reviews_id WHERE r.rating = :rating", nativeQuery = true)
	long getAverageScoreOfRewiewsWithRating(int rating);
	
	/*@Query(value = "select count(*) from reviews r2 where (select sum(s.score)/count(*) from reviews r inner join sentence s on r.id = s.reviews_id where r.rating = :rating and r.id =r2.id) > :minScore and (select sum(s.score)/count(*) from reviews r inner join sentence s on r.id = s.reviews_id where r.id =r2.id)< :maxScore", nativeQuery = true)
	long getNumberOfRewiewsWithRatingXAndScoreBiggerThanYAndLesserThanZ(int rating, double minScore, double maxScore);*/
	
	/*@Query(value = "select count(*) from reviews r2 where (select (((sum(s.score)- count(*))/4)/count(*))*100 from reviews r inner join sentence s on r.id = s.reviews_id where r.rating = :rating and r.id =r2.id) >= :minScore and (select (((sum(s.score)- count(*))/4)/count(*))*100 from reviews r inner join sentence s on r.id = s.reviews_id where r.id =r2.id)< :maxScore", nativeQuery = true)
	long getNumberOfRewiewsWithRatingXAndScoreBiggerThanYAndLesserThanZ(int rating, double minScore, double maxScore);*/
	
	@Query(value = "select count(*) from reviews r2 where r2.rating = :rating and r2.normalised_average_sentence_score >= :minScore and r2.normalised_average_sentence_score < :maxScore", nativeQuery = true)
	long getNumberOfRewiewsWithRatingXAndScoreBiggerThanYAndLesserThanZ(int rating, double minScore, double maxScore);
}

