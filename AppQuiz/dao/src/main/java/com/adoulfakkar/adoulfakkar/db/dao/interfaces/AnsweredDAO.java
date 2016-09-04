package com.adoulfakkar.quizzApp.db.dao.interfaces;

import com.adoulfakkar.quizzApp.db.model.AnsweredQuestion;


public interface AnsweredDAO extends GenericDAO<AnsweredQuestion> {

	public AnsweredQuestion exist(AnsweredQuestion answeredQuestion);

}
