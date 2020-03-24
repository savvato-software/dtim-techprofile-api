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
	
	@Override
	@Transactional
	public boolean addExistingLineItemAsChild(Long topicId, Long existingLineItemId) {
		Optional<TechProfileTopic> topic = techProfileTopicRepository.findById(topicId);

		if (topic.isPresent()) {
			TechProfileTopic _topic = topic.get();

			Optional<TechProfileLineItem> existingLineItemIdOptional = techProfileLineItemRepository.findById(existingLineItemId);

			if (existingLineItemIdOptional.isPresent()) {
				TechProfileLineItem _existingLineItem = existingLineItemIdOptional.get();

				List resultList = em.createNativeQuery("SELECT max(sequence) FROM tech_profile_topic_line_item_map where tech_profile_topic_id=:topicId")
						.setParameter("topicId", _topic.getId())
						.getResultList();

				Long currentMaxSequenceNum = 0L;
						
				if (resultList.size() > 0 && resultList.get(0) != null)
					currentMaxSequenceNum = Long.parseLong(resultList.get(0).toString());
				
				em.createNativeQuery("INSERT INTO tech_profile_topic_line_item_map (tech_profile_topic_id, tech_profile_line_item_id, sequence) VALUES (:topicId, :lineItemId, :sequence)")
					.setParameter("topicId", _topic.getId())
					.setParameter("lineItemId", _existingLineItem.getId())
					.setParameter("sequence", currentMaxSequenceNum + 1)
					.executeUpdate();

				return true;
			}
		}

		return false;
	}
	
	@Override
	public boolean removeLineItemAsChild(Long topicId, Long existingLineItemId) { 
		Optional<TechProfileTopic> topic = techProfileTopicRepository.findById(topicId);

		if (topic.isPresent()) {
			TechProfileTopic _topic = topic.get();
			Set<TechProfileLineItem> topicLineItems = _topic.getLineItems();
			
			topicLineItems.removeIf(li -> li.getId() == existingLineItemId);
			
			// necessary?
			_topic.setLineItems(topicLineItems);
			
			techProfileTopicRepository.save(_topic);
			return true;
		}

		return false;
	}
}
