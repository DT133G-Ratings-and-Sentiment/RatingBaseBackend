package com.dt002g.reviewapplication.backend.repositories;


import com.dt002g.reviewapplication.backend.models.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Reviews, Long> {

}
