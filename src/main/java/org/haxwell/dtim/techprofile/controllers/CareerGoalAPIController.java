package org.haxwell.dtim.techprofile.controllers;

import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.CareerGoal;
import org.haxwell.dtim.techprofile.entities.Path;
import org.haxwell.dtim.techprofile.services.CareerGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CareerGoalAPIController {

	@Autowired
	CareerGoalService careerGoalService;
	
	CareerGoalAPIController() {
		
	}
	
	@RequestMapping(value = { "/api/careergoal/{id}" }, method=RequestMethod.GET)
	public Optional<CareerGoal> get(@PathVariable Long id) {
		return careerGoalService.getById(id);
	}
	
	@RequestMapping(value = { "/api/careergoal/" }, method=RequestMethod.GET)
	public Iterable<CareerGoal> get() {
		return careerGoalService.getAll();
	}
	
	@RequestMapping(value = { "/api/careergoal/{careerGoalId}/paths" }, method=RequestMethod.GET)
	public Iterable<Path> getPathsThatBelongToGivenCareerGoal(@PathVariable Long careerGoalId) {
		return careerGoalService.getPathsFor(careerGoalId);
	}
}
