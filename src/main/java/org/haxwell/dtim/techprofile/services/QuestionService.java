package org.haxwell.dtim.techprofile.services;

import java.util.List;
import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.Question;

public interface QuestionService {

	Iterable<Question> getAll();
	
	Optional<Question> getById(Long id);
	Iterable<Question> getByTextOrDescription(String query);	
	Iterable<Question> getByLineItem(Long lineItemId);
	Iterable<Question> getByLineItemAndLevelNumber(Long lineItemId, Long lineItemLevel);
	Iterable<Question> getQuestionsAnsweredCorrectly(Long userId);
	Iterable<Question> getQuestionsAnsweredIncorrectly(Long userId);
	Iterable<Question> getQuestionsAnsweredCorrectlyAtAGivenLineItemAndLevelNumber(Long lineItemId, Long lineItemLevel, Long userId);
	Iterable<Question> getQuestionsAnsweredIncorrectlyAtAGivenLineItemAndLevelNumber(Long lineItemId, Long lineItemLevel, Long userId);

	List getAllLineItemAndLevelsFor(Long questionId);
	
	void setAllLineItemAndLevelsFor(Long questionId, int[][] arr);
	
	List<Question> getAllQuestionsAskedPeriod(Long userId);
	
	Question save(Long questionId, String questionText, String questionDescription, int[][] lilvassociations);
}
