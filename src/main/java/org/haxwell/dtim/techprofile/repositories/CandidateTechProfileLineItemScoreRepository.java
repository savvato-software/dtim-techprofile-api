package org.haxwell.dtim.techprofile.repositories;

import java.util.List;

import org.haxwell.dtim.techprofile.entities.CandidateTechProfileLineItemScore;
import org.springframework.data.repository.CrudRepository;

public interface CandidateTechProfileLineItemScoreRepository extends CrudRepository<CandidateTechProfileLineItemScore, Long> {

	public List<CandidateTechProfileLineItemScore> findByCandidateId(Long candidateId);
}