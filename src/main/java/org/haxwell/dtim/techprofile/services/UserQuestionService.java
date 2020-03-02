package org.haxwell.dtim.techprofile.services;

import java.util.List;

import org.haxwell.dtim.techprofile.entities.Question;

public interface UserQuestionService {
	public List<Question> getNextQuestionsForCareerGoal(Long userId, Long careerGoalId, Integer maxToReturn);
	public List<Question> getQuestionsAskedInAGivenSession(Long userId, Long sessionId);
}
