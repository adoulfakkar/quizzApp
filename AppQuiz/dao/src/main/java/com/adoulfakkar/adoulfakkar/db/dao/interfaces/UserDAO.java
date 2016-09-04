package com.adoulfakkar.quizzApp.db.dao.interfaces;

import java.util.List;

import com.adoulfakkar.quizzApp.db.model.User;


public interface UserDAO extends GenericDAO<User> {

	public User findByLogin(String login);
	
	public Long rank (User user);
	
	public User getFirst ();
	
	public List<User> getBetween (Long rank);
	
	public Long count();

	public List<User> getPaged(Integer from, Integer size);


	public boolean isPseudoExists(String login);
}
