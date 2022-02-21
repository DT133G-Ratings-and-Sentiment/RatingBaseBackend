package com.dt002g.reviewapplication.backend.controllers;

import com.dt002g.reviewapplication.backend.models.Review;
import com.dt002g.reviewapplication.backend.repositories.ReviewRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/references")
public class ReviewController {

    @Autowired
    private ReviewRepository referenceRepository;

    @GetMapping("/getAll")
    public List<Review> getAll(){
        return referenceRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Optional<Review> get(@PathVariable Long id){
        return referenceRepository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Review create(@RequestBody final Review reference){
        return referenceRepository.saveAndFlush(reference);
    }

    @RequestMapping(value="{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        referenceRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Review update(@PathVariable Long id, @RequestBody Review reference){
        Review existingReference = referenceRepository.getById(id);
        BeanUtils.copyProperties(reference, existingReference, "id");
        return referenceRepository.saveAndFlush(existingReference);
    }
}
