package org.haxwell.dtim.techprofile.services;

import java.util.List;

import org.haxwell.dtim.techprofile.entities.Question;

public interface TechProfileLineItemService {
	Iterable<Question> getQuestionsByLineItem(Long lineItemId);
	
	List getLineItemsForQuestion(Long questionId);
}
