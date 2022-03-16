package com.dt002g.reviewapplication.backend;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class BackendApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	final private String searchExtension = "search?searchString1=good&searchString2=cat";
	final private String searchExtensionWithRating = "search?rating=1&comment=test";
	final private String api = "/api/v1/reviews/";

	@Test
	public void getAllShouldReturnStatus200() throws Exception{
		System.out.println("Testing: Get all");
		this.mockMvc.perform(get(api + "getAll"))
		.andExpect(status().isOk());
	}
	
	@Test
	public void getTopReviewsShouldReturnStatus200() throws Exception{
		System.out.println("Testing: Get top reviews");
		this.mockMvc.perform(get(api + "getTopReviews"))
		.andExpect(status().isOk());
	}
	
	@Test
	public void getTopReviewsLargerThanIdShouldReturnStatus200() throws Exception{
		System.out.println("Testing: Get top reviews larger than id");
		this.mockMvc.perform(get(api + "getTopReviewsLargerThanId/0"))
		.andExpect(status().isOk());
	}
	
	@Test
	public void getbyStringShouldReturnStatus200() throws Exception{
		System.out.println("Testing: Get by string");
		this.mockMvc.perform(get(api + "getByString/test"))
		.andExpect(status().isOk());
	}
	
	@Test
	public void getByRatingShouldReturnStatus200() throws Exception{
		System.out.println("Testing: Get by rating");
		this.mockMvc.perform(get(api + "getByRating/1"))
		.andExpect(status().isOk());
	}
	
	@Test
	public void getTopReviewsByRatingLargerThanIdShouldReturnStatus200() throws Exception{
		System.out.println("Testing: Get top reviews by rating larger than id");
		this.mockMvc.perform(get(api + "getTopReviewsByRatingLargerThanId/1/0"))
		.andExpect(status().isOk());
	}
	
	@Test
	public void getByStringsShouldReturnStatus200() throws Exception{
		System.out.println("Testing: Get by strings");
		this.mockMvc.perform(post(api + "getByStrings/" + searchExtension))
		.andExpect(status().isOk());
	}
	
	@Test
	public void getTopReviewsByStringsLargerThanIdShouldReturnStatus200() throws Exception{
		System.out.println("Testing: Get top reviews by strings larger than id");
		this.mockMvc.perform(get(api + "getTopReviewsByStringsLargerThanId/0/" + searchExtension))
		.andExpect(status().isOk());
	}
	
	@Test
	public void getTopReviewsByInclusiveStringsLargerThanIdShouldReturnStatus200() throws Exception {
		System.out.println("Testing: Get top reviews by inclusive strings larger than id");
		this.mockMvc.perform(get(api + "getTopReviewsByInclusiveStringsLargerThanId/1/" + searchExtension))
		.andExpect(status().isOk());
	}
	
	@Test
	public void getNumberOfReviewsByInclusiveStringsShouldReturnStatus200() throws Exception {
		System.out.println("Testing: Get number of reviews by inclusive strings");
		this.mockMvc.perform(get(api + "getNumberOfReviewsByInclusiveStrings/" + searchExtension))
		.andExpect(status().isOk());
	}
	
	@Test
	public void getTopReviewsByRatingAndStringsLargerThanIdShouldReturnStatus200() throws Exception {
		System.out.println("Testing: Get top reviews by rating and strings larger than id");
		this.mockMvc.perform(get(api + "getTopReviewsByRatingAndStringsLargerThanId/0/1/" + searchExtension))
		.andExpect(status().isOk());
	}
	
	@Test
	public void getRatingByInclusiveSearchStringShouldReturnStatus200() throws Exception {
		System.out.println("Testing: Get rating by inclusive search string");
		this.mockMvc.perform(post(api + "getRatingByInclusiveSearchString/" + searchExtension))
		.andExpect(status().isOk());
	}
	
	@Test
	public void getNumberOfReviewsByStringsShouldReturnStatus200() throws Exception {
		System.out.println("Testing: Get number of reviews by strings");
		this.mockMvc.perform(get(api + "getNumberOfReviewsByStrings/" + searchExtension))
		.andExpect(status().isOk());
	}
	
	@Test
	public void getByRatingAndStringShouldReturnStatus200() throws Exception {
		System.out.println("Testing: Get by rating and string");
		this.mockMvc.perform(post(api + "getByRatingAndString/" + searchExtensionWithRating))
		.andExpect(status().isOk());
	}
	
	@Test
	public void getRatingByStringShouldReturnStatus200() throws Exception{
		System.out.println("Testing: Get rating by string");
		this.mockMvc.perform(get(api + "getRatingByString/test"))
		.andExpect(status().isOk());
	}
	
	@Test
	public void getCountOfReviewsWhereCommentContainsShouldReturnStatus200() throws Exception{
		System.out.println("Testing: Get count of reviews where comment contains");
		this.mockMvc.perform(get(api + "getCountOfReviewsWhereCommentContains/test"))
		.andExpect(status().isOk());
	}

}
