package org.haxwell.dtim.techprofile.controllers;

import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.Question;
import org.haxwell.dtim.techprofile.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionAPIController {

	@Autowired
	QuestionService questionService;
	
	QuestionAPIController() {
		
	}
	
	@RequestMapping(value = { "/api/question/{lineItemLevelId}/{lineItemLevelIndex}" }, method=RequestMethod.GET)
	public Iterable<Question> get(@PathVariable Long lineItemLevelId, @PathVariable Long lineItemLevelIndex) {
		return questionService.getByLineItemAndLevelNumber(lineItemLevelId, lineItemLevelIndex);
	}

	@RequestMapping(value = { "/api/question/{id}"}, method=RequestMethod.GET)
	public Optional<Question> getById(@PathVariable Long id) {
		return questionService.getById(id);
	}
}
