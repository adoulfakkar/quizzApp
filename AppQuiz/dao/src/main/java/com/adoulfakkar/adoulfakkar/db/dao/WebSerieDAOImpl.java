package com.adoulfakkar.quizzApp.db.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.adoulfakkar.quizzApp.db.dao.interfaces.WebSerieDAO;
import com.adoulfakkar.quizzApp.db.model.WebSerie;

@Repository("webSerieDao")
public class WebSerieDAOImpl extends AbstractDAO<WebSerie> implements WebSerieDAO {

	@Override
	public List<WebSerie> getFromLastUpdate(Calendar date) {
		Query query = em.createNamedQuery(PersistenceConstants.WEB_SERIE_LAST_UPDATE);
		query.setParameter("updateDate", date);
		return castResult(query.getResultList());
	}

	@Override
	public Long count() {
		Query query = em.createNamedQuery(PersistenceConstants.COUNT_USER);
		return (Long) query.getSingleResult();
	}

	@Override
	public List<WebSerie> getPaged(Integer from, Integer size) {
		Query query = em.createQuery("From WebSerie");
		query.setFirstResult(from);
		query.setMaxResults(size);
		return castResult(query.getResultList());
	}
}
