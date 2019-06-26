package org.haxwell.dtim.techprofile.services;

import org.haxwell.dtim.techprofile.entities.Candidate;

public interface CandidateService {
	public Candidate createNewCandidate(String name, String phone, String email, String preferredContactMethod);
	
	public boolean markInAttendance(Long candidateId);
	
	public Candidate find(String query);
}
