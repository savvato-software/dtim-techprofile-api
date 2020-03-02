package org.haxwell.dtim.techprofile.controllers;

import java.util.List;

import org.haxwell.dtim.techprofile.entities.Question;
import org.haxwell.dtim.techprofile.services.MockInterviewSessionService;
import org.haxwell.dtim.techprofile.services.UserQuestionService;
import org.haxwell.dtim.techprofile.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserMockInterviewSessionAPIController {

	@Autowired
	UserService userService;
	
	@Autowired
	UserQuestionService userQuestionService;
	
	@Autowired
	MockInterviewSessionService mockInterviewSessionService;
	
	UserMockInterviewSessionAPIController() {
		
	}
	
	@RequestMapping(value = { "/api/user/{userId}/mockinterviewsession/{sessionId}/questions" }, method=RequestMethod.GET)
	public List<Question> getQuestionsAskedInAGivenSession(@PathVariable Long userId, @PathVariable Long sessionId) {
		return userQuestionService.getQuestionsAskedInAGivenSession(userId, sessionId);
	}
}