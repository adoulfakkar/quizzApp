package com.adoulfakkar.quizzApp.db.model;

import java.io.Serializable;

public class ShopScore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6874465571705128566L;

	private Shop shop;
	
	private Long rank;

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Long getRank() {
		return rank;
	}

	public void setRank(Long rank) {
		this.rank = rank;
	}
}
