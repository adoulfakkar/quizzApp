package com.adoulfakkar.quizzApp.db.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.adoulfakkar.quizzApp.db.dao.PersistenceConstants;

@Entity
@Table(name = "answered_question")
@NamedQueries({
	@NamedQuery(name=PersistenceConstants.ANSWERED_BY_USER_AND_QUESTION, query="SELECT aq FROM AnsweredQuestion aq WHERE aq.question.id = :question AND aq.user.id = :user")
})
public class AnsweredQuestion {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="question_id", nullable=true)
	private Question question;
	
	@Column(name="answered_date")
	private Calendar answeredDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Calendar getAnsweredDate() {
		return answeredDate;
	}

	public void setAnsweredDate(Calendar answeredDate) {
		this.answeredDate = answeredDate;
	}
	
}
