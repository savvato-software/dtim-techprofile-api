package org.haxwell.dtim.techprofile.services;

import org.haxwell.dtim.techprofile.entities.Question;

public interface TechProfileTopicService {
	Iterable<Question> getQuestionsByTopic(Long topicId);
}
