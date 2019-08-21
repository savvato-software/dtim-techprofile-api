package org.haxwell.dtim.techprofile.controllers;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.haxwell.dtim.techprofile.entities.UserAttendanceHistory;
import org.haxwell.dtim.techprofile.repositories.UserRepository;
import org.haxwell.dtim.techprofile.services.AttendanceHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = { "/api/attendance-history" })
public class AttendanceHistoryController {

	private static final Log logger = LogFactory.getLog(AttendanceHistoryController.class);
	
	@Autowired
	AttendanceHistoryService ahs;
	
	@Autowired
	UserRepository ur;
	
	AttendanceHistoryController() {
		
	}
	
	@RequestMapping(value = { "/{id}" }, method=RequestMethod.GET)
	public List<UserAttendanceHistory> search(@PathVariable Long id) {
		return ahs.getAttendanceHistory(id);
	}
	
}