package org.haxwell.dtim.techprofile.controllers;

import org.haxwell.dtim.techprofile.entities.Question;
import org.haxwell.dtim.techprofile.services.TechProfileLineItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TechProfileLineItemAPIController {

	@Autowired
	TechProfileLineItemService techProfileLineItemService;
	
	TechProfileLineItemAPIController() {
		
	}
	
	@RequestMapping(value = { "/api/techprofile/lineitem/{lineItemId}/questions" }, method=RequestMethod.GET)
	public Iterable<Question> getQuestionsForLineItem(@PathVariable Long lineItemId) {
		return this.techProfileLineItemService.getQuestionsByLineItem(lineItemId);
	}
}
