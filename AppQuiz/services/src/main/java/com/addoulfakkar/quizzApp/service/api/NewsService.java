package com.adoulfakkar.quizzApp.service.api;

import java.util.Calendar;
import java.util.List;

import com.adoulfakkar.quizzApp.db.model.News;

public interface NewsService extends GenericService<News> {

	public List<News> getFromLastUpdate (Calendar date);

	public Long getAllCount();

	public List<News> getPaged(Integer from, Integer size);
}
