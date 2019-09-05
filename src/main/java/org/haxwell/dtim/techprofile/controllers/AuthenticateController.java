package org.haxwell.dtim.techprofile.controllers;

import java.io.IOException;	

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.haxwell.dtim.techprofile.config.principle.UserPrincipal;
import org.haxwell.dtim.techprofile.entities.User;
import org.haxwell.dtim.techprofile.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = { "/api/authenticate" })
public class AuthenticateController {

	private static final Log logger = LogFactory.getLog(AuthenticateController.class);
	
	@Autowired
	UserRepository ur;
	
	@Autowired
	ProviderManager authManager;
	
	AuthenticateController() {
		
	}
	
	@GetMapping
	public User authenticate(HttpServletRequest request, Model model) throws Exception {
		String[] arr = extractAndDecodeHeader(request.getHeader("Authorization"));
		
		UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(arr[0], arr[1]);
		Authentication auth = authManager.authenticate(authReq);
		SecurityContext sc = SecurityContextHolder.getContext();
		sc.setAuthentication(auth);		
		
		if (sc.getAuthentication().isAuthenticated()) {
			logger.info("*** Successfully logged in [" + arr[0] + "]");
			System.out.println("*** Successfully logged in [" + arr[0] + "]");
			return ((UserPrincipal)auth.getPrincipal()).getUser();
		} else {
			logger.info("*** Could not log in [" + arr[0] + "] with the given password.");
			System.out.println("*** Could not log in [" + arr[0] + "] with the given password.");
			throw new BadCredentialsException("Could not find a user with the given name and password.");
		}
	}
	
	/**
	 * Decodes the header into a username and password.
	 * 
	 * Very Inspired by Spring Security's BasicAuthenticationFilter.
	 *
	 * @throws BadCredentialsException if the Basic header is not present or is not valid
	 * Base64
	 */
	private String[] extractAndDecodeHeader(String header)
			throws IOException {

		byte[] base64Token = header.substring(6).getBytes("UTF-8");
		byte[] decoded;
		try {
			decoded = Base64.decode(base64Token);
		}
		catch (IllegalArgumentException e) {
			throw new BadCredentialsException(
					"Failed to decode basic authentication token");
		}

		String token = new String(decoded);

		int delim = token.indexOf(":");

		if (delim == -1) {
			logger.error("Basic Authentication Token seems to be malformed [" + token + "]. Throwing BadCredentialsException.");
			throw new BadCredentialsException("Invalid basic authentication token");
		}
		return new String[] { token.substring(0, delim), token.substring(delim + 1) };
	}
	
}