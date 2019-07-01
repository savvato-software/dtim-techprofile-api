package org.haxwell.dtim.techprofile.services;

import org.haxwell.dtim.techprofile.entities.Question;

public interface QuestionService {

	Iterable<Question> getByLineItemAndLevelNumber(Long lineItemId, Long lineItemLevel);
}
