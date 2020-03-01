package org.haxwell.dtim.techprofile.controllers;

import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.Path;
import org.haxwell.dtim.techprofile.services.PathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PathAPIController {

	@Autowired
	PathService pathService;
	
	PathAPIController() {
		
	}
	
	@RequestMapping(value = { "/api/path/{id}" }, method=RequestMethod.GET)
	public Optional<Path> get(@PathVariable Long id) {
		return pathService.getById(id);
	}
	
	@RequestMapping(value = { "/api/path/" }, method=RequestMethod.GET)
	public Iterable<Path> get() {
		return pathService.getAll();
	}
}
