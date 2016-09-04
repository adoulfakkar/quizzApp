package com.adoulfakkar.quizzApp.service.api;

import java.io.Serializable;
import java.util.List;

public interface GenericService<T> {

	public List<T> getAll();

	public T find(Serializable id);

	public void insert(T newObject);

	public T update(T obj);

	public void delete(T obj);
}
