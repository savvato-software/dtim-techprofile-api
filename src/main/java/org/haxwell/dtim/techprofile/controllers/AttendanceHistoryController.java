package org.haxwell.dtim.techprofile.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.haxwell.dtim.techprofile.repositories.UserRepository;
import org.haxwell.dtim.techprofile.services.attendancehistory.AttendanceHistoryAPIResponse;
import org.haxwell.dtim.techprofile.services.attendancehistory.AttendanceHistoryService;
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
	public List<AttendanceHistoryAPIResponse> search(@PathVariable Long id) {
		List<AttendanceHistoryAPIResponse> rtn = new ArrayList<>();
		
		Map<Long, AttendanceHistoryAPIResponse> attendanceHistory = ahs.getAttendanceHistory(id);
		
		Set<Long> keySet = attendanceHistory.keySet();
		
		Iterator<Long> keySetIterator = keySet.iterator();
		
		while (keySetIterator.hasNext()) {
			rtn.add(attendanceHistory.get(keySetIterator.next()));
		}
		
		return rtn;
	}
}