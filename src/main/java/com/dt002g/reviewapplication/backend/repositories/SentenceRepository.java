package com.dt002g.reviewapplication.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dt002g.reviewapplication.backend.models.Sentence;

@Repository
public interface SentenceRepository extends JpaRepository<Sentence, Long>{

}
