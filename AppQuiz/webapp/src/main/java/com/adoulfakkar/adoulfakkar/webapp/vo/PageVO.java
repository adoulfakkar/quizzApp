package com.adoulfakkar.quizzApp.webapp.vo;

import java.util.List;

public class PageVO<T> {

	private Integer from;

	private Long count;
	
	private List<T> list;
	
	public Integer getFrom() {
		return from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}
