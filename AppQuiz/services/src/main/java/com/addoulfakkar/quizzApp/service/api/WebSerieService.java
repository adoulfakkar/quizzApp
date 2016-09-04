package com.adoulfakkar.quizzApp.service.api;

import java.util.Calendar;
import java.util.List;

import com.adoulfakkar.quizzApp.db.model.WebSerie;

public interface WebSerieService extends GenericService<WebSerie> {

	public List<WebSerie> getFromLastUpdate (Calendar date);
	
	public Long getAllCount();

	public List<WebSerie> getPaged(Integer from, Integer size);
}
