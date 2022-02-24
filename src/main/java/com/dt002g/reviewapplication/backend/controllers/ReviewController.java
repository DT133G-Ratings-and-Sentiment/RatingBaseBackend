package com.dt002g.reviewapplication.backend.controllers;

import com.dt002g.reviewapplication.backend.models.Review;
import com.dt002g.reviewapplication.backend.repositories.ReviewRepository;
import com.mysql.cj.Session;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    //  http://localhost:8080/api/v1/reviews/getAll
    @GetMapping("/getAll")
    public List<Review> getAll(){
        return reviewRepository.findAll();
    }
    
    //  http://localhost:8080/api/v1/reviews/getByString/{string} 
    @GetMapping()
    @RequestMapping("/getByString/{comment}")
    public List<Review> getByString(@PathVariable String comment){
    	ExampleMatcher getByComment = ExampleMatcher.matchingAny()
    			.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
    			.withIgnorePaths("id", "rating")
    			.withIgnoreCase();
    	
    	Review review = new Review();
    	review.setFreeText(comment);
	    Example<Review> example = Example.of(review , getByComment);
	    return reviewRepository.findAll(example);
    }
    
    //  http://localhost:8080/api/v1/reviews/getByRating/{int} 
    @GetMapping()
    @RequestMapping("/getByRating/{rating}")
    public List<Review> getByRating(@PathVariable int rating){
    	ExampleMatcher getByRating = ExampleMatcher.matchingAll()
    			.withIgnorePaths("id", "comment");
    	
    	Review review = new Review();
    	review.setRating(rating);
    	Example<Review> example = Example.of(review, getByRating);
    	return reviewRepository.findAll(example);
    }
    
    @PostMapping(value = "/getByStrings/search")

    public List<Reviews> getByStrings(HttpServletRequest request){
    	String query = "SELECT * FROM reviews WHERE comment LIKE '%" + request.getParameter("searchString1").toString() + "%'";
    			
		for(int i = 2; i <= 10; i++) {
			try {
				query += "OR comment LIKE '%" +  request.getParameter("searchString" + i).toString() + "%'";
			}
			catch(NullPointerException e) {
				break;
			}
		}
		
	    return reviewRepository.customQuery(query);
    }
    

    @GetMapping
    @RequestMapping("{id}")
    public Optional<Review> get(@PathVariable Long id){
        return reviewRepository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Review create(@RequestBody final Review reference){
        return reviewRepository.saveAndFlush(reference);
    }

    @RequestMapping(value="{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        reviewRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Review update(@PathVariable Long id, @RequestBody Review reference){
        Review existingReference = reviewRepository.getById(id);
        BeanUtils.copyProperties(reference, existingReference, "id");
        return reviewRepository.saveAndFlush(existingReference);
    }
}
