package com.adoulfakkar.quizzApp.service.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adoulfakkar.quizzApp.db.dao.interfaces.AnswerDAO;
import com.adoulfakkar.quizzApp.db.dao.interfaces.AnsweredDAO;
import com.adoulfakkar.quizzApp.db.dao.interfaces.QuestionDAO;
import com.adoulfakkar.quizzApp.db.model.Answer;
import com.adoulfakkar.quizzApp.db.model.AnsweredQuestion;
import com.adoulfakkar.quizzApp.db.model.Question;
import com.adoulfakkar.quizzApp.db.model.User;
import com.adoulfakkar.quizzApp.service.api.QuestionService;

@Service("questionService")
public class QuestionServiceImpl extends AbstractService<Question> implements QuestionService {

	private QuestionDAO questionDAO;
	
	@Autowired
	private AnsweredDAO answeredDAO;

	@Autowired
	private AnswerDAO answerDAO;
	
	@Autowired
	public void setQuestionDAO(QuestionDAO sDao) {
		this.questionDAO = sDao;
		setDao(sDao);
	}
	
	@Override
	public void insert(Question newObject) {
		newObject.setUpdateDate(new GregorianCalendar());
		super.insert(newObject);
	}

	@Override
	public Question update(Question question) {
		question.setUpdateDate(new GregorianCalendar());
		return super.update(question);
	}

	
	@Override
	public void delete(Question question) {
		for (Answer answer : question.getAnswers()) {
			answerDAO.delete(answer);
		}
		super.delete(question);
	}

	@Override
	public List<Question> getFromLastUpdate(Calendar date) {
		if (date != null)
			return questionDAO.getFromLastUpdate(date);
		else
			return questionDAO.getAll();
	}
	
	@Override
	public List<Question> getQuestionForQuiz(User user) {
		List<Question> res = questionDAO.getUnansweredQuestion(user, 10);
		int nbQuestion = res.size();
		if (nbQuestion < 10)
			res.addAll(questionDAO.getRandomedQuestion(10 - nbQuestion, res));
		return res;
	}

	@Override
	public void saveAnswer(AnsweredQuestion answeredQuestion) {
		AnsweredQuestion exist = answeredDAO.exist (answeredQuestion);
		if (exist == null) {
			answeredQuestion.setAnsweredDate(new GregorianCalendar ());
			answeredDAO.insert(answeredQuestion);
		}
		else {
			exist.setAnsweredDate(new GregorianCalendar ());
			answeredDAO.update(exist);
		}
	}

	@Override
	public Long getAllCount() {
		return questionDAO.count ();
	}

	@Override
	public List<Question> getPaged(Integer from, Integer size) {
		return questionDAO.getPaged (from, size);
	}
}
