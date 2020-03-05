package org.haxwell.dtim.techprofile.controllers;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.haxwell.dtim.techprofile.entities.CareerGoal;
import org.haxwell.dtim.techprofile.entities.Path;
import org.haxwell.dtim.techprofile.services.PathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

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
	
	@RequestMapping(value = { "/api/path/all" }, method=RequestMethod.GET)
	public Iterable<Path> get() {
		return pathService.getAll();
	}
	
	@RequestMapping(value = { "/api/path/save"}, method=RequestMethod.POST)
	public Path save(HttpServletRequest request) {
		Path rtn = null;
		
		try {
			String str = request.getReader().lines().collect(Collectors.joining());
			net.minidev.json.parser.JSONParser parser = new JSONParser();
			
			JSONObject obj = (JSONObject)parser.parse(str);
			
			JSONObject p = ((JSONObject)obj.get("path"));
			String milestoneIds = (String)obj.get("milestoneassociations");
			
			rtn = pathService.save(
					Long.parseLong(p.getAsString("id")),
					p.getAsString("name"),
					milestoneIds);
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rtn;
	}	
}
