package com.adoulfakkar.quizzApp.db.dao;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.adoulfakkar.quizzApp.db.dao.interfaces.AnsweredDAO;
import com.adoulfakkar.quizzApp.db.model.AnsweredQuestion;

@Repository("answeredDao")
public class AnsweredDAOImpl extends AbstractDAO<AnsweredQuestion> implements AnsweredDAO {

	@Override
	public AnsweredQuestion exist(AnsweredQuestion answeredQuestion) {
		Query query = em.createNamedQuery(PersistenceConstants.ANSWERED_BY_USER_AND_QUESTION);
		query.setParameter("user", answeredQuestion.getUser().getId());
		query.setParameter("question", answeredQuestion.getQuestion().getId());
		try {
			return (AnsweredQuestion) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

}
