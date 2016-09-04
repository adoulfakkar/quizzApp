package com.adoulfakkar.quizzApp.service.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adoulfakkar.quizzApp.db.dao.interfaces.GoodPracticeDAO;
import com.adoulfakkar.quizzApp.db.model.GoodPractice;
import com.adoulfakkar.quizzApp.service.api.GoodPracticeService;

@Service("goodPracticeService")
public class GoodPracticeServiceImpl extends AbstractService<GoodPractice> implements GoodPracticeService {

	private GoodPracticeDAO goodPracticeDAO;
	
	@Autowired
	public void setGoodPracticeDAO(GoodPracticeDAO sDao) {
		this.goodPracticeDAO = sDao;
		setDao(sDao);
	}
	
	@Override
	public void insert(GoodPractice goodPractice) {
		goodPractice.setUpdateDate(new GregorianCalendar ());
		super.insert(goodPractice);
	}

	@Override
	public GoodPractice update(GoodPractice goodPractice) {
		goodPractice.setUpdateDate(new GregorianCalendar ());
		return super.update(goodPractice);
	}

	@Override
	public List<GoodPractice> getFromLastUpdate(Calendar date) {
		if (date != null)
			return goodPracticeDAO.getFromLastUpdate(date);
		else
			return goodPracticeDAO.getAll();
			
	}
	
	@Override
	public Long getAllCount() {
		return goodPracticeDAO.count();
	}

	@Override
	public List<GoodPractice> getPaged(Integer from, Integer size) {
		return goodPracticeDAO.getPaged(from, size);
	}
}
