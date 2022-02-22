package com.dt002g.reviewapplication.backend.controllers;

import com.dt002g.reviewapplication.backend.models.Reviews;
import com.dt002g.reviewapplication.backend.repositories.ReviewRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    //  http://localhost:8080/api/v1/reviews/getAll
    @GetMapping("/getAll")
    public List<Reviews> getAll(){
        return reviewRepository.findAll();
    }
    
    //  http://localhost:8080/api/v1/reviews/getByString/{string} 
    @GetMapping()
    @RequestMapping("/getByString/{comment}")
    public List<Reviews> getByString(@PathVariable String comment){
    	ExampleMatcher getByComment = ExampleMatcher.matchingAny()
    			.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
    			.withIgnorePaths("id", "rating")
    			.withIgnoreCase();
    	
    	Reviews review = new Reviews();
    	review.setFreeText(comment);
	    Example<Reviews> example = Example.of(review , getByComment);
	    return reviewRepository.findAll(example);
    }

    @GetMapping
    @RequestMapping("{id}")
    public Optional<Reviews> get(@PathVariable Long id){
        return reviewRepository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reviews create(@RequestBody final Reviews reference){
        return reviewRepository.saveAndFlush(reference);
    }

    @RequestMapping(value="{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        reviewRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Reviews update(@PathVariable Long id, @RequestBody Reviews reference){
        Reviews existingReference = reviewRepository.getById(id);
        BeanUtils.copyProperties(reference, existingReference, "id");
        return reviewRepository.saveAndFlush(existingReference);
    }
}
