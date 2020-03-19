package org.haxwell.dtim.techprofile.services;

import java.util.List;

import org.haxwell.dtim.techprofile.entities.Question;
import org.haxwell.dtim.techprofile.repositories.QuestionRepository;
import org.haxwell.dtim.techprofile.repositories.TechProfileLineItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TechProfileLineItemServiceImpl implements TechProfileLineItemService {

	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	TechProfileLineItemRepository techProfileLineItemRepository;
	
	@Override
	public Iterable<Question> getQuestionsByLineItem(Long lineItemId) {
		return questionRepository.findByLineItem(lineItemId);
	}
	
	@Override
	/**
	 * Returns an array of arrays, where each item represent a line item and level for the given questionId
	 * 
	 * 		[ [1, 2, 9], [5, 0, 9] ]
	 * 
	 * In this example, the given array represents that questionId 9 is associated with line item 1, at level 2, and with line item 5 at level 0.
	 * 
	 * @param questionId
	 * @return
	 */
	public List getLineItemsForQuestion(Long questionId) {
		return techProfileLineItemRepository.getLineItemLevelIndexForQuestion(questionId); 
	}
}
