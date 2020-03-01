package org.haxwell.dtim.techprofile.controllers;

import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.Milestone;
import org.haxwell.dtim.techprofile.services.MilestoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MilestoneAPIController {

	@Autowired
	MilestoneService milestoneService;
	
	MilestoneAPIController() {
		
	}
	
	@RequestMapping(value = { "/api/milestone/{id}" }, method=RequestMethod.GET)
	public Optional<Milestone> get(@PathVariable Long id) {
		return milestoneService.getById(id);
	}
	
	@RequestMapping(value = { "/api/milestone/" }, method=RequestMethod.GET)
	public Iterable<Milestone> get() {
		return milestoneService.getAll();
	}
}
