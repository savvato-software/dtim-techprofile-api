package org.haxwell.dtim.techprofile.services;

import java.util.List;
import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.Question;

public interface QuestionService {

	Iterable<Question> getAll();
	
	Optional<Question> getById(Long id);
	Iterable<Question> getByLineItemAndLevelNumber(Long lineItemId, Long lineItemLevel);
	Iterable<Question> getQuestionsAnsweredCorrectlyAtAGivenLineItemAndLevelNumber(Long lineItemId, Long lineItemLevel, Long userId);
	
	List getAllLineItemAndLevelsFor(Long questionId);
	
	void setAllLineItemAndLevelsFor(Long questionId, int[][] arr);
	
	List<Question> getAllQuestionsAskedPeriod(Long userId);
	List<Question> getAllQuestionsAskedButNotSuccessfullyAnswered(Long userId);
	
	Question save(Long questionId, String questionText, String questionDescription, int[][] lilvassociations);
}
