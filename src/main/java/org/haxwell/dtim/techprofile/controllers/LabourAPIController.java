package org.haxwell.dtim.techprofile.controllers;

import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.Labour;
import org.haxwell.dtim.techprofile.services.LabourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LabourAPIController {

	@Autowired
	LabourService labourService;
	
	LabourAPIController() {
		
	}
	
	@RequestMapping(value = { "/api/labour/{id}" }, method=RequestMethod.GET)
	public Optional<Labour> get(@PathVariable Long id) {
		return labourService.getById(id);
	}
	
	@RequestMapping(value = { "/api/labour/" }, method=RequestMethod.GET)
	public Iterable<Labour> get() {
		return labourService.getAll();
	}
}
