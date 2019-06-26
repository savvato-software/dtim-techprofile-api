package org.haxwell.dtim.techprofile.controllers;

import javax.servlet.http.HttpServletRequest;

import org.haxwell.dtim.techprofile.constants.Constants;
import org.haxwell.dtim.techprofile.entities.Candidate;
import org.haxwell.dtim.techprofile.services.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CandidateAPIController {

	@Autowired
	CandidateService candidateService;
	
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
}
