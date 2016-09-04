package com.adoulfakkar.quizzApp.webapp.vo;

import java.io.Serializable;

public abstract class AbstractResponse implements Serializable{

	private static final long serialVersionUID = 1310876284575737176L;
	public static final String RESPONSE_RESULT_OK = "OK";
	public static final String RESPONSE_RESULT_KO = "KO";
	
	protected String result;
	protected String error;
	
	public AbstractResponse() {
		result = RESPONSE_RESULT_OK;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
