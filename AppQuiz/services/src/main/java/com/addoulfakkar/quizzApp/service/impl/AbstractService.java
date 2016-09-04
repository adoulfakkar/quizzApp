package com.adoulfakkar.quizzApp.service.impl;

import java.io.Serializable;
import java.util.List;

import com.adoulfakkar.quizzApp.db.dao.interfaces.GenericDAO;
import com.adoulfakkar.quizzApp.service.api.GenericService;

public class AbstractService<T> implements GenericService<T> {

	private GenericDAO<T> dao;

	public GenericDAO<T> getDao() {
		return dao;
	}

	public void setDao(GenericDAO<T> dao) {
		this.dao = dao;
	}

	@Override
	public List<T> getAll() {
		return dao.getAll();
	}

	@Override
	public T find(Serializable id) {
		return dao.find(id);
	}

	@Override
	public void insert(T newObject) {
		dao.insert(newObject);
	}

	@Override
	public T update(T obj) {
		return dao.update(obj);
	}

	@Override
	public void delete(T obj) {
		dao.delete(obj);
	}

}
