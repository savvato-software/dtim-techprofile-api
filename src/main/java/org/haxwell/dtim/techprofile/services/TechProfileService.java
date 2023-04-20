package org.haxwell.dtim.techprofile.services;

import java.util.List;
import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.TechProfile;
import org.haxwell.dtim.techprofile.entities.TechProfileLineItem;
import org.haxwell.dtim.techprofile.entities.TechProfileTopic;
import org.haxwell.dtim.techprofile.entities.UserTechProfileLineItemScore;

public interface TechProfileService {

	public List<UserTechProfileLineItemScore> getUserIdScores(Long userId);
	
	public List getQuestionCountsPerCell();
	public List getCorrectlyAnsweredQuestionCountsPerCell(Long userId);
	public List getIncorrectlyAnsweredQuestionCountsPerCell(Long userId);
	public List getAskedQuestionCountsPerCell(Long userId);
}
