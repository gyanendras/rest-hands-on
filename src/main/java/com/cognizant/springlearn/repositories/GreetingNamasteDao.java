package com.cognizant.springlearn.repositories;

import org.springframework.stereotype.Repository;

@Repository
public class GreetingNamasteDao extends GreetingDao {

	@Override
	public String getGreeting() {
		// TODO Auto-generated method stub
		return "Namaste";
	}
}
