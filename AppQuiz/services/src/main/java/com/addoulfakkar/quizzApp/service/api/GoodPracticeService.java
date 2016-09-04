package com.adoulfakkar.quizzApp.service.api;

import java.util.Calendar;
import java.util.List;

import com.adoulfakkar.quizzApp.db.model.GoodPractice;

public interface GoodPracticeService extends GenericService<GoodPractice> {

	public List<GoodPractice> getFromLastUpdate (Calendar date);
	
	public Long getAllCount();

	public List<GoodPractice> getPaged(Integer from, Integer size);
}
