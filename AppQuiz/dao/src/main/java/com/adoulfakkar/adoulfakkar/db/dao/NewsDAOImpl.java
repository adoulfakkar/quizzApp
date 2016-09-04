package com.adoulfakkar.quizzApp.db.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.adoulfakkar.quizzApp.db.dao.interfaces.NewsDAO;
import com.adoulfakkar.quizzApp.db.model.News;

@Repository("newsDao")
public class NewsDAOImpl extends AbstractDAO<News> implements NewsDAO {

	@Override
	public List<News> getFromLastUpdate(Calendar date) {
		Query query = em.createNamedQuery(PersistenceConstants.NEWS_LAST_UPDATE);
		query.setParameter("updateDate", date);
		return castResult(query.getResultList());
	}

	@Override
	public Long count() {
		Query query = em.createNamedQuery(PersistenceConstants.COUNT_NEWS);
		return (Long) query.getSingleResult();
	}

	@Override
	public List<News> getPaged(Integer from, Integer size) {
		Query query = em.createQuery("From News");
		query.setFirstResult(from);
		query.setMaxResults(size);
		return castResult(query.getResultList());
	}

}
