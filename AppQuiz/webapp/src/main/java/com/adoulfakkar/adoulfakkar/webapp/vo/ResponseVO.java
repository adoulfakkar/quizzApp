package com.adoulfakkar.quizzApp.webapp.vo;

import java.util.List;

import com.adoulfakkar.quizzApp.db.model.GoodPractice;
import com.adoulfakkar.quizzApp.db.model.News;
import com.adoulfakkar.quizzApp.db.model.Question;
import com.adoulfakkar.quizzApp.db.model.User;
import com.adoulfakkar.quizzApp.db.model.WebSerie;

public class ResponseVO {

	private String token;

	private User user;
	
	private List<Question> questions;
	
	private List<News> news;
	
	private List<GoodPractice> goodPractice;
	
	private List<WebSerie> webSeries;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public List<News> getNews() {
		return news;
	}

	public void setNews(List<News> news) {
		this.news = news;
	}

	public List<GoodPractice> getGoodPractice() {
		return goodPractice;
	}

	public void setGoodPractice(List<GoodPractice> goodPractice) {
		this.goodPractice = goodPractice;
	}

	public List<WebSerie> getWebSeries() {
		return webSeries;
	}

	public void setWebSeries(List<WebSerie> webSeries) {
		this.webSeries = webSeries;
	}
	
}
