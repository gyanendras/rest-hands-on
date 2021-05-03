package com.cognizant.springlearn.controller;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class AuthenticationController {
	@GetMapping("/authenticate")
	@ResponseBody
	private Map<String, String>  authenticate(@RequestHeader("Authorization") String authHeader ) {
		log.debug("Begin Authenticate");
		Map<String, String> m = new HashMap<>();
		String user = getUser(authHeader);
		log.debug("User "+user);
		String token=generateJwt(user);
		m.put("token", token);
		log.debug("Token: "+token);
		log.debug("End Authenticate");
		return m;
	}
	
	private String generateJwt(String user) {
	  JwtBuilder builder = Jwts.builder(); 
	 builder.setSubject(user); 
	 
	// Set the token issue time as current time 
	builder.setIssuedAt(new Date()); 
	 // Set the token expiry as 20 minutes from now 
	 builder.setExpiration(new Date((new Date()).getTime() + 1200000)); 
	 builder.signWith(SignatureAlgorithm.HS256, "ABC123"); 
	 
	 String token = builder.compact(); 
	 
	 return token; 
	}
	
	private String getUser(String authHeader) {
		String baseStr = authHeader.substring(authHeader.indexOf("Basic ")+6);
		byte[] encCred = Base64.getDecoder().decode(baseStr);
		String encStr = new String(encCred);
		log.debug("EncStr " +encStr);
		return encStr.substring(0, encStr.indexOf(":"));
		
	}
}
