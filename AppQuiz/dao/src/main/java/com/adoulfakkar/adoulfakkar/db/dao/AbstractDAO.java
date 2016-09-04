package com.adoulfakkar.quizzApp.db.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.adoulfakkar.quizzApp.db.dao.interfaces.GenericDAO;

public abstract class AbstractDAO<T> implements GenericDAO<T> {

	@PersistenceContext(unitName="DBDataSource")
	protected EntityManager em;
	private Class<T> classType;
	
	@SuppressWarnings("unchecked")
	protected List<T> castResult (List<?> result) {
		return (List<T>) result;
	}

	@SuppressWarnings("unchecked")
	public AbstractDAO() {
		super();
		classType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Override
	public List<T> getAll() {
		return castResult (em.createQuery("select t from "+classType.getSimpleName()+" t").getResultList());
	}
	
	@Override
	public T find (Serializable id){
		return em.find(classType, id);
	}
	
	/**
	 * Utility method for queries returning one or no results.
	 * 
	 * @param query
	 * @return unique result or null
	 * @throws RadarTechnicalException
	 *             exception
	 */
	protected T findUniqueOrNull(Query query) {
		List<T> result = castResult (query.getResultList());

		if (result.size() != 0) {
			return result.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void insert(T newObject){
		em.persist(newObject);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public T update (T obj){
		
		T result = em.merge(obj);
		return result;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void delete (T obj){
		em.remove(obj);
	}
	
	public EntityTransaction beginTransaction(){
		EntityTransaction result = em.getTransaction();
		result.begin();
		return result;
	}
	
	public void commit(){
		em.getTransaction().commit();
	}
}
