package com.adoulfakkar.quizzApp.db.dao.interfaces;

import java.util.Calendar;
import java.util.List;

import com.adoulfakkar.quizzApp.db.model.WebSerie;

public interface WebSerieDAO extends GenericDAO<WebSerie> {

	public List<WebSerie> getFromLastUpdate(Calendar date);
	
	public Long count();

	public List<WebSerie> getPaged(Integer from, Integer size);
}
