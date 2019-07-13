package org.haxwell.dtim.techprofile.services;

import java.util.List;

import org.haxwell.dtim.techprofile.entities.CandidateTechProfileLineItemScore;
import org.haxwell.dtim.techprofile.entities.TechProfile;

public interface TechProfileService {

	public TechProfile get(Long id);
	public boolean addTopic(String topicName);
	public boolean addLineItem(Long topicId, String lineItemName);
	public List<CandidateTechProfileLineItemScore> getCandidateIdScores(Long candidateId);
}
