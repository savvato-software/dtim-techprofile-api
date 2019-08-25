package org.haxwell.dtim.techprofile.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.haxwell.dtim.techprofile.entities.MockInterviewSession;
import org.haxwell.dtim.techprofile.entities.User;
import org.haxwell.dtim.techprofile.services.MockInterviewSessionService;
import org.haxwell.dtim.techprofile.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockInterviewSessionAPIController {

	@Autowired
	MockInterviewSessionService mockInterviewSessionService;
	
	@Autowired
	UserService userService;
	
	MockInterviewSessionAPIController() {
		
	}
	
	@RequestMapping(value = { "/api/mockinterviewsession/create" }, method=RequestMethod.POST)
	public MockInterviewSession createSession(HttpServletRequest request) {
		return mockInterviewSessionService.createNewSession();
	}
	
	@RequestMapping(value = { "/api/mockinterviewsession/mostrecent" }, method=RequestMethod.GET)
	public MockInterviewSession getLastSession(HttpServletRequest request) {
		Optional<MockInterviewSession> opt = mockInterviewSessionService.getLastSession();
		
		if (opt.isPresent())
			return opt.get();
		else
			return null;
	}
	
	@RequestMapping(value = { "/api/mockinterviewsession/in-attendance" }, method=RequestMethod.GET)
	public List<User> getUsersInAttendance() {
		return mockInterviewSessionService.getUsersInAttendance();
	}
}
