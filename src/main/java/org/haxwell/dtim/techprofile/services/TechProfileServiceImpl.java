package org.haxwell.dtim.techprofile.services;

import java.util.List;
import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.CandidateTechProfileLineItemScore;
import org.haxwell.dtim.techprofile.entities.TechProfile;
import org.haxwell.dtim.techprofile.repositories.CandidateTechProfileLineItemScoreRepository;
import org.haxwell.dtim.techprofile.repositories.TechProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TechProfileServiceImpl implements TechProfileService {

	@Autowired
	TechProfileRepository techProfileRepository;
	
	@Autowired
	CandidateTechProfileLineItemScoreRepository ctplisRepository;
	
	@Override
	public TechProfile get(Long id) {
		Optional<TechProfile> opt = techProfileRepository.findById(id);
		
		return opt.get();
	}
	
	@Override
	public List<CandidateTechProfileLineItemScore> getCandidateIdScores(Long candidateId) {
		return ctplisRepository.findByCandidateId(candidateId);
	}

}
