package org.haxwell.dtim.techprofile.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(CandidateTechProfileLineItemScoreId.class)
public class CandidateTechProfileLineItemScore implements Serializable {

	private static final long serialVersionUID = 19844238L;
	
	@Id
	private Long candidateId;
	
	public Long getCandidateId() {
		return candidateId;
	}
	
	public void setCandidateId(Long id) {
		this.candidateId = id;
	}
	
	@Id
	private Long techProfileLineItemId;
	
	public Long getTechProfileLineItemId() {
		return techProfileLineItemId;
	}
	
	public void setTechProfileLineItemId(Long id) {
		this.techProfileLineItemId = id;
	}
	
	private Long techProfileLineItemScore;
	
	public Long getTechProfileLineItemScore() {
		return techProfileLineItemScore;
	}
	
	public void setTechProfileLineItemScore(Long score) {
		this.techProfileLineItemScore = score;
	}
	
	public CandidateTechProfileLineItemScore(Long candidateId, Long techProfileLineItemId, Long techProfileLineItemScore) {
		this.candidateId = candidateId;
		this.techProfileLineItemId = techProfileLineItemId;
		this.techProfileLineItemScore = techProfileLineItemScore;
	}
	
	public CandidateTechProfileLineItemScore() {
		
	}
}
