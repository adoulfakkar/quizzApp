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

import com.adoulfakkar.quizzApp.db.model.WebSerie;
import com.adoulfakkar.quizzApp.service.api.WebSerieService;
import com.adoulfakkar.quizzApp.webapp.vo.WebSeriePage;


@Controller
@RequestMapping("webSerie")
@Transactional(readOnly=false)
@PreAuthorize("hasRole('ADMIN')")
public class WebSerieController extends AbstractController {

	@Autowired
	private WebSerieService webSerieService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	@ResponseBody
	public WebSerie get(@PathVariable Integer id) {
		return webSerieService.find(id);
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	@ResponseBody
	public WebSeriePage getAll(@RequestParam(required=false) Integer from, @RequestParam(required=false) Integer size) {
		WebSeriePage res = new WebSeriePage();
		if (from == null && size == null) {
			List<WebSerie> questions = webSerieService.getAll();
			res.setFrom(0);
			res.setCount(new Long (questions.size()));
			res.setList(questions);
		}
		else {
			if (from == null)
				from = 0;
			if (size == null)
				size = 10;
			res.setFrom(from);
			res.setCount(webSerieService.getAllCount ());
			res.setList(webSerieService.getPaged (from, size));
		}
		return res;
	}

	@RequestMapping(value="{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public void delete(@PathVariable Integer id) {
		WebSerie webSerie = webSerieService.find(id);
		webSerieService.delete(webSerie);
	}

	@RequestMapping(value="/", method=RequestMethod.PUT)
	@ResponseBody
	public WebSerie update(@RequestBody WebSerie webSerie) {
		webSerie = webSerieService.update(webSerie);
		return webSerie;
	}

	@RequestMapping(value="/", method=RequestMethod.POST)
	@ResponseBody
	public WebSerie insert(@RequestBody WebSerie webSerie) {
		webSerieService.insert(webSerie);
		return webSerie;
	}
}
