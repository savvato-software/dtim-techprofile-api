package org.haxwell.dtim.techprofile.services;

import org.haxwell.dtim.techprofile.entities.Question;
import org.haxwell.dtim.techprofile.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TechProfileTopicServiceImpl implements TechProfileTopicService {

	@Autowired
	QuestionRepository questionRepository;
	
	@Override
	public Iterable<Question> getQuestionsByTopic(Long topicId) {
		return questionRepository.findByTopic(topicId);
	}
}
