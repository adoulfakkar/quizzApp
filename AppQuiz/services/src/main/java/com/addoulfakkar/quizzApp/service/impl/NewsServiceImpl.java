package com.adoulfakkar.quizzApp.service.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adoulfakkar.quizzApp.db.dao.interfaces.NewsDAO;
import com.adoulfakkar.quizzApp.db.model.News;
import com.adoulfakkar.quizzApp.service.api.NewsService;

@Service("newsService")
public class NewsServiceImpl extends AbstractService<News> implements NewsService {

	private NewsDAO newsDAO;
	
	@Autowired
	public void setNewsDAO(NewsDAO sDao) {
		setDao(sDao);
		this.newsDAO = sDao;
	}
	
	@Override
	public void insert(News newObject) {
		newObject.setUpdateDate(new GregorianCalendar ());
		super.insert(newObject);
	}
	
	@Override
	public News update(News obj) {
		obj.setUpdateDate(new GregorianCalendar ());
		return super.update(obj);
	}

	@Override
	public List<News> getFromLastUpdate(Calendar date) {
		if (date != null)
			return newsDAO.getFromLastUpdate(date);
		else
			return newsDAO.getAll();
			

	}

	@Override
	public Long getAllCount() {
		return newsDAO.count ();
	}

	@Override
	public List<News> getPaged(Integer from, Integer size) {
		return newsDAO.getPaged (from, size);
	}
}
