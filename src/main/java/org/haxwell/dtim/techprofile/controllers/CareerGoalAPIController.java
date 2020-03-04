package org.haxwell.dtim.techprofile.controllers;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.haxwell.dtim.techprofile.entities.CareerGoal;
import org.haxwell.dtim.techprofile.entities.Path;
import org.haxwell.dtim.techprofile.entities.Question;
import org.haxwell.dtim.techprofile.services.CareerGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

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
	
	@RequestMapping(value = { "/api/careergoal/save"}, method=RequestMethod.POST)
	public CareerGoal save(HttpServletRequest request) {
		CareerGoal rtn = null;
		
		try {
			String str = request.getReader().lines().collect(Collectors.joining());
			net.minidev.json.parser.JSONParser parser = new JSONParser();
			
			JSONObject obj = (JSONObject)parser.parse(str);
			
			JSONObject cg = ((JSONObject)obj.get("careergoal"));
			String pathIds = (String)obj.get("pathassociations");
			
			rtn = careerGoalService.save(
					Long.parseLong(cg.getAsString("id")),
					cg.getAsString("name"),
					pathIds);
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rtn;
	}	
}
