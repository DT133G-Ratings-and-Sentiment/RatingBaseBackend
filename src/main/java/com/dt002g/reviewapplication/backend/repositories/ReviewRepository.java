package com.dt002g.reviewapplication.backend.repositories;


import com.dt002g.reviewapplication.backend.models.Reviews;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ReviewRepository extends JpaRepository<Reviews, Long> {

	List<Reviews> customQuery(String queryString);
}
