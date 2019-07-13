package org.haxwell.dtim.techprofile.services;

import java.util.List;

import org.haxwell.dtim.techprofile.entities.CandidateTechProfileLineItemScore;
import org.haxwell.dtim.techprofile.entities.TechProfile;
import org.haxwell.dtim.techprofile.entities.TechProfileLineItem;
import org.haxwell.dtim.techprofile.entities.TechProfileTopic;

public interface TechProfileService {

	public TechProfile get(Long id);
	public TechProfileTopic addTopic(String topicName);
	public TechProfileLineItem addLineItem(Long topicId, String lineItemName, String l0desc, String l1desc, String l2desc, String l3desc);
	public List<CandidateTechProfileLineItemScore> getCandidateIdScores(Long candidateId);
}
