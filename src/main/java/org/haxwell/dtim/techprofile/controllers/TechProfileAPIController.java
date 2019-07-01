package org.haxwell.dtim.techprofile.controllers;

import org.haxwell.dtim.techprofile.entities.TechProfile;
import org.haxwell.dtim.techprofile.services.TechProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TechProfileAPIController {

	@Autowired
	TechProfileService techProfileService;
	
	TechProfileAPIController() {
		
	}
	
	@RequestMapping(value = { "/api/techprofile/{id}" }, method=RequestMethod.GET)
	public TechProfile get(@PathVariable Long id) {
		return techProfileService.get(id);
	}
}
