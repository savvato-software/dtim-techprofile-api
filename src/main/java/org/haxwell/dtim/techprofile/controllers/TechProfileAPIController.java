package org.haxwell.dtim.techprofile.controllers;

import javax.servlet.http.HttpServletRequest;

import org.haxwell.dtim.techprofile.entities.TechProfile;
import org.haxwell.dtim.techprofile.entities.TechProfileLineItem;
import org.haxwell.dtim.techprofile.entities.TechProfileTopic;
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

	@RequestMapping(value = { "/api/techprofile/topics/new" }, method=RequestMethod.POST)
	public TechProfileTopic newTopic(HttpServletRequest request) {
		String name = request.getParameter("topicName");
		
		return techProfileService.addTopic(name);
	}

	@RequestMapping(value = { "/api/techprofile/topics/{id}/lineitem/new" }, method=RequestMethod.POST)
	public TechProfileLineItem newLineItem(HttpServletRequest request, @PathVariable Long id) {
		String name = request.getParameter("lineItemName");
		String l0desc = request.getParameter("l0description");
		String l1desc = request.getParameter("l1description");
		String l2desc = request.getParameter("l2description");
		String l3desc = request.getParameter("l3description");
		
		return techProfileService.addLineItem(id, name, l0desc, l1desc, l2desc, l3desc);
	}
}
