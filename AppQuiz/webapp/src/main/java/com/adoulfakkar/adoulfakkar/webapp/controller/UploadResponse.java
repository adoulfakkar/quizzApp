package com.adoulfakkar.quizzApp.webapp.controller;

public class UploadResponse {

	private String path;
	
	public UploadResponse (String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
