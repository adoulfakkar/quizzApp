package com.adoulfakkar.quizzApp.service.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adoulfakkar.quizzApp.db.dao.interfaces.WebSerieDAO;
import com.adoulfakkar.quizzApp.db.model.WebSerie;
import com.adoulfakkar.quizzApp.service.api.WebSerieService;

@Service("webSerieService")
public class WebSerieServiceImpl extends AbstractService<WebSerie> implements WebSerieService {

	private WebSerieDAO webSerieDao;
	
	@Autowired
	public void setWebSerieDAO(WebSerieDAO sDao) {
		setDao(sDao);
		this.webSerieDao = sDao;
	}
	
	@Override
	public void insert(WebSerie newObject) {
		newObject.setUpdateDate(new GregorianCalendar ());
		super.insert(newObject);
	}
	
	@Override
	public WebSerie update(WebSerie obj) {
		obj.setUpdateDate(new GregorianCalendar ());
		return super.update(obj);
	}

	@Override
	public List<WebSerie> getFromLastUpdate(Calendar date) {
		/*if (date != null)
			return webSerieDao.getFromLastUpdate(date);
		else
			return webSerieDao.getAll();*/
			return webSerieDao.getAll();
	}
	
	@Override
	public Long getAllCount() {
		return webSerieDao.count();
	}

	@Override
	public List<WebSerie> getPaged(Integer from, Integer size) {
		return webSerieDao.getPaged(from, size);
	}
}
