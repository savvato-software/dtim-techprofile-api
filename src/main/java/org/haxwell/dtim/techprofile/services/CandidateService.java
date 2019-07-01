package org.haxwell.dtim.techprofile.services;

import java.util.List;

import org.haxwell.dtim.techprofile.entities.Candidate;
import org.haxwell.dtim.techprofile.entities.CandidateTechProfileLineItemScore;

public interface CandidateService {
	public Candidate createNewCandidate(String name, String phone, String email, String preferredContactMethod);
	
	public boolean markInAttendance(Long candidateId);
	
	public Candidate find(String query);
	
	public List<Candidate> getCandidatesInAttendance();
	
	public boolean saveScores(List<CandidateTechProfileLineItemScore> scores);
}
