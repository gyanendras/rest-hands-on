package com.cognizant.springlearn.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cognizant.springlearn.SpringLearnApplication;

public class Country {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringLearnApplication.class);
	
	
	private String name;
	private String code;
	public Country() {
		super();
		LOGGER.debug("Inside Country Constructor.");
	}
	
	
	/*
	public Country(String name, String code) {
		super();
		this.name = name;
		this.code = code;
	}*/



	public String getName() {
		LOGGER.debug("Inside Country getName()");
		return name;
	}
	public void setName(String name) {
		LOGGER.debug("Inside Country setName()");
		this.name = name;
	}
	public String getCode() {
		LOGGER.debug("Inside Country getCode()");
		return code;
	}
	public void setCode(String code) {
		LOGGER.debug("Inside Country setCode()");
		this.code = code;
	}
	@Override
	public String toString() {
		return "Country [name=" + name + ", code=" + code + "]";
	}

	
	
	
	
}
