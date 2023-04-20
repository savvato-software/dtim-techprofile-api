package org.haxwell.dtim.techprofile.services;

import java.util.List;

import org.haxwell.dtim.techprofile.entities.Question;

public interface LineItemQuestionService {
	Iterable<Question> getQuestionsByLineItem(Long lineItemId);
	
	List getLineItemsForQuestion(Long questionId);
}
