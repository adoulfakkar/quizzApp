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
@Table(name="web_serie") 
@NamedQueries({
	@NamedQuery(name=PersistenceConstants.WEB_SERIE_LAST_UPDATE, query="SELECT ws FROM WebSerie ws WHERE ws.updateDate > :updateDate"),
	@NamedQuery(name = PersistenceConstants.COUNT_WEB_SERIE, query = "SELECT count(ws.id) FROM WebSerie ws")
})
public class WebSerie implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3162057745387400791L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="title", nullable=false)
	private String title;
	
	@Column(name="description", nullable=true)
	private String description;
	
	@Column(name="preview_url", nullable=false)
	private String previewUrl;
	
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getPreviewUrl() {
		return previewUrl;
	}

	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
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
