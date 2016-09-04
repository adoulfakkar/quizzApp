package com.adoulfakkar.quizzApp.webapp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adoulfakkar.quizzApp.db.model.GoodPractice;
import com.adoulfakkar.quizzApp.db.model.News;
import com.adoulfakkar.quizzApp.db.model.Shop;
import com.adoulfakkar.quizzApp.db.model.ShopScore;
import com.adoulfakkar.quizzApp.db.model.User;
import com.adoulfakkar.quizzApp.db.model.UserScore;
import com.adoulfakkar.quizzApp.db.model.WebSerie;
import com.adoulfakkar.quizzApp.service.api.GoodPracticeService;
import com.adoulfakkar.quizzApp.service.api.NewsService;
import com.adoulfakkar.quizzApp.service.api.QuestionService;
import com.adoulfakkar.quizzApp.service.api.UserService;
import com.adoulfakkar.quizzApp.service.api.WebSerieService;
import com.adoulfakkar.quizzApp.webapp.vo.LoginVO;
import com.adoulfakkar.quizzApp.webapp.vo.ResponseVO;
import com.adoulfakkar.quizzApp.webapp.vo.ScoreResult;
import com.adoulfakkar.quizzApp.webapp.vo.TokenVO;
import com.adoulfakkar.quizzApp.webapp.vo.UserPage;


@Controller
@RequestMapping("user")
@Transactional(readOnly=false)
public class UserController extends AbstractController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private NewsService newsService;
	
	@Autowired
	private GoodPracticeService goodPracticeService;
	
	@Autowired
	private WebSerieService webSerieService;

	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public ResponseVO login(@RequestBody LoginVO login) throws JAXBException {
		String token = userService.getToken(login.getNickname(), login.getPassword());
		User user = userService.login(token);
		if (user != null) {
			List<News> news = newsService.getFromLastUpdate(user.getLastConnection());
			List<GoodPractice> goodPractices = goodPracticeService.getFromLastUpdate(user.getLastConnection());
			List<WebSerie> webSeries = webSerieService.getFromLastUpdate(user.getLastConnection());
			ResponseVO res = new ResponseVO ();
			res.setToken(token);
			res.setGoodPractice(goodPractices);
			res.setNews(news);
			res.setUser(user);
			res.setWebSeries(webSeries);
			
	    	user.setLastConnection(new GregorianCalendar ());
	    	userService.update(user);
			
			return res;
		}
		return null;
	}
	
	@RequestMapping(value="/download/login", method=RequestMethod.POST)
	@ResponseBody
	public TokenVO getToken(@RequestBody LoginVO login) throws JAXBException {
		String token = userService.getToken(login.getNickname(), login.getPassword());
		List<String> roles = userService.role(token);
		System.out.println(roles);
		TokenVO res = new TokenVO();
		res.setToken(token);
		return res;
	}
	
	@RequestMapping(value="/subscribe", method=RequestMethod.POST)
	@ResponseBody
	public boolean subscribe(@RequestBody String pseudo) throws Exception {
		/*User user = userService.find(getUser().getId());
		if (user == null)
			throw new Exception("User doew not exist");

		user.setPseudo(pseudo);
		userService.update(user);
		return true;*/

		User user = userService.find(getUser().getId());
		if (user != null && !userService.isPseudoExists(pseudo))
			{
			user.setPseudo(pseudo);
			userService.update(user);
			return true;
			}
		else
		 {return false;}

		
	}
	
	@RequestMapping(value="/scores", method=RequestMethod.GET)
	@ResponseBody
	public List<UserScore> getUserScore() throws Exception {
		User user = userService.find(getUser().getId());
		List<UserScore> users = userService.getScores(user);
		return users;
	}
	
	@RequestMapping(value="/scores/all", method=RequestMethod.GET)
	@ResponseBody
	public ScoreResult getAllScore() throws Exception {
		User user = userService.find(getUser().getId());
		if (user != null) {
			ScoreResult scoreResult = new ScoreResult();
			scoreResult.setUserScores(userService.getScores(user));
			scoreResult.setShopScores(userService.getScoreShop(user));
			return scoreResult;
		}
		return null;
	}

	@RequestMapping(value="/shop/scores", method=RequestMethod.GET)
	@ResponseBody
	public List<ShopScore> getShopScore(@RequestParam Integer userId) throws Exception {
		User user = userService.find(getUser().getId());
		List<ShopScore> shops = userService.getScoreShop(user);
		return shops;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	@ResponseBody
	public User get(@PathVariable Integer id) {
		User user = userService.find(id);
		return user;
	
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/", method=RequestMethod.GET)
	@ResponseBody
	public UserPage getAll(@RequestParam(required=false) Integer from, @RequestParam(required=false) Integer size) {
		UserPage res = new UserPage();
		if (from == null && size == null) {
			List<User> users = userService.getAll();
			res.setFrom(0);
			res.setCount(new Long (users.size()));
			res.setList(users);
		}
		else {
			if (from == null)
				from = 0;
			if (size == null)
				size = 10;
			res.setFrom(from);
			res.setCount(userService.getAllCount ());
			res.setList(userService.getPaged (from, size));
		}
		return res;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public void delete(@PathVariable Integer id) {
		User user = userService.find(id);
		userService.delete(user);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/", method=RequestMethod.PUT)
	@ResponseBody
	public User update(@RequestBody User user) {
		user = userService.update(user);
		return user;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/", method=RequestMethod.POST)
	@ResponseBody
	public User insert(@RequestBody User user) {
		userService.insert(user);
		return user;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/shop/inject", method=RequestMethod.POST)
	@ResponseBody
	public List<Shop> injectShops() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("shoplist.csv")));
		String line = null;
		List<Shop> res = new ArrayList<Shop> ();
		while ((line = in.readLine()) != null) {
			String[] values = line.split(";");
			Shop shop = new Shop();
			shop.setCode(values[1]);
			shop.setName(values[0]);
			shop.setScore(0);
			userService.saveShop(shop);
			res.add(shop);
		}
		return res;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/shop/create/generic", method=RequestMethod.POST)
	@ResponseBody
	public Shop createShop() {
		Shop shop = new Shop();
		shop.setCode("000000");
		shop.setName("Magasin générique");
		shop.setScore(0);
		userService.saveShop(shop);
		return shop;
	}
}
