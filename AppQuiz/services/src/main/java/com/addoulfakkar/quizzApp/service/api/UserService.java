package com.adoulfakkar.quizzApp.service.api;

import java.util.List;

import javax.xml.bind.JAXBException;

import com.adoulfakkar.quizzApp.db.model.Shop;
import com.adoulfakkar.quizzApp.db.model.ShopScore;
import com.adoulfakkar.quizzApp.db.model.User;
import com.adoulfakkar.quizzApp.db.model.UserScore;

public interface UserService extends GenericService<User> {

	public User login(String token);
	
	public String getToken (String login, String password);
	
	public User getByToken(String token) throws JAXBException;
	
	public List<String> role (String token);
	
	public List<UserScore> getScores(User user);

	public List<ShopScore> getScoreShop(User user);
	
	public Long getAllCount();

	public List<User> getPaged(Integer from, Integer size);
	
	public Shop saveShop(Shop shop);

	public boolean isPseudoExists(String pseudo);

}
