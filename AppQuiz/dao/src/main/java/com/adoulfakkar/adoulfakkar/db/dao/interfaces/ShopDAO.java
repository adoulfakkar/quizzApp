package com.adoulfakkar.quizzApp.db.dao.interfaces;

import java.util.List;

import com.adoulfakkar.quizzApp.db.model.Shop;

public interface ShopDAO extends GenericDAO<Shop> {

	public Long rank(Shop shop);

	public List<Shop> getBetween(Long rank);

	public Shop getFirst();

	public List<Shop> findByCodes(List<String> codes);

	public Shop findByCode(String string);
	
}
