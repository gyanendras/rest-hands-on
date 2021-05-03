package com.cognizant.springlearn.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JWTFilter extends OncePerRequestFilter {


    @Override
    public void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws IOException,
        ServletException {

        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String jwt = this.resolveToken(httpServletRequest);
            if (StringUtils.hasText(jwt)) {
               
                    Authentication authentication = getAuthentication(httpServletRequest);;
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                
            }
            filterChain.doFilter(servletRequest, servletResponse);

            this.resetAuthenticationAfterRequest();
        } catch (ExpiredJwtException eje) {
            log.info("Security exception for user {} - {}", eje.getClaims().getSubject(), eje.getMessage());
            ((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.debug("Exception " + eje.getMessage(), eje);
        }
    }

    private void resetAuthenticationAfterRequest() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    private String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String jwt = bearerToken.substring(7, bearerToken.length());
            return jwt;
        }
        return null;
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