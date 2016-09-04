package com.adoulfakkar.quizzApp.db.dao.interfaces;

import java.util.Calendar;
import java.util.List;

import com.adoulfakkar.quizzApp.db.model.News;

public interface NewsDAO extends GenericDAO<News> {

	public List<News> getFromLastUpdate(Calendar date);

	public Long count();

	public List<News> getPaged(Integer from, Integer size);
}
