package com.adoulfakkar.quizzApp.webapp.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.adoulfakkar.quizzApp.db.model.User;
import com.adoulfakkar.quizzApp.service.api.UserService;

@Component("tokenBaseFilter")
public class TokenBaseFilter extends GenericFilterBean {

	@Autowired
	private UserService userService;
	
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		if (request.getPathInfo().endsWith("/user/login") || request.getPathInfo().endsWith("/get/application.apk") ||
				request.getPathInfo().endsWith("/user/download/login") || request.getPathInfo().endsWith("/get/video.mp4"))
			return false;
		return true;
	}

	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException,
			ServletException {
		String token = request.getHeader("token");
		if (token != null) {
			try {
				User user = userService.getByToken(token);
				if (user != null)  {
					List<String> roles = userService.role(token);
					List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
					for (String role:roles) {
						GrantedAuthority authority = new SimpleGrantedAuthority(role);
						authorities.add(authority);
					}
					Authentication auth = new AnonymousAuthenticationToken(token, user, authorities);
					return auth;
				}
			} catch (Exception e) {
				throw new AuthenticationCredentialsNotFoundException("Erreur lors de l'authentification", e);
			}
		}
		throw new AuthenticationCredentialsNotFoundException("Erreur lors de l'authentification");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		if (requiresAuthentication(request, response)) {
			Authentication authResult;
			try {
				authResult = attemptAuthentication(request, response);
				if (authResult != null) {
					SecurityContextHolder.getContext().setAuthentication(authResult);
				}
			} catch (InternalAuthenticationServiceException failed) {
				doError(response, failed);
				return;
			} catch (AuthenticationException failed) {
				doError(response, failed);
				return;
			}
		}
		chain.doFilter(request, response);
	}
	
	private void doError (HttpServletResponse response, Exception failed) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Authorization, x-requested-with, Content-Type, token");
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed: " + failed.getMessage());
	}
	
}
