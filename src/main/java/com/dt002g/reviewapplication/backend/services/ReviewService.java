package com.dt002g.reviewapplication.backend.services;

import com.dt002g.reviewapplication.backend.models.Review;
import com.dt002g.reviewapplication.backend.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	public int insertCsvFileDataToDataBase(File csvFile) {
		int numberOfReviewsAdded = 0;
		ArrayList<Review> data = new ArrayList<>();
			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(csvFile));
				String line;
			    while ((line = br.readLine()) != null) {
			    	String[] tempData = line.split(";#");
			    	if(tempData.length == 2) {
				    	int rating = Integer.parseInt(tempData[0]);
				    	data.add(new Review(rating, tempData[1]));
				    	numberOfReviewsAdded++;
			    	}
			    }
			    br.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		reviewRepository.saveAllAndFlush(data);
		csvFile.delete();
		return numberOfReviewsAdded;
	}
}
