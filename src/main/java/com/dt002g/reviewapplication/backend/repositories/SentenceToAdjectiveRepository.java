package com.dt002g.reviewapplication.backend.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dt002g.reviewapplication.backend.models.Adjective;
import com.dt002g.reviewapplication.backend.models.Review;
import com.dt002g.reviewapplication.backend.models.SentenceToAdjective;
import com.dt002g.reviewapplication.backend.models.SentenceToAdjectiveId;
@Repository
public interface SentenceToAdjectiveRepository extends JpaRepository<SentenceToAdjective, SentenceToAdjectiveId>{
	/*@Query(value = "SELECT * FROM Adjective WHERE adjective = :word", nativeQuery = true )
	List<SenetnceToAdjective> getBySenetnceAndAdjective(@Param("sentence") Sentence s);
	
	@Query(value = "SELECT * FROM Adjective WHERE adjective_id = :adjectiveId and sentence_id = :sentenceId", nativeQuery = true )
	List<SentenceToAdjective> getByAdjectiveIdAndSentenceId(@Param("adjectveId") Long adjectiveId, @Param("sentenceId") Long sentenceId);*/
	
	
	@Transactional
	List<SentenceToAdjective> customQuery(String queryString);
}
