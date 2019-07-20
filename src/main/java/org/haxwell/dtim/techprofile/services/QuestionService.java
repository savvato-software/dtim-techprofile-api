package org.haxwell.dtim.techprofile.services;

import java.util.List;
import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.Question;

public interface QuestionService {

	Iterable<Question> getAll();
	
	Iterable<Question> getByLineItemAndLevelNumber(Long lineItemId, Long lineItemLevel);
	Optional<Question> getById(Long id);
	
	List getAllLineItemAndLevelsFor(Long questionId);
	
	void setAllLineItemAndLevelsFor(Long questionId, int[][] arr);
	
	Question save(Long questionId, String questionText, int[][] lilvassociations);
}
