package org.haxwell.dtim.techprofile.services;

import org.haxwell.dtim.techprofile.entities.Question;
import org.haxwell.dtim.techprofile.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TechProfileLineItemServiceImpl implements TechProfileLineItemService {

	@Autowired
	QuestionRepository questionRepository;
	
	@Override
	public Iterable<Question> getQuestionsByLineItem(Long lineItemId) {
		return questionRepository.findByLineItem(lineItemId);
	}
}
