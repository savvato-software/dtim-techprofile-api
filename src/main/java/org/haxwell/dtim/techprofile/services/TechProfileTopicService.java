package org.haxwell.dtim.techprofile.services;

import org.haxwell.dtim.techprofile.entities.Question;

public interface TechProfileTopicService {
	Iterable<Question> getQuestionsByTopic(Long topicId);
	boolean addExistingLineItemAsChild(Long topicId, Long existingLineItemId);
	boolean removeLineItemAsChild(Long topicId, Long existingLineItemId);
}
