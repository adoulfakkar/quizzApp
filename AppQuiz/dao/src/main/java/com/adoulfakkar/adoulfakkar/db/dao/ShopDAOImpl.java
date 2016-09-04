package com.adoulfakkar.quizzApp.db.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.adoulfakkar.quizzApp.db.dao.interfaces.ShopDAO;
import com.adoulfakkar.quizzApp.db.model.Shop;

@Repository("shopDao")
public class ShopDAOImpl extends AbstractDAO<Shop> implements ShopDAO {

	@Override
	public Long rank(Shop shop) {
		Query query = em.createNamedQuery(PersistenceConstants.GET_SHOP_RANK);
		query.setParameter("shopId", shop.getId());
		Double res = (Double) query.getSingleResult();
		return res.longValue();
	}
	
	@Override
	public List<Shop> getBetween(Long rank) {
		Query query = em.createNamedQuery(PersistenceConstants.FIND_SHOPS_BY_SCORE);
		query.setMaxResults(3);
		if (rank > 1)
			rank -= 2;
		else
			rank = new Long (0);
		query.setFirstResult(rank.intValue());
		return castResult(query.getResultList());
	}

	@Override
	public Shop getFirst() {
		Query query = em.createNamedQuery(PersistenceConstants.FIND_SHOPS_BY_SCORE);
		query.setMaxResults(1);
		return (Shop) query.getSingleResult();
	}

	@Override
	public List<Shop> findByCodes(List<String> codes) {
		if (codes == null || codes.size() == 0)
			return null;
		Query query = em.createNamedQuery(PersistenceConstants.FIND_SHOPS_BY_CODES);
		query.setParameter("codes", codes);
		return castResult(query.getResultList());
	}

	@Override
	public Shop findByCode(String code) {
		Query query = em.createNamedQuery(PersistenceConstants.FIND_SHOP_BY_CODE);
		query.setParameter("code", code);
		return (Shop) query.getSingleResult();
	}

}
