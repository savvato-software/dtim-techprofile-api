package org.haxwell.dtim.techprofile.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

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
	
	@RequestMapping(value = { "/api/question/{questionId}/lineitem/levels" }, method=RequestMethod.GET)
	public Iterable<Question> getAllLineItemLevelsAssociatedWithTheGivenQuestion(@PathVariable Long questionId) {
		return questionService.getAllLineItemAndLevelsFor(questionId);
	}
	
	@RequestMapping(value = { "/api/question/{questionId}/lineitem/levels" }, method=RequestMethod.POST)
	public boolean setAllLineItemLevelsAssociatedWithTheGivenQuestion(HttpServletRequest request, @PathVariable Long questionId) {
		String count = request.getParameter("count");
		
		if (count != null) {
			int[][] arr = new int[Integer.parseInt(count)][2];
			
			for (int x = 0; x < Integer.parseInt(count); x++) {
				arr[x][0] = Integer.parseInt(request.getParameter("liId" + x));
				arr[x][1] = Integer.parseInt(request.getParameter("liVal" + x));
			}
			
			questionService.setAllLineItemAndLevelsFor(questionId, arr, Integer.parseInt(count));
			
			return true;
		}
		
		return false;
	}
	
	@RequestMapping(value = { "/api/question/{id}"}, method=RequestMethod.GET)
	public Iterable<Question> getById(@PathVariable String id) {
		if ("all".equals(id)) {
			return questionService.getAll();
		} else {
			ArrayList<Question> rtn = new ArrayList<Question>();
			rtn.add(questionService.getById(Long.parseLong(id)).get());
			
			return rtn;
		}
	}
}
