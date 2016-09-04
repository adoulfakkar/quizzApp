package com.adoulfakkar.quizzApp.db.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.adoulfakkar.quizzApp.db.dao.PersistenceConstants;

@Entity
@Table(name="good_practice") 
@NamedQueries({
	@NamedQuery(name=PersistenceConstants.GOOD_PRACTICE_FROM_LAST_UPDATE, query="SELECT gp FROM GoodPractice gp WHERE gp.updateDate > :updateDate"),
	@NamedQuery(name = PersistenceConstants.COUNT_GOOD_PRACTICE, query = "SELECT count(gp.id) FROM GoodPractice gp")
})
public class GoodPractice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6288477666245494314L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="title", nullable=false)
	private String title;
	
	@Column(name="type", nullable=false)
	private String type;
	
	@Column(name="file_url", nullable=false)
	private String fileUrl;
	
	@Column(name="update_date", nullable=false)
	private Calendar updateDate;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public Calendar getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Calendar updateDate) {
		this.updateDate = updateDate;
	}
	
}
