package com.minemyrep.io;


import java.util.List;

import org.primefaces.model.chart.MeterGaugeChartModel;

public class Result 
{
	String URL;
	String sentiment;
	String Category;
	String sentiment_weightage;
	String resumeCorrelation;
	double category_weightage;
	double similarity;
	private Integer rating;
	private List<String> keywords;
	
	
	private MeterGaugeChartModel meter;
	
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	
	public MeterGaugeChartModel getMeter() {
		return meter;
	}
	public void setMeter(MeterGaugeChartModel meter) {
		this.meter = meter;
	}
	public String getresumeCorrelation() {
		return resumeCorrelation;
	}
	public void setResumeCorrelation(String urlCorrelation) {
		this.resumeCorrelation = urlCorrelation;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getSentiment() {
		return sentiment;
	}
	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}
	public String getSentiment_weightage() {
		return sentiment_weightage;
	}
	public void setSentiment_weightage(String sentiment_weightage) {
		this.sentiment_weightage = sentiment_weightage;
	}
	public double getCategory_weightage() {
		return category_weightage;
	}
	public void setCategory_weightage(double category_weightage) {
		this.category_weightage = category_weightage;
	}
	public double getSimilarity() {
		return similarity;
	}
	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}
	public List<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}
	public String getResumeCorrelation() {
		return resumeCorrelation;
	}
}
