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
@Table(name="news") 
@NamedQueries({
	@NamedQuery(name=PersistenceConstants.NEWS_LAST_UPDATE, query="SELECT n FROM News n WHERE n.updateDate > :updateDate"),
	@NamedQuery(name = PersistenceConstants.COUNT_NEWS, query = "SELECT count(n.id) FROM News n")
})
public class News implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4244227932713024607L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(name="title", nullable=false)
	private String title;
	
	@Column(name="description", nullable=false)
	private String description;
	
	@Column(name="image", nullable=true)
	private String image;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Calendar getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Calendar updateDate) {
		this.updateDate = updateDate;
	}
	
}
