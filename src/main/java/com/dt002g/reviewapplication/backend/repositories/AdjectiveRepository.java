package com.dt002g.reviewapplication.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dt002g.reviewapplication.backend.models.Adjective;


@Repository
public interface AdjectiveRepository extends JpaRepository<Adjective, Long>{
	
	@Query(value = "SELECT * FROM Adjective WHERE word = :word", nativeQuery = true )
	List<Adjective> getByAdjective(@Param("word") String word);
	
	//@Query(value = "select count(*) from reviews r inner join sentence s on r.id = s.reviews_id inner join sentence_2_adjective sa on s.id = sa.sentence_id inner join adjective a on a.id = sa.adjective_id WHERE (r.rating/20) > (select SUM(s2.score)/COUNT(*) as sum FROM sentence s2 where r.id = s2.reviews_id and r.rating >= :minRating and r.rating <= :maxRating) and a.word = :word", nativeQuery = true )
	@Query(value = "select count(*) from reviews r inner join sentence s on r.id = s.reviews_id inner join sentence_2_adjective sa on s.id = sa.sentence_id inner join adjective a on a.id = sa.adjective_id WHERE a.word = :word and ((r.rating/20) + 0.5) > (select SUM(s2.score)/COUNT(*) as sum FROM sentence s2 where r.id = s2.reviews_id and r.rating >= :minRating and r.rating <= :maxRating) and ((r.rating/20)-0.5) < (select SUM(s2.score)/COUNT(*) as sum FROM sentence s2 where r.id = s2.reviews_id and r.rating >= :minRating and r.rating <= :maxRating)", nativeQuery = true )
	Long getNumberOfTimesAdjectiveCorrelate(String word, int minRating, int maxRating);
}
