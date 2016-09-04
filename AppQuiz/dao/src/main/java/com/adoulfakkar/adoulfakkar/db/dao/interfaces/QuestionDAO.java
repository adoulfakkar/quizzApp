package com.adoulfakkar.quizzApp.db.dao.interfaces;

import java.util.Calendar;
import java.util.List;

import com.adoulfakkar.quizzApp.db.model.Question;
import com.adoulfakkar.quizzApp.db.model.User;

public interface QuestionDAO extends GenericDAO<Question> {

	public List<Question> getFromLastUpdate(Calendar date);
	
	public List<Question> getRandomedQuestion (Integer limit, List<Question> questions);
	
	public List<Question> getUnansweredQuestion (User user, Integer limit);

	public Long count();

	public List<Question> getPaged(Integer from, Integer size);
	
}
