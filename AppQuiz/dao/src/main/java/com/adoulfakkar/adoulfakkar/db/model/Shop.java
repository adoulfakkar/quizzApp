package com.adoulfakkar.quizzApp.db.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.adoulfakkar.quizzApp.db.dao.PersistenceConstants;

@Entity
@Table(name="shop") 
@NamedQueries({
	@NamedQuery(name=PersistenceConstants.FIND_SHOPS_BY_SCORE, query="SELECT s FROM Shop s ORDER BY s.score DESC"),
	@NamedQuery(name=PersistenceConstants.FIND_SHOPS_BY_CODES, query="SELECT s FROM Shop s WHERE s.code in :codes"),
	@NamedQuery(name=PersistenceConstants.FIND_SHOP_BY_CODE, query="SELECT s FROM Shop s WHERE s.code = :code")
})
@NamedNativeQueries({
	@NamedNativeQuery(name=PersistenceConstants.GET_SHOP_RANK, query="SELECT rank FROM (SELECT t.id, @rownum \\:= @rownum + 1 AS rank FROM shop t, (SELECT @rownum \\:= 0) r ORDER BY t.score DESC) `selection` WHERE id=:shopId")
})
public class Shop implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1620465078957290970L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column (name="login", nullable=false)
	private String code;
	
	@Column (name="name", nullable=false)
	private String name;
	
	@Column (name="score", nullable=true)
	private Integer score;
	
	@OneToMany (mappedBy="shop", fetch=FetchType.LAZY)
	private Collection<User> users;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
}
