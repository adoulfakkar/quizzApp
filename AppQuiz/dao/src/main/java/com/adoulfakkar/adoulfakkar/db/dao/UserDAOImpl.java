package com.adoulfakkar.quizzApp.db.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.adoulfakkar.quizzApp.db.dao.interfaces.UserDAO;
import com.adoulfakkar.quizzApp.db.model.User;

@Repository("userDao")
public class UserDAOImpl extends AbstractDAO<User> implements UserDAO {

	@Override
	public User findByLogin(String login) {
		Query q = em.createNamedQuery(PersistenceConstants.NAMED_QUERY_FIND_BY_LOGIN);
		q.setParameter("login", login);
		List<User> users = castResult(q.getResultList());
		return users.size() == 0 ? null : users.get(0);
	}
	
	public Long rank (User user) {
		Query q = em.createNamedQuery(PersistenceConstants.GET_USER_RANK);
		q.setParameter("userId", user);
		Double res = (Double) q.getSingleResult();
		return res.longValue();
	}
	
	public User getFirst () {
		Query q = em.createNamedQuery(PersistenceConstants.FIND_USERS_BY_SCORE);
		q.setMaxResults(1);
		return (User) q.getSingleResult();
	}

	public List<User> getBetween (Long rank) {
		Query q = em.createNamedQuery(PersistenceConstants.FIND_USERS_BY_SCORE);
		q.setMaxResults(3);
		if (rank > 1)
			rank -= 2;
		else
			rank = new Long (0);
		q.setFirstResult(rank.intValue());
		return castResult(q.getResultList());
	}

	@Override
	public Long count() {
		Query query = em.createNamedQuery(PersistenceConstants.COUNT_USER);
		return (Long) query.getSingleResult();
	}

	@Override
	public List<User> getPaged(Integer from, Integer size) {
		Query query = em.createQuery("From User");
		query.setFirstResult(from);
		query.setMaxResults(size);
		return castResult(query.getResultList());
	}

	@Override
	public boolean isPseudoExists(String userPseudo)
	{
	/*Query query = session.createSQLQuery("select u.pseudo from user u where u.pseudo = :pseudo");
	query.setParameter("pseudo", userPseudo);
	
	List result = query.list();
	if(result.size()>0)
	   {return true;}
	else 
		{return false;}*/


		Query query = em.createQuery("from User u where u.pseudo= :pseudo");
		query.setParameter("pseudo", userPseudo);
		
		List result = query.getResultList();
		if(result.size()>0)
	   {return true;}
	else 
		{return false;}
		
		
	}

}
