package com.dt002g.reviewapplication.backend.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dt002g.reviewapplication.backend.models.RatingInterface;
import com.dt002g.reviewapplication.backend.models.Review;
import com.dt002g.reviewapplication.backend.repositories.ReviewRepository;
import com.dt002g.reviewapplication.backend.services.ReviewService;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;
    
    @Autowired
    private ReviewService reviewService;

    //  http://localhost:8080/api/v1/reviews/getAll
    @GetMapping("/getAll")
    public List<Review> getAll(){
        //return reviewRepository.findAll();
    	return reviewRepository.findAll();
    }
    
    @GetMapping
    @RequestMapping(value = "/getTopReviews", method = RequestMethod.GET)
    public List<Review> getTopReviews(){
    	List<Review> temp =  reviewRepository.findTop100ByOrderByIdAsc();
    	System.out.println("getTop100Reviews size:" + temp.size());
    	return temp;
    }
    
    @GetMapping
    @RequestMapping(value = "/getTopReviewsLargerThanId/{id}", method = RequestMethod.GET)
    public List<Review> getTopReviewsLargerThanId(@PathVariable Long id){
    	/*List<Review> temp =  reviewRepository.findTop100ByIdGreaterThanOrderByIdAsc(id);
    	System.out.println("getTopReviewsFromId:" + temp.size());
    	return temp;*/
    	String query = "SELECT * FROM reviews WHERE id > "+ id + " ORDER BY id ASC LIMIT 100";
    	return reviewRepository.customQuery(query);
    }
    
    //  http://localhost:8080/api/v1/reviews/getByString/{string} 
    @GetMapping()
    @RequestMapping("/getByString/{comment}")
    public List<Review> getByString(@PathVariable String comment){
    	return reviewRepository.findByCommentContaining(comment);
    }
    
    //  http://localhost:8080/api/v1/reviews/getByRating/{int} 
    @GetMapping()
    @RequestMapping("/getByRating/{rating}")
    public List<Review> getByRating(@PathVariable int rating){
    	return reviewRepository.findByRating(rating);
    }
    
    @GetMapping()
    @RequestMapping("/getTopReviewsByRatingLargerThanId/{rating}/{id}")
    public List<Review>  getTopReviewsByRatingLargerThanId(@PathVariable Integer rating, @PathVariable Long id){
    	//return reviewRepository.getTop100ByRatingAndIdGreaterThanId(rating, id);
    	
    	String query = "SELECT * FROM reviews WHERE rating = " + rating + " and id > "+ id + " ORDER BY id ASC LIMIT 100";
    	return reviewRepository.customQuery(query);
    }
    
    //  http://localhost:8080/api/v1/reviews/getByStrings/search?searchString1=cat&searchString2=dog etc... 
    @PostMapping(value = "/getByStrings/search")
    public List<Review> getByStrings(HttpServletRequest request){
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
    
    @GetMapping(value = "/getTopReviewsByStringsLargerThanId/{id}/search")
    public List<Review> getTopReviewsByStringsLargerThanId(@PathVariable Long id, HttpServletRequest request){
    	/*String query = "SELECT TOP 100 * FROM reviews WHERE id > "+id+" and comment LIKE '%" + request.getParameter("searchString1").toString() + "%'";
    			
		for(int i = 2; i <= 10; i++) {
			try {
				query += "OR comment LIKE '%" +  request.getParameter("searchString" + i).toString() + "%'";
			}
			catch(NullPointerException e) {
				break;
			}
		}
		
	    return reviewRepository.customQuery(query);*/
    	
    	String query = "SELECT * FROM reviews WHERE id > "+id+" and comment LIKE '%" + request.getParameter("searchString1").toString() + "%'";
		
		for(int i = 2; i <= 10; i++) {
			try {
				query += "OR comment LIKE '%" +  request.getParameter("searchString" + i).toString() + "%'";
			}
			catch(NullPointerException e) {
				break;
			}
		}
		query += " ORDER BY id ASC LIMIT 100";
	    return reviewRepository.customQuery(query);
    }
    
    @GetMapping(value = "/getTopReviewsByInclusiveStringsLargerThanId/{id}/search")
    public List<Review> getTopReviewsByInclusiveStringsLargerThanId(@PathVariable Long id, HttpServletRequest request){
    	/*String query = "SELECT TOP 100 * FROM reviews WHERE id > "+id+" and comment LIKE '%" + request.getParameter("searchString1").toString() + "%'";
    			
		for(int i = 2; i <= 10; i++) {
			try {
				query += "AND comment LIKE '%" +  request.getParameter("searchString" + i).toString() + "%'";
			}
			catch(NullPointerException e) {
				break;
			}
		}
		
	    return reviewRepository.customQuery(query);*/
    	
    	String query = "SELECT * FROM reviews WHERE id > "+id+" and comment LIKE '%" + request.getParameter("searchString1").toString() + "%'";
		
		for(int i = 2; i <= 10; i++) {
			try {
				query += "AND comment LIKE '%" +  request.getParameter("searchString" + i).toString() + "%'";
			}
			catch(NullPointerException e) {
				break;
			}
		}
		query += " ORDER BY id ASC LIMIT 100";
		
	    return reviewRepository.customQuery(query);
    }
    
    @GetMapping(value = "/getNumberOfReviewsByInclusiveStrings/search")
    public Integer getNumberOfReviewsByInclusiveStrings(HttpServletRequest request){
    	String query = "SELECT COUNT(*) FROM reviews WHERE comment LIKE '%" + request.getParameter("searchString1").toString() + "%'";
    			
		for(int i = 2; i <= 10; i++) {
			try {
				query += "AND comment LIKE '%" +  request.getParameter("searchString" + i).toString() + "%'";
			}
			catch(NullPointerException e) {
				break;
			}
		}
		
	    return reviewRepository.customNumberOfResultQuery(query);
    }
    @GetMapping(value = "/getTopReviewsByRatingAndStringsLargerThanId/{id}/{rating}/search{searchString}")
    public List<Review> getTopReviewsByRatingAndStringsLargerThanId(@PathVariable Long id, @PathVariable Integer rating, HttpServletRequest request){
    	/*String query = "SELECT TOP 100 * FROM reviews WHERE id > "+ id +" and rating = " + rating + " and comment LIKE '%" + request.getParameter("searchString1").toString() + "%'";
    			
		for(int i = 2; i <= 10; i++) {
			try {
				query += "OR comment LIKE '%" +  request.getParameter("searchString" + i).toString() + "%'";
			}
			catch(NullPointerException e) {
				break;
			}
		}
		
	    return reviewRepository.customQuery(query);*/
    	
    	String query = "SELECT * FROM reviews WHERE id > "+ id +" and rating = " + rating + " and comment LIKE '%" + request.getParameter("searchString1").toString() + "%'";
		
		for(int i = 2; i <= 10; i++) {
			try {
				query += "OR comment LIKE '%" +  request.getParameter("searchString" + i).toString() + "%'";
			}
			catch(NullPointerException e) {
				break;
			}
		}
		query += " ORDER BY id ASC LIMIT 100";
		
	    return reviewRepository.customQuery(query);
    }
    
    @PostMapping(value = "/getRatingByInclusiveSearchString/search{searchString}")
    public List<RatingInterface> getRatingByExplicitSearchString(HttpServletRequest request){
    	String query = "SELECT rating as rating , COUNT(comment) as amount FROM reviews WHERE comment LIKE '%" + request.getParameter("searchString1") + "%' ";
    	
    	for(int i = 2; i <= 10; i++) {
			try {
				query += "AND comment LIKE '%" +  request.getParameter("searchString" + i).toString() + "%'";
			}
			catch(NullPointerException e) {
				break;
			}
		}
    	 query += " GROUP BY rating ORDER BY rating ASC";
    	 System.out.println("Query: " + query);
    	 return reviewRepository.customQueryRatingInterface(query);
    }
    
    @GetMapping(value = "/getNumberOfReviewsByStrings/search{searchString}")
    public Map<String, Long> getNumberOfReviewsByStrings(HttpServletRequest request){
    	HashMap<String, Long> result = new HashMap<String, Long>();
		for(int i = 1; i <= 10; i++) {
			try {
				result.put(request.getParameter("searchString" + i).toString(), reviewRepository.getCountOfReviewsWhereCommentContains("%" + request.getParameter("searchString" + i).toString() + "%"));
			}
			catch(NullPointerException e) {
				break;
			}
		}
		
	    return result;
    }
    
    //  http://localhost:8080/api/v1/reviews/getByStrings/search?comment=cat&rating=2
    @PostMapping(value = "/getByRatingAndString/search")
    public List<Review> getByRatingAndString(HttpServletRequest request){
    	try {
    		String comment = "%" +  request.getParameter("comment") + "%";
    		return reviewRepository.getByRatingAndComment(Integer.parseInt(request.getParameter("rating")), comment);
    	}
    	catch(NullPointerException e) {
    		return null;
    	}
    }
    
    @GetMapping()
    @RequestMapping("/getRatingByString/{comment}")
    public List<RatingInterface> getRatingByString(@PathVariable String comment){
    	return reviewRepository.getRatingByComment("%" + comment + "%");
    }
    
    @GetMapping()
    @RequestMapping("/getCountOfReviewsWhereCommentContains/{comment}")
    public Long getCountOfReviewsWhereCommentContains(@PathVariable String comment){
    	
    	
    	return reviewRepository.getCountOfReviewsWhereCommentContains("%" + comment + "%");
    }
    


    //  Vet ej om vi behöver dom här under?
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
    
    @RequestMapping(path= "/csvupload", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> uploadCSVFile(@RequestPart String description, @RequestPart MultipartFile csvFile){
    	InputStream initialStream = null;
    	try {
    		initialStream = csvFile.getInputStream();
    		byte[] buffer = new byte[initialStream.available()];
    		initialStream.read(buffer);
    		File targetFile = new File(csvFile.getOriginalFilename());
    		try(OutputStream outStream = new FileOutputStream(targetFile)){
    			outStream.write(buffer);
    		}
    		reviewService.insertCsvFileDataToDataBase(targetFile);
    		
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    	return ResponseEntity.ok().build();
    }
    
}
