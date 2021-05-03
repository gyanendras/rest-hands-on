package com.cognizant.springlearn.controller;

import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cognizant.springlearn.model.Greeting;

@Controller
public class SayHiLoginController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SayHiLoginController.class);
	
	private static final String template = "Hi, %s!";
	private final AtomicLong counter = new AtomicLong();

	@ResponseBody
	@GetMapping("/sayhi")
	public Greeting sayHi(@RequestParam(value = "name", defaultValue = "World") String name) {
		LOGGER.debug("Inside greeting");
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
	
	 // Login form  
	@RequestMapping("/login.html")  
    public String login() {  
        return "login.html";  
    } 
		
	@RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
	  public String fetchSignoutSite(HttpServletRequest request, HttpServletResponse response){
	        
	      HttpSession session = request.getSession(false);
	      SecurityContextHolder.clearContext();

	      session = request.getSession(false);
	      if(session != null) {
	          session.invalidate();
	      }

	      for(Cookie cookie : request.getCookies()) {
	          cookie.setMaxAge(0);
	      }

	      return "redirect:/login?logout";
	  }
	
	
}