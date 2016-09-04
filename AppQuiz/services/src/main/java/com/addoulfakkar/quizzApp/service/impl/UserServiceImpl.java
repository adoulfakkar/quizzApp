package com.adoulfakkar.quizzApp.service.impl;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.adoulfakkar.quizzApp.db.dao.interfaces.ShopDAO;
import com.adoulfakkar.quizzApp.db.dao.interfaces.UserDAO;
import com.adoulfakkar.quizzApp.db.model.ShopScore;
import com.adoulfakkar.quizzApp.db.model.UserScore;
import com.adoulfakkar.quizzApp.db.model.Shop;
import com.adoulfakkar.quizzApp.db.model.User;
import com.adoulfakkar.quizzApp.service.api.UserService;
import com.adoulfakkar.quizzApp.service.wsdl.auth.GetAttribut;
import com.adoulfakkar.quizzApp.service.wsdl.auth.GetAttributResponse;
import com.adoulfakkar.quizzApp.service.wsdl.auth.GetPrivileges;
import com.adoulfakkar.quizzApp.service.wsdl.auth.GetPrivilegesResponse;
import com.adoulfakkar.quizzApp.service.wsdl.auth.GetProfil;
import com.adoulfakkar.quizzApp.service.wsdl.auth.GetProfilResponse;
import com.adoulfakkar.quizzApp.service.wsdl.auth.Login;
import com.adoulfakkar.quizzApp.service.wsdl.auth.LoginResponse;
import com.adoulfakkar.quizzApp.service.wsdl.auth.ObjectFactory;
import com.adoulfakkar.quizzApp.service.wsdl.user.Person;

@Service("userService")
public class UserServiceImpl extends AbstractService<User> implements UserService {

	private static final String LDAP_APP_NAME = "DEMARQUEZ_VOUS";

	protected UserDAO userDao;
	
	@Autowired
	private ShopDAO shopDao;
	
	@Autowired
	public void setUserDAO(UserDAO userDao) {
		setDao(userDao);
		this.userDao = userDao;
	}
	
	@Autowired
    private WebServiceTemplate webServiceTemplate;
    
	public User login (String token) {
    	try {
	    	Person person = getProfil(token);
	    	String userLogin = person.getLogin();
	    	
	    	User user = userDao.findByLogin(userLogin);
	    	if (user == null) {
	    		user = subscribe (person, token);
	    	}
	    	return user;
    	}
	    catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return null;
    }
	
	@SuppressWarnings("unchecked")
	public List<String> role (String token) {
    	GetPrivileges getprivilege = new ObjectFactory().createGetPrivileges();
    	getprivilege.setId(token);
    	getprivilege.setApplicationName(LDAP_APP_NAME);
    	JAXBElement<GetPrivileges> jaxbGetPrivilege = new ObjectFactory().createGetPrivileges(getprivilege);
    	JAXBElement<GetPrivilegesResponse> result = (JAXBElement<GetPrivilegesResponse>) webServiceTemplate.marshalSendAndReceive(jaxbGetPrivilege);
    	return result.getValue().getReturn();
    }
    
    @SuppressWarnings("unchecked")
	public String getToken (String login, String password) {
    	Login loginObj = new ObjectFactory().createLogin();
    	loginObj.setUser(login);
    	loginObj.setPassword(password);
    	JAXBElement<Login> jaxbLogin = new ObjectFactory().createLogin(loginObj);
    	JAXBElement<LoginResponse> result = (JAXBElement<LoginResponse>) webServiceTemplate.marshalSendAndReceive(jaxbLogin);
    	
    	String token = result.getValue().getReturn();
    	return token;
    }
    
    @SuppressWarnings("unchecked")
	private Person getProfil (String token) throws JAXBException {
    	ObjectFactory factory = new ObjectFactory();
    	GetProfil profil = factory.createGetProfil();
    	profil.setId(token);
    	JAXBElement<GetProfil> jaxbProfil = factory.createGetProfil(profil);
    	JAXBElement<GetProfilResponse> personResult = (JAXBElement<GetProfilResponse>) webServiceTemplate.marshalSendAndReceive(jaxbProfil);
    	
    	JAXBContext jaxbContext = JAXBContext.newInstance(Person.class);
    	Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    	
    	String userXML = personResult.getValue().getReturn();
    	StringReader reader = new StringReader(userXML);
    	Person person = (Person) unmarshaller.unmarshal(reader);
    	
    	return person;
    }

	@SuppressWarnings("unchecked")
	private User subscribe(Person person, String token) {
		ObjectFactory factory = new ObjectFactory();
		GetAttribut getAttribut = factory.createGetAttribut();
    	getAttribut.setAttributName("eduPersonOrgUnitDN");
    	getAttribut.setId(token);
    	
    	JAXBElement<GetAttribut> jaxbAttribut = factory.createGetAttribut(getAttribut);
    	JAXBElement<GetAttributResponse> attrResult = (JAXBElement<GetAttributResponse>) webServiceTemplate.marshalSendAndReceive(jaxbAttribut);
    	
    	List<String> values = attrResult.getValue().getReturn();
    	List<String> frValues = new ArrayList<String> ();
    	for (String value : values) {
    		String[] subValues = value.split (",");
    		String firstValue = subValues[0];
    		String[] splitted = firstValue.split("=");
    		if (splitted.length > 1) {
    			String frValue = splitted[1];
    			if (frValue.indexOf("FR") == 0) {
    				frValues.add(frValue);
    			}
    		}
    	}
    	
    	List<Shop> shops = shopDao.findByCodes (frValues);
		
		User user = new User ();
		user.setLastConnection(null);
		user.setLogin(person.getLogin());
		user.setPseudo(null);
		user.setScore(0);
		if (shops != null && shops.size() > 0)
			user.setShop(shops.get(0));
		else {
			user.setShop(shopDao.findByCode("000000"));
		}
		
		userDao.insert(user);
		
		return user;
	}

	@Override
	public List<UserScore> getScores (User user) {
		Long rank = userDao.rank(user);
		User first = userDao.getFirst();
		List<User> users = userDao.getBetween(rank);
		List<UserScore> scoreRanks = new ArrayList<UserScore> ();
		UserScore scoreRank = new UserScore();
		//first.setShop(null);
		scoreRank.setUser(first);
		scoreRank.setRank(new Long (1));
		scoreRanks.add(scoreRank);
		
		Long currentRank = rank == 1 ? 1 : rank - 1;
		for (User u : users) {
			if (!u.getId().equals(first.getId())) {
				scoreRank = new UserScore();
				scoreRank.setUser(u);
				scoreRank.setRank(new Long (currentRank));
				scoreRanks.add(scoreRank);
				//u.setShop(null);
			}
			++currentRank;
		}
		return scoreRanks;
	}

	@Override
	public List<ShopScore> getScoreShop (User user) {
		Long rank = shopDao.rank(user.getShop());
		Shop first = shopDao.getFirst();
		List<Shop> shops = shopDao.getBetween(rank);
		List<ShopScore> scoreRanks = new ArrayList<ShopScore> ();
		ShopScore scoreRank = new ShopScore();
		scoreRank.setShop(first);
		scoreRank.setRank(new Long (1));
		scoreRanks.add(scoreRank);
		
		Long currentRank = rank == 1 ? 1 : rank - 1;
		for (Shop s : shops) {
			if (!s.getId().equals(first.getId())) {
				scoreRank = new ShopScore();
				scoreRank.setShop(s);
				scoreRank.setRank(new Long (currentRank));
				scoreRanks.add(scoreRank);
			}
			++currentRank;
		}
		return scoreRanks;
	}

	@Override
	public Long getAllCount() {
		return userDao.count();
	}

	@Override
	public List<User> getPaged(Integer from, Integer size) {
		return userDao.getPaged(from, size);
	}

	@Override
	public User getByToken(String token) throws JAXBException {
		Person person = getProfil(token);
    	String userLogin = person.getLogin();
    	
    	User user = userDao.findByLogin(userLogin);
    	return user;
	}

	@Override
	public Shop saveShop(Shop shop) {
		shopDao.insert(shop);
		return shop;
	}

	@Override
	public boolean isPseudoExists(String pseudo)
	{
		return userDao.isPseudoExists(pseudo);
	}

}
