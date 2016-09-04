package com.adoulfakkar.quizzApp.db.dao;

import org.springframework.stereotype.Repository;

import com.adoulfakkar.quizzApp.db.dao.interfaces.AnswerDAO;
import com.adoulfakkar.quizzApp.db.model.Answer;

@Repository("answerDao")
public class AnswerDAOImpl extends AbstractDAO<Answer> implements AnswerDAO {

}
