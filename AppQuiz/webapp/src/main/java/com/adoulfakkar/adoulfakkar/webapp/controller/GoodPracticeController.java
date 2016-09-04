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

import com.adoulfakkar.quizzApp.db.model.GoodPractice;
import com.adoulfakkar.quizzApp.service.api.GoodPracticeService;
import com.adoulfakkar.quizzApp.webapp.vo.GoodPracticePage;


@Controller
@RequestMapping("goodPractice")
@Transactional(readOnly=false)
@PreAuthorize("hasRole('ADMIN')")
public class GoodPracticeController extends AbstractController {

	@Autowired
	private GoodPracticeService goodPracticeService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	@ResponseBody
	public GoodPractice get(@PathVariable Integer id) {
		return goodPracticeService.find(id);
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	@ResponseBody
	public GoodPracticePage getAll(@RequestParam(required=false) Integer from, @RequestParam(required=false) Integer size) {
		GoodPracticePage res = new GoodPracticePage();
		if (from == null && size == null) {
			List<GoodPractice> goodPractices = goodPracticeService.getAll();
			res.setFrom(0);
			res.setCount(new Long (goodPractices.size()));
			res.setList(goodPractices);
		}
		else {
			if (from == null)
				from = 0;
			if (size == null)
				size = 10;
			res.setFrom(from);
			res.setCount(goodPracticeService.getAllCount ());
			res.setList(goodPracticeService.getPaged (from, size));
		}
		return res;
	}

	@RequestMapping(value="{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public void delete(@PathVariable Integer id) {
		GoodPractice goodPractice = goodPracticeService.find(id);
		goodPracticeService.delete(goodPractice);
	}

	@RequestMapping(value="/", method=RequestMethod.PUT)
	@ResponseBody
	public GoodPractice update(@RequestBody GoodPractice goodPractice) {
		goodPractice = goodPracticeService.update(goodPractice);
		return goodPractice;
	}

	@RequestMapping(value="/", method=RequestMethod.POST)
	@ResponseBody
	public GoodPractice insert(@RequestBody GoodPractice goodPractice) {
		goodPracticeService.insert(goodPractice);
		return goodPractice;
	}
}
