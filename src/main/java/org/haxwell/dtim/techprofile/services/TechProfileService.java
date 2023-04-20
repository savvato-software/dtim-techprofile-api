package org.haxwell.dtim.techprofile.services;

import org.haxwell.dtim.techprofile.entities.UserTechProfileLineItemScore;

import java.util.List;

public interface TechProfileService {

	public List<UserTechProfileLineItemScore> getUserIdScores(Long userId);
	
	public List getQuestionCountsPerCell();
	public List getCorrectlyAnsweredQuestionCountsPerCell(Long userId);
	public List getIncorrectlyAnsweredQuestionCountsPerCell(Long userId);
	public List getAskedQuestionCountsPerCell(Long userId);
}
