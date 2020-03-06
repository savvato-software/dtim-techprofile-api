package org.haxwell.dtim.techprofile.controllers;

import org.haxwell.dtim.techprofile.entities.Question;
import org.haxwell.dtim.techprofile.services.TechProfileTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TechProfileTopicAPIController {

	@Autowired
	TechProfileTopicService techProfileTopicService;
	
	TechProfileTopicAPIController() {
		
	}
	
	@RequestMapping(value = { "/api/techprofile/topic/{topicId}/questions" }, method=RequestMethod.GET)
	public Iterable<Question> getQuestionsForTopic(@PathVariable Long topicId) {
		return this.techProfileTopicService.getQuestionsByTopic(topicId);
	}
}
