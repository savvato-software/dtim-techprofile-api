package org.haxwell.dtim.techprofile.services;

import java.util.List;

import org.haxwell.dtim.techprofile.entities.CandidateTechProfileLineItemScore;
import org.haxwell.dtim.techprofile.entities.TechProfile;

public interface TechProfileService {

	public TechProfile get(Long id);
	public List<CandidateTechProfileLineItemScore> getCandidateIdScores(Long candidateId);
}
