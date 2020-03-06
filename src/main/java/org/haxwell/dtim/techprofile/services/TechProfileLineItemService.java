package org.haxwell.dtim.techprofile.services;

import org.haxwell.dtim.techprofile.entities.Question;

public interface TechProfileLineItemService {
	Iterable<Question> getQuestionsByLineItem(Long lineItemId);
}
