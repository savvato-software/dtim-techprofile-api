package org.haxwell.dtim.techprofile.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.haxwell.dtim.techprofile.constants.Constants;
import org.haxwell.dtim.techprofile.entities.CareerGoal;
import org.haxwell.dtim.techprofile.entities.Question;
import org.haxwell.dtim.techprofile.entities.User;
import org.haxwell.dtim.techprofile.entities.UserQuestionGrade;
import org.haxwell.dtim.techprofile.entities.UserTechProfileLineItemScore;
import org.haxwell.dtim.techprofile.services.MockInterviewSessionService;
import org.haxwell.dtim.techprofile.services.TechProfileService;
import org.haxwell.dtim.techprofile.services.UserCareerGoalService;
import org.haxwell.dtim.techprofile.services.UserQuestionService;
import org.haxwell.dtim.techprofile.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAPIController {

	@Autowired
	UserService userService;
	
	@Autowired
	UserQuestionService userQuestionService;
	
	@Autowired
	UserCareerGoalService userCareerGoalService;
	
	@Autowired
	TechProfileService techProfileService;
	
	@Autowired
	MockInterviewSessionService mockInterviewSessionService;
	
	UserAPIController() {
		
	}
	
	@RequestMapping(value = { "/api/user/new" }, method=RequestMethod.POST)
	public ResponseEntity<User> createUser(HttpServletRequest request) {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		
		User user = userService.createNewUser(name, password, phone, email, Constants.SMS);
		
		if (user == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(user);
		}
	}
	
	@RequestMapping(value = { "/api/user/{userId}" }, method=RequestMethod.POST)
	public boolean updateUser(HttpServletRequest request, @PathVariable Long userId) {
		String password = request.getParameter("password");
		
		User user = userService.updateUserPassword(userId, password);
		
		return mockInterviewSessionService.markInAttendance(userId);
	}
	
	@RequestMapping(value = { "/api/user/{userId}/markInAttendance" }, method=RequestMethod.POST)
	public boolean markInAttendance(@PathVariable Long userId) {
		return mockInterviewSessionService.markInAttendance(userId);
	}
	
	@RequestMapping(value = { "/api/user" }, method=RequestMethod.GET)
	public User search(HttpServletRequest request) {
		String query = request.getParameter("q");
		
		return userService.find(query);
	}
	
	@RequestMapping(value = { "/api/user/{id}" }, method=RequestMethod.GET)
	public User search(@PathVariable Long id) {
		return userService.findById(id);
	}
	
	@RequestMapping(value = { "/api/user/{id}/techprofile/scores" }, method=RequestMethod.GET)
	public List<UserTechProfileLineItemScore> getUserIdScores(@PathVariable Long id) {
		return techProfileService.getUserIdScores(id);
	}
	
	@RequestMapping(value = { "/api/user/{id}/techprofile/scores" }, method=RequestMethod.POST)
	public boolean setUserIdScores(HttpServletRequest request, @PathVariable Long id) {
		Long count = Long.parseLong(request.getParameter("count"));
		int ctr = 0;

		List<UserTechProfileLineItemScore> scores = new ArrayList<>();

		while (ctr < count) {
			UserTechProfileLineItemScore score = new UserTechProfileLineItemScore();
			score.setUserId(id);
			score.setTechProfileLineItemId(Long.parseLong(request.getParameter("techProfileLineItemId"+ctr)));
			score.setTechProfileLineItemScore(Long.parseLong(request.getParameter("techProfileLineItemScore"+ctr)));
			
			scores.add(score);
			ctr++;
		}

		return userService.saveScores(scores);
	}
	
	@RequestMapping(value = { "/api/user/{cId}/question/{qId}/history" }, method=RequestMethod.GET)
	public List<UserQuestionGrade> getCandidateHistoryForQuestion(HttpServletRequest request, @PathVariable Long cId, @PathVariable Long qId) {
		return userService.getUserQuestionHistory(cId, qId);
	}
	
	@RequestMapping(value = { "/api/user/{cId}/question/{qId}/history" }, method=RequestMethod.POST)
	public UserQuestionGrade setCandidateHistoryForQuestion(HttpServletRequest request, @PathVariable Long cId, @PathVariable Long qId) {
		Long sessionId = Long.parseLong(request.getParameter("sessionId"));
		Long score = Long.parseLong(request.getParameter("score"));
		String comment = request.getParameter("comment");

		return userService.setGradeForQuestion(cId, sessionId, qId, score, comment);
	}
	
	@RequestMapping(value = { "/api/user/{cId}/careergoal/{cgId}/questions" }, method=RequestMethod.GET)
	public List<Question> getUsersNextQuestionsTowardACareerGoal(@PathVariable Long cId, @PathVariable Long cgId) {
		return userQuestionService.getNextQuestionsForCareerGoal(cId, cgId, 10);
	}
	
	@RequestMapping(value = { "/api/user/{cId}/careergoal" }, method=RequestMethod.GET)
	public CareerGoal getUsersCareerGoal(@PathVariable Long cId) {
		// sends the entire career goal object, with path relationships and all.
		return userCareerGoalService.getCareerGoalForUser(cId);
	}
	
}
