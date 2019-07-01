package org.haxwell.dtim.techprofile.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.Candidate;
import org.haxwell.dtim.techprofile.entities.CandidateAttendanceHistory;
import org.haxwell.dtim.techprofile.entities.CandidateTechProfileLineItemScore;
import org.haxwell.dtim.techprofile.entities.MockInterviewSession;
import org.haxwell.dtim.techprofile.repositories.CandidateAttendanceHistoryRepository;
import org.haxwell.dtim.techprofile.repositories.CandidateRepository;
import org.haxwell.dtim.techprofile.repositories.CandidateTechProfileLineItemScoreRepository;
import org.haxwell.dtim.techprofile.repositories.MockInterviewSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidateServiceImpl implements CandidateService {

	@Autowired
	CandidateRepository candidateRepo;
	
	@Autowired
	CandidateAttendanceHistoryRepository cahRepo;
	
	@Autowired
	CandidateTechProfileLineItemScoreRepository scoreRepo;
	
	@Autowired
	MockInterviewSessionRepository misRepo;

	public Candidate createNewCandidate(String name, String phone, String email, String preferredContactMethod) {
		if (isPhoneValid(phone) && isEmailValid(email) && name.length() >= 3) {
			
			Optional<Candidate> opt = this.candidateRepo.findByNamePhoneOrEmail(name, phone, email);
			
			if (!opt.isPresent()) {
				Candidate candidate = new Candidate(name, phone, email);
				
				Candidate rtn = candidateRepo.save(candidate);
				
				this.sendNewCandidateSMS();
				this.sendNewCandidateEmail();
				
				return rtn;
			} else {
				throw new IllegalArgumentException("This Candidate already exists: " + name + " " + phone + " " + email);
			}
		}
		
		return null;
	}
	
	public boolean markInAttendance(Long candidateId) {
		boolean rtn = false;
		
		Optional<MockInterviewSession> opt = misRepo.findMostRecentlyStarted();
		if (opt.isPresent()) {
			rtn = cahRepo.save(new CandidateAttendanceHistory(opt.get().getId(), candidateId)) != null;
		}
		
		return rtn;
	}
	
	public Candidate find(String query) {
		Optional<Candidate> opt = candidateRepo.findByPhoneOrEmail(query);
		Candidate rtn = null;
		
		if (opt.isPresent())
			rtn = opt.get();
		
		return rtn;
	}
	
	public List<Candidate> getCandidatesInAttendance() {
		List<CandidateAttendanceHistory> cahList = cahRepo.getThoseWithinTheLastThreeHours();
		
		List<Candidate> canList = new ArrayList<>();
		
		Iterator<CandidateAttendanceHistory> iterator = cahList.iterator();
		while (iterator.hasNext()) {
			canList.add(candidateRepo.findById(iterator.next().getCandidateId()).get());
		}
		
		return canList;
	}
	
	public boolean saveScores(List<CandidateTechProfileLineItemScore> scores) {
		scores.forEach((score) -> { 
			scoreRepo.save(score);
		});

		return true;
	}
	
	public boolean isPhoneValid(String phone) {
		return true;
	}
	
	public boolean isEmailValid(String email) {
		return true;
	}
	
	public void sendNewCandidateSMS() {
		// if the user prefers sms, they can respond to the link in this sms.
		// that will confirm their account, set their preference.
		
		// when they log in next time, we will confirm their attendance by sending an sms.
	}

	public void sendNewCandidateEmail() {
		// if the user prefers email, they can respond to the link in this email.
		// that will confirm their account, set their preference.
		
		// when they log in next time, we will confirm their attendance by sending an email.
	}
}
