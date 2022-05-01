package com.dt002g.reviewapplication.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dt002g.reviewapplication.backend.models.Adjective;
import com.dt002g.reviewapplication.backend.util.Pair;


@Repository
public interface AdjectiveRepository extends JpaRepository<Adjective, Long>{
	
	@Query(value = "SELECT * FROM Adjective WHERE word = :word", nativeQuery = true )
	List<Adjective> getByAdjective(@Param("word") String word);
	
	//@Query(value = "select count(*) from reviews r inner join sentence s on r.id = s.reviews_id inner join sentence_2_adjective sa on s.id = sa.sentence_id inner join adjective a on a.id = sa.adjective_id WHERE (r.rating/20) > (select SUM(s2.score)/COUNT(*) as sum FROM sentence s2 where r.id = s2.reviews_id and r.rating >= :minRating and r.rating <= :maxRating) and a.word = :word", nativeQuery = true )
	/*@Query(value = "select count(*) from reviews r inner join sentence s on r.id = s.reviews_id inner join sentence_2_adjective sa on s.id = sa.sentence_id inner join adjective a on a.id = sa.adjective_id WHERE a.word = :word and ((r.rating/20) + 0.5) > (select SUM(s2.score)/COUNT(*) as sum FROM sentence s2 where r.id = s2.reviews_id and r.rating >= :minRating and r.rating <= :maxRating) and ((r.rating/20)-0.5) < (select SUM(s2.score)/COUNT(*) as sum FROM sentence s2 where r.id = s2.reviews_id and r.rating >= :minRating and r.rating <= :maxRating)", nativeQuery = true )
	Long getNumberOfTimesAdjectiveCorrelate(String word, int minRating, int maxRating);*/
	
	@Query(value = "select count(*) from reviews r where r.id in (select rew.id from reviews rew inner join sentence s on rew.id = s.reviews_id inner join sentence_2_adjective sa on s.id = sa.sentence_id inner join adjective a on sa.adjective_id = a.id  where a.word = :adjective) and (r.rating >= :minRating and r.rating < :maxRating and r.normalised_average_sentence_score >= :minScore and r.normalised_average_sentence_score < :maxScore)", nativeQuery = true) 
	long getNumberOfAdjectivesByNameInReviewRatingAndScoreRange(int minRating, int maxRating, double minScore, double maxScore, String adjective);
	
	@Query(value = "select count(*) from reviews r where r.id in (select rew.id from reviews rew inner join sentence s on rew.id = s.reviews_id inner join sentence_2_adjective sa on s.id = sa.sentence_id inner join adjective a on sa.adjective_id = a.id  where a.word = :adjective) and (r.rating >= :minRating and r.rating < :maxRating and r.normalised_median_sentence_score >= :minScore and r.normalised_median_sentence_score < :maxScore)", nativeQuery = true) 
	long getNumberOfAdjectivesByNameInReviewRatingAndMedianScoreRange(int minRating, int maxRating, double minScore, double maxScore, String adjective);
	
	@Query(value = "select a.word, sum(sa.number_of_occurence) from reviews r inner join sentence s2 on r.id = s2.reviews_id inner join sentence_2_adjective sa on s2.id = sa.sentence_id inner join adjective a on sa.adjective_id = a.id where r.rating = r.normalised_average_sentence_score group by a.word", nativeQuery = true) 
	List<Object[]> getNumberOfTimesAdjectiveOccureWhenRatingAndScoreIsTheSame();
}
