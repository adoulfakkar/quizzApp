package com.adoulfakkar.quizzApp.db.model;

import java.io.Serializable;

public class UserScore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8667439208083590183L;

	private User user;
	
	private Long rank;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getRank() {
		return rank;
	}

	public void setRank(Long rank) {
		this.rank = rank;
	}
}
