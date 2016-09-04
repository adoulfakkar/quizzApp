package com.adoulfakkar.quizzApp.db.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.adoulfakkar.quizzApp.db.dao.PersistenceConstants;

@Entity
@Table(name = "user")
@NamedQueries({
	@NamedQuery(name = PersistenceConstants.NAMED_QUERY_FIND_BY_LOGIN, query = "SELECT u FROM User u WHERE u.login = :login"),
	@NamedQuery(name = PersistenceConstants.FIND_USERS_BY_SCORE, query = "SELECT u FROM User u ORDER BY u.score DESC"),
	@NamedQuery(name = PersistenceConstants.COUNT_USER, query = "SELECT count(u.id) FROM User u"),
})
@NamedNativeQueries({
	@NamedNativeQuery(name=PersistenceConstants.GET_USER_RANK, query="SELECT rank FROM (SELECT t.id, @rownum \\:= @rownum + 1 AS rank FROM user t, (SELECT @rownum \\:= 0) r ORDER BY t.score DESC) `selection` WHERE id=:userId")
})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7528997013729946874L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "login", nullable = false)
	private String login;

	@Column(name = "pseudo", nullable = true)
	private String pseudo;

	@Column(name = "score", nullable = true)
	private Integer score;

	@Column(name = "last_connection", nullable = true)
	private Calendar lastConnection;

	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.PERSIST)
	@JoinColumn(name = "shopId", nullable = true)
	private Shop shop;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Calendar getLastConnection() {
		return lastConnection;
	}

	public void setLastConnection(Calendar lastConnection) {
		this.lastConnection = lastConnection;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

}
