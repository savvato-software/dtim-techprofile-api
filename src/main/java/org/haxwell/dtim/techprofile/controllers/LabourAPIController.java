package org.haxwell.dtim.techprofile.controllers;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.haxwell.dtim.techprofile.entities.Labour;
import org.haxwell.dtim.techprofile.services.LabourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

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
	
	@RequestMapping(value = { "/api/labour/all" }, method=RequestMethod.GET)
	public Iterable<Labour> get() {
		return labourService.getAll();
	}

	@RequestMapping(value = { "/api/labour/save"}, method=RequestMethod.POST)
	public Labour save(HttpServletRequest request) {
		Labour rtn = null;
 		
		try {
			String str = request.getReader().lines().collect(Collectors.joining());
			net.minidev.json.parser.JSONParser parser = new JSONParser();
			
			JSONObject obj = (JSONObject)parser.parse(str);
			
			JSONObject labour = ((JSONObject)obj.get("labour"));
			String questionIds = (String)obj.get("questionassociations");
			
			rtn = labourService.save(
					Long.parseLong(labour.getAsString("id")),
					labour.getAsString("name"),
					questionIds);
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rtn;
	}	
}
