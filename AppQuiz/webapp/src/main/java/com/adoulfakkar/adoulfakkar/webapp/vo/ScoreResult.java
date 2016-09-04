package com.adoulfakkar.quizzApp.webapp.vo;

import java.util.List;

import com.adoulfakkar.quizzApp.db.model.ShopScore;
import com.adoulfakkar.quizzApp.db.model.UserScore;

public class ScoreResult {

	private List<UserScore> userScores;
	
	private List<ShopScore> shopScores;

	public List<UserScore> getUserScores() {
		return userScores;
	}

	public void setUserScores(List<UserScore> userScores) {
		this.userScores = userScores;
	}

	public List<ShopScore> getShopScores() {
		return shopScores;
	}

	public void setShopScores(List<ShopScore> shopScores) {
		this.shopScores = shopScores;
	}
	
}
