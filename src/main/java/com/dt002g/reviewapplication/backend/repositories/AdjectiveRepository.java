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
	
}
