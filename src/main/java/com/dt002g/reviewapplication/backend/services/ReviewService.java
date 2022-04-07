package com.dt002g.reviewapplication.backend.services;

import com.dt002g.reviewapplication.backend.models.Adjective;
import com.dt002g.reviewapplication.backend.models.Review;
import com.dt002g.reviewapplication.backend.models.SentenceToAdjective;
import com.dt002g.reviewapplication.backend.models.Sentence;
import com.dt002g.reviewapplication.backend.models.SentenceToAdjectiveId;
import com.dt002g.reviewapplication.backend.repositories.AdjectiveRepository;
import com.dt002g.reviewapplication.backend.repositories.ReviewRepository;
import com.dt002g.reviewapplication.backend.repositories.SentenceToAdjectiveRepository;
import com.dt002g.reviewapplication.backend.util.Pair;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private AdjectiveRepository adjectiveRepository;
	
	@Autowired
	private SentenceToAdjectiveRepository sentenceToAdjectiveRepository;
	
	public int insertCsvFileDataToDataBase(File csvFile) {
		int numberOfReviewsAdded = 0;
		ArrayList<Review> data = new ArrayList<>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(csvFile));
			String line;
		    while ((line = br.readLine()) != null) {
				ArrayList<Pair<Adjective, Sentence>> adjectives = new ArrayList<>();
				ArrayList<SentenceToAdjective> sTA = new ArrayList<>();
				int score = -10;
		    	String[] tempData = line.split(";#");
		    	if(tempData.length == 4) {
		    		System.out.println("tempData > 2");
			    	int rating = Integer.parseInt(tempData[0]);
			    	String comment = tempData[1];
			    	score = Integer.parseInt(tempData[3]);
			    	String[] sentenceData = tempData[2].split(";@");
			    	ArrayList<Sentence> sentences = new ArrayList<>();
			    	for(String s: sentenceData) {
			    		System.out.println("Sentence: " + s);
			    		String[] sentenceDataParts= s.split(";£");
			    		if(sentenceDataParts.length > 1) {
				    		String[] gradesData = sentenceDataParts[1].split(";¤");
				    		ArrayList<Double> grades = new ArrayList<>();
				    		String[] adjectivesData = null;
				    		if(gradesData.length > 1) {
				    			for(int i =0; i < gradesData.length; i++) {
					    			System.out.println("Grade: " + gradesData[i]);
				    				grades.add(Double.parseDouble(gradesData[i]));
				    			}
				    		}
				    		System.out.println("Number of Grades: " + grades.size());
				    		if(grades.size() == 5) {					    			
				    			sentences.add(new Sentence(sentenceDataParts[0], grades));
				    		}
				    		System.out.println("Number of sentence added: " + sentences.size());
				    		if(sentenceDataParts.length > 2) {
				    			adjectivesData = sentenceDataParts[2].split(";¤");
				    			for(String a: adjectivesData) {
				    				List<Adjective> temp = adjectiveRepository.getByAdjective(a);
				    				Pair<Adjective, Sentence> adSen = new Pair<Adjective, Sentence>();
				    				if(temp == null || temp.size()< 1) {
				    					adSen.first = new Adjective(a);
				    					adSen.second = sentences.get(sentences.size()-1);
				    					adjectives.add(adSen);
				    					adjectiveRepository.save(adjectives.get(adjectives.size()-1).first);
				    					temp = adjectiveRepository.getByAdjective(a);
				    				}
				    				else {
				    					adSen.first = temp.get(0);
				    					adSen.second = sentences.get(sentences.size()-1);
				    					adjectives.add(adSen);
				    					
				    				}
				    				
				    			}
				    		}
			    		}
			    	}
			    	Review rev = new Review(rating, tempData[1], sentences, score);
			    	rev = reviewRepository.saveAndFlush(rev);
			    	adjectiveRepository.flush();
			    	ArrayList<SentenceToAdjective> sToA = new ArrayList<>();
			    	for(Pair<Adjective, Sentence> adSen: adjectives) {
			    		//SentenceToAdjective senToAd = sentenceToAdjectiveRepository.findById(new SentenceToAdjective(adSen.first, adSen.second) );
			    		
			    		// List<SentenceToAdjective> senToAd = sentenceToAdjectiveRepository.getByAdjectiveIdAndSentenceId(adSen.first.getId(), adSen.second.getId());
			        	String query = "SELECT * FROM sentence_2_adjective WHERE adjective_id =" + adSen.first.getId() + " and sentence_id =" + adSen.second.getId();
			        	List<SentenceToAdjective> senToAd = sentenceToAdjectiveRepository.customQuery(query);
			        	
			    		if(senToAd == null || senToAd.size() == 0) {
			    			sToA.add(new SentenceToAdjective(adSen.second, adSen.first, 1));
			    		}
			    		else {
			    			System.out.println("SentenceToAdjective exist: id: " + senToAd.get(0).getId() + " word: " + senToAd.get(0).getAdjective().getWord() + " Sentence: " + senToAd.get(0).getSentence());
			    			senToAd.get(0).setNumberOfOccurence(senToAd.get(0).getNumberOfOccurence() +1);
			    			sToA.add(senToAd.get(0));
			    		}
			    		sentenceToAdjectiveRepository.saveAndFlush(sToA.get(sToA.size() -1));
			    	}
			    	//sentenceToAdjectiveRepository.saveAllAndFlush(sToA);
		    		System.out.println("Number of sentence added: " + sentences.size());
			    	data.add(rev);
		    		//data.add(new Review(rating, tempData[1], sentences));
			    	numberOfReviewsAdded++;
		    	}
		    }
		    br.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		//reviewRepository.saveAllAndFlush(data);
		csvFile.delete();
		return numberOfReviewsAdded;
	}
}
