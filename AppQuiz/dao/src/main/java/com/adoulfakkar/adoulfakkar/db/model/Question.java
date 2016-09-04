package com.adoulfakkar.quizzApp.db.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.adoulfakkar.quizzApp.db.dao.PersistenceConstants;

@Entity
@Table(name = "question")
@NamedQueries({
	@NamedQuery(name = PersistenceConstants.QUESTION_LAST_UPDATE, query = "SELECT q FROM Question q WHERE q.updateDate > :updateDate"),
	@NamedQuery(name = PersistenceConstants.QUESTION_FOR_QUIZ, query = "SELECT q FROM Question q WHERE q.id not in (SELECT aq.question.id FROM AnsweredQuestion aq WHERE aq.user.id = :user and aq.question.updateDate < aq.answeredDate) ORDER BY rand()"),
	@NamedQuery(name = PersistenceConstants.RANDOM_QUESTION, query = "SELECT q FROM Question q ORDER BY rand()"),
	@NamedQuery(name = PersistenceConstants.RANDOM_QUESTION_EXCLUDE, query = "SELECT q FROM Question q WHERE q not in :questions ORDER BY rand()"),
	@NamedQuery(name = PersistenceConstants.COUNT_QUESTION, query = "SELECT count(q.id) FROM Question q")
})
public class Question implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1878239503435200839L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "type", nullable = false)
	private Integer type;
	
	@Column(name = "information", nullable = false)
	private String information;

	@Column(name = "is_good", nullable = true)
	private Boolean isGood;

	@Column(name = "update_date", nullable = false)
	private Calendar updateDate;

	@OneToMany(mappedBy = "question", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Collection<Answer> answers;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public Boolean getIsGood() {
		return isGood;
	}

	public void setIsGood(Boolean isGood) {
		this.isGood = isGood;
	}

	public Calendar getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Calendar updateDate) {
		this.updateDate = updateDate;
	}

	public Collection<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Collection<Answer> answers) {
		this.answers = answers;
	}

}
