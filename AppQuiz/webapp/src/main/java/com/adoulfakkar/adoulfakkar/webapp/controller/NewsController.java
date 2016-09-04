package com.adoulfakkar.quizzApp.webapp.controller;

import java.util.List;

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

import com.adoulfakkar.quizzApp.db.model.News;
import com.adoulfakkar.quizzApp.service.api.NewsService;
import com.adoulfakkar.quizzApp.webapp.vo.NewsPage;


@Controller
@RequestMapping(value="news")
@Transactional(readOnly=false)
@PreAuthorize("hasRole('ADMIN')")
public class NewsController extends AbstractController {

	@Autowired
	private NewsService newsService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	@ResponseBody
	public News get(@PathVariable Integer id) {
		return newsService.find(id);
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	@ResponseBody
	public NewsPage getAll(@RequestParam(required=false) Integer from, @RequestParam(required=false) Integer size) {
		NewsPage nRes = new NewsPage();
		if (from == null && size == null) {
			List<News> news = newsService.getAll();
			nRes.setFrom(0);
			nRes.setCount(new Long (news.size()));
			nRes.setList(news);
		}
		else {
			if (from == null)
				from = 0;
			if (size == null)
				size = 10;
			nRes.setFrom(from);
			nRes.setCount(newsService.getAllCount ());
			nRes.setList(newsService.getPaged (from, size));
		}
		return nRes;
	}

	@RequestMapping(value="{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public void delete(@PathVariable Integer id) {
		News news = newsService.find(id);
		newsService.delete(news);
	}

	@RequestMapping(value="/", method=RequestMethod.PUT)
	@ResponseBody
	public News update(@RequestBody News news) {
		news = newsService.update(news);
		return news;
	}

	@RequestMapping(value="/", method=RequestMethod.POST)
	@ResponseBody
	public News insert(@RequestBody News news) {
		newsService.insert(news);
		return news;
	}
}
