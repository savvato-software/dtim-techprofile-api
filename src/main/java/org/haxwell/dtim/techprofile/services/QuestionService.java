package org.haxwell.dtim.techprofile.services;

import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.Question;

public interface QuestionService {

	Iterable<Question> getByLineItemAndLevelNumber(Long lineItemId, Long lineItemLevel);
	Optional<Question> getById(Long id);
}
