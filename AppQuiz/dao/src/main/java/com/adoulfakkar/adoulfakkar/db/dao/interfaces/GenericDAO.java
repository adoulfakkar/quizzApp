package com.adoulfakkar.quizzApp.db.dao.interfaces;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityTransaction;

public interface GenericDAO<T> {

	public List<T> getAll();

	public T find(Serializable id);

	public void insert(T newObject);

	public T update(T obj);

	public void delete(T obj);
	
	public EntityTransaction beginTransaction();
	
	public void commit();

}