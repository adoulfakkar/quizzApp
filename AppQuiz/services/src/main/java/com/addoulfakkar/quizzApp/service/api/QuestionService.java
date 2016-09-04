package com.adoulfakkar.quizzApp.service.api;

import java.util.Calendar;
import java.util.List;

import com.adoulfakkar.quizzApp.db.model.AnsweredQuestion;
import com.adoulfakkar.quizzApp.db.model.Question;
import com.adoulfakkar.quizzApp.db.model.User;

public interface QuestionService extends GenericService<Question> {

	public List<Question> getFromLastUpdate (Calendar date);

	public List<Question> getQuestionForQuiz(User user);

	public void saveAnswer(AnsweredQuestion answeredQuestion);

	public Long getAllCount();

	public List<Question> getPaged(Integer from, Integer size);
}
