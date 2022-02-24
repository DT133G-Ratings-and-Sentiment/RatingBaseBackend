package com.dt002g.reviewapplication.backend.repositories;


import com.dt002g.reviewapplication.backend.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
