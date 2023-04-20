package org.haxwell.dtim.techprofile.controllers;

import org.haxwell.dtim.techprofile.services.TechProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TechProfileAPIController {

	@Autowired
	TechProfileService techProfileService;
	
	TechProfileAPIController() {
		
	}
	
	@RequestMapping(value = { "/api/techprofile/questionCountsPerCell" }, method=RequestMethod.GET)
	public List getQuestionCountsPerTechProfileCell() {
		List list = techProfileService.getQuestionCountsPerCell();
		
		return list;
	}
	
	@RequestMapping(value = { "/api/techprofile/user/{userId}/correctlyAnsweredQuestionCountsPerCell" }, method=RequestMethod.GET)
	public List getCorrectlyAnsweredQuestionCountsPerCell(@PathVariable Long userId) {
		List list = techProfileService.getCorrectlyAnsweredQuestionCountsPerCell(userId);
		
		return list;
	}
	@RequestMapping(value = { "/api/techprofile/user/{userId}/incorrectlyAnsweredQuestionCountsPerCell" }, method=RequestMethod.GET)
	public List getIncorrectlyAnsweredQuestionCountsPerCell(@PathVariable Long userId) {
		List list = techProfileService.getIncorrectlyAnsweredQuestionCountsPerCell(userId);
		
		return list;
	}
	@RequestMapping(value = { "/api/techprofile/user/{userId}/askedQuestionCountsPerCell" }, method=RequestMethod.GET)
	public List getAskedQuestionCountsPerCell(@PathVariable Long userId) {
		List list = techProfileService.getAskedQuestionCountsPerCell(userId);
		
		return list;
	}
}
