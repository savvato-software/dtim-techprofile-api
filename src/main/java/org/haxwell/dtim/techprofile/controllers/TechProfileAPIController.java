package org.haxwell.dtim.techprofile.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.haxwell.dtim.techprofile.constants.Constants;
import org.haxwell.dtim.techprofile.entities.TechProfile;
import org.haxwell.dtim.techprofile.entities.TechProfileLineItem;
import org.haxwell.dtim.techprofile.entities.TechProfileTopic;
import org.haxwell.dtim.techprofile.services.TechProfileService;
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

	@RequestMapping(value = { "/api/techprofile/topic/{topicId}" }, method=RequestMethod.POST)
	public TechProfileTopic updateTopic(HttpServletRequest request, @PathVariable Long topicId) {
		TechProfileTopic rtn = null;
		
		try {
			String str = request.getReader().lines().collect(Collectors.joining());
			net.minidev.json.parser.JSONParser parser = new JSONParser();
			
			JSONObject obj = (JSONObject)parser.parse(str);
			
			String newName = ((JSONObject)obj.get("topic")).get("name").toString();
			
			rtn = techProfileService.updateTopic(
					topicId,
					newName);
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rtn;
	}
	
	@RequestMapping(value = { "/api/techprofile/topics/{id}/lineitem/new" }, method=RequestMethod.POST)
	public TechProfileLineItem newLineItem(HttpServletRequest request, @PathVariable Long id) {
		String name = request.getParameter("lineItemName");
		String l0desc = request.getParameter(Constants.L0DESCRIPTION);
		String l1desc = request.getParameter(Constants.L1DESCRIPTION);
		String l2desc = request.getParameter(Constants.L2DESCRIPTION);
		String l3desc = request.getParameter(Constants.L3DESCRIPTION);
		
		if (l0desc == null) l0desc = "Level 1 skill.";
		if (l1desc == null) l1desc = "Level 2 skill.";
		if (l2desc == null) l2desc = "Level 3 skill.";
		if (l3desc == null) l3desc = "Level 4 skill.";
		
		return techProfileService.addLineItem(id, name, l0desc, l1desc, l2desc, l3desc);
	}
	
	@RequestMapping(value = { "/api/techprofile/lineitem/{lineItemId}" }, method=RequestMethod.GET)
	public TechProfileLineItem getLineItem(HttpServletRequest request, @PathVariable Long lineItemId) {
		Optional<TechProfileLineItem> rtn = this.techProfileService.getLineItem(lineItemId);
		
		if (rtn.isPresent())
			return rtn.get();
		else
			return null;
	}
	
	@RequestMapping(value = { "/api/techprofile/lineitem/{lineItemId}" }, method=RequestMethod.POST)
	public TechProfileLineItem updateLineItem(HttpServletRequest request, @PathVariable Long lineItemId) {
		TechProfileLineItem rtn = null;
		
		try {
			String str = request.getReader().lines().collect(Collectors.joining());
			net.minidev.json.parser.JSONParser parser = new JSONParser();
			
			JSONObject obj = (JSONObject)((JSONObject)parser.parse(str)).get("lineItem");
			
			rtn = techProfileService.updateLineItem(
					Long.parseLong(obj.getAsString("id")),
					obj.getAsString("name"),
					obj.getAsString(Constants.L0DESCRIPTION),
					obj.getAsString(Constants.L1DESCRIPTION),
					obj.getAsString(Constants.L2DESCRIPTION),
					obj.getAsString(Constants.L3DESCRIPTION));
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rtn;
	}
	
	@RequestMapping(value = { "/api/techprofile/sequences" }, method=RequestMethod.POST)
	public boolean updateLineItemSequences(HttpServletRequest request) {
		try {
			String str = request.getReader().lines().collect(Collectors.joining());
			
			net.minidev.json.parser.JSONParser parser = new JSONParser();
			
			JSONObject obj = (JSONObject)parser.parse(str);
			JSONArray arr = (JSONArray)obj.get("arr");
			
			long numOfTopics = arr.size();
			
			for (long x=0; x < numOfTopics; x++) {
				JSONArray topicArr = (JSONArray)arr.get((int)x);
				long numOfLineItems = topicArr.size();
				
				for (long y=0; y < numOfLineItems; y++) {
					JSONArray liArr = (JSONArray)topicArr.get((int)y);
					String str2 = liArr.toString();
					str2 = str2.substring(1, str2.length() - 1);
					List<String> list = Arrays.asList(str2.split("\\s*,\\s*"));

					long[] larr = new long[5];

					larr[0] = Long.parseLong(list.get(0));
					larr[1] = Long.parseLong(list.get(1));
					larr[2] = Long.parseLong(list.get(2));
					larr[3] = Long.parseLong(list.get(3));
					larr[4] = Long.parseLong(list.get(4));

					techProfileService.updateSequencesRelatedToATopicAndItsLineItems(larr);
				}
			}
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}
	
	@RequestMapping(value = { "/api/techprofile/questionCountsPerCell" }, method=RequestMethod.GET)
	public List getQuestionCountsPerTechProfileCell() {
		List list = techProfileService.getQuestionCountsPerCell();
		
		return list;
	}
	
	@RequestMapping(value = { "/api/techprofile/user/{userId}/correctlyAnsweredQuestionCountsPerCell" }, method=RequestMethod.GET)
	public List getCorrectlyAnsweredQuestionCountsPerCell(@PathVariable Long userId) {
		List list = techProfileService.getCorrectlyAnsweredQuestionCountsPerCell(userId);
		
		return list;
	}
	@RequestMapping(value = { "/api/techprofile/user/{userId}/incorrectlyAnsweredQuestionCountsPerCell" }, method=RequestMethod.GET)
	public List getIncorrectlyAnsweredQuestionCountsPerCell(@PathVariable Long userId) {
		List list = techProfileService.getIncorrectlyAnsweredQuestionCountsPerCell(userId);
		
		return list;
	}
	@RequestMapping(value = { "/api/techprofile/user/{userId}/askedQuestionCountsPerCell" }, method=RequestMethod.GET)
	public List getAskedQuestionCountsPerCell(@PathVariable Long userId) {
		List list = techProfileService.getAskedQuestionCountsPerCell(userId);
		
		return list;
	}
}
