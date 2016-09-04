package com.adoulfakkar.quizzApp.db.dao.interfaces;

import java.util.Calendar;
import java.util.List;

import com.adoulfakkar.quizzApp.db.model.GoodPractice;

public interface GoodPracticeDAO extends GenericDAO<GoodPractice> {

	public List<GoodPractice> getFromLastUpdate(Calendar date);
	
	public Long count();

	public List<GoodPractice> getPaged(Integer from, Integer size);

}
