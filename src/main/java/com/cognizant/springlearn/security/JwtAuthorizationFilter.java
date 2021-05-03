package com.cognizant.springlearn.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
		log.info("Start Inside authenticationManager");
		log.debug("{}: ", authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		log.info("Start doFilterInternal");
		String header = req.getHeader("Authorization");
		log.debug(header);

		if (header == null || !header.startsWith("Bearer ")) {
			chain.doFilter(req, res);
			return;
		}
		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
		log.info("End authentication" );
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		log.debug("token is " + token);
		if (token != null) {
// parse the token. 
			Jws<Claims> jws;
			try {
				jws = Jwts.parser().setSigningKey("ABC123").parseClaimsJws(token.replace("Bearer ", ""));
				String user = jws.getBody().getSubject();
				log.debug("User is " + user);
				ArrayList<SimpleGrantedAuthority> arr = new ArrayList<>();
				arr.add(new SimpleGrantedAuthority("ROLE_USER"));
				if (user != null) {
					return new UsernamePasswordAuthenticationToken(user, null,arr );
				}
			} catch (JwtException ex) {
				return null;
			}
			return null;
		}
		return null;
	}

}
