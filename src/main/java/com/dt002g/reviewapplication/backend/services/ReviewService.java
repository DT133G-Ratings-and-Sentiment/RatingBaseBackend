package com.dt002g.reviewapplication.backend.services;

import com.dt002g.reviewapplication.backend.models.Adjective;
import com.dt002g.reviewapplication.backend.models.AdjectiveByReviewRatingAndScore;
import com.dt002g.reviewapplication.backend.models.Review;
import com.dt002g.reviewapplication.backend.models.ReviewRatingByScore;
import com.dt002g.reviewapplication.backend.models.ReviewRatingByScoreAndAdjective;
import com.dt002g.reviewapplication.backend.models.ReviewsByAdjective;
import com.dt002g.reviewapplication.backend.models.SentenceToAdjective;
import com.dt002g.reviewapplication.backend.models.Sentence;
import com.dt002g.reviewapplication.backend.models.SentenceToAdjectiveId;
import com.dt002g.reviewapplication.backend.repositories.AdjectiveRepository;
import com.dt002g.reviewapplication.backend.repositories.ReviewRepository;
import com.dt002g.reviewapplication.backend.repositories.SentenceToAdjectiveRepository;
import com.dt002g.reviewapplication.backend.util.Pair;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
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
		    	System.out.println("tempdata length: " + tempData.length);
		    	if(tempData.length == 5) {
		    		System.out.println("tempData > 2");
			    	int rating = Integer.parseInt(tempData[0]);
			    	double averageScore = Double.parseDouble(tempData[3]);
			    	double medianScore = Double.parseDouble(tempData[4]);
			    	String comment = tempData[1];
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
				    		int totalScore = -10;
				    		if(sentenceDataParts.length > 2) {
				    			System.out.println("sentenceDataParts[2]: " + sentenceDataParts[2]);
				    			totalScore = Integer.parseInt(sentenceDataParts[2]);
				    		}
				    		double normalisedScore = -10;
				    		if(sentenceDataParts.length > 3) {
				    			System.out.println("sentenceDataParts[3]: " + sentenceDataParts[3]);
				    			normalisedScore = Double.parseDouble(sentenceDataParts[3]);
				    		}
				    		System.out.println("Number of Grades: " + grades.size());
				    		if(grades.size() == 5 && sentenceDataParts.length > 3) {					    			
				    			sentences.add(new Sentence(sentenceDataParts[0], grades, totalScore, normalisedScore));
				    		}
				    		System.out.println("Number of sentence added: " + sentences.size());
				    		if(sentenceDataParts.length > 4) {
				    			System.out.println("Add adjectives");
				    			adjectivesData = sentenceDataParts[4].split(";¤");
				    			for(String a: adjectivesData) {
					    			System.out.println("Add adjectives: "+ a);
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
			    	Review rev = new Review(rating, tempData[1], sentences, averageScore, medianScore);
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
			    			//System.out.println("SentenceToAdjective exist: id: " + senToAd.get(0).getId() + " word: " + senToAd.get(0).getAdjective().getWord() + " Sentence: " + senToAd.get(0).getSentence());
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
			System.out.println("error: " + e.getMessage() + e.getStackTrace());
		} catch(Exception e) {
			System.out.println("error: " + e.getMessage() + e.getStackTrace());
		}
		finally {
			csvFile.delete();	
		}
		//reviewRepository.saveAllAndFlush(data);
		return numberOfReviewsAdded;
	}
	
	public List<ReviewRatingByScore> getNumberOfRewiewsByRatingAndScoreMatrix() {
		ArrayList<ReviewRatingByScore>  ReviewRatingByScoreMatrix = new ArrayList<>();
		
		ArrayList<Pair<Double, Double>> minMaxScores = new ArrayList<>();
		minMaxScores.add(new Pair<Double, Double>(0.0, 20.0));
		minMaxScores.add(new Pair<Double, Double>(20.0, 40.0));
		minMaxScores.add(new Pair<Double, Double>(40.0, 60.0));
		minMaxScores.add(new Pair<Double, Double>(60.0, 80.0));
		minMaxScores.add(new Pair<Double, Double>(80.0, 100.0));
		
		for(int rating = 0; rating <= 100; rating++) {
			for(Pair<Double, Double> minMaxScore: minMaxScores) {
				Long amount = reviewRepository.getNumberOfRewiewsWithRatingXAndScoreBiggerThanYAndLesserThanZ(rating, minMaxScore.first, minMaxScore.second);
				ReviewRatingByScoreMatrix.add(new ReviewRatingByScore(rating, minMaxScore.first, minMaxScore.second, amount));
			}
		}
		
		/*long total = 0;
		for(ReviewRatingByScore r: ReviewRatingByScoreMatrix) {
			long amount = 0;
			for(int rating = 0; rating <= 100; rating++) {
				if(r.getRating() == rating) {
					amount += r.getAmount();
					total += amount;
				}
			}
			System.out.println("Rating: " + r.getRating() + " minScore: " + r.getMinScore() + " maxScore: " + r.getMaxScore() + " Amount: " + amount);
		}
		System.out.println("Total: " + total);*/
		return ReviewRatingByScoreMatrix;
	}
	


	public List<ReviewRatingByScore> getNumberOfReviewsByRatingAndScoreMatrixMedian() {
		ArrayList<ReviewRatingByScore>  ReviewRatingByScoreMatrix = new ArrayList<>();

		ArrayList<Pair<Double, Double>> minMaxScores = new ArrayList<>();
		minMaxScores.add(new Pair<Double, Double>(0.0, 20.0));
		minMaxScores.add(new Pair<Double, Double>(20.0, 40.0));
		minMaxScores.add(new Pair<Double, Double>(40.0, 60.0));
		minMaxScores.add(new Pair<Double, Double>(60.0, 80.0));
		minMaxScores.add(new Pair<Double, Double>(80.0, 100.0));

		for(int rating = 0; rating <= 100; rating++) {
			for(Pair<Double, Double> minMaxScore: minMaxScores) {
				Long amount = reviewRepository.getCountOfScoreAndRatingMedian(rating, minMaxScore.first, minMaxScore.second);
				ReviewRatingByScoreMatrix.add(new ReviewRatingByScore(rating, minMaxScore.first, minMaxScore.second, amount));
			}
		}

		/*long total = 0;
		for(ReviewRatingByScore r: ReviewRatingByScoreMatrix) {
			long amount = 0;
			for(int rating = 0; rating <= 100; rating++) {
				if(r.getRating() == rating) {
					amount += r.getAmount();
					total += amount;
				}
			}
			System.out.println("Rating: " + r.getRating() + " minScore: " + r.getMinScore() + " maxScore: " + r.getMaxScore() + " Amount: " + amount);
		}
		System.out.println("Total: " + total);*/
		return ReviewRatingByScoreMatrix;
	}
	
	public List<AdjectiveByReviewRatingAndScore> getNumberOfAdjectivesByNameInReviewRatingAndAverageScoreRangeMatrix() {
		ArrayList<AdjectiveByReviewRatingAndScore> adjectiveMatrix = new ArrayList<>();
		
		ArrayList<Pair<Double, Double>> minMaxScores = new ArrayList<>();
		minMaxScores.add(new Pair<Double, Double>(0.0, 20.0));
		minMaxScores.add(new Pair<Double, Double>(20.0, 40.0));
		minMaxScores.add(new Pair<Double, Double>(40.0, 60.0));
		minMaxScores.add(new Pair<Double, Double>(60.0, 80.0));
		minMaxScores.add(new Pair<Double, Double>(80.0, 100.0));
		
		List<Adjective> adjectives = adjectiveRepository.findAll();
		
		for(Adjective adjective: adjectives) {
			for(int rating = 0; rating < 100; rating+=20) {
				for(Pair<Double, Double> minMaxScore: minMaxScores) {
					Long amount = adjectiveRepository.getNumberOfAdjectivesByNameInReviewRatingAndScoreRange(rating, rating+20, minMaxScore.first, minMaxScore.second, adjective.getWord());
					adjectiveMatrix.add(new AdjectiveByReviewRatingAndScore(adjective.getWord(), amount, rating, rating+20, minMaxScore.first, minMaxScore.second));
				}
			}
		}

		return adjectiveMatrix;
	}
	
	public List<AdjectiveByReviewRatingAndScore> getNumberOfAdjectivesByNameInReviewRatingAndMedianScoreRangeMatrix() {
		ArrayList<AdjectiveByReviewRatingAndScore> adjectiveMatrix = new ArrayList<>();
		
		ArrayList<Pair<Double, Double>> minMaxScores = new ArrayList<>();
		minMaxScores.add(new Pair<Double, Double>(0.0, 20.0));
		minMaxScores.add(new Pair<Double, Double>(20.0, 40.0));
		minMaxScores.add(new Pair<Double, Double>(40.0, 60.0));
		minMaxScores.add(new Pair<Double, Double>(60.0, 80.0));
		minMaxScores.add(new Pair<Double, Double>(80.0, 100.0));
		
		List<Adjective> adjectives = adjectiveRepository.findAll();
		
		for(Adjective adjective: adjectives) {
			for(int rating = 0; rating < 100; rating+=20) {
				for(Pair<Double, Double> minMaxScore: minMaxScores) {
					Long amount = adjectiveRepository.getNumberOfAdjectivesByNameInReviewRatingAndMedianScoreRange(rating, rating+20, minMaxScore.first, minMaxScore.second, adjective.getWord());
					adjectiveMatrix.add(new AdjectiveByReviewRatingAndScore(adjective.getWord(), amount, rating, rating+20, minMaxScore.first, minMaxScore.second));
				}
			}
		}

		return adjectiveMatrix;
	}
	
	public List<ReviewRatingByScore> getNumberOfRewiewsByRatingAndScoreAvgTotalMatrix() {
		ArrayList<ReviewRatingByScore>  ReviewRatingByScoreMatrix = new ArrayList<>();
		
		for(int rating = 0; rating <= 100; rating++) {
			for(int score = 0; score <= 100; score++) {
				Long amount = reviewRepository.getNumberOfRewiewsWithRatingXAndAvgScoreY(rating, score);
				ReviewRatingByScoreMatrix.add(new ReviewRatingByScore(rating, score, score, amount));
			}
		}
		return ReviewRatingByScoreMatrix;
	}
	
	public List<ReviewRatingByScore> getNumberOfRewiewsByRatingAndScoreMedianTotalMatrix() {
		ArrayList<ReviewRatingByScore>  ReviewRatingByScoreMatrix = new ArrayList<>();
		
		for(int rating = 0; rating <= 100; rating++) {
			for(int score = 0; score <= 100; score++) {
				Long amount = reviewRepository.getNumberOfRewiewsWithRatingXAndMedianScoreY(rating, score);
				ReviewRatingByScoreMatrix.add(new ReviewRatingByScore(rating, score, score, amount));
			}
		}
		return ReviewRatingByScoreMatrix;
	}
	
    public List<Pair<String,Long>> getNumberOfTimesAdjectiveOccureWhenRatingAndScoreIsTheSame(){
    	List<Object[]> adjectiveAmouts = adjectiveRepository.getNumberOfTimesAdjectiveOccureWhenRatingAndScoreIsTheSame();
    	ArrayList<Pair<String, Long>> result = new ArrayList<>();    	
    	for(Object[] obj: adjectiveAmouts) {
    		result.add(new Pair<String,Long>((String)obj[0], ((BigDecimal)obj[1]).longValue()));
    	}
    	return result;
    }
    
	public List<Review> getAllReviewsWithAdjective(String adjective){
		return reviewRepository.getAllReviewsWithAdjective(adjective);
	}
	
	public List<ReviewsByAdjective> getAllReviewsWithAdjectiveMatrix(){
		List<Adjective> adjectives = adjectiveRepository.getAllAdjectives();
		List<ReviewsByAdjective> reviewsByAdjectives = new ArrayList<ReviewsByAdjective>();
		for(Adjective adjective: adjectives) {
			List<Review> reviews = reviewRepository.getAllReviewsWithAdjective(adjective.getWord());
			reviewsByAdjectives.add(new ReviewsByAdjective(adjective.getWord(), reviews));
		}
		return reviewsByAdjectives;
	}
	
	/*public List<ReviewRatingByScoreAndAdjective> getAllReviewRatingByScoreAndAdjectiveMatrix(){
		List<Adjective> adjectives = adjectiveRepository.getAllAdjectives();
		List<ReviewRatingByScoreAndAdjective> reviewRatingByScoreAndAdjectives = new ArrayList<ReviewRatingByScoreAndAdjective>();
		for(Adjective adjective: adjectives) {
			List<Review> reviews = reviewRepository.getAllReviewsWithAdjective(adjective.getWord());
			ReviewRatingByScoreAndAdjective reviewRatingByScoreAndAdjective = new ReviewRatingByScoreAndAdjective();
			reviewRatingByScoreAndAdjective.setAdjective(adjective.getWord());
			for(Review r: reviews) {
				reviewRatingByScoreAndAdjective.getReviewRatingByScores().add(new ReviewRatingByScore(r.getRating(), r.getNormalisedAverageSentenceScore(), r.getNormalisedAverageSentenceScore()));
			}
			reviewsByAdjectives.add(new ReviewsByAdjective(adjective.getWord(), reviews));
		}
		return reviewsByAdjectives;
	}*/

}
