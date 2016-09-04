package com.adoulfakkar.quizzApp.db.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.adoulfakkar.quizzApp.db.dao.interfaces.QuestionDAO;
import com.adoulfakkar.quizzApp.db.model.Question;
import com.adoulfakkar.quizzApp.db.model.User;

@Repository("questionDao")
public class QuestionDAOImpl extends AbstractDAO<Question> implements QuestionDAO {

	@Override
	public List<Question> getFromLastUpdate(Calendar date) {
		Query query = em.createNamedQuery(PersistenceConstants.QUESTION_LAST_UPDATE);
		query.setParameter("updateDate", date);
		return castResult(query.getResultList());
	}
	
	public List<Question> getUnansweredQuestion (User user, Integer limit) {
		Query query = em.createNamedQuery(PersistenceConstants.QUESTION_FOR_QUIZ);
		query.setParameter("user", user.getId());
		if (limit != null)
			query.setMaxResults(limit);
		return castResult(query.getResultList());
	}
	
	public List<Question> getRandomedQuestion (Integer limit, List<Question> questions) {
		Query query = null;
		if (questions == null || questions.size() == 0)
			query = em.createNamedQuery(PersistenceConstants.RANDOM_QUESTION);
		else {
			query = em.createNamedQuery(PersistenceConstants.RANDOM_QUESTION_EXCLUDE);
			query.setParameter("questions", questions);
		}
		if (limit != null)
			query.setMaxResults(limit);
		return castResult(query.getResultList());
	}
	
	@Override
	public Long count() {
		Query query = em.createNamedQuery(PersistenceConstants.COUNT_QUESTION);
		return (Long) query.getSingleResult();
	}

	@Override
	public List<Question> getPaged(Integer from, Integer size) {
		Query query = em.createQuery("From Question");
		query.setFirstResult(from);
		query.setMaxResults(size);
		return castResult(query.getResultList());
	}

}
