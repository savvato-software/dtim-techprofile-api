package org.haxwell.dtim.techprofile.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.haxwell.dtim.techprofile.entities.Question;
import org.haxwell.dtim.techprofile.entities.TechProfileLineItem;
import org.haxwell.dtim.techprofile.entities.TechProfileTopic;
import org.haxwell.dtim.techprofile.repositories.QuestionRepository;
import org.haxwell.dtim.techprofile.repositories.TechProfileLineItemRepository;
import org.haxwell.dtim.techprofile.repositories.TechProfileTopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TechProfileTopicServiceImpl implements TechProfileTopicService {

	@PersistenceContext
	EntityManager em;
	
	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	TechProfileTopicRepository techProfileTopicRepository;
	
	@Autowired
	TechProfileLineItemRepository techProfileLineItemRepository;
	
	@Override
	public Iterable<Question> getQuestionsByTopic(Long topicId) {
		return questionRepository.findByTopic(topicId);
	}
}
