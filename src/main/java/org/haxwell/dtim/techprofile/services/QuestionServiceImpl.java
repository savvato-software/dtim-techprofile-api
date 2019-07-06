package org.haxwell.dtim.techprofile.services;

import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.Question;
import org.haxwell.dtim.techprofile.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	QuestionRepository questionRepository;
	
	@Override
	public Iterable<Question> getByLineItemAndLevelNumber(Long lineItemId, Long lineItemLevel) {
		return this.questionRepository.findByLineItemAndLevelIndex(lineItemId, lineItemLevel);
	}
	
	@Override
	public Optional<Question> getById(Long id) {
		return this.questionRepository.findById(id);
	}
	
}
