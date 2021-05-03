package com.cognizant.springlearn.service;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.springlearn.model.Greeting;
import com.cognizant.springlearn.repositories.GreetingDao;

@Service
public class GreetingService {
	public Long counter ;
	
	@Autowired
	GreetingDao greetingDao;
	
	public GreetingService() {
		this.counter = 1L;
	}

	public GreetingService(Long counter) {
		this.counter = counter;
	}
	
	public Greeting getGreeting(){
		Greeting gr = new Greeting(20l, greetingDao.getGreeting()); 	
		return gr;
	}
	
	
}