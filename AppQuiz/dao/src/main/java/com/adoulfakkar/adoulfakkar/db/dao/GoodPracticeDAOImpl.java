/**
 * 
 */
package com.adoulfakkar.quizzApp.db.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.adoulfakkar.quizzApp.db.dao.interfaces.GoodPracticeDAO;
import com.adoulfakkar.quizzApp.db.model.GoodPractice;

/**
 * @author ebataille
 *
 */
@Repository("goodPracticeDao")
public class GoodPracticeDAOImpl extends AbstractDAO<GoodPractice> implements GoodPracticeDAO {

	@Override
	public List<GoodPractice> getFromLastUpdate(Calendar date) {
		Query query = em.createNamedQuery(PersistenceConstants.GOOD_PRACTICE_FROM_LAST_UPDATE);
		query.setParameter("updateDate", date);
		return castResult(query.getResultList());
	}
	
	@Override
	public Long count() {
		Query query = em.createNamedQuery(PersistenceConstants.COUNT_GOOD_PRACTICE);
		return (Long) query.getSingleResult();
	}

	@Override
	public List<GoodPractice> getPaged(Integer from, Integer size) {
		Query query = em.createQuery("From GoodPractice");
		query.setFirstResult(from);
		query.setMaxResults(size);
		return castResult(query.getResultList());
	}

}
