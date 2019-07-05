package org.haxwell.dtim.techprofile.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.haxwell.dtim.techprofile.constants.Constants;
import org.haxwell.dtim.techprofile.entities.Candidate;
import org.haxwell.dtim.techprofile.entities.CandidateQuestionGrade;
import org.haxwell.dtim.techprofile.entities.CandidateTechProfileLineItemScore;
import org.haxwell.dtim.techprofile.services.CandidateService;
import org.haxwell.dtim.techprofile.services.TechProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CandidateAPIController {

	@Autowired
	CandidateService candidateService;
	
	@Autowired
	TechProfileService techProfileService;
	
	CandidateAPIController() {
		
	}
	
	@RequestMapping(value = { "/api/candidate/new" }, method=RequestMethod.POST)
	public Candidate createCandidate(HttpServletRequest request) {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		
		return candidateService.createNewCandidate(name, email, phone, Constants.SMS);
	}
	
	@RequestMapping(value = { "/api/candidate/{id}/markInAttendance" }, method=RequestMethod.POST)
	public boolean markInAttendance(@PathVariable Long id) {
		return candidateService.markInAttendance(id);
	}
	
	@RequestMapping(value = { "/api/candidate" }, method=RequestMethod.GET)
	public Candidate search(HttpServletRequest request) {
		String query = request.getParameter("q");
		
		return candidateService.find(query);
	}
	
	@RequestMapping(value = { "/api/candidates/in-attendance" }, method=RequestMethod.GET)
	public List<Candidate> getCandidatesInAttendance() {
		return candidateService.getCandidatesInAttendance();
	}
	
	@RequestMapping(value = { "/api/candidate/{id}/techprofile/scores" }, method=RequestMethod.GET)
	public List<CandidateTechProfileLineItemScore> getCandidateIdScores(@PathVariable Long id) {
		return techProfileService.getCandidateIdScores(id);
	}
	
	@RequestMapping(value = { "/api/candidate/{id}/techprofile/scores" }, method=RequestMethod.POST)
	public boolean setCandidateIdScores(HttpServletRequest request, @PathVariable Long id) {
		Long count = Long.parseLong(request.getParameter("count"));
		int ctr = 0;
		
		List<CandidateTechProfileLineItemScore> scores = new ArrayList<>();
		
		while (ctr < count) {
			CandidateTechProfileLineItemScore score = new CandidateTechProfileLineItemScore();
			score.setCandidateId(id);
			score.setTechProfileLineItemId(Long.parseLong(request.getParameter("techProfileLineItemId"+ctr)));
			score.setTechProfileLineItemScore(Long.parseLong(request.getParameter("techProfileLineItemScore"+ctr)));
			
			scores.add(score);
			ctr++;
		}
		
		return candidateService.saveScores(scores);
	}
	
	@RequestMapping(value = { "/api/candidate/{cId}/question/{qId}/history" }, method=RequestMethod.GET)
	public List<CandidateQuestionGrade> getCandidateHistoryForQuestion(HttpServletRequest request, @PathVariable Long cId, @PathVariable Long qId) {
		return candidateService.getCandidateQuestionHistory(cId, qId);
	}	

	@RequestMapping(value = { "/api/candidate/{cId}/question/{qId}/history" }, method=RequestMethod.POST)
	public CandidateQuestionGrade setCandidateHistoryForQuestion(HttpServletRequest request, @PathVariable Long cId, @PathVariable Long qId) {
		Long sessionId = Long.parseLong(request.getParameter("sessionId"));
		Long score = Long.parseLong(request.getParameter("score"));

		return candidateService.setGradeForQuestion(cId, sessionId, qId, score);
	}	
}
